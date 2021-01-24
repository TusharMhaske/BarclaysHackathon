package com.barclays.model;

import lombok.Data;

@Data
public class BookModel {
	private int bookID;
	private String title;
	private String authors;
	private float avgRating;
	private long isbn;
	private String lanCode;
	private long ratingCount;
	private double price; 
 

}

 