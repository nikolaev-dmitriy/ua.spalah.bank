package ua.spalah.bank.models;

import org.hibernate.annotations.GenericGenerator;
import ua.spalah.bank.models.accounts.Account;
import ua.spalah.bank.models.type.Gender;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by Man on 07.01.2017.
 */
@Entity
@Table(name="CLIENTS")
public class Client {
    @Id
    @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment",strategy = "increment")
    private long id;
    @Column (name="name")
    private String name;
    @Enumerated (EnumType.STRING)
    private Gender gender;
    @Column (name="email")
    private String email;
    @Column (name="telephone")
    private String telephone;
    @Column (name="city")
    private String city;
    @OneToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(name="active_account_id",foreignKey = @ForeignKey(name="FK_ACTIVE_ACCOUNT"))
    private Account activeAccount;
    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id",foreignKey = @ForeignKey(name="FK_CLIENT_ACCOUNTS"))
    private List<Account> accounts;

    public Client() {
        accounts = new ArrayList<Account>();
    }

    public Client(long id, String name, Gender gender, String email, String telephone, String city) {
        accounts = new ArrayList<Account>();
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.email = email;
        this.telephone = telephone;
        this.city = city;
    }

    public Client(String name, Gender gender, String email, String telephone, String city) {
        accounts = new ArrayList<Account>();
        this.name = name;
        this.gender = gender;
        this.email = email;
        this.telephone = telephone;
        this.city = city;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
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
