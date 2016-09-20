package com.ravensuite.value;

import net.davidtanzer.jdefensive.Args;

public abstract class SingleValue<T> {
	private final T value;
	
	protected SingleValue(T value) {
		Args.notNull(value, "value");
		this.value = value;
	}
	
	public T value() {
		return value;
	}
	
	@Override
	public boolean equals(final Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		
		SingleValue<?> that = (SingleValue<?>) o;
		
		return value.equals(that.value);
		
	}
	
	@Override
	public int hashCode() {
		return value.hashCode();
	}
	
	@Override
	public String toString() {
		return getClass().getSimpleName()+" ("+value+")";
	}
}
