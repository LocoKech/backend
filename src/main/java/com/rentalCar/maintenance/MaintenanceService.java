package com.rentalCar.maintenance;

import com.rentalCar.booking.EmailService;
import com.rentalCar.car.Car;
import com.rentalCar.car.CarRepository;
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
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MaintenanceService {

    private final MaintenanceRepository maintenanceRepository;

    @Value("${application.bucket.name}")
    private String bucketName;

    @Autowired
    private EmailService emailService;

    @Autowired
    private S3Service s3Service;

    @Autowired
    private CarRepository carRepository;

    @Value("${admin.email}")
    private String adminEmail;
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



    @Scheduled(cron = "0 26 11 * * ?")
    public void checkMaintenance() {
        List<Maintenance> maintenances = maintenanceRepository.findAll();
        List<Maintenance> dueMaintenances = maintenances.stream()
                .filter(this::isFrequent)
                .filter(this::isDueForMaintenance)
                .collect(Collectors.toList());
    }

    private boolean isFrequent(Maintenance maintenance){
        return maintenance.getFrequency() != Frequency.Once;
    }

    private boolean isDueForMaintenance(Maintenance maintenance) {
        LocalDate currentDate = LocalDate.now();
        LocalDate nextDueDate = calculateNextDueDate(maintenance.getLastMaintenanceDate(), maintenance.getFrequency());
        LocalDate warningDate = currentDate.plusWeeks(1);

        if (nextDueDate.isBefore(currentDate)) {
            sendMaintenanceOverdueReminder(maintenance);
            return true;
        }

        if (nextDueDate.isBefore(warningDate) || nextDueDate.isEqual(warningDate)) {
            sendMaintenanceUpcomingReminder(maintenance);
            return true;
        }

        return false;
    }

    private void sendMaintenanceOverdueReminder(Maintenance maintenance) {
        String subject = "Maintenance Overdue: " + maintenance.getTaskName();
        String text = "The maintenance task '" + maintenance.getTaskName() +
                "' for car " + maintenance.getCar().getMatriculate() +
                " was due on " + maintenance.getLastMaintenanceDate().plus(frequencyToPeriod(maintenance.getFrequency())) +
                " and is now overdue. Please take action.";
        emailService.sendSimpleMessage("admin@example.com", subject, text);
    }

    private void sendMaintenanceUpcomingReminder(Maintenance maintenance) {
        String subject = "Upcoming Maintenance: " + maintenance.getTaskName();
        String text = "The maintenance task '" + maintenance.getTaskName() +
                "' for car " + maintenance.getCar().getMatriculate() +
                " is due on " + maintenance.getLastMaintenanceDate().plus(frequencyToPeriod(maintenance.getFrequency())) +
                ". Please prepare to complete this task.";
        emailService.sendSimpleMessage("admin@example.com", subject, text);
    }

    private Period frequencyToPeriod(Frequency frequency) {
        switch (frequency) {
            case DAILY:
                return Period.ofDays(1);
            case WEEKLY:
                return Period.ofWeeks(1);
            case MONTHLY:
                return Period.ofMonths(1);
            case YEARLY:
                return Period.ofYears(1);
            default:
                throw new IllegalArgumentException("Unknown frequency: " + frequency);
        }
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

    public Maintenance save(MaintenanceRequest request) {
        Car car = carRepository.findById(request.getCarId()).orElseThrow(() -> new RuntimeException("Car not found"));

        Maintenance maintenance = new Maintenance();
        maintenance.setTaskName(request.getTaskName());
        maintenance.setDescription(request.getDescription());
        maintenance.setFrequency(request.getFrequency());
        maintenance.setLastMaintenanceDate(request.getLastMaintenanceDate());
        maintenance.setCar(car);
        maintenance.setCost(request.getCost());

        return maintenanceRepository.save(maintenance);
    }
}
