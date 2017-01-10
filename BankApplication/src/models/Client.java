package models;

import models.accounts.Account;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Created by Man on 07.01.2017.
 */
public class Client {
    private String name;
    private String gender;
    private Account activeAccount;
    private ArrayList <Account> accounts;

    public void setActiveAccount(Account activeAccount) {
        this.activeAccount = activeAccount;
    }

    public Account getActiveAccount() {
        return activeAccount;
    }

    public Client(String name, String gender) {
        this.name = name;
        this.gender = gender;
        accounts = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public String getGender() {
        return gender;
    }


    public ArrayList<Account> getAccounts() {
        return accounts;
    }
    @Override
    public String toString() {
        return "\n  ____________________________________________________________________________________________\nClient{" +
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
