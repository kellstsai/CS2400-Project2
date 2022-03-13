 import java.util.EmptyStackException;

/**
 * A class of stacks whose entries are stored in a chain of nodes.
 * 
 * @author Frank M. Carrano and Timothy M. Henry
 * @version 5.0
 */

public final class LinkedStack<T> implements StackInterface<T> {
	private Node topNode; // References the first node in the chain

	public LinkedStack() {
		topNode = null;
	} // end default constructor

//  < Implementations of the stack operations go here. >
//  . . .

	/**
	 * Converts an infix expression to an equivalent postfix expression.
	 * 
	 * @param an infix expression
	 */

	// currently following textbook
	// im using online pdf textbook pg 252
	public static String convertToPostFix(String infix) {
		StackInterface<Character> operatorStack = new LinkedStack<>();
		String postfix = ""; // empty String to hold the postfix expression

		for (char ch : infix.toCharArray()) { // iterate through the infix String
			// System.out.println("ch: " + ch);
			// System.out.println("post: " + postfix);

			char nextChar = ch; // temp variable to hold onto the character

			if (Character.isLetter(nextChar)) // only essentially looking for operators
				postfix += nextChar;
			else if (nextChar == '^') // lowest precedence so will be pushed onto the stack
				operatorStack.push(nextChar);
			else if (nextChar == '+' || nextChar == '-' || nextChar == '*' || nextChar == '/') {
				while (!operatorStack.isEmpty() && (precedence(nextChar) <= precedence(operatorStack.peek()))) {
					// if the stack is not empty and the precedence of the current character is less
					// than the precedence of the character in the stack
					postfix += operatorStack.peek();
					operatorStack.pop();
					// the precedence of the stack cannot be lower than the precedence of the
					// character, hence the pop
				}
				operatorStack.push(ch);
			} else if (nextChar == '(')
				operatorStack.push(ch);
			else if (nextChar == ')') {
				char topOperator = operatorStack.pop();

				while (topOperator != '(') {
					postfix += topOperator;
					topOperator = operatorStack.pop();
					// popping the stack until the left parenthesis is found to complete the set
				}
			}
		}

		// if the stack is not empty, empty the rest of the stack into the postfix
		while (!operatorStack.isEmpty()) {
			postfix += operatorStack.pop();
		}

		return postfix;
	} // end convertToPostFix

	/**
	 * Returns the precedence of the given operator.
	 * 
	 * @param nextChar (operator)
	 * @return nextChar's (operator) precedence
	 */
	private static int precedence(char nextChar) {

		// switch cases to check what the precedence of the operator is
		switch (nextChar) {
		case ('+'):
		case ('-'):
			return 1;
		// + and - are the highest precedence
		case ('*'):
		case ('/'):
			return 2;
		// * and / are the second highest precedence
		}

		return 0;
	} // end precedence

	private class Node {
		private T data; // Entry in stack
		private Node next; // Link to next node

		private Node(T dataPortion) {
			this(dataPortion, null);
		} // end constructor

		private Node(T dataPortion, Node linkPortion) {
			data = dataPortion;
			next = linkPortion;
		} // end constructor

		private T getData() {
			return data;
		} // end getData

		private void setData(T newData) {
			data = newData;
		} // end setData

		private Node getNextNode() {
			return next;
		} // end getNextNode

		private void setNextNode(Node nextNode) {
			next = nextNode;
		} // end setNextNode
	} // end Node

	@Override
	public void push(T newEntry) {
		topNode = new Node(newEntry, topNode);
	}

	@Override
	public T pop() {
		T top = peek();
		// Assertion: topNode != null
		topNode = topNode.getNextNode();
		return top;
	}

	@Override
	public T peek() {
		if (isEmpty())
			throw new EmptyStackException();
		else
			return topNode.getData();
	}

	@Override
	public boolean isEmpty() {
		return topNode == null;
	}

	@Override
	public void clear() {
		topNode = null;
	}

	public static void main(String[] args) {
		String sample = "a*b/(c-a)+d*e";
		System.out.println(convertToPostFix(sample));
	}
} // end LinkedStack
