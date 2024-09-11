package org.licsys.licenseservice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/licenses")
public class LicenseController {

    @GetMapping
    public ResponseEntity<String> getLicense() {
        return ResponseEntity.ok("This is the license");
    }
}
