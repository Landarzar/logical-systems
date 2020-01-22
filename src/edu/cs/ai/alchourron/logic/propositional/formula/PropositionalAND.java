package edu.cs.ai.alchourron.logic.propositional.formula;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import edu.cs.ai.alchourron.logic.propositional.PropositionalFormula;
import edu.cs.ai.alchourron.logic.propositional.PropositionalSignature;
import edu.cs.ai.alchourron.logic.syntax.SyntacticElement;
import edu.cs.ai.alchourron.logic.syntax.formula.LogicalAND;

public class PropositionalAND<PSym> extends PropositionalFormula<PSym>
			implements LogicalAND<PropositionalSignature<PSym>> {

		protected PropositionalSignature<PSym> signature;
		ArrayList<PropositionalFormula<PSym>> operands;

		/**
		 * Constructs a new instance of this class
		 * 
		 * @author Kai Sauerwald
		 */
		public PropositionalAND(PropositionalSignature<PSym> signature,
				Collection<PropositionalFormula<PSym>> operands) {
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
		 * 
		 * @see edu.cs.ai.alchourron.logic.syntax.LogicalOperator#getOperands()
		 */
		@Override
		public List<PropositionalFormula<PSym>> getOperands() {
			return Collections.unmodifiableList(operands);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see edu.cs.ai.alchourron.logic.syntax.SyntacticElement#isAtom()
		 */
		@Override
		public boolean isAtom() {
			return false;
		}
		
		@Override
		public int hashCode() {
			return Objects.hash(operands, signature);
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (!(obj instanceof PropositionalAND))
				return false;
			PropositionalAND other = (PropositionalAND) obj;
			return Objects.equals(operands, other.operands) && Objects.equals(signature, other.signature);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Object#toString()
		 */
		@Override
		public String toString() {
			StringBuilder builder = new StringBuilder();
			builder.append("(");

			boolean first = true;

			for (PropositionalFormula<PSym> propositionalFormula : operands) {
				if (first) {
					builder.append(propositionalFormula.toString());
					continue;
				}
				builder.append(" AND ");
				first = false;
				builder.append(propositionalFormula.toString());
			}

			builder.append(")");
			return builder.toString();
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see edu.cs.ai.alchourron.LaTeX#toLaTeX()
		 */
		@Override
		public String toLaTeX() {
			StringBuilder builder = new StringBuilder();
//			builder.append("(");

			boolean first = true;

			for (PropositionalFormula<PSym> propositionalFormula : operands) {
				if (first) {
					builder.append(propositionalFormula.toLaTeX());
					first = false;
					continue;
				}
//				builder.append(" \\land "); // Short form
				builder.append(propositionalFormula.toLaTeX());
			}

//			builder.append(")");
			return builder.toString();
		}
	}