package edu.cs.ai.alchourron.logic.syntax.formula;

import java.util.Objects;

import edu.cs.ai.alchourron.logic.Formula;
import edu.cs.ai.alchourron.logic.syntax.structure.PropositionLogicSignature;

/***
 * Represents a proposition
 * 
 * @author Kai Sauerwald
 *
 * @param <PSym> The type for symbols
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
	public String toString() {
		return symbol.toString();
	}
	
	@Override
	public boolean isSignatureMatching(S signature) throws UnsupportedOperationException {
		return signature.getPropositions().contains(symbol);
	}

	@Override
	public int hashCode() {
		return Objects.hash(symbol);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof FormulaProposition))
			return false;
		FormulaProposition other = (FormulaProposition) obj;
		return Objects.equals(symbol, other.symbol);
	}
}
