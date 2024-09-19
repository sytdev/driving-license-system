package org.licesys.license.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.licesys.license.command.RegisterOwnerCommand;
import org.licesys.license.command.UpdateOwnerCommand;
import org.licesys.license.service.OwnerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/command.owners")
public class OwnerCommandController {

    private final OwnerService ownerService;

    @PostMapping
    public ResponseEntity<Void> save(@Valid @RequestBody RegisterOwnerCommand command) {
        ownerService.register(command);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{idCard}")
    public ResponseEntity<Void> update(@PathVariable String idCard, @Valid @RequestBody UpdateOwnerCommand command) {
        ownerService.update(idCard, command);
        return ResponseEntity.ok().build();
    }
}
