package s11.mod.util;

public class ArrayHelper {
	public static int maxIntInArray(int[] array) {
		int max = 0;
		for (int num : array) {
			if (num > max) {
				max = num;
			}
		}
		
		return max;
	}
}
