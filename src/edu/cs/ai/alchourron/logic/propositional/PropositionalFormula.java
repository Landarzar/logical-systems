package edu.cs.ai.alchourron.logic.propositional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import edu.cs.ai.alchourron.logic.Formula;
import edu.cs.ai.alchourron.logic.propositional.PropositionalFormula.PropositionalAtom;
import edu.cs.ai.alchourron.logic.syntax.LogicalAND;
import edu.cs.ai.alchourron.logic.syntax.LogicalOR;
import edu.cs.ai.alchourron.logic.syntax.Predicate;
import edu.cs.ai.alchourron.logic.syntax.SyntacticElement;
import edu.cs.ai.alchourron.logic.syntax.Term;

/***
 * Represents propositional formula.
 * 
 * @author Kai Sauerwald
 *
 * @param <PSym>
 *            The type of symbols over which the signature is defined
 */
public abstract class PropositionalFormula<PSym> implements Formula<PropositionalSignature<PSym>>, SyntacticElement<PropositionalSignature<PSym>> {
	
	public PropositionalFormula<PSym> Neg(){
		return new PropositionalNEG<>(this.getSignature(), this);
	}
	
	public PropositionalFormula<PSym> And(PropositionalFormula<PSym> f){
		return new PropositionalAND<>(this.getSignature(), this, f);
	}
	
	public PropositionalFormula<PSym> Or(PropositionalFormula<PSym> f){
		return new PropositionalOR<>(this.getSignature(), this, f);
	}
	
	public static <PSym> PropositionalFormula<PSym> getFalsum(PropositionalSignature<PSym> signature) {
		return new PropositionalAtom<>(signature, signature.getSymbols().get(0)).Neg()
		.And(new PropositionalAtom<>(signature, signature.getSymbols().get(0)));
	}
	
	public static <PSym> PropositionalFormula<PSym> getTautology(PropositionalSignature<PSym> signature) {
		return new PropositionalAtom<>(signature, signature.getSymbols().get(0)).Neg()
		.Or(new PropositionalAtom<>(signature, signature.getSymbols().get(0)));
	}


	public static class PropositionalAtom<PSym> extends PropositionalFormula<PSym> implements Predicate<PSym, PropositionalSignature<PSym>> {

		protected PSym symbol;
		protected PropositionalSignature<PSym> signature;

