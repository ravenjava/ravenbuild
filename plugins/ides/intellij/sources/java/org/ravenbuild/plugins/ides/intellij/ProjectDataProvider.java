package org.ravenbuild.plugins.ides.intellij;

import java.util.Optional;

public interface ProjectDataProvider {
	Optional<CompilerConfiguraitonProvider> compilerConfigurationProvider();
	Optional<ModuleDataProvider> moduleDataProvider();
}
