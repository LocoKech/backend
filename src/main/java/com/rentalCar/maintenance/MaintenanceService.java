package com.rentalCar.maintenance;

import com.rentalCar.images.S3Service;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MaintenanceService {

    private final MaintenanceRepository maintenanceRepository;

    @Value("${application.bucket.name}")
    private String bucketName;

    @Autowired
    private JavaMailSender emailSender;

    @Autowired
    private S3Service s3Service;
    @Autowired
    public MaintenanceService(MaintenanceRepository maintenanceRepository) {
        this.maintenanceRepository = maintenanceRepository;
    }


    public Maintenance createMaintenance(Maintenance maintenance) {
        return this.maintenanceRepository.save(maintenance);
    }

    public Maintenance getMaintenanceById(Long id) {
        return this.maintenanceRepository.getReferenceById(id);
    }

    public Maintenance updateMaintenance(Maintenance maintenance) {
        return this.maintenanceRepository.save(maintenance);
    }

    public void deleteMaintenance(Long id) {
        this.maintenanceRepository.deleteById(id);
    }

    public List<Maintenance> getAllMaintenance() {
        return this.maintenanceRepository.findAll();
    }



    @Scheduled(cron = "0 0 0 * * ?") // Runs every day at midnight
    public void checkMaintenance() {
        List<Maintenance> maintenances = maintenanceRepository.findAll();
        List<Maintenance> dueMaintenances = maintenances.stream()
                .filter(this::isDueForMaintenance)
                .collect(Collectors.toList());
        dueMaintenances.forEach(this::sendReminder);
    }

    private boolean isDueForMaintenance(Maintenance maintenance) {
        LocalDate nextDueDate = calculateNextDueDate(maintenance.getLastMaintenanceDate(), maintenance.getFrequency());
        return nextDueDate.isBefore(LocalDate.now()) || nextDueDate.isEqual(LocalDate.now());
    }

    private LocalDate calculateNextDueDate(LocalDate lastMaintenanceDate, Frequency frequency) {
        switch (frequency) {
            case DAILY:
                return lastMaintenanceDate.plusDays(1);
            case WEEKLY:
                return lastMaintenanceDate.plusWeeks(1);
            case MONTHLY:
                return lastMaintenanceDate.plusMonths(1);
            case YEARLY:
                return lastMaintenanceDate.plusYears(1);
            default:
                throw new IllegalArgumentException("Unknown frequency: " + frequency);
        }
    }

    private void sendReminder(Maintenance maintenance) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo("admin@example.com"); // Set the admin's email address
        message.setSubject("Maintenance Reminder: " + maintenance.getTaskName());
        message.setText("The following maintenance task is due: " + maintenance.getTaskName() +
                "\nDescription: " + maintenance.getDescription() +
                "\nCar: " + maintenance.getCar().getMatriculate());
        emailSender.send(message);
    }

    public List<Maintenance> getDueMaintenanceTasks() {
        List<Maintenance> maintenances = maintenanceRepository.findAll();
        return maintenances.stream()
                .filter(this::isDueForMaintenance)
                .collect(Collectors.toList());
    }

    public void addInvoice(Long maintenanceId, List<MultipartFile> files) throws IOException {

        Maintenance maintenance = this.maintenanceRepository.findById(maintenanceId)
                .orElseThrow(() -> new EntityNotFoundException("Maintnance not found"));;

        List<String> details = maintenance.getInvoices() != null ? new ArrayList<>(maintenance.getInvoices()) : new ArrayList<>();
        for (MultipartFile file : files) {
            String detailImageUrl = s3Service.uploadFile(file, bucketName);
            details.add(detailImageUrl);
        }
    }
}
