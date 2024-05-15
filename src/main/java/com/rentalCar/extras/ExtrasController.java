package com.rentalCar.extras;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/extras")
public class ExtrasController {

    private final ExtrasService extrasService;
    @Autowired
    public ExtrasController(ExtrasService extrasService) {
        this.extrasService = extrasService;
    }

    @GetMapping()
    public List<Extras> getExtras(){
        return this.extrasService.getExtras();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Extras> getExtraById(@PathVariable Long id){
        Optional<Extras> extras = this.extrasService.getExtraById(id);
        return ResponseEntity.of(extras);
    }

    @PostMapping()
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public Extras createExtra(@RequestBody Extras extra){
        return this.extrasService.createExtra(extra);
    }

    @PutMapping()
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public Extras editExtra(@RequestBody Extras extra){
        return this.extrasService.editExtra(extra);
    }

}
