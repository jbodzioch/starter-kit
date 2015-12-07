package pl.spring.demo.to;

import java.util.ArrayList;
import java.util.List;

public class BookEntity implements IdAware {
	private Long id;
	private String title;
	private List<AuthorTo> authors = new ArrayList<AuthorTo>();

	public BookEntity() {
		id = null;
		title = null;
		authors.add(setEmptyAuthor());
	}

	public BookEntity(Long id, String title, String authors) {
		this.id = id;
		this.title = title;
		this.authors = splitAuthors(authors);
	}

	private List<AuthorTo> splitAuthors(String authors) {

		List<AuthorTo> list = new ArrayList<AuthorTo>();
		long counter = 1L;

		if (authors != null) {
			String[] names = authors.split(" ");
			for (int i = 0; i < names.length; i += 2) {
				if (i + 1 < names.length) {
					list.add(new AuthorTo(counter, names[i], names[i + 1]));
					counter = counter + 1L;
				} else {
					list.add(new AuthorTo(counter, names[i], null));
					counter = counter + 1L;
				}
			}
		} else {
			list.add(setEmptyAuthor());
		}

		return list;
	}

	private AuthorTo setEmptyAuthor() {
		return new AuthorTo(0L, null, null);
	}

	@Override
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<AuthorTo> getAuthors() {
		return authors;
	}

	public void setAuthors(List<AuthorTo> authors) {
		this.authors = authors;
	}
}
