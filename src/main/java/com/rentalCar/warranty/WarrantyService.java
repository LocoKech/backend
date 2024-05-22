package com.rentalCar.warranty;

import com.rentalCar.booking.EmailService;
import com.rentalCar.images.S3Service;
import com.rentalCar.maintenance.Maintenance;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class WarrantyService {

    private final WarrantyRepository warrantyRepository;

    @Autowired
    private EmailService emailService;

    @Value("${application.bucket.name}")
    private String bucketName;

    @Autowired
    private S3Service s3Service;

    @Value("${admin.email}")
    private String adminEmail;

    @Autowired
    public WarrantyService(WarrantyRepository warrantyRepository) {
        this.warrantyRepository = warrantyRepository;
    }

    public List<Warranty> getAllWarranties() {
        return warrantyRepository.findAll();
    }

    public Optional<Warranty> getWarrantyById(Long id) {
        return warrantyRepository.findById(id);
    }

    public Warranty createWarranty(Warranty warranty) {
        return warrantyRepository.save(warranty);
    }

    public Warranty updateWarranty(Long id, Warranty warranty) {
        return warrantyRepository.save(warranty);
    }

    public void deleteWarranty(Long id) {
        warrantyRepository.deleteById(id);
    }

    @Scheduled(cron = "0 0 0 * * ?") // Runs every day at midnight
    public void checkExpiringWarranties() {
        List<Warranty> warranties = warrantyRepository.findAll();
        List<Warranty> expiringWarranties = warranties.stream()
                .filter(this::isExpiringSoon)
                .collect(Collectors.toList());
        expiringWarranties.forEach(this::sendReminder);
    }

    private boolean isExpiringSoon(Warranty warranty) {
        LocalDate warningDate = LocalDate.now().plusWeeks(1); // Notify 1 week before expiry
        return warranty.getEndDate().isBefore(warningDate) || warranty.getEndDate().isEqual(warningDate);
    }

    private void sendReminder(Warranty warranty) {
        String subject = "Warranty Expiry Reminder: " + warranty.getCar().getMatriculate();
        String text = "The warranty for car " + warranty.getCar().getMatriculate() +
                " provided by " + warranty.getWarrantyProvider() +
                " is expiring on " + warranty.getEndDate() + ".";
        emailService.sendSimpleMessage(adminEmail, subject, text);
    }

    public void addInvoice(Long warrantyId, List<MultipartFile> files) throws IOException {

        Warranty warranty = this.warrantyRepository.findById(warrantyId)
                .orElseThrow(() -> new EntityNotFoundException("Waranty not found"));;

        List<String> details = warranty.getInvoices() != null ? new ArrayList<>(warranty.getInvoices()) : new ArrayList<>();
        for (MultipartFile file : files) {
            String detailImageUrl = s3Service.uploadFile(file, bucketName);
            details.add(detailImageUrl);
        }
    }

    public List<Warranty> getDueWarranties() {
        List<Warranty> maintenances = this.warrantyRepository.findAll();
        return maintenances.stream()
                .filter(this::isExpiringSoon)
                .collect(Collectors.toList());
    }
}
