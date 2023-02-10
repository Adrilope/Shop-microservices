package com.springboot.app.products.models.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.springboot.app.products.models.entity.Product;
import com.springboot.app.products.models.repo.ProductDAO;

@Service
public class ProductServiceImpl implements ProductService{

	@Autowired
	private ProductDAO productDAO;
	
	
	@Override
	@Transactional(readOnly = true)		// to sync the query with DB
	public List<Product> findAll() {
		return (List<Product>) productDAO.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Product findById(Long id) {
		return productDAO.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public Product save(Product product) {
		return productDAO.save(product);
	}

	@Override
	@Transactional
	public void deleteById(Long id) {
		productDAO.deleteById(id);
	}
	
}
