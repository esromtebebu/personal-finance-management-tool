package project.group8.oeconomus.model;

import java.time.LocalDate;;

public class Budget {
    private double budgetAmount;
    private LocalDate startDate;
    private LocalDate endDate;

    public Budget() {
        this.budgetAmount = 0;
        this.startDate = LocalDate.now().withDayOfMonth(1);
        this.endDate = LocalDate.now();
    }

    public double getBudgetAmount() {
        return budgetAmount;
    }

    public void setBudgetAmount(double budgetAmount) {
        this.budgetAmount = budgetAmount;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    
    
}
