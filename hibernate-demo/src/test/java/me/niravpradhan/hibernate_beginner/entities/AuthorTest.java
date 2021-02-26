package me.niravpradhan.hibernate_beginner.entities;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@SpringBootTest
class AuthorTest {

    @Autowired
    EntityManager em;

    @Test
    @Transactional
    @Rollback(false)
    void createAuthor() {
        Author author = new Author();
        author.setFirstName("Amita");
        author.setLastName("Pradhan");

        em.persist(author);
    }
}