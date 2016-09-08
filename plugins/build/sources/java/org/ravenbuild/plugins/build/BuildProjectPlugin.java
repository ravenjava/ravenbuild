package org.ravenbuild.plugins.build;

import org.ravenbuild.plugins.BuildPlugin;
import org.ravenbuild.plugins.PluginContext;
import org.ravenbuild.tasks.EmptyTaskOptions;
import org.ravenbuild.tasks.extensible.ExtensibleTaskSubtasks;

public class BuildProjectPlugin implements BuildPlugin {
	@Override
	public void initialize(final PluginContext pluginContext) {
		ExtensibleTaskSubtasks compileSubtasks = new ExtensibleTaskSubtasks();
		pluginContext.registerTask("compileSubtasks", compileSubtasks, EmptyTaskOptions.class, "Internal");
		pluginContext.registerTask("compile", new CompileTask(compileSubtasks, "compileSubtasks"), EmptyTaskOptions.class, "Build");
		
		ExtensibleTaskSubtasks resourcesSubtasks = new ExtensibleTaskSubtasks();
		pluginContext.registerTask("resourcesSubtasks", resourcesSubtasks, EmptyTaskOptions.class, "Internal");
		pluginContext.registerTask("resources", new ResourcesTask(resourcesSubtasks, "resourcesSubtasks"), EmptyTaskOptions.class, "Build");
		
		ExtensibleTaskSubtasks checkSubtasks = new ExtensibleTaskSubtasks();
		pluginContext.registerTask("checkSubtasks", checkSubtasks, EmptyTaskOptions.class, "Internal");
		pluginContext.registerTask("check", new CheckTask(checkSubtasks, "checkSubtasks"), EmptyTaskOptions.class, "Build");
		
		ExtensibleTaskSubtasks unitTestSubtasks = new ExtensibleTaskSubtasks();
		pluginContext.registerTask("unitTestSubtasks", unitTestSubtasks, EmptyTaskOptions.class, "Internal");
		pluginContext.registerTask("unitTest", new UnitTestTask(unitTestSubtasks, "unitTestSubtasks"), EmptyTaskOptions.class, "Build");
		
		ExtensibleTaskSubtasks functionalTestSubtasks = new ExtensibleTaskSubtasks();
		pluginContext.registerTask("functionalTestSubtasks", functionalTestSubtasks, EmptyTaskOptions.class, "Internal");
		pluginContext.registerTask("functionalTest", new FunctionalTestTask(functionalTestSubtasks, "functionalTestSubtasks"), EmptyTaskOptions.class, "Build");
		
		ExtensibleTaskSubtasks buildSubtasksTask = new ExtensibleTaskSubtasks();
		pluginContext.registerTask("buildSubtasks", buildSubtasksTask, EmptyTaskOptions.class, "Internal");
		pluginContext.registerTask("build", new BuildTask(buildSubtasksTask, "buildSubtasks"), EmptyTaskOptions.class, "Build");
	}
	
	@Override
	public String getId() {
		return "org.ravenbuild.build";
	}
}
