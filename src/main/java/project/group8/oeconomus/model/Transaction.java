package project.group8.oeconomus.model;

import java.time.LocalDate;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Transaction {
    @Id 
    private String transactionID;
    private String transactionName;
    private String transactionCategory;
    private LocalDate transactionDate;
    private Double transactionAmount;



    public Transaction(LocalDate transactionDate, Double transactionAmount, String transactionName, String transactionCategory) {
        this.transactionID = UUID.randomUUID().toString();
        this.transactionDate = transactionDate;
        this.transactionAmount = transactionAmount;
        this.transactionName = transactionName;
        this.transactionCategory = transactionCategory;
    }
    public Transaction() {}

    public String getTransactionID() {
        return this.transactionID;
    }

    public void setTransactionID(String transactionID) {
        this.transactionID = transactionID;
    }

    public Double getTransactionAmount() {
        return transactionAmount;
    }
    public void setTransactionAmount(Double transactionAmount) {
        this.transactionAmount = transactionAmount;
    }
    public String getTransactionName() {
        return transactionName;
    }
    public void setTransactionName(String transactionName) {
        this.transactionName = transactionName;
    }
    public String getTransactionCategory() {
        return transactionCategory;
    }
    public void setTransactionCategory(String transactionCategory) {
        this.transactionCategory = transactionCategory;
    }
    public LocalDate getTransactionDate() {
        return transactionDate;
    }
    public void setTransactionDate(LocalDate transactionDate) {
        this.transactionDate = transactionDate;
    }
    @Override
    public String toString() {
        return "Transaction [transactionName=" + transactionName + ", transactionCategory=" + transactionCategory
                + ", transactionDate=" + transactionDate + ", transactionAmount=" + transactionAmount + "]";
    }    
}
