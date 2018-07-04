package edu.cs.ai.alchourron.logic.propositional;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import edu.cs.ai.alchourron.logic.Signature;

/***
 * Represents a propositional signature, which uses Elements of the type Psym as
 * symbol supply.
 * 
 * @author Kai Sauerwald
 *
 * @param <PSym>
 *            The type of the symbols
 */
public class PropositionalSignature<PSym> implements Signature, Iterable<PropositionalInterpretation<PSym, PropositionalSignature<PSym>>> {

	/***
	 * The symbols of this signature
	 * 
	 * @author Kai Sauerwald
	 */
	ArrayList<PSym> symbols;

	/***
	 * Constructs a new propositional signature
	 * 
	 * @author Kai Sauerwald
	 * @param symbols
	 *            The symbols of this signature
	 */
	public PropositionalSignature(PSym... symbols) {
		this.symbols = new ArrayList<>();
		for (int i = 0; i < symbols.length; i++) {
			PSym v = symbols[i];
			this.symbols.add(v);
		}
	}

	/***
	 * Constructs a new propositional signature
	 * 
	 * @author Kai Sauerwald
	 * @param symbols
	 *            The symbols of this signature
	 */
	public PropositionalSignature(Collection<PSym> symbols) {
		this.symbols = new ArrayList<>(symbols);
	}

	/***
	 * Returns the set of the symbols of this signature
	 * 
	 * @author Kai Sauerwald
	 */
	public List<PSym> getSymbols() {
		return Collections.unmodifiableList(symbols);
	}

    /**
     * Returns a {@code Stream} of all interpretations under this signature
     * @author Kai Sauerwald.
     */
	public Stream<PropositionalInterpretation<PSym, PropositionalSignature<PSym>>> stream() {
		return StreamSupport.stream(Spliterators.spliterator(iterator(), /* initial size */ 0L, Spliterator.IMMUTABLE), false);
	}

	/**
	 * Creates an iterator, that iterates over all interpretations under this signature.
	 * @author Kai Sauerwald
	 * @see java.lang.Iterable#iterator()
	 */
	@Override
	public Iterator<PropositionalInterpretation<PSym, PropositionalSignature<PSym>>> iterator() {
		String cmp = "";
		for (int i = 0; i < symbols.size(); i++) {
			cmp += "1";
		}
		final String cmp2 = cmp;

		PropositionalSignature<PSym> sig = this;
		return new Iterator<PropositionalInterpretation<PSym, PropositionalSignature<PSym>>>() {

			BigInteger bigint = BigInteger.ZERO;
			String max = cmp2;

			/*
			 * (non-Javadoc)
			 * 
			 * @see java.util.Iterator#hasNext()
			 */
			@Override
			public boolean hasNext() {
				return bigint.compareTo(new BigInteger(max, 2)) <= 0;
			}

			/*
			 * (non-Javadoc)
			 * 
			 * @see java.util.Iterator#next()
			 */
			@Override
			public PropositionalInterpretation<PSym, PropositionalSignature<PSym>> next() {
				LinkedList<PSym> temp = new LinkedList<>();
				for (int i = 0; i < symbols.size(); i++) {
					if (bigint.testBit(i))
						temp.add(symbols.get(i));
				}

				bigint = bigint.add(BigInteger.ONE);
				return new PropositionalInterpretation<PSym, PropositionalSignature<PSym>>(sig, temp);
			}

		};
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return symbols.hashCode();
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object o) {
		if (o == this)
            return true;
        if (!(o instanceof PropositionalSignature))
            return false;
        
        PropositionalSignature<PSym> sig = (PropositionalSignature<PSym>) o;

	    return this.symbols.equals(sig.symbols);
	}
}
