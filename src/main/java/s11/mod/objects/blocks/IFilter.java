package s11.mod.objects.blocks;

public interface IFilter {
	boolean canWork();
	boolean hasCooledOff();

	/**
	 * @return true if successfully used
	 */
	boolean fakeUse();
}
