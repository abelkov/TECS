//package tecs.assembler;
//
//import java.nio.file.Path;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//
//class LabelParser extends Parser {
//    private int nextRomAddress;
//
//    LabelParser(Path filePath, SymbolTable table) {
//        super(filePath, table);
//    }
//
//    protected void parseLine(String line) {
//        Matcher m = Pattern.compile(AsmPattern.label).matcher(line);
//        if (m.matches()) {
//            table.addEntry(m.group(1), nextRomAddress);
//        } else {
//            nextRomAddress += 1;
//        }
//    }
//}
