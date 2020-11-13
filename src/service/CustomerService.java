package service;

import data.dao.CustomerDao;
import data.entity.Customer;

public class CustomerService {
    CustomerDao customerDao = new CustomerDao();

    public boolean saveCustomer(Customer customer) {
        if (mobileNumberIsValid(customer.getMobileNumber()) && postalCodeIsValid(customer.getPostalCode())) {
            return customerDao.saveCustomer(customer);
        }
        return false;
    }

    //=====================================================================
    public Customer getCustomer(String mobileNumber) {
        if (mobileNumberIsValid(mobileNumber)) {
            Customer customer = customerDao.findCustomer(mobileNumber);
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
