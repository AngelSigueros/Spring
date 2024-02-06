package com.sas;

import com.sas.model.Author;
import com.sas.repository.AuthorRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class Spring006MysqlApplication {

	public static void main(String[] args) {

		ApplicationContext context = SpringApplication.run(Spring006MysqlApplication.class, args);
		AuthorRepository repo = context.getBean((AuthorRepository.class));

		Author a1 = new Author();
		a1.setName("John");
		a1.setEmail("john@j.com");

		Author a2 = new Author();
		a2.setName("Jane");
		a2.setEmail("jane@j.com");

		Author a3 = Author.builder().name("Johana").email("j0hana@j.com").build();

		try {
			repo.save(a1);
			repo.save(a2);
			repo.save(a3);

			//repo.findById(2L).ifPresent(System.out::println).orElse(null);
			repo.findById(5L).ifPresentOrElse(System.out::println, ()->System.out.println("No existe el id"));
			repo.findAll().forEach(System.out::println);

			System.out.println("Total: " + repo.count());
			System.out.println("Total: " + repo.findAll().size());
			String string = repo.findAll().get(2).toString();
			System.out.println(string);
			System.out.println(repo.findAll().getFirst().getId());
			System.out.println(repo.findAll().get(2).getName() + " " + repo.findAll().get(2).getEmail());

			repo.deleteAll();

		} catch (Exception e) {
			System.out.println("Tengo un error: " + e.getMessage());
		}


	}

}
