//package edu.cs.ai.alchourron.logic.propositional;
//
//import edu.cs.ai.alchourron.logic.propositional.formula.PropositionalAND;
//import edu.cs.ai.alchourron.logic.propositional.formula.PropositionalFalsum;
//import edu.cs.ai.alchourron.logic.propositional.formula.PropositionalNEG;
//import edu.cs.ai.alchourron.logic.propositional.formula.PropositionalOR;
//import edu.cs.ai.alchourron.logic.propositional.formula.PropositionalVerum;
//import edu.cs.ai.alchourron.logic.syntax.Formula;
//
///***
// * Represents propositional formula.
// * 
// * @author Kai Sauerwald
// *
// * @param <PSym> The type of symbols over which the signature is defined
// */
//public interface PropositionalFormula<PSym>
//		extends Formula<PropositionalSignature<PSym>> {
//
////	public static <PSym> PropositionalFormula<PSym> getFalsum(PropositionalSignature<PSym> signature) {
////		return new PropositionalAtom<>(signature, signature.getSymbols().get(0)).Neg()
////				.And(new PropositionalAtom<>(signature, signature.getSymbols().get(0)));
////	}
////
////	public static <PSym> PropositionalFormula<PSym> getTautology(PropositionalSignature<PSym> signature) {
////		return new PropositionalAtom<>(signature, signature.getSymbols().get(0)).Neg()
////				.Or(new PropositionalAtom<>(signature, signature.getSymbols().get(0)));
////	}
//
//	public static <PSym> PropositionalFormula<PSym> getFalsum(PropositionalSignature<PSym> signature) {
//		return new PropositionalFalsum<>(signature);
//	}
//
//	public static <PSym> PropositionalFormula<PSym> getTautology(PropositionalSignature<PSym> signature) {
//		return new PropositionalVerum<>(signature);
//	}
//
//	public default PropositionalFormula<PSym> Neg() {
//		return new PropositionalNEG<>(this.getSignature(), this);
//	}
//
//	public default PropositionalFormula<PSym> And(PropositionalFormula<PSym> f) {
//		return new PropositionalAND<>(this.getSignature(), f);
//	}
//
//	public default PropositionalFormula<PSym> Or(PropositionalFormula<PSym> f) {
//		return new PropositionalOR<>(f.getSignature(), f);
//	}
//
//	@Override
//	public abstract boolean equals(Object obj);
//}
