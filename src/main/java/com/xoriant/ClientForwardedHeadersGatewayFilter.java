package com.xoriant;

import java.util.Optional;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;

/**
 * Sets {@code X-Forwarded-Host}, {@code X-Forwarded-Proto}, and {@code X-Forwarded-Port} from the
 * <em>browser-facing</em> request URI seen by the gateway so downstream servlet apps (e.g.
 * {@code xarvis-authentication}) resolve {@link jakarta.servlet.http.HttpServletResponse#sendRedirect(String)}
 * against {@code localhost:8088} instead of the Eureka instance ({@code 192.168.x.x:8080}).
 * <p>
 * Overwrites any client-supplied forwarded headers (gateway is the trust boundary for local / same-cluster use).
 */
@Component
public class ClientForwardedHeadersGatewayFilter implements GlobalFilter, Ordered {

  @Override
  public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
    ServerHttpRequest req = exchange.getRequest();
    var uri = req.getURI();
    String scheme = Optional.ofNullable(uri.getScheme()).orElse("http");
    String host = uri.getHost();
    if (host == null || host.isBlank()) {
      return chain.filter(exchange);
    }
    int port = uri.getPort();
    boolean defaultPort = ("http".equalsIgnoreCase(scheme) && port == -1)
        || ("http".equalsIgnoreCase(scheme) && port == 80)
        || ("https".equalsIgnoreCase(scheme) && (port == -1 || port == 443));
    String xfHost = defaultPort ? host : (host + ":" + port);
    String xfPort = port == -1 ? ("https".equalsIgnoreCase(scheme) ? "443" : "80") : Integer.toString(port);

    ServerHttpRequest.Builder b = req.mutate();
    b.headers(headers -> {
      headers.remove("X-Forwarded-Host");
      headers.remove("X-Forwarded-Proto");
      headers.remove("X-Forwarded-Port");
      headers.remove("X-Forwarded-Prefix");
      headers.set("X-Forwarded-Host", xfHost);
      headers.set("X-Forwarded-Proto", scheme);
      headers.set("X-Forwarded-Port", xfPort);
    });
    return chain.filter(exchange.mutate().request(b.build()).build());
  }

  @Override
  public int getOrder() {
    return 10_000;
  }
}
