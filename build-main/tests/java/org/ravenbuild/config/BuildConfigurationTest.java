package org.ravenbuild.config;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import java.util.List;
import java.util.Map;

import static org.hamcrest.CoreMatchers.*;
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
	public void canPutConfigurationMapIntoInnerConfigValuesField() {
		buildConfiguration.loadFromString("{ \"sectionName\": {" +
				"\"foo\": [ \"1\", \"2\" ], " +
				"\"bar\": [ \"3\", \"4\" ] " +
				"}}");
		
		final ConfigurationListener<ConfigValuesTestConfigType> configurationListener = mock(ConfigurationListener.class);
		buildConfiguration.registerConfigurationListener("sectionName", ConfigValuesTestConfigType.class, configurationListener);
		buildConfiguration.processConfigurationForListeners();
		
		final ArgumentCaptor<ConfigValuesTestConfigType> captor = ArgumentCaptor.forClass(ConfigValuesTestConfigType.class);
		verify(configurationListener).configurationLoaded(captor.capture());
		
		ConfigValuesTestConfigType config = captor.getValue();
		assertThat(config.configValues, is(notNullValue()));
		assertThat(config.configValues.get("foo"), hasItems("1", "2"));
		assertThat(config.configValues.get("bar"), hasItems("3", "4"));
	}
	
	@Test
	@Ignore("FIXME: Should report a meaningful error message if there is no listener for a given section")
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
	
	public static class TestConfigType {
	}
	
	public static class ComplextTestConfigType {
		private String strValue;
		private int intValue;
		private List<String> list;
		private SubObject subObject;
	}
	
	public static class SubObject {
		private String foo;
	}
	
	public static class ConfigValuesTestConfigType {
		private Map<String, List<String>> configValues;
	}
}