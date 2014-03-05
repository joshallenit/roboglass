package org.robolectric.shadows.glass;

import org.junit.runners.model.InitializationError;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.SdkConfig;
import org.robolectric.bytecode.ClassHandler;
import org.robolectric.bytecode.ClassInfo;
import org.robolectric.bytecode.Setup;
import org.robolectric.bytecode.ShadowGlassWrangler;
import org.robolectric.bytecode.ShadowMap;
import org.robolectric.bytecode.ShadowWrangler;
import org.robolectric.internal.DoNotInstrument;
import org.robolectric.internal.Instrument;

import com.google.android.glass.touchpad.Gesture;
import com.xtremelabs.utilities.Logger;

public class RoboGlassTestRunner extends RobolectricTestRunner {
	
	public RoboGlassTestRunner(Class<?> testClass) throws InitializationError {
		super(testClass);
		Logger.d("two");
		System.out.println("two");
	}
	
	@Override
	protected ClassHandler createClassHandler(ShadowMap shadowMap) {
		Logger.d("three");
		System.out.println("three");
		return new ShadowGlassWrangler(shadowMap, createSetup());
	}
	
	@Override
	public Setup createSetup() {
	    return new Setup() {
	    	@Override
	    	public boolean containsStubs(ClassInfo classInfo) {
	    		boolean val = super.containsStubs(classInfo) || isStubbedGdk(classInfo.getName());
//	    		System.out.println("containsStubs "+classInfo.getName()+" = "+val);
	    		return val;
	    	}
	    	
	    	@Override
	    	public boolean isFromAndroidSdk(ClassInfo classInfo) {
	    	    boolean val = super.isFromAndroidSdk(classInfo) || isStubbedGdk(classInfo.getName());
//	    	    System.out.println("isFromAndroidSdk "+classInfo.getName()+" = "+val);
	    	    return val;
	    	}

	    	@Override
	    	public boolean shouldAcquire(String name) {
	    	    boolean val = super.shouldAcquire(name);
//	    	    System.out.println("shouldAcquire "+name+" = "+val);
	    	    return val;
	    	}
	    	
	    	@Override
	    	public boolean shouldInstrument(ClassInfo classInfo) {
	    		boolean val = super.shouldInstrument(classInfo);
//	    		System.out.println("shouldInstrument "+classInfo.getName()+" = "+val);
	    		return val;
	    	}
	    	
	    	public boolean isStubbedGdk(String className) {
	    		return className.startsWith("com.google.android.glass.")
					&& !className.startsWith("com.google.android.glass.sample")
					&& !className.equals(com.google.android.glass.touchpad.Gesture.class.getName());
	    	}
	    };
	}

}
