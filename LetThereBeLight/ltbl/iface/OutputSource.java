package ltbl.iface;

/**
 * Supports visitor pattern by defining an interface which
 * any class which can provide data to an Output can implement.
 * The Output will hold a list of these objects and call 
 * getOutputs() on each with itself as an argument whenever
 * it needs data.
 * @author Nathan Bernard
 *
 */
public interface OutputSource{
	/**
	 * Called when the Output needs data.
	 * @param o Output object requesting data
	 */
	public void getOutputs(Output o);
}