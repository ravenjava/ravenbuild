package org.ravenbuild.config;

import com.google.gson.Gson;
import net.davidtanzer.jdefensive.Args;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Field;
import java.util.*;

public class BuildConfiguration {
	private final Map<String, ConfigurationListenerInfo> configurationListeners = new HashMap<>();

	private Map<String, Object> configurationMap = Collections.emptyMap();
	private Map<String, Object> parentConfiguration = Collections.emptyMap();
	
	public BuildConfiguration() {
	}
	
	public BuildConfiguration(final Map<String, Object> parentConfiguration) {
		this();
		
		Args.notNull(parentConfiguration, "parentConfiguration");
		this.parentConfiguration = parentConfiguration;
	}
	
	public void load(final String buildConfigFile) {
		Gson gson = new Gson();
		
		try {
			Map<String, Object> loadedConfiguration = gson.fromJson(new FileReader(buildConfigFile), Map.class);
			configurationMap = deepMergeMaps(parentConfiguration, loadedConfiguration);
		} catch (FileNotFoundException e) {
			//Nothing to do here (except maybe log a verbose message that the file was not loaded?)
		}
	}
	
	private Map<String, Object> deepMergeMaps(final Map<String, Object> map1, final Map<String, Object> map2) {
		Map<String, Object> newMap = new HashMap<>();
		newMap.putAll(map1);
		
		for(String key : map2.keySet()) {
			if(newMap.containsKey(key)) {
				Object existingValue = newMap.get(key);
				Object otherValue = map2.get(key);
				
				Object value = getMergedValue(existingValue, otherValue);
				newMap.put(key, value);
			} else {
				newMap.put(key, map2.get(key));
			}
		}
		
		return newMap;
	}
	
	private Object getMergedValue(final Object existingValue, final Object otherValue) {
		if(Map.class.isAssignableFrom(existingValue.getClass()) && Map.class.isAssignableFrom(otherValue.getClass())) {
			return deepMergeMaps((Map<String, Object>) existingValue, (Map<String, Object>) otherValue);
		} else if(List.class.isAssignableFrom(existingValue.getClass()) && List.class.isAssignableFrom(otherValue.getClass())) {
			return deepMergeLists((List) existingValue, (List) otherValue);
		} else {
			return otherValue;
		}
	}
	
	private List deepMergeLists(final List existingValue, final List otherValue) {
		List newList = new ArrayList();
		newList.addAll(existingValue);
		newList.addAll(otherValue);
		return newList;
	}
	
	void loadFromString(final String json) {
		Gson gson = new Gson();
		Map<String, Object> loadedConfiguration = gson.fromJson(json, Map.class);
		configurationMap = deepMergeMaps(parentConfiguration, loadedConfiguration);
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
			
			try {
				setInnerConfigurationValuesField(gson, jsonConfig);
			} catch (NoSuchFieldException e) {
				T configuration = gson.fromJson(jsonConfig, configType);
				configurationListener.configurationLoaded(configuration);
			}
		}
		
		private void setInnerConfigurationValuesField(final Gson gson, final String jsonConfig) throws NoSuchFieldException {
			Field configValuesField = configType.getDeclaredField("configValues");
			boolean wasAccessible = configValuesField.isAccessible();
			configValuesField.setAccessible(true);
			
			try {
				T configuration = configType.newInstance();
				Object configurationValues = gson.fromJson(jsonConfig, configValuesField.getType());
				configValuesField.set(configuration, configurationValues);
				configurationListener.configurationLoaded(configuration);
			} catch (InstantiationException | IllegalAccessException e) {
				throw new IllegalStateException("FIXME: Better error message; Could not instantiate configuration type", e);
			}
			
			configValuesField.setAccessible(wasAccessible);
		}
	}
}
