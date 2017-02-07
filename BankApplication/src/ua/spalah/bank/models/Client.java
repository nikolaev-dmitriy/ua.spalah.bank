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
    private long id=0;
    private String name;
    private Gender gender;
    private String email;
    private String telephone;
    private String city;
    private Account activeAccount;
    private List<Account> accounts = new ArrayList<>();

    public Client( String name, Gender gender, String email, String telephone, String city) {
        this.id = this.id+1;
        this.name = name;
        this.gender = gender;
        this.email = email;
        this.telephone = telephone;
        this.city = city;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getEmail() {

        return email;
    }

    public String getTelephone() {
        return telephone;
    }

    public String getCity() {
        return city;
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
                ", gender=" + gender +
                ", e-mail='" + email + '\'' +
                ", telephone='" + telephone + '\'' +
                ", city='" + city + '\'' +
                ", \nactiveAccount=" + activeAccount +
                ", \naccounts=" + accounts +
                '}';
    }

    @Override
    public boolean equals(Object otherObject) {
        if (this == otherObject) return true;
        if (otherObject == null) return false;
        if (getClass() != otherObject.getClass()) return false;
        Client other = (Client) otherObject;
        return Objects.equals(name, other.name) && Objects.equals(gender, other.gender) && Objects.equals(email, other.email)&& Objects.equals(telephone, other.telephone)&& Objects.equals(city, other.city);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, gender, city, email, telephone);
    }
}
