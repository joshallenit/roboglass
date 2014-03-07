package org.robolectric.shadows.glass;

import org.robolectric.annotation.Implementation;
import org.robolectric.annotation.Implements;
import org.robolectric.internal.Instrument;

import com.google.android.glass.timeline.LiveCard;

/**
 * 
 */
@Implements(LiveCard.PublishMode.class)
public class ShadowPublishMode {
	
	public static final ShadowPublishMode REVEAL = new ShadowPublishMode();
	public static final ShadowPublishMode SILENT = new ShadowPublishMode();
	
	@Implementation
	public static ShadowPublishMode[] values() {
		return new ShadowPublishMode[] {REVEAL, SILENT};
	}
	
	@Implementation
	public static ShadowPublishMode valueOf(String name) {
		if ("REVEAL".equals(name)) {
			return REVEAL;
		}
		if ("SILENT".equals(name)) {
			return SILENT;
		} 
		throw new IllegalArgumentException("Unknown PublishMode "+name);
	}
	
	@Implementation
	public void __constructor__() {
	}

}
