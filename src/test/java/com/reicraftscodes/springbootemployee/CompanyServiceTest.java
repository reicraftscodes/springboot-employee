package com.reicraftscodes.springbootemployee;

import com.reicraftscodes.springbootemployee.models.Company;
import com.reicraftscodes.springbootemployee.models.Employee;
import com.reicraftscodes.springbootemployee.repository.ICompanyRepository;
import com.reicraftscodes.springbootemployee.services.CompanyService;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CompanyServiceTest {

    @Test
    void should_return_2_companies_when_get_companies_given_2_companies() {
        //given
        ICompanyRepository companyRepository = mock(ICompanyRepository.class);

        List<Employee> employeeList = Arrays.asList(
                new Employee("nelly", 18, "female", 10),
                new Employee("nelly", 18, "male", 10),
                new Employee("nelly", 18, "male", 10));

        List<Employee> employeeList2 = Arrays.asList(
                new Employee("nelly", 18, "female", 10),
                new Employee("nelly", 18, "male", 10),
                new Employee("nelly", 18, "male", 10));

        when(companyRepository.findAll()).thenReturn(asList(new Company("OOCL", employeeList),
                new Company("SM", employeeList2)));

        CompanyService companyService = new CompanyService(companyRepository);

        //when
        Integer companyCount = companyService.getAll().size();

        //then
        assertEquals(2, companyCount);
    }
}
