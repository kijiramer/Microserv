package com.example.frontwebservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;


import java.util.List;

@SpringBootApplication
@RestController
public class MonService {
    public static void main(String[] args) {
        SpringApplication.run(MonService.class, args);
    }
    @Autowired
    DiscoveryClient discoveryClient;
    @GetMapping("/")
    public String hello() {
        List<ServiceInstance> instances = discoveryClient.getInstances("webservice");
        ServiceInstance test = instances.get(0);
        String hostname = test.getHost();
        int port = test.getPort();

        System.out.println(hostname + " " + port);

        RestTemplate restTemplate = new RestTemplate();
        String microservice1Address = "http://" + hostname + ":" + port;
        ResponseEntity<String> response =
                restTemplate.getForEntity(microservice1Address, String.class);
        String s = response.getBody();
        return s;
    }

}
