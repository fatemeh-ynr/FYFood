package service;

import repository.dao.FoodOrderDao;
import repository.dto.CustomerOrdersDto;
import repository.dto.RestaurantOrderDto;
import repository.entity.FoodOrder;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class FoodOrderService {
    FoodOrderDao orderDao = new FoodOrderDao();
    static List<CustomerOrdersDto> customers;
    static List<RestaurantOrderDto> restaurants;

    public boolean saveOrder(FoodOrder order) {
        if (orderDao.saveOrder(order)) {
            saveOrderInFile(order);
            return true;
        }
        return false;
    }

    //=====================================================================
    private void saveOrderInFile(FoodOrder order) {
        String fileOwner = order.getCustomer().getName();
        String fileContent = order.toString();
        String timestamp = order.getOrderDate().toString();
        String fileName = "Files\\OrderReports\\" + fileOwner + "_" + timestamp + ".txt";
        try (FileWriter fileWriter = new FileWriter(fileName);) {
            fileWriter.write(fileContent);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    //=====================================================================
    public void getCustomersAndTheirTotalAmountOfPaymentsInRecentYear() {
        customers = orderDao.getCustomersAndTheirTotalAmountOfPaymentsInRecentYear();
    }

    public static List<CustomerOrdersDto> getFilteredCustomerReport(int registrationMonth, int minTotalOrdersValue, int maxTotalOrdersValue) {
        List<CustomerOrdersDto> filteredCustomers = customers.stream()
                .filter(customer -> customer.getOrderDate().getMonthValue() == registrationMonth)
                .filter(customer -> customer.getSumOfPayments() > minTotalOrdersValue && customer.getSumOfPayments() < maxTotalOrdersValue)
                .collect(Collectors.toList());
        return filteredCustomers;
    }

    //=====================================================================
    public void getRestaurantsAndTheirTotalCourierFeeValue() {
        restaurants = orderDao.getRestaurantsAndTheirTotalCourierFeeValue();
    }

    public Map getTheBestSellingFoodOfEachRestaurant() {
        return orderDao.getTheBestSellingFoodOfEachRestaurant();
    }

    public List getFilteredRestaurantsReport(int serviceArea, int minTotalCourierFee, int maxTotalCourierFee) {
        List<RestaurantOrderDto> filteredRestaurants = restaurants.stream()
                .filter(restaurantOrderDto -> restaurantOrderDto.getServiceArea() == serviceArea)
                .filter(restaurantOrderDto -> restaurantOrderDto.getSumOfCourierFees() > minTotalCourierFee
                        && restaurantOrderDto.getSumOfCourierFees() < maxTotalCourierFee)
                .collect(Collectors.toList());
        return filteredRestaurants;
    }
}
