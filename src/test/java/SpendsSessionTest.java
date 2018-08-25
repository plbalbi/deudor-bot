import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SpendsSessionTest {

	private SpendsSession spendsSession;

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
		spendsSession.spend("pablo", 10);
		assertFalse(spendsSession.isClean());
		assertEquals(spendsSession.getSpendsOf("pablo"),10);
	}


}