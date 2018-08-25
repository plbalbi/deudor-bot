import java.util.HashMap;
import java.util.Map;

public class SpendsSession {

	private Map<String, Float> balance = new HashMap<String, Float>();

	public SpendsSession() {
	}

	public boolean isClean() {
		return balance.isEmpty();
	}

	public void spend(String aName, float anAmount) {
		Float actualSpends = balance.getOrDefault(aName, 0.0f);
		balance.put(aName, actualSpends + anAmount);
	}

	public float getBalanceOf(String aName){
		return balance.getOrDefault(aName, 0.0f);
	}

	public void pay(String aName, float anAmount) {
		Float actualSpends = balance.getOrDefault(aName, 0.0f);
		balance.put(aName, actualSpends - anAmount);
	}
}
