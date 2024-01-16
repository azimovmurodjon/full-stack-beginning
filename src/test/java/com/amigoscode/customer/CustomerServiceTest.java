package com.amigoscode.customer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;

class CustomerServiceTest {

    @Mock
    private CustomerDao customerDao;
    private CustomerService underTest;

    @BeforeEach
    void setUp() {
        AutoCloseable autoCloseable = MockitoAnnotations.openMocks(this);
        underTest = new CustomerService(customerDao);
    }

    @Test
    void getAllCustomers() {
        //GIVCEN
        //WHEN
        //THEN
    }

    @Test
    void getCustomer() {
        //GIVCEN
        //WHEN
        //THEN
    }

    @Test
    void addCustomer() {
        //GIVCEN
        //WHEN
        //THEN
    }

    @Test
    void deleteCustomerById() {
        //GIVCEN
        //WHEN
        //THEN
    }

    @Test
    void updateCustomer() {
        //GIVCEN
        //WHEN
        //THEN
    }
}