package tecs.vmtranslator;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Scanner;

class Parser {
	private Scanner input;
	private String currentLine;
	private Command currentCommand;
	private String arg1;
	private int arg2;

	Parser(Path filePath) throws IOException {
		this.input = new Scanner(filePath);
	}

	Command commandType() {
		return currentCommand;
	}

	boolean hasMoreCommands() {
		if (input.hasNextLine()) {
			return true;
		}
		input.close();
		return false;
	}

	void advance() {
		currentLine = input.nextLine();
	}

	public String getArg1() {
		return arg1;
	}

	public int getArg2() {
		return arg2;
	}
}
