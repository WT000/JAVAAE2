package org.solent.com504.oodd.cart.model.dto;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;
import javax.persistence.Embedded;
import org.solent.com504.oodd.password.PasswordUtils;
import org.solent.com504.oodd.bank.model.dto.CreditCard;

@Entity
public class User {

    private Long id;

    private String firstName = "";

    private String secondName = "";

    private String username = "";

    private String password = "";

    private String hashedPassword = "";

    private Address address;

    // Optionally store parts of their CreditCard (not cvv), only used for
    // SessionUser
    private CreditCard card;

    private UserRole userRole;

    private Boolean enabled = true;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Embedded
    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    @Embedded
    public CreditCard getCard() {
        return card;
    }

    public void setCard(CreditCard card) {
        this.card = card;
    }

    // passwords not saved in database only passwordhash is saved
    @Transient
    public String getPassword() {
        return password;
    }

    // generate hashed password to save in database
    public void setPassword(String password) {
        this.password = password;
        setHashedPassword(PasswordUtils.hashPassword(password));
    }

    public boolean isValidPassword(String checkPassword) {
        if (checkPassword == null || getHashedPassword() == null) {
            return false;
        }
        return PasswordUtils.checkPassword(checkPassword, getHashedPassword());
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }

    // no password or hashed password
    @Override
    public String toString() {
        return "User{" + "id=" + id + ", firstName=" + firstName + ", secondName=" + secondName + ", username=" + username + ", password=NOT SHOWN, address=" + address + ", userRole=" + userRole + '}';
    }

}
