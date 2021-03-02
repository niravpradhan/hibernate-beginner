package me.niravpradhan.hibernate_beginner;

import me.niravpradhan.hibernate_beginner.entities.Author;
import me.niravpradhan.hibernate_beginner.entities.AuthorStatus;
import me.niravpradhan.hibernate_beginner.entities.Book;
import me.niravpradhan.hibernate_beginner.entities.Order;
import me.niravpradhan.hibernate_beginner.entities.OrderItem;
import me.niravpradhan.hibernate_beginner.entities.Product;
import me.niravpradhan.hibernate_beginner.entities.Review;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.time.LocalDate;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

@SpringBootTest
class TestHibernateFeatures {

    private static final Logger logger = LoggerFactory.getLogger(TestHibernateFeatures.class);

    @Autowired
    EntityManager em;

    private Product createProduct(String name, String description, BigDecimal price) {
        Product product = new Product();
        product.setName(name);
        product.setDescription(description);
        product.setPrice(price);
        return product;
    }

    private Order createOrder(String orderNumber) {
        Order order = new Order();
        order.setOrderNumber(orderNumber);
        return order;
    }

    private OrderItem createOrderItem(int quantity, Product product, Order order) {
        OrderItem orderItem = new OrderItem();
        orderItem.setQuantity(quantity);
        orderItem.setProduct(product);
        orderItem.setOrder(order);
        return orderItem;
    }

    private Book createBook(String title) {
        Book book = new Book();
        book.setTitle(title);
        return book;
    }

    private Author createAuthor(String firstName, String lastName, LocalDate dateOfBirth, AuthorStatus status) {
        Author author = new Author();
        author.setFirstName(firstName);
        author.setLastName(lastName);
        author.setDateOfBirth(dateOfBirth);
        author.setStatus(status);
        return author;
    }

    private Review createReview(String comment) {
        Review review = new Review();
        review.setComment(comment);
        return review;
    }

    @Test
    @Transactional
    @Rollback(false)
    void test_hibernate_session() {
        Session session = em.unwrap(Session.class);
        assertThat(session, is(notNullValue()));
    }

    @Test
    @Transactional
    @Rollback(false)
    void test_hibernate_session_factory() {
        SessionFactory sessionFactory = em.getEntityManagerFactory().unwrap(SessionFactory.class);
        assertThat(sessionFactory, is(notNullValue()));
    }
}
