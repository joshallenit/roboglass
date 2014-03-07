package org.robolectric.shadows.glass;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.annotation.Config;
import org.robolectric.bytecode.ShadowWrangler;

import com.google.android.glass.timeline.LiveCard.PublishMode;

@Config(emulateSdk = 16)
@RunWith(RoboGlassTestRunner.class)
public class PublishModeTest {
  
  @Before
  public void setup() {
    ShadowWrangler.debug = true;
  }
	
	@Test
	public void REVEAL_is_enum() {
		assertTrue("enum", Enum.class.isAssignableFrom(PublishMode.REVEAL.getClass()));
	}
	
	@Test
	public void REVEAL_equals_REVEAL() {
		assertEquals(PublishMode.REVEAL, PublishMode.REVEAL);
	}
	
	@Test
	public void SILENT_equals_SILENT() {
		assertEquals(PublishMode.SILENT, PublishMode.SILENT);
	}
	
	@Test
	public void REVEAL_not_equal_to_SILENT() {
		assertEquals(PublishMode.REVEAL, PublishMode.REVEAL);
	}

}
