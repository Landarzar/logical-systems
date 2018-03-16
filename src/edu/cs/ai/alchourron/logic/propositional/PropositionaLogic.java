package edu.cs.ai.alchourron.logic.propositional;

import java.util.Set;

import edu.cs.ai.alchourron.logic.Formula;
import edu.cs.ai.alchourron.logic.LogicalSystem;
import edu.cs.ai.alchourron.logic.syntax.LogicalAND;
import edu.cs.ai.alchourron.logic.syntax.LogicalNEG;
import edu.cs.ai.alchourron.logic.syntax.LogicalOR;
import edu.cs.ai.alchourron.logic.syntax.LogicalOperator;
import edu.cs.ai.alchourron.logic.syntax.Predicate;
import edu.cs.ai.alchourron.logic.syntax.SyntacticElement;

/***
 * Logical System representing propositional logic over symbol space of type
 * {@link Psym}.
 * 
 * @author Kai Sauerwald
 *
 * @param <Psym>
 *            The symbol space over which the signature is definable
 * @param <F>
 *            The type for formula
 */
public class PropositionaLogic<Psym, F extends Formula<PropositionalSignature<Psym>>>
		implements LogicalSystem<Boolean, PropositionalSignature<Psym>, F, PropositionalInterpretation<Psym, PropositionalSignature<Psym>>> {

	/*
	 * (non-Javadoc)
	 * 
	 * @see cs.ai.logic.LogicalSystem#validFormula(cs.ai.logic.Formula)
	 */
	@Override
	public boolean validFormula(F formula) {
		SyntacticElement<PropositionalSignature<Psym>> syntacton = formula.getSyntax();
		return validSyntaxTree(syntacton, (PropositionalSignature<Psym>) formula.getSignature());
	}

	/****
	 * Checks where the syntax tree contains only elements that are valid for a
	 * propositional formula
	 * 
	 * @author Kai Sauerwald
	 * @param syntacton
	 *            the root of the syntax tree
	 */
	@SuppressWarnings("unchecked")
	private boolean validSyntaxTree(SyntacticElement<PropositionalSignature<Psym>> syntacton, PropositionalSignature<Psym> sig) {
		if (syntacton instanceof LogicalAND<?> || syntacton instanceof LogicalOR<?> || syntacton instanceof LogicalNEG<?>) {
			LogicalOperator<PropositionalSignature<Psym>> le = (LogicalOperator<PropositionalSignature<Psym>>) syntacton;
			return le.getOperands().stream().allMatch(se -> validSyntaxTree(se, sig));
		} else if (syntacton instanceof Predicate<?, ?>) {
			Predicate<Psym, PropositionalSignature<Psym>> atom = (Predicate<Psym, PropositionalSignature<Psym>>) syntacton;
			if (atom.getArity() != 0)
				return false;
			if (!sig.getSymbols().contains(atom.getSymbol()))
				return false;
			return true;
		}

		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * edu.cs.ai.alchourron.logic.LogicalSystem#models(edu.cs.ai.alchourron.logic.
	 * Formula)
	 */
	@Override
	public Set<PropositionalInterpretation<Psym, PropositionalSignature<Psym>>> models(F formula) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean eval(PropositionalInterpretation<Psym, PropositionalSignature<Psym>> interpretation, F formula) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean entails(PropositionalInterpretation<Psym, PropositionalSignature<Psym>> interpretation, F formula) {
		// TODO Auto-generated method stub
		return false;
	}

}
