package minetweaker.api.client;

import minetweaker.api.formatting.IFormattedText;
import stanhebben.zenscript.annotations.*;

@ZenClass("minetweaker.api.IFontRenderer")
public interface IFontRenderer {
	
	@ZenMethod
	int getFontHeight();
	
	@ZenMethod
	int getStringWidth(IFormattedText text);
	
	@ZenMethod
	void setUnicodeFlag(boolean flag);
	
	@ZenMethod
	boolean isUnicodeEnabled();
	
	@ZenMethod
	void drawString(IFormattedText text, int x, int y, @Optional int colour);
	
	@ZenMethod
	void drawStringWithShadow(IFormattedText text, int x, int y, @Optional int colour);
	
	@ZenMethod
	String trimToWidth(IFormattedText text, int width, @Optional boolean reverse);
	
	@ZenMethod
	void drawSplitString(IFormattedText text, int x, int y, int wrapWidth, @Optional int textColor);
}
