package com.google.android.glass.sample.timer;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.annotation.Config;
import org.robolectric.bytecode.ShadowWrangler;
import org.robolectric.shadows.ServiceInstantiator;
import org.robolectric.shadows.ShadowApplication;
import org.robolectric.shadows.glass.RoboGlassTestRunner;
import org.robolectric.shadows.glass.ShadowLiveCard;
import org.robolectric.tester.android.view.TestMenuItem;

import android.app.Service;
import android.content.Intent;
import android.widget.TextView;

/**
 * The way Timer works is that it keeps recursively calling handler.post(listener, DELAY_MILLIS), and updates the text
 * view every time. 
 */
@Config(emulateSdk = 16)
@RunWith(RoboGlassTestRunner.class)
public class MenuActivityTest {
	
	MenuActivity sut;
	TimerService timerService;
	
	@Before
	public void setup() {
		ShadowWrangler.debug = false;
		
		// Save our service when it is created so we can notify listeners that the surface was created.
		ShadowApplication app = Robolectric.getShadowApplication();
		app.setServiceInstantiator(new ServiceInstantiator() {
			@Override
			public Service instantiateService(Intent intent) {
				Service service = super.instantiateService(intent);
				if (service instanceof TimerService) {
					timerService = (TimerService) service;
				}
				return service;
			}
		});
		
		// Create our Activity
		sut = Robolectric.buildActivity(MenuActivity.class).create().start().resume().visible().get();
		
		// Create the surface
		ShadowLiveCard shadowLiveCard = Robolectric.shadowOf_(timerService.getLiveCard());
		shadowLiveCard.notifySurfaceCreated();
	}

	@Test
	public void initial_display_is_0() {
		assertDisplayEquals("00", "00", "00");
	}
	
	@Test
	public void display_does_not_increment_if_start_is_not_called() {
		Robolectric.getUiThreadScheduler().advanceBy(1000);
		assertDisplayEquals("00", "00", "00");
	}
	
	@Test
	public void display_increments_by_seconds_after_start() {
		sut.onOptionsItemSelected(new TestMenuItem(R.id.start));
		Robolectric.getUiThreadScheduler().advanceBy(1000);
		assertDisplayEquals("00", "00", "01");
	}
	
	@Test
	public void display_increments_by_minutes_after_start() {
		sut.onOptionsItemSelected(new TestMenuItem(R.id.start));
		Robolectric.getUiThreadScheduler().advanceBy(1000*60);
		assertDisplayEquals("00", "01", "00");
	}
	
	@Test
	public void display_increments_by_hours_after_start() {
		sut.onOptionsItemSelected(new TestMenuItem(R.id.start));
		Robolectric.getUiThreadScheduler().advanceBy(1000*60*60);
		assertDisplayEquals("01", "00", "00");
	}
	
	@Test
	public void display_increments_by_time_after_start() {
		sut.onOptionsItemSelected(new TestMenuItem(R.id.start));
		Robolectric.getUiThreadScheduler().advanceBy(1000*60*60*2+1000*60*23+1000*17);
		assertDisplayEquals("02", "23", "17");
	}
	
	@Test
	public void display_does_not_increment_after_pause() {
		sut.onOptionsItemSelected(new TestMenuItem(R.id.start));
		Robolectric.getUiThreadScheduler().advanceBy(1000*1);
		sut.onOptionsItemSelected(new TestMenuItem(R.id.pause));
		Robolectric.getUiThreadScheduler().advanceBy(1000*1);
		assertDisplayEquals("00", "00", "01");
	}
	
	private void assertDisplayEquals(String hours, String minutes, String seconds) {
		TimerView view = timerService.getTimerDrawer().getView();
		TextView hoursView = (TextView) view.findViewById(R.id.hours);
		TextView minutesView = (TextView) view.findViewById(R.id.minutes);
		TextView secondsView = (TextView) view.findViewById(R.id.seconds);
		assertEquals(hours, hoursView.getText().toString());
		assertEquals(minutes, minutesView.getText().toString());
		assertEquals(seconds, secondsView.getText().toString());
	}

}
