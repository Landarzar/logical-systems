package edu.cs.ai.alchourron.logic.propositional;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import edu.cs.ai.alchourron.logic.Signature;

/***
 * Represents a propositional signature, which uses Elements of the type Psym as symbol supply.
 * @author Kai Sauerwald
 *
 * @param <PSym> The type of the symbols
 */
public class PropositionalSignature<PSym> implements Signature {
	
	/***
	 * The symbols of this signature
	 * @author Kai Sauerwald
	 */
	HashSet<PSym> symbols;
	
	/***
	 * Constructs a new propositional signature
	 * @author Kai Sauerwald
	 * @param symbols The symbols of this signature
	 */
	public PropositionalSignature(PSym[] symbols) {
		this.symbols = new HashSet<>();
		for (int i = 0; i < symbols.length; i++) {
			PSym v = symbols[i];
			this.symbols.add(v);
		}
	}
	
	/***
	 * Constructs a new propositional signature
	 * @author Kai Sauerwald
	 * @param symbols The symbols of this signature
	 */
	public PropositionalSignature(Collection<PSym> symbols) {
		this.symbols = new HashSet<>(symbols);
	}
	
	/***
	 * Returns the set of the symbols of this signature 
	 * @author Kai Sauerwald
	 */
	public Set<PSym> getSymbols(){
		return Collections.unmodifiableSet(symbols);
	}
}
