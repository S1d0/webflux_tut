package com.example.demo.product;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Document
@Data
public class Product {
	@Id
	private String id;
	private String name;
	private Double price;

	public Product() {
	}

	public Product(String id, String name, Double price) {
		this.id = id;
		this.name = name;
		this.price = price;
	}
}
