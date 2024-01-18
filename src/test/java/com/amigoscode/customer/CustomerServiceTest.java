package com.amigoscode.customer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

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
        //WHEN
        underTest.getAllCustomers();

        //THEN
        verify(customerDao).selectAllCustomers();
    }

    @Test
    void getCustomer() {
        //GIVEN

        //WHEN

        //THEN
    }

    @Test
    void addCustomer() {
        //GIVEN

        //WHEN

        //THEN
    }

    @Test
    void deleteCustomerById() {
        //GIVEN

        //WHEN

        //THEN
    }

    @Test
    void updateCustomer() {
        //GIVEN

        //WHEN

        //THEN
    }
}