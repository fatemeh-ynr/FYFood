package ir.fyfood.repository.dao;

import ir.fyfood.repository.entity.Food;
import ir.fyfood.repository.entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FoodDao extends JpaRepository<Food, Integer> {

    @Query("select distinct type from Food ")
    List<String> getFoodTypes();

    @Query("select DISTINCT food from Food food left join food.foodId.restaurant where food.foodId.restaurant=:restaurantName")
    List<Food> getFoodListOfRestaurant(@Param("restaurantName") Restaurant restaurant);
    //return duplicate row!!!
    //List<Food> findDistinctByFoodId_Restaurant(Restaurant restaurant);


}
