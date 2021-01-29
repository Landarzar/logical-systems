package edu.cs.ai.math.setheory.objects;

public interface Relation<U> {

	public boolean contains(Tuple<U> tuple);

	public int getArity();

}
