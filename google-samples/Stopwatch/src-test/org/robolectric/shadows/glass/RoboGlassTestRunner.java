package org.robolectric.shadows.glass;

import org.junit.runners.model.InitializationError;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.SdkConfig;
import org.robolectric.bytecode.ClassHandler;
import org.robolectric.bytecode.ClassInfo;
import org.robolectric.bytecode.Setup;
import org.robolectric.bytecode.ShadowConfig;
import org.robolectric.bytecode.ShadowMap;
import org.robolectric.bytecode.ShadowWrangler;
import org.robolectric.internal.DoNotInstrument;
import org.robolectric.internal.Instrument;

import com.google.android.glass.touchpad.Gesture;
import com.xtremelabs.utilities.Logger;

public class RoboGlassTestRunner extends RobolectricTestRunner {

  private static ShadowMap mainShadowMap;

  public RoboGlassTestRunner(Class<?> testClass) throws InitializationError {
    super(testClass);
  }

  @Override
  public Setup createSetup() {
    return new Setup() {
      @Override
      public boolean containsStubs(ClassInfo classInfo) {
        return super.containsStubs(classInfo) || isStubbedGdk(classInfo.getName());
      }

      @Override
      public boolean isFromAndroidSdk(ClassInfo classInfo) {
        return super.isFromAndroidSdk(classInfo) || isStubbedGdk(classInfo.getName());
      }

      public boolean isStubbedGdk(String className) {
        return className.startsWith("com.google.android.glass.")
            && !className.startsWith("com.google.android.glass.sample")
            && !className.equals(com.google.android.glass.touchpad.Gesture.class.getName());
      }
    };
  }

  @Override
  protected ShadowMap createShadowMap() {
    synchronized (RoboGlassTestRunner.class) {
      if (mainShadowMap == null) {
        mainShadowMap = new GlassShadowMap.Builder().build();
      }
      return mainShadowMap;
    }
  }

}
