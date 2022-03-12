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
	 * @param infix
	 */

	// currently following textbook
	// im using online pdf textbook pg 252
	public static String convertToPostFix(String infix) {
		StackInterface<Character> operatorStack = new LinkedStack<>();
		String postfix = "";

		for (char ch : infix.toCharArray()) {
			System.out.println("ch: " + ch);
			System.out.println("post: " + postfix);

			char nextChar = ch;

			if (Character.isLetter(nextChar))
				postfix += nextChar;
			else if (nextChar == '^')
				operatorStack.push(nextChar);
			else if (nextChar == '+' || nextChar == '-' || nextChar == '*' || nextChar == '/') {
				while (!operatorStack.isEmpty() && (precedence(nextChar) <= precedence(operatorStack.peek()))) {
					postfix += operatorStack.peek();
					operatorStack.pop();
				}
				operatorStack.push(ch);
			} else if (nextChar == '(')
				operatorStack.push(ch);
			else if (nextChar == ')') {
				char topOperator = operatorStack.pop();

				while (topOperator != '(') {
					postfix += topOperator;
					topOperator = operatorStack.pop();
				}
			}
		}

		while (!operatorStack.isEmpty()) {
			postfix += operatorStack.pop();
		}

		return postfix;
	}

	/**
	 * Returns the precedence of the given operator.
	 * 
	 * @param nextChar (operator)
	 * @return nextChar's (operator) precedence
	 */
	private static int precedence(char nextChar) {

		switch (nextChar) {
		case ('+'):
		case ('-'):
			return 1;

		case ('*'):
		case ('/'):
			return 2;

		}

		return 0;
	}

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
