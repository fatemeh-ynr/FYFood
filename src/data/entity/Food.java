package data.entity;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

@Entity
public class Food {
    @EmbeddedId
    private FoodId foodId = new FoodId();
    private int price;
    private String type;

    public Food() {
    }

    public Food(String name, int price, String type, Restaurant restaurant) {
        setName(name);
        setPrice(price);
        setType(type);
        setRestaurant(restaurant);
    }

    public void setName(String name) {
        foodId.setName(name);
    }

    public String getName() {
        return foodId.getName();
    }

    public void setRestaurant(Restaurant restaurant) {
        foodId.setRestaurant(restaurant);
    }

    public Restaurant getRestaurant() {
        return foodId.getRestaurant();
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getPrice() {
        return price;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        return "name=" + foodId.getName() + ", price=" + price + ", type=" + type;
    }
}
