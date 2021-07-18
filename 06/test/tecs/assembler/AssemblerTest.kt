package tecs.assembler;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class AssemblerTest {
    @Test
    void testAssemble() throws IOException {
        File[] asmFiles = new File("testData").listFiles((dir, name) -> name.endsWith(".asm"));
        for (File file : asmFiles) {
            Path asmPath = file.toPath();
            String asmCode = Files.readString(asmPath);
            var assembler = new Assembler(asmCode);
            String output = assembler.assemble();

            Path parentDirectory = asmPath.getParent();
            String hackFileName = asmPath.getFileName().toString().split("\\.")[0] + ".hack";
            Path hackFilePath = parentDirectory.resolve(hackFileName);
            String hackCode = Files.readString(hackFilePath);

            assertEquals(hackCode, output);
        }
    }
}
