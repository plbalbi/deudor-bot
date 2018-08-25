import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SpendsSessionTest {

	private SpendsSession spendsSession;
	private String pablo = "pablo";

	@BeforeEach
	void setUp() {
		spendsSession = new SpendsSession();
	}

	@Test
	void TestNewSpendSessionHasIsClean() {
		assertTrue(spendsSession.isClean());
	}

	@Test
	void TestSessionWithOneSpendIsNotCleanAnymore() {
		spendsSession.spend(pablo, 10);
		assertFalse(spendsSession.isClean());
		assertEquals(spendsSession.getBalanceOf(pablo),10);
	}

	@Test
	void TestManySpendsOfSamePersonAccumulateOverTime() {
		spendsSession.spend(pablo, 10);
		spendsSession.spend(pablo, 20);
		spendsSession.spend(pablo, 30);

		assertEquals(spendsSession.getBalanceOf(pablo), 60);
	}

	@Test
	void TestSpendsOfNonExistingUserReturnZero() throws Exception{
		assertEquals(spendsSession.getBalanceOf(pablo), 0.0f);
	}

	@Test
	void testSomeoneSpendsAndThenPays() {
		spendsSession.spend(pablo, 50);
		spendsSession.pay(pablo, 70);

		assertEquals(spendsSession.getBalanceOf(pablo), -20.0f);
	}

}