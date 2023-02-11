package com.springboot.app.item.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.app.item.models.Item;
import com.springboot.app.item.models.Product;
import com.springboot.app.item.models.service.ItemService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;

@RefreshScope		// allows @Component, @Controller... to refresh the context and their @Value or Environment without restarting the server
@RestController
public class ItemController {
	
	private final Logger logger = LoggerFactory.getLogger(ItemController.class);
	
	@Value("${config.text}")
	private String text;
	
	@Autowired
	private Environment env;
	
	@Autowired
	private CircuitBreakerFactory cbFactory;
	
	@Autowired
	@Qualifier("serviceFeign")	// identify the service to use
	private ItemService itemService;
	
	
	@GetMapping("/show")
	public List<Item> getAllItems(@RequestParam(name="username", required=false) String username, @RequestHeader(name="token-request", required=false) String token) {
		System.out.println(username);
		System.out.println(token);
		return itemService.findAll();
	}
	
	// if there is an error on the service that we are consuming, it changes to the fallbackMethod
	// @HystrixCommand(fallbackMethod = "alternativeMethod")
	@GetMapping("/item/{id}/amount/{amount}")
	public Item getItemById(@PathVariable Long id, @PathVariable Integer amount) {
		// create a cb and then try to use the service and then, if there are fails, calls the alternative method
		return cbFactory.create("items")
					.run(() -> itemService.findById(id, amount), e -> alternativeMethod(id, amount, e));
	}
	
	@CircuitBreaker(name="items", fallbackMethod="alternativeMethod")
	@GetMapping("/item2/{id}/amount/{amount}")
	public Item getItemById2(@PathVariable Long id, @PathVariable Integer amount) {
		return itemService.findById(id, amount);
	}
	
	@CircuitBreaker(name="items", fallbackMethod="alternativeMethod2")
	@TimeLimiter(name="items")
	@GetMapping("/item3/{id}/amount/{amount}")
	public CompletableFuture<Item> getItemById3(@PathVariable Long id, @PathVariable Integer amount) {
		return CompletableFuture.supplyAsync(() -> itemService.findById(id, amount));
	}
	
	public Item alternativeMethod(Long id, Integer amount, Throwable e) {
		logger.info(e.getMessage());
		
		Item item = new Item();
		Product product = new Product();
		
		item.setAmount(amount);
		product.setId(id);
		product.setName("Sony Camera");
		product.setPrice(500.00);
		item.setProduct(product);
		
		return item;
	}
	
	public CompletableFuture<Item> alternativeMethod2(Long id, Integer amount, Throwable e) {
		logger.info(e.getMessage());
		
		Item item = new Item();
		Product product = new Product();
		
		item.setAmount(amount);
		product.setId(id);
		product.setName("Sony Camera");
		product.setPrice(500.00);
		item.setProduct(product);
		
		return CompletableFuture.supplyAsync(() -> item);
	}
	
	@GetMapping("/getConfig")
	public ResponseEntity<?> getConfig(@Value("${server.port}") String port) {
		logger.info(text);
		
		Map<String, String> json = new HashMap<>();
		
		json.put("text", text);
		json.put("port", port);
		
		if(env.getActiveProfiles().length > 0 && env.getActiveProfiles()[0].equals("dev")) {
			json.put("author.name", env.getProperty("config.author.name"));
			json.put("author.email", env.getProperty("config.author.email"));
		}
		
		return new ResponseEntity<Map<String, String>>(json, HttpStatus.OK);
	}
	
	@PostMapping("/create")
	@ResponseStatus(HttpStatus.CREATED)
	public Product create(@RequestBody Product product) {
		return itemService.save(product);
	}
	
	@PutMapping("/update/{id}")
	@ResponseStatus(HttpStatus.CREATED)
	public Product update(@RequestBody Product product, @PathVariable Long id) {
		return itemService.update(product, id);
	}
	
	@DeleteMapping("delete/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long id) {
		itemService.delete(id);
	}
}
