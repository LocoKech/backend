package com.rentalCar.Financial;

import com.rentalCar.booking.Booking;
import com.rentalCar.booking.BookingRepository;
import com.rentalCar.maintenance.Maintenance;
import com.rentalCar.maintenance.MaintenanceRepository;
import com.rentalCar.warranty.Warranty;
import com.rentalCar.warranty.WarrantyRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class FinancialService {
    private final BookingRepository bookingRepository;
    private final MaintenanceRepository maintenanceRepository;
    private final WarrantyRepository warrantyRepository;

    public FinancialService(BookingRepository bookingRepository,
                            MaintenanceRepository maintenanceRepository,
                            WarrantyRepository warrantyRepository) {
        this.bookingRepository = bookingRepository;
        this.maintenanceRepository = maintenanceRepository;
        this.warrantyRepository = warrantyRepository;
    }

    public double getTotalIncome(TimeFrame timeFrame) {
        LocalDate startDate = calculateStartDate(timeFrame);
        return bookingRepository.findAll().stream()
                .filter(booking -> !booking.getStartDate().isBefore(startDate))
                .mapToDouble(Booking::getTotalPrice)
                .sum();
    }

    public double getTotalExpenses(TimeFrame timeFrame) {
        LocalDate startDate = calculateStartDate(timeFrame);
        double maintenanceCost = maintenanceRepository.findAll().stream()
                .filter(maintenance -> !maintenance.getLastMaintenanceDate().isBefore(startDate))
                .mapToDouble(Maintenance::getCost)
                .sum();
        double warrantyCost = warrantyRepository.findAll().stream()
                .filter(warranty -> !warranty.getStartDate().isBefore(startDate))
                .mapToDouble(Warranty::getCost)
                .sum();
        return maintenanceCost + warrantyCost;
    }

    public double getNetIncome(TimeFrame timeFrame) {
        return getTotalIncome(timeFrame) - getTotalExpenses(timeFrame);
    }

    public FinancialSummary getFinancialSummary(TimeFrame timeFrame) {
        return new FinancialSummary(getTotalIncome(timeFrame), getTotalExpenses(timeFrame), getNetIncome(timeFrame));
    }

    private LocalDate calculateStartDate(TimeFrame timeFrame) {
        LocalDate now = LocalDate.now();
        switch (timeFrame) {
            case DAILY:
                return now.minusDays(1);
            case MONTHLY:
                return now.minusMonths(1);
            case YEARLY:
                return now.minusYears(1);
            default:
                throw new IllegalArgumentException("Unknown time frame: " + timeFrame);
        }
    }


    public Map<String, List<String>> getFinancialData(TimeFrame timeFrame) {
        List<String> labels = getLabelsForTimeFrame(timeFrame);
        List<Double> incomeData = getIncomeData(timeFrame, labels);
        List<Double> expenseData = getExpenseData(timeFrame, labels);

        Map<String, List<String>> data = new HashMap<>();
        data.put("labels",labels);
        data.put("incomeData", incomeData.stream().map(incom -> incom.toString()).toList());
        data.put("expenseData", expenseData.stream().map(incom -> incom.toString()).toList());
        System.out.println(data);
        System.out.println(expenseData);
        return data;
    }

    private List<String> getLabelsForTimeFrame(TimeFrame timeFrame) {
        LocalDate now = LocalDate.now();
        List<String> labels = new ArrayList<>();
        switch (timeFrame) {
            case DAILY:
                for (int i = 6; i >= 0; i--) {
                    labels.add(now.minusDays(i).toString());
                }
                break;
            case MONTHLY:
                for (int i = 12; i >= 0; i--) {
                    labels.add(now.minusMonths(i).getMonth().toString());
                }
                break;
            case YEARLY:
                for (int i = 5; i >= 0; i--) {
                    labels.add(String.valueOf(now.getYear() - i));
                }
                break;
        }
        return labels;
    }

    private List<Double> getIncomeData(TimeFrame timeFrame, List<String> labels) {
        LocalDate now = LocalDate.now();
        DateTimeFormatter formatter;
        Map<String, Double> incomeMap;

        switch (timeFrame) {
            case DAILY:
                formatter = DateTimeFormatter.ISO_LOCAL_DATE;
                incomeMap = bookingRepository.findAll().stream()
                        .filter(booking -> !booking.getEndDate().isBefore(now.minusDays(6)))
                        .collect(Collectors.groupingBy(
                                booking -> booking.getEndDate().format(formatter),
                                Collectors.summingDouble(Booking::getTotalPrice)
                        ));
                break;
            case MONTHLY:
                formatter = DateTimeFormatter.ofPattern("MMMM");
                incomeMap = bookingRepository.findAll().stream()
                        .filter(booking -> !booking.getEndDate().isBefore(now.minusMonths(6)))
                        .collect(Collectors.groupingBy(
                                booking -> booking.getEndDate().format(formatter),
                                Collectors.summingDouble(Booking::getTotalPrice)
                        ));
                break;
            case YEARLY:
                formatter = DateTimeFormatter.ofPattern("yyyy");
                incomeMap = bookingRepository.findAll().stream()
                        .filter(booking -> !booking.getEndDate().isBefore(now.minusYears(6)))
                        .collect(Collectors.groupingBy(
                                booking -> booking.getEndDate().format(formatter),
                                Collectors.summingDouble(Booking::getTotalPrice)
                        ));
                break;
            default:
                throw new IllegalArgumentException("Unknown time frame: " + timeFrame);
        }


        for (Map.Entry<String, Double> entry : incomeMap.entrySet()) {
            String uppercaseKey = entry.getKey().toUpperCase();
            Double value = entry.getValue();
            incomeMap.put(uppercaseKey, value);
        }

        return labels.stream()
                .map(label -> incomeMap.getOrDefault(label, 0.0))
                .collect(Collectors.toList());
    }

    private List<Double> getExpenseData(TimeFrame timeFrame, List<String> labels) {
        LocalDate now = LocalDate.now();
        DateTimeFormatter formatter;
        Map<String, Double> maintenanceMap;
        Map<String, Double> warrantyMap;

        switch (timeFrame) {
            case DAILY:
                formatter = DateTimeFormatter.ISO_LOCAL_DATE;
                maintenanceMap = maintenanceRepository.findAll().stream()
                        .filter(maintenance -> !maintenance.getLastMaintenanceDate().isBefore(now.minusDays(6)))
                        .collect(Collectors.groupingBy(
                                maintenance -> maintenance.getLastMaintenanceDate().format(formatter),
                                Collectors.summingDouble(Maintenance::getCost)
                        ));
                warrantyMap = warrantyRepository.findAll().stream()
                        .filter(warranty -> !warranty.getStartDate().isBefore(now.minusDays(6)))
                        .collect(Collectors.groupingBy(
                                warranty -> warranty.getStartDate().format(formatter),
                                Collectors.summingDouble(Warranty::getCost)
                        ));
                break;
            case MONTHLY:
                formatter = DateTimeFormatter.ofPattern("MMMM");
                maintenanceMap = maintenanceRepository.findAll().stream()
                        .filter(maintenance -> !maintenance.getLastMaintenanceDate().isBefore(now.minusMonths(6)))
                        .collect(Collectors.groupingBy(
                                maintenance -> maintenance.getLastMaintenanceDate().format(formatter),
                                Collectors.summingDouble(Maintenance::getCost)
                        ));
                warrantyMap = warrantyRepository.findAll().stream()
                        .filter(warranty -> !warranty.getStartDate().isBefore(now.minusMonths(6)))
                        .collect(Collectors.groupingBy(
                                warranty -> warranty.getStartDate().format(formatter),
                                Collectors.summingDouble(Warranty::getCost)
                        ));
                break;
            case YEARLY:
                formatter = DateTimeFormatter.ofPattern("yyyy");
                maintenanceMap = maintenanceRepository.findAll().stream()
                        .filter(maintenance -> !maintenance.getLastMaintenanceDate().isBefore(now.minusYears(6)))
                        .collect(Collectors.groupingBy(
                                maintenance -> maintenance.getLastMaintenanceDate().format(formatter),
                                Collectors.summingDouble(Maintenance::getCost)
                        ));
                warrantyMap = warrantyRepository.findAll().stream()
                        .filter(warranty -> !warranty.getStartDate().isBefore(now.minusYears(6)))
                        .collect(Collectors.groupingBy(
                                warranty -> warranty.getStartDate().format(formatter),
                                Collectors.summingDouble(Warranty::getCost)
                        ));
                break;
            default:
                throw new IllegalArgumentException("Unknown time frame: " + timeFrame);
        }
        maintenanceMap.forEach((key,maintnance) -> {
            System.out.println(maintnance);
            System.out.println(key);
        });
        System.out.println(maintenanceMap);

        Map<String, Double> combinedMap = maintenanceMap;
        warrantyMap.forEach((key, value) -> combinedMap.merge(key, value, Double::sum));
        System.out.println(maintenanceMap.get("MAY"));

        for (Map.Entry<String, Double> entry : combinedMap.entrySet()) {
            String uppercaseKey = entry.getKey().toUpperCase();
            Double value = entry.getValue();
            combinedMap.put(uppercaseKey, value);
        }
        return labels.stream()
                .map(label -> combinedMap.getOrDefault(label, 0.0))
                .collect(Collectors.toList());
    }

}

