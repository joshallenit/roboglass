package com.google.android.glass.sample.compass;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricService;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.glass.RoboGlassTestRunner;
import org.robolectric.shadows.glass.ShadowLiveCard;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.TestSensorManager;

import com.google.android.glass.timeline.LiveCard;

@Config(emulateSdk=16)
@RunWith(RoboGlassTestRunner.class)
public class CompassServiceTest {
	
	CompassService sut;
	Context context = Robolectric.getShadowApplication().getApplicationContext();
	
	@Before
	public void setup() throws Exception {
		sut = new CompassService();
		new RobolectricService<CompassService>(sut).create().start();
		
		LiveCard card = sut.getLiveCard();
    ShadowLiveCard shadow = Robolectric.shadowOf_(card); 
    shadow.notifySurfaceCreated();
	}

	@Test
	public void onStartCommand_returns_sticky() {
		
		Intent intent = new Intent(context, CompassService.class);
		int mode = sut.onStartCommand(intent, 0, 0);
		assertEquals(Service.START_STICKY, mode);
	}
	
	@Test
	public void listeners_are_started_when_surface_is_created() {
		LiveCard card = sut.getLiveCard();
		ShadowLiveCard shadow = Robolectric.shadowOf_(card); 
		shadow.notifySurfaceCreated();
	}
	
	@SuppressLint("ServiceCast")
	@Test 
	public void listeners_respond_to_touch_events() {
		
		// Notify sensors have changed.
		TestSensorManager sensors = (TestSensorManager) context.getSystemService(Context.SENSOR_SERVICE);
		SensorEvent event = sensors.createSensorEvent(4, Sensor.TYPE_ROTATION_VECTOR);
		event.accuracy = 1;
		// Rotation vector component along the x axis (x * sin(O/2)).
		event.values[0] = 0;
		// Rotation vector component along the y axis (y * sin(O/2)).
		event.values[1] = 0;
		// Rotation vector component along the z axis (z * sin(O/2)).
		// 1/4 PI = 90 degrees down
		event.values[2] = (float) Math.PI / 4; 
		// Scalar component of the rotation vector ((cos(O/2)).
		event.values[3] = 0;
		System.out.println("sensors.getListeners() "+sensors+", "+sensors.getListeners());
		
		for (SensorEventListener listener : sensors.getListeners()) {
			listener.onSensorChanged(event);
		}
	}

}
