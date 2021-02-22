package edu.cs.ai.alchourron.logic.syntax.formula;

import edu.cs.ai.alchourron.logic.Formula;
import edu.cs.ai.alchourron.logic.syntax.structure.PropositionLogicSignature;

/***
 * Represents predicates, for instance relations in first order logic or
 * propositional atoms.
 * 
 * @author Kai Sauerwald
 *
 * @param <PSym> The type for predicate symbols
 * @param <S>    The signature type
 */
public class FormulaProposition<PSym, S extends PropositionLogicSignature<PSym>> extends FormulaAtom<S> {

	protected PSym symbol;

	public FormulaProposition(PSym symbol) {
		this.symbol = symbol;
	}

	/***
	 * Gets the symbol of this predicate.
	 * 
	 * @author Kai Sauerwald
	 */
	public PSym getSymbol() {
		return symbol;
	}

	@Override
	public String stringify() {
		return symbol.toString();
	}
	
	@Override
	public boolean isSignatureMatching(S signature) throws UnsupportedOperationException {
		return signature.getPropositions().contains(symbol);
	}
}
