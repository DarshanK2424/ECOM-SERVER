package com.clgw.ecom_server.controllers;

import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.clgw.ecom_server.repos.ProductRepo;

import io.swagger.v3.oas.annotations.tags.Tag;

import com.clgw.ecom_server.exceptions.ResourceNotFoundException;
import com.clgw.ecom_server.models.Product;

@RestController
@RequestMapping("/products")//to provide path to our api
public class ProductController {
	
	private static final Logger log=LoggerFactory.getLogger(ProductController.class);	
	
	@Autowired ProductRepo productrepo;
	
	@Tag(name="Get all products")
	
	@GetMapping("/all")
	public List<Product> getAllProducts(){
		log.info("Fetching products");
		return productrepo.findAll();
	}
	
	@PostMapping("/add")
	public Product addproduct(@RequestBody Product newproduct) {
		log.info("Adding product"+" "+newproduct);
		return productrepo.save(newproduct);
	}
	
	@DeleteMapping("/product/delete/{id}")
	public String deleteproduct(@PathVariable String id) {
		Optional<Product> findproduct=productrepo.findById(id);
		if(findproduct.isEmpty()) {
			log.error("Failed to delete product"+id);
			
			throw new ResourceNotFoundException("Product not found");
			
		}
		productrepo.deleteById(id);
		log.info("Product deleted successfully");
		return "Product Deleted";
		
	}
	
	@PutMapping ("/product/edit/{id}")
	public Product editPorduct(@PathVariable String id, @RequestBody Product newproduct) {
		Product findproduct = productrepo.findById(id).get();
		findproduct.setName(newproduct.getName());
		findproduct.setDescription(newproduct.getDescription());
		findproduct.setCategory(newproduct.getCategory());
		findproduct.setTags(newproduct.getTags());
		findproduct.setPrice(newproduct.getPrice());
		findproduct.setStock(newproduct.getStock());
		return productrepo.save(findproduct);
	}
	
	

}
