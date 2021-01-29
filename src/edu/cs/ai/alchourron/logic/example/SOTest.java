/**
 * 
 */
package edu.cs.ai.alchourron.logic.example;

import java.util.List;
import java.util.Set;

import edu.cs.ai.alchourron.logic.Formula;
import edu.cs.ai.alchourron.logic.logics.predicatelogics.FiniteStructure;
import edu.cs.ai.alchourron.logic.logics.predicatelogics.SOSignature;
import edu.cs.ai.alchourron.logic.logics.predicatelogics.SecondOrderLogic;
import edu.cs.ai.alchourron.logic.logics.predicatelogics.StandardQuantifier;
import edu.cs.ai.alchourron.logic.syntax.formula.FormulaPredicateVariable;
import edu.cs.ai.alchourron.logic.syntax.formula.FormulaQuantification;
import edu.cs.ai.alchourron.logic.syntax.formula.FormulaSOQuantification;
import edu.cs.ai.alchourron.logic.syntax.terms.VariableTerm;
import edu.cs.ai.math.setheory.objects.Pair;
import edu.cs.ai.math.setheory.objects.Relation;
import edu.cs.ai.math.setheory.objects.RelationImpl;
import edu.cs.ai.math.setheory.objects.Tuple;

/**
 * @author Kai Sauerwald
 *
 */
public class SOTest {

	enum Empty {
	};

	enum ABPredicates {
		SignA, SignB, SUCC;
	};

	enum SOVars {
		R, P, X, Y, Z;
	};

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		SOSignature<ABPredicates, Empty, Character, SOVars> signature = new SOSignature<>(
				List.of(ABPredicates.SignA, ABPredicates.SignB, ABPredicates.SUCC), List.of(1, 1, 2));

		Relation<Integer> IsAIntp = new RelationImpl<>(1, Set.of(new Tuple<>(0), new Tuple<>(2), new Tuple<>(4)));
		Relation<Integer> IsBIntp = new RelationImpl<>(1, Set.of(new Tuple<>(1), new Tuple<>(3), new Tuple<>(5)));
		Relation<Integer> SuccIntp = new Relation<Integer>() {

			@Override
			public boolean contains(Tuple<Integer> tuple) {
				if (tuple.getArity() != 2)
					throw new IllegalArgumentException("wrong arity of tuple");

				return tuple.getIth(0) + 1 == tuple.getIth(1);
			}

			@Override
			public int getArity() {
				return 2;
			}

			@Override
			public String toString() {
				return "(0,1), (1,2), ...";
			}
		};

		FiniteStructure<Integer, ABPredicates, Empty, SOSignature<ABPredicates, Empty, Character, SOVars>> structure = new FiniteStructure<>(
				signature, Set.of(0, 1, 2, 3, 4, 5), List.of(new Pair<>(ABPredicates.SignA, IsAIntp),
						new Pair<>(ABPredicates.SignB, IsBIntp), new Pair<>(ABPredicates.SUCC, SuccIntp)),
				List.of());

		Formula<SOSignature<ABPredicates, Empty, Character, SOVars>> myform = new FormulaSOQuantification<>(signature,
				StandardQuantifier.FORALL, SOVars.P, new FormulaQuantification<>(signature, StandardQuantifier.FORALL,
						'x', new FormulaPredicateVariable<>(signature, SOVars.P, List.of(new VariableTerm<>('x')))));

		SecondOrderLogic<ABPredicates, Empty, Character, SOVars> so = new SecondOrderLogic<>();
		System.out.println(structure + " |= " + myform + " ? " + so.satisfies(structure, myform));

	}

}