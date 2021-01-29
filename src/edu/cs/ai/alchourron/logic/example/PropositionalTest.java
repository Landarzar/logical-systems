/**
 * 
 */
package edu.cs.ai.alchourron.logic.example;

import edu.cs.ai.alchourron.logic.propositional.PropositionalSignature;
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
		
		PropositionalSignature<Character> signature = new PropositionalSignature<Character>('a','b');
		
		FormulaProposition<Character,PropositionalSignature<Character>> prop = new FormulaProposition<>(signature, 'a');
		
		System.out.println(prop);
		
	}

}
