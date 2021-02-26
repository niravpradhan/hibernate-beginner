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
    void test_mapping_enum_ordinal_to_database_column() {
        // change the database table author, remove existing author_status column if present
        Author author = new Author();
        author.setFirstName("Gargi");
        author.setLastName("Pradhan");
        author.setDateOfBirth(LocalDate.of(1980, 1, 21));
        author.setStatus(AuthorStatus.SELF_PUBLISHED);

        em.persist(author);
    }

    @Test
    @Transactional
    @Rollback(false)
    void test_mapping_enum_string_to_database_column() {
        // change the database table author, remove existing author_status column if present
        Author author = new Author();
        author.setFirstName("Gargi");
        author.setLastName("Pradhan");
        author.setDateOfBirth(LocalDate.of(1980, 1, 21));
        author.setStatus(AuthorStatus.SELF_PUBLISHED);

        em.persist(author);
    }
}
