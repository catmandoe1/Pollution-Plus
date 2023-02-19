package s11.mod.util;

public class Reference {
	public static final String MODID = "pollutionplus";
	public static final String NAME = "Pollution Plus";
	public static final String VERSION = "1.12.2-2.0.5.0";
	public static final String DEPENDENCIES = "required-after:adpother@[1.12.2-1,)";
	
	public static final String CLIENT = "s11.mod.proxy.ClientProxy";
	public static final String COMMON = "s11.mod.proxy.CommonProxy";
	
	// gui ids
	public enum GuiIds {
		POWER_INFUSER(0),
		HYDRAULIC_PRESS(1),
		ALLOY_FURNACE(2),
		DISCHARGER(3),
		VOID_MINER(4);
		
		private int index;
		
		GuiIds(int index) {
			this.index = index;
		}
		
		public int getIndex() {
			return this.index;
		}
	    public static GuiIds fromInt(int i) {
	        for (GuiIds f : values()) {
	            if (f.getIndex() == i) {
	                return f;
	            }
	        }
	        return null;
	    }
	}
}
