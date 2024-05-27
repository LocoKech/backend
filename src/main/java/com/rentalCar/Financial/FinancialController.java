package com.rentalCar.Financial;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/financial")
public class FinancialController {
    private final FinancialService financialService;

    public FinancialController(FinancialService financialService) {
        this.financialService = financialService;
    }

    @GetMapping("/summary")
    public FinancialSummary getFinancialSummary(@RequestParam TimeFrame timeFrame) {
        return financialService.getFinancialSummary(timeFrame);
    }

    @GetMapping("/income")
    public double getTotalIncome(@RequestParam TimeFrame timeFrame) {
        return financialService.getTotalIncome(timeFrame);
    }

    @GetMapping("/expenses")
    public double getTotalExpenses(@RequestParam TimeFrame timeFrame) {
        return financialService.getTotalExpenses(timeFrame);
    }

    @GetMapping("/net-income")
    public double getNetIncome(@RequestParam TimeFrame timeFrame) {
        return financialService.getNetIncome(timeFrame);
    }

    @GetMapping("/data")
    public Map<String, List<String>> getFinancialData(@RequestParam TimeFrame timeFrame) {
        return financialService.getFinancialData(timeFrame);
    }
}

