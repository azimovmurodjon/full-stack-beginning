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

    private CustomerJPADataAccessService underTest;
    private AutoCloseable autoCloseable;
    @Mock private CustomerRepository customerRepository;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        underTest = new CustomerJPADataAccessService(customerRepository);
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
        underTest.selectAllCustomers();

        //Then
        verify(customerRepository)
                .findAll();
    }

    @Test
    void insertCustomer() {
        //Given
        int id = 1;

        //When
        underTest.selectCustomerById(id);
        //Then
        verify(customerRepository).findById(id);
    }

    @Test
    void existsPersonWithEmail() {
        // Given
        String email = "foo@gmail.com";

        // When
        underTest.existsPersonWithEmail(email);

        // Then
        verify(customerRepository).existsCustomerByEmail(email);
    }

    @Test
    void existsPersonWithId() {
        // Given
        String email = "foo@gmail.com";

        // When
        underTest.existsPersonWithEmail(email);

        // Then
        verify(customerRepository).existsCustomerByEmail(email);
    }

    @Test
    void deleteCustomerById() {
        //Given
        int id = 1;

        //When
        underTest.deleteCustomerById(id);

        //Then
        verify(customerRepository).deleteById(id);
    }

    @Test
    void updateCustomer() {
        //Given
        Customer customer = new Customer(
                1,"Ali", "Ali@gmail.com", 32
        );
        //When
        underTest.updateCustomer(customer);

        //Then
        verify(customerRepository).save(customer);
    }
}