package project.group8.oeconomus.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class User {
    @Id 
    private String userID;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private List<Transaction> expenses;
    private List<Transaction> incomes;
    private Budget budgetGoal;
    private Budget savingsGoal;

    public User() {
        Random random = new Random();
        this.userID = UUID.randomUUID().toString();
        this.expenses = new ArrayList<>();
        this.incomes = new ArrayList<>();
        for (int i = 0; i <= 30; i++) {
            this.expenses.add(new Transaction(LocalDate.now().minusDays(i), Math.round((0.01 + (1500.00 - 0.01) * random.nextDouble())* 100.0) / 100.0, LocalDate.now().minusDays(i).toString(), i%7 + ""));
        }
        for (int i = 0; i <= 12; i++) {
            this.incomes.add(new Transaction(LocalDate.now().minusDays(30*i), 25420.07, LocalDate.now().minusDays(30*i).toString(), i%7 + ""));
        }
        this.budgetGoal = new Budget();
        this.savingsGoal = new Budget();
    };
    
    public User(String email, String password) {
        this.userID = UUID.randomUUID().toString();
        this.email = email;
        this.password = password;
    }

    public Budget getBudgetGoal() {
        return budgetGoal;
    }

    public void setBudgetGoal(Budget budgetGoal) {
        this.budgetGoal = budgetGoal;
    }

    public Budget getSavingsGoal() {
        return savingsGoal;
    }

    public void setSavingsGoal(Budget savingsGoal) {
        this.savingsGoal = savingsGoal;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void setExpenses(List<Transaction> expenses) {
        this.expenses = expenses;
    }
    public void setIncomes(List<Transaction> incomes) {
        this.incomes = incomes;
    }
    public String getUserID() {
        return userID;
    }
    public String getFirstName() {
        return firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public String getEmail() {
        return email;
    }
    public String getPassword() {
        return password;
    }
    public List<Transaction> getExpenses() {
        return expenses;
    }
    public List<Transaction> getIncomes() {
        return incomes;
    }
    
    @Override
    public String toString() {
        return "User [userID=" + userID + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email
                + ", password=" + password + ", expenses=" + expenses + ", incomes=" + incomes + "]";
    }
}
