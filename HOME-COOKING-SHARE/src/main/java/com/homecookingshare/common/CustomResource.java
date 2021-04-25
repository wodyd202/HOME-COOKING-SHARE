package com.homecookingshare.common;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

import org.springframework.hateoas.Resource;

public class CustomResource<T> extends Resource<T>{
	
	public CustomResource(T content, Class<?> classes) {
		super(content);
		add(linkTo(classes).withSelfRel());
	}
	
}
