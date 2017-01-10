package models.accounts;

/**
 * Created by Man on 07.01.2017.
 */
public class SavingAccount implements Account {
    protected double balance;
    private AccountType accountType = AccountType.SAVING;

    public SavingAccount(double balance) {
        setBalance(balance);

    }

    public AccountType getType() {
        if (this.getClass().getSimpleName().equalsIgnoreCase("SavingAccount")) {
            return AccountType.SAVING;
        }
        if (this.getClass().getSimpleName().equalsIgnoreCase("CheckingAccount")) {
            return AccountType.CHECKING;
        }
        throw new NullPointerException("AccountType Error");
    }

    public void setBalance(double balance) {
        if (balance >= 0) {
            this.balance = balance;
        } else {
            throw new IllegalArgumentException("Balance can't be negative");
        }
    }

    public double getBalance() {
        return this.balance;
    }

//    @Override
//    public String toString(){
//        return  "Account type: Saving Accounts"+", balance: "+this.getBalance()+"; ";
//    }

    @Override
    public String toString() {
        return "\nSavingAccount{" +
                "balance=" + balance +
                ", accountType=" + this.getType() +
                "}";
    }
}

