package tecs.assembler;

import java.util.regex.Pattern;

import static tecs.assembler.AsmPattern.NUMBER;
import static tecs.assembler.Command.*;

public class Assembler {
    private final SymbolTable symbolTable = new SymbolTable();
    private final String asm;

    public Assembler(String asm) {
        this.asm = asm;
    }

    public String assemble() {
        firstPass();
        return secondPass();
    }

    private void firstPass() {
        // update the symbol map with labels
        int lineNumber = 0;
        Parser labelParser = new Parser(asm);
        while (labelParser.hasMoreCommands()) {
            labelParser.advance();
            if (labelParser.commandType() == null) {
                continue;
            } else if (labelParser.commandType() == L_COMMAND) {
                String symbol = labelParser.getSymbol();
                symbolTable.addEntry(symbol, lineNumber);
            } else {
                lineNumber += 1;
            }
        }
    }

    private String secondPass() {
        // 2nd pass: turn assembly into machine code
        StringBuilder output = new StringBuilder();
        Code codeTable = new Code();
        Parser programParser = new Parser(asm);
        int nextRAMAddress = 16;
        while (programParser.hasMoreCommands()) {
            programParser.advance();
            if (programParser.commandType() == C_COMMAND) {
                String c = codeTable.comp(programParser.getComp());
                String d = codeTable.dest(programParser.getDest());
                String j = codeTable.jump(programParser.getJump());
                output.append("111" + c + d + j + "\n");
            } else if (programParser.commandType() == A_COMMAND) {
                String symbol = programParser.getSymbol();
                int address;
                if (Pattern.matches(NUMBER, symbol)) {
                    address = Integer.parseInt(symbol);
                } else {
                    if (!symbolTable.contains(symbol)) {
                        symbolTable.addEntry(symbol, nextRAMAddress);
                        nextRAMAddress += 1;
                    }
                    address = symbolTable.getAddress(symbol);
                }
                String binaryAddress = toBinary(address);
                output.append(binaryAddress + "\n");
            }
        }

        return output.toString();
    }

    private String toBinary(int address) {
        return String.format("%16s", Integer.toBinaryString(address)).replace(' ', '0');
    }
}
