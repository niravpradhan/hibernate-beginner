package me.niravpradhan.hibernate_beginner;

import me.niravpradhan.hibernate_beginner.entities.Author;
import me.niravpradhan.hibernate_beginner.entities.AuthorStatus;
import me.niravpradhan.hibernate_beginner.entities.Book;
import me.niravpradhan.hibernate_beginner.entities.Order;
import me.niravpradhan.hibernate_beginner.entities.OrderItem;
import me.niravpradhan.hibernate_beginner.entities.Product;
import me.niravpradhan.hibernate_beginner.entities.Review;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

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
    @org.junit.jupiter.api.Order(1)
    void test_createAuthors() {
        Author author1 = createAuthor("Thorben", "Jassen", LocalDate.of(1978, 1, 1), AuthorStatus.NOT_PUBLISHED);
        Author author2 = createAuthor("Cay", "Horstmann", LocalDate.of(1978, 1, 1), AuthorStatus.NOT_PUBLISHED);

        em.persist(author1);
        em.persist(author2);
    }

    @Test
    @Transactional
    @Rollback(false)
    @org.junit.jupiter.api.Order(2)
    void test_createBooks() {
        Book book1 = createBook("Hibernate Beginners");
        Book book2 = createBook("Hibernate Tips");
        Book book3 = createBook("Core Java Part-I");
        Book book4 = createBook("Core Java Part-II");

        em.persist(book1);
        em.persist(book2);
        em.persist(book3);
        em.persist(book4);
    }

    @Test
    @Transactional
    @Rollback(false)
    @org.junit.jupiter.api.Order(3)
    void test_createBookAuthors() {
        Book book1 = em.find(Book.class, 1L);
        Book book2 = em.find(Book.class, 2L);
        Book book3 = em.find(Book.class, 3L);
        Book book4 = em.find(Book.class, 4L);

        Author author1 = em.find(Author.class, 1L);
        Author author2 = em.find(Author.class, 2L);

        author1.addBook(book1);
        author1.addBook(book2);
        author2.addBook(book3);
        author2.addBook(book4);

        author1.setStatus(AuthorStatus.PUBLISHED);
        author2.setStatus(AuthorStatus.PUBLISHED);
    }

    @Test
    @Transactional
    @Rollback(false)
    @org.junit.jupiter.api.Order(4)
    void test_createBookReviews() {
        Book book1 = em.find(Book.class, 1L);
        Book book2 = em.find(Book.class, 2L);
        Book book3 = em.find(Book.class, 3L);
        Book book4 = em.find(Book.class, 4L);

        Review review1 = createReview("Hibernate Beginners Book is really good.");
        Review review2 = createReview("Hibernate Tips Book is really good.");
        Review review3 = createReview("Core Java Part-I Book is really good.");
        Review review4 = createReview("Core Java Part-II Book is really good.");

        review1.setBook(book1);
        review2.setBook(book2);
        review3.setBook(book3);
        review4.setBook(book4);

        em.persist(review1);
        em.persist(review2);
        em.persist(review3);
        em.persist(review4);
    }

    @Test
    @Transactional
    @Rollback(false)
    @org.junit.jupiter.api.Order(5)
    void test_first_page() {
        int pageNo = 1;
        int pageSize = 2;
        Query nativeQuery = em.createNativeQuery("select * from book", Book.class);
        nativeQuery.setMaxResults(pageSize);
        nativeQuery.setFirstResult((pageNo - 1) * pageSize);
        List<Book> books = nativeQuery.getResultList();
        books.stream().map(Book::getTitle).forEach(System.out::println);
    }

    @Test
    @Transactional
    @Rollback(false)
    @org.junit.jupiter.api.Order(6)
    void test_second_page() {
        int pageNo = 2;
        int pageSize = 2;
        Query nativeQuery = em.createNativeQuery("select * from book", Book.class);
        nativeQuery.setMaxResults(pageSize);
        nativeQuery.setFirstResult((pageNo - 1) * pageSize);
        List<Book> books = nativeQuery.getResultList();
        books.stream().map(Book::getTitle).forEach(System.out::println);
    }
}
