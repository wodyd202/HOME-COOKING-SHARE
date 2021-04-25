package com.homecookingshare;

import java.util.Arrays;
import java.util.List;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationListener;

@SuppressWarnings("unchecked")
public class MockApplicationEventPublisher implements ApplicationEventPublisher {

	@SuppressWarnings("rawtypes")
	private final List<ApplicationListener> eventHandler;

	@Override
	public void publishEvent(Object event) {
		for (int i = 0; i < eventHandler.size(); i++) {
			eventHandler.get(i).onApplicationEvent((ApplicationEvent) event);
		}
	}

	public MockApplicationEventPublisher(ApplicationListener<?>... applicationListeners) {
		this.eventHandler = Arrays.asList(applicationListeners);
	}
}
