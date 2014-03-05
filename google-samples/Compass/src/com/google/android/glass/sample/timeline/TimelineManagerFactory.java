package com.google.android.glass.sample.timeline;

import android.content.Context;

import com.google.android.glass.timeline.TimelineManager;

public class TimelineManagerFactory {
	
	private static TimelineManagerFactory instance;

	public static synchronized TimelineManagerFactory getFactory() {
		if (instance == null) {
			instance = new TimelineManagerFactory();
		}
		return instance;
	}

	public static synchronized void setFactory(TimelineManagerFactory factory) {
		instance = factory;
	}
	
	public TimelineManager createInstance(Context context) {
		return TimelineManager.from(context);
	}	

}
