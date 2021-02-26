package me.niravpradhan.hibernate_beginner;

import me.niravpradhan.hibernate_beginner.entities.Book;
import me.niravpradhan.hibernate_beginner.entities.Review;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@SpringBootTest
class TestHibernateFeatures {

    @Autowired
    EntityManager em;

    @Test
    @Transactional
    @Rollback(false)
    void test_manytoone_bidirectional_relationship() {
        Book book = em.find(Book.class, 1L);

        Review review = new Review();
        review.setComment("Awesome book.");
        book.addReview(review);

        em.persist(review);
    }
}
