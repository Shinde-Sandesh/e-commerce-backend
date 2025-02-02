package com.example.SpringEcommerceApp.service;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.SpringEcommerceApp.model.Product;
import com.example.SpringEcommerceApp.repo.ProductRepo;

@Service
public class ProductService {

	@Autowired
	private ProductRepo repo;
	
	public List<Product> getAllProducts() {
		// TODO Auto-generated method stub
		return repo.findAll();
	}

	public Product getProductById(int id) {
		// TODO Auto-generated method stub
		return repo.findById(id).orElse(null);
	}

	public Product addProduct(Product product, MultipartFile image) throws IOException {
		// TODO Auto-generated method stub
		product.setImageName(image.getOriginalFilename());
		product.setImageType(image.getContentType());
		product.setImageData(image.getBytes());
		return repo.save(product);
		
	}

	public Product updateProductById(Product product, MultipartFile image) throws IOException {
		// TODO Auto-generated method stub
        product.setImageName(image.getOriginalFilename());
        product.setImageType(image.getContentType());
        product.setImageData(image.getBytes());
		return repo.save(product);
	}

    public void deleteProduct(int id) {
        repo.deleteById(id);
    } 

	public List<Product> searchByKeyword(String keyword) {
		// TODO Auto-generated method stub
		return repo.search(keyword);
	}
	
//    public Product addOrUpdateProduct(Product product, MultipartFile image) throws IOException {
//        product.setImageName(image.getOriginalFilename());
//        product.setImageType(image.getContentType());
//        product.setImageData(image.getBytes());
//
//        return repo.save(product);
//    }

}
