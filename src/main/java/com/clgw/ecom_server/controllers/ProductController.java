package com.clgw.ecom_server.controllers;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.clgw.ecom_server.repos.ProductRepo;
import com.clgw.ecom_server.models.Product;

@RestController
@RequestMapping("/products")//to provide path to our api
public class ProductController {
	@Autowired ProductRepo productrepo;
	
	@GetMapping("/all")
	public List<Product> getAllProducts(){
		return productrepo.findAll();
	}
	
	@PostMapping("/add")
	public Product addproduct(@RequestBody Product newproduct) {
		return productrepo.save(newproduct);
	}
	
	@DeleteMapping("/product/delete/{id}")
	public String deleteproduct(@PathVariable String id) {
		Product findproduct=productrepo.findById(id).get();
		if(findproduct!=null) {
			productrepo.deleteById(id);
			return "Product deleted"+findproduct.getName();
		}
		else {
			return "Product not found";
		}
		
	}
		
	

}
