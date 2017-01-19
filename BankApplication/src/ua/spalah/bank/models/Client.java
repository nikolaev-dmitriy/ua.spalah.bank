package ua.spalah.bank.models;

import ua.spalah.bank.models.accounts.Account;
import ua.spalah.bank.models.type.Gender;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by Man on 07.01.2017.
 */
public class Client {
    private String name;
    private Gender gender;
    private Account activeAccount;
    private List<Account> accounts = new ArrayList<>();

    public Client(String name, Gender gender) {
        this.name = name;
        this.gender = gender;
    }

    public void setActiveAccount(Account activeAccount) {
        this.activeAccount = activeAccount;
    }

    public Account getActiveAccount() {
        return activeAccount;
    }

    public String getName() {
        return name;
    }

    public Gender getGender() {
        return gender;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    @Override
    public String toString() {
        return "\nClient{" +
                "name='" + name + '\'' +
                ", gender='" + gender + '\'' +
                ",\nactiveAccount:" + activeAccount +
                "\naccounts=" + accounts +
                "}";
    }

    @Override
    public boolean equals(Object otherObject) {
        if (this == otherObject) return true;
        if (otherObject == null) return false;
        if (getClass() != otherObject.getClass()) return false;
        Client other = (Client) otherObject;
        return Objects.equals(name, other.name) && Objects.equals(gender, other.gender);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, gender);
    }
}
