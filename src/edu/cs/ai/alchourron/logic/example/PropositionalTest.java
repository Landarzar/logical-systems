/**
 * 
 */
package edu.cs.ai.alchourron.logic.example;

import edu.cs.ai.alchourron.logic.Formula;
import edu.cs.ai.alchourron.logic.logics.propositional.PropositionalNormalForms;
import edu.cs.ai.alchourron.logic.logics.propositional.PropositionalSignature;
import edu.cs.ai.alchourron.logic.syntax.formula.FormulaAND;
import edu.cs.ai.alchourron.logic.syntax.formula.FormulaOR;
import edu.cs.ai.alchourron.logic.syntax.formula.FormulaProposition;

/**
 * @author Kai Sauerwald
 *
 */
public class PropositionalTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		PropositionalSignature<Character> signature = new PropositionalSignature<Character>('a', 'b', 'c');
		Formula<PropositionalSignature<Character>> prop1 = new FormulaAND<>(signature, new FormulaProposition<>(signature, 'b'), new FormulaProposition<>(signature, 'c'));
		Formula<PropositionalSignature<Character>> prop = new FormulaOR<>(signature,
				prop1,new FormulaProposition<>(signature, 'a'));

		PropositionalNormalForms<Character> nf = new PropositionalNormalForms<>();

		System.out.println(prop);
		System.out.println(nf.formulaToCNF(prop));

	}

}
