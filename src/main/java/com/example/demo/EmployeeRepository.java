package com.example.demo;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import reactor.core.publisher.Mono;

@Component
public class EmployeeRepository {
    private Map<String, Employee> employes;

    public EmployeeRepository() {
        this.employes = new HashMap<>();
        this.employes.put("1", new Employee("1", "Adam", "Nowak"));
    }

    public Mono<Employee> getEmployById(String id) {
        Employee e = employes.get(id);
        return Mono.just(e);
    }

}
