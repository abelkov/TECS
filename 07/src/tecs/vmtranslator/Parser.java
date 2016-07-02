package tecs.vmtranslator;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static tecs.vmtranslator.Command.*;
import static tecs.vmtranslator.VMPattern.*;

class Parser {
	private Scanner input;
	private String currentLine;
	private Command currentCommand;
	private String arg1;
	private int arg2;

	Parser(Path filePath) throws IOException {
		this.input = new Scanner(filePath);
	}

	String getArg1() {
		return arg1;
	}

	int getArg2() {
		return arg2;
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
		if (Pattern.matches(currentLine, ARITHMETIC)) {
			currentCommand = C_ARITHMETIC;
			parseArithmetic();
		} else if (Pattern.matches(currentLine, PUSH)) {
			currentCommand = C_PUSH;
			parsePush();
		} else if (Pattern.matches(currentLine, POP)) {
			currentCommand = C_POP;
			parsePop();
		} else if (Pattern.matches(currentLine, LABEL)) {
			currentCommand = C_LABEL;
			parseLabel();
		} else if (Pattern.matches(currentLine, GOTO)) {
			currentCommand = C_GOTO;
			parseGoto();
		} else if (Pattern.matches(currentLine, IF)) {
			currentCommand = C_IF;
			parseIf();
		} else if (Pattern.matches(currentLine, FUNCTION)) {
			currentCommand = C_FUNCTION;
			parseFunction();
		} else if (Pattern.matches(currentLine, CALL)) {
			currentCommand = C_CALL;
			parseCall();
		} else if (Pattern.matches(currentLine, RETURN)) {
			currentCommand = C_RETURN;
		} else {
			currentCommand = null;
		}
	}

	private void parseArithmetic() {
		Matcher m = Pattern.compile(currentLine).matcher(ARITHMETIC);
		m.matches();
		arg1 = m.group(1);
	}

	private void parsePush() {
		Matcher m = Pattern.compile(currentLine).matcher(PUSH);
		m.matches();
		arg1 = m.group(1);
		arg2 = Integer.parseInt(m.group(2));
	}

	private void parsePop() {
		Matcher m = Pattern.compile(currentLine).matcher(POP);
		m.matches();
		arg1 = m.group(1);
		arg2 = Integer.parseInt(m.group(2));
	}

	private void parseLabel() {
		Matcher m = Pattern.compile(currentLine).matcher(LABEL);
		m.matches();
		arg1 = m.group(1);
	}

	private void parseGoto() {
		Matcher m = Pattern.compile(currentLine).matcher(GOTO);
		m.matches();
		arg1 = m.group(1);
	}

	private void parseIf() {
		Matcher m = Pattern.compile(currentLine).matcher(IF);
		m.matches();
		arg1 = m.group(1);
	}

	private void parseFunction() {
		Matcher m = Pattern.compile(currentLine).matcher(FUNCTION);
		m.matches();
		arg1 = m.group(1);
		arg2 = Integer.parseInt(m.group(2));
	}

	private void parseCall() {
		Matcher m = Pattern.compile(currentLine).matcher(POP);
		m.matches();
		arg1 = m.group(1);
		arg2 = Integer.parseInt(m.group(2));
	}
}
