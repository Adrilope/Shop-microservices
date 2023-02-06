package com.springboot.app.item.clients;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.springboot.app.item.models.Product;

@FeignClient(name = "service-products")		// component managed by spring, so it can be autowired 
public interface ProductRestClient {
	
	@GetMapping("/show")
	public List<Product> findAll();
	
	
	@GetMapping("/product/{id}")
	public Product getProductById(@PathVariable Long id);
	
}
