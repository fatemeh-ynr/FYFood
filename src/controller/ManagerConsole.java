package controller;

import repository.dto.CustomerOrdersDto;
import repository.dto.RestaurantOrderDto;
import repository.entity.Manager;
import service.FoodOrderService;
import service.RestaurantService;

import java.util.List;
import java.util.Map;

public class ManagerConsole {
    static FoodOrderService foodOrderService = new FoodOrderService();
    static RestaurantService restaurantService = new RestaurantService();

    public static void StartInteractingWithManager() {
        if (authenticateManager())
            showReportsMenu();
    }

    //=====================================================================
    private static boolean authenticateManager() {
        int numberOfInvalidInput = 0;
        Manager manager = new Manager();
        while (numberOfInvalidInput < 3) {
            manager.setUsername(ScannerReaders.readStringWord("\nusername:"));
            manager.setPassword(ScannerReaders.readStringWord("password:"));

            if (manager.getUsername().compareTo("manager") == 0 && manager.getPassword().compareTo("123") == 0) {
                System.out.println("Valid username or password!");
                return true;
            }
            System.out.println("Invalid username and password...");
        }
        return false;
    }

    //=====================================================================
    private static void showReportsMenu() {

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
    private static void showCustomersReport() {
        foodOrderService.getCustomersAndTheirTotalAmountOfPaymentsInRecentYear();
        List<CustomerOrdersDto> customersList1, customersList2, customersList3;

        for (int registrationMonth = 1; registrationMonth <= 12; registrationMonth++) {

            customersList1 = foodOrderService.getFilteredCustomerReport(registrationMonth, 0, 100000);
            customersList2 = foodOrderService.getFilteredCustomerReport(registrationMonth, 100000, 500000);
            customersList3 = foodOrderService.getFilteredCustomerReport(registrationMonth, 500000, Integer.MAX_VALUE);

            if (!customersList1.isEmpty() || !customersList2.isEmpty() || !customersList3.isEmpty()) {
                System.out.println("=================================");
                System.out.println("Month:" + registrationMonth);
                if (!customersList1.isEmpty()) {
                    System.out.println("---------------------");
                    System.out.println("Total orders value: less than 100,000 Tomans");
                    customersList1.forEach(System.out::println);
                }
                if (!customersList2.isEmpty()) {
                    System.out.println("---------------------");
                    System.out.println("Total orders value: between 100,000-500,000 Tomans");
                    customersList2.forEach(System.out::println);
                }
                if (!customersList3.isEmpty()) {
                    System.out.println("---------------------");
                    System.out.println("Total orders value: more than 500,000 Tomans");
                    customersList3.forEach(System.out::println);
                }
            }
        }
        System.out.println("========================================");
    }

    //=====================================================================
    private static void showRestaurantsReport() {
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
