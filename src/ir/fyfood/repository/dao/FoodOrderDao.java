package ir.fyfood.repository.dao;

import ir.fyfood.repository.dto.CustomerOrdersDto;
import ir.fyfood.repository.dto.OrdersDtoNativeQuery;
import ir.fyfood.repository.dto.RestaurantOrderDto;
import ir.fyfood.repository.entity.FoodOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Map;

@Repository
public interface FoodOrderDao extends JpaRepository<FoodOrder, Integer>, JpaSpecificationExecutor<FoodOrder> {

    @Query("SELECT foodOrder.customer as customer, " +
            " sum(foodOrder.totalAmountOfPayment) as sumOfPayments," +
            " foodOrder.orderDate as orderDate" +
            " FROM FoodOrder foodOrder " +
            "GROUP BY customer ")
    public List getCustomersAndTheirTotalAmountOfPaymentsInRecentYear();


//    @Query("select new CustomerOrdersDto(" +
//            " foodOrder.customer," +
//            " sum(foodOrder.totalAmountOfPayment)," +
//            " foodOrder.orderDate) " +
//            " FROM FoodOrder foodOrder " +
//            " GROUP BY customer ")
//    public List<CustomerOrdersDto> getCustomersAndTheirTotalAmountOfPaymentsInRecentYear();


//    @Query(value = "SELECT mobileNumber, name, orderDate, sum(totalAmountOfPayment) FROM customer\n" +
//            " join orders where customer.mobileNumber=orders.customer_mobileNumber\n" +
//            " group by mobileNumber ", nativeQuery = true)
//    List<OrdersDtoNativeQuery> getCustomersAndTheirTotalAmountOfPaymentsInRecentYear();


//    @Query(value = "select new ir.fyfood.repository.dto.CustomerOrdersDto2(mobileNumber, name, orderDate, sum(totalAmountOfPayment)) FROM customer\n" +
//            " join orders where customer.mobileNumber=orders.customer_mobileNumber\n" +
//            " group by mobileNumber ", nativeQuery = true)
//    List<CustomerOrdersDto2> getCustomersAndTheirTotalAmountOfPaymentsInRecentYear();


//    @Query(value = "SELECT mobileNumber, name, orderDate, sum(totalAmountOfPayment) FROM customer\n" +
//            " join orders where customer.mobileNumber=orders.customer_mobileNumber\n" +
//            " group by mobileNumber ", nativeQuery = true)
//    List<CustomerDto> getCustomersAndTheirTotalAmountOfPaymentsInRecentYear();

//    public static interface CustomerDto {
//        String getMobileNumber();
//        String getName();
//        LocalDate getOrderDate();
//        long getSumOfPayments();
//    }

    @Query("Select foodOrder.restaurant.name as restaurantName, " +
            " foodOrder.restaurant.serviceArea as serviceArea, " +
            "sum(foodOrder.courierFee) as sumOfCourierFees FROM FoodOrder foodOrder " +
            " group by foodOrder.restaurant")
    public List<RestaurantOrderDto> getRestaurantsAndTheirTotalCourierFeeValue();

    @Query(value = "SELECT restaurantName,food_name from " +
            "(SELECT restaurantName,max(totalNumberOfFood) as maxTotalNumberOfFood from " +
            "(SELECT name as restaurantName,food_name,sum(amount) as totalNumberOfFood from restaurant " +
            "inner join orders_foodlist on restaurant.name=orders_foodlist.Restaurant_name " +
            "group by restaurantName,food_name) as foodNumber " +
            "group by restaurantName) as maxFoodNumber " +
            "inner join " +
            "(SELECT name as restaurantName2,food_name,sum(amount) as totalNumberOfFood from restaurant" +
            "inner join orders_foodlist on restaurant.name=orders_foodlist.Restaurant_name " +
            "group by restaurantName2,food_name) as foodNumber2 " +
            "on maxFoodNumber.maxTotalNumberOfFood=foodNumber2.totalNumberOfFood and maxFoodNumber.restaurantName=foodNumber2.restaurantName2"
            , nativeQuery = true)
    public Map getTheBestSellingFoodOfEachRestaurant();

}
