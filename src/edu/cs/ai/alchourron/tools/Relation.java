package edu.cs.ai.alchourron.tools;

import java.util.HashSet;

public interface Relation<U> {
	
	public boolean contains(Tuple<U> tuple);
	
	public int getArity();

}
