package edu.cs.ai.alchourron.logic.syntax.terms;

import edu.cs.ai.alchourron.LaTeX;
import edu.cs.ai.alchourron.logic.Signature;
import edu.cs.ai.alchourron.logic.SyntacticElement;
import edu.cs.ai.alchourron.logic.syntax.structure.FunctionTermLogicSignature;
import edu.cs.ai.alchourron.logic.syntax.structure.VariableTermLogicSignature;

/***
 * 
 * @author Kai Sauerwald
 *
 * @param <F> The type for the function symbols
 * @param <V> The type for the variables
 */
public interface Term<S extends Signature> extends SyntacticElement, LaTeX {
	public boolean isSignatureMatching(S signature);	
}
