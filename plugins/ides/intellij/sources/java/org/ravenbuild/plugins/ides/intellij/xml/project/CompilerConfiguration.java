package org.ravenbuild.plugins.ides.intellij.xml.project;

import org.ravenbuild.plugins.ides.intellij.xml.Component;

public class CompilerConfiguration extends Component {
	public CompilerConfiguration() {
		super(new ComponentName("CompilerConfiguration"));
	}
	
	public void add(final CompilerConfigurationSection compilerConfigurationSection) {
		super.add(compilerConfigurationSection);
	}
}
