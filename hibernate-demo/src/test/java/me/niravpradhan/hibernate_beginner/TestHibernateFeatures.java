package me.niravpradhan.hibernate_beginner;

import me.niravpradhan.hibernate_beginner.entities.Author;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDate;

@SpringBootTest
class TestHibernateFeatures {

    @Autowired
    EntityManager em;

    @Test
    @Transactional
    @Rollback(false)
    void test_How_to_persist_LocalDate_and_LocalDateTime_with_JPA() {
        Author author = new Author();
        author.setFirstName("Dilip");
        author.setLastName("Pradhan");
        author.setDateOfBirth(LocalDate.of(1942, 1, 27));

        em.persist(author);
    }

    @Test
    void test_How_to_read_LocalDate_and_LocalDateTime_with_JPA() {
        Author author = em.find(Author.class, 4L);

        System.out.printf("%s%n", author);
    }
}
