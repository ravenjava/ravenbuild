package org.ravenbuild.environment;

import java.io.FileWriter;
import java.io.IOException;

@FunctionalInterface
public interface FileWriterHandler {
	void write(FileWriter fileWriter) throws IOException;
}
