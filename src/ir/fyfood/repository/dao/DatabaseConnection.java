package ir.fyfood.repository.dao;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class DatabaseConnection {
    private final static SessionFactory sessionFactory = new Configuration().configure("ir/fyfood/configuration/hibernate.cfg.xml").buildSessionFactory();
    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
