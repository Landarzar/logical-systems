package edu.cs.ai.alchourron.logic.propositional;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import edu.cs.ai.alchourron.logic.Signature;

/***
 * Represents a propositional signature, which uses Elements of the type V as symbol supply.
 * @author Kai Sauerwald
 *
 * @param <V> The type of the symbols
 */
public class PropositionalSignature<V> implements Signature {
	
	/***
	 * The symbols of this signature
	 * @author Kai Sauerwald
	 */
	HashSet<V> symbols;
	
	/***
	 * Constructs a new propositional signature
	 * @author Kai Sauerwald
	 * @param symbols The symbols of this signature
	 */
	public PropositionalSignature(V[] symbols) {
		this.symbols = new HashSet<>();
		for (int i = 0; i < symbols.length; i++) {
			V v = symbols[i];
			this.symbols.add(v);
		}
	}
	
	/***
	 * Constructs a new propositional signature
	 * @author Kai Sauerwald
	 * @param symbols The symbols of this signature
	 */
	public PropositionalSignature(Collection<V> symbols) {
		this.symbols = new HashSet<>(symbols);
	}
	
	/***
	 * Returns the set of the symbols of this signature 
	 * @author Kai Sauerwald
	 */
	public Set<V> getSymbols(){
		return Collections.unmodifiableSet(symbols);
	}
}
