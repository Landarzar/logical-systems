package cs.ai.logic.syntax;

import cs.ai.logic.Signature;

public interface Predicate<S extends Signature>  extends SyntacticElement<S> {
	/*
	 * (non-Javadoc)
	 * @see edu.cs.ai.alchourron.logic.syntax.SyntacticElement#isLogical()
	 */
	@Override
	default boolean isLogical() {
		return false;
	}
	
	/*
	 * (non-Javadoc)
	 * @see edu.cs.ai.alchourron.logic.syntax.SyntacticElement#isLogical()
	 */
	@Override
	default boolean isAtom() {
		return true;
	}
}
