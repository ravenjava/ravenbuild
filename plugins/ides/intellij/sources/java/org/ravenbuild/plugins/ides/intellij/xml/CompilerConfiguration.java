package org.ravenbuild.plugins.ides.intellij.xml;

public class CompilerConfiguration extends Component {
	public CompilerConfiguration() {
		super(new ComponentName("CompilerConfiguration"));
	}
	
	public void add(final CompilerConfigurationSection compilerConfigurationSection) {
		super.add(compilerConfigurationSection);
	}
}
