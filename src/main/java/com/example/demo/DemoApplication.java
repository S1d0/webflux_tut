package com.example.demo;

import com.example.demo.product.Product;
import com.example.demo.product.ProductRepository;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import reactor.core.publisher.Flux;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Bean
	CommandLineRunner init(ProductRepository repository) {
		return args -> {
			Flux<Product> productFlux = Flux.just(
				new Product(null, "Hammer", 19.99),
				new Product(null, "Jam", 9.99),
				new Product(null, "Boots", 39.99))
				.flatMap(repository::save);

				productFlux
					.thenMany(repository.findAll())
					.subscribe(System.out::println);
		};
	}
}
