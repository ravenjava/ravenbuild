package org.ravenbuild.plugins.ides.intellij;

import net.davidtanzer.jdefensive.Args;
import org.ravenbuild.environment.FileWriterHandler;
import org.ravenbuild.plugins.ides.intellij.xml.*;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class CompilerXmlWriter implements FileWriterHandler {
	private final List<CompilerConfiguraitonProvider> compilerConfigurationProviders;
	
	public CompilerXmlWriter(final List<CompilerConfiguraitonProvider> compilerConfigurationProviders) {
		Args.notNull(compilerConfigurationProviders, "compilerConfigurationProviders");
		this.compilerConfigurationProviders = compilerConfigurationProviders;
	}
	
	@Override
	public void write(final FileWriter fileWriter) throws IOException {
		fileWriter.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
		
		Project project = new Project();
		
		CompilerConfiguration compilerConfiguration = new CompilerConfiguration();
		
		ResourceExtensions resourceExtensions = new ResourceExtensions();
		compilerConfiguration.add(resourceExtensions);
		
		WildcardResourcePatterns wildcardResourcePatterns = new WildcardResourcePatterns();
		compilerConfigurationProviders.stream().forEach(ccp -> addWildcardPattern(wildcardResourcePatterns, ccp));
		compilerConfiguration.add(wildcardResourcePatterns);
		
		AnnotationProcessing annotationProcessing = new AnnotationProcessing();
		Profile defaultProfile = new Profile();
		defaultProfile.add(new ProcessorPath());
		annotationProcessing.add(defaultProfile);
		compilerConfiguration.add(annotationProcessing);
		
		project.addComponent(compilerConfiguration);
		
		fileWriter.write(project.render());
	}
	
	private void addWildcardPattern(final WildcardResourcePatterns wildcardResourcePatterns, final CompilerConfiguraitonProvider compilerConfiguraitonProvider) {
		compilerConfiguraitonProvider.wildcardPatterns().stream().forEach(wp -> wildcardResourcePatterns.addPattern(wp));
	}
}
