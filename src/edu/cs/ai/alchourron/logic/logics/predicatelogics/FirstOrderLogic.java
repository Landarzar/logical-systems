/**
 * 
 */
package edu.cs.ai.alchourron.logic.logics.predicatelogics;

import java.util.function.Function;

import edu.cs.ai.alchourron.logic.Formula;
import edu.cs.ai.alchourron.logic.Interpretation;
import edu.cs.ai.alchourron.logic.semantics.interpretations.FiniteStructure;
import edu.cs.ai.alchourron.logic.syntax.formula.ClassicalQuantifier;
import edu.cs.ai.alchourron.logic.syntax.formula.FormulaQuantification;

/***
 * Logical System representing first order logic.
 * 
 * @author Kai Sauerwald
 *
 * @param <R> The type for Relationsymbols
 * @param <K> The type for Functionsymbols
 * @param <V> The type for Variables
 */
public class FirstOrderLogic<R, K, V> extends GeneralizedFirstOrderLogic<R, K, V, ClassicalQuantifier, FOSignature<R, K, V>> {

	@Override
	protected <U> Boolean evalQuantifiedFormua(FiniteStructure<U, R, K, FOSignature<R, K, V>> interpretation,
			FormulaQuantification<ClassicalQuantifier, V, FOSignature<R, K, V>> formula, Function<V, U> valuation) {

		if (formula.getQuantifyer() == ClassicalQuantifier.FORALL) {
			return interpretation.getUniverse().parallelStream().allMatch(u -> {

				Function<V, U> nval = var -> {
					if (var.equals(formula.getVariables()))
						return u;
					return valuation.apply(var);
				};

				return eval(interpretation, formula.getQuantified(), nval);
			});
		}
		else if (formula.getQuantifyer() == ClassicalQuantifier.EXISTS) {
			return interpretation.getUniverse().parallelStream().anyMatch(u -> {

				Function<V, U> nval = var -> {
					if (var.equals(formula.getVariables()))
						return u;
					return valuation.apply(var);
				};

				return eval(interpretation, formula.getQuantified(), nval);
			});
		}
		
		throw new UnsupportedOperationException("Unknown quantifier" + formula.getQuantifyer());
	}



}
