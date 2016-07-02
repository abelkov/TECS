package tecs.vmtranslator;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

class CodeWriter {
	private BufferedWriter writer;

	CodeWriter(Path outputPath) throws IOException {
		setFilePath(outputPath);
	}

	void setFilePath(Path outputPath) throws IOException {
		if (writer != null) {
			writer.close();
		}
		writer = Files.newBufferedWriter(outputPath);
	}

	void writeArithmetic(String command) {

	}

	void writePushPop(Command command, String segment, int index) {

	}

	void close() throws IOException {
		writer.close();
	}
}
