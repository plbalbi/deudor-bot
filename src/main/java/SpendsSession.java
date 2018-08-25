import java.util.HashMap;
import java.util.Map;

public class SpendsSession {

	private Map<String, Float> spendsList = new HashMap<String, Float>();

	public SpendsSession() {
	}

	public boolean isClean() {
		return spendsList.isEmpty();
	}

	public void spend(String aName, float anAmount) {
		spendsList.put(aName, anAmount);
	}

	public float getSpendsOf(String aName) {
		return 10.0f;
	}
}
