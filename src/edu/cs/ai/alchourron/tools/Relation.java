package edu.cs.ai.alchourron.tools;

/***
 * Results of comparison with partial orders.
 * @author Kai Sauerwald
 *
 */
public enum Relation {
	/**
	 * The first element is with respect to the relation lower than the second.
	 * @author Kai Sauerwald
	 */
	LESSER,  
	/**
	 * The first element is with respect to the relation strictly lower than the second.
	 * @author Kai Sauerwald
	 */
	STRICT_LESSER,
	/**
	 * The first element is equal to the second with respect to the relation.
	 * @author Kai Sauerwald
	 */
	EQUAL, 
	/**
	 * The first element is with respect to the relation greater than the second.
	 * @author Kai Sauerwald
	 */
	GREATER, 
	/**
	 * The first element is with respect to the relation strictly greater than the second.
	 * @author Kai Sauerwald
	 */
	STRICT_GREATER,
	/**
	 * The is no relation between the first and second element.
	 * @author Kai Sauerwald
	 */
	INCOMPARABLE;
}
