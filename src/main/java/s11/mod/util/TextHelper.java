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
		return getLang("item." + lang + ".tooltip");
	}
	
	public static int calculateRF(int delay, int powerUse) {
		//return (int) ((20 / (float)delay) * powerUse);
		//return (20 / (float)powerUse) * delay;
		return (int)((float)powerUse / (float)delay);
	}
	
	public static int getSecondsFromTicks(int ticks) {
		return (int) Math.floor(ticks / 20);
	}
	
	/**
	 * 
	 * @param secs
	 * @return Hrs:Mins:Secs
	 */
	public static String getHrMnScFromSeconds(int secs) {
		int hours = 0;
		int mins = 0;
		boolean hour = false;
		boolean min = false;
		boolean sec = false;
		String time = "";
		
		boolean flag = false;
		
		while (secs > 59) {
			secs = secs - 60;
			mins++;
		}
		while (mins > 59) {
			mins = mins - 60;
			hours++;
		}
		
		if (hours == 1) {
			hour = true;
		}
		if (mins == 1) {
			min = true;
		}
		if (secs == 1) {
			sec = true;
		}

		if (hours > 0) {
			time = time + hours;
			flag = true;
			if (hour) {
				time = time + " Hour ";
			} else {
				time = time + " Hours ";
			}
		}
		
		if (mins > 0) {
			time = time + mins;
			flag = true;
			if (min) {
				time = time + " Minute ";
			} else {
				time = time + " Minutes ";
			}
		}
		
		if (secs != 0 && !flag) {
			time = time + secs;
			if (sec) {
				time = time + " Second";
			} else {
				time = time + " Seconds";
			}
		}
		
		return time;
	}
}
