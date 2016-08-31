package org.ravenbuild.logging;

import org.ravenbuild.LogLevel;

import java.io.PrintStream;

public class Logger {
	private final LogLevel logLevel;
	private final PrintStream outputStream;
	
	public Logger(final LogLevel logLevel) {
		this(logLevel, System.out);
	}
	
	Logger(final LogLevel logLevel, final PrintStream outputStream) {
		this.logLevel = logLevel;
		this.outputStream = outputStream;
	}
	
	public void log(final LogLevel logLevel, final String prefix, final String message) {
		if(logLevel.ordinal() <= this.logLevel.ordinal()) {
			printFormattedLogLevel(logLevel);
			printFormattedPrefix(prefix);
			outputStream.println(message);
		}
	}
	
	private void printFormattedLogLevel(final LogLevel logLevel) {
		outputStream.print(logLevel.prefix());
		outputStream.print(" ");
	}
	
	private void printFormattedPrefix(final String prefix) {
		int printedCharacters = 0;
		for(; printedCharacters<20-prefix.length(); printedCharacters++) {
			outputStream.print(" ");
		}
		
		for(int i=0; printedCharacters < 20; i++, printedCharacters++) {
			outputStream.print(prefix.charAt(i));
		}
		
		if(prefix.length() == 0) {
			outputStream.print("  ");
		} else {
			outputStream.print(": ");
		}
		printedCharacters++;
	}
	
	public void logNewLine(final LogLevel logLevel) {
		if(logLevel.ordinal() <= this.logLevel.ordinal()) {
			printFormattedLogLevel(logLevel);
			outputStream.println();
		}
	}
}
