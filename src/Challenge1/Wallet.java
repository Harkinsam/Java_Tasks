package Challenge1;

public class Wallet {
    private double balance;

    public Wallet(double balance) {
        this.balance = balance;
    }

    public double balance() {
        return balance;
    }

    public void setBalance(double balance) {
        if(balance >0){
            this.balance = balance;
        }else {
            System.out.println("Balance too low");
        }
    }
    public void addFunds(double amount) {
        if (amount > 0) {
            this.balance += amount;
        } else {
            System.out.println("Invalid amount.");
        }
    }

    public synchronized void deductFunds(double amount) {
        if (amount > 0 && amount <= balance) {
            this.balance -= amount;
        } else {
            System.out.println("Invalid amount or insufficient balance.");
        }
    }
}
