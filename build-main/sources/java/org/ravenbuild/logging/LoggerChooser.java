package org.ravenbuild.logging;

import net.davidtanzer.jdefensive.Args;
import org.ravenbuild.LogLevel;

public class LoggerChooser {
	private final Logger logger;
	
	LoggerChooser(final Logger logger) {
		Args.notNull(logger, "logger");
		this.logger = logger;
	}
	
	public LoggerChooser error(String prefix, String message) {
		if(logger.getLogLevel() == LogLevel.ERROR) {
			logger.log(LogLevel.ERROR, prefix, message);
		}
		return this;
	}
	
	public LoggerChooser defaultLevel(String prefix, String message) {
		if(logger.getLogLevel() == LogLevel.DEFAULT) {
			logger.log(LogLevel.DEFAULT, prefix, message);
		}
		return this;
	}
	
	public LoggerChooser verbose(String prefix, String message) {
		if(logger.getLogLevel() == LogLevel.VERBOSE) {
			logger.log(LogLevel.VERBOSE, prefix, message);
		}
		return this;
	}
	
	public LoggerChooser veryVerbose(String prefix, String message) {
		if(logger.getLogLevel() == LogLevel.VERY_VERBOSE) {
			logger.log(LogLevel.VERY_VERBOSE, prefix, message);
		}
		return this;
	}
	
	public LoggerChooser debug(String prefix, String message) {
		if(logger.getLogLevel() == LogLevel.DEBUG) {
			logger.log(LogLevel.DEBUG, prefix, message);
		}
		return this;
	}
}
