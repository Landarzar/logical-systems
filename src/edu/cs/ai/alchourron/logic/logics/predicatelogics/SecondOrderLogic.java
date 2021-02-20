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
public class SecondOrderLogic<R extends Enum<R>, K extends Enum<K>, V, P> implements
		ModelTheoreticLogic<FiniteStructure<?, R, K, SOSignature<R, K, V, P>>, Formula<SOSignature<R, K, V, P>>, Boolean, SOSignature<R, K, V, P>> {

	public Set<Pair<P, Integer>> getFreeSOVariables(Formula<SOSignature<R, K, V, P>> formula) {
		if (formula instanceof FormulaPredicate<?, ?, ?, ?>) {
			return Set.of();
		}
		if (formula instanceof FormulaPredicateVariable<?, ?, ?, ?, ?>) {
			FormulaPredicateVariable<R, K, V, P, SOSignature<R, K, V, P>> pred = (FormulaPredicateVariable<R, K, V, P, SOSignature<R, K, V, P>>) formula;

			return Set.of(new Pair<>(pred.getSymbol(), pred.getArity()));
		}

		if (formula instanceof FormulaSOQuantification<?, ?, ?>) {
			FormulaSOQuantification<ClassicalQuantifier, P, SOSignature<R, K, V, P>> quantor = (FormulaSOQuantification<ClassicalQuantifier, P, SOSignature<R, K, V, P>>) formula;

			Set<Pair<P, Integer>> result = new HashSet<>(getFreeSOVariables(quantor.getQuantified()));
			result.removeIf(p -> p.getFirst().equals(quantor.getVariables()));
			return result;
		}

		if (formula instanceof LogicalOperator<?>) {
			LogicalOperator<SOSignature<R, K, V, P>> operator = (LogicalOperator<SOSignature<R, K, V, P>>) formula;

			Set<Pair<P, Integer>> result = new HashSet<>();
			for (Formula<SOSignature<R, K, V, P>> op : operator.getOperands()) {
				result.addAll(getFreeSOVariables(op));
			}

			return result;
		}

		throw new IllegalArgumentException("unexpected formula type");
	}

	public Set<V> getFreeVariables(Term<K, V> term) {
		if (term instanceof FunctionTerm<?, ?>) {
			FunctionTerm<K, V> new_name = (FunctionTerm<K, V>) term;

			Set<V> result = new HashSet<V>();
			new_name.getSubTerms().forEach(t -> result.addAll(getFreeVariables(t)));
			return result;
		}
		if (term instanceof VariableTerm<?, ?>) {
			VariableTerm<K, V> new_name = (VariableTerm<K, V>) term;
			return Set.of(new_name.getVariable());
		}
		throw new IllegalArgumentException("unexpected term type");
	}

	public Set<V> getFreeVariables(Formula<SOSignature<R, K, V, P>> formula) {
		if (formula instanceof FormulaPredicate<?, ?, ?, ?>) {
			FormulaPredicate<R, K, V, SOSignature<R, K, V, P>> pred = (FormulaPredicate<R, K, V, SOSignature<R, K, V, P>>) formula;
			Set<V> result = new HashSet<>();
			for (Term<K, V> term : pred.getTerms()) {
				result.addAll(getFreeVariables(term));
			}

			return result;
		}
		if (formula instanceof FormulaPredicateVariable<?, ?, ?, ?, ?>) {
			FormulaPredicateVariable<R, K, V, P, SOSignature<R, K, V, P>> pred = (FormulaPredicateVariable<R, K, V, P, SOSignature<R, K, V, P>>) formula;

			Set<V> result = new HashSet<>();
			for (Term<K, V> term : pred.getTerms()) {
				result.addAll(getFreeVariables(term));
			}

			return result;
		}

		if (formula instanceof FormulaQuantification<?, ?, ?>) {
			FormulaQuantification<ClassicalQuantifier, V, SOSignature<R, K, V, P>> quantor = (FormulaQuantification<ClassicalQuantifier, V, SOSignature<R, K, V, P>>) formula;

			Set<V> result = new HashSet<>(getFreeVariables(quantor.getQuantified()));
			result.removeIf(v -> v.equals(quantor.getVariables()));
			return result;
		}

		if (formula instanceof LogicalOperator<?>) {
			LogicalOperator<SOSignature<R, K, V, P>> operator = (LogicalOperator<SOSignature<R, K, V, P>>) formula;

			Set<V> result = new HashSet<>();
			for (Formula<SOSignature<R, K, V, P>> op : operator.getOperands()) {
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
	public Boolean eval(FiniteStructure<?, R, K, SOSignature<R, K, V, P>> interpretation,
			Formula<SOSignature<R, K, V, P>> formula) {
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
	public <U> Boolean eval(FiniteStructure<U, R, K, SOSignature<R, K, V, P>> interpretation,
			Formula<SOSignature<R, K, V, P>> formula, Function<V, U> valuation, Function<P, Relation<U>> SOvaluation) {

		if (formula instanceof FormulaPredicate<?, ?, ?, ?>) {
			FormulaPredicate<R, K, V, SOSignature<R, K, V, P>> pred = (FormulaPredicate<R, K, V, SOSignature<R, K, V, P>>) formula;
			Tuple<U> tuple = new Tuple<>(pred.getTerms().stream().sorted().map(t -> eval(interpretation, t, valuation))
					.collect(Collectors.toUnmodifiableList()));
			return interpretation.getRelation(pred.getSymbol()).contains(tuple);
		}
		if (formula instanceof FormulaPredicateVariable<?, ?, ?, ?, ?>) {
			FormulaPredicateVariable<R, K, V, P, SOSignature<R, K, V, P>> pred = (FormulaPredicateVariable<R, K, V, P, SOSignature<R, K, V, P>>) formula;
			Tuple<U> tuple = new Tuple<>(pred.getTerms().stream().sorted().map(t -> eval(interpretation, t, valuation))
					.collect(Collectors.toUnmodifiableList()));
			return SOvaluation.apply(pred.getSymbol()).contains(tuple);
		}
		if (formula instanceof FormulaQuantification<?, ?, ?>) {
			FormulaQuantification<ClassicalQuantifier, V, SOSignature<R, K, V, P>> quantor = (FormulaQuantification<ClassicalQuantifier, V, SOSignature<R, K, V, P>>) formula;
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
			FormulaSOQuantification<ClassicalQuantifier, P, SOSignature<R, K, V, P>> quantor = (FormulaSOQuantification<ClassicalQuantifier, P, SOSignature<R, K, V, P>>) formula;
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
			FormulaAND<SOSignature<R, K, V, P>> and = (FormulaAND<SOSignature<R, K, V, P>>) formula;
			return and.getOperands().stream().allMatch(o -> eval(interpretation, o, valuation, SOvaluation));
		}
		if (formula instanceof FormulaOR<?>) {
			FormulaOR<SOSignature<R, K, V, P>> or = (FormulaOR<SOSignature<R, K, V, P>>) formula;
			return or.getOperands().stream().anyMatch(o -> eval(interpretation, o, valuation, SOvaluation));
		}
		if (formula instanceof FormulaImplication<?>) {
			FormulaImplication<SOSignature<R, K, V, P>> implication = (FormulaImplication<SOSignature<R, K, V, P>>) formula;
			return !eval(interpretation, implication.getPremise(), valuation, SOvaluation)
					|| eval(interpretation, implication.getConclusion(), valuation, SOvaluation);
		}

		if (formula instanceof FormulaBiImplication<?>) {
			FormulaBiImplication<SOSignature<R, K, V, P>> implication = (FormulaBiImplication<SOSignature<R, K, V, P>>) formula;
			boolean b1 = eval(interpretation, implication.getFirst(), valuation, SOvaluation);
			boolean b2 = eval(interpretation, implication.getSecond(), valuation, SOvaluation);
			return (b1 && b2) || (!b1 && !b2);
		}
		if (formula instanceof FormulaNeg<?>) {
			FormulaNeg<SOSignature<R, K, V, P>> neg = (FormulaNeg<SOSignature<R, K, V, P>>) formula;
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
	public <U> U eval(FiniteStructure<U, R, K, SOSignature<R, K, V, P>> interpretation, Term<K, V> term,
			Function<V, U> valuation) {
		if (term instanceof FunctionTerm<?, ?>) {
			FunctionTerm<K, V> fterm = (FunctionTerm<K, V>) term;
			Tuple<U> tuple = new Tuple<>(fterm.getSubTerms().stream().sorted()
					.map(t -> eval(interpretation, t, valuation)).collect(Collectors.toUnmodifiableList()));

			return interpretation.getFunction(fterm.getSymbol()).apply(tuple);
		}
		if (term instanceof VariableTerm<?, ?>) {
			VariableTerm<K, V> vterm = (VariableTerm<K, V>) term;
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
	public boolean satisfies(FiniteStructure<?, R, K, SOSignature<R, K, V, P>> interpretation,
			Formula<SOSignature<R, K, V, P>> formula) {

		return eval(interpretation, formula);
	}

	@Override
	public boolean validFormula(Formula<SOSignature<R, K, V, P>> formula) {
		return true;
	}

}
