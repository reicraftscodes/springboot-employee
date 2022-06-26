package com.reicraftscodes.springbootemployee.controller;

import com.reicraftscodes.springbootemployee.models.Company;
import com.reicraftscodes.springbootemployee.services.CompanyService;
import com.reicraftscodes.springbootemployee.dto.CompanyRequest;
import com.reicraftscodes.springbootemployee.dto.CompanyResponse;
import com.reicraftscodes.springbootemployee.dto.EmployeeResponse;
import com.reicraftscodes.springbootemployee.mapper.CompanyMapper;
import com.reicraftscodes.springbootemployee.mapper.EmployeeMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/companies")
public class CompaniesController {

    private final CompanyMapper companyMapper;
    private final EmployeeMapper employeeMapper;
    private final CompanyService companyService;

    public CompaniesController(CompanyMapper companyMapper, EmployeeMapper employeeMapper, CompanyService companyService) {
        this.companyMapper = companyMapper;
        this.employeeMapper = employeeMapper;
        this.companyService = companyService;
    }

    @GetMapping
    public List<CompanyResponse> getAll() {
        return companyMapper.toResponseList(companyService.getAll());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CompanyResponse create(@RequestBody CompanyRequest companyRequest) {
        Company company = companyMapper.toEntity(companyRequest);
        Company createdCompany = companyService.create(company);
        return companyMapper.toResponse(createdCompany);
    }

    @GetMapping("/{companyId}")
    public CompanyResponse searchById(@PathVariable("companyId") Integer companyId) {
        return companyMapper.toResponse(companyService.searchById(companyId));
    }

    @GetMapping("/{companyId}/employees")
    public List<EmployeeResponse> getEmployeesByCompanyId(@PathVariable("companyId") Integer companyId) {
        return employeeMapper.toResponseList(companyService.getEmployeesByCompanyId(companyId));
    }

    @PutMapping("/{companyId}")
    public CompanyResponse update(@PathVariable("companyId") Integer companyId, @RequestBody CompanyRequest companyRequest) {
        Company company = companyMapper.toEntity(companyRequest);
        Company updatedCompany = companyService.update(companyId, company);
        return companyMapper.toResponse(updatedCompany);
    }

    @DeleteMapping("/{companyId}")
    public void delete(@PathVariable("companyId") Integer companyId) {
        companyService.delete(companyId);
    }

    @GetMapping(params = {"page", "pageSize"})
    public List<CompanyResponse> getCompaniesByPageAndPageSize(@RequestParam("page") Integer page,
                                                       @RequestParam("pageSize") Integer pageSize) {
        return companyMapper.toResponseList(companyService.getCompaniesByPageAndPageSize(page, pageSize));
    }
}
