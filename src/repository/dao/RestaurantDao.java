package repository.dao;

import repository.entity.Restaurant;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.Query;
import org.hibernate.transform.DistinctRootEntityResultTransformer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.PersistenceException;
import java.util.List;

public class RestaurantDao {
    SessionFactory sessionFactory = DatabaseConnection.getSessionFactory();
    Logger logger = LoggerFactory.getLogger(RestaurantDao.class);

    public boolean saveRestaurant(Restaurant restaurant) {
        boolean success;
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.save(restaurant);
            transaction.commit();
            success = true;
            logger.info("Insert \"" + restaurant.getName() + "\" restaurant information to \"restaurant\" table.");
        } catch (PersistenceException ex) {
            success = false;
            logger.info("Cannot insert \"" + restaurant.getName() + "\" restaurant information to \"restaurant\" table.");
        }
        session.close();
        return success;
    }

    //=====================================================================
    public List<Restaurant> getRestauranInAreaWithSpecialFoodType(String type, int area) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Criteria restaurantCriteria = session.createCriteria(Restaurant.class);
        restaurantCriteria.add(Restrictions.eq("serviceArea", area));
        if (type != null) {
            Criteria foodCriteria = restaurantCriteria.createCriteria("foodsList");
            foodCriteria.add(Restrictions.eq("type", type));
        }
        restaurantCriteria.setResultTransformer(DistinctRootEntityResultTransformer.INSTANCE);//Distinct
        List<Restaurant> restaurants = restaurantCriteria.list();
        return restaurants;
    }

    //=====================================================================
    public List<Integer> getAllServiceArea() {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Query query = session.createQuery("select distinct restaurant.serviceArea from Restaurant restaurant");
        List<Integer> serviceAreas = query.list();
        transaction.commit();
        session.close();
        return serviceAreas;
    }

}
