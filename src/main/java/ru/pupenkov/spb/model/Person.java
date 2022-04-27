package ru.pupenkov.spb.model;


import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class Person {

    @Size(min=10, max=30, message = "Mail size should be min=10, max=30")
    @NotEmpty(message = "Mail should not be empty")
    @Email(message = "Mail should be valid")
    private String mail;
    @Size(min=6, max=20, message = "Password size should be min=6, max=20")
    @NotEmpty(message = "Password should not be empty")
    private String password;
    private int id;
    private String name;

    public Person() {
   }

    public Person(int id, String name, String mail, String password) {
        this.id = id;
        this.name = name;
        this.mail = mail;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
