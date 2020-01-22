package edu.cs.ai.alchourron.logic.propositional;

import edu.cs.ai.alchourron.logic.Formula;
import edu.cs.ai.alchourron.logic.propositional.formula.PropositionalAND;
import edu.cs.ai.alchourron.logic.propositional.formula.PropositionalFalsum;
import edu.cs.ai.alchourron.logic.propositional.formula.PropositionalNEG;
import edu.cs.ai.alchourron.logic.propositional.formula.PropositionalOR;
import edu.cs.ai.alchourron.logic.propositional.formula.PropositionalVerum;

/***
 * Represents propositional formula.
 * 
 * @author Kai Sauerwald
 *
 * @param <PSym> The type of symbols over which the signature is defined
 */
public abstract class PropositionalFormula<PSym>
		implements Formula<PropositionalSignature<PSym>> {

//	public static <PSym> PropositionalFormula<PSym> getFalsum(PropositionalSignature<PSym> signature) {
//		return new PropositionalAtom<>(signature, signature.getSymbols().get(0)).Neg()
//				.And(new PropositionalAtom<>(signature, signature.getSymbols().get(0)));
//	}
//
//	public static <PSym> PropositionalFormula<PSym> getTautology(PropositionalSignature<PSym> signature) {
//		return new PropositionalAtom<>(signature, signature.getSymbols().get(0)).Neg()
//				.Or(new PropositionalAtom<>(signature, signature.getSymbols().get(0)));
//	}

	public static <PSym> PropositionalFormula<PSym> getFalsum(PropositionalSignature<PSym> signature) {
		return new PropositionalFalsum<>(signature);
	}

	public static <PSym> PropositionalFormula<PSym> getTautology(PropositionalSignature<PSym> signature) {
		return new PropositionalVerum<>(signature);
	}

	public PropositionalFormula<PSym> Neg() {
		return new PropositionalNEG<>(this.getSignature(), this);
	}

	public PropositionalFormula<PSym> And(PropositionalFormula<PSym> f) {
		return new PropositionalAND<>(this.getSignature(), this, f);
	}

	public PropositionalFormula<PSym> Or(PropositionalFormula<PSym> f) {
		return new PropositionalOR<>(this.getSignature(), this, f);
	}

	@Override
	public abstract boolean equals(Object obj);
}
