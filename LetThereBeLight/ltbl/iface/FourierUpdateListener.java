package ltbl.iface;

import ltbl.algo.FourierAnalysis;

/**
 * Supports observer pattern by defining an interface 
 * that can be used by all classes which need information
 * from FourierAnalysis when it becomes available. FourierAnalysis
 * will have a list of objects implementing this interface
 * and will call update(FourierAnalysis) on them when data
 * becomes available.
 * @author Nathan Bernard
 *
 */
public interface FourierUpdateListener{
	/**
	 * called by the FourierAnalysis object on each listener
	 * when new data is available
	 * @param a FourierAnalysis object with new data
	 */
	public void update(FourierAnalysis a);
}