package s11.mod.util;

import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.translation.I18n;;

public class TextHelper {
	public static String localize(String input, Object... format) {
        return I18n.translateToLocalFormatted(input, format);
    }
	
	/**
	 * @deprecated use {@link getLang}
	 * @param unlocalized
	 * @return
	 */
	public static String localize(String... unlocalized) {
	    String str = "";
	    for (String s : unlocalized)
	      str = str + (new TextComponentTranslation(s, new Object[0])).getFormattedText(); 
	    return str;
	}
	
	/**
	 * e.g.  "{@code tile.ore_void.name}"
	 * @param lang
	 * @return
	 */
	public static String getLang(String lang) {
		return net.minecraft.client.resources.I18n.format(lang);
	}
	
	/**
	 * @param lang
	 * @return item. + {@code lang} + .tooltip
	 */
	public static String itemToolTipGetLang(String lang) {
		return net.minecraft.client.resources.I18n.format("item." + lang + ".tooltip");
	}
	
	public static int calculateRF(int delay, int powerUse) {
		//return (int) ((20 / (float)delay) * powerUse);
		//return (20 / (float)powerUse) * delay;
		return (int)((float)powerUse / (float)delay);
	}
}
