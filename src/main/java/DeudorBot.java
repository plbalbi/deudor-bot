import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


public class DeudorBot extends TelegramLongPollingBot {

	private static final Logger log = LogManager.getLogger(DeudorBot.class);

	public void onUpdateReceived(Update update) {
		if (update.hasMessage() && update.getMessage().hasText()) {

			log.info("Message arrived from chatId: " + update.getMessage().getChatId().toString());

			SendMessage message = new SendMessage()
				.setChatId(update.getMessage().getChatId())
				.setText(update.getMessage().getText());
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
