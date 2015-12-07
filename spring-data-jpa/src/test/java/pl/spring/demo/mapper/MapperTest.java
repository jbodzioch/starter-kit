package pl.spring.demo.mapper;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import pl.spring.demo.to.BookEntity;
import pl.spring.demo.to.BookTo;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "MapperTest-context.xml")
public class MapperTest {

	private BookMapper bookMapper;

	@Autowired
	public void setBookMapper(BookMapper bookMapper) {
		this.bookMapper = bookMapper;
	}

	@Test
	public void shouldReturnGivenValuesForNewBookEntity() {

		BookEntity entity = new BookEntity(2L, "title", "name lastname name2");

		Long expectedId = 2L;
		String expectedTitle = "title";
		Long expectedFirstAuthorId = 1L;
		String expectedFirstAuthorFirstName = "name";
		String expectedFirstAuthorLastName = "lastname";
		Long expectedSecondAuthorId = 2L;
		String expectedSecondAuthorFirstName = "name2";
		String expectedSecondAuthorLastName = null;

		assertEquals(expectedId, entity.getId());
		assertEquals(expectedTitle, entity.getTitle());
		assertEquals(expectedFirstAuthorFirstName, entity.getAuthors().get(0).getFirstName());
		assertEquals(expectedFirstAuthorLastName, entity.getAuthors().get(0).getLastName());
		assertEquals(expectedSecondAuthorFirstName, entity.getAuthors().get(1).getFirstName());
		assertEquals(expectedSecondAuthorLastName, entity.getAuthors().get(1).getLastName());
		assertEquals(expectedFirstAuthorId, entity.getAuthors().get(0).getId());
		assertEquals(expectedSecondAuthorId, entity.getAuthors().get(1).getId());

	}

	@Test
	public void shouldReturnNulls() {

		BookEntity entity = new BookEntity(null, null, null);

		Long expectedId = null;
		String expectedTitle = null;
		String expectedFirstName = null;
		String expectedLastName = null;

		assertEquals(expectedId, entity.getId());
		assertEquals(expectedTitle, entity.getTitle());
		assertEquals(expectedFirstName, entity.getAuthors().get(0).getFirstName());
		assertEquals(expectedLastName, entity.getAuthors().get(0).getLastName());

	}

	@Test
	public void shouldReturnToValuesInMappedEntity() {

		BookEntity entity = new BookEntity(2L, "title", "name lastname name2");
		BookTo to = bookMapper.entity2To(entity);

		String inputString = "name lastname name2";

		assertEquals(to.getId(), entity.getId());
		assertEquals(to.getTitle(), entity.getTitle());
		assertEquals(inputString, to.getAuthors());

	}

	@Test
	public void shouldReturnEntityValuesInMappedTo() {

		BookTo to = new BookTo(3L, "title", "name lastname name2");
		BookEntity entity = bookMapper.to2Entity(to);

		Long expectedFirstAuthorId = 1L;
		String expectedFirstAuthorFirstName = "name";
		String expectedFirstAuthorLastName = "lastname";
		Long expectedSecondAuthorId = 2L;
		String expectedSecondAuthorFirstName = "name2";
		String lastNexpectedSecondAuthorLastName = null;

		assertEquals(to.getId(), entity.getId());
		assertEquals(to.getTitle(), entity.getTitle());
		assertEquals(expectedFirstAuthorFirstName, entity.getAuthors().get(0).getFirstName());
		assertEquals(expectedFirstAuthorLastName, entity.getAuthors().get(0).getLastName());
		assertEquals(expectedSecondAuthorFirstName, entity.getAuthors().get(1).getFirstName());
		assertEquals(lastNexpectedSecondAuthorLastName, entity.getAuthors().get(1).getLastName());
		assertEquals(expectedFirstAuthorId, entity.getAuthors().get(0).getId());
		assertEquals(expectedSecondAuthorId, entity.getAuthors().get(1).getId());

	}

	@Test
	public void shouldReturnToListValuesInMappedEntityList() {

		List<BookEntity> entities = new ArrayList<BookEntity>();

		entities.add(new BookEntity(1L, "title", "name lastname"));
		entities.add(new BookEntity(2L, "title2", "name2 lastname2"));

		List<BookTo> tos = bookMapper.entity2ToList(entities);

		String firstInputString = "name lastname";
		String secondInputString = "name2 lastname2";

		assertEquals(tos.get(0).getId(), entities.get(0).getId());
		assertEquals(tos.get(0).getTitle(), entities.get(0).getTitle());
		assertEquals(firstInputString, tos.get(0).getAuthors());
		assertEquals(tos.get(1).getId(), entities.get(1).getId());
		assertEquals(tos.get(1).getTitle(), entities.get(1).getTitle());
		assertEquals(secondInputString, tos.get(1).getAuthors());

	}

	@Test
	public void shouldReturnEntityListValuesInMappedToList() {

		List<BookTo> tos = new ArrayList<BookTo>();

		tos.add(new BookTo(2L, "title", "name lastname"));
		tos.add(new BookTo(3L, "title2", "name2 lastname2"));

		List<BookEntity> entities = bookMapper.to2EntityList(tos);

		String firstObjectFirstName = "name";
		String firstObjectLastName = "lastname";
		String secondObjectFirstName = "name2";
		String secondObjectLastName = "lastname2";

		assertEquals(tos.get(0).getId(), entities.get(0).getId());
		assertEquals(tos.get(0).getTitle(), entities.get(0).getTitle());
		assertEquals(firstObjectFirstName, entities.get(0).getAuthors().get(0).getFirstName());
		assertEquals(firstObjectLastName, entities.get(0).getAuthors().get(0).getLastName());
		assertEquals(tos.get(1).getId(), entities.get(1).getId());
		assertEquals(tos.get(1).getTitle(), entities.get(1).getTitle());
		assertEquals(secondObjectFirstName, entities.get(1).getAuthors().get(0).getFirstName());
		assertEquals(secondObjectLastName, entities.get(1).getAuthors().get(0).getLastName());

	}

}
