package com.rentalCar.Financial;

public class FinancialSummary {
    private double totalIncome;
    private double totalExpenses;
    private double netIncome;

    public FinancialSummary(double totalIncome, double totalExpenses, double netIncome) {
        this.totalIncome = totalIncome;
        this.totalExpenses = totalExpenses;
        this.netIncome = netIncome;
    }

    public double getTotalIncome() {
        return totalIncome;
    }

    public void setTotalIncome(double totalIncome) {
        this.totalIncome = totalIncome;
    }

    public double getTotalExpenses() {
        return totalExpenses;
    }

    public void setTotalExpenses(double totalExpenses) {
        this.totalExpenses = totalExpenses;
    }

    public double getNetIncome() {
        return netIncome;
    }

    public void setNetIncome(double netIncome) {
        this.netIncome = netIncome;
    }
}
