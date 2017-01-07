package models.accounts;

/**
 * Created by Man on 07.01.2017.
 */
public class SavingAccount implements Account {
    private double balance;

    public  SavingAccount(double balance) {
        if (balance>=0) {
            this.balance = balance;
        }
        else {
            System.out.println("You cant do it, because your balance will be negative");
        }
    }
    public AccountType getType(){
        if (this.getClass().getSimpleName().equalsIgnoreCase("SavingAccount")){
            return AccountType.SAVING;
        }
        if (this.getClass().getSimpleName().equalsIgnoreCase("CheckingAccount")){
            return AccountType.CHECKING;
        }
        return null;
    }
    public void setBalance(double balance) {
        this.balance = balance;
    }

    public double getBalance() {
        return balance;
    }

    @Override
    public String toString(){
        return  "Account type: Saving Accounts"+", balance: "+this.getBalance()+"; ";
    }
}

