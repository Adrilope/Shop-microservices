package com.springboot.app.gateway.filters;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;

@Component
public class ExampleGlobalFilter implements GlobalFilter, Ordered {

	private final Logger logger = LoggerFactory.getLogger(ExampleGlobalFilter.class);
	
	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		// pre filter
		logger.info("executing pre filter");
		exchange.getRequest().mutate().headers(h -> h.add("token", "12345"));
		
		return chain.filter(exchange).then(Mono.fromRunnable(() -> {
			// post filter
			logger.info("executing post filter");
			
			Optional.ofNullable(exchange.getRequest().getHeaders().getFirst("token")).ifPresent(val -> {
				exchange.getResponse().getHeaders().add("token", val);
			});
			
			exchange.getResponse().getCookies().add("color", ResponseCookie.from("color", "red").build());
//			exchange.getResponse().getHeaders().setContentType(MediaType.TEXT_PLAIN);
		}));
	}

	@Override
	public int getOrder() {
		return 100;
	}

}
