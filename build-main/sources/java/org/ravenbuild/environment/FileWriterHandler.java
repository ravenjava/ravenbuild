package org.ravenbuild.environment;

import java.io.FileWriter;

@FunctionalInterface
public interface FileWriterHandler {
	void write(FileWriter fileWriter);
}
