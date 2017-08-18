package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import entities.Question;
import entities.Quiz;

public class QuizTest {
	private EntityManagerFactory emf;
	private EntityManager em;

	@Before
	public void setUp() {
		emf = Persistence.createEntityManagerFactory("Quiz");
		em = emf.createEntityManager();
	}

	@After
	public void tearDown() {
		em.close();
		emf.close();
	}

	@Test
	public void test() {
		boolean pass = true;
		assertEquals(pass, true);
	}

	@Test
	public void test_that_quiz_has_many_questions() {
		Quiz q = em.find(Quiz.class, 10);
		assertNotNull(q.getQuestions());
	}
	
	@Test
	public void test_that_question_has_many_answers() {
		Question question = em.find(Question.class, 2);
		assertNotNull(question.getAnswers());
	}
	
}
