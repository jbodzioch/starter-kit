package pl.spring.demo.mock;
/**
 * @COPYRIGHT (C) 2015 Schenker AG
 *
 * All rights reserved
 */

import org.junit.Before;
import org.junit.Test;
import org.mockito.*;
import pl.spring.demo.dao.BookDao;
import pl.spring.demo.mapper.BookMapper;
import pl.spring.demo.service.impl.BookServiceImpl;
import pl.spring.demo.to.AuthorTo;
import pl.spring.demo.to.BookEntity;
import pl.spring.demo.to.BookTo;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

/**
 * TODO The class BookServiceImplTest is supposed to be documented...
 *
 * @author AOTRZONS
 */
public class BookServiceImplTest {

	@InjectMocks
	private BookServiceImpl bookService;
	@Mock
	private BookDao bookDao;
	@Mock
	private BookMapper bookMapper;

	@Before
	public void setUpt() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testShouldSaveBook() {
		BookTo book = new BookTo(null, "title", "author");
		BookEntity bookEntity = new BookEntity(null, "title", "author");
		BookEntity resultBookEntity = new BookEntity(1L, "title", "author");

		Mockito.when(bookDao.save(bookEntity)).thenReturn(resultBookEntity);
		Mockito.when(bookMapper.entity2To(resultBookEntity)).thenReturn(new BookTo(1L, "title", "author"));
		Mockito.when(bookMapper.to2Entity(book)).thenReturn(bookEntity);

		BookTo result = bookService.saveBook(book);

		Mockito.verify(bookDao).save(bookEntity);
		Mockito.verify(bookMapper).entity2To(resultBookEntity);
		assertEquals(1L, result.getId().longValue());
	}
}