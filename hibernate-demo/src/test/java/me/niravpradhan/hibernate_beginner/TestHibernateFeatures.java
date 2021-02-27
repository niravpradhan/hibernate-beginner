package me.niravpradhan.hibernate_beginner;

import me.niravpradhan.hibernate_beginner.entities.Author;
import me.niravpradhan.hibernate_beginner.entities.AuthorStatus;
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
    void test_createAuthor() {
        Author author = new Author();
        author.setFirstName("John");
        author.setLastName("Doe");
        author.setDateOfBirth(LocalDate.of(2001, 1, 1));
        author.setStatus(AuthorStatus.NOT_PUBLISHED);

        System.out.println("Persisting a new entity");
        em.persist(author);

        System.out.println("Calling em flush method");
        em.flush();
    }
}
