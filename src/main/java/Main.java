import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class Main {

	private static final Logger log = LogManager.getLogger(Main.class);

	public static void main(String[] args) {

		ApiContextInitializer.init();

		TelegramBotsApi botsApi = new TelegramBotsApi();

		try {
			log.info("Staring DeudorBot...");
			botsApi.registerBot(new DeudorBot());
		} catch (TelegramApiException e) {
			log.fatal("Error in Deudor execution or starting: " + e.toString());
			e.printStackTrace();
		}

	}
}
