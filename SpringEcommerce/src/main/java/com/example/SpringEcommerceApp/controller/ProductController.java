package com.example.SpringEcommerceApp.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.SpringEcommerceApp.model.Product;
import com.example.SpringEcommerceApp.service.ProductService;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class ProductController {

	@Autowired
	private ProductService productService;
	
	@GetMapping("/products")
	public ResponseEntity<List<Product>>  getProductString () {
		return new ResponseEntity<>(productService.getAllProducts(), HttpStatus.OK);
	}
	
	@GetMapping("/product/{id}")
	public ResponseEntity<Product> getProductById (@PathVariable("id") int id) {
		
		Product product = productService.getProductById(id);
		
		if(product != null) {
			return new ResponseEntity<>( product, HttpStatus.OK);			
		}
		else {
			return new ResponseEntity<>( HttpStatus.NOT_FOUND);
		}
	}
	
    @GetMapping("/product/{productId}/image")
    public ResponseEntity<byte[]> getProductImage(@PathVariable int productId) {
        Product product = productService.getProductById(productId);
        if (product.getId() > 0) {
            return new ResponseEntity<>(product.getImageData(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
	 
	@PostMapping("/product") 
	public ResponseEntity<?> addProduct(@RequestPart Product product, @RequestPart MultipartFile imageFile) {
		Product savedProduct = null;
		
		try {
			savedProduct = productService.addProduct(product, imageFile);
			return new ResponseEntity<>(savedProduct, HttpStatus.CREATED);
		} catch (Exception e) {
			// TODO: handle exception
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/product/{id}")
	public ResponseEntity<String> updateProductById (@PathVariable int id, @RequestPart Product product, @RequestPart MultipartFile imageFile) {
		
		@SuppressWarnings("unused")
		Product updatedProduct = null;
		
		try {
			updatedProduct = productService.updateProductById(product, imageFile);
			return new ResponseEntity<>("Updated", HttpStatus.OK);
		} catch (IOException e) {
			// TODO: handle exception
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
    @DeleteMapping("/product/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable int id) {
        Product product = productService.getProductById(id);
        if (product != null) {
            productService.deleteProduct(id);
            return new ResponseEntity<>("Deleted", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Not Found", HttpStatus.NOT_FOUND);
        }
    }
    
    @GetMapping("/products/search")
    public ResponseEntity<List<Product>> searchProduct(@RequestParam String keyword) {
    	List<Product> products = productService.searchByKeyword(keyword);
    	return new ResponseEntity<>(products, HttpStatus.OK);
    }
}




