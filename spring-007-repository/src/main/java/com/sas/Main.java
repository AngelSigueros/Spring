package com.sas;

import com.sas.model.*;
import com.sas.repository.BookRepository;
import com.sas.repository.CompanyRepository;
import com.sas.repository.EmployeeRepository;
import com.sas.repository.ProjectRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.time.LocalDate;
import java.util.List;

@SpringBootApplication
public class Main {

	public static void main(String[] args) {

		ApplicationContext context = SpringApplication.run(Main.class, args);
		BookRepository repoBook = context.getBean(BookRepository.class);
		EmployeeRepository employeeRepo = context.getBean(EmployeeRepository.class);
		CompanyRepository companyRepo = context.getBean(CompanyRepository.class);
		ProjectRepository projectRepo = context.getBean(ProjectRepository.class);

// 		Crear libres con for
//		List<Book> bookList = new ArrayList<>();
//		for (int i = 0; i < 10; i++) {
//			Book book1 = new Book(null, "Lib"+i,100,29.95,true,Status.DISCONTINUED, LocalDate.now());
//			bookList.add(book1);
//		}
//		repoBook.saveAll(bookList);

		Book book1 = Book.builder().title("Libro 1").numPages(198).price(23.55).available(true).status(Status.PUBLISHED).publishDate(LocalDate.of(2000, 12, 10)).build();
		Book book2 = Book.builder().title("Libro 2").numPages(222).price(123.55).available(true).status(Status.CENSORED).publishDate(LocalDate.of(2000, 12, 10)).build();
		Book book3 = Book.builder().title("Libro 3").numPages(333).price(223.55).available(false).status(Status.DRAFT).publishDate(LocalDate.of(2000, 12, 10)).build();
		Book book4 = Book.builder().title("Libro 4").numPages(444).price(323.55).available(true).status(Status.DISCONTINUED).publishDate(LocalDate.of(2000, 12, 10)).build();
		Book book5 = Book.builder().title("Libro 5").numPages(555).price(423.55).available(true).status(Status.PUBLISHED).publishDate(LocalDate.of(2000, 12, 10)).build();
		Book book6 = Book.builder().title("Libro 6").numPages(666).price(523.55).available(true).status(Status.CENSORED).publishDate(LocalDate.of(2000, 12, 10)).build();
		Book book7 = Book.builder().title("Libro 7").numPages(777).price(623.55).available(false).status(Status.PUBLISHED).publishDate(LocalDate.of(2000, 12, 10)).build();
		Book book8 = Book.builder().title("Libro 8").numPages(888).price(723.55).available(true).status(Status.DRAFT).publishDate(LocalDate.of(2000, 12, 10)).build();
		Book book9 = Book.builder().title("Libro 9").numPages(999).price(823.55).available(true).status(Status.PUBLISHED).publishDate(LocalDate.of(2000, 12, 10)).build();
		Book book10 = Book.builder().title("Libro 10").numPages(1000).price(923.55).available(false).status(Status.DISCONTINUED).publishDate(LocalDate.of(2000, 12, 10)).build();

		repoBook.saveAll(List.of(book1, book2, book3, book4, book5, book6, book7, book8, book9, book10));

		System.out.println(repoBook.findByPublishDateBefore(LocalDate.of(2000, 12, 31)));
		System.out.println(repoBook.findByNumPagesLessThan(258));
		System.out.println(repoBook.findByNumPagesBetween(400, 700));

		System.out.println(repoBook.findByTitleIgnoreCase("Libro 1"));
		System.out.println(repoBook.getByTitleIgnoreCase("Libro 2"));

		Company comp1 = new Company(null, "Adecco", "Madrid", "28001");
		Company comp2 = new Company(null, "CertiDevs", "Madrid", "28002");
		companyRepo.saveAll(List.of(comp1, comp2));

		Project proj1 = new Project(null, "PROJ1", true, null);
		Project proj2 = new Project(null, "PROJ2", true, null);
		Project proj3 = new Project(null, "PROJ3", false, null);
		Project proj4 = new Project(null, "PROJ4", true, null);
		projectRepo.saveAll(List.of(proj1, proj2, proj3, proj4));

		Employee emp1 = new Employee();
		emp1.setEmail("emp1@gmail.com");
		emp1.setSalary(2000.0);
		emp1.setCompany(comp1);
		emp1.getProjects().add(proj1);
		emp1.getProjects().add(proj3);

		Employee emp2 = new Employee();
		emp2.setEmail("emp2@gmail.com");
		emp2.setSalary(3000.0);
		emp2.setCompany(comp2);
		emp2.getProjects().add(proj1);
		emp2.getProjects().add(proj2);
		emp2.getProjects().add(proj4);

		employeeRepo.saveAll(List.of(emp1, emp2));

		System.out.println("findByCompany_Name");
		System.out.println(employeeRepo.findByCompany_Name("Adecco"));

		System.out.println("findByCompany_CityAndCompany_PostalCode");
		System.out.println(employeeRepo.findByCompany_CityAndCompany_PostalCode("Madrid", "28002"));


		// los projects son una asociación MANY, que es LAZY por defecto, por tanto no vienen los proyectos por defecto:
		// Error LazyInitializationException
		// System.out.println(employeeRepo.findByCompany_CityAndCompany_PostalCode("Madrid", "28002").getFirst().getProjects());

		// Solución: consulta QUERY para traer los empleados con sus respectivos proyectos en la misma query
		System.out.println(employeeRepo.findAllWithProjects().getFirst().getProjects());

	}

}
