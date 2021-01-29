package edu.cs.ai.alchourron.logic.semantics.interpretations;

import java.util.Iterator;

import edu.cs.ai.alchourron.tools.Tuple;

/***
 * Representation of a Relation over a Universe
 * @author Kai Sauerwald
 */
public interface Relation<U> extends Iterable<Tuple<U>> {
	
	/***
	 * Provides the arity of this relation
	 */
	public int getArtity();
		
	@Override
	default Iterator<Tuple<U>> iterator() {
		return null;
	}
	
}
