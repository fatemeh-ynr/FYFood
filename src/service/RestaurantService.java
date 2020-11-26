package service;

import repository.dao.RestaurantDao;
import repository.entity.Food;
import repository.entity.Restaurant;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class RestaurantService {
    RestaurantDao restaurantDao = new RestaurantDao();
    FoodService foodService = new FoodService();

    public void initializeDBFromFile(String path) {
        try (FileReader inf = new FileReader(path);
             Scanner scanner = new Scanner(inf);) {
            int restaurantsNumber = Integer.valueOf(scanner.nextLine());
            String line;
            String[] words;
            for (int i = 0; i < restaurantsNumber; i++) {
                line = scanner.nextLine();
                words = line.split(", ");
                try {
                    Restaurant restaurant = new Restaurant(words[0], Integer.valueOf(words[2]), Integer.valueOf(words[3]));
                    saveRestaurant(restaurant);

                    int foodNumber = Integer.valueOf(words[1]);
                    for (int j = 0; j < foodNumber; j++) {
                        line = scanner.nextLine();
                        words = line.split(", ");
                        try {
                            Food food = new Food(words[0], Integer.valueOf(words[1]), words[2], restaurant);
                            foodService.saveFood(food);
                        } catch (ArrayIndexOutOfBoundsException ex) {
                            //inner try-catch:one invalid food format does not prevent saving other foods
                            ex.printStackTrace();
                        } catch (NumberFormatException ex) {
                            System.out.println(ex.getMessage());
                        }
                    }
                } catch (ArrayIndexOutOfBoundsException ex) {
                    ex.printStackTrace();
                } catch (NumberFormatException ex) {
                    System.out.println(ex.getMessage());
                }

            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    //=====================================================================
    public boolean saveRestaurant(Restaurant restaurant) {
        if (restaurant.getServiceArea() > 0 && restaurant.getCourierFee() >= 0) {
            return restaurantDao.saveRestaurant(restaurant);
        }
        return false;
    }

    //=====================================================================
    public List<Restaurant> getRestaurantWithFoodTypeAndArea(String type, int area) {
        return restaurantDao.getRestauranInAreaWithSpecialFoodType(type, area);
    }

    //=====================================================================
    public List<Integer> getAllServiceArea() {
        return restaurantDao.getAllServiceArea();
    }

}
