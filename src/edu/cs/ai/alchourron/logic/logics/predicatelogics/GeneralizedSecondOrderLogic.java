package edu.cs.ai.alchourron.logic.logics.predicatelogics;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import edu.cs.ai.alchourron.logic.Formula;
import edu.cs.ai.alchourron.logic.ModelTheoreticLogic;
import edu.cs.ai.alchourron.logic.semantics.interpretations.FiniteStructure;
import edu.cs.ai.alchourron.logic.syntax.formula.ClassicalQuantifier;
import edu.cs.ai.alchourron.logic.syntax.formula.FormulaAND;
import edu.cs.ai.alchourron.logic.syntax.formula.FormulaBiImplication;
import edu.cs.ai.alchourron.logic.syntax.formula.FormulaFalsum;
import edu.cs.ai.alchourron.logic.syntax.formula.FormulaImplication;
import edu.cs.ai.alchourron.logic.syntax.formula.FormulaNeg;
import edu.cs.ai.alchourron.logic.syntax.formula.FormulaOR;
import edu.cs.ai.alchourron.logic.syntax.formula.FormulaPredicate;
import edu.cs.ai.alchourron.logic.syntax.formula.FormulaPredicateVariable;
import edu.cs.ai.alchourron.logic.syntax.formula.FormulaQuantification;
import edu.cs.ai.alchourron.logic.syntax.formula.FormulaSOQuantification;
import edu.cs.ai.alchourron.logic.syntax.formula.FormulaVerum;
import edu.cs.ai.alchourron.logic.syntax.formula.LogicalOperator;
import edu.cs.ai.alchourron.logic.syntax.terms.FunctionTerm;
import edu.cs.ai.alchourron.logic.syntax.terms.Term;
import edu.cs.ai.alchourron.logic.syntax.terms.VariableTerm;
import edu.cs.ai.math.combinatorics.KTupleEnumeration;
import edu.cs.ai.math.combinatorics.PowerSetLexicographic;
import edu.cs.ai.math.settheory.Pair;
import edu.cs.ai.math.settheory.Tuple;
import edu.cs.ai.math.settheory.relation.Relation;
import edu.cs.ai.math.settheory.relation.implementation.RelationGeneralImpl;

/***
 * Logical System representing propositional logic over symbol space of type
 * {@link PSym}.
 * 
 * @author Kai Sauerwald
 *
 * @param <R> The type for Relationsymbols
 * @param <K> The type for Functionsymbols
 * @param <V> The type for first-order variables
 * @param <P> The type for second-order variables
 */
