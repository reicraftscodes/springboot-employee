package com.reicraftscodes.springbootemployee.services;

import com.reicraftscodes.springbootemployee.exceptions.EmployeeNotFoundException;
import com.reicraftscodes.springbootemployee.exceptions.InvalidEmployeeException;
import com.reicraftscodes.springbootemployee.models.Employee;
import com.reicraftscodes.springbootemployee.repository.IEmployeeRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.Objects.isNull;

@Service
public class EmployeeService {

    private IEmployeeRepository employeeRepository;

    public EmployeeService(IEmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public List<Employee> getAll() {
        return employeeRepository.findAll();
    }

    public Employee create(Employee newEmployee) {
        validateEmployee(newEmployee);
        return employeeRepository.save(newEmployee);
    }

    public Employee searchById(Integer id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException("Employee with id:" + id + " not found"));
    }

    public Employee update(Integer id, Employee employeeUpdate) {
        validateEmployee(employeeUpdate);
        Employee employee = searchById(id);
        employee.setSalary(employeeUpdate.getSalary());
        employee.setAge(employeeUpdate.getAge());
        employee.setGender(employeeUpdate.getGender());
        employee.setName(employeeUpdate.getName());
        return employeeRepository.save(employee);
    }

    private void validateEmployee(Employee employee) {
        if (isNull(employee.getGender())
                || isNull(employee.getAge())
                || isNull(employee.getSalary())
                || isNull(employee.getName())) {
            throw new InvalidEmployeeException("Employee given has null fields!");
        }
    }

    public void delete(Integer id) {
        employeeRepository.delete(searchById(id));
    }

    public List<Employee> searchByGender(String gender) {
        return employeeRepository.findByGender(gender);
    }

    public List<Employee> getEmployeeByPageAndPageSize(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page - 1, pageSize);

        return employeeRepository.findAll(pageable).toList();
    }
}
