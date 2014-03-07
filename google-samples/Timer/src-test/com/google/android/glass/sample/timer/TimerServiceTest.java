package com.google.android.glass.sample.timer;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricService;
import org.robolectric.annotation.Config;
import org.robolectric.bytecode.ShadowWrangler;
import org.robolectric.shadows.ServiceInstantiator;
import org.robolectric.shadows.ShadowApplication;
import org.robolectric.shadows.glass.RoboGlassTestRunner;
import org.robolectric.shadows.glass.ShadowGlassTypeface;
import org.robolectric.shadows.glass.ShadowLiveCard;
import org.robolectric.shadows.glass.ShadowTimelineManager;

@Config(shadows = {ShadowGlassTypeface.class, ShadowTimelineManager.class, ShadowLiveCard.class}, 
		emulateSdk = 16)
@RunWith(RoboGlassTestRunner.class)
public class TimerServiceTest {
	
	TimerService sut;
	
	@Before
	public void setup() {
		ShadowWrangler.debug = true;
		
		// Create and start the service
		sut = new TimerService();
		new RobolectricService<TimerService>(sut).create().start();
		
		// Create the surface
		ShadowLiveCard shadowLiveCard = Robolectric.shadowOf_(sut.getLiveCard());
		shadowLiveCard.notifySurfaceCreated();

		ShadowApplication app = Robolectric.getShadowApplication();
		app.setServiceInstantiator(new ServiceInstantiator());
	}

	@Test
	public void timer_displays_0_when_first_started() {
		
		
		
	}

}
