package uz.job.javainterview.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.job.javainterview.service.DbService;

@RestController
public class DbServiceController {
    @Autowired
    DbService dbService;
    @GetMapping(value = {"/api/v1/db/init-test-data", "/init-test-data"})
    public ResponseEntity<Void> initTestData() throws Exception {
        dbService.initTestData();
        return ResponseEntity.ok().build();}
}
