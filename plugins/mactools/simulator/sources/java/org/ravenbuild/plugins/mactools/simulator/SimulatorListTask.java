package org.ravenbuild.plugins.mactools.simulator;

import net.davidtanzer.jdefensive.Args;
import org.openbakery.CommandRunner;
import org.openbakery.Destination;
import org.openbakery.Type;
import org.openbakery.simulators.SimulatorControl;
import org.openbakery.tools.Xcode;
import org.ravenbuild.LogLevel;
import org.ravenbuild.logging.Logger;
import org.ravenbuild.plugins.help.LongDescription;
import org.ravenbuild.plugins.help.ShortDescription;
import org.ravenbuild.tasks.EmptyTaskOptions;
import org.ravenbuild.tasks.Task;

import java.util.Collections;
import java.util.List;

@ShortDescription("Lists all available iOS simulators.")
@LongDescription({
		"Running this task will print a list of all available simulators. This is mostly",
		"\"just for your information\", but you can also use this information when",
		"specifying simulator targets for unit tests or running your app."
})
public class SimulatorListTask implements Task<EmptyTaskOptions> {
	private final Logger logger;
	
	public SimulatorListTask(final Logger logger) {
		Args.notNull(logger, "logger");
		this.logger = logger;
	}
	
	@Override
	public void run(final EmptyTaskOptions taskOptions) {
		CommandRunner commandRunner = new CommandRunner();
		Xcode xcode = new Xcode(commandRunner);
		SimulatorControl simulatorControl = new SimulatorControl(commandRunner, xcode);
		
		List<Destination> availableSimulators = simulatorControl.getAllDestinations(Type.iOS);
		List<Destination> tvOsSimulators = simulatorControl.getAllDestinations(Type.tvOS);
		availableSimulators.addAll(tvOsSimulators);
		
		Collections.sort(availableSimulators, (first, second) -> {
			int result = compareTo(first.getOs(), second.getOs());
			if (result != 0) {
				return result;
			}
			return compareTo(first.getName(), second.getName());
		});
		
		String currentId = "";
		for (Destination destination : availableSimulators) {
			
			String destinationId = destination.getPlatform() + " -- " + destination.getOs();
			if (!currentId.equals(destinationId)) {
				logger.logSeparator(LogLevel.DEFAULT, destinationId);
				currentId = destinationId;
			}
			
			String id = "";
			if (destination.getId() != null) {
				id = destination.getId();
			}
			
			logger.log(LogLevel.DEFAULT, id, destination.getName());
		}
	}
	
	private int compareTo(final String first, final String second) {
		if (first == null && second == null) {
			return 0;
		}
		
		if (first == null && second != null) {
			return 1;
		}
		
		if (first != null && second == null) {
			return -1;
		}
		return first.compareTo(second);
		
	}
}