package me.niravpradhan.hibernate_beginner;

import me.niravpradhan.hibernate_beginner.dtos.AuthorDTO;
import me.niravpradhan.hibernate_beginner.entities.Author;
import me.niravpradhan.hibernate_beginner.entities.Order;
import me.niravpradhan.hibernate_beginner.entities.OrderItem;
import me.niravpradhan.hibernate_beginner.entities.Product;
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

    @Test
    @Transactional
    @Rollback(false)
    void test_nativeSQLQuery() {
        Query nativeQuery = em.createNativeQuery("select a.first_name, a.last_name from author a");
        List<Object[]> resultList = nativeQuery.getResultList();

        resultList.forEach((Object[] a) -> System.out.printf("Author %s %s%n", a[0], a[1]));
    }

    @Test
    @Transactional
    @Rollback(false)
    void test_positional_parameters_binding() {
        Query nativeQuery = em.createNativeQuery("select a.first_name, a.last_name from author a where a.id = ?");
        nativeQuery.setParameter(1, 7L);
        Object[] result = (Object[]) nativeQuery.getSingleResult();

        System.out.printf("Author %s %s%n", result[0], result[1]);
    }

    @Test
    @Transactional
    @Rollback(false)
    void test_named_parameters_binding() {
        Query nativeQuery = em.createNativeQuery("select a.first_name, a.last_name from author a where a.id = :id");
        nativeQuery.setParameter("id", 7L);
        Object[] result = (Object[]) nativeQuery.getSingleResult();

        System.out.printf("Author %s %s%n", result[0], result[1]);
    }

    @Test
    @Transactional
    @Rollback(false)
    void test_native_query_result_to_entity_mapping() {
        Query nativeQuery = em.createNativeQuery("select a.id, a.first_name, a.last_name, a.date_of_birth, a.version, a.status from author a", Author.class);
        List<Author> authors = nativeQuery.getResultList();

        authors.forEach(a -> System.out.printf("Author %s %s%n", a.getFirstName(), a.getLastName()));
    }

    @Test
    @Transactional
    @Rollback(false)
    void test_native_query_result_to_dto_mapping() {
        Query nativeQuery = em.createNativeQuery("select a.id, a.first_name, a.last_name, a.date_of_birth, " +
                "a.status, a.version, (ba.fk_author) as num_of_books from author a " +
                "left join book_author ba on a.id = ba.fk_author", "AuthorDTOMapping");
        List<AuthorDTO> authors = nativeQuery.getResultList();

        authors.forEach(a -> System.out.printf("Author %s %s -> total book = %d%n", a.getFirstName(), a.getLastName(), a.getNumOfBooks()));
    }

    @Test
    @Transactional
    @Rollback(false)
    void test_native_named_query_result_to_dto_mapping() {
        Query nativeQuery = em.createNamedQuery("Author.selectAllWithBooksCount");
        List<Object[]> authors = nativeQuery.getResultList();

        authors.forEach((Object[] a) -> System.out.printf("Author %s %s -> total book = %d%n", a[1], a[2], a[6]));
    }
}
