/**
 * 
 */
package edu.cs.ai.alchourron;


/**
 * Marks objects which can be convertet to a LaTeX representation
 * @author Kai Sauerwald
 *
 */
public interface LaTeX {
	
	/**
	 * Builds a LaTeX representation of this object.
	 * @author Kai Sauerwald
	 * @throws UnsupportedOperationException When not implemented.
	 */
	public default String toLaTeX() {
		throw new UnsupportedOperationException();
	}
}
