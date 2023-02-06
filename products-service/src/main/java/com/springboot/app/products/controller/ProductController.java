package com.springboot.app.products.controller;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.app.products.models.entity.Product;
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

}
