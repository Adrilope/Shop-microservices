package com.springboot.app.item.clients;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.springboot.app.commons.models.entity.Product;

@FeignClient(name = "service-products")		// component managed by spring, so it can be autowired 
public interface ProductRestClient {
	
	@GetMapping("/show")
	public List<Product> findAll();
	
	@GetMapping("/product/{id}")
	public Product getProductById(@PathVariable Long id);
	
	@PostMapping("/create")
	public Product create(@RequestBody Product product);
	
	@PutMapping("/update/{id}")
	public Product update(@RequestBody Product product, @PathVariable Long id);
	
	@DeleteMapping("/delete/{id}")
	public void delete(@PathVariable Long id);
}
