package ir.fyfood.repository.dto;

import ir.fyfood.repository.entity.Customer;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public class CustomerOrdersDto {
    private Customer customer;
    private double sumOfPayments;
    private LocalDate orderDate;

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public double getSumOfPayments() {
        return sumOfPayments;
    }

    public void setSumOfPayments(double sumOfPayments) {
        this.sumOfPayments = sumOfPayments;
    }

    @Override
    public String toString() {
        String result = "totalAmountOfPayments=" + (int) sumOfPayments + ", ";
        if (this.customer != null) {
            result += "name=" + customer.getName() + ", ";
            result += "mobileNumber=" + customer.getMobileNumber();
        }
        return result;
    }
}
