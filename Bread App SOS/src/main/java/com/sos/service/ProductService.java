package com.sos.service;

import com.sos.models.Products;
import com.sos.generic.GenericService;

public interface ProductService extends GenericService<Products> {

	boolean authenticate(String email, String password);
	
	Products findByEmail(String email);
	
}
