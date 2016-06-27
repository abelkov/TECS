package tecs.assembler;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static tecs.assembler.AsmPattern.*;
import static tecs.assembler.Command.*;

class Parser {
	Scanner input;

	private String currentLine;
	private Command currentCommand;

	private String dest;
	private String comp;
	private String jump;
	private String symbol;

	Parser(Path filePath) throws IOException {
		this.input = new Scanner(filePath);
	}

	public String getSymbol() {
		return symbol;
	}

	public String getDest() {
		return dest;
	}

	public String getComp() {
		return comp;
	}

	public String getJump() {
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
			parseLabel();
		} else {
			currentCommand = null;
		}
	}

	Command commandType() {
		return currentCommand;
	}

	private void parseAt() {
		Matcher m = Pattern.compile(AT_NUMBER).matcher(currentLine);
		if (m.matches()) {
			symbol = m.group(1);
		}
	}

	private void parseDest() {
		Matcher m = Pattern.compile(DEST).matcher(currentLine);
		m.matches();
		dest = m.group(1).trim();
		comp = m.group(2).trim();
		jump = null;
	}

	private void parseJump() {
		Matcher m = Pattern.compile(JUMP).matcher(currentLine);
		m.matches();
		dest = null;
		comp = m.group(1).trim();
		jump = m.group(2).trim();
	}

	private void parseLabel() {
		currentCommand = L_COMMAND;
	}

//	private boolean skipLine(String line) {
//		return line.startsWith("//") || Pattern.matches(EMPTY, line);
//	}
}
