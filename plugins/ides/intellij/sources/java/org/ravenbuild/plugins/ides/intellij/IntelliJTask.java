package org.ravenbuild.plugins.ides.intellij;

import net.davidtanzer.jdefensive.Args;
import org.ravenbuild.environment.BuildEnvironment;
import org.ravenbuild.plugins.dependencies.DependenciesTask;
import org.ravenbuild.plugins.help.LongDescription;
import org.ravenbuild.plugins.help.ShortDescription;
import org.ravenbuild.projectinfo.AllProjects;
import org.ravenbuild.projectinfo.ProjectInfo;
import org.ravenbuild.tasks.EmptyTaskOptions;
import org.ravenbuild.tasks.Task;
import org.ravenbuild.tasks.TaskContext;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@ShortDescription("Writes IntelliJ IDEA project files for the current project.")
@LongDescription({
		"Use this task to write the IntelliJ IDEA project files for the current project.",
		"After running this task, you can import / re-import the project in IntelliJ",
		"IDEA."
})
public class IntelliJTask implements Task<EmptyTaskOptions> {
	private final List<ProjectDataProvider> projectDataProviders = new ArrayList<>();
	private final BuildEnvironment buildEnvironment;
	private final AllProjects allProjects;
	private DependenciesTask dependenciesTask;
	private ProjectInfo projectInfo;
	
	public IntelliJTask(final BuildEnvironment buildEnvironment, final AllProjects allProjects) {
		Args.notNull(buildEnvironment, "buildEnvironment");
		Args.notNull(allProjects, "allProjects");
		
		this.buildEnvironment = buildEnvironment;
		this.allProjects = allProjects;
	}
	
	@Override
	public void initialize(final TaskContext taskContext) {
		dependenciesTask = taskContext.dependsOn("dependencies");
		
		projectInfo = taskContext.projectInfo();
	}
	
	@Override
	public void run(final EmptyTaskOptions taskOptions) {
		boolean isRootModule = !projectInfo.getParent().isPresent();

		List<ModuleDataProvider> moduleDataProviders = projectDataProviders.stream()
				.map(pdp -> pdp.moduleDataProvider())
				.filter(mdp -> mdp.isPresent())
				.map(mdp -> mdp.get())
				.collect(Collectors.toList());
		buildEnvironment.writeFile(projectInfo.getProjectName()+".iml", new ImlFileWriter(moduleDataProviders));
		
		if(isRootModule) {
			List<CompilerConfiguraitonProvider> compilerConfigurationProviders = projectDataProviders.stream()
					.map(pdp -> pdp.compilerConfigurationProvider())
					.filter(ccp -> ccp.isPresent())
					.map(ccp -> ccp.get())
					.collect(Collectors.toList());
			buildEnvironment.writeFile(".idea/compiler.xml", new CompilerXmlWriter(compilerConfigurationProviders));
			buildEnvironment.writeFile(".idea/modules.xml", new ModulesXmlWriter(allProjects, buildEnvironment.buildBaseDirectory()));
			buildEnvironment.writeFile(".idea/misc.xml", new MiscXmlWriter());
		}
	}
	
	public void addProjectDataProvider(final ProjectDataProvider projectDataProvider) {
		Args.notNull(projectDataProvider, "projectDataProvider");
		
		projectDataProviders.add(projectDataProvider);
	}
}
