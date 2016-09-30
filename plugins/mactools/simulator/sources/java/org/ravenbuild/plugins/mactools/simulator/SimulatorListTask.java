package org.ravenbuild.plugins.mactools.simulator;

import org.openbakery.simulators.SimulatorControl;
import org.openbakery.simulators.SimulatorsListTask;
import org.ravenbuild.plugins.help.LongDescription;
import org.ravenbuild.plugins.help.ShortDescription;
import org.ravenbuild.tasks.EmptyTaskOptions;
import org.ravenbuild.tasks.Task;

@ShortDescription("Lists all available iOS simulators.")
@LongDescription({
		"Running this task will print a list of all available simulators. This is mostly",
		"\"just for your information\", but you can also use this information when",
		"specifying simulator targets for unit tests or running your app."
})
public class SimulatorListTask implements Task<EmptyTaskOptions> {
	@Override
	public void run(final EmptyTaskOptions taskOptions) {
	}
}
