package org.robolectric.shadows.glass;

import java.util.Map;

import org.robolectric.bytecode.ShadowConfig;
import org.robolectric.bytecode.ShadowMap;
import org.robolectric.bytecode.ShadowMap.Builder;
import org.robolectric.bytecode.ShadowWrangler;

public class GlassShadowMap extends ShadowMap {

	protected GlassShadowMap(Map<String, ShadowConfig> map) {
		super(map);
	}
	
	@Override
  public ShadowConfig get(Class<?> clazz) {
	  
	  String[][] shadowDefaults = {
			  {"org.robolectric.shadows.glass", "Shadow"},
			  {"org.robolectric.shadows.glass", "ShadowGlass"}
	  };
	  for (String[] shadowDefault : shadowDefaults) {
		  String shadowName = getShadowClassName(clazz.getName(), shadowDefault[0], shadowDefault[1]);
		  if (ShadowWrangler.debug) System.out.println("Checking shadowName "+shadowName);
		  ShadowConfig config = get(clazz, shadowName);
		  if (config != null) {
		    if (ShadowWrangler.debug) System.out.println("Got config "+config);
			  return config;
		  }
	  }
	  return super.get(clazz);    
  }
	
	@Override
	public Builder newBuilder() {
    return new Builder(this);
  }
	
	public static class Builder extends ShadowMap.Builder {
	  
	  public Builder() {
	  }
	  
	  public Builder(ShadowMap copy) {
	     super(copy);
	  }
	  
		@Override
		public ShadowMap build() {
	      return new GlassShadowMap(getMap());
		}
	}

}
