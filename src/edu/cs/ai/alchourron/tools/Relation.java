package edu.cs.ai.alchourron.tools;

public interface Relation<U> {
	
	public boolean contains(Tuple<U> tuple);
	
	public int getArity();

}
