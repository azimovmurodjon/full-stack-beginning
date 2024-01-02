package com.amigoscode.customer;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CustomerJPADataAccessServiceTest {

    private CustomerJPADataAccessService underaTest;
    private AutoCloseable autoCloseable;
    @Mock private CustomerRepository customerRepository;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        underaTest = new CustomerJPADataAccessService(customerRepository);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void selectAllCustomers() {
    }

    @Test
    void selectCustomerById() {
        //When
        underaTest.selectAllCustomers();

        //Then
        verify(customerRepository)
                .findAll();
    }

    @Test
    void insertCustomer() {
        //Given
        int id = 1;

        //When
        underaTest.selectCustomerById(id);
        //Then
        verify(customerRepository).findById(id);
    }

    @Test
    void existsPersonWithEmail() {
    }

    @Test
    void existsPersonWithId() {
    }

    @Test
    void deleteCustomerById() {
    }

    @Test
    void updateCustomer() {
    }
}