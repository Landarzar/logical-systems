package edu.cs.ai.alchourron.logic;

import java.util.List;

/***
 * 
 * @author Kai Sauerwald
 *
 * @param <S> The signature of this formula
 */
public interface SyntacticElement {

	/***
	 * Get all childen of this element in the syntactic tree.
	 * 
	 * @author Kai Sauerwald
	 */
	public default List<SyntacticElement> getSyntacticChildren() {
		return List.of();
	}

	/***
	 * Checks where this elements is atomic in this coressponding logic
	 * 
	 * @author Kai Sauerwald
	 */
	public boolean isAtom();

	/***
	 * Checks where this elements represents a logical symbol in the logic.
	 * 
	 * @author Kai Sauerwald
	 */
	public boolean isLogical();

	/***
	 * Build a string representation of this syntactic object.
	 * 
	 * @author Kai Sauerwald
	 */
	public default String stringify() {
		return toString();
	}
}
