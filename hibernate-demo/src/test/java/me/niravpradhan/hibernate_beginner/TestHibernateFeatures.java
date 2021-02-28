package me.niravpradhan.hibernate_beginner;

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
    @org.junit.jupiter.api.Order(1)
    void test_createProducts() {
        logger.info("PERSISTING PRODUCTS...");

        Product p1 = createProduct("IPhone", "IPhone 10", new BigDecimal("1000"));
        Product p2 = createProduct("IPhone", "IPhone 11", new BigDecimal("1500"));
        Product p3 = createProduct("IPhone", "IPhone 12", new BigDecimal("2000"));

        em.persist(p1);
        em.persist(p2);
        em.persist(p3);
    }

    @Test
    @Transactional
    @Rollback(false)
    @org.junit.jupiter.api.Order(2)
    void test_createOrder() {
        logger.info("PERSISTING ORDER...");

        Order order = createOrder("A1");
        em.persist(order);
    }

    @Test
    @Transactional
    @Rollback(false)
    @org.junit.jupiter.api.Order(3)
    void test_createOrderItems() {
        logger.info("PERSISTING ORDER ITEMS...");

        Product p1 = em.find(Product.class, 1L);
        Product p2 = em.find(Product.class, 2L);
        Product p3 = em.find(Product.class, 3L);

        Order order = em.find(Order.class, 1L);

        OrderItem orderItem1 = createOrderItem(1, p1, order);
        OrderItem orderItem2 = createOrderItem(1, p2, order);
        OrderItem orderItem3 = createOrderItem(1, p3, order);

        em.persist(orderItem1);
        em.persist(orderItem2);
        em.persist(orderItem3);
    }

    @Test
    @Transactional
    @Rollback(false)
    void test_eagerLoading() {
        OrderItem orderItem = em.find(OrderItem.class, 1L);

        logger.info("PRODUCT NAME: " + orderItem.getProduct().getName());
    }

    @Test
    @Transactional
    @Rollback(false)
    void test_onetomany_lazy_loading() {
        List<Order> orders = em.createQuery("select distinct o from Order o").getResultList();

        logger.info("PRINTING EACH ORDER'S ITEMS NAME");
        orders.stream()
                .flatMap(o -> o.getItems().stream())
                .forEach(oi -> System.out.println("PRODUCT NAME: " + oi.getProduct().getName()));

    }

    @Test
    @Transactional
    @Rollback(false)
    void test_onetomany_eager_loading() {
        List<Order> orders = em.createQuery("select distinct o from Order o join fetch o.items oi").getResultList();

        logger.info("PRINTING EACH ORDER'S ITEMS NAME");
        orders.stream()
                .flatMap(o -> o.getItems().stream())
                .forEach(oi -> System.out.println("PRODUCT NAME: " + oi.getProduct().getName()));
    }
}
