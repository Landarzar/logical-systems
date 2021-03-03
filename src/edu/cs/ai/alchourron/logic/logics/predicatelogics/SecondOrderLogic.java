/**
 * 
 */
package edu.cs.ai.alchourron.logic.logics.predicatelogics;

import java.util.ArrayList;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import edu.cs.ai.alchourron.logic.semantics.interpretations.FiniteStructure;
import edu.cs.ai.alchourron.logic.syntax.formula.ClassicalQuantifier;
import edu.cs.ai.alchourron.logic.syntax.formula.FormulaQuantification;
import edu.cs.ai.alchourron.logic.syntax.formula.FormulaSOQuantification;
import edu.cs.ai.math.combinatorics.KTupleEnumeration;
import edu.cs.ai.math.combinatorics.PowerSetLexicographic;
import edu.cs.ai.math.settheory.Pair;
import edu.cs.ai.math.settheory.relation.Relation;
import edu.cs.ai.math.settheory.relation.implementation.RelationGeneralImpl;

/**
 * @author Kai Sauerwald
 *
 */
public class SecondOrderLogic<R, K, V, P> extends
		GeneralizedSecondOrderLogic<R, K, V, ClassicalQuantifier, P, ClassicalQuantifier, SOSignature<R, K, V, P>> {

	@Override
	protected <U> Boolean evalQuantifiedFormua(FiniteStructure<U, R, K, SOSignature<R, K, V, P>> interpretation,
			FormulaQuantification<ClassicalQuantifier, V, SOSignature<R, K, V, P>> formula, Function<V, U> valuation,
			Function<P, Relation<U>> SOvaluation) {
		if (formula.getQuantifyer() == ClassicalQuantifier.FORALL) {
			return interpretation.getUniverse().stream().allMatch(u -> {

				Function<V, U> nval = var -> {
					if (var.equals(formula.getVariables()))
						return u;
					return valuation.apply(var);
				};

				return eval(interpretation, formula.getQuantified(), nval, SOvaluation);
			});
		}
		if (formula.getQuantifyer() == ClassicalQuantifier.EXISTS) {
			return interpretation.getUniverse().stream().anyMatch(u -> {

				Function<V, U> nval = var -> {
					if (var.equals(formula.getVariables()))
						return u;
					return valuation.apply(var);
				};

				return eval(interpretation, formula.getQuantified(), nval, SOvaluation);
			});
		}
		throw new UnsupportedOperationException("Unknown quantifier" + formula.getQuantifyer());
	}

	@Override
	protected <U> Boolean evalQuantifiedSOFormua(FiniteStructure<U, R, K, SOSignature<R, K, V, P>> interpretation,
			FormulaSOQuantification<ClassicalQuantifier, P, SOSignature<R, K, V, P>> formula, Function<V, U> valuation,
			Function<P, Relation<U>> SOvaluation) {
		Optional<Pair<P, Integer>> findFirst = getFreeSOVariables(formula.getQuantified()).stream()
				.filter(p -> p.getFirst().equals(formula.getVariables())).findFirst();

		if (findFirst.isEmpty())
			return eval(interpretation, formula.getQuantified(), valuation, SOvaluation);

		int tuplearity = findFirst.get().getSecond();
		Stream<Relation<U>> relationStream = PowerSetLexicographic
				.stream(KTupleEnumeration.stream(new ArrayList<>(interpretation.getUniverse()), tuplearity)
						.collect(Collectors.toUnmodifiableList()))
				.map(s -> new RelationGeneralImpl<>(tuplearity, s));

		if (formula.getQuantifyer() == ClassicalQuantifier.FORALL) {
			return relationStream.allMatch(r -> {
				Function<P, Relation<U>> nval = var -> {
					if (var.equals(formula.getVariables()))
						return r;
					return SOvaluation.apply(var);
				};
				return eval(interpretation, formula.getQuantified(), valuation, nval);
			});
		}
		if (formula.getQuantifyer() == ClassicalQuantifier.EXISTS) {
			return relationStream.anyMatch(r -> {
				Function<P, Relation<U>> nval = var -> {
					if (var.equals(formula.getVariables()))
						return r;
					return SOvaluation.apply(var);
				};
				return eval(interpretation, formula.getQuantified(), valuation, nval);
			});
		}
		throw new UnsupportedOperationException("Unknown quantifier" + formula.getQuantifyer());
	}


}
