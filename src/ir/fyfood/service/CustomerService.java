package ir.fyfood.service;

import ir.fyfood.repository.dao.CustomerDao;
import ir.fyfood.repository.entity.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {
    CustomerDao customerDao;

    @Autowired
    public CustomerService(CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    public boolean saveCustomer(Customer customer) {
        if (mobileNumberIsValid(customer.getMobileNumber()) && postalCodeIsValid(customer.getPostalCode())) {
            customerDao.save(customer);
            return true;
        }
        return false;
    }

    //=====================================================================
    public Customer getCustomer(String mobileNumber) {
        if (mobileNumberIsValid(mobileNumber)) {
            Customer customer = customerDao.findByMobileNumber(mobileNumber);
            if (customer == null) {
                customer = new Customer(mobileNumber);
            }
            return customer;
        }
        return null;
    }

    //=====================================================================
    private boolean mobileNumberIsValid(String mobileNumber) {
        if (mobileNumber.length() > 0)
            //if(mobileNumber.length()==11)
            return true;
        return false;
    }

    //=====================================================================
    private boolean postalCodeIsValid(String postalCode) {
        if (postalCode.length() > 0)
            //if(postalCode.length() == 10)
            return true;
        return false;
    }

}
