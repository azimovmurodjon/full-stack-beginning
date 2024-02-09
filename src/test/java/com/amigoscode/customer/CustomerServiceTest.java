package com.amigoscode.customer;

import com.amigoscode.exception.DuplicateResourceException;
import com.amigoscode.exception.RequestValidationException;
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
    void canGetCustomer() {
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
        assertThatThrownBy(() -> underTest.addCustomer(request))
                .isInstanceOf(DuplicateResourceException.class)
                .hasMessage("Email already taken");

        //THEN

        verify(customerDao, never()).insertCustomer(any());
    }

    @Test
    void deleteCustomerById() {
        //GIVEN
        int id = 10;

        when(customerDao.existsPersonWithId(id)).thenReturn(true);

        //WHEN
        underTest.deleteCustomerById(id);

        //THEN
        verify(customerDao).deleteCustomerById(id);
    }

    @Test
    void willThrowDeleteCustomerByIdNotExists() {
        //GIVEN
        int id = 10;

        when(customerDao.existsPersonWithId(id)).thenReturn(false);

        //WHEN
        assertThatThrownBy(
                () -> underTest.deleteCustomerById(id))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Customer with id [%s] not found".formatted(id)
                );

        //THEN
        verify(customerDao, never()).deleteCustomerById(id);
    }

    @Test
    void canUpdateAllCustomerProperties() {
        //GIVEN
        int id = 10;
        Customer customer = new Customer(
                id, "Alex", "alex@gmail.com", 33
        );
        when(customerDao.selectCustomerById(id)).thenReturn(Optional.of(customer));

        String newEmail = "alexandro@gmail.com";

        CustomerUpdateRequest updateRequest = new CustomerUpdateRequest(
                "Alexandro", newEmail, 23);

        when(customerDao.existsPersonWithEmail(newEmail)).thenReturn(false);

        //WHEN
        underTest.updateCustomer(id, updateRequest);

        //THEN
        ArgumentCaptor<Customer> customerArgumentCaptor =
                ArgumentCaptor.forClass(Customer.class);
        verify(customerDao).updateCustomer(customerArgumentCaptor.capture());
        Customer captureCustomer = customerArgumentCaptor.getValue();

        assertThat(captureCustomer.getAge()).isEqualTo(updateRequest.age());
        assertThat(captureCustomer.getEmail()).isEqualTo(updateRequest.email());
        assertThat(captureCustomer.getName()).isEqualTo(updateRequest.name());

    }

    @Test
    void canUpdateOnlyCustomerName() {
        //GIVEN
        int id = 10;
        Customer customer = new Customer(
                id, "Alex", "alex@gmail.com", 33
        );
        when(customerDao.selectCustomerById(id)).thenReturn(Optional.of(customer));

        String newEmail = "alexandro@gmail.com";

        CustomerUpdateRequest updateRequest = new CustomerUpdateRequest(
                "Alexandro", null, null);

        //WHEN
        underTest.updateCustomer(id, updateRequest);

        //THEN
        ArgumentCaptor<Customer> customerArgumentCaptor =
                ArgumentCaptor.forClass(Customer.class);
        verify(customerDao).updateCustomer(customerArgumentCaptor.capture());
        Customer captureCustomer = customerArgumentCaptor.getValue();

        assertThat(captureCustomer.getAge()).isEqualTo(customer.getAge());
        assertThat(captureCustomer.getEmail()).isEqualTo(customer.getEmail());
        assertThat(captureCustomer.getName()).isEqualTo(updateRequest.name());

    }

    @Test
    void canUpdateOnlyCustomerEmail() {
        //GIVEN
        int id = 10;
        Customer customer = new Customer(
                id, "Alex", "alex@gmail.com", 33
        );
        when(customerDao.selectCustomerById(id)).thenReturn(Optional.of(customer));

        String newEmail = "alexandro@gmail.com";

        CustomerUpdateRequest updateRequest = new CustomerUpdateRequest(
                null, newEmail, null);

        when(customerDao.existsPersonWithEmail(newEmail)).thenReturn(false);

        //WHEN
        underTest.updateCustomer(id, updateRequest);

        //THEN
        ArgumentCaptor<Customer> customerArgumentCaptor =
                ArgumentCaptor.forClass(Customer.class);
        verify(customerDao).updateCustomer(customerArgumentCaptor.capture());
        Customer captureCustomer = customerArgumentCaptor.getValue();

        assertThat(captureCustomer.getAge()).isEqualTo(customer.getAge());
        assertThat(captureCustomer.getEmail()).isEqualTo(updateRequest.email());
        assertThat(captureCustomer.getName()).isEqualTo(customer.getName());

    }

    @Test
    void canUpdateOnlyCustomerAge() {
        //GIVEN
        int id = 10;
        Customer customer = new Customer(
                id, "Alex", "alex@gmail.com", 33
        );
        when(customerDao.selectCustomerById(id)).thenReturn(Optional.of(customer));

        String newEmail = "alexandro@gmail.com";

        CustomerUpdateRequest updateRequest = new CustomerUpdateRequest(
                null, null, 23);

        //WHEN
        underTest.updateCustomer(id, updateRequest);

        //THEN
        ArgumentCaptor<Customer> customerArgumentCaptor =
                ArgumentCaptor.forClass(Customer.class);
        verify(customerDao).updateCustomer(customerArgumentCaptor.capture());
        Customer captureCustomer = customerArgumentCaptor.getValue();

        assertThat(captureCustomer.getAge()).isEqualTo(updateRequest.age());
        assertThat(captureCustomer.getEmail()).isEqualTo(customer.getEmail());
        assertThat(captureCustomer.getName()).isEqualTo(customer.getName());

    }

    @Test
    void willThrowWhenTryingToUpdateCustomerEmailWhenAlreadyTaken() {
        //GIVEN
        int id = 10;
        Customer customer = new Customer(
                id, "Alex", "alex@gmail.com", 33
        );
        when(customerDao.selectCustomerById(id)).thenReturn(Optional.of(customer));

        String newEmail = "alexandro@gmail.com";

        CustomerUpdateRequest updateRequest = new CustomerUpdateRequest(
                null, newEmail, null);

        when(customerDao.existsPersonWithEmail(newEmail)).thenReturn(true);

        //WHEN
        assertThatThrownBy(() -> underTest.updateCustomer(id, updateRequest))
                .isInstanceOf(DuplicateResourceException.class)
                .hasMessage("Email already taken");

        //THEN

        verify(customerDao, never()).updateCustomer(any());


    }

    @Test
    void willThrowWhenCustomerUpdateHasNoChanges() {
        //GIVEN
        int id = 10;
        Customer customer = new Customer(
                id, "Alex", "alex@gmail.com", 33
        );
        when(customerDao.selectCustomerById(id)).thenReturn(Optional.of(customer));

        CustomerUpdateRequest updateRequest = new CustomerUpdateRequest(
                customer.getName(), customer.getEmail(), customer.getAge());

        //WHEN
        assertThatThrownBy(() -> underTest.updateCustomer(id, updateRequest))
                .isInstanceOf(RequestValidationException.class)
                .hasMessage("no data changes found");

        //THEN
        verify(customerDao, never()).updateCustomer(any());

    }
}