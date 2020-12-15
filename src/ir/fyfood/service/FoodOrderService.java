package ir.fyfood.service;

import ir.fyfood.repository.dao.FoodOrderDao;
import ir.fyfood.repository.dto.CustomerOrdersDto;
import ir.fyfood.repository.dto.RestaurantOrderDto;
import ir.fyfood.repository.entity.Customer;
import ir.fyfood.repository.entity.FoodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class FoodOrderService {
    FoodOrderDao orderDao;
    static List<CustomerOrdersDto> customers;
    static List<RestaurantOrderDto> restaurants;

    @Autowired
    public FoodOrderService(FoodOrderDao orderDao) {
        this.orderDao = orderDao;
    }

    //=====================================================================
    public boolean saveOrder(FoodOrder order) {
        FoodOrder savedOrder = orderDao.save(order);
        if (savedOrder != null) {
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
        //customers = orderDao.getCustomersAndTheirTotalAmountOfPaymentsInRecentYear();
        List<Object[]> result = orderDao.getCustomersAndTheirTotalAmountOfPaymentsInRecentYear();
        customers = new ArrayList<>();
        for(int i=0; i<result.size(); i++){
            CustomerOrdersDto customerDto = new CustomerOrdersDto();
            customerDto.setCustomer((Customer) result.get(i)[0]);
            customerDto.setSumOfPayments((Long) result.get(i)[1]);
            customerDto.setOrderDate((LocalDate) result.get(i)[2]);
            customers.add(customerDto);
        }
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
