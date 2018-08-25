public class Debt {

	private String debtor, payee;
	private Float amount;

	public Debt(String from, String to, Float anAmount) {
		debtor = from;
		payee = to;
		amount = anAmount;
	}

	@Override
	public String toString() {
		StringBuilder message = new StringBuilder();
		message.append(debtor)
			.append(" le debe a ")
			.append(payee)
			.append(" ").append(amount)
			.append(" pesos");

		return message.toString();
	}

	@Override
	public boolean equals(Object obj) {
		return this.getClass() == obj.getClass() &&
			debtor.equals(((Debt)obj).debtor) &&
			payee.equals(((Debt)obj).payee) &&
			// MetNum appears again!
			Math.abs(amount - ((Debt)obj).amount) < 0.1;
	}
}
