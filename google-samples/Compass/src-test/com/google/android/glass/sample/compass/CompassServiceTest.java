package com.google.android.glass.sample.compass;

import static org.junit.Assert.assertEquals;
import static org.robolectric.Robolectric.shadowOf;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowContextImpl;
import org.robolectric.shadows.ShadowSensorManager;
import org.robolectric.shadows.ShadowSurfaceView;
import org.robolectric.shadows.ShadowSurfaceView.FakeSurfaceHolder;
import org.robolectric.shadows.glass.ShadowGlassTypeface;
import org.robolectric.shadows.glass.ShadowPublishMode;
import org.robolectric.shadows.glass.ShadowTimelineManager;

import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.hardware.RoboGlassSensorManager;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.RemoteViews;

import com.google.android.glass.sample.timeline.TimelineManagerFactory;
import com.google.android.glass.timeline.LiveCard;
import com.google.android.glass.timeline.TimelineManager;
import com.pivotallabs.logging.SystemOutLogger;
import com.xtremelabs.utilities.Logger;

import org.robolectric.shadows.glass.*; 

@Config(shadows = {ShadowGlassTypeface.class, ShadowPublishMode.class})
@RunWith(RoboGlassTestRunner.class)
public class CompassServiceTest {
	
	private CompassService sut;
	
	static {
		Logger.setInstance(new SystemOutLogger());
	}
	
	Context context = Robolectric.getShadowApplication().getApplicationContext();
	private SurfaceView surfaceView;
	
	@Before
	public void setup_services() {
		//((ShadowContextImpl) shadowOf(context)).setSystemService(Context.SENSOR_SERVICE, service);
		Robolectric.getShadowApplication().setSystemService(Context.SENSOR_SERVICE, new RoboGlassSensorManager());
	}

	@Before
	public void setup() throws Exception {
		setupLogger();
		Logger.d("one");
		System.out.println("one");
		final TimelineManager timeline = Mockito.mock(TimelineManager.class);
		LiveCard card = Mockito.mock(LiveCard.class);
		
		Mockito.when(card.setViews(Mockito.any(RemoteViews.class))).thenReturn(card);
		Mockito.when(card.setAction(Mockito.any(PendingIntent.class))).thenReturn(card);
		Mockito.when(card.setDirectRenderingEnabled(Mockito.any(Boolean.class))).thenReturn(card);
		
		surfaceView = new SurfaceView(context);
		Mockito.when(card.getSurfaceHolder()).thenReturn(surfaceView.getHolder());
		
		Mockito.when(timeline.createLiveCard(Mockito.any(String.class))).thenReturn(card);
		
		TimelineManagerFactory.setFactory(new TimelineManagerFactory() {
			public TimelineManager createInstance(Context context) {
				return timeline;
			}
		});
		
		sut = new CompassService();
		sut.attachBaseContext(context);
	}

	@Test
	public void onStartCommand_returns_sticky() {
		
		// I created the service using this suggestion: http://stackoverflow.com/a/12934567/1596587
		
		sut.onCreate();
//		Object me = TimelineManager.from(context);
		Intent intent = new Intent(context, CompassService.class);
		int mode = sut.onStartCommand(intent, 0, 0);
		assertEquals(Service.START_STICKY, mode);
	}
	
	@Test
	public void listeners_are_started_when_surface_is_created() {
		sut.onCreate();
//		Object me = TimelineManager.from(context);
		Intent intent = new Intent(context, CompassService.class);
		sut.onStartCommand(intent, 0, 0);
		ShadowSurfaceView shadowSurface = (ShadowSurfaceView) Robolectric.shadowOf(surfaceView);
		FakeSurfaceHolder holder = shadowSurface.getFakeSurfaceHolder();
		for (SurfaceHolder.Callback callback : holder.getCallbacks()) {
			callback.surfaceCreated(holder);
		}
	}
	
	@SuppressLint("ServiceCast")
	@Test 
	public void listeners_respond_to_touch_events() {
		
		sut.onCreate();
		Intent intent = new Intent(context, CompassService.class);
		sut.onStartCommand(intent, 0, 0);
		
		
		// Add sensor listeners
		ShadowSurfaceView shadowSurface = (ShadowSurfaceView) Robolectric.shadowOf(surfaceView);
		FakeSurfaceHolder holder = shadowSurface.getFakeSurfaceHolder();
		for (SurfaceHolder.Callback callback : holder.getCallbacks()) {
			callback.surfaceCreated(holder);
		}
		
		// Notify sensors have changed.
		RoboGlassSensorManager sensors = (RoboGlassSensorManager) context.getSystemService(Context.SENSOR_SERVICE);
		SensorEvent event = sensors.createSensorEvent(10);
		Sensor sensor = Mockito.mock(Sensor.class);
		Mockito.when(sensor.getType()).thenReturn(Sensor.TYPE_ROTATION_VECTOR);
		event.sensor = sensor;
		event.accuracy = 100;
		event.values[0] = 2;
		event.values[1] = 4;
		event.values[2] = 8;
		event.timestamp = new Date().getTime();
		for (SensorEventListener listener : sensors.getListeners()) {
			listener.onSensorChanged(event);
		}
	}

	@Before
    public void setupLogger() throws Exception {
        Logger.setInstance(new SystemOutLogger());
    }

}
