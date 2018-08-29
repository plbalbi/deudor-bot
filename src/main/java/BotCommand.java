import com.sun.tools.corba.se.idl.StringGen;
import org.telegram.telegrambots.bots.DefaultAbsSender;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

public abstract class BotCommand {

	private DefaultAbsSender bot;
	private String name;
	private Integer argumentCount;

	public BotCommand(DefaultAbsSender bot, String name, Integer argumentCount) {
		this.bot = bot;
		this.name = name;
		this.argumentCount = argumentCount;
	}

	private String[] splittedCommand;

	public boolean isThisCommand(Message message) {

		// Clean previous splitted command
		splittedCommand = null;

		if (!isCommand(message))
			return false;

		splittedCommand = message.getText().split(" ");
		String parsedName = splittedCommand[0].substring(1, splittedCommand[0].length());

		return
			parsedName.equals(name) &&
			// Substract the command name parsed string
			argumentCount.equals(splittedCommand.length - 1);

		// TODO: Count command arguments
		// TODO: Maybe add a command type with variadic arguments
	}

	public abstract SendMessage doAction();

	private boolean isCommand(Message message) {
		return
			message.hasText() &&
			message.getText().charAt(0) == '/';
	}

}
