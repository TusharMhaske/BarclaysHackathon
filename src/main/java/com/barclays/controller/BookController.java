package com.barclays.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.barclays.model.BookModel;
import com.barclays.model.ImageModel;
import com.barclays.model.PaymentRequestModel;
import com.barclays.service.BookService;
import com.instamojo.wrapper.model.PaymentOrderResponse;
 
@RestController
@RequestMapping("/api")
public class BookController {
	
	@Autowired
	private BookService bookService; 
	
	@GetMapping("get-book-list")
	public ResponseEntity<List<BookModel>> getBooks() {
		return new ResponseEntity<>(bookService.getBookList(),HttpStatus.OK); 
	}
	@GetMapping("get-image-list")
	public ResponseEntity<List<ImageModel>> getImages() {
		return new ResponseEntity<>(bookService.getImageList(),HttpStatus.OK); 
	}
	
	@PostMapping("payment")
	public ResponseEntity< PaymentOrderResponse> makePayment(@RequestBody PaymentRequestModel data){
		System.out.println("Total Price > "+data.getAmount());
		return new ResponseEntity<>(bookService.makePayment(data),HttpStatus.OK); 
	}
	
	@GetMapping("remove-cache")
	@CacheEvict(value = {"book-list-cache", "image-list-cache"}) 
	public ResponseEntity<Map<String, String>> removeCache() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("Status", "true");
		map.put("msg", "Cache Removed"); 
		return new ResponseEntity<>(map,HttpStatus.OK); 
	}

}
