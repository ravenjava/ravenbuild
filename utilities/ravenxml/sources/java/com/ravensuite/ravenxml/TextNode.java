package com.ravensuite.ravenxml;

import com.ravensuite.value.StringValue;
import net.davidtanzer.jdefensive.Args;

public class TextNode implements Node {
	private final String text;
	
	public TextNode(final StringValue text) {
		Args.notNull(text, "text");
		this.text = text.value();
	}
	
	@Override
	public String render() {
		StringBuilder renderedResultBuilder = new StringBuilder();
		render(renderedResultBuilder, "");
		return renderedResultBuilder.toString();
	}
	
	@Override
	public void render(final StringBuilder renderedResultBuilder, final String indent) {
		renderedResultBuilder.append(indent);
		renderedResultBuilder.append(text);
	}
}
