package s11.mod.util;

import org.lwjgl.input.Keyboard;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PlayerPressing {
	
	/**
	 * Is left shift or left control being held down
	 * @return
	 */
	@SideOnly(Side.CLIENT)
	public static final boolean areCommonControlKeysDown() {
		return isLeftShiftDown() || isLeftCrtlDown();
	}
	
	/**
	 * Is left or right control keys being held down
	 * @return
	 */
	@SideOnly(Side.CLIENT)
	public static final boolean isCrtlDown() {
		return isLeftCrtlDown() || isRightCrtlDown();
	}
	
	/**
	 * Is left or right shift keys being held down 
	 * @return
	 */
	@SideOnly(Side.CLIENT)
	public static final boolean isShiftDown() {
		return isLeftShiftDown() || isRightShiftDown();
	}
	
	@SideOnly(Side.CLIENT)
	public static final boolean isLeftShiftDown() {
		return Keyboard.isKeyDown(Keyboard.KEY_LSHIFT);
	}
	
	@SideOnly(Side.CLIENT)
	public static final boolean isRightShiftDown() {
		return Keyboard.isKeyDown(Keyboard.KEY_RSHIFT);
	}
	
	@SideOnly(Side.CLIENT)
	public static final boolean isLeftCrtlDown() {
		return Keyboard.isKeyDown(Keyboard.KEY_LCONTROL);
	}
	
	@SideOnly(Side.CLIENT)
	public static final boolean isRightCrtlDown() {
		return Keyboard.isKeyDown(Keyboard.KEY_RCONTROL);
	}
}
