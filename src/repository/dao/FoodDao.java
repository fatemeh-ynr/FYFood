package repository.dao;

import repository.entity.Food;
import repository.entity.Restaurant;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.PersistenceException;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

public class FoodDao {
    SessionFactory sessionFactory = DatabaseConnection.getSessionFactory();
    Logger logger = LoggerFactory.getLogger(FoodDao.class);

    public boolean saveFood(Food food) {
        boolean success;
        Session session = sessionFactory.openSession();
        try {
            Transaction transaction = session.beginTransaction();
            session.save(food);
            transaction.commit();
            success = true;
            logger.info("Insert food information to \"food\" table: \"" + food.getName() + "\" from restaurant \"" + food.getRestaurant() + "\"");
        } catch (PersistenceException ex) {
            success = false;
            logger.info("Cannot Insert food information to \"food\" table: \"" + food.getName() + "\" from restaurant \"" + food.getRestaurant() + "\"");
        }
        session.close();
        return success;
    }

    //=====================================================================
    public List<String> getFoodTypes() {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Query query = session.createQuery("select distinct type from Food ");
        List<String> types = query.getResultList();
        transaction.commit();
        session.close();
        return types;
    }

    //=====================================================================
    public List<Food> getFoodListOfRestaurant(Restaurant restaurant) {
        List<Food> foods = new ArrayList<>();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Query query = session.createQuery("from Food food where food.foodId.restaurant=:restaurantName");
        query.setParameter("restaurantName", restaurant.getName());
        transaction.commit();
        session.close();
        return foods;
    }


}
