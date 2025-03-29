package model;

import java.io.Serializable;

public class User implements Serializable {
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String address;
    private String dateOfBirth;
    private String fullName;

    // 기본 생성자
    public User() {}

    // 모든 필드를 받는 생성자 (필요 시)
    public User(String email, String password, String firstName, String lastName, String address, String dateOfBirth) {
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.dateOfBirth = dateOfBirth;
    }

    // Getter / Setter
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public void setName(String firstName, String lastName){
        this.fullName = firstName + " " + lastName;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
}
