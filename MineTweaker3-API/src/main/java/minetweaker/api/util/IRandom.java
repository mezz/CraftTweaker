package minetweaker.api.util;

import stanhebben.zenscript.annotations.*;

@ZenClass("minetweaker.util.IRandom")
public interface IRandom {
	
	int getInt(@Optional int bound);
	
	int getInt(int min, int max);
	
	long gettLong();
	
	boolean getBoolean();
	
	double getDouble();
	
	float getFloat();
	
	double getGaussian();
}
