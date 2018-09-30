package com.yyq.es_retreatment.controller;

import com.yyq.es_retreatment.repository.EnAndChRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SearchController {
    @Autowired
    EnAndChRepository repo;
    @GetMapping("/get_data")
    public ResponseEntity getData() {
        return ResponseEntity.ok().body(repo.count());
    }
}
