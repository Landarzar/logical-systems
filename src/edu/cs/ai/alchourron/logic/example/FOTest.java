/**
 * 
 */
package edu.cs.ai.alchourron.logic.example;

import java.util.List;
import java.util.Set;

import edu.cs.ai.alchourron.logic.Formula;
import edu.cs.ai.alchourron.logic.fo.FOSignature;
import edu.cs.ai.alchourron.logic.fo.FiniteStructure;
import edu.cs.ai.alchourron.logic.fo.FirstOrderLogic;
import edu.cs.ai.alchourron.logic.fo.StandardQuantifier;
import edu.cs.ai.alchourron.logic.propositional.PropositionalSignature;
import edu.cs.ai.alchourron.logic.syntax.formula.Predicate;
import edu.cs.ai.alchourron.logic.syntax.formula.Proposition;
import edu.cs.ai.alchourron.logic.syntax.formula.Quantor;
import edu.cs.ai.alchourron.logic.syntax.terms.VariableTerm;
import edu.cs.ai.alchourron.tools.Pair;
import edu.cs.ai.alchourron.tools.Relation;
import edu.cs.ai.alchourron.tools.RelationImpl;
import edu.cs.ai.alchourron.tools.Tuple;

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

		Relation<Integer> IsAIntp = new RelationImpl(1, Set.of(new Tuple<>(0), new Tuple<>(2), new Tuple<>(4)));
		Relation<Integer> IsBIntp = new RelationImpl(1, Set.of(new Tuple<>(1), new Tuple<>(3), new Tuple<>(5)));
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
		};

		FiniteStructure<Integer, ABPredicates, Empty, FOSignature<ABPredicates, Empty, Character>> structure = new FiniteStructure<>(
				signature, Set.of(0, 1, 2, 3, 4, 5),
				List.of(new Pair<>(ABPredicates.IsA, IsAIntp), new Pair<>(ABPredicates.IsB, IsBIntp)), List.of());

		Formula<FOSignature<ABPredicates, Empty, Character>> myform = new Quantor<>(signature,
				StandardQuantifier.EXISTS, 'x',
				new Predicate<>(signature, ABPredicates.IsA, List.of(new VariableTerm<>('x'))));

		FirstOrderLogic<ABPredicates, Empty, Character> fo = new FirstOrderLogic<>();
		System.out.println(fo.satisfies(structure, myform));

	}

}
