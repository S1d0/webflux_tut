package com.example.demo;

import org.springframework.web.reactive.function.client.WebClient;

public class EmployeeWebClient {
    WebClient client = WebClient.create("http://localhost:8080");    
}
