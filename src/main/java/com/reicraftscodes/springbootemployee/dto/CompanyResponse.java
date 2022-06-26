package com.reicraftscodes.springbootemployee.dto;

import com.reicraftscodes.springbootemployee.models.Employee;

import java.util.List;

public class CompanyResponse {
    private Integer id;
    private String companyName;
    private List<Employee> employees;
    private Integer employeesNumber;

    public CompanyResponse() {
    }

    public CompanyResponse(Integer id, String companyName, List<Employee> employees, Integer employeesNumber) {
        this.id = id;
        this.companyName = companyName;
        this.employees = employees;
        this.employeesNumber = employeesNumber;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    public Integer getEmployeesNumber() {
        return employeesNumber;
    }

    public void setEmployeesNumber(Integer employeesNumber) {
        this.employeesNumber = employeesNumber;
    }
}
