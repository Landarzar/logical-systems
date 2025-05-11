package edu.cs.ai.alchourron.logic.semantics.interpretations;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import edu.cs.ai.alchourron.logic.Formula;
import edu.cs.ai.alchourron.logic.Interpretation;
import edu.cs.ai.alchourron.logic.logics.propositional.PropositionalSignature;
import edu.cs.ai.alchourron.logic.syntax.formula.FormulaAND;
import edu.cs.ai.alchourron.logic.syntax.formula.FormulaNeg;
import edu.cs.ai.alchourron.logic.syntax.formula.FormulaPropositionalAtom;

/***
 * Represents propositional teams.
 * 
 * @author Kai Sauerwald
 *
 * @param <P> The type of symbols used in the signature
 */
public class PropositionalTeam<P> implements Interpretation<PropositionalSignature<P>> {

	PropositionalSignature<P> signature;
	Set<PropositionalInterpretation<P>> elements;

	/***
	 * Constructs a new interpretation
	 * 
	 * @param sig        The signature over which the interpretation is defined
	 * @param trueValued The elements of the team
	 */
	public PropositionalTeam(PropositionalSignature<P> sig, PropositionalInterpretation<P>... elements) {
		this.signature = sig;
		this.elements = new CopyOnWriteArraySet<>();
		for (int i = 0; i < elements.length; i++) {
			PropositionalInterpretation<P> v = elements[i];
			this.elements.add(v);
		}
	}

	/***
	 * Constructs a new interpretation
	 * 
	 * @param sig        The signature over which the interpretation is defined
	 * @param trueValued The elements which are considered to be true
	 */
	public PropositionalTeam(PropositionalSignature<P> sig, Collection<PropositionalInterpretation<P>> elements) {
		this.signature = sig;
		this.elements = new HashSet<>(elements);
	}

	/***
	 * Checks where under this interpretation {@link symbol} is true.
	 * 
	 * @author Kai Sauerwald
	 * @param valuation The valuation
	 */
	public boolean isTrue(PropositionalInterpretation<P> valuation) {
		return elements.contains(valuation);
	}

	/***
	 * Gets all variables which are true in this world.
	 * 
	 * @author Kai Sauerwald
	 */
	public Set<PropositionalInterpretation<P>> getAllTrue() {
		return Collections.unmodifiableSet(elements);
	}

	/***
	 * Checks where under this interpretation {@link symbol} is false.
	 * 
	 * @author Kai Sauerwald
	 * @param symbol The symbol
	 */
	public boolean isElement(PropositionalInterpretation<P> element) {
		return !isTrue(element);
	}

	/***
	 * Gets all variables which are false in this world.
	 * 
	 * @author Kai Sauerwald
	 */
	public Set<P> getAllFalse() {
		HashSet<P> set = new HashSet<>(signature.getPropositions());
		set.removeAll(elements);
		return Collections.unmodifiableSet(set);
	}

	/*****
	 * Provides the signature of this propositional interpretation
	 *@author Kai Sauerwald
	 */
	public PropositionalSignature<P> getSignature() {
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
		return elements.toString();
//		for (P pSym : signature.getPropositions()) {
//			if (elements.contains(pSym))
//				builder.append(pSym);
//			else
//				builder.append("\u00AC" + pSym);
//		}
//		return builder.toString();
	}

	/***
	 * Generats a LaTeX-ified version of the stringrepresentation
	 */
	@Override
	public String toLaTeX() {
		StringBuilder builder = new StringBuilder();
		for (P pSym : signature.getPropositions()) {
			if (elements.contains(pSym))
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

//		for (P pSym : signature.getPropositions()) {
//			Formula<PropositionalSignature<P>> tmp;
//			if (isTrue(pSym))
//				tmp = new FormulaPropositionalAtom<>(pSym);
//			else
//				tmp = new FormulaNeg<>(new FormulaPropositionalAtom<>(pSym));
//
//			if (result == null)
//				result = tmp;
//			else
//				result = new FormulaAND<>(result, tmp);
//		}
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof PropositionalTeam))
			return false;
		PropositionalTeam<P> pi = (PropositionalTeam<P>) obj;
		if (!pi.getSignature().equals(this.getSignature()))
			return false;
		for (PropositionalInterpretation<P> propositionalInterpretation : signature) {
			if(isElement(propositionalInterpretation) != pi.isElement(propositionalInterpretation))
				return false;
		}
		return true;
	}

	@Override
	public int hashCode() {
		return signature.hashCode() * 31 + elements.hashCode();
	}

	@Override
	public boolean isMatchingSignature(PropositionalSignature<P> signature) {
		return this.signature.equals(signature);
	}
	
	
}
