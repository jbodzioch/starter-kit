package pl.spring.demo.service.impl;

import pl.spring.demo.dao.BookDao;
import pl.spring.demo.mapper.BookMapper;
import pl.spring.demo.service.BookService;
import pl.spring.demo.to.BookTo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("bookService")
public class BookServiceImpl implements BookService {

	private BookDao bookDao;

	private BookMapper bookMapper;

	@Autowired
	public void setBookDao(BookDao bookDao) {
		this.bookDao = bookDao;
	}

	@Autowired
	public void setBookMapper(BookMapper bookMapper) {
		this.bookMapper = bookMapper;
	}

	@Override
	public List<BookTo> findAllBooks() {
		return bookMapper.entity2ToList(bookDao.findAll());
	}

	@Override
	public List<BookTo> findBooksByTitle(String title) {
		return bookMapper.entity2ToList(bookDao.findBookByTitle(title));
	}

	@Override
	public List<BookTo> findBooksByAuthor(String author) {
		return bookMapper.entity2ToList(bookDao.findBooksByAuthor(author));
	}

	@Override
	public BookTo saveBook(BookTo book) {
		return bookMapper.entity2To(bookDao.save(bookMapper.to2Entity(book)));
	}

}
