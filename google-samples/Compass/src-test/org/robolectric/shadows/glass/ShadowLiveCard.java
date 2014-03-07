package org.robolectric.shadows.glass;

import org.robolectric.Robolectric;
import org.robolectric.annotation.Implementation;
import org.robolectric.annotation.Implements;
import org.robolectric.annotation.RealObject;
import org.robolectric.shadows.ShadowSurfaceView;
import org.robolectric.shadows.ShadowSurfaceView.FakeSurfaceHolder;

import android.app.PendingIntent;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.RemoteViews;

import com.google.android.glass.timeline.LiveCard;

@Implements(LiveCard.class)
public class ShadowLiveCard {
	
	@RealObject
	private LiveCard realLiveCard;
	
	private SurfaceView surfaceView = new SurfaceView(Robolectric.getShadowApplication().getApplicationContext());

	@Implementation
	public void __constructor__() {
	}
	
	@Implementation
	public LiveCard setViews(RemoteViews views) {
		return realLiveCard;
	}
	
	@Implementation
	public LiveCard setAction(PendingIntent intent) {
		return realLiveCard;
	}
	
	@Implementation
	public LiveCard setDirectRenderingEnabled(boolean enabled) {
		return realLiveCard;
	}
	
	@Implementation
	public SurfaceHolder getSurfaceHolder() {
		return getSurfaceView().getHolder();
	}
	
	/**
	 * @return SurfaceView that the SurfaceHolder is from.
	 */
	public SurfaceView getSurfaceView() {
		return surfaceView;
	}
	
	/**
	 * Notify surface holder listeners that the surface has been created.
	 */
	public void notifySurfaceCreated() {
		ShadowSurfaceView shadowSurface = (ShadowSurfaceView) Robolectric.shadowOf(getSurfaceView());
		FakeSurfaceHolder holder = shadowSurface.getFakeSurfaceHolder();
		for (SurfaceHolder.Callback callback : holder.getCallbacks()) {
			callback.surfaceCreated(holder);
		}
	}
	
	@Implements(LiveCard.PublishMode.class)
	public static class ShadowPublishMode {
	  
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
	
}
