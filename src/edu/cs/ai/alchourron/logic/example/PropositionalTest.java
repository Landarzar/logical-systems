/**
 * 
 */
package edu.cs.ai.alchourron.logic.example;

import edu.cs.ai.alchourron.logic.propositional.PropositionalSignature;
import edu.cs.ai.alchourron.logic.syntax.formula.Proposition;

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
		
		Proposition<Character,PropositionalSignature<Character>> prop = new Proposition<>(signature, 'a');
		
		
		
	}

}
