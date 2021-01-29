package edu.cs.ai.alchourron.logic.logics.propositional;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import edu.cs.ai.alchourron.logic.Formula;
import edu.cs.ai.alchourron.logic.Interpretation;
import edu.cs.ai.alchourron.logic.syntax.formula.FormulaAND;
import edu.cs.ai.alchourron.logic.syntax.formula.FormulaNeg;
import edu.cs.ai.alchourron.logic.syntax.formula.FormulaProposition;

/***
 * Represents propositional interpretations.
 * 
 * @author Kai Sauerwald
 *
 * @param <P> The type of symbols used in the signature
 * @param <S> The type of the signature
 */
public class PropositionalInterpretation<P, S extends PropositionalSignature<P>> implements Interpretation<S> {

	S signature;
	Set<P> trueValued;

	/***
	 * Constructs a new interpretation
	 * 
	 * @param sig        The signature over which the interpretation is defined
	 * @param trueValued The elements which are considered to be true
	 */
	public PropositionalInterpretation(S sig, P... trueValued) {
		this.signature = sig;
		this.trueValued = new HashSet<>();
		for (int i = 0; i < trueValued.length; i++) {
			P v = trueValued[i];
			this.trueValued.add(v);
		}
	}

	/***
	 * Constructs a new interpretation
	 * 
	 * @param sig        The signature over which the interpretation is defined
	 * @param trueValued The elements which are considered to be true
	 */
	public PropositionalInterpretation(S sig, Collection<P> trueValued) {
		this.signature = sig;
		this.trueValued = new HashSet<>(trueValued);
	}

	/***
	 * Checks where under this interpretation {@link symbol} is true.
	 * 
	 * @author Kai Sauerwald
	 * @param symbol The symbol
	 */
	public boolean isTrue(P symbol) {
		return trueValued.contains(symbol);
	}

	/***
	 * Gets all variables which are true in this world.
	 * 
	 * @author Kai Sauerwald
	 */
	public Set<P> getAllTrue() {
		return Collections.unmodifiableSet(trueValued);
	}

	/***
	 * Checks where under this interpretation {@link symbol} is false.
	 * 
	 * @author Kai Sauerwald
	 * @param symbol The symbol
	 */
	public boolean isFalse(P symbol) {
		return !isTrue(symbol);
	}

	/***
	 * Gets all variables which are false in this world.
	 * 
	 * @author Kai Sauerwald
	 */
	public Set<P> getAllFalse() {
		HashSet<P> set = new HashSet<>(signature.getSymbols());
		set.removeAll(trueValued);
		return Collections.unmodifiableSet(set);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cs.ai.logic.Interpretation#getSignature()
	 */
	@Override
	public S getSignature() {
		return signature;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		for (P pSym : signature.symbols) {
			if (trueValued.contains(pSym))
				builder.append(pSym);
			else
				builder.append("\u00AC" + pSym);
		}
		return builder.toString();
	}

	/***
	 * Generats a LaTeX-ified version of the stringrepresentation
	 */
	@Override
	public String toLaTeX() {
		StringBuilder builder = new StringBuilder();
		for (P pSym : signature.symbols) {
			if (trueValued.contains(pSym))
				builder.append(pSym);
			else
				builder.append("\\negOf{" + pSym + "}");
		}
		return builder.toString();
	}

	/***
	 * Builds a formula which has exactly this interpretation as model
	 * 
	 * @author Kai Sauerwald
	 */
	public Formula<PropositionalSignature<P>> getCharacterizingFormula() {
		Formula<PropositionalSignature<P>> result = null;

		for (P pSym : signature.getSymbols()) {
			Formula<PropositionalSignature<P>> tmp;
			if (isTrue(pSym))
				tmp = new FormulaProposition<>(signature, pSym);
			else
				tmp = new FormulaNeg<>(signature, new FormulaProposition<>(signature, pSym));

			if (result == null)
				result = tmp;
			else
				result = new FormulaAND<>(signature, tmp);
		}
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof PropositionalInterpretation))
			return false;
		PropositionalInterpretation<P, PropositionalSignature<P>> pi = (PropositionalInterpretation<P, PropositionalSignature<P>>) obj;
		if (!pi.getSignature().equals(this.getSignature()))
			return false;
		for (P pSym : getSignature().symbols) {
			if (pi.isTrue(pSym) != this.isTrue(pSym))
				return false;
		}
		return true;
	}

	@Override
	public int hashCode() {
		return signature.hashCode() * 31 + trueValued.hashCode();
	}

	@Override
	public boolean isMatchingSignature(S signature) {
		// TODO Auto-generated method stub
		return false;
	}
}
