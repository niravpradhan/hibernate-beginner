package me.niravpradhan.hibernate_beginner.dtos;

import me.niravpradhan.hibernate_beginner.entities.AuthorStatus;

import java.io.Serializable;
import java.time.LocalDate;

public class AuthorDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String firstName;

    private String lastName;

    private LocalDate dateOfBirth;

    private AuthorStatus status;

    private int version;

    private Long numOfBooks;

    public AuthorDTO(Long id, String firstName, String lastName, LocalDate dateOfBirth, String status, int version, Long numOfBooks) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.status = AuthorStatus.valueOf(status);
        this.version = version;
        this.numOfBooks = numOfBooks;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public AuthorStatus getStatus() {
        return status;
    }

    public void setStatus(AuthorStatus status) {
        this.status = status;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public Long getNumOfBooks() {
        return numOfBooks;
    }

    public void setNumOfBooks(Long numOfBooks) {
        this.numOfBooks = numOfBooks;
    }
}
