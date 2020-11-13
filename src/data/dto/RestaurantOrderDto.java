package data.dto;

public class RestaurantOrderDto {
    private String restaurantName;
    private double sumOfCourierFees;
    private int serviceArea;

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public double getSumOfCourierFees() {
        return sumOfCourierFees;
    }

    public void setSumOfCourierFees(double sumOfCourierFees) {
        this.sumOfCourierFees = sumOfCourierFees;
    }

    public int getServiceArea() {
        return serviceArea;
    }

    public void setServiceArea(int serviceArea) {
        this.serviceArea = serviceArea;
    }

    @Override
    public String toString() {
        return "RestaurantOrderDto{" +
                "RestaurantName='" + restaurantName + '\'' +
                ", sumOfCourierFees=" + sumOfCourierFees +
                ", serviceArea=" + serviceArea +
                "}\n";
    }
}
