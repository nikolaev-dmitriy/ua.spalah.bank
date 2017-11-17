package ua.spalah.bank.models.accounts;

import org.hibernate.annotations.GenericGenerator;
import ua.spalah.bank.models.type.AccountType;

import javax.persistence.*;

/**
 * Created by Man on 12.03.2017.
 */
@Entity
@Table (name="ACCOUNTS")
@Inheritance (strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name= "ACCOUNT_TYPE")
public abstract class Account {
    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name="increment", strategy="increment")
    @Column(name="id")
    private long id;
    @Enumerated(EnumType.STRING)
    private AccountType type;
    @Column(name="balance")
    private double balance;



    protected Account(double balance,AccountType accountType){
        this.balance=balance;
        this.type = accountType;
    }

    protected Account(AccountType accountType) {
        this.type = accountType;
    }

    protected Account() {
    }



    public AccountType getAccountType() {
        return type;
    }



    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }




    public long getId() {
        return id;
    }


    public void setId(long id) {
        this.id = id;
    }

    public void setAccountType(AccountType accountType) {
        this.type = accountType;
    }
}
