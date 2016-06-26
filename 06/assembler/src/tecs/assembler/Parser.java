package tecs.assembler;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Scanner;
import java.util.regex.Pattern;

import static tecs.assembler.Command.*;

class Parser {
    Scanner input;
    private String currentLine;
    private Command currentCommand;

    Parser(Path filePath) throws IOException {
        this.input = new Scanner(filePath);

    }

//    void parse() throws IOException {
//        try (BufferedReader br = Files.newBufferedReader(filePath)) {
//            for (String line; (line = br.readLine()) != null; ) {
//                if (skipLine(line)) {
//                    continue;
//                }
//                parseLine(line);
//            }
//        }
//
//    }

    boolean hasMoreCommands() {
        if (!input.hasNextLine()) {
            input.close();
            return false;
        }
        return true;
    }

    void advance() {
        currentLine = input.nextLine();
        if (Pattern.matches(AsmPattern.at, currentLine)) {
            parseAt();
        } else if (Pattern.matches(AsmPattern.dest, currentLine)) {
            parseDest();
        } else if (Pattern.matches(AsmPattern.jump, currentLine)) {
            parseJump();
        } else if (Pattern.matches(AsmPattern.label, currentLine)) {
            parseLabel();
        }
    }

    Command commandType() {
        return currentCommand;
    }

    private void parseAt() {
        currentCommand = A_COMMAND;
    }

    private void parseDest() {
        currentCommand = C_COMMAND;
    }

    private void parseJump() {
        currentCommand = C_COMMAND;
    }

    private void parseLabel() {
        currentCommand = L_COMMAND;
    }

    private boolean skipLine(String line) {
        return line.startsWith("//") || Pattern.matches(AsmPattern.empty, line);
    }

//    protected void parseLine(String line) {
//
//    }
}
