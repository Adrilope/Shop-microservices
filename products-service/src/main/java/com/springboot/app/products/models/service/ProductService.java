package com.springboot.app.products.models.service;

import java.util.List;

import com.springboot.app.commons.models.entity.Product;

public interface ProductService {
	
	public List<Product> findAll();
	
	public Product findById(Long id);
	
	public Product save(Product product);
	
	public void deleteById(Long id);
}
