package com.barclays.service;

import java.util.List;
import java.util.UUID;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.barclays.constants.ApplicationConstant;
import com.barclays.model.BookModel;
import com.barclays.model.ImageModel;
import com.barclays.model.PaymentRequestModel;
import com.instamojo.wrapper.api.ApiContext;
import com.instamojo.wrapper.api.Instamojo;
import com.instamojo.wrapper.api.InstamojoImpl;
import com.instamojo.wrapper.exception.ConnectionException;
import com.instamojo.wrapper.exception.HTTPException;
import com.instamojo.wrapper.model.PaymentOrder;
import com.instamojo.wrapper.model.PaymentOrderResponse;

@Service
public class BookService {
	
	@Cacheable(value = "book-list-cache")
	public List<BookModel> getBookList() {
		 String _url = "https://s3-ap-southeast-1.amazonaws.com/he-public-data/books8f8fe52.json";
		 RestTemplate rst = new RestTemplate();
		 List<BookModel> bookList = rst.getForObject(_url, List.class);  
		 return bookList;
	} 
	
	@Cacheable(value = "image-list-cache")
	public List<ImageModel> getImageList() { 
		 String _imageurl = "https://s3-ap-southeast-1.amazonaws.com/he-public-data/bookimage816b123.json";
		 RestTemplate rst = new RestTemplate(); 
		 List<ImageModel> imageList = rst.getForObject(_imageurl, List.class); 
		 return  imageList;
	} 
	
	public List<BookModel> getListFromCache()  {
		return getBookList();
	}
	public  List<ImageModel> getImagesFromCache()  {
		return getImageList();
	}
	
	public PaymentOrderResponse makePayment(PaymentRequestModel data) {
		ApiContext context = ApiContext.create(ApplicationConstant.PARAM_CLIENT_ID, 
				ApplicationConstant.PARAM_CLIENT_SECRET,
											   ApiContext.Mode.TEST);
		
		Instamojo api = new InstamojoImpl(context); 
		
        PaymentOrder order = new PaymentOrder();
        UUID uuid1 = UUID.randomUUID(); 
        order.setName(data.getName());
        order.setEmail(ApplicationConstant.EMAIL);
        order.setPhone(ApplicationConstant.PHONE);
        order.setCurrency(ApplicationConstant.CURRANCY);
        order.setAmount(data.getAmount());
        order.setDescription("This is a test transaction.");
        order.setRedirectUrl(ApplicationConstant.REDIRECT);
        //order.setWebhookUrl(ApplicationConstant.REDIRECT);
        order.setTransactionId(uuid1.toString());
        PaymentOrderResponse paymentOrderResponse = null;
        try {
            paymentOrderResponse = api.createPaymentOrder(order);
            System.out.println(paymentOrderResponse.getPaymentOrder().getStatus());

        } catch (HTTPException e) {
            System.out.println(e.getStatusCode());
            System.out.println(e.getMessage());
            System.out.println(e.getJsonPayload());

        } catch (ConnectionException e) {
            System.out.println(e.getMessage());
        }
		
		return paymentOrderResponse;
	}
 

}
