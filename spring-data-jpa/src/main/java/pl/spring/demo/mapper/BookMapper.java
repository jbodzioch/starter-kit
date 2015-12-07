package pl.spring.demo.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import pl.spring.demo.to.AuthorTo;
import pl.spring.demo.to.BookEntity;
import pl.spring.demo.to.BookTo;

@Component
public class BookMapper {

	public List<BookEntity> to2EntityList(List<BookTo> book) {

		List<BookEntity> entities = new ArrayList<BookEntity>();

		for (BookTo to : book) {
			entities.add(to2Entity(to));
		}

		return entities;
	}

	public BookEntity to2Entity(BookTo book) {

		BookEntity entity = new BookEntity(book.getId(), book.getTitle(), book.getAuthors());

		return entity;
	}

	public BookTo entity2To(BookEntity book) {

		BookTo to = new BookTo(book.getId(), book.getTitle(), listToString(book));

		return to;
	}

	public List<BookTo> entity2ToList(List<BookEntity> book) {

		List<BookTo> to = new ArrayList<BookTo>();

		for (BookEntity entity : book) {
			to.add(entity2To(entity));
		}

		return to;
	}

	private String listToString(BookEntity book) {

		String result = "";

		for (AuthorTo author : book.getAuthors()) {
			result = result + author.getFirstName() + " ";
			if (author.getLastName() != null) {
				result = result + author.getLastName() + " ";
			}
		}

		result = result.substring(0, result.length() - 1);

		return result;
	}

}
