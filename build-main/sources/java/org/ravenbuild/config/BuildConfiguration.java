package org.ravenbuild.config;

import com.google.gson.Gson;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

public class BuildConfiguration {
	private final Map<String, Object> configurationMap;
	private final Map<String, ConfigurationListenerInfo> configurationListeners = new HashMap<>();
	
	public BuildConfiguration() {
		configurationMap = new HashMap<>();
	}
	
	public BuildConfiguration(final Map<String, Object> parentConfiguration) {
		this();
		configurationMap.putAll(parentConfiguration);
	}
	
	public void load(final String buildConfigFile) {
		Gson gson = new Gson();
		
		try {
			configurationMap.putAll(gson.fromJson(new FileReader(buildConfigFile), Map.class));
		} catch (FileNotFoundException e) {
			//Nothing to do here (except maybe log a verbose message that the file was not loaded?)
		}
	}
	
	void loadFromString(final String json) {
		Gson gson = new Gson();
		configurationMap.putAll(gson.fromJson(json, Map.class));
	}
	
	public <T> void registerConfigurationListener(final String sectionName, final Class<T> configType, final ConfigurationListener<T> configurationListener) {
		configurationListeners.put(sectionName, new ConfigurationListenerInfo(configType, configurationListener));
	}
	
	/**
	 * @Deprecated Use only internally in build-main.
	 */
	@Deprecated
	public <T> T getConfigurationFor(final String configurationSection, final Class<T> type) {
		return (T) configurationMap.get(configurationSection);
	}
	
	public void processConfigurationForListeners() {
		for(String configurationSection : configurationMap.keySet()) {
			ConfigurationListenerInfo configurationListenerInfo = configurationListeners.get(configurationSection);
			
			if(configurationListenerInfo != null) {
				configurationListenerInfo.notifyListener(configurationMap.get(configurationSection));
			}
		}
	}
	
	private class ConfigurationListenerInfo<T> {
		private final Class<T> configType;
		private final ConfigurationListener<T> configurationListener;
		
		public ConfigurationListenerInfo(final Class<T> configType, final ConfigurationListener<T> configurationListener) {
			this.configType = configType;
			this.configurationListener = configurationListener;
		}
		
		public void notifyListener(final Object configurationMap) {
			Gson gson = new Gson();
			final String jsonConfig = gson.toJson(configurationMap);
			
			T configuration = gson.fromJson(jsonConfig, configType);
			
			configurationListener.configurationLoaded(configuration);
		}
	}
}
