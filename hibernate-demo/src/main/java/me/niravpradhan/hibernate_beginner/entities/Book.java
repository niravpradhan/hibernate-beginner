package me.niravpradhan.hibernate_beginner.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@SqlResultSetMapping(
        name = "BookMapping",
        entities = {
                @EntityResult(
                        entityClass = Book.class,
                        fields = {
                                @FieldResult(name = "id", column = "id"),
                                @FieldResult(name = "title", column = "title"),
                                @FieldResult(name = "version", column = "version")
                        }
                )
        })
public class Book implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Version
    private Integer version;

    private String title;

    @OneToMany(mappedBy = "book")
    private List<Review> reviews = new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "book_author", joinColumns = {@JoinColumn(name = "fk_book", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "fk_author", referencedColumnName = "id")}
    )
    private List<Author> authors = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }

    public void addReview(Review review) {
        review.setBook(this);
        this.getReviews().add(review);
    }

    public void addAuthor(Author author) {
        this.getAuthors().add(author);
        author.getBooks().add(this);
    }
}
