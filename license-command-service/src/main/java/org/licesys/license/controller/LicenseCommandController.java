package org.licesys.license.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.licesys.license.command.IssueLicenseCommand;
import org.licesys.license.command.UpdateLicenseCommand;
import org.licesys.license.service.LicenseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/command.licenses")
public class LicenseCommandController {

    private final LicenseService licenseService;

    @PostMapping
    public ResponseEntity<String> save(@Valid @RequestBody final IssueLicenseCommand command) {

        licenseService.issue(command);

        return ResponseEntity.ok("License saved");
    }

    @PutMapping("{licenseNumber}")
    public ResponseEntity<String> update(@PathVariable final String licenseNumber,
                                         @Valid @RequestBody final UpdateLicenseCommand command) {

        licenseService.update(licenseNumber, command);

        return ResponseEntity.ok("License updated");
    }

    @PostMapping("/invalidate/{licenseNumber}")
    public ResponseEntity<String> invalidate(@PathVariable final String licenseNumber) {

        licenseService.invalidate(licenseNumber);

        return ResponseEntity.ok("License invalidated");
    }

}
