package me.niravpradhan.hibernate_beginner;

import me.niravpradhan.hibernate_beginner.entities.Author;
import me.niravpradhan.hibernate_beginner.entities.Book;
import me.niravpradhan.hibernate_beginner.entities.Review;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.Date;

@SpringBootTest
class TestHibernateFeatures {

    @Autowired
    EntityManager em;

    @Test
    @Order(2)
    @Transactional
    @Rollback(false)
    void test_createAuthor() {
        Author author = new Author();
        author.setFirstName("Nirav");
        author.setLastName("Pradhan");

        em.persist(author);
    }

    @Test
    @Order(3)
    @Transactional
    @Rollback(false)
    void test_createBook() {
        Book book = new Book();
        book.setTitle("Hibernate Beginner");

        em.persist(book);
    }

    @Test
    @Order(4)
    @Transactional
    @Rollback(false)
    void test_manytoone_bidirectional_relationship() {
        Book book = em.find(Book.class, 1L);

        Review review = new Review();
        review.setComment("Awesome book.");
        book.addReview(review);

        em.persist(review);
    }

    @Test
    @Order(5)
    @Transactional
    @Rollback(false)
    void test_manytomay_bidirectional_association() {
        Book book = em.find(Book.class, 1L);

        Author author = new Author();
        author.setFirstName("Thorben");
        author.setLastName("Janssen");
        author.addBook(book);

        em.persist(author);
    }

    @Test
    @Order(6)
    @Transactional
    @Rollback(false)
    void test_how_to_map_a_java_util_Date() {
        Author author = new Author();
        author.setFirstName("Amita");
        author.setLastName("Pradhan");
        author.setDateOfBirth(new Date(80, 0, 8));

        em.persist(author);
        em.flush();

        author = em.find(Author.class, author.getId());
        System.out.printf("%s%n", author);
    }
}
