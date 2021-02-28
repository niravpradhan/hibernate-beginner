package me.niravpradhan.hibernate_beginner.entities;

import me.niravpradhan.hibernate_beginner.dtos.AuthorDTO;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

@Entity
@NamedNativeQuery(name = "Author.selectAll", query = "select a.id, a.first_name, a.last_name, a.date_of_birth, a.version, a.status from author a")
@NamedNativeQuery(name = "Author.selectAllWithBooksCount", query = "select a.id, a.first_name, a.last_name, a.date_of_birth, " +
        "a.status, a.version, (ba.fk_author) as num_of_books from author a " +
        "left join book_author ba on a.id = ba.fk_author")
@SqlResultSetMapping(
        name = "AuthorDTOMapping",
        classes = @ConstructorResult(
                targetClass = AuthorDTO.class,
                columns = {
                        @ColumnResult(name = "id", type = Long.class),
                        @ColumnResult(name = "first_name", type = String.class),
                        @ColumnResult(name = "last_name", type = String.class),
                        @ColumnResult(name = "date_of_birth", type = LocalDate.class),
                        @ColumnResult(name = "status", type = String.class),
                        @ColumnResult(name = "version"),
                        @ColumnResult(name = "num_of_books", type = Long.class),
                }
        )
)
public class Author implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;

    private String lastName;

    private LocalDate dateOfBirth;

    @Enumerated(EnumType.STRING)
    private AuthorStatus status;

    @Version
    private int version;

    @ManyToMany(mappedBy = "authors")
    private List<Book> books = new ArrayList<>();

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

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public void addBook(Book book) {
        this.getBooks().add(book);
        book.getAuthors().add(this);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Author.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("firstName='" + firstName + "'")
                .add("lastName='" + lastName + "'")
                .add("dateOfBirth=" + dateOfBirth)
                .add("status=" + status)
                .toString();
    }
}
