package org.robolectric.shadows.glass;

import java.util.Arrays;

import org.robolectric.annotation.Config;
import org.robolectric.annotation.Implementation;
import org.robolectric.annotation.Implements;
import org.robolectric.internal.HiddenApi;
import org.robolectric.shadows.ShadowTypeface;

import android.graphics.Typeface;

@Implements(Typeface.class)
public class ShadowGlassTypeface extends ShadowTypeface {
	
	@HiddenApi @Implementation
	public static int nativeCreateFromFile(String path) {
		if ("/system/glass_fonts/Roboto-Thin.ttf".equals(path)
				|| "/system/glass_fonts/Roboto-Light.ttf".equals(path)) {
			return nativeCreate(path, 0);
		}
		return ShadowTypeface.nativeCreateFromFile(Arrays.asList(path));
	}

}
