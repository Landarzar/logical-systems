package edu.cs.ai.alchourron.logic.propositional;

import java.security.InvalidParameterException;
import java.util.Set;
import java.util.stream.Collectors;

import edu.cs.ai.alchourron.logic.propositional.formula.PropositionalAND;
import edu.cs.ai.alchourron.logic.propositional.formula.PropositionalAtom;
import edu.cs.ai.alchourron.logic.propositional.formula.PropositionalFalsum;
import edu.cs.ai.alchourron.logic.propositional.formula.PropositionalNEG;
import edu.cs.ai.alchourron.logic.propositional.formula.PropositionalOR;
import edu.cs.ai.alchourron.logic.propositional.formula.PropositionalVerum;
import edu.cs.ai.alchourron.logic.semantics.ModelTheory;
import edu.cs.ai.alchourron.logic.syntax.Formula;
import edu.cs.ai.alchourron.logic.syntax.SyntacticElement;
import edu.cs.ai.alchourron.logic.syntax.formula.LogicalAND;
import edu.cs.ai.alchourron.logic.syntax.formula.LogicalNEG;
import edu.cs.ai.alchourron.logic.syntax.formula.LogicalOR;
import edu.cs.ai.alchourron.logic.syntax.formula.LogicalOperator;
import edu.cs.ai.alchourron.logic.syntax.formula.Proposition;

/***
 * Logical System representing propositional logic over symbol space of type
 * {@link PSym}.
 * 
 * @author Kai Sauerwald
 *
 * @param <PSym> The symbol space over which the signature is definable
 * @param <F>    The type for formula
 */
public class PropositionalLogic<PSym> implements
		ModelTheory<Boolean, PropositionalSignature<PSym>, PropositionalFormula<PSym>, PropositionalInterpretation<PSym, PropositionalSignature<PSym>>> {


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
	 * @param syntacton the root of the syntax tree
	 */
	@SuppressWarnings("unchecked")
	protected boolean validSyntaxTree(SyntacticElement<PropositionalSignature<PSym>> syntacton,
			PropositionalSignature<PSym> sig) {
		if (syntacton instanceof LogicalAND<?> || syntacton instanceof LogicalOR<?>
				|| syntacton instanceof LogicalNEG<?>) {
			LogicalOperator<PropositionalSignature<PSym>> le = (LogicalOperator<PropositionalSignature<PSym>>) syntacton;
			return le.getOperands().stream().allMatch(se -> validSyntaxTree(se, sig));
		} else if (syntacton instanceof Proposition<?, ?>) {
			Proposition<PSym, PropositionalSignature<PSym>> atom = (Proposition<PSym, PropositionalSignature<PSym>>) syntacton;
			if (atom.getArity() != 0)
				return false;
			if (!sig.getSymbols().contains(atom.getSymbol()))
				return false;
			return true;
		}

		return false;
	}
	

	

	
	/***
	 * Generates a formula having exact having exact the set of interpretations as models.
	 * @param set The set of models.
	 */
	public PropositionalFormula<PSym> getCharacterisingFormula(PropositionalSignature<PSym> sig, Set<PropositionalInterpretation<PSym, PropositionalSignature<PSym>>> set){
		if(set.isEmpty())
			return new PropositionalFalsum<PSym>(sig);
		if(set.size() == 1)
			return set.stream().findFirst().get().getCharacterizingFormula();
		
		PropositionalFormula<PSym> formula = null;
		
		for (PropositionalInterpretation<PSym, PropositionalSignature<PSym>> i : set) {
			if(formula == null)
				formula = i.getCharacterizingFormula();
			else
				formula = formula.Or(i.getCharacterizingFormula());
		}
		
		return formula;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * edu.cs.ai.alchourron.logic.LogicalSystem#models(edu.cs.ai.alchourron.logic.
	 * Formula)
	 */
	@Override
	public Set<PropositionalInterpretation<PSym, PropositionalSignature<PSym>>> modelsOf(
			PropositionalFormula<PSym> formula) {
		assert this.validFormula(formula) : "given formula ist not (syntactically) valid";
		return formula.getSignature().stream().filter(i -> satisfies(i, formula)).collect(Collectors.toSet());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * edu.cs.ai.alchourron.logic.LogicalSystem#eval(edu.cs.ai.alchourron.logic.
	 * Interpretation, edu.cs.ai.alchourron.logic.Formula)
	 */
	@Override
	public Boolean eval(PropositionalInterpretation<PSym, PropositionalSignature<PSym>> interpretation,
			PropositionalFormula<PSym> formula) {
		return satisfies(interpretation, formula);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * edu.cs.ai.alchourron.logic.LogicalSystem#entails(edu.cs.ai.alchourron.logic.
	 * Interpretation, edu.cs.ai.alchourron.logic.Formula)
	 */
	@Override
	public boolean satisfies(PropositionalInterpretation<PSym, PropositionalSignature<PSym>> interpretation,
			PropositionalFormula<PSym> formula) {
//		assert this.validFormula(formula) : "the given formula ist not valid";

		if (formula instanceof PropositionalAtom<?>) {
			PropositionalAtom<PSym> atom = (PropositionalAtom<PSym>) formula;
			return interpretation.isTrue(atom.getSymbol());
		}
		if (formula instanceof PropositionalAND<?>) {
			PropositionalAND<PSym> and = (PropositionalAND<PSym>) formula;
			return and.getOperands().stream().allMatch(o -> satisfies(interpretation, (PropositionalFormula<PSym>) o));
		}
		if (formula instanceof PropositionalOR<?>) {
			PropositionalOR<PSym> or = (PropositionalOR<PSym>) formula;
			return or.getOperands().stream().anyMatch(o -> satisfies(interpretation, (PropositionalFormula<PSym>) o));
		}
		if (formula instanceof PropositionalNEG<?>) {
			PropositionalNEG<PSym> neg = (PropositionalNEG<PSym>) formula;
			return !satisfies(interpretation, (PropositionalFormula<PSym>) neg.getOperands().get(0));
		}
		if (formula instanceof PropositionalFalsum<?>) {
			return false;
		}
		if (formula instanceof PropositionalVerum<?>) {
			return true;
		}

		throw new InvalidParameterException("The given formula object is not a valid propositional formula");
	}

}
