package com.sas;

import com.sas.model.*;
import com.sas.repository.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@SpringBootApplication
public class Spring006MysqlApplication {

	public static void main(String[] args) {

		ApplicationContext context = SpringApplication.run(Spring006MysqlApplication.class, args);
		AuthorRepository repo = context.getBean((AuthorRepository.class));
		CategoryRepository categoryRepo = context.getBean((CategoryRepository.class));
		FilmRepository filmRepo = context.getBean((FilmRepository.class));

		Author a1 = new Author();
		a1.setName("John");
		a1.setEmail("john@j.com");
		a1.setAvailable(true);
		a1.setFechaNacimiento(LocalDate.parse("2000-01-01"));

		Author a2 = new Author();
		a2.setName("Jane");
		a2.setEmail("jane@j.com");
		a2.setAvailable(false);
		a2.setFechaNacimiento(LocalDate.parse("1999-11-11"));

		Author a3 = Author.builder().name("Johana").email("j0hana@j.com").available(true).fechaNacimiento(LocalDate.of(2001,8, 10)).build();

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

			List<Author> authors = repo.findAll();
			System.out.println(authors);

			Optional<Author> authorOpt = repo.findById(31L);
			System.out.println(authorOpt);
			if (authorOpt.isPresent()) {
				System.out.println(authorOpt.get());
			}
			else {
				System.out.println("No existe el author");
			}

			Author author = authorOpt.orElseThrow();
			System.out.println(author);

			authorOpt.ifPresentOrElse(System.out::println, ()->System.out.println("No existe el id"));

		} catch (Exception e) {
			System.out.println("Tengo un error: " + e.getMessage());
			//repo.deleteAll();
		}

			Category category1 = Category.builder().Id(null).name("Accion").min_age(18).build();
			Category category2 = Category.builder().Id(null).name("Drama").min_age(16).build();
			Category category3 = Category.builder().Id(null).name("Comedia").min_age(14).build();
			Category category4 = Category.builder().Id(null).name("Terror").min_age(12).build();

//			categoryRepo.save(category1);
//			categoryRepo.save(category2);
//			categoryRepo.save(category3);
//			categoryRepo.save(category4);

			categoryRepo.saveAll(List.of(category1, category2, category3, category4));

			Film film1 = Film.builder().Id(null)
					.title("El mago de oz")
					.duration(199)
					.releaseDate(LocalDate.of(1955, 05, 21))
					.categories(List.of(category1, category2)).build();

			Film film2 = Film.builder().Id(null)
					.title("Superman III")
					.duration(220)
					.releaseDate(LocalDate.of(1970, 02, 01))
					.categories(List.of(category2)).build();

			Film film3 = Film.builder().Id(null)
					.title("El Libro de la selva")
					.duration(184)
					.releaseDate(LocalDate.of(1985, 01, 02))
					.categories(List.of(category1, category4)).build();

			filmRepo.saveAll(List.of(film1, film2, film3));

			//repo.deleteAll();

		AddressRepository addressRepository = context.getBean(AddressRepository.class);
		Address ad1 = new Address(null, "Calle falsa", "Madrid");
		Address ad2 = new Address(null, "Calle prueba", "Madrid");
		Address ad3 = new Address(null, "Calle verdadera", "Madrid");
		Address ad4 = new Address(null, "Calle mala", "Madrid");
		addressRepository.saveAll(List.of(ad1, ad2, ad3, ad4));

		UserRepository userRepository = context.getBean(UserRepository.class);
		User user1 = new User(null, "u1@gmail.com", "admin", ad1);
		User user2 = new User(null, "u2@gmail.com", "admin", ad2);
		User user3 = new User(null, "u3@gmail.com", "admin", ad3);
		userRepository.saveAll(List.of(user1, user2, user3));

	}

}
