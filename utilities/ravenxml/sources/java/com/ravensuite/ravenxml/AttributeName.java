package com.ravensuite.ravenxml;

import com.ravensuite.value.StringValue;

public class AttributeName extends StringValue {
	protected AttributeName(final String value) {
		super(value);
	}

	public static AttributeName of(final String name) {
		return new AttributeName(name);
	}
}
