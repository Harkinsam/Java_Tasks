package Challenge1;

public class User {
    private Wallet wallet;

    public User(Wallet wallet) {
        this.wallet = wallet;
    }



    public double getWalletBalance() {
        return wallet.balance();
    }

    public void addFundsToWallet(double amount) {
        wallet.addFunds(amount);
    }

    public void deductFundsFromWallet(double amount) {
        wallet.deductFunds(amount);
    }
}

