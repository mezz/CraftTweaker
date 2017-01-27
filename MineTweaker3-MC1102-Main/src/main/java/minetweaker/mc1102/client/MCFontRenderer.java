package minetweaker.mc1102.client;

import minetweaker.api.client.IFontRenderer;
import minetweaker.api.formatting.IFormattedText;
import net.minecraft.client.gui.FontRenderer;
import stanhebben.zenscript.annotations.Optional;

public class MCFontRenderer implements IFontRenderer {
	
	private FontRenderer font;
	
	public MCFontRenderer(FontRenderer font) {
		this.font = font;
	}
	
	@Override
	public int getFontHeight() {
		return font.FONT_HEIGHT;
	}
	
	@Override
	public int getStringWidth(IFormattedText text) {
		return font.getStringWidth(text.asString());
	}
	
	@Override
	public void setUnicodeFlag(boolean flag) {
		font.setUnicodeFlag(flag);
	}
	
	@Override
	public boolean isUnicodeEnabled() {
		return font.getUnicodeFlag();
	}
	
	@Override
	public void drawString(IFormattedText text, int x, int y, @Optional int colour) {
		font.drawString(text.asString(), x, y, colour);
	}
	
	@Override
	public void drawStringWithShadow(IFormattedText text, int x, int y, @Optional int colour) {
		font.drawStringWithShadow(text.asString(), x, y, colour);
	}
	
	@Override
	public String trimToWidth(IFormattedText text, int width, boolean reverse) {
		return font.trimStringToWidth(text.asString(), width, reverse);
	}
	
	@Override
	public void drawSplitString(IFormattedText text, int x, int y, int wrapWidth, @Optional int textColor) {
		font.drawSplitString(text.asString(), x, y, wrapWidth, textColor);
	}
}
