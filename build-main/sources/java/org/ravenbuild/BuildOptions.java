package org.ravenbuild;

import net.davidtanzer.jdefensive.Args;
import org.ravenbuild.environment.RunMode;

import java.util.HashMap;
import java.util.Map;

public class BuildOptions {
	static final BuildOptions DEFAULT = new BuildOptions("build", new HashMap<>(), LogLevel.DEFAULT, "raven.json", RunMode.ALLOW_MODIFICATIONS);
	
	private final String task;
	private final Map<String, String> taskOptions;
	private final LogLevel logLevel;
	private final String buildConfigFile;
	private final RunMode runMode;
	
	private BuildOptions(final String task, final Map<String, String> taskOptions, final LogLevel logLevel, final String buildConfigFile, final RunMode runMode) {
		Args.notEmpty(task, "task");
		Args.notNull(taskOptions, "taskOptions");
		Args.notNull(logLevel, "logLevel");
		Args.notEmpty(buildConfigFile, "buildConfigFile");
		Args.notNull(runMode, "runMode");
		
		this.task = task;
		this.taskOptions = taskOptions;
		this.logLevel = logLevel;
		this.buildConfigFile = buildConfigFile;
		this.runMode = runMode;
	}
	
	public String task() {
		return task;
	}
	
	public Map<String, String> taskOptions() {
		return taskOptions;
	}
	
	public LogLevel logLevel() {
		return logLevel;
	}
	
	public String buildConfigFile() {
		return buildConfigFile;
	}
	
	public RunMode runMode() {
		return runMode;
	}
	
	public static BuildOptions parseFrom(final String[] args) {
		String task = "build";
		String buildConfigFile = "raven.json";
		LogLevel logLevel = LogLevel.DEFAULT;
		HashMap<String, String> taskOptions = new HashMap<>();
		RunMode runMode = RunMode.ALLOW_MODIFICATIONS;
		
		if(args.length > 0) {
			int taskStart = 0;
			for (int i = 0; i < args.length; i++) {
				if (!args[i].startsWith("-")) {
					break;
				}
				
				if(args[i].equals("-v")) {
					logLevel = LogLevel.VERBOSE;
				} else if(args[i].equals("-vv")) {
					logLevel = LogLevel.VERY_VERBOSE;
				} else if(args[i].equals("-D")) {
					logLevel = LogLevel.DEBUG;
				} else if(args[i].equals("-dry")) {
					runMode = RunMode.DRY_RUN;
				}
				taskStart++;
			}
			
			task = args[taskStart];
			
			parseTaskOptions(args, taskOptions, taskStart + 1);
		}
		
		return new BuildOptions(task, taskOptions, logLevel, buildConfigFile, runMode);
	}
	
	private static void parseTaskOptions(final String[] args, final HashMap<String, String> taskOptions, final int startIndex) {
		for(int i=startIndex; i<args.length; i++) {
			parseTaskOption(args[i], taskOptions);
		}
	}
	
	private static void parseTaskOption(final String arg, final HashMap<String, String> taskOptions) {
		String optionName = arg;
		String optionValue = "true";
		
		if(arg.contains("=")) {
			optionName = arg.substring(0, arg.indexOf("="));
			optionValue = arg.substring(arg.indexOf("=") + 1);
		}
		
		taskOptions.put(optionName, optionValue);
	}
	
	@Override
	public boolean equals(final Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		
		final BuildOptions that = (BuildOptions) o;
		
		if (!task.equals(that.task)) {
			return false;
		}
		if (!taskOptions.equals(that.taskOptions)) {
			return false;
		}
		if (logLevel != that.logLevel) {
			return false;
		}
		return buildConfigFile.equals(that.buildConfigFile);
		
	}
	
	@Override
	public int hashCode() {
		int result = task.hashCode();
		result = 31 * result + taskOptions.hashCode();
		result = 31 * result + logLevel.hashCode();
		result = 31 * result + buildConfigFile.hashCode();
		return result;
	}
}
