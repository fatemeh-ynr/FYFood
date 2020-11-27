package ir.fyfood.repository.dao;

import ir.fyfood.repository.entity.Customer;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.PersistenceException;

@Repository
public class CustomerDao {
    SessionFactory sessionFactory = DatabaseConnection.getSessionFactory();
    static Logger logger = LoggerFactory.getLogger(CustomerDao.class);

    public boolean saveCustomer(Customer customer) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        boolean success;
        try {
            session.save(customer);
            transaction.commit();
            success = true;
            logger.info("Insert information of \"" + customer.getName() + "\" to \"customer\" table.");
        } catch (PersistenceException ex) {
            success = false;
            logger.info("Cannot insert information of \"" + customer.getName() + "\" to \"customer\" table.");
        }
        session.close();
        return success;
    }

    //=====================================================================
    public Customer findCustomer(String phoneNumber) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Customer customer = session.get(Customer.class, phoneNumber);
        transaction.commit();
        session.close();
        return customer;
    }

}
