package com.reicraftscodes.springbootemployee.integration;

import com.reicraftscodes.springbootemployee.models.Employee;
import com.reicraftscodes.springbootemployee.repository.IEmployeeRepository;
import com.reicraftscodes.springbootemployee.services.EmployeeService;
import org.junit.jupiter.api.Test;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

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
}