/**
 * 
 */
package edu.cs.ai.alchourron.logic.example;

import java.util.List;
import java.util.Set;

import edu.cs.ai.alchourron.logic.Formula;
import edu.cs.ai.alchourron.logic.fo.FOSignature;
import edu.cs.ai.alchourron.logic.fo.FiniteStructure;
import edu.cs.ai.alchourron.logic.propositional.PropositionalSignature;
import edu.cs.ai.alchourron.logic.syntax.formula.Proposition;
import edu.cs.ai.alchourron.tools.Pair;
import edu.cs.ai.alchourron.tools.Relation;
import edu.cs.ai.alchourron.tools.Tuple;

/**
 * @author Kai Sauerwald
 *
 */
public class FOTest {

	enum Empty {
	};

	enum ABPredicates {
		IsA, IsB;
	};

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		FOSignature<ABPredicates, Empty, Character> signature = new FOSignature<>(
				List.of(ABPredicates.IsA, ABPredicates.IsB), List.of(1, 1));

		Relation<Integer> IsAIntp = new Relation<>();
		IsAIntp.add(new Tuple<>(0));
		IsAIntp.add(new Tuple<>(2));
		IsAIntp.add(new Tuple<>(4));
		Relation<Integer> IsBIntp = new Relation<>();
		IsBIntp.add(new Tuple<>(1));
		IsBIntp.add(new Tuple<>(3));
		IsBIntp.add(new Tuple<>(5));

		FiniteStructure<Integer, ABPredicates, Empty, FOSignature<ABPredicates, Empty, Character>> structure = new FiniteStructure<>(
				signature, Set.of(0, 1, 2, 3, 4, 5),
				List.of(new Pair<>(ABPredicates.IsA, IsAIntp), new Pair<>(ABPredicates.IsB, IsBIntp)), List.of());

//		Formula<FOSignature<ABPredicates, Empty, Character>> myform = new Pred
		
	}

}
