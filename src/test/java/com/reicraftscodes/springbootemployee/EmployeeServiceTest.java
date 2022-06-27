package com.reicraftscodes.springbootemployee;

import com.reicraftscodes.springbootemployee.models.Employee;
import com.reicraftscodes.springbootemployee.repository.IEmployeeRepository;
import com.reicraftscodes.springbootemployee.services.EmployeeService;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static java.util.Optional.of;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EmployeeServiceTest {

    @Test
    void should_return_2_when_get_employees_given_2_employees() {
        //given
        IEmployeeRepository repository = mock(IEmployeeRepository.class);
        when(repository.findAll()).thenReturn(asList(new Employee(), new Employee()));
        EmployeeService employeeService = new EmployeeService(repository);

        //when
        Integer employeeCount = employeeService.getAll().size();

        //then
        assertEquals(2, employeeCount);
    }

    @Test
    void should_return_employee_with_id_1_when_create_given_employee_with_id_of_1() {
        //given
        Employee newEmployee = new Employee("", 20, "male", 1000);
        newEmployee.setId(1);
        IEmployeeRepository repository = mock(IEmployeeRepository.class);
        when(repository.save(newEmployee)).thenReturn(newEmployee);
        EmployeeService employeeService = new EmployeeService(repository);

        //when
        Employee employee = employeeService.create(newEmployee);

        //then
        assertEquals(1, employee.getId());
    }

    @Test
    void should_return_employee_when_searchById_given_employee_with_id_of_1() {
        //given
        Employee employee = new Employee("", 20, "male", 1000);
        IEmployeeRepository repository = mock(IEmployeeRepository.class);
        when(repository.findById(employee.getId())).thenReturn(of(employee));
        EmployeeService employeeService = new EmployeeService(repository);

        //when
        Employee fetchedEmployee = employeeService.searchById(employee.getId());

        //then
        assertNotNull(fetchedEmployee);
        assertSame(employee, fetchedEmployee);
    }

    @Test
    void should_return_updated_employee_when_update_given_employee_and_field_updates() {
        //given
        Employee employee = new Employee("", 20, "male", 1000);
        employee.setId(1);
        Employee expectedEmployee = new Employee("Cedric", 19, "female", 6600);
        IEmployeeRepository repository = mock(IEmployeeRepository.class);

        Optional<Employee> optionalEmployee = of(expectedEmployee);
        when(repository.findById(employee.getId())).thenReturn(optionalEmployee);
        when(repository.save(optionalEmployee.get())).thenReturn(expectedEmployee);
        EmployeeService employeeService = new EmployeeService(repository);

        //when
        Employee updatedEmployee = employeeService.update(employee.getId(), employee);

        //then
        assertSame(expectedEmployee, updatedEmployee);
    }

    @Test
    void should_trigger_repository_delete_once_when_service_delete_called_given_1_employee() {
        //given
        Employee employee = new Employee("", 20, "male", 1000);
        employee.setId(1);
        IEmployeeRepository repository = mock(IEmployeeRepository.class);
        when(repository.findById(employee.getId())).thenReturn(of(employee));

        EmployeeService employeeService = new EmployeeService(repository);
        //when
        employeeService.delete(employee.getId());
        //then
        verify(repository, times(1)).delete(employee);
    }

    @Test
    void should_return_2_male_employee_when_getByGender_given_2_male() {
        //given
        Employee firstEmployee = new Employee("Cedric", 20, "male", 1000);
        Employee secondEmployee = new Employee("Jaycee", 20, "male", 1000);
        IEmployeeRepository repository = mock(IEmployeeRepository.class);
        when(repository.findByGender("male")).thenReturn(asList(firstEmployee, secondEmployee));
        EmployeeService employeeService = new EmployeeService(repository);

        //when
        List<Employee> employees = employeeService.searchByGender("male");

        //then
        assertEquals(2, employees.size());
    }

    @Test
    void should_return_2_employees_when_getByEmployeeByPage_given_2_employees_page_1_and_pageSize_2() {
        //given
        Employee firstEmployee = new Employee("Cedric", 20, "male", 1000);
        Employee secondEmployee = new Employee("Jaycee", 20, "male", 1000);
        int page = 1, pageSize = 2;
        Pageable pageable = PageRequest.of(page - 1, pageSize);
        IEmployeeRepository repository = mock(IEmployeeRepository.class);
        Page<Employee> mockPage = mock(Page.class);
        when(repository.findAll(pageable)).thenReturn(mockPage);
        when(repository.findAll(pageable).toList()).thenReturn(asList(firstEmployee, secondEmployee));
        EmployeeService employeeService = new EmployeeService(repository);

        //when
        List<Employee> fetchedEmployees = employeeService.getEmployeeByPageAndPageSize(page, pageSize);

        //then
        assertEquals(2, fetchedEmployees.size());

    }

    @Test
    void should_return_2_employees_when_getByEmployeeByPage_given_2_employees_page_1_and_pageSize_5() {
        //given
        Employee firstEmployee = new Employee("Cedric", 20, "male", 1000);
        Employee secondEmployee = new Employee("Jaycee", 20, "male", 1000);
        IEmployeeRepository repository = mock(IEmployeeRepository.class);

        int page = 1, pageSize = 5;
        Pageable pageable = PageRequest.of(page - 1, pageSize);
        Page<Employee> mockPage = mock(Page.class);
        when(repository.findAll(pageable)).thenReturn(mockPage);
        when(repository.findAll(pageable).toList()).thenReturn(asList(firstEmployee, secondEmployee));
        EmployeeService employeeService = new EmployeeService(repository);

        //when
        List<Employee> fetchedEmployees = employeeService.getEmployeeByPageAndPageSize(page, pageSize);

        //then
        assertEquals(2, fetchedEmployees.size());
    }

    @Test
    void should_return_third_employee_when_getByEmployeeByPage_given_3_employees_page_2_and_pageSize_2() {
        //given
        Employee firstEmployee = new Employee("Cedric", 20, "male", 1000);
        Employee secondEmployee = new Employee("Jaycee", 20, "male", 1000);
        Employee thirdEmployee = new Employee("Shana", 20, "male", 1000);
        int page = 2, pageSize = 2;
        Pageable pageable = PageRequest.of(page - 1, pageSize);
        IEmployeeRepository repository = mock(IEmployeeRepository.class);
        when(repository.findAll()).thenReturn(asList(firstEmployee, secondEmployee, thirdEmployee));

        Page mockPage = mock(Page.class);
        when(mockPage.toList()).thenReturn(singletonList(thirdEmployee));
        when(repository.findAll(pageable)).thenReturn(mockPage);

        EmployeeService employeeService = new EmployeeService(repository);

        //when
        List<Employee> fetchedEmployees = employeeService.getEmployeeByPageAndPageSize(page, pageSize);

        //then
        assertEquals(1, fetchedEmployees.size());
        assertSame(thirdEmployee, fetchedEmployees.get(0));
    }

    @Test
    void should_return_0_employee_when_getByEmployeeByPage_given_0_employees_page_1_and_pageSize_2() {
        //given
        int page = 1, pageSize = 2;
        Pageable pageable = PageRequest.of(page - 1, pageSize);
        IEmployeeRepository repository = mock(IEmployeeRepository.class);
        Page<Employee> mockPage = mock(Page.class);
        when(repository.findAll(pageable)).thenReturn(mockPage);
        when(repository.findAll(pageable).toList()).thenReturn(emptyList());
        EmployeeService employeeService = new EmployeeService(repository);

        //when
        List<Employee> fetchedEmployees = employeeService.getEmployeeByPageAndPageSize(page, pageSize);

        //then
        assertEquals(0, fetchedEmployees.size());
    }
}