package pl.spring.demo.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.aop.MethodBeforeAdvice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import pl.spring.demo.annotation.NullableId;
import pl.spring.demo.common.Sequence;
import pl.spring.demo.dao.BookDao;
import pl.spring.demo.exception.BookNotNullIdException;
import pl.spring.demo.to.BookEntity;
import pl.spring.demo.to.IdAware;

import java.lang.reflect.Method;

@Component
@Aspect
public class BookDaoAdvisor implements MethodBeforeAdvice {

	private Sequence sequence;

	private BookDao bookDao;

	@Autowired
	public void setSequence(Sequence sequence) {
		this.sequence = sequence;
	}

	@Autowired
	public void setBookDao(BookDao bookDao) {
		this.bookDao = bookDao;
	}

	@Pointcut("execution(* pl.spring.demo.dao.impl.BookDaoImpl.save(..))")
	public void sequenceCutter() {
	}

	@Before("sequenceCutter()")
	public void idChecker(JoinPoint joinPiont) {

		BookEntity book = (BookEntity) joinPiont.getArgs()[0];

		if (book.getId() != null) {
			throw new BookNotNullIdException();
		} else {
			book.setId(sequence.nextValue(bookDao.findAll()));
		}
	}

	@Override
	public void before(Method method, Object[] objects, Object o) throws Throwable {

		if (hasAnnotation(method, o, NullableId.class)) {
			checkNotNullId(objects[0]);
		}
	}

	private void checkNotNullId(Object o) {
		if (o instanceof IdAware && ((IdAware) o).getId() != null) {
			throw new BookNotNullIdException();
		}
	}

	private boolean hasAnnotation(Method method, Object o, Class annotationClazz) throws NoSuchMethodException {
		boolean hasAnnotation = method.getAnnotation(annotationClazz) != null;

		if (!hasAnnotation && o != null) {
			hasAnnotation = o.getClass().getMethod(method.getName(), method.getParameterTypes())
					.getAnnotation(annotationClazz) != null;
		}
		return hasAnnotation;
	}
}
