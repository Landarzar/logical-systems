/**
 * 
 */
package edu.cs.ai.alchourron.logic.example;

import edu.cs.ai.alchourron.logic.Formula;
import edu.cs.ai.alchourron.logic.logics.propositional.PropositionalNormalForms;
import edu.cs.ai.alchourron.logic.logics.propositional.PropositionalSignature;
import edu.cs.ai.alchourron.logic.syntax.formula.FormulaAND;
import edu.cs.ai.alchourron.logic.syntax.formula.FormulaNeg;
import edu.cs.ai.alchourron.logic.syntax.formula.FormulaOR;
import edu.cs.ai.alchourron.logic.syntax.formula.FormulaPropositionalAtom;

/**
 * @author Kai Sauerwald
 *
 */
public class PLTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		PropositionalSignature<Character> signature = new PropositionalSignature<Character>('a', 'b', 'c');
		Formula<PropositionalSignature<Character>> prop1 = new FormulaAND<>(new FormulaPropositionalAtom<>('b'),
				new FormulaPropositionalAtom<>('c'));
		Formula<PropositionalSignature<Character>> prop2 = new FormulaAND<>(new FormulaPropositionalAtom<>('a'),
				new FormulaPropositionalAtom<>('c'));
		Formula<PropositionalSignature<Character>> prop = new FormulaNeg<PropositionalSignature<Character>>(new FormulaOR<>(prop1, prop2, new FormulaPropositionalAtom<>('a')));

		PropositionalNormalForms<Character> nf = new PropositionalNormalForms<>();

		System.out.println("Formula: " + prop);
		System.out.println("CNF: " + nf.formulaToCNF(prop));
		System.out.println("NNF: " + nf.formulaToNegationNormalForm(prop));
		System.out.println("QMC: " + nf.computeQMC(prop, signature));
		System.out.println("QMC (Latex): " + nf.computeQMC(prop, signature).toLaTeX());

	}

}
