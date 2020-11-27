package ir.fyfood.service;

import ir.fyfood.repository.dao.FoodDao;
import ir.fyfood.repository.entity.Food;
import ir.fyfood.repository.entity.Restaurant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FoodService {
    FoodDao foodDao;

    @Autowired
    public FoodService(FoodDao foodDao) {
        this.foodDao = foodDao;
    }

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
