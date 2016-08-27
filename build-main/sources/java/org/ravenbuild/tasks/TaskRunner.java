package org.ravenbuild.tasks;

import net.davidtanzer.jdefensive.Args;

import java.lang.reflect.Field;
import java.util.Map;

public class TaskRunner {
	public <T> void run(final Task<T> task, final Class<T> taskOptionsType, final Map<String, String> taskOptionsMap) {
		Args.notNull(task, "task");
		
		final T taskOptions = buildTaskOptions(taskOptionsType, taskOptionsMap);
		task.run(taskOptions);
	}
	
	private <T> T buildTaskOptions(final Class<T> taskOptionsType, final Map<String, String> taskOptionsMap) {
		try {
			final T taskOptions = taskOptionsType.newInstance();
			
			final Field[] fields = taskOptionsType.getDeclaredFields();
			for(Field field : fields) {
				if(taskOptionsMap.containsKey(field.getName())) {
					final String fieldStringValue = taskOptionsMap.get(field.getName());
					
					boolean fieldAccessible = field.isAccessible();
					field.setAccessible(true);
					final Object fieldValue = convertFieldValue(field.getType(), fieldStringValue);
					field.set(taskOptions, fieldValue);
					field.setAccessible(fieldAccessible);
				}
			}
			
			return taskOptions;
		} catch (InstantiationException | IllegalAccessException e) {
			throw new IllegalStateException("Could not instantiate task options class \""+taskOptionsType+"\".", e);
		}
	}
	
	private Object convertFieldValue(final Class<?> type, final String fieldStringValue) {
		if(type.equals(Boolean.class) || type.equals(boolean.class)) {
			if(fieldStringValue.equals("true")) {
				return true;
			}
			return false;
		} else if(type.equals(Integer.class) || type.equals(int.class)) {
			return Integer.parseInt(fieldStringValue);
		} else if(type.equals(String.class)) {
			return fieldStringValue;
		}
		throw new IllegalStateException("FIXME: Cannot convert the field value, handle this error correctly.");
	}
}
