package com.ravensuite.ravenxml;

import net.davidtanzer.jdefensive.Args;

import java.util.ArrayList;
import java.util.List;

public abstract class Element implements Node {
	private static final String DEFAULT_INDENT_LEVEL = "  ";
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
		render(renderedResultBuilder, "");
		return renderedResultBuilder.toString();
	}
	
	@Override
	public void render(final StringBuilder renderedResultBuilder, final String indent) {
		renderOpenTag(renderedResultBuilder, indent);
		renderChildren(renderedResultBuilder, indent+DEFAULT_INDENT_LEVEL);
		renderCloseTag(renderedResultBuilder, indent);
	}
	
	private void renderOpenTag(final StringBuilder renderedResultBuilder, final String indent) {
		renderedResultBuilder.append(indent);
		renderedResultBuilder.append("<");
		renderedResultBuilder.append(tagName.value());
		attributes.render(renderedResultBuilder);
		if(children.isEmpty()) {
			renderedResultBuilder.append("/>\n");
		} else {
			renderedResultBuilder.append(">\n");
		}
	}
	
	private void renderCloseTag(final StringBuilder renderedResultBuilder, final String indent) {
		if(!children.isEmpty()) {
			renderedResultBuilder.append(indent);
			renderedResultBuilder.append("</");
			renderedResultBuilder.append(tagName.value());
			renderedResultBuilder.append(">\n");
		}
	}

	private void renderChildren(final StringBuilder renderedResultBuilder, final String indent) {
		for(Node child : children) {
			child.render(renderedResultBuilder, indent);
		}
	}
}
