/**
 * 
 */
package edu.cs.ai.alchourron.logic.example;

import java.util.List;
import java.util.Set;

import edu.cs.ai.alchourron.logic.Formula;
import edu.cs.ai.alchourron.logic.logics.predicatelogics.FOSignature;
import edu.cs.ai.alchourron.logic.logics.predicatelogics.FiniteStructure;
import edu.cs.ai.alchourron.logic.logics.predicatelogics.FirstOrderLogic;
import edu.cs.ai.alchourron.logic.logics.predicatelogics.StandardQuantifier;
import edu.cs.ai.alchourron.logic.syntax.formula.FormulaPredicate;
import edu.cs.ai.alchourron.logic.syntax.formula.FormulaQuantification;
import edu.cs.ai.alchourron.logic.syntax.terms.VariableTerm;
import edu.cs.ai.math.setheory.objects.Pair;
import edu.cs.ai.math.setheory.objects.Relation;
import edu.cs.ai.math.setheory.objects.RelationImpl;
import edu.cs.ai.math.setheory.objects.Tuple;

/**
 * @author Kai Sauerwald
 *
 */
public class FOTest {

	enum Empty {
	};

	enum ABPredicates {
		IsA, IsB, SUCC;
	};

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		FOSignature<ABPredicates, Empty, Character> signature = new FOSignature<>(
				List.of(ABPredicates.IsA, ABPredicates.IsB, ABPredicates.SUCC), List.of(1, 1, 2));

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
				return "(0,1), (1,2),...";
			}
		};

		FiniteStructure<Integer, ABPredicates, Empty, FOSignature<ABPredicates, Empty, Character>> structure = new FiniteStructure<>(
				signature, Set.of(0, 1, 2, 3, 4, 5),
				List.of(new Pair<>(ABPredicates.IsA, IsAIntp), new Pair<>(ABPredicates.IsB, IsBIntp), new Pair<>(ABPredicates.SUCC,SuccIntp)), List.of());

		Formula<FOSignature<ABPredicates, Empty, Character>> myform = new FormulaQuantification<>(signature,
				StandardQuantifier.EXISTS, 'x',
				new FormulaPredicate<>(signature, ABPredicates.IsA, List.of(new VariableTerm<>('x'))));

		FirstOrderLogic<ABPredicates, Empty, Character> fo = new FirstOrderLogic<>();
		System.out.println(structure + " |= " + myform +" ? " + fo.satisfies(structure, myform));

	}

}
