package com.sas.repository;

import com.sas.model.Company;
import com.sas.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
/*
Repositorio con métodos derivados para practicar consultas sobre la entidad Employee con ASOCIACIONES
 */
public interface EmployeeRepository extends JpaRepository<Employee, Long> {


    // DERIDED METHODS
    List<Employee> findByCompany_CityIgnoreCaseOrderBySalaryDesc(String city);
    List<Employee> findByCompany_CityAndCompany_PostalCode(String city, String postalCode);
    List<Employee> findByProjects_NameContains(String name);




    // QUERY METHODS: JPQL JAKARTA PERSISTENCE QUERY LANGUAGE, es un lenguaje similar a SQL pero orientado a objetos de Java
    @Query("select e from Employee e where e.company.name = ?1")
    List<Employee> findByCompany_Name(String name);

    @Query("""
            select e from Employee e inner join e.projects projects
            where e.salary >= ?1 and e.company.city = ?2 and projects.deadlineDate <= ?3""")
    List<Employee> findBySalaryGreaterThanEqualAndCompany_CityAndProjects_DeadlineDateLessThanEqual(Double salary, String city, LocalDate deadlineDate);

    // Las asociaciones Many como projects dentro Employee se cargan LAZY, es decir, no se cargan por defecto
    // pero podemos cargaras con una consulta personalizada como esta:
    @Query("""
            select e from Employee e join fetch e.projects
            """)
    List<Employee> findAllWithProjects();

    @Query("select e from Employee e where e.company = ?1")
    Employee findByCompany(Company company);


    @Query("select e from Employee e where e.company.name = ?1 and e.company.postalCode = ?2")
    Employee findByCompany_NameAndCompany_PostalCode(String name, String postalCode);


    List<Employee> findByCompany_City(String companyCity);

    List<Employee> findByCompany_PostalCode(String postalCode);

}
