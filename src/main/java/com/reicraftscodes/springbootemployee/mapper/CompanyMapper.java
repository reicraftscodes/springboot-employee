package com.reicraftscodes.springbootemployee.mapper;

import com.reicraftscodes.springbootemployee.dto.CompanyRequest;
import com.reicraftscodes.springbootemployee.dto.CompanyResponse;
import com.reicraftscodes.springbootemployee.models.Company;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.Collections.*;

@Component
public class CompanyMapper {

    public Company toEntity(CompanyRequest companyRequest) {
        Company company = new Company();
        BeanUtils.copyProperties(companyRequest, company);
        return company;
    }

    public CompanyResponse toResponse(Company company) {
        CompanyResponse companyResponse = new CompanyResponse();
        BeanUtils.copyProperties(company, companyResponse);
        boolean hasEmployee = company.getEmployees() != null;
        companyResponse.setEmployeesNumber(hasEmployee ? company.getEmployees().size() : 0);
        companyResponse.setEmployees(hasEmployee ? company.getEmployees() : EMPTY_LIST);
        return companyResponse;
    }

    public List<CompanyResponse> toResponseList(List<Company> companies) {
        return companies.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }
}
