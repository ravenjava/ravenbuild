package org.ravenbuild.plugins.ides.intellij.java;

import org.ravenbuild.plugins.ides.intellij.CompilerConfiguraitonProvider;

import java.util.Arrays;
import java.util.List;

public class JavaCompilerConfigurationProvider implements CompilerConfiguraitonProvider {
	@Override
	public List<String> wildcardPatterns() {
		return Arrays.asList("*.java");
	}
}
