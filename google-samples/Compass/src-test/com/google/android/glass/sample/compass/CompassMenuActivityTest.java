package com.google.android.glass.sample.compass;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ServiceInstantiator;
import org.robolectric.shadows.ShadowApplication;
import org.robolectric.shadows.glass.RoboGlassTestRunner;
import org.robolectric.shadows.glass.ShadowLiveCard;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.TestSensorManager;
import android.view.View;
import android.view.ViewGroup;

@SuppressLint("ServiceCast")
@Config(emulateSdk=16)
@RunWith(RoboGlassTestRunner.class)
public class CompassMenuActivityTest {
	
	CompassMenuActivity sut;
	CompassService compassService;
	Context context = Robolectric.getShadowApplication().getApplicationContext();
	
	@Before
	public void setup() {
		
	  // Save our service when it is created so we can notify listeners that the surface was created.
	  ShadowApplication app = Robolectric.getShadowApplication();
    app.setServiceInstantiator(new ServiceInstantiator() {
      @Override
      public Service instantiateService(Intent intent) {
        Service service = super.instantiateService(intent);
        if (service instanceof CompassService) {
          compassService = (CompassService) service;
        }
        return service;
      }
    });
    
    // Create our Activity
    sut = Robolectric.buildActivity(CompassMenuActivity.class).create().start().resume().visible().get();
    
    // Create the surface
    ShadowLiveCard shadowLiveCard = Robolectric.shadowOf_(compassService.getLiveCard());
    shadowLiveCard.notifySurfaceCreated();
	}
	
	@Test
	public void tooltip_not_displayed_when_looking_straight() {

	  // Look straight ahead
	  TestSensorManager sensors = (TestSensorManager) context.getSystemService(Context.SENSOR_SERVICE);
	  float[] rotation = {0, 0, 0, 0};
    SensorEvent event = sensors.createSensorEvent(rotation, Sensor.TYPE_ROTATION_VECTOR);
    
    sensors.notifySensorChanged(event);
    
    ViewGroup layout = compassService.getRenderer().getLayout();
    // The tooltips show/hide is done by the alpha property
    View tips = layout.findViewById(R.id.tips_view);
    assertEquals(0f, tips.getAlpha(), 0f);
	}
	
	@Test
  public void tooltip_displayed_when_looking_too_far_down() {
    
  }

}
