package com.springboot.app.products.models.repo;

import org.springframework.data.repository.CrudRepository;

import com.springboot.app.commons.models.entity.Product;

public interface ProductDAO extends CrudRepository<Product, Long> {
	
}
