package com.springboot.app.item;

import java.time.Duration;

import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JCircuitBreakerFactory;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JConfigBuilder;
import org.springframework.cloud.client.circuitbreaker.Customizer;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;

@Configuration
public class AppConfig {
	
	@Bean("clientRest")		// name
	@LoadBalanced
	public RestTemplate registerRestTemplate() {
		return new RestTemplate();
	}
	
	@Bean
	public Customizer<Resilience4JCircuitBreakerFactory> defaultCustomizer() {
		return factory -> factory.configureDefault(id  -> {				// id = circuitBreakerName
			return new Resilience4JConfigBuilder(id)
					.circuitBreakerConfig(CircuitBreakerConfig.custom()
							.slidingWindowSize(10)						// number of requests (default 100)
							.failureRateThreshold(50)					// percentage of failure
							.waitDurationInOpenState(Duration.ofSeconds(10L))
							.permittedNumberOfCallsInHalfOpenState(5)
							.slowCallRateThreshold(50)
							.slowCallDurationThreshold(Duration.ofSeconds(2L))
							.build())
					.timeLimiterConfig(TimeLimiterConfig.custom()
							.timeoutDuration(Duration.ofSeconds(3L))	// timeout for slow requests (default 1s)
							.build())
					.build();
		});
	}
}
