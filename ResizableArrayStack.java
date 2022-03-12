/**
 * A class of stacks whose entries are stored in an array.
 * 
 * @author Frank M. Carrano and Timothy M. Henry
 * @version 5.0
 */
public final class ResizableArrayStack<T> implements StackInterface<T> {
	private T[] stack; // Array of stack entries
	private int topIndex; // Index of top entry
	private boolean integrityOK = false;
	private static final int DEFAULT_CAPACITY = 50;
	private static final int MAX_CAPACITY = 10000;

	public ResizableArrayStack() {
		this(DEFAULT_CAPACITY);
	} // end default constructor

	public ResizableArrayStack(int initialCapacity) {
		integrityOK = false;
		// checkCapacity(initialCapacity);

		// The cast is safe because the new array contains null entries
		@SuppressWarnings("unchecked")
		T[] tempStack = (T[]) new Object[initialCapacity];
		stack = tempStack;
		topIndex = -1;
		integrityOK = true;
	}

	@Override
	public void push(T newEntry) {
		// TODO Auto-generated method stub

	}

	@Override
	public T pop() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public T peek() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub

	} // end constructor

//  < Implementations of the stack operations go here. >
//  < Implementations of the private methods go here; checkCapacity and checkIntegrity
//    are analogous to those in Chapter 2. >
//  . . .
	public int evaluatePostFix(String postfix) {
		ResizableArrayStack valueStack = new ResizableArrayStack();

		for (char ch : postfix.toCharArray()) {
			char nextChar = ch;

			if (Character.isDigit(nextChar))
				valueStack.push(ch);
			else if (nextChar == '+') {
				int operandTwo = valueStack.pop();
				int operandOne = valueStack.pop();
				int result = operandTwo + operandOne;
				valueStack.push((char) result);
			} else if (nextChar == '-') {
				int operandTwo = valueStack.pop();
				int operandOne = valueStack.pop();
				int result = operandTwo - operandOne;
				valueStack.push((char) result);
			} else if (nextChar == '*') {
				int operandTwo = valueStack.pop();
				int operandOne = valueStack.pop();
				int result = operandTwo * operandOne;
				valueStack.push((char) result);
			} else if (nextChar == '/') {
				int operandTwo = valueStack.pop();
				int operandOne = valueStack.pop();
				int result = operandTwo / operandOne;
				valueStack.push((char) result);
			}
		}

		return valueStack.peek();
		// need to return int i just changed it to void cause the error notification was
		// annoying
	}

} // end ArrayStack
