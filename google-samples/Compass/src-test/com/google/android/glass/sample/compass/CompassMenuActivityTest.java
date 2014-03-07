package com.google.android.glass.sample.compass;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.glass.RoboGlassTestRunner;

@Config(emulateSdk=16)
@RunWith(RoboGlassTestRunner.class)
public class CompassMenuActivityTest {
	
	CompassMenuActivity sut; 
	
	@Before
	public void setup() {
		sut = Robolectric.buildActivity(CompassMenuActivity.class).create().start().resume().visible().get();
	}
	
	@Test
	public void show_tooltip_when_pitch_is_too_high() {
		
	}

}
