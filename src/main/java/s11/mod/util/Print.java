package s11.mod.util;

import java.util.Arrays;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;

public class Print {
	
	public static void print(List<?> input) {
		for (Object e : input) {
			System.out.println(e);
		}
	}
	
	/**
	 * used for debugging
	 * @param str
	 */
	public static void print(String str) {
		if (str == null) {
			return;
		}
		System.out.println(str);
	}
	
	/**
	 * used for debugging
	 * @param bool
	 */
	public static void print(Boolean bool) {
		if (bool == null) {
			return;
		}
		System.out.println(Boolean.toString(bool));
	}
	
	/**
	 * used for debugging
	 * @param num
	 */
	public static void print(Integer num) {
		if (num == null) {
			return;
		}
		System.out.println(Integer.toString(num));
	}
	
	/**
	 * used for debugging
	 * @param num
	 */
	public static void print(Float num) {
		if (num == null) {
			return;
		}
		System.out.println(Float.toString(num));
	}
	
	/**
	 * used for debugging
	 * @param num
	 */
	public static void print(Double num) {
		if (num == null) {
			return;
		}
		System.out.println(Double.toString(num));
	}
	
	public static void printBlockArray(Block[][] array) {
		System.out.println("\n" + Arrays.deepToString(array).replace("], ", "]\n"));
	}
	
	public static void printBlockArray(Block[][][] array) {
		System.out.println("\n" + Arrays.deepToString(array).replace("], ", "]\n"));
	}
}
