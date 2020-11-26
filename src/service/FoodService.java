package service;

import repository.dao.FoodDao;
import repository.entity.Food;
import repository.entity.Restaurant;

import java.util.List;

public class FoodService {
    FoodDao foodDao = new FoodDao();

    public boolean saveFood(Food food) {
        if (food.getPrice() >= 0)
            return foodDao.saveFood(food);
        return false;
    }

    public List<String> getFoodTypes() {
        return foodDao.getFoodTypes();
    }


    public List<Food> getFoodListOfRestaurant(Restaurant restaurant){
        return foodDao.getFoodListOfRestaurant(restaurant);
    }
}
