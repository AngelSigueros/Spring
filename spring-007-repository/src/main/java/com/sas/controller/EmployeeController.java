package com.sas.controller;

import com.sas.model.Employee;
import com.sas.repository.EmployeeRepository;
import org.springframework.data.domain.Example;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

    private final EmployeeRepository repoEmployee;

    public EmployeeController(EmployeeRepository repoEmployee) {
        this.repoEmployee = repoEmployee;
    }

    @GetMapping
    public List<Employee> findAll() {
        return repoEmployee.findAll();
    }

    @GetMapping("/employees")
    public List<Employee> findEmployees(
            @RequestParam(required = false) String companyCity,
            @RequestParam(required = false) String postalCode) {

        if (StringUtils.hasLength(companyCity) && StringUtils.hasLength(postalCode))
            return repoEmployee.findByCompany_CityAndCompany_PostalCode(companyCity, postalCode);
        else if (StringUtils.hasLength(companyCity))
            return repoEmployee.findByCompany_City(companyCity);
        else if (StringUtils.hasLength(postalCode))
            return repoEmployee.findByCompany_PostalCode(postalCode);
        else
            return repoEmployee.findAll();
    }

    @PostMapping("employees/filter")
    public List<Employee> findAllFiltering(@RequestBody Employee employee) {
        Example<Employee> filter = Example.of(employee);
        return repoEmployee.findAll(filter);
    }

}
