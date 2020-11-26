package repository.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Restaurant {
    @Id
    private String name;
    private int serviceArea;
    private int courierFee;
    @OneToMany(mappedBy = "foodId.restaurant")
    private List<Food> foodsList = new ArrayList<>();

    public Restaurant() {
    }

    public Restaurant(String name, int serviceArea, int courierFee) {
        setName(name);
        setServiceArea(serviceArea);
        setCourierFee(courierFee);
    }

    public String getName() {
        return name;
    }

    public int getServiceArea() {
        return serviceArea;
    }

    public int getCourierFee() {
        return courierFee;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setServiceArea(int serviceArea) {
        this.serviceArea = serviceArea;
    }

    public void setCourierFee(int courierFee) {
        this.courierFee = courierFee;
    }

    public void addFood(Food food) {
        foodsList.add(food);
    }

    public void addFoods(List<Food> foods) {
        foodsList.addAll(foods);
    }

    public Food getFood(int index) {
        return foodsList.get(index);
    }

    public void clearAllFoods() {
        foodsList.clear();
    }

    public boolean hasNoFood() {
        if (foodsList == null || foodsList.size() == 0) {
            return true;
        }
        return false;
    }

    public int size() {
        if (foodsList == null)
            return 0;
        return foodsList.size();
    }

    @Override
    public String toString() {
        return name + ", Courier Fee=" + courierFee;
    }

    public String foodListToString() {
        if (hasNoFood())
            return "";
        String result = "\nList of food in restaurant \"" + name + "\" :\n";
        for (int i = 0; i < foodsList.size(); i++) {
            result += (i + 1) + ". " + foodsList.get(i) + "\n";
        }
        return result;
    }
}
