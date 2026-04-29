package com.xoriant;

import java.util.LinkedHashSet;
import java.util.List;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;

/**
 * Browsers reject {@code Access-Control-Allow-Origin} when it appears as multiple values
 * (e.g. {@code http://localhost:5173, http://localhost:5173}) from merged gateway + downstream CORS
 * or from duplicate route/filter handling. Normalizes to a single origin when all parts agree.
 */
@Component
public class AccessControlAllowOriginSanitizeGatewayFilter implements GlobalFilter, Ordered {

  @Override
  public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
    return chain.filter(exchange).then(Mono.fromRunnable(() -> sanitize(exchange)));
  }

  private static void sanitize(ServerWebExchange exchange) {
    List<String> raw = exchange.getResponse().getHeaders().get(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN);
    if (raw == null || raw.isEmpty()) {
      return;
    }
    LinkedHashSet<String> distinct = new LinkedHashSet<>();
    for (String entry : raw) {
      if (entry == null || entry.isBlank()) {
        continue;
      }
      for (String part : entry.split(",")) {
        String t = part.trim();
        if (!t.isEmpty()) {
          distinct.add(t);
        }
      }
    }
    if (distinct.isEmpty()) {
      return;
    }
    String chosen = distinct.iterator().next();
    if (distinct.size() == 1 && raw.size() == 1) {
      String only = raw.get(0);
      if (only != null && !only.contains(",") && only.trim().equals(chosen)) {
        return;
      }
    }
    exchange.getResponse().getHeaders().put(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, List.of(chosen));
  }

  @Override
  public int getOrder() {
    return Ordered.LOWEST_PRECEDENCE - 2;
  }
}
