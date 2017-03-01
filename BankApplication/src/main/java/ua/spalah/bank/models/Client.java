package main.java.ua.spalah.bank.models;

import main.java.ua.spalah.bank.models.accounts.Account;
import main.java.ua.spalah.bank.models.type.Gender;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by Man on 07.01.2017.
 */
public class Client {
    private long id;
    private String name;
    private Gender gender;
    private String email;
    private String telephone;
    private String city;
    private Account activeAccount;
    private List<Account> accounts = new ArrayList<Account>();


    public Client(long id, String name, Gender gender, String email, String telephone, String city) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.email = email;
        this.telephone = telephone;
        this.city = city;
    }

    public Client(String name, Gender gender, String email, String telephone, String city) {
        this.name = name;
        this.gender = gender;
        this.email = email;
        this.telephone = telephone;
        this.city = city;
    }


    public List<Account> getAccounts() {
        return accounts;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
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

    @Override
    public String toString() {
        return "\nClient{" +
                "name='" + name + '\'' +
                ", gender=" + gender +
                ", e-mail='" + email + '\'' +
                ", telephone='" + telephone + '\'' +
                ", city='" + city + '\'' +
                ", \nactiveAccount=" + activeAccount +
                '}';
    }

    @Override
    public boolean equals(Object otherObject) {
        if (this == otherObject) return true;
        if (otherObject == null) return false;
        if (getClass() != otherObject.getClass()) return false;
        Client other = (Client) otherObject;
        return Objects.equals(name, other.name) && Objects.equals(gender, other.gender) && Objects.equals(email, other.email) && Objects.equals(telephone, other.telephone) && Objects.equals(city, other.city);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, gender, city, email, telephone);
    }
}
