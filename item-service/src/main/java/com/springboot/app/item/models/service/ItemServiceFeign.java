package com.springboot.app.item.models.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.app.item.clients.ProductRestClient;
import com.springboot.app.item.models.Item;
import com.springboot.app.commons.models.entity.Product;

@Service("serviceFeign")
// @Primary: if we have two services implementing an interface, wherever the interface is 
// injected this is the service on usage (the other possibility is @Qualifier)
public class ItemServiceFeign implements ItemService {

	@Autowired
	private ProductRestClient productClientFeign;
	
	
	@Override
	public List<Item> findAll() {
		return productClientFeign.findAll().stream().map(p -> new Item(p, 1)).collect(Collectors.toList());
	}

	@Override
	public Item findById(Long id, Integer amount) {
		return new Item(productClientFeign.getProductById(id), amount);
	}

	@Override
	public Product save(Product product) {
		return productClientFeign.create(product);
	}

	@Override
	public Product update(Product product, Long id) {
		return productClientFeign.update(product, id);
	}

	@Override
	public void delete(Long id) {
		productClientFeign.delete(id);
	}

}
