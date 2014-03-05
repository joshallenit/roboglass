package org.robolectric;

import java.lang.reflect.Method;

import android.app.Service;
import android.content.Context;
import android.content.Intent;

public class RoblectricService<T extends Service> {
	
	/**
	 * Set object that will be returned by Context.getSystemService(Context.XXX).
	 * See ShadowContextImpl for default values.
	 */
	@SuppressWarnings("deprecation")
	public static void setSystemService(String name, Object serviceManager) {
		Robolectric.getShadowApplication().setSystemService(name, serviceManager);
	}

	private T service;
	
	public RoblectricService(T service) {
		this.service = service;
		if (service.getBaseContext() == null) {
			setContext(Robolectric.getShadowApplication().getApplicationContext());
		}
	}
	
	protected void setContext(Context context) {
		try {
			// service.attachBaseContext(context);
			Method method = Service.class.getMethod("attachBaseContext", new Class[]{Context.class});
			method.setAccessible(true);
			method.invoke(service, new Object[]{context});
		} catch (Exception ex) {
			throw new IllegalArgumentException("Could not attachBaseContext", ex);
		}
	}
	
	public T getService() {
		return service;
	}
	
	public RoblectricService<T> create() {
		service.onCreate();
		return this;
	}
	
	public RoblectricService<T> start() {
		Intent intent = new Intent(Robolectric.getShadowApplication().getApplicationContext(), service.getClass());
		service.onStartCommand(intent, 0, 0);
		return this;
	}

}
