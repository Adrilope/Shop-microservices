package com.springboot.app.gateway.filters.factory;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.OrderedGatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

import reactor.core.publisher.Mono;

@Component
public class ExampleGatewayFilterFactory extends AbstractGatewayFilterFactory<ExampleGatewayFilterFactory.Config> {

	private final Logger logger = LoggerFactory.getLogger(ExampleGatewayFilterFactory.class);
	
	
	public ExampleGatewayFilterFactory() {
		super(Config.class);
	}
	

	@Override
	public GatewayFilter apply(Config config) {
		return new OrderedGatewayFilter((exchange, chain) -> {	// filter() of GatewayFilter
			logger.info("Executing gateway pre filter factory: " + config.message);
			
			return chain.filter(exchange).then(Mono.fromRunnable(() -> {
				Optional.ofNullable(config.cookieValue).ifPresent(cookie -> {	  // only if cookie has a val, we create a cookie
					exchange.getResponse().addCookie(ResponseCookie.from(config.cookieName, cookie).build());
				});
				
				logger.info("Executing gateway post filter factory: " + config.message);
				
			}));
		}, 2);		// order
	}
	
	@Override
	public List<String> shortcutFieldOrder() {
		return Arrays.asList("message", "cookieName", "cookieValue");
	}

	@Override
	public String name() {
		return "ExampleCookie";
	}




	public static class Config {
		private String message;
		private String cookieValue;
		private String cookieName;
		
		
		public String getMessage() {
			return message;
		}
		public void setMessage(String message) {
			this.message = message;
		}
		public String getCookieValue() {
			return cookieValue;
		}
		public void setCookieValue(String cookieValue) {
			this.cookieValue = cookieValue;
		}
		public String getCookieName() {
			return cookieName;
		}
		public void setCookieName(String cookieName) {
			this.cookieName = cookieName;
		}
	}

}
