package edu.cs.ai.alchourron.tools;

import java.util.ArrayList;
import java.util.List;

/***
 * Represents a tuple over a fixed type
 * @author Kai Sauerwald
 *
 */
public class Tuple<T> {
	
	protected ArrayList<T> implTuple = null;
	
	/***
	 * produces the empty tuple
	 */
	public Tuple() {
	}
	

	/***
	 * 
	 */
	public Tuple(T... elements) {
		implTuple = new ArrayList<>(elements.length);
	}
	
	/***
	 * 
	 */
	public Tuple(List<T> elements) {
		implTuple = new ArrayList<>(elements);
	}
	
	
	/***
	 * The arity of this tuple
	 */
	public int getArity()
	{
		if(implTuple == null)
			return 0;
		return implTuple.size();
	}
	
	/***
	 * Gets the element at index {@code i}
	 * @param i The index
	 */
	public T getIth(int i) {
		return implTuple.get(i);
	}
}
