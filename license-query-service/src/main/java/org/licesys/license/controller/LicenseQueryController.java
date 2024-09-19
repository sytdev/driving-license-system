package org.licesys.license.controller;

import lombok.RequiredArgsConstructor;
import org.licesys.license.dto.DisplayLicenseValidityDTO;
import org.licesys.license.dto.DisplayLicensesDTO;
import org.licesys.license.query.FilterLicensesQuery;
import org.licesys.license.service.LicenseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/query.licenses")
public class LicenseQueryController {

    private final LicenseService licenseService;

    @GetMapping("/validate/{licenseNumber}")
    public ResponseEntity<DisplayLicenseValidityDTO> validate(@PathVariable final String licenseNumber){
        return ResponseEntity.ok(licenseService.displayLicenseValidity(licenseNumber));
    }

    @PostMapping
    public ResponseEntity<List<DisplayLicensesDTO>> display(@RequestBody final FilterLicensesQuery query){
        return ResponseEntity.ok(licenseService.displayLicenses(query));
    }
}