public class GeneralizedSecondOrderLogic<R, K, V, Q, P, QSO, S extends GeneralizedSOSignature<R, K, V, Q, P, QSO>> implements
		ModelTheoreticLogic<FiniteStructure<?, R, K, S>, Formula<S>, Boolean, S> {

	public Set<Pair<P, Integer>> getFreeSOVariables(Formula<S> formula) {
		if (formula instanceof FormulaPredicate<?, ?>) {
			return Set.of();
		}
		if (formula instanceof FormulaPredicateVariable<?, ?, ?>) {
			FormulaPredicateVariable<QSO, P, S> pred = (FormulaPredicateVariable<QSO, P, S>) formula;

			return Set.of(new Pair<>(pred.getSymbol(), pred.getArity()));
		}

		if (formula instanceof FormulaSOQuantification<?, ?, ?>) {
			FormulaSOQuantification<QSO, P, S> quantor = (FormulaSOQuantification<QSO, P, S>) formula;

			Set<Pair<P, Integer>> result = new HashSet<>(getFreeSOVariables(quantor.getQuantified()));
			result.removeIf(p -> p.getFirst().equals(quantor.getVariables()));
			return result;
		}

		if (formula instanceof LogicalOperator<?>) {
			LogicalOperator<S> operator = (LogicalOperator<S>) formula;

			Set<Pair<P, Integer>> result = new HashSet<>();
			for (Formula<S> op : operator.getOperands()) {
				result.addAll(getFreeSOVariables(op));
			}

			return result;
		}

		throw new IllegalArgumentException("unexpected formula type");
	}

	public Set<V> getFreeVariables(Term<S> term) {
		if (term instanceof FunctionTerm<?, ?>) {
			FunctionTerm<K, S> new_name = (FunctionTerm<K, S>) term;

			Set<V> result = new HashSet<V>();
			new_name.getSubTerms().forEach(t -> result.addAll(getFreeVariables(t)));
			return result;
		}
		if (term instanceof VariableTerm<?, ?>) {
			VariableTerm<V, S> new_name = (VariableTerm<V, S>) term;
			return Set.of(new_name.getVariable());
		}
		throw new IllegalArgumentException("unexpected term type");
	}

	public Set<V> getFreeVariables(Formula<S> formula) {
		if (formula instanceof FormulaPredicate<?, ?>) {
			FormulaPredicate<R, S> pred = (FormulaPredicate<R, S>) formula;
			Set<V> result = new HashSet<>();
			for (Term<S> term : pred.getTerms()) {
				result.addAll(getFreeVariables(term));
			}

			return result;
		}
		if (formula instanceof FormulaPredicateVariable<?, ?, ?>) {
			FormulaPredicateVariable<QSO, P, S> pred = (FormulaPredicateVariable<QSO, P, S>) formula;

			Set<V> result = new HashSet<>();
			for (Term<S> term : pred.getTerms()) {
				result.addAll(getFreeVariables(term));
			}

			return result;
		}

		if (formula instanceof FormulaQuantification<?, ?, ?>) {
			FormulaQuantification<Q, V, S> quantor = (FormulaQuantification<Q, V, S>) formula;

			Set<V> result = new HashSet<>(getFreeVariables(quantor.getQuantified()));
			result.removeIf(v -> v.equals(quantor.getVariables()));
			return result;
		}

		if (formula instanceof LogicalOperator<?>) {
			LogicalOperator<S> operator = (LogicalOperator<S>) formula;

			Set<V> result = new HashSet<>();
			for (Formula<S> op : operator.getOperands()) {
				result.addAll(getFreeVariables(op));
			}
			return result;
		}

		throw new IllegalArgumentException("unexpected formula type");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * edu.cs.ai.alchourron.logic.LogicalSystem#eval(edu.cs.ai.alchourron.logic.
	 * Interpretation, edu.cs.ai.alchourron.logic.Formula)
	 */
	@Override
	public Boolean eval(FiniteStructure<?, R, K, S> interpretation,
			Formula<S> formula) {
		if (getFreeVariables(formula).isEmpty()) {
			return eval(interpretation, formula, var -> null, pvar -> null);
		}
		throw new UnsupportedOperationException("Not implemeted yed");
	}

	/***
	 * Evaluations a formula under a valuation function
	 * 
	 * @author Kai Sauerwald
	 * @param interpretation The interpretation
	 * @param formula        The formula to be interpreted
	 * @param valuation      A valuation for the variables of type <V>
	 */
	public <U> Boolean eval(FiniteStructure<U, R, K, S> interpretation,
			Formula<S> formula, Function<V, U> valuation,
			Function<P, Relation<U>> SOvaluation) {

		if (formula instanceof FormulaPredicate<?, ?>) {
			FormulaPredicate<R, S> pred = (FormulaPredicate<R, S>) formula;
			Tuple<U> tuple = new Tuple<U>(pred.getTerms().stream().sorted().map(t -> eval(interpretation, t, valuation))
					.collect(Collectors.toUnmodifiableList()));
			return interpretation.getRelation(pred.getSymbol()).contains(tuple);
		}
		if (formula instanceof FormulaPredicateVariable<?, ?, ?>) {
			FormulaPredicateVariable<QSO, P, S> pred = (FormulaPredicateVariable<QSO, P, S>) formula;
			Tuple<U> tuple = new Tuple<U>(pred.getTerms().stream().sorted().map(t -> eval(interpretation, t, valuation))
					.collect(Collectors.toUnmodifiableList()));
			return SOvaluation.apply(pred.getSymbol()).contains(tuple);
		}
		if (formula instanceof FormulaQuantification<?, ?, ?>) {
			FormulaQuantification<Q, V, S> quantor = (FormulaQuantification<Q, V, S>) formula;
			if (quantor.getQuantifyer() == ClassicalQuantifier.FORALL) {
				return interpretation.getUniverse().stream().allMatch(u -> {

					Function<V, U> nval = var -> {
						if (var.equals(quantor.getVariables()))
							return u;
						return valuation.apply(var);
					};

					return eval(interpretation, quantor.getQuantified(), nval, SOvaluation);
				});
			}
			if (quantor.getQuantifyer() == ClassicalQuantifier.EXISTS) {
				return interpretation.getUniverse().stream().anyMatch(u -> {

					Function<V, U> nval = var -> {
						if (var.equals(quantor.getVariables()))
							return u;
						return valuation.apply(var);
					};

					return eval(interpretation, quantor.getQuantified(), nval, SOvaluation);
				});
			}
		}

		if (formula instanceof FormulaSOQuantification<?, ?, ?>) {
			FormulaSOQuantification<QSO, P, S> quantor = (FormulaSOQuantification<QSO, P, S>) formula;
			Optional<Pair<P, Integer>> findFirst = getFreeSOVariables(quantor.getQuantified()).stream()
					.filter(p -> p.getFirst().equals(quantor.getVariables())).findFirst();

			if (findFirst.isEmpty())
				return eval(interpretation, quantor.getQuantified(), valuation, SOvaluation);

			int tuplearity = findFirst.get().getSecond();
			Stream<Relation<U>> relationStream = PowerSetLexicographic
					.stream(KTupleEnumeration.stream(new ArrayList<>(interpretation.getUniverse()), tuplearity)
							.collect(Collectors.toUnmodifiableList()))
					.map(s -> new RelationGeneralImpl<>(tuplearity, s));

			if (quantor.getQuantifyer() == ClassicalQuantifier.FORALL) {
				return relationStream.allMatch(r -> {
					Function<P, Relation<U>> nval = var -> {
						if (var.equals(quantor.getVariables()))
							return r;
						return SOvaluation.apply(var);
					};
					return eval(interpretation, quantor.getQuantified(), valuation, nval);
				});
			}
			if (quantor.getQuantifyer() == ClassicalQuantifier.EXISTS) {
				return relationStream.anyMatch(r -> {
					Function<P, Relation<U>> nval = var -> {
						if (var.equals(quantor.getVariables()))
							return r;
						return SOvaluation.apply(var);
					};
					return eval(interpretation, quantor.getQuantified(), valuation, nval);
				});
			}
		}
		if (formula instanceof FormulaAND<?>) {
			FormulaAND<S> and = (FormulaAND<S>) formula;
			return and.getOperands().stream().allMatch(o -> eval(interpretation, o, valuation, SOvaluation));
		}
		if (formula instanceof FormulaOR<?>) {
			FormulaOR<S> or = (FormulaOR<S>) formula;
			return or.getOperands().stream().anyMatch(o -> eval(interpretation, o, valuation, SOvaluation));
		}
		if (formula instanceof FormulaImplication<?>) {
			FormulaImplication<S> implication = (FormulaImplication<S>) formula;
			return !eval(interpretation, implication.getPremise(), valuation, SOvaluation)
					|| eval(interpretation, implication.getConclusion(), valuation, SOvaluation);
		}

		if (formula instanceof FormulaBiImplication<?>) {
			FormulaBiImplication<S> implication = (FormulaBiImplication<S>) formula;
			boolean b1 = eval(interpretation, implication.getFirst(), valuation, SOvaluation);
			boolean b2 = eval(interpretation, implication.getSecond(), valuation, SOvaluation);
			return (b1 && b2) || (!b1 && !b2);
		}
		if (formula instanceof FormulaNeg<?>) {
			FormulaNeg<S> neg = (FormulaNeg<S>) formula;
			return !eval(interpretation, neg.getOperands().get(0), valuation, SOvaluation);
		}
		if (formula instanceof FormulaFalsum<?>) {
			return false;
		}
		if (formula instanceof FormulaVerum<?>) {
			return true;
		}

		throw new IllegalArgumentException("unexpected formula type");
	}

	/***
	 * Evaluations a term under a valuation function
	 * 
	 * @author Kai Sauerwald
	 * @param interpretation The interpretation
	 * @param term           The term to be interpreted
	 * @param valuation      A valuation for the variables of type <V>
	 */
	public <U> U eval(FiniteStructure<U, R, K, S> interpretation,
			Term<S> term, Function<V, U> valuation) {
		if (term instanceof FunctionTerm<?, ?>) {
			FunctionTerm<K, S> fterm = (FunctionTerm<K, S>) term;
			Tuple<U> tuple = new Tuple<>(fterm.getSubTerms().stream().sorted()
					.map(t -> eval(interpretation, t, valuation)).collect(Collectors.toUnmodifiableList()));

			return interpretation.getFunction(fterm.getSymbol()).apply(tuple);
		}
		if (term instanceof VariableTerm<?, ?>) {
			VariableTerm<V, S> vterm = (VariableTerm<V, S>) term;
			return valuation.apply(vterm.getVariable());
		}
		throw new InvalidParameterException("Unexpeted term");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * edu.cs.ai.alchourron.logic.LogicalSystem#entails(edu.cs.ai.alchourron.logic.
	 * Interpretation, edu.cs.ai.alchourron.logic.Formula)
	 */
	@Override
	public boolean satisfies(FiniteStructure<?, R, K, S> interpretation,
			Formula<S> formula) {

		return eval(interpretation, formula);
	}

	@Override
	public boolean validFormula(Formula<S> formula) {
		return true;
	}

}
