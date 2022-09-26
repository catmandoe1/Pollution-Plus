package s11.mod.util;

import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.translation.I18n;

public class TextHelper {
	public static String localize(String input, Object... format) {
        return I18n.translateToLocalFormatted(input, format);
    }
	
	public static String localize(String... unlocalized) {
	    String str = "";
	    for (String s : unlocalized)
	      str = str + (new TextComponentTranslation(s, new Object[0])).getFormattedText(); 
	    return str;
	  }
}
