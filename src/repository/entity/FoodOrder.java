package repository.entity;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Entity
@Table(name = "orders")
public class FoodOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int orderCode;
    @ManyToOne
    private Customer customer;
    @ManyToOne
    private Restaurant restaurant;
    private int courierFee;
    private int totalAmountOfPayment;
    private LocalDate orderDate;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "orders_foodList", joinColumns = @JoinColumn(name = "orderCode"))
    @MapKeyJoinColumns({
            @MapKeyJoinColumn(name = "food_name"),
            @MapKeyJoinColumn(name = "restaurant_name"),
    })
    @Column(name = "amount")
    private Map<Food, Integer> foodsList = new HashMap<>();

    public FoodOrder(Customer customer) {
        setCustomer(customer);
    }

    public FoodOrder() {
    }

    public int getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(int orderCode) {
        this.orderCode = orderCode;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public int getCourierFee() {
        return courierFee;
    }

    public void setCourierFee(int price) {
        courierFee = price;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    public Map getFoodsList() {
        return foodsList;
    }

    //-----------------------------------
    public void addFood(Food food, int number) {
        if (foodsList.containsKey(food)) {
            foodsList.put(food, foodsList.get(food) + number);//update food number
        } else
            foodsList.put(food, number);//insert new food
    }

    //-----------------------------------
    public void removeFood(Food food, int number) {
        if (foodsList.containsKey(food)) {
            if (foodsList.get(food) - number > 0)
                foodsList.put(food, foodsList.get(food) - number);
            else
                foodsList.remove(food);
        }
    }

    //-----------------------------------
    public int getTotalPrice() {
        if (foodsList == null || foodsList.size() == 0)
            return 0;
        int totalPrice = courierFee;
        for (Map.Entry entery : foodsList.entrySet()) {
            totalPrice += ((Food) entery.getKey()).getPrice() * ((int) entery.getValue());
        }
        this.totalAmountOfPayment = totalPrice;
        return totalPrice;
    }

    //-----------------------------------
    @Override
    public String toString() {
        String result = "-----------------------------------------------\n";
        if (foodsList != null) {
            for (Map.Entry entry : foodsList.entrySet()) {
                result += ((Food) entry.getKey()).toString() + ", number= " + ((int) entry.getValue()) + "\n";
            }
        }
        result += "Courier Fee= " + courierFee;
        result += "\nTotal Price= " + getTotalPrice();
        result += "\n-----------------------------------------------\n";
        return result;
    }
}


