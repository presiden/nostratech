package com.example.movie.util;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service("authorityService")
public class AuthorityService {
//    @Value("${app.authorities}") // read roles from properties file
    private String authorities = "ADMIN";
    
    public List<String> getAuthorities(){
        // convert the comma separated Strings to list.
        List<String> items = Arrays.asList(authorities.split("\\s*,\\s*")); 
        return items;
    }
}
