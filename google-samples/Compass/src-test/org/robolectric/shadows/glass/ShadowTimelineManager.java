package org.robolectric.shadows.glass;

import org.robolectric.annotation.Implementation;
import org.robolectric.annotation.Implements;
import org.robolectric.internal.Instrument;

import android.content.Context;

import com.google.android.glass.timeline.TimelineManager;

@Implements(TimelineManager.class)
//@Instrument
public class ShadowTimelineManager {
	
	@Implementation
	public static TimelineManager from(Context context) {
		throw new RuntimeException("hops");
	}
	
	@Implementation
	public com.google.android.glass.timeline.LiveCard createLiveCard(java.lang.String tag) {
		throw new RuntimeException("hops");
	}
	
	@Implementation
 public long insert(com.google.android.glass.app.Card card) {
	 throw new RuntimeException("hops");
 }

	@Implementation
 public boolean update(long id, com.google.android.glass.app.Card card) {
	 throw new RuntimeException("hops");
 }

	@Implementation
 public com.google.android.glass.app.Card query(long id) {
	 throw new RuntimeException("hops");
 }

	@Implementation
 public boolean delete(long id) {
	 throw new RuntimeException("hops");
	 
 }

}
