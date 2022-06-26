package com.reicraftscodes.springbootemployee.services;

import com.reicraftscodes.springbootemployee.exceptions.CompanyNotFoundException;
import com.reicraftscodes.springbootemployee.exceptions.InvalidCompanyException;
import com.reicraftscodes.springbootemployee.repository.ICompanyRepository;
import com.reicraftscodes.springbootemployee.models.Company;
import com.reicraftscodes.springbootemployee.models.Employee;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.Objects.isNull;

@Service
public class CompanyService {
    private ICompanyRepository companyRepository;

    public CompanyService(ICompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    public List<Company> getAll() {
        return companyRepository.findAll();
    }

    public Company create(Company newCompany) {
        validateCompany(newCompany);
        return companyRepository.save(newCompany);
    }

    public Company searchById(Integer id) {
        return companyRepository.findById(id)
                .orElseThrow(() -> new CompanyNotFoundException("Company with id:" + id + " not found"));
    }

    public List<Employee> getEmployeesByCompanyId(Integer id) {
        Company company = searchById(id);
        return company.getEmployees();
    }

    public Company update(Integer id, Company updatedCompany) {
        validateCompany(updatedCompany);
        Company company = searchById(id);
        company.setCompanyName(updatedCompany.getCompanyName());
        return companyRepository.save(company);
    }

    public void delete(Integer id) {
        companyRepository.delete(searchById(id));
    }

    public List<Company> getCompaniesByPageAndPageSize(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page - 1, pageSize);
        return companyRepository.findAll(pageable).toList();
    }

    private void validateCompany(Company company) {
        if (isNull(company.getCompanyName())) {
            throw new InvalidCompanyException("Company given has null fields!");
        }
    }
}
