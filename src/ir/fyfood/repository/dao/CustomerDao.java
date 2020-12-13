package ir.fyfood.repository.dao;

import ir.fyfood.repository.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerDao extends JpaRepository<Customer, Integer> {
    Customer findByMobileNumber(String phoneNumber);
}
