package org.ravenbuild;

public enum LogLevel {
	ERROR("ERROR"), DEFAULT, VERBOSE("VERBO"), VERY_VERBOSE("VERBO"), DEBUG("DEBUG");
	
	private final String logLevelPrefix;
	
	LogLevel(final String logLevelPrefix) {
		this.logLevelPrefix = logLevelPrefix;
	}
	
	LogLevel() {
		this("");
	}
	
	public String prefix() {
		if(logLevelPrefix.length()==0) {
			return "       ";
		}
		return "["+logLevelPrefix+"]";
	}
}
