package org.ravenbuild.plugins.mactools.main;

import org.openbakery.CommandRunner;
import org.openbakery.output.ConsoleOutputAppender;
import org.openbakery.output.OutputAppender;
import org.ravenbuild.plugins.BuildPlugin;
import org.ravenbuild.plugins.PluginContext;

import java.util.Arrays;

public class MacToolsPlugin implements BuildPlugin {
	@Override
	public void initialize(final PluginContext pluginContext) {
		CommandRunner commandRunner = new CommandRunner();
		System.out.println("From mactools");
		commandRunner.run(Arrays.asList("echo", "foo foo fooo"), System.out::println);
		//SimList Task starten...
	}
	
	@Override
	public String getId() {
		return "org.ravenbuild.mactools";
	}
}
