package org.ravenbuild.logging;

import org.junit.Test;
import org.ravenbuild.LogLevel;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

public class LoggerTest {
	@Test
	public void logPrintsToTheProvidedOutputStream() {
		final ByteArrayOutputStream rawStream = new ByteArrayOutputStream();
		final PrintStream outStream = new PrintStream(rawStream);
		final Logger logger = new Logger(LogLevel.DEFAULT, outStream);
		
		logger.log(LogLevel.DEFAULT, "", "Test Message");
		
		final String printedMessage = new String(rawStream.toByteArray());
		assertThat(printedMessage, is("                              Test Message\n"));
	}
	
	@Test
	public void formatsShortPrefixCorrectly() {
		final ByteArrayOutputStream rawStream = new ByteArrayOutputStream();
		final PrintStream outStream = new PrintStream(rawStream);
		final Logger logger = new Logger(LogLevel.DEFAULT, outStream);
		
		logger.log(LogLevel.DEFAULT, "short", "Test Message");
		
		final String printedMessage = new String(rawStream.toByteArray());
		assertThat(printedMessage, is("                       short: Test Message\n"));
	}
	
	@Test
	public void formatsLongPrefixCorrectly() {
		final ByteArrayOutputStream rawStream = new ByteArrayOutputStream();
		final PrintStream outStream = new PrintStream(rawStream);
		final Logger logger = new Logger(LogLevel.DEFAULT, outStream);
		
		logger.log(LogLevel.DEFAULT, "A very long prefix that has more than 20 characters", "Test Message");
		
		final String printedMessage = new String(rawStream.toByteArray());
		assertThat(printedMessage, is("        A very long prefix t: Test Message\n"));
	}

	@Test
	public void logDoesNotPrintToTheProvidedStreamIfLogLevelIsFinerThanConfigured() {
		final PrintStream outStream = mock(PrintStream.class);
		final Logger logger = new Logger(LogLevel.DEFAULT, outStream);
		
		logger.log(LogLevel.DEBUG, "Test Prefix", "Test Message");
		
		verifyNoMoreInteractions(outStream);
	}
	
	@Test
	public void printsLogLevelForErrors() {
		final ByteArrayOutputStream rawStream = new ByteArrayOutputStream();
		final PrintStream outStream = new PrintStream(rawStream);
		final Logger logger = new Logger(LogLevel.DEFAULT, outStream);
		
		logger.log(LogLevel.ERROR, "", "Test Message");
		
		final String printedMessage = new String(rawStream.toByteArray());
		assertThat(printedMessage, is("[ERROR]                       Test Message\n"));
	}
	
	@Test
	public void printsLogLevelForVerbose() {
		final ByteArrayOutputStream rawStream = new ByteArrayOutputStream();
		final PrintStream outStream = new PrintStream(rawStream);
		final Logger logger = new Logger(LogLevel.DEBUG, outStream);
		
		logger.log(LogLevel.VERBOSE, "", "Test Message");
		
		final String printedMessage = new String(rawStream.toByteArray());
		assertThat(printedMessage, is("[VERBO]                       Test Message\n"));
	}
	
	@Test
	public void printsLogLevelForDebug() {
		final ByteArrayOutputStream rawStream = new ByteArrayOutputStream();
		final PrintStream outStream = new PrintStream(rawStream);
		final Logger logger = new Logger(LogLevel.DEBUG, outStream);
		
		logger.log(LogLevel.DEBUG, "", "Test Message");
		
		final String printedMessage = new String(rawStream.toByteArray());
		assertThat(printedMessage, is("[DEBUG]                       Test Message\n"));
	}
}
