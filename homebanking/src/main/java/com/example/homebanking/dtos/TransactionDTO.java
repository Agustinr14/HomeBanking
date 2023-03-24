package com.example.homebanking.dtos;

import com.example.homebanking.models.Transaction;
import com.example.homebanking.models.TransactionType;

import java.time.LocalDateTime;

public class TransactionDTO {
    private Long id;
    private TransactionType type;
    private double amount;
    private String description;
    private LocalDateTime date;


    public TransactionDTO(long id, TransactionType type, double amount, String description, LocalDateTime date) {
        this.id = id;
        this.type = type;
        this.amount = amount;
        this.description = description;
        this.date = date;
    }
    public  TransactionDTO(Transaction transaction){
        this.id = transaction.getId();
        this.type = transaction.getType();
        this.amount = transaction.getAmount();
        this.description = transaction.getDescription();
        this.date = transaction.getDate();

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getDescription() {
        String newDescription = description;
        switch (this.type){
            case CREDIT:
                        newDescription += "- CREDITO";
                        break;
            case DEBIT:
                        newDescription += "- DEBITO";
                        break;
            default:
                    newDescription += "- ERROR";
                    break;
        }
        return newDescription;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }
}
