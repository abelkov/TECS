package tecs.vmtranslator;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;

import static tecs.vmtranslator.Command.C_POP;
import static tecs.vmtranslator.Command.C_PUSH;

class CodeWriter {
	private BufferedWriter bw;
	private String n = System.getProperty("line.separator");
	private int booleanLabelCount = 0;

	CodeWriter(Path outputPath) throws IOException {
		setFilePath(outputPath);
	}

	void setFilePath(Path outputPath) throws IOException {
		if (bw != null) {
			bw.close();
		}
		bw = Files.newBufferedWriter(outputPath);
	}

	void writeArithmetic(String command) throws IOException {
		switch (command) {
			case "add":
				writeBinary("+");
				break;
			case "sub":
				writeBinary("-");
				break;
			case "neg":
				writeUnary("-");
				break;
			case "eq":
				writeBoolean("EQ");
				break;
			case "gt":
				writeBoolean("GT");
				break;
			case "lt":
				writeBoolean("LT");
				break;
			case "and":
				writeBinary("&");
				break;
			case "or":
				writeBinary("|");
				break;
			case "not":
				writeUnary("!");
				break;
		}
	}

	private void writeBinary(String op) throws IOException {
		String command;
		if (op.equals("-")) {
			command = "M-D";
		} else {
			command = "D" + op + "M";
		}
		String code =
			"@SP" + n +
			"AM=M-1" + n +
			"D=M" + n +
			"A=A-1" + n +
			"M=" + command + n;
		bw.write(code);
	}

	private void writeUnary(String op) throws IOException {
		String code =
			"@SP" + n +
			"A=M-1" + n +
			"M=" + op + "M" + n;
		bw.write(code);
	}

	private void writeBoolean(String command) throws IOException {
		String trueLabel = "BOOLEANTRUE" + booleanLabelCount;
		String endLabel = "BOOLEANEND" + booleanLabelCount;
		String code =
			"@SP" + n +
			"M=M-1" + n +
			"AM=M-1" + n +
			"D=M" + n +
			"A=A+1" + n +
			"D=D-M" + n + // arg1 - arg2
			"@" + trueLabel + n +
			"D;J" + command + n +
			"@SP" + n +
			"A=M" + n +
			"M=0" + n + // false
			"@" + endLabel + n +
			"0;JMP" + n +
			"(" + trueLabel + ")" + n +
			"@SP" + n +
			"A=M" + n +
			"M=-1" + n + // true
			"(" + endLabel + ")" + n +
			"@SP" + n +
			"M=M+1" + n;
		bw.write(code);
		booleanLabelCount += 1;
	}

	void writePushPop(Command command, String segment, int index) throws IOException {
		HashMap<String, String> segmentMap = new HashMap<>();
		segmentMap.put("local", "LCL");
		segmentMap.put("argument", "ARG");
		segmentMap.put("this", "THIS");
		segmentMap.put("that", "THAT");
		segmentMap.put("temp", "5");

		String segmentAddress = null;
		if (segmentMap.containsKey(segment)) {
			segmentAddress = segmentMap.get(segment);
		}

		// compute segment + index
		// if segment == "constant", use index only
		String code =
			"@" + index + n +
			"D=A" + n;
		if (segmentAddress != null) {
			code +=
				"@" + segmentAddress + n +
				"D=D+M" + n;
		}

		if (command == C_PUSH) {
			if (segmentAddress != null) {
				// fetch value to push
				code +=
					"A=D" + n +
					"D=M" + n;
			}
			code +=
				// push
				"@SP" + n +
				"A=M" + n +
				"M=D" + n +

				// increment SP
				"@SP" + n +
				"M=M+1" + n;
		} else if (command == C_POP) {
			code +=
				// save destination address into temporary variable
				"@R13" + n +
				"M=D" + n +

				// decrement SP
				"@SP" + n +
				"M=M-1" + n +

				// fetch value to pop
				"A=M" + n +
				"D=M" + n +

				// store value at destination address
				"@R13" + n +
				"A=M" + n +
				"M=D" + n;
		}
		bw.write(code);
	}

	void close() throws IOException {
		bw.close();
	}
}
