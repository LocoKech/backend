package com.rentalCar.warranty;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;


    @RestController
    @RequestMapping("/warranties")

    public class WarrantyController     {

        private final WarrantyService warrantyService;

        @Autowired
        public WarrantyController(WarrantyService warrantyService) {
            this.warrantyService = warrantyService;
        }

        @GetMapping
        public ResponseEntity<List<Warranty>> getAllWarranties() {
            List<Warranty> warranties = warrantyService.getAllWarranties();
            return ResponseEntity.ok().body(warranties);
        }

        @GetMapping("/{id}")
        public ResponseEntity<Warranty> getWarrantyById(@PathVariable Long id) {
            Optional<Warranty> warranty = warrantyService.getWarrantyById(id);
            return warranty.map(value -> ResponseEntity.ok().body(value))
                    .orElseGet(() -> ResponseEntity.notFound().build());
        }

        @PostMapping
        public ResponseEntity<Warranty> createWarranty(@RequestBody Warranty warranty) {
            Warranty createdWarranty = warrantyService.createWarranty(warranty);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdWarranty);
        }

        @PutMapping("/{id}")
        public ResponseEntity<Warranty> updateWarranty(@PathVariable Long id, @RequestBody Warranty warranty) {
            Warranty updatedWarranty = warrantyService.updateWarranty(id, warranty);
            return ResponseEntity.ok().body(updatedWarranty);
        }

        @DeleteMapping("/{id}")
        public ResponseEntity<Void> deleteWarranty(@PathVariable Long id) {
            warrantyService.deleteWarranty(id);
            return ResponseEntity.noContent().build();
        }

        @PostMapping("/{maintenanceId}/invoices")
        public ResponseEntity<String> addInvoice(@PathVariable Long warantyId, @RequestParam("file") List<MultipartFile> file)  {
            try{
                warrantyService.addInvoice(warantyId, file);
                return ResponseEntity.ok().body("invoices uploaded successfully.");
            }catch (IOException e) {
                e.printStackTrace();
                return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        @GetMapping("/due")
        public List<Warranty> getDueWarranties(){
            return this.warrantyService.getDueWarranties();
        }
    }

