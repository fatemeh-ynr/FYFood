package data.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDate;

@Entity
public class Customer {
    @Id
    private String mobileNumber;
    private String name;
    private String postalCode;
    private LocalDate registrationDate;

    public Customer(String mobileNumber) {
        setMobileNumber(mobileNumber);
    }

    public Customer() {
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public LocalDate getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDate registrationDate) {
        this.registrationDate = registrationDate;
    }

    //----------------------------------------
    public boolean isNew() {
        if (name == null)
            return true;
        else
            return false;
    }

    public String toString() {
        return "username:" + name + ", mobile number:" + mobileNumber;
    }
}
