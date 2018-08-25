import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SpendsSessionTest {

	@Test
	void TestNewSpendSessionHasIsClean() {
		SpendsSession spendsSession = new SpendsSession();

		assertTrue(spendsSession.isClean());
	}





}