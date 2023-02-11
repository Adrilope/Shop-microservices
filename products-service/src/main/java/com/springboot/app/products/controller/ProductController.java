package com.springboot.app.products.controller;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.app.commons.models.entity.Product;
import com.springboot.app.products.models.service.ProductService;

@RestController
public class ProductController {
	
	@Autowired
	private Environment env;	// interface to get any env var
	
	@Value("${server.port}")
	private Integer port;
	
	@Autowired
	private ProductService productService;
	
	
	@GetMapping("/show")
	public List<Product> getAllProducts() {
		return productService.findAll().stream().map(product -> {
			product.setPort(Integer.parseInt(env.getProperty("local.server.port")));
//			product.setPort(port);
			return product;
		}).collect(Collectors.toList());
	}
	
	@GetMapping("/product/{id}")
	public Product getProductById(@PathVariable Long id) throws InterruptedException {
		if (id.equals(10L)) {
			throw new IllegalStateException("Product not found");
		}
		
		if (id.equals(7L)) {
			TimeUnit.SECONDS.sleep(5L);		// simulate a timeout
		}
		
		Product product = productService.findById(id);
		product.setPort(Integer.parseInt(env.getProperty("local.server.port")));
//		product.setPort(port);
		
		return product;
	}

	@PostMapping("/create")
	@ResponseStatus(HttpStatus.CREATED)
	public Product create(@RequestBody Product product) {
		return productService.save(product);
	}
	
	@PutMapping("/update/{id}")
	@ResponseStatus(HttpStatus.CREATED)
	public Product edit(@RequestBody Product product, @PathVariable Long id) {
		Product productDb = productService.findById(id);
		
		productDb.setName(product.getName());
		productDb.setPrice(product.getPrice());
		
		return productService.save(productDb);
	}
	
	@DeleteMapping("/delete/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long id) {
		productService.deleteById(id);
	}
	
}
