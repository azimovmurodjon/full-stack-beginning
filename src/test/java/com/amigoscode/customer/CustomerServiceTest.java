package com.amigoscode.customer;

import com.amigoscode.exception.DuplicateResourceException;
import com.amigoscode.exception.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
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
        int id = 10;
        Customer customer = new Customer(
                id, "Alex", "alex@gmail.com", 33
        );
        when(customerDao.selectCustomerById(id)).thenReturn(Optional.of(customer));

        //WHEN
        Customer actual = underTest.getCustomer(10);

        //THEN
        assertThat(actual).isEqualTo(customer);

    }
    @Test
    void willThrowWhenGetCustomerReturnsEmptyOptional() {
        //GIVEN
        int id = 10;

        when(customerDao.selectCustomerById(id)).thenReturn(Optional.empty());

        //WHEN
        //THEN
        assertThatThrownBy(() -> underTest.getCustomer(id))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage(
                        "Customer with id [%s] not found"
                                .formatted(id));

    }

    @Test
    void addCustomer() {
        //GIVEN
        int id = 10;
        String email = "alex@gmail.com";

        when(customerDao.existsPersonWithEmail(email)).thenReturn(false);

        CustomerRegistrationRequest request = new CustomerRegistrationRequest(
                "Alex", email, 19
        );
        //WHEN
        underTest.addCustomer(request);

        //THEN
        ArgumentCaptor<Customer> customerArgumentCaptor = ArgumentCaptor.forClass(
                Customer.class
        );
        verify(customerDao).insertCustomer(customerArgumentCaptor.capture());

        Customer capturedCustomer = customerArgumentCaptor.getValue();
        assertThat(capturedCustomer.getId()).isNull();
        assertThat(capturedCustomer.getName()).isEqualTo(request.name());
        assertThat(capturedCustomer.getEmail()).isEqualTo((request.email()));
        assertThat(capturedCustomer.getAge()).isEqualTo((request.age()));

    }
    @Test
    void willThrowWhenEmailExistsWhileAddingACustomer() {
        //GIVEN
        int id = 10;
        String email = "alex@gmail.com";

        when(customerDao.existsPersonWithEmail(email)).thenReturn(true);

        CustomerRegistrationRequest request = new CustomerRegistrationRequest(
                "Alex", email, 19
        );
        //WHEN
        assertThatThrownBy(()-> underTest.addCustomer(request))
                .isInstanceOf(DuplicateResourceException.class)
                .hasMessage("Email already taken");

        //THEN

        verify(customerDao, never()).insertCustomer(any());
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