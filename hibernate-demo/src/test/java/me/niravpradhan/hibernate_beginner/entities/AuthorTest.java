package me.niravpradhan.hibernate_beginner.entities;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.service.ServiceRegistry;
import org.junit.jupiter.api.Test;

class AuthorTest {

    @Test
    void createAuthor() {
        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().configure().build();

        SessionFactory sessionFactory = new MetadataSources(serviceRegistry).addAnnotatedClass(Author.class)
            .buildMetadata().buildSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        Author author = new Author();
        author.setFirstName("Nirav");
        author.setLastName("Pradhan");

        session.save(author);

        session.getTransaction().commit();
        session.close();
        sessionFactory.close();
    }
}