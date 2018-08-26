import domain.Debt;
import domain.SpendsSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class DeudorBot extends TelegramLongPollingBot {

	private static final Logger log = LogManager.getLogger(DeudorBot.class);
	private Map<Long, SpendsSession> sessionManager;

	public DeudorBot() {
		sessionManager = new HashMap<>();
	}

	public void onUpdateReceived(Update update) {
		if (update.hasMessage() && update.getMessage().hasText()) {

			log.info("Message arrived from chatId: " + update.getMessage().getChatId().toString());

			// Parse commands
			String recievedMessage = update.getMessage().getText();
			String[] splittedMessage = recievedMessage.split(" ");
			Integer commandsQunatity = splittedMessage.length;

			// Debug
			StringBuilder response = new StringBuilder();
			String name;
			Float amount;

			Long chatId = update.getMessage().getChatId();

			if (!sessionManager.containsKey(chatId)) {
				sessionManager.put(chatId, new SpendsSession());
			}
			SpendsSession currentSession = sessionManager.get(update.getMessage().getChatId());

			if (splittedMessage[0].charAt(0) == '/') {
				// I'm parsing a command
				String command = splittedMessage[0].substring(1, splittedMessage[0].length());
				switch (command) {
					// /gasto quien cuanto
					case "gasto":
						name = splittedMessage[1];
						amount = Float.parseFloat(splittedMessage[2]);

						currentSession.spend(name, amount);

						response.append("dalep");
						break;
					// /puso quien cuanto
					case "puso":
						name = splittedMessage[1];
						amount = Float.parseFloat(splittedMessage[2]);

						currentSession.pay(name, amount);

						response.append("roger that");
						break;
					case "liquidar":
						List<Debt>	debts = currentSession.end();

						response.append("Cuánto nos debemos?").append('\n');

						for (Debt debt : debts) {
							response.append(debt.toString()).append('\n');
						}

						break;
					case "reset":
						sessionManager.put(chatId, new SpendsSession());
						response.append("empezamos de cero");
						break;
					default:
						response.append("unrecognized command!");
						break;
				}
			}

			SendMessage message = new SendMessage()
				.setChatId(update.getMessage().getChatId())
				.setText(response.toString());
			try {
				execute(message);
			} catch (TelegramApiException e) {
				e.printStackTrace();
			}
		}
	}

	public String getBotUsername() {
		return "DeudorBot";
	}

	public String getBotToken() {
		return "636005835:AAEC7sLNcxgflZdpfDVEXYqOZicA0bXEYKQ";
	}
}
