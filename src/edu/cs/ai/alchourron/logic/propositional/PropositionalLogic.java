package edu.cs.ai.alchourron.logic.propositional;

import java.security.InvalidParameterException;
import java.util.Set;
import java.util.stream.Collectors;

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
 * {@link PSym}.
 * 
 * @author Kai Sauerwald
 *
 * @param <PSym>
 *            The symbol space over which the signature is definable
 * @param <F>
 *            The type for formula
 */
public class PropositionalLogic<PSym>
		implements LogicalSystem<Boolean, PropositionalSignature<PSym>, PropositionalFormula<PSym>, PropositionalInterpretation<PSym, PropositionalSignature<PSym>>> {

	/*
	 * (non-Javadoc)
	 * 
	 * @see cs.ai.logic.LogicalSystem#validFormula(cs.ai.logic.Formula)
	 */
	@Override
	public boolean validFormula(PropositionalFormula<PSym> formula) {
		SyntacticElement<PropositionalSignature<PSym>> syntacton = formula.getSyntaxTree();
		return validSyntaxTree(syntacton, (PropositionalSignature<PSym>) formula.getSignature());
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
	protected boolean validSyntaxTree(SyntacticElement<PropositionalSignature<PSym>> syntacton, PropositionalSignature<PSym> sig) {
		if (syntacton instanceof LogicalAND<?> || syntacton instanceof LogicalOR<?> || syntacton instanceof LogicalNEG<?>) {
			LogicalOperator<PropositionalSignature<PSym>> le = (LogicalOperator<PropositionalSignature<PSym>>) syntacton;
			return le.getOperands().stream().allMatch(se -> validSyntaxTree(se, sig));
		} else if (syntacton instanceof Predicate<?, ?>) {
			Predicate<PSym, PropositionalSignature<PSym>> atom = (Predicate<PSym, PropositionalSignature<PSym>>) syntacton;
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
	public Set<PropositionalInterpretation<PSym, PropositionalSignature<PSym>>> models(PropositionalFormula<PSym> formula) {
		assert this.validFormula(formula) : "given formula ist not valid";
		return formula.getSignature().stream().filter(i -> eval(i, formula)).collect(Collectors.toSet());
	}

	/*
	 * (non-Javadoc)
	 * @see edu.cs.ai.alchourron.logic.LogicalSystem#eval(edu.cs.ai.alchourron.logic.Interpretation, edu.cs.ai.alchourron.logic.Formula)
	 */
	@Override
	public Boolean eval(PropositionalInterpretation<PSym, PropositionalSignature<PSym>> interpretation, PropositionalFormula<PSym> formula) {
		return entails(interpretation, formula);
	}

	/*
	 * (non-Javadoc)
	 * @see edu.cs.ai.alchourron.logic.LogicalSystem#entails(edu.cs.ai.alchourron.logic.Interpretation, edu.cs.ai.alchourron.logic.Formula)
	 */
	@Override
	public boolean entails(PropositionalInterpretation<PSym, PropositionalSignature<PSym>> interpretation, PropositionalFormula<PSym> formula) {
		assert this.validFormula(formula) : "given formula ist not valid";
				
		if (formula instanceof PropositionalFormula.PropositionalAtom<?>) {
			PropositionalFormula.PropositionalAtom<PSym> atom = (PropositionalFormula.PropositionalAtom<PSym>) formula;
			return interpretation.isTrue(atom.getSymbol());
		}
		if (formula instanceof PropositionalFormula.PropositionalAND<?>) {
			PropositionalFormula.PropositionalAND<PSym> and = (PropositionalFormula.PropositionalAND<PSym>) formula;
			return and.getOperands().stream().allMatch( o -> entails(interpretation, (PropositionalFormula<PSym>) o)  );
		}
		if (formula instanceof PropositionalFormula.PropositionalOR<?>) {
			PropositionalFormula.PropositionalOR<PSym> or = (PropositionalFormula.PropositionalOR<PSym>) formula;
			return or.getOperands().stream().anyMatch( o -> entails(interpretation, (PropositionalFormula<PSym>) o)  );
		}
		if (formula instanceof PropositionalFormula.PropositionalNEG<?>) {
			PropositionalFormula.PropositionalNEG<PSym> neg = (PropositionalFormula.PropositionalNEG<PSym>) formula;
			return !entails(interpretation, (PropositionalFormula<PSym>) neg.getOperands().get(0) );
		}

		
		throw new InvalidParameterException("The given formula object is not a valid propistional formula");
	}

}
