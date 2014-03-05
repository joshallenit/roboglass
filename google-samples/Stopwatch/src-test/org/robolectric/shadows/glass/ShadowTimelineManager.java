package org.robolectric.shadows.glass;

import org.robolectric.Robolectric;
import org.robolectric.annotation.Implementation;
import org.robolectric.annotation.Implements;
import org.robolectric.internal.Instrument;

import android.content.Context;

import com.google.android.glass.timeline.LiveCard;
import com.google.android.glass.timeline.TimelineManager;

@Implements(TimelineManager.class)
public class ShadowTimelineManager {
	
	@Implementation
	public static TimelineManager from(Context context) {
		// This is actually going to return a ShadowTimelineManager because of the Robolectric class loader. 
		return Robolectric.newInstanceOf(TimelineManager.class);
	}
	
	@Implementation
	public LiveCard createLiveCard(java.lang.String tag) {
		return Robolectric.newInstanceOf(LiveCard.class);
	}

}
