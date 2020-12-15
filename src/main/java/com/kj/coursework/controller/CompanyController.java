package com.kj.coursework.controller;

import com.kj.coursework.service.CompanyService;
import com.kj.coursework.util.request.CompanyRequest;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/companies")
public class CompanyController {
    @Autowired
    private CompanyService service;

    @PostMapping
    public ResponseEntity<?> createCompany(@RequestBody CompanyRequest companyRequest) {
        try {
            return new ResponseEntity<>(service.save(companyRequest), HttpStatus.OK);
        }
        catch(Exception e) {
            return new ResponseEntity<>(e.getLocalizedMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCompany(@PathVariable String id, @RequestBody CompanyRequest companyRequest) {
        try {
            return new ResponseEntity<>(service.update(new ObjectId(id), companyRequest), HttpStatus.OK);
        }
        catch(Exception e) {
            return new ResponseEntity<>(e.getLocalizedMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCompany(@PathVariable String id) {
        try {
            return new ResponseEntity<>(service.get(new ObjectId(id)), HttpStatus.OK);
        }
        catch(Exception e) {
            return new ResponseEntity<>(e.getLocalizedMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCompany(@PathVariable String id) {
        return new ResponseEntity<>(service.delete(new ObjectId(id)), HttpStatus.OK);
    }
}