		/**
		 * Constructs a new instance of this class
		 * 
		 * @author Kai Sauerwald
		 */
		public PropositionalAtom(PropositionalSignature<PSym> signature, PSym sign) {
			this.symbol = sign;
			this.signature = signature;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see edu.cs.ai.alchourron.logic.Formula#getSyntaxTree()
		 */
		@Override
		public SyntacticElement<PropositionalSignature<PSym>> getSyntaxTree() throws UnsupportedOperationException {
			return this;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see edu.cs.ai.alchourron.logic.Formula#getSignature()
		 */
		@Override
		public PropositionalSignature<PSym> getSignature() {
			return signature;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see edu.cs.ai.alchourron.logic.syntax.SyntacticElement#stringify()
		 */
		@Override
		public String stringify() {
			return symbol.toString();
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see edu.cs.ai.alchourron.logic.syntax.Predicate#getSymbol()
		 */
		@Override
		public PSym getSymbol() {
			return symbol;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see edu.cs.ai.alchourron.logic.syntax.Predicate#getTerms()
		 */
		@Override
		public List<Term<PropositionalSignature<PSym>>> getTerms() {
			return List.of();
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see edu.cs.ai.alchourron.logic.syntax.Predicate#getArity()
		 */
		@Override
		public int getArity() {
			return 0;
		}
		
		/*
		 * (non-Javadoc)
		 * @see java.lang.Object#toString()
		 */
		@Override
		public String toString() {
			return symbol.toString();
		}
	}

	public static class PropositionalAND<PSym> extends PropositionalFormula<PSym> implements LogicalAND<PropositionalSignature<PSym>> {

		protected PropositionalSignature<PSym> signature;
		ArrayList<PropositionalFormula<PSym>> operands;

		/**
		 * Constructs a new instance of this class
		 * 
		 * @author Kai Sauerwald
		 */
		public PropositionalAND(PropositionalSignature<PSym> signature, Collection<PropositionalFormula<PSym>> operands) {
			this.signature = signature;
			this.operands = new ArrayList<>(operands);
		}
		

		/**
		 * Constructs a new instance of this class
		 * 
		 * @author Kai Sauerwald
		 */
		@SafeVarargs
		public PropositionalAND(PropositionalSignature<PSym> signature, PropositionalFormula<PSym>... operands) {
			this.signature = signature;
			this.operands = new ArrayList<>();
			for (PropositionalFormula<PSym> op : operands) {
				this.operands.add(op);
			}
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see edu.cs.ai.alchourron.logic.Formula#getSyntaxTree()
		 */
		@Override
		public SyntacticElement<PropositionalSignature<PSym>> getSyntaxTree() throws UnsupportedOperationException {
			return this;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see edu.cs.ai.alchourron.logic.Formula#getSignature()
		 */
		@Override
		public PropositionalSignature<PSym> getSignature() {
			return signature;
		}

		/*
		 * (non-Javadoc)
		 * @see edu.cs.ai.alchourron.logic.syntax.LogicalOperator#getOperands()
		 */
		@Override
		public List<? extends SyntacticElement<PropositionalSignature<PSym>>> getOperands() {
			return operands;
		}

		/*
		 * (non-Javadoc)
		 * @see edu.cs.ai.alchourron.logic.syntax.SyntacticElement#isAtom()
		 */
		@Override
		public boolean isAtom() {
			return false;
		}		
		
		/*
		 * (non-Javadoc)
		 * @see java.lang.Object#toString()
		 */
		@Override
		public String toString() {
			StringBuilder builder = new StringBuilder();
			builder.append("(");
			
			boolean first = true;
			
			for (PropositionalFormula<PSym> propositionalFormula : operands) {
				if(first) {
					builder.append(propositionalFormula.toString());
					continue;
				}
				builder.append(" AND ");
				builder.append(propositionalFormula.toString());
			}
			

			builder.append(")");
			return "(" + this.operands;
		}
	}

	public static class PropositionalOR<PSym> extends PropositionalFormula<PSym> implements LogicalOR<PropositionalSignature<PSym>> {

		protected PropositionalSignature<PSym> signature;
		ArrayList<PropositionalFormula<PSym>> operands;

		/**
		 * Constructs a new instance of this class
		 * 
		 * @author Kai Sauerwald
		 */
		public PropositionalOR(PropositionalSignature<PSym> signature, Collection<PropositionalFormula<PSym>> operands) {
			this.signature = signature;
			this.operands = new ArrayList<>(operands);
		}
		

		/**
		 * Constructs a new instance of this class
		 * 
		 * @author Kai Sauerwald
		 */
		@SafeVarargs
		public PropositionalOR(PropositionalSignature<PSym> signature, PropositionalFormula<PSym>... operands) {
			this.signature = signature;
			this.operands = new ArrayList<>();
			for (PropositionalFormula<PSym> op : operands) {
				this.operands.add(op);
			}
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see edu.cs.ai.alchourron.logic.Formula#getSyntaxTree()
		 */
		@Override
		public SyntacticElement<PropositionalSignature<PSym>> getSyntaxTree() throws UnsupportedOperationException {
			return this;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see edu.cs.ai.alchourron.logic.Formula#getSignature()
		 */
		@Override
		public PropositionalSignature<PSym> getSignature() {
			return signature;
		}

		/*
		 * (non-Javadoc)
		 * @see edu.cs.ai.alchourron.logic.syntax.LogicalOperator#getOperands()
		 */
		@Override
		public List<? extends SyntacticElement<PropositionalSignature<PSym>>> getOperands() {
			return operands;
		}

		/*
		 * (non-Javadoc)
		 * @see edu.cs.ai.alchourron.logic.syntax.SyntacticElement#isAtom()
		 */
		@Override
		public boolean isAtom() {
			return false;
		}		
		
		/*
		 * (non-Javadoc)
		 * @see java.lang.Object#toString()
		 */
		@Override
		public String toString() {
			StringBuilder builder = new StringBuilder();
			builder.append("(");
			
			boolean first = true;
			
			for (PropositionalFormula<PSym> propositionalFormula : operands) {
				if(first) {
					builder.append(propositionalFormula.toString());
					continue;
				}
				builder.append(" OR ");
				builder.append(propositionalFormula.toString());
			}
			

			builder.append(")");
			return "(" + this.operands;
		}
	}

	public static class PropositionalNEG<PSym> extends PropositionalFormula<PSym> implements LogicalAND<PropositionalSignature<PSym>> {

		protected PropositionalSignature<PSym> signature;
		protected  PropositionalFormula<PSym> operand;

		/**
		 * Constructs a new instance of this class
		 * 
		 * @author Kai Sauerwald
		 */
		public PropositionalNEG(PropositionalSignature<PSym> signature, PropositionalFormula<PSym> operand) {
			this.signature = signature;
			this.operand = operand;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see edu.cs.ai.alchourron.logic.Formula#getSyntaxTree()
		 */
		@Override
		public SyntacticElement<PropositionalSignature<PSym>> getSyntaxTree() throws UnsupportedOperationException {
			return this;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see edu.cs.ai.alchourron.logic.Formula#getSignature()
		 */
		@Override
		public PropositionalSignature<PSym> getSignature() {
			return signature;
		}

		/*
		 * (non-Javadoc)
		 * @see edu.cs.ai.alchourron.logic.syntax.LogicalOperator#getOperands()
		 */
		@Override
		public List<? extends SyntacticElement<PropositionalSignature<PSym>>> getOperands() {
			return List.of(operand);
		}

		/*
		 * (non-Javadoc)
		 * @see edu.cs.ai.alchourron.logic.syntax.SyntacticElement#isAtom()
		 */
		@Override
		public boolean isAtom() {
			return false;
		}		
		
		/*
		 * (non-Javadoc)
		 * @see java.lang.Object#toString()
		 */
		@Override
		public String toString() {
			return "NOT " + this.operand;
		}
	}
}
