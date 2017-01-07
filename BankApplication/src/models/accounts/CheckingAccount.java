package models.accounts;

/**
 * Created by Man on 07.01.2017.
 */
public class CheckingAccount extends SavingAccount {
    private double overdraft;

    public double getOverdraft() {
        return overdraft;
    }
    public CheckingAccount(double balance, double overdraft) {
        super(balance);
        this.overdraft = overdraft;
    }
    @Override
    public String  toString(){
        return "Account type: Checking Accounts"+", balance: "+this.getBalance()+", overdraft: "+this.overdraft+"; ";
    }
}
