package ir.fyfood.repository.dto;

import ir.fyfood.repository.entity.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public class CustomerOrdersDto {
    private Customer customer;
    private long sumOfPayments;
    private LocalDate orderDate;

    public CustomerOrdersDto(Customer customer, long sumOfPayments, LocalDate orderDate) {
        this.customer = customer;
        this.sumOfPayments = sumOfPayments;
        this.orderDate = orderDate;
    }

    public CustomerOrdersDto() {
    }

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

    public long getSumOfPayments() {
        return sumOfPayments;
    }

    public void setSumOfPayments(long sumOfPayments) {
        this.sumOfPayments = sumOfPayments;
    }

    @Override
    public String toString() {
        String result ="\t\t\t\t\t\t\t\t\t";
        if (this.customer != null) {
            result += customer.getName() + "\t\t";
            result += customer.getMobileNumber();

        }
        return result;
    }
}
