package com.sos.service.impl;

import com.sos.models.Products;
import com.sos.repository.ProductRepository;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sos.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {
	
	@Autowired
	private ProductRepository productRepository;
	
	@Override
	public Products save(Products entity) {
		return productRepository.save(entity);
	}

	@Override
	public Products update(Products entity) {
		return productRepository.save(entity);
	}

	@Override
	public void delete(Products entity) {
		productRepository.delete(entity);
	}

	@Override
	public void delete(Long id) {
		productRepository.delete(id);
	}

	@Override
	public Products find(Long id) {
		return productRepository.findOne(id);
	}

	@Override
	public List<Products> findAll() {
		return productRepository.findAll();
	}

	@Override
	public boolean authenticate(String username, String password){
		Products pro = this.findByEmail(username);
		if(pro == null){
			return false;
		}else{
			if(password.equals(pro.getPassword())) return true;
			else return false;
		}
	}

	@Override
	public Products findByEmail(String email) {
		return productRepository.findByEmail(email);
	}

	@Override
	public void deleteInBatch(List<Products> users) {
		productRepository.deleteInBatch(users);
	}
	
}
