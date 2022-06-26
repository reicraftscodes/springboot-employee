package com.reicraftscodes.springbootemployee.controller;

import com.reicraftscodes.springbootemployee.models.Employee;
import com.reicraftscodes.springbootemployee.dto.EmployeeRequest;
import com.reicraftscodes.springbootemployee.dto.EmployeeResponse;
import com.reicraftscodes.springbootemployee.mapper.EmployeeMapper;
import com.reicraftscodes.springbootemployee.services.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeesController {

    private final EmployeeMapper employeeMapper;
    private final EmployeeService employeeService;

    public EmployeesController(EmployeeMapper employeeMapper, EmployeeService employeeService) {
        this.employeeMapper = employeeMapper;
        this.employeeService = employeeService;
    }

    @GetMapping
    public List<EmployeeResponse> getAll() {
        return employeeMapper.toResponseList(employeeService.getAll());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EmployeeResponse create(@RequestBody EmployeeRequest employeeRequest) {
        Employee employee = employeeMapper.toEntity(employeeRequest);
        Employee createdEmployee = employeeService.create(employee);
        return employeeMapper.toResponse(createdEmployee);
    }

    @GetMapping("/{employeeId}")
    public EmployeeResponse searchById(@PathVariable("employeeId") Integer employeeId) {
        return employeeMapper.toResponse(employeeService.searchById(employeeId));
    }

    @PutMapping("/{employeeId}")
    public EmployeeResponse update(@PathVariable("employeeId") Integer employeeId, @RequestBody EmployeeRequest employeeRequest) {
        Employee employee = employeeMapper.toEntity(employeeRequest);
        Employee updatedEmployee = employeeService.update(employeeId, employee);
        return employeeMapper.toResponse(updatedEmployee);
    }

    @DeleteMapping("/{employeeId}")
    public void delete(@PathVariable("employeeId") Integer employeeId) {
        employeeService.delete(employeeId);
    }

    @GetMapping(params = "gender")
    public List<EmployeeResponse> getByGender(@RequestParam("gender") String gender) {
        return employeeMapper.toResponseList(employeeService.searchByGender(gender));
    }

    @GetMapping(params = {"page", "pageSize"})
    public List<EmployeeResponse> getByEmployeeByPage(@RequestParam("page") Integer page,
                                                      @RequestParam("pageSize") Integer pageSize) {
        return employeeMapper.toResponseList(employeeService.getEmployeeByPageAndPageSize(page, pageSize));
    }
}
