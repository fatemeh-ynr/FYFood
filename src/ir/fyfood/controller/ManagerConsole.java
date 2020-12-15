package ir.fyfood.controller;

import ir.fyfood.repository.dto.CustomerOrdersDto;
import ir.fyfood.repository.dto.RestaurantOrderDto;
import ir.fyfood.service.FoodOrderService;
import ir.fyfood.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class ManagerConsole {
    FoodOrderService foodOrderService;
    RestaurantService restaurantService;

    @Autowired
    public ManagerConsole(FoodOrderService foodOrderService, RestaurantService restaurantService) {
        this.foodOrderService = foodOrderService;
        this.restaurantService = restaurantService;
    }

    public void StartInteractingWithManager() {
            showReportsMenu();
    }


    //=====================================================================
    private void showReportsMenu() {

        while (true) {
            System.out.println("\n1.Report of customers");
            System.out.println("2.Report of restaurants");
            System.out.println("3.Exit");
            int reportType = ScannerReaders.readInt("\nselect one option: ", 1, 3);

            switch (reportType) {
                case 1:
                    showCustomersReport();
                    break;
                case 2:
                    showRestaurantsReport();
                    break;
                case 3:
                    return;
            }
        }

    }

    //=====================================================================
    private void showCustomersReport() {
        foodOrderService.getCustomersAndTheirTotalAmountOfPaymentsInRecentYear();
        List<CustomerOrdersDto> customersList1, customersList2, customersList3;

        System.out.println("\nMonth\tSumOfPayments(100Tomans)\tName\tMobileNumber ");
        for (int registrationMonth = 1; registrationMonth <= 12; registrationMonth++) {

            customersList1 = foodOrderService.getFilteredCustomerReport(registrationMonth, 0, 100000);
            customersList2 = foodOrderService.getFilteredCustomerReport(registrationMonth, 100000, 500000);
            customersList3 = foodOrderService.getFilteredCustomerReport(registrationMonth, 500000, Integer.MAX_VALUE);

            if (!customersList1.isEmpty() || !customersList2.isEmpty() || !customersList3.isEmpty()) {
                System.out.println("==========================================================");
                System.out.println(" " + registrationMonth);

                System.out.println("\t\t\tless than 100");
                if (!customersList1.isEmpty()) {
                    customersList1.forEach(System.out::println);
                }

                System.out.println("\t\t\t---------------------------------------------");
                System.out.println("\t\t\tbetween 100-500 ");
                if (!customersList2.isEmpty()) {
                    customersList2.forEach(System.out::println);
                }

                System.out.println("\t\t\t---------------------------------------------");
                System.out.println("\t\t\tmore than 500");
                if (!customersList3.isEmpty()) {
                    customersList3.forEach(System.out::println);
                }
            }
        }
        System.out.println("==========================================================");
    }

    //=====================================================================
    private void showRestaurantsReport() {
        foodOrderService.getRestaurantsAndTheirTotalCourierFeeValue();
        Map<String, List<String>> bestSellingFoods = foodOrderService.getTheBestSellingFoodOfEachRestaurant();

        List<RestaurantOrderDto> restaurantsList1, restaurantsList2, restaurantsList3;
        List<Integer> serviceAreas = restaurantService.getAllServiceArea();
        for (int serviceArea : serviceAreas) {
            restaurantsList1 = foodOrderService.getFilteredRestaurantsReport(serviceArea, 0, 17000);
            restaurantsList2 = foodOrderService.getFilteredRestaurantsReport(serviceArea, 17000, 2000000);
            restaurantsList3 = foodOrderService.getFilteredRestaurantsReport(serviceArea, 2000000, Integer.MAX_VALUE);

            if (!restaurantsList1.isEmpty() || !restaurantsList2.isEmpty() || !restaurantsList3.isEmpty()) {
                System.out.println("=================================");
                System.out.println("Service area:" + serviceArea);
                if (!restaurantsList1.isEmpty()) {
                    System.out.println("---------------------");
                    System.out.println("Total courier fee: less than 10,000 Tomans");
                    restaurantsList1.forEach(restaurantOrderDto -> {
                        System.out.println("restaurant: " + restaurantOrderDto.getRestaurantName());
                        bestSellingFoods.forEach((restaurantName, foods) -> {
                            if (restaurantName.equals(restaurantOrderDto.getRestaurantName())) {
                                System.out.println("best selling food: ");
                                ((List<String>) foods).forEach(System.out::println);
                            }
                        });
                    });
                }
                if (!restaurantsList2.isEmpty()) {
                    System.out.println("---------------------");
                    System.out.println("Total courier fee: between 10,000-2,000,000 Tomans");
                    restaurantsList2.forEach(restaurantOrderDto -> {
                        System.out.println("restaurant: " + restaurantOrderDto.getRestaurantName());
                        bestSellingFoods.forEach((restaurantName, foods) -> {
                            if (restaurantName.equals(restaurantOrderDto.getRestaurantName())) {
                                System.out.println("best selling food: ");
                                ((List<String>) foods).forEach(System.out::println);
                            }
                        });
                    });
                }
                if (!restaurantsList3.isEmpty()) {
                    System.out.println("---------------------");
                    System.out.println("Total courier fee: more than 2,000,000 Tomans");
                    restaurantsList3.forEach(restaurantOrderDto -> {
                        System.out.println("restaurant: " + restaurantOrderDto.getRestaurantName());
                        bestSellingFoods.forEach((restaurantName, foods) -> {
                            if (restaurantName.equals(restaurantOrderDto.getRestaurantName())) {
                                System.out.println("best selling food: ");
                                ((List<String>) foods).forEach(System.out::println);
                            }
                        });
                    });
                }
            }
        }
        System.out.println("========================================");
    }

}
