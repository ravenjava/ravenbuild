package org.ravenbuild.config;

import com.google.gson.Gson;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;
import java.util.Map;

public class BuildConfiguration {
	private Map configurationMap;
	
	public void load(final String buildConfigFile) {
		Gson gson = new Gson();
		
		try {
			configurationMap = gson.fromJson(new FileReader(buildConfigFile), Map.class);
		} catch (FileNotFoundException e) {
			throw new IllegalStateException("FIXME: Add better error. Could not load config file.", e);
		}
	}
	
	public <T> T getConfigurationFor(final String configurationSection, final Class<T> type) {
		return null;
	}
}
