package org.ravenbuild.config;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import java.util.List;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class BuildConfigurationTest {
	private BuildConfiguration buildConfiguration;
	
	@Before
	public void setup() {
		buildConfiguration = new BuildConfiguration();
	}
	
	@Test
	public void notifiesListenerAboutConfigurationLoaded() {
		buildConfiguration.loadFromString("{ \"sectionName\": {}}");
		
		final ConfigurationListener<TestConfigType> configurationListener = mock(ConfigurationListener.class);
		buildConfiguration.registerConfigurationListener("sectionName", TestConfigType.class, configurationListener);
		buildConfiguration.processConfigurationForListeners();
		
		verify(configurationListener).configurationLoaded(any(TestConfigType.class));
	}
	
	@Test
	public void canLoadComplexObjectFromConfigurationSection() {
		buildConfiguration.loadFromString("{ \"sectionName\": {" +
				"\"strValue\": \"some string\"," +
				"\"intValue\": \"5\"," +
				"\"list\": [\"one\", \"two\", \"three\"]," +
				"\"subObject\": {" +
				"    \"foo\": \"bar\"" +
				"}" +
				"}}");
		
		final ConfigurationListener<ComplextTestConfigType> configurationListener = mock(ConfigurationListener.class);
		buildConfiguration.registerConfigurationListener("sectionName", ComplextTestConfigType.class, configurationListener);
		buildConfiguration.processConfigurationForListeners();
		
		final ArgumentCaptor<ComplextTestConfigType> captor = ArgumentCaptor.forClass(ComplextTestConfigType.class);
		verify(configurationListener).configurationLoaded(captor.capture());
		
		ComplextTestConfigType config = captor.getValue();
		assertThat(config.strValue, is("some string"));
		assertThat(config.intValue, is(5));
		assertThat(config.list, hasItems("one", "two", "three"));
		
		assertThat(config.subObject, is(notNullValue()));
		assertThat(config.subObject.foo, is("bar"));
	}
	
	@Test
	@Ignore("FIXME: Should report a sensible error message if there is no listener for a given section")
	public void reportsAnErrorIfThereIsNoListenerForASection() {
		fail("Implement me!");
	}
	
	@Test
	@Ignore("FIXME: Should report a meaningful error message the config type for a section does not contain a field for a json property")
	public void reportsAnErrorIfThereIsNoConfigFieldForJsonProperty() {
		fail("Implement me!");
	}
	
	@Test
	@Ignore("FIXME: Should report NO error if there is a listener for which no config section is available")
	public void reportsNoErrorIfThereIsNoSectionForAListener() {
		fail("Implement me!");
	}
	
	@Test
	@Ignore("FIXME: Should still call the listener (with a default config object) if there is a listener for which no config section is available")
	public void callsTheListenerWithDefaultConfigIfThereIsNoSectionForAListener() {
		fail("Implement me!");
	}
	
	private static class TestConfigType {
	}
	
	private static class ComplextTestConfigType {
		private String strValue;
		private int intValue;
		private List<String> list;
		private SubObject subObject;
	}
	
	private static class SubObject {
		private String foo;
	}
}