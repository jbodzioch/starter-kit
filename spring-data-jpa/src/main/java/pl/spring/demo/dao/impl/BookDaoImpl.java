package pl.spring.demo.dao.impl;

import pl.spring.demo.annotation.NullableId;
import pl.spring.demo.common.Sequence;
import pl.spring.demo.dao.BookDao;
import pl.spring.demo.to.AuthorTo;
import pl.spring.demo.to.BookEntity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("bookDao")
public class BookDaoImpl implements BookDao {

	private final Set<BookEntity> ALL_BOOKS = new HashSet<>();

	public BookDaoImpl() {
	}

	@PostConstruct
	public void setUp() {
		addTestBooks();
	}

	@Override
	public List<BookEntity> findAll() {
		return new ArrayList<>(ALL_BOOKS);
	}

	@Override
	public List<BookEntity> findBookByTitle(String title) {

		List<BookEntity> tempBooks = new ArrayList<BookEntity>(ALL_BOOKS);
		List<BookEntity> result = new ArrayList<BookEntity>();

		for (BookEntity entity : tempBooks) {
			if (checkTitle(entity, title)) {
				result.add(entity);
			}
		}

		return result;
	}

	private boolean checkTitle(BookEntity entity, String title) {

		boolean result = false;

		if (entity.getTitle().length() == title.length()) {
			if (entity.getTitle().equalsIgnoreCase(title)) {
				result = true;
			}
		} else if (entity.getTitle().length() > title.length()) {
			if (entity.getTitle().substring(0, title.length()).equalsIgnoreCase(title)) {
				result = true;
			}
		}

		return result;
	}

	@Override
	public List<BookEntity> findBooksByAuthor(String author) {

		List<BookEntity> tempBooks = new ArrayList<BookEntity>(ALL_BOOKS);
		List<BookEntity> result = new ArrayList<BookEntity>();

		for (BookEntity entity : tempBooks) {
			if (checkFirstName(entity, author) || checkLastName(entity, author)) {
				result.add(entity);
			}
		}

		return result;
	}

	private boolean checkFirstName(BookEntity entity, String author) {

		boolean result = false;

		for (AuthorTo thisAuthor : entity.getAuthors()) {
			if (thisAuthor.getFirstName().length() == author.length()) {
				if (thisAuthor.getFirstName().equalsIgnoreCase(author)) {
					result = true;
				}
			} else if (thisAuthor.getFirstName().length() > author.length()) {
				if (thisAuthor.getFirstName().substring(0, author.length()).equalsIgnoreCase(author)) {
					result = true;
				}
			}
		}

		return result;
	}

	private boolean checkLastName(BookEntity entity, String author) {

		boolean result = false;

		if (entity.getAuthors().get(0).getLastName() != null) {
			for (AuthorTo thisAuthor : entity.getAuthors()) {
				if (thisAuthor.getLastName().length() == author.length()) {
					if (thisAuthor.getLastName().equalsIgnoreCase(author)) {
						result = true;
					}
				} else if (thisAuthor.getLastName().length() > author.length()) {
					if (thisAuthor.getLastName().substring(0, author.length()).equalsIgnoreCase(author)) {
						result = true;
					}
				}
			}
		}

		return result;
	}

	@Override
	@NullableId
	public BookEntity save(BookEntity book) {
		ALL_BOOKS.add(book);
		return book;
	}

	private void addTestBooks() {
		ALL_BOOKS.add(new BookEntity(1L, "Romeo i Julia", "Wiliam Szekspir"));
		ALL_BOOKS.add(new BookEntity(2L, "Opium w rosole", "Hanna Ożogowska"));
		ALL_BOOKS.add(new BookEntity(3L, "Przygody Odyseusza", "Jan Parandowski"));
		ALL_BOOKS.add(new BookEntity(4L, "Awantura w Niekłaju", "Edmund Niziurski"));
		ALL_BOOKS.add(new BookEntity(5L, "Pan Samochodzik i Fantomas", "Zbigniew Nienacki"));
		ALL_BOOKS.add(new BookEntity(6L, "Zemsta", "Aleksander Fredro"));
	}

}
