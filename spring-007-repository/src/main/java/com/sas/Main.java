package com.sas;

import com.sas.model.Book;
import com.sas.model.Status;
import com.sas.repository.BookRepository;
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

// 		Crear libres con for
//		List<Book> bookList = new ArrayList<>();
//		for (int i = 0; i < 10; i++) {
//			Book book1 = new Book(null, "Lib"+i,100,29.95,true,Status.DISCONTINUED, LocalDate.now());
//			bookList.add(book1);
//		}
//		repoBook.saveAll(bookList);

		Book book1 = Book.builder().title("Libro 1").numPages(198).price(23.55).available(true).status(Status.PUBLISHED).releaseDate(LocalDate.of(2000, 12, 10)).build();
		Book book2 = Book.builder().title("Libro 2").numPages(222).price(123.55).available(true).status(Status.CENSORED).releaseDate(LocalDate.of(2000, 12, 10)).build();
		Book book3 = Book.builder().title("Libro 3").numPages(333).price(223.55).available(false).status(Status.DRAFT).releaseDate(LocalDate.of(2000, 12, 10)).build();
		Book book4 = Book.builder().title("Libro 4").numPages(444).price(323.55).available(true).status(Status.DISCONTINUED).releaseDate(LocalDate.of(2000, 12, 10)).build();
		Book book5 = Book.builder().title("Libro 5").numPages(555).price(423.55).available(true).status(Status.PUBLISHED).releaseDate(LocalDate.of(2000, 12, 10)).build();
		Book book6 = Book.builder().title("Libro 6").numPages(666).price(523.55).available(true).status(Status.CENSORED).releaseDate(LocalDate.of(2000, 12, 10)).build();
		Book book7 = Book.builder().title("Libro 7").numPages(777).price(623.55).available(false).status(Status.PUBLISHED).releaseDate(LocalDate.of(2000, 12, 10)).build();
		Book book8 = Book.builder().title("Libro 8").numPages(888).price(723.55).available(true).status(Status.DRAFT).releaseDate(LocalDate.of(2000, 12, 10)).build();
		Book book9 = Book.builder().title("Libro 9").numPages(999).price(823.55).available(true).status(Status.PUBLISHED).releaseDate(LocalDate.of(2000, 12, 10)).build();
		Book book10 = Book.builder().title("Libro 10").numPages(1000).price(923.55).available(false).status(Status.DISCONTINUED).releaseDate(LocalDate.of(2000, 12, 10)).build();

		repoBook.saveAll(List.of(book1, book2, book3, book4, book5, book6, book7, book8, book9, book10));

		System.out.println(repoBook.findByReleaseDateBefore(LocalDate.of(2000, 12, 31)));
		System.out.println(repoBook.findByNumPagesLessThan(258));
		System.out.println(repoBook.findByNumPagesBetween(400, 700));


	}

}
