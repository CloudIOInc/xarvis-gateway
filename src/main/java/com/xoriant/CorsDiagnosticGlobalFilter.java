package com.xoriant;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;

/**
 * Logs final {@code Access-Control-Allow-Origin} on the gateway response (after the filter chain).
 * Enable with {@code logging.level.com.xoriant.CorsDiagnosticGlobalFilter=INFO} (or DEBUG).
 */
@Component
public class CorsDiagnosticGlobalFilter implements GlobalFilter, Ordered {

    private static final Logger log = LogManager.getLogger(CorsDiagnosticGlobalFilter.class);

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String path = exchange.getRequest().getPath().value();
        String requestOrigin = exchange.getRequest().getHeaders().getFirst(HttpHeaders.ORIGIN);
        return chain.filter(exchange).then(Mono.fromRunnable(() -> {
            List<String> allowOrigins = exchange.getResponse().getHeaders().get(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN);
            if (allowOrigins == null || allowOrigins.isEmpty()) {
                if (requestOrigin != null && path.startsWith("/api")) {
                    log.debug("CORS diagnostic: path={} requestOrigin={} — no Access-Control-Allow-Origin on response",
                            path, requestOrigin);
                }
                return;
            }
            log.info("CORS diagnostic: path={} requestOrigin={} Access-Control-Allow-Origin entries={} values={}",
                    path, requestOrigin, allowOrigins.size(), allowOrigins);
            for (String v : allowOrigins) {
                if (v != null && v.contains(",")) {
                    log.warn("CORS diagnostic: Allow-Origin value contains comma (often invalid for browsers): {}", v);
                }
            }
        }));
    }

    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE;
    }
}
