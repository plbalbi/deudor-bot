import domain.Debt;
import domain.SpendsSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.awt.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class DeudorBot extends TelegramLongPollingBot {

	private static final Logger log = LogManager.getLogger(DeudorBot.class);
	public static final String BOT_KEY = "636005835:AAEC7sLNcxgflZdpfDVEXYqOZicA0bXEYKQ";
	public static final String BOT_NAME = "DeudorBot";
	private Map<Long, SpendsSession> sessionManager;

	public DeudorBot() {
		sessionManager = new HashMap<>();
	}


	// TODO: Add exception handling to float parsing
	// TODO: Use some kind of abstraction for commands
	// TODO: Add some kind of session dump

	public void onUpdateReceived(Update update) {
		if (update.hasMessage() && update.getMessage().hasText()) {

			log.info("Message arrived from chatId: " + update.getMessage().getChatId().toString());

			// Parse commands
			String recievedMessage = update.getMessage().getText();
			String[] splittedMessage = recievedMessage.split(" ");
			// Not counting command name
			Integer argumentsQuantity = splittedMessage.length - 1;

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
					case "spend":
						if (argumentsQuantity < 2) {
							response.append("Wrong number of arguments");
							break;
						}
						name = splittedMessage[1];
						amount = Float.parseFloat(splittedMessage[2]);

						currentSession.spend(name, amount);

						response.append(String.format("%s spend %.02f", name, amount));
						break;

					// /puso quien cuanto
					case "pay":
						if (argumentsQuantity < 2) {
							response.append("Wrong number of arguments");
							break;
						}
						name = splittedMessage[1];
						amount = Float.parseFloat(splittedMessage[2]);

						currentSession.pay(name, amount);

						response.append(String.format("%s payed %.02f for the group", name, amount));
						break;

					case "balance":
						List<Debt>	debts = currentSession.end();

						response.append("How much do we owe each other?").append('\n');

						for (Debt debt : debts) {
							response.append(debt.toString()).append('\n');
						}

						break;

					case "split":
						if (argumentsQuantity < 2) {
							response.append("Wrong number of arguments");
							break;
						}

						List<String> splittedCommandAsList = Arrays.asList(splittedMessage);
						// /commandName arg0 arg1 arg2 ...
						// In this case, it should be /split 150 name0 name1...
						amount = Float.parseFloat(splittedCommandAsList.get(1));
						List<String> group = splittedCommandAsList.subList(2, splittedCommandAsList.size());

						currentSession.splitBill(amount, group);

						response.append(String.format("%.02f has been added to ", amount/group.size()));
						for (String person: group) {
							response.append(person).append(" ");
						}

						break;

					case "reset":
						sessionManager.put(chatId, new SpendsSession());
						response.append("Starting over");
						break;

					default:
						response.append("Unrecognized command!");
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
		return BOT_NAME;
	}

	public String getBotToken() {
		return BOT_KEY;
	}
}
