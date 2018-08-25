import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

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

	@Test
	void testPersonsBalancesEndCorrectlyWhenEndingSessionWithOnePayingAll() {
		String juan = "juan", teo = "teo";
		spendsSession.spend(juan, 80);
		spendsSession.spend(pablo,150);
		spendsSession.spend(teo,95);

		spendsSession.pay(juan,325);

		List<Debt> debts = spendsSession.end();

		for (Debt debt : debts) {
			System.out.println(debt.toString());
		}

		assertEquals(debts.size(), 2);
		assertTrue(debts.contains(new Debt(pablo, juan, 150.0f)));
		assertTrue(debts.contains(new Debt(teo, juan, 95.0f)));
	}

}