package edu.cs.ai.alchourron.logic.syntax;

import edu.cs.ai.alchourron.logic.Formula;
import edu.cs.ai.alchourron.logic.Signature;

/***
 * 
 * @author Kai Sauerwald
 *
 * @param <S> The signature of this formula 
 */
public interface SyntacticElement<S extends Signature> {
	
	/***
	 * Checks where this elements represents a variable in this coressponding logic
	 * @author Kai Sauerwald
	 */
	public boolean isVariable();
	
	/***
	 * Checks where this elements is atomic in this coressponding logic
	 * @author Kai Sauerwald
	 */
	public boolean isAtom();
	
	/***
	 * Checks where this elements represents a logical symbol in the logic.
	 * @author Kai Sauerwald
	 */
	public boolean isLogical();

	/***
	 * Build a string representation of this syntactic object.
	 * @return
	 */
	public String stringify();
}