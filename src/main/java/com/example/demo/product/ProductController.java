package com.example.demo.product;

import java.time.Duration;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/products")
class ProductController {
    private ProductRepository repository;

    ProductController(ProductRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    Flux<Product> getAllProducts() {
        return repository.findAll();
    }

    @GetMapping("{id}")
    Mono<ResponseEntity<Product>> getProduct(@PathVariable String id) {
        return repository.findById(id)
                .map(product -> ResponseEntity.ok(product))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Product> saveProduct(@RequestBody Product product) {
        return repository.save(product);
    }

    @PutMapping("{id}")
    Mono<ResponseEntity<Product>> updateProduct(@PathVariable String id, @RequestBody Product product) {
        return repository.findById(id)
        .flatMap(existingProduct -> {
            existingProduct.setName(product.getName());
            existingProduct.setPrice(product.getPrice());
            return repository.save(existingProduct);
        })
        .map(updatedProduct -> ResponseEntity.ok(updatedProduct))
        .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    Mono<ResponseEntity<Void>> deleteProduct(@PathVariable String id) {
        return repository.findById(id)
        .flatMap(existingProduct -> {
            return repository.delete(existingProduct)
            .then(
                Mono.just(ResponseEntity.ok().<Void>build()));
        })
        .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping(value = "/events", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    Flux<ProductEvent> getEvents() {
        return Flux.interval(Duration.ofSeconds(2))
        .map(val ->
         new ProductEvent(UUID.randomUUID().toString()  , "Product Event")
        );
    }
}
