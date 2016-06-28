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
		} else if (Pattern.matches(DEST, currentLine)) {
			currentCommand = C_COMMAND;
			parseDest();
		} else if (Pattern.matches(JUMP, currentLine)) {
			currentCommand = C_COMMAND;
			parseJump();
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

	private void parseDest() {
		Matcher m = Pattern.compile(DEST).matcher(currentLine);
		m.matches();
		dest = m.group(1);
		comp = m.group(2);
		jump = null;
	}

	private void parseJump() {
		Matcher m = Pattern.compile(JUMP).matcher(currentLine);
		m.matches();
		dest = null;
		comp = m.group(1);
		jump = m.group(2);
	}

	private void parseLabel() {
		Matcher m = Pattern.compile(LABEL).matcher(currentLine);
		m.matches();
		symbol = m.group(1);
	}
}
