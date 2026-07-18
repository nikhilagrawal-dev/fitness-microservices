package com.fitness.gateway;

import com.fitness.gateway.user.RegisterRequest;
import com.fitness.gateway.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Component
@Slf4j
@RequiredArgsConstructor
public class KeycloakUserSyncFilter implements WebFilter {

    private final UserService userService;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {

        return exchange.getPrincipal()
                .cast(JwtAuthenticationToken.class)
                .flatMap(authentication -> {

                    log.info("========== KEYCLOAK USER SYNC FILTER ==========");

                    // Log complete JWT claims
                    log.info("JWT Claims: {}", authentication.getToken().getClaims());

                    // Log individual claims
                    log.info("JWT Subject (sub): {}", authentication.getToken().getSubject());
                    log.info("JWT Email: {}", authentication.getToken().getClaimAsString("email"));
                    log.info("JWT First Name: {}", authentication.getToken().getClaimAsString("given_name"));
                    log.info("JWT Last Name: {}", authentication.getToken().getClaimAsString("family_name"));

                    RegisterRequest registerRequest = new RegisterRequest();
                    registerRequest.setKeycloakId(authentication.getToken().getSubject());
                    registerRequest.setEmail(authentication.getToken().getClaimAsString("email"));
                    registerRequest.setFirstName(authentication.getToken().getClaimAsString("given_name"));
                    registerRequest.setLastName(authentication.getToken().getClaimAsString("family_name"));
                    registerRequest.setPassword("dummy@123123");

                    log.info("RegisterRequest Created: {}", registerRequest);

                    String userId = exchange.getRequest()
                            .getHeaders()
                            .getFirst("X-User-ID");

                    log.info("Incoming X-User-ID Header: {}", userId);

                    if (userId == null) {
                        userId = registerRequest.getKeycloakId();
                        log.info("Using KeycloakId as UserId: {}", userId);
                    }

                    String finalUserId = userId;

                    log.info("Calling validateUser({})", finalUserId);

                    return userService.validateUser(finalUserId)
                            .doOnNext(exists ->
                                    log.info("User Exists? {}", exists)
                            )
                            .flatMap(exists -> {

                                if (!exists) {

                                    log.info("User not found. Registering user...");

                                    log.info("Sending RegisterRequest to User Service:");
                                    log.info("KeycloakId : {}", registerRequest.getKeycloakId());
                                    log.info("Email      : {}", registerRequest.getEmail());
                                    log.info("First Name : {}", registerRequest.getFirstName());
                                    log.info("Last Name  : {}", registerRequest.getLastName());

                                    return userService.registerUser(registerRequest)
                                            .doOnSuccess(response ->
                                                    log.info("User Registered Successfully: {}", response)
                                            );
                                }

                                log.info("User already exists. Skipping registration.");
                                return Mono.empty();
                            })
                            .then(Mono.defer(() -> {

                                log.info("Adding X-User-ID Header: {}", finalUserId);

                                ServerHttpRequest mutatedRequest =
                                        exchange.getRequest()
                                                .mutate()
                                                .header("X-User-ID", finalUserId)
                                                .build();

                                log.info("Forwarding request to downstream service.");
                                log.info("==============================================");

                                return chain.filter(
                                        exchange.mutate()
                                                .request(mutatedRequest)
                                                .build()
                                );
                            }));
                })
                .onErrorResume(ex -> {

                    log.error("========== FILTER ERROR ==========");
                    log.error("Exception: ", ex);
                    log.error("==================================");

                    return chain.filter(exchange);
                })
                .switchIfEmpty(chain.filter(exchange));
    }
}