package com.amigoscode.customer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
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