package s11.mod.util;

import org.lwjgl.input.Keyboard;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PlayerPressing {
	
	@SideOnly(Side.CLIENT)
	public static final boolean isCrtlDown() {
		return Keyboard.isKeyDown(Keyboard.KEY_LCONTROL) || Keyboard.isKeyDown(Keyboard.KEY_RCONTROL);
	}
	
	@SideOnly(Side.CLIENT)
	public static final boolean isShiftDown() {
		return Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT);
	}
}
