package org.ravenbuild.plugins.ides.intellij;

import net.davidtanzer.jdefensive.Args;
import org.ravenbuild.environment.BuildEnvironment;
import org.ravenbuild.plugins.dependencies.DependenciesTask;
import org.ravenbuild.plugins.help.LongDescription;
import org.ravenbuild.plugins.help.ShortDescription;
import org.ravenbuild.projectinfo.ProjectInfo;
import org.ravenbuild.tasks.EmptyTaskOptions;
import org.ravenbuild.tasks.Task;
import org.ravenbuild.tasks.TaskContext;

@ShortDescription("Writes IntelliJ IDEA project files for the current project.")
@LongDescription({
		"Use this task to write the IntelliJ IDEA project files for the current project.",
		"After running this task, you can import / re-import the project in IntelliJ",
		"IDEA."
})
public class IntelliJTask implements Task<EmptyTaskOptions> {
	private final BuildEnvironment buildEnvironment;
	private DependenciesTask dependenciesTask;
	private ProjectInfo projectInfo;
	
	public IntelliJTask(final BuildEnvironment buildEnvironment) {
		Args.notNull(buildEnvironment, "buildEnvironment");
		
		this.buildEnvironment = buildEnvironment;
	}
	
	@Override
	public void initialize(final TaskContext taskContext) {
		dependenciesTask = taskContext.dependsOn("dependencies");
		
		projectInfo = taskContext.projectInfo();
	}
	
	@Override
	public void run(final EmptyTaskOptions taskOptions) {
		buildEnvironment.writeFile(projectInfo.getProjectName()+".iml", new ImlFileWriter());
		if(!projectInfo.getParent().isPresent()) {
			buildEnvironment.writeFile(".idea/compiler.xml", new CompilerXmlWriter());
			buildEnvironment.writeFile(".idea/modules.xml", new ModulesXmlWriter());
		}
	}
}
