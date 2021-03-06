package ir.fyfood.controller;

import ir.fyfood.repository.entity.Customer;
import ir.fyfood.repository.entity.FoodOrder;
import ir.fyfood.repository.entity.Restaurant;
import ir.fyfood.service.CustomerService;
import ir.fyfood.service.FoodOrderService;
import ir.fyfood.service.FoodService;
import ir.fyfood.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class UserConsole {
    private CustomerService customerService;
    private FoodService foodService;
    private RestaurantService restaurantService;
    private FoodOrderService orderService;
    private Customer customer = new Customer();

    @Autowired
    public UserConsole(CustomerService customerService, FoodService foodService, RestaurantService restaurantService, FoodOrderService orderService) {
        this.customerService = customerService;
        this.foodService = foodService;
        this.restaurantService = restaurantService;
        this.orderService = orderService;
    }

    public void StartInteractingWithUser() {
        customer = getLoggedInCustomer();
        int area = ScannerReaders.readInt("enter your area: ");
        showFoodTypesMenu(area);
    }

    //=====================================================================
    private Customer getLoggedInCustomer() {
        Customer customer;
        while (true) {
            String mobileNumber = ScannerReaders.readNumber("enter your phone number: ");
            customer = customerService.getCustomer(mobileNumber);
            if (customer != null)
                break;
            else
                System.out.println("invalid mobileNumber...");
        }
        return customer;
    }

    //=====================================================================
    private void showFoodTypesMenu(int area) {
        List<String> foodTypes = foodService.getFoodTypes();
        if (foodTypes != null)
            foodTypes.add("all types");

        while (true) {
            //when come back to "types menu": type=null --> "all type":
            foodTypes.set(foodTypes.size() - 1, "all types");
            System.out.println("\nFood types:");
            for (int i = 0; i < foodTypes.size(); i++) {
                System.out.println((i + 1) + "." + foodTypes.get(i));
            }
            int selectedFoodTypeIndex = ScannerReaders.readInt("\nselect one option: ", 1, foodTypes.size());
            foodTypes.set(foodTypes.size() - 1, null); //type="all type"--> null
            String selectedFoodType = foodTypes.get(selectedFoodTypeIndex - 1);

            boolean successfulOrdering = showRestaurantList(selectedFoodType, area);

            if (successfulOrdering) {
                int option = ScannerReaders.readInt("\nenter 0 to exit 1 to continue: ", 0, 1);
                if (option == 0) {
                    break;
                }
            }
        }
    }

    //=====================================================================
    private boolean showRestaurantList(String selectedFoodType, int area) {
        while (true) {
            List<Restaurant> restaurants = restaurantService.getRestaurantWithFoodTypeAndArea(selectedFoodType, area);

            if (restaurants == null || restaurants.size() == 0) {
                System.out.println("\nNo restaurant is available ");
                return false;
            }

            System.out.println("\nList of restaurants: ");
            for (int i = 0; i < restaurants.size(); i++) {
                System.out.println((i + 1) + ". " + restaurants.get(i).toString());
            }

            int option = ScannerReaders.readInt("\nenter 0 to return to previous menu or 1 to continue: ", 0, 1);
            if (option == 0) {
                return false;
            }

            int restaurantIndex = ScannerReaders.readInt("\nselect restaurant number: ", 1, restaurants.size());
            Restaurant restaurant = restaurants.get(restaurantIndex - 1);
            boolean successfulOrdering = showFoodList(restaurant);
            if (successfulOrdering)
                return true;
        }
    }

    //=====================================================================
    private boolean showFoodList(Restaurant restaurant) {
        restaurant.addFoods(foodService.getFoodListOfRestaurant(restaurant));
        if (restaurant.hasNoFood()) {
            System.out.println("\nNo food is available in this restaurant \"" + restaurant.getName() + "\" right now.");
            return false;
        }

        System.out.println(restaurant.foodListToString());

        int option = ScannerReaders.readInt("enter 0 to return to previous menu or 1 to continue: ", 0, 1);
        if (option == 0) {
            return false;
        }

        FoodOrder order = createOrder(restaurant);
        boolean successfulOrdering = saveOrder(order);
        return successfulOrdering;
    }

    //=====================================================================
    private FoodOrder createOrder(Restaurant restaurant) {
        int foodIndex, foodNumber, options = 1;
        FoodOrder order = new FoodOrder(customer);
        order.setRestaurant(restaurant);
        order.setCourierFee(restaurant.getCourierFee());
        while (true) {
            if (options == 1) {
                System.out.println(restaurant.foodListToString());
                foodIndex = ScannerReaders.readInt("select one food to add: ", 1, restaurant.size());
                foodNumber = ScannerReaders.readInt("enter the number of this food: ", 1, Integer.MAX_VALUE);
                order.addFood(restaurant.getFood(foodIndex - 1), foodNumber);
            } else if (options == 2) {
                System.out.println(restaurant.foodListToString());
                foodIndex = ScannerReaders.readInt("select one food for remove: ", 1, restaurant.size());
                foodNumber = ScannerReaders.readInt("enter the number of this food you want to remove: ", 1, Integer.MAX_VALUE);
                order.removeFood(restaurant.getFood(foodIndex - 1), foodNumber);
            } else if (options == 3) {
                System.out.println(order.toString());

                System.out.println("1.Update the order");
                System.out.println("2.Finalize the order");
                int op = ScannerReaders.readInt("select one option:", 1, 2);
                if (op == 2)
                    break;
            }
            System.out.println("\n1.Add food");
            System.out.println("2.Remove food");
            System.out.println("3.Shopping Cart");
            options = ScannerReaders.readInt("select one option:", 1, 3);
        }

        order.setOrderDate(LocalDate.now());
        return order;
    }

    //=====================================================================
    private boolean saveOrder(FoodOrder order) {
        if (order.getTotalPrice() != 0) {

            boolean successfulSignIn = signInOrLogIn();
            if (successfulSignIn) {
                if (orderService.saveOrder(order))
                    System.out.println("Successful order registration");
                else
                    System.out.println("Order failed...");
            }
            return true;
        }
        return false;
    }

    //=====================================================================
    private boolean signInOrLogIn() {
        if (customer.isNew()) {
            System.out.println("\n complete your information:");
            customer.setName(ScannerReaders.readStringWord("enter your name: "));
            customer.setPostalCode(ScannerReaders.readNumber("enter postalCode: "));
            customer.setRegistrationDate(LocalDate.now());
            return customerService.saveCustomer(customer);
        }
        return true;
    }
}