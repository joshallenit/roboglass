package com.google.android.glass.sample.stopwatch;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricService;
import org.robolectric.Robolectric;
import org.robolectric.annotation.Config;
import org.robolectric.bytecode.ShadowWrangler;
import org.robolectric.shadows.glass.RoboGlassTestRunner;
import org.robolectric.shadows.glass.ShadowGlassTypeface;
import org.robolectric.shadows.glass.ShadowLiveCard;
import org.robolectric.shadows.glass.ShadowPublishMode;
import org.robolectric.shadows.glass.ShadowTimelineManager;

@Config(shadows = {ShadowGlassTypeface.class, ShadowPublishMode.class, ShadowTimelineManager.class, ShadowLiveCard.class})
@RunWith(RoboGlassTestRunner.class)
public class StopwatchServiceTest {
	
	StopwatchService sut;
	
	@Before
	public void setup() {
		ShadowWrangler.debug = true;
		
		// Create and start the service
		sut = new StopwatchService();
		new RobolectricService<StopwatchService>(sut).create().start();
		
		// Create the surface
		ShadowLiveCard shadowLiveCard = Robolectric.shadowOf_(sut.getLiveCard());
		shadowLiveCard.notifySurfaceCreated();
	}

	@Test
	public void displays_initial_time_when_service_started() {
		// Start service
		
	}

}
