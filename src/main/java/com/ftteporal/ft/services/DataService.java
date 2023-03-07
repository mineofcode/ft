package com.ftteporal.ft.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

@Service
public class DataService {

    @Autowired
    RestTemplate restTemplate;

    public HashMap getData() {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<HashMap> entity = new HttpEntity<HashMap>(headers);
        System.out.println("there");
        HashMap Data = restTemplate.exchange("https://reqres.in/api/users?delay=2", HttpMethod.GET, entity, HashMap.class).getBody();
        return Data;
    }


    public HashMap getUserData() {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<HashMap> entity = new HttpEntity<HashMap>(headers);

        HashMap d =   restTemplate.exchange("https://reqres.in/api/users/2", HttpMethod.GET, entity, HashMap.class).getBody();
        return d;//d.get("data").toString();
    }



}
