import java.util.Arrays;
import java.util.EmptyStackException;

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
		// checkIntegrity();
		ensureCapacity();
		stack[topIndex + 1] = newEntry;

	}

	@Override
	public T pop() {
		// checkIntegrity();
		if (isEmpty())
			throw new EmptyStackException();
		else {
			T top = stack[topIndex];
			stack[topIndex] = null;
			topIndex--;
			return top;
		}
	}

	@Override
	public T peek() {
		// cehckIntegrity();
		if (isEmpty())
			throw new EmptyStackException();
		else
			return stack[topIndex];
	}

	@Override
	public boolean isEmpty() {
		return topIndex < 0;
	}

	@Override
	public void clear() {
		// checkIntegrity();
		while (topIndex > -1) {
			stack[topIndex] = null;
			topIndex--;
		}

	} // end constructor

	private void ensureCapacity() {
		if (topIndex >= stack.length - 1) {
			int newLength = 2 * stack.length; // can be 1.5 or golden ratio, 2 is just for convenience
			// checkCapacity(newLength);
			stack = Arrays.copyOf(stack, newLength);
		}
	}

//  < Implementations of the stack operations go here. >
//  < Implementations of the private methods go here; checkCapacity and checkIntegrity
//    are analogous to those in Chapter 2. >
//  . . .
	public static int evaluatePostFix(String postfix) {
		ResizableArrayStack<Character> valueStack = new ResizableArrayStack<>();

		for (char ch : postfix.toCharArray()) {
			char nextChar = ch;

			if (Character.isDigit(nextChar))
				valueStack.push(ch);
			else if (nextChar == '+' || nextChar == '-' || nextChar == '*' || nextChar == '/') {
				int operandTwo = valueStack.pop();
				int operandOne = valueStack.pop();
				int result = calc(ch, operandOne, operandTwo);
				valueStack.push((char) result);
			}
		}

		return valueStack.peek();

		// need to return int i just changed it to void cause the error notification was
		// annoying
	}

	public static int calc(char ch, int two, int one) {
		int result = 0;

		switch (ch) {
		case '+':
			result = one + two;
			break;
		case '-':
			result = one - two;
			break;
		case '*':
			result = one * two;
			break;
		case '/':
			result = one / two;
			break;
		}
		return result;
	}

	public static void main(String[] args) {
		String sample = "23_45-/";
		System.out.println(evaluatePostFix(sample));
	}

} // end ArrayStack
