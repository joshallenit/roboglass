package android.hardware;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.robolectric.Robolectric;
import org.robolectric.annotation.Implementation;
import org.robolectric.annotation.Implements;
import org.robolectric.shadows.ShadowSensorManager;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

/**
 * Exposes the listeners so they can be notified. 
 * This must inherit from SensorManager or you will get a ClassCastException.
 * Note: ShadowSensorManager is useless because Robolectric does not use the Shadow scheme for getSystemService(), see
 *       ShadowContextImpl for how it uses setSystemService() instead.
 */
public class RoboGlassSensorManager extends SensorManager {
	
	public RoboGlassSensorManager() {
	}

	private ArrayList<SensorEventListener> listeners = new ArrayList<SensorEventListener>();

	  public boolean forceListenersToFail = false;
	
	  
	  @Override
	  public boolean registerListener(SensorEventListener listener, Sensor sensor, int rate) {
	    if(forceListenersToFail)
	      return false;
	
	    if(!listeners.contains(listener))
	      listeners.add(listener);
	
	    return true;
	  }
	
	  @Override
	  public void unregisterListener(SensorEventListener listener, Sensor sensor) {
	    listeners.remove(listener);
	  }
	
	  @Override
	  public void unregisterListener(SensorEventListener listener) {
	    listeners.remove(listener);
	  }
	
	  public boolean hasListener(SensorEventListener listener) {
	    return listeners.contains(listener);
	  }
	  
	  public List<SensorEventListener> getListeners() {
		  return Collections.unmodifiableList(listeners);
	  }
	  
	  public SensorEvent createSensorEvent(int size) {
		  try {
		    SensorEvent event = Robolectric.newInstanceOf(SensorEvent.class);
		    // Use reflection to set values as the Stub version of SensorEvent is missing the constructor with a size
		    // parameter. As such there is no other way to set the final field values.
		    Field field = SensorEvent.class.getField("values");
		    field.setAccessible(true);
		    // Remove final modifier, as per http://zarnekow.blogspot.ca/2013/01/java-hacks-changing-final-fields.html
		    Field modifiersField = Field.class.getDeclaredField("modifiers");
		    modifiersField.setAccessible(true);
		    modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);
	
		    field.set(event, new float[size]);
		    return event;
		  } catch (Exception ex) {
			  throw new IllegalArgumentException(ex);
		  }
	  }

}
