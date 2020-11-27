package ir.fyfood.repository.dao;

import ir.fyfood.repository.dto.CustomerOrdersDto;
import ir.fyfood.repository.dto.RestaurantOrderDto;
import ir.fyfood.repository.entity.FoodOrder;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.Query;
import org.hibernate.transform.Transformers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.PersistenceException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class FoodOrderDao {
    SessionFactory sessionFactory = DatabaseConnection.getSessionFactory();
    Logger logger = LoggerFactory.getLogger(FoodDao.class);

    public boolean saveOrder(FoodOrder order) {
        boolean success;
        Session session = sessionFactory.openSession();
        try {
            Transaction transaction = session.beginTransaction();
            session.save(order);
            transaction.commit();
            success = true;
            logger.info("Insert a new order to \"order\" table.");
        } catch (PersistenceException ex) {
            success = false;
            logger.info("Cannot insert a new order to \"order\" table.");
        }
        session.close();
        return success;
    }

    //=====================================================================
    public List<CustomerOrdersDto> getCustomersAndTheirTotalAmountOfPaymentsInRecentYear() {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Criteria criteria = session.createCriteria(FoodOrder.class);
        criteria.setProjection(Projections.projectionList()
                .add(Projections.groupProperty("customer"))
                .add(Projections.alias(Projections.property("customer"), "customer"))
                .add(Projections.alias(Projections.sum("totalAmountOfPayment"), "sumOfPayments"))
                .add(Projections.alias(Projections.property("orderDate"), "orderDate")));
        criteria.add(Restrictions.between("orderDate", LocalDate.now().minusYears(1), LocalDate.now()));
        criteria.setResultTransformer(Transformers.aliasToBean(CustomerOrdersDto.class));
        List<CustomerOrdersDto> customers = criteria.list();
        transaction.commit();
        session.close();
        return customers;
    }

    //=====================================================================
    public List<RestaurantOrderDto> getRestaurantsAndTheirTotalCourierFeeValue() {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Query query = session.createQuery(
                "Select foodOrder.restaurant.name as restaurantName, " +
                        " foodOrder.restaurant.serviceArea as serviceArea, " +
                        "sum(foodOrder.courierFee) as sumOfCourierFees FROM FoodOrder foodOrder " +
                        " group by foodOrder.restaurant");
        query.setResultTransformer(Transformers.aliasToBean(RestaurantOrderDto.class));
        List<RestaurantOrderDto> restaurants = query.list();
        transaction.commit();
        session.close();
        return restaurants;
    }

    //=====================================================================
    public Map getTheBestSellingFoodOfEachRestaurant() {
        Map<String, List<String>> resultMap = new HashMap<>();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Query nativeQuery = session.createNativeQuery("SELECT restaurantName,food_name from \n" +
                "(SELECT restaurantName,max(totalNumberOfFood) as maxTotalNumberOfFood from \n" +
                "(SELECT name as restaurantName,food_name,sum(amount) as totalNumberOfFood from restaurant \n" +
                "inner join orders_foodlist on restaurant.name=orders_foodlist.Restaurant_name \n" +
                "group by restaurantName,food_name) as foodNumber \n" +
                "group by restaurantName) as maxFoodNumber \n" +
                "inner join \n" +
                "(SELECT name as restaurantName2,food_name,sum(amount) as totalNumberOfFood from restaurant\n" +
                "inner join orders_foodlist on restaurant.name=orders_foodlist.Restaurant_name \n" +
                "group by restaurantName2,food_name) as foodNumber2 \n" +
                "on maxFoodNumber.maxTotalNumberOfFood=foodNumber2.totalNumberOfFood and maxFoodNumber.restaurantName=foodNumber2.restaurantName2");

        List<Object[]> queryResult = nativeQuery.list();
        for (Object[] objects : queryResult) {
            String restaurantName = (String) objects[0];
            String foodName = (String) objects[1];
            if (restaurantName != null && foodName != null) {
                if (!resultMap.containsKey(restaurantName)) {
                    List<String> bestSellingFood = new ArrayList<>();
                    bestSellingFood.add(foodName);
                    resultMap.put(restaurantName, bestSellingFood);
                } else
                    //if there is two best selling food with the same total number
                    resultMap.get(restaurantName).add(foodName);
            }
        }
        transaction.commit();
        session.close();
        return resultMap;
    }
}
