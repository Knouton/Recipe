package com.example.Recipe.events.listener;

import com.example.Recipe.events.CustomSpringEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class CustomSpringEventListener implements ApplicationListener<CustomSpringEvent> {
	@Override
	public void onApplicationEvent(CustomSpringEvent event) {
		System.out.println("Received spring custom event - " + event.getMessage());
	}
}
