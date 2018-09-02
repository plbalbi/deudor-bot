package domain;

import java.util.*;

public class SpendsSession {

	private Map<String, Float> balance = new HashMap<String, Float>();

	public SpendsSession() {}

	public void spend(String aName, float anAmount) {
		Float actualSpends = balance.getOrDefault(aName, 0.0f);
		balance.put(aName, actualSpends + anAmount);
	}

	public void pay(String aName, float anAmount) {
		Float actualSpends = balance.getOrDefault(aName, 0.0f);
		balance.put(aName, actualSpends - anAmount);
	}

	public boolean isClean() {
		return balance.isEmpty();
	}

	public float getBalanceOf(String aName){
		return balance.getOrDefault(aName, 0.0f);
	}

	public List<Debt> end() {

		LinkedList<Debt> debts = new LinkedList<>();
		Stack<Tuple<String, Float>> debtors = new Stack<>();
		Stack<Tuple<String, Float>> payees = new Stack<>();

		// Separate balances into debtors and payees
		balance.forEach((name, balance) -> {
			if (balance > 0) {
				debtors.push(new Tuple<>(name, balance));
			} else if (balance < 0) {
				payees.push(new Tuple<>(name, -balance));
			}
		});

		// Fill the payees balance in a greedy way
		// TODO: Sort the debtors in a decreasing manner, in order to make the ones that owe less, make just one transaction

		while (!payees.empty()) {
			Tuple<String,Float> payee = payees.pop();

			// In a greedy way, pay the whole balance of payee
			while (payee.second > 0 && !debtors.empty()) {
				Tuple<String,Float> debtor = debtors.pop();

				if (payee.second > debtor.second) {
					payee.second -= debtor.second;
					debts.add(new Debt(debtor.first, payee.first, debtor.second));
				} else if (debtor.second > payee.second) {
					debts.add(new Debt(debtor.first, payee.first, payee.second));
					// debtor has more money to pay others
					debtor.second -= payee.second;
					debtors.push(debtor);
					break;
				} else {
					debts.add(new Debt(debtor.first, payee.first, debtor.second));
					break;
				}
			}
		}

		return debts;
	}

	public class Tuple<X,Y> {
		public X first;
		public Y second;

		public Tuple(X first, Y second) {
			this.first = first;
			this.second = second;
		}
	}
}

