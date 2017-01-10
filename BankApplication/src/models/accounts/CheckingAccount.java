package models.accounts;

/**
 * Created by Man on 07.01.2017.
 */
public class CheckingAccount extends AbstractAccount {
    private double overdraft;

    public double getOverdraft() {
        return overdraft;
    }

    public CheckingAccount(double balance, double overdraft) {
        super(balance);
        setOverdraft(overdraft);
        setAccountType(AccountType.CHECKING);
    }

    public void setOverdraft(double overdraft) {
        this.overdraft = overdraft;
    }

    @Override
    public String toString() {
        return "\nCheckingAccount{" +
                "balance=" + this.getBalance() +
                ", overdraft=" + this.getOverdraft() +
                ", accountType=" + this.getAccountType() +
                "}";
    }
}
