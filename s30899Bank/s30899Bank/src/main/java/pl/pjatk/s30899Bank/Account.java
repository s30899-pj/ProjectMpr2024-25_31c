package pl.pjatk.s30899Bank;

public class Account {
    private int id;
    private String name;
    private String surname;
    private double balance = 0.0;
    private Status lastTransactionStatus = Status.NONE;

    private Account (){}

    public Account(String name, String surname, double balance) {
        this.name = name;
        this.surname = surname;
        this.balance = balance;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public double getBalance() {
        return balance;
    }

    public Status getLastTransactionStatus() {
        return lastTransactionStatus;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public void setLastTransactionStatus(Status lastTransactionStatus) {
        this.lastTransactionStatus = lastTransactionStatus;
    }
}
