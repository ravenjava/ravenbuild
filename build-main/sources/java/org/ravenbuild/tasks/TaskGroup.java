package org.ravenbuild.tasks;

import java.util.ArrayList;
import java.util.List;

public class TaskGroup {
	private final String name;
	private final List<Task> tasks = new ArrayList<>();
	
	public TaskGroup(final String name) {
		this.name = name;
	}
}
