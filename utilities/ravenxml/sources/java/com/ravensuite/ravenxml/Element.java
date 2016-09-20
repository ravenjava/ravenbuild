package com.ravensuite.ravenxml;

import net.davidtanzer.jdefensive.Args;

import java.util.ArrayList;
import java.util.List;

public abstract class Element implements Node {
	private final List<Node> children = new ArrayList<>();
	private Attributes attributes = new Attributes();
	private final TagName tagName;
	
	protected Element(final TagName tagName) {
		Args.notNull(tagName, "tagName");
		Args.notEmpty(tagName.value(), "tagName.value");
		
		this.tagName = tagName;
	}
	
	protected void add(final Node... nodes) {
		for(Node node : nodes) {
			children.add(node);
		}
	}
	
	protected void removeAllChildren() {
		children.clear();
	}
	
	protected void setAttribute(final AttributeName attributeName, final AttributeValue attributeValue) {
		attributes.add(attributeName, attributeValue);
	}
	
	@Override
	public String render() {
		StringBuilder renderedResultBuilder = new StringBuilder();
		render(renderedResultBuilder);
		return renderedResultBuilder.toString();
	}
	
	@Override
	public void render(final StringBuilder renderedResultBuilder) {
		renderOpenTag(renderedResultBuilder);
		renderChildren(renderedResultBuilder);
		renderCloseTag(renderedResultBuilder);
	}
	
	private void renderOpenTag(final StringBuilder renderedResultBuilder) {
		renderedResultBuilder.append("<");
		renderedResultBuilder.append(tagName.value());
		attributes.render(renderedResultBuilder);
		renderedResultBuilder.append(">");
	}
	
	private void renderCloseTag(final StringBuilder renderedResultBuilder) {
		renderedResultBuilder.append("</");
		renderedResultBuilder.append(tagName.value());
		renderedResultBuilder.append(">");
	}

	private void renderChildren(final StringBuilder renderedResultBuilder) {
		for(Node child : children) {
			child.render(renderedResultBuilder);
		}
	}
}
