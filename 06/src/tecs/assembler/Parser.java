package tecs.assembler;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static tecs.assembler.AsmPattern.*;
import static tecs.assembler.Command.*;

class Parser {
	private Scanner input;
	private String currentLine;
	private Command currentCommand;
	private String dest;
	private String comp;
	private String jump;
	private String symbol;

	Parser(Path filePath) throws IOException {
		this.input = new Scanner(filePath);
	}

	Command commandType() {
		return currentCommand;
	}

	String getSymbol() {
		return symbol;
	}

	String getDest() {
		return dest;
	}

	String getComp() {
		return comp;
	}

	String getJump() {
		return jump;
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
		if (Pattern.matches(AT, currentLine)) {
			currentCommand = A_COMMAND;
			parseAt();
		} else if (Pattern.matches(COMP, currentLine)) {
			currentCommand = C_COMMAND;
			parseComp();
		} else if (Pattern.matches(LABEL, currentLine)) {
			currentCommand = L_COMMAND;
			parseLabel();
		} else {
			currentCommand = null;
		}
	}

	private void parseAt() {
		Matcher m = Pattern.compile(AT).matcher(currentLine);
		m.matches();
		symbol = m.group(1);
	}

	private void parseComp() {
		Matcher m = Pattern.compile(COMP).matcher(currentLine);
		m.matches();
		dest = m.group(1);
		comp = m.group(2);
		jump = m.group(3);
	}

	private void parseLabel() {
		Matcher m = Pattern.compile(LABEL).matcher(currentLine);
		m.matches();
		symbol = m.group(1);
	}
}
