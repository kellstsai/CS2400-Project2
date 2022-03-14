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
		checkCapacity(initialCapacity);

		// The cast is safe because the new array contains null entries
		@SuppressWarnings("unchecked")
		T[] tempStack = (T[]) new Object[initialCapacity];
		stack = tempStack;
		topIndex = -1;
		integrityOK = true;
	}

	@Override
	public void push(T newEntry) {
		checkIntegrity();
		ensureCapacity();
		stack[topIndex + 1] = newEntry;
		topIndex++;

	}

	@Override
	public T pop() {
		checkIntegrity();
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
		checkIntegrity();
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
		checkIntegrity();
		while (topIndex > -1) {
			stack[topIndex] = null;
			topIndex--;
		}

	} // end constructor

	private void ensureCapacity() {
		if (topIndex >= stack.length - 1) {
			int newLength = 2 * stack.length; // can be 1.5 or golden ratio, 2 is just for convenience
			checkCapacity(newLength);
			stack = Arrays.copyOf(stack, newLength);
		}
	}

	// Throws an exception if the client requests a capacity that is too large.
	private void checkCapacity(int capacity) {
		if (capacity > MAX_CAPACITY)
			throw new IllegalStateException(
					"Attempt to create a bag whose capacity exceeds " + "allowed maximum of " + MAX_CAPACITY);
	} // end checkCapacity

	// Throws an exception if receiving object is not initialized.
	private void checkIntegrity() {
		if (!integrityOK)
			throw new SecurityException("ArrayBag object is corrupt.");
	} // end checkIntegrity

//  < Implementations of the stack operations go here. >
//  < Implementations of the private methods go here; checkCapacity and checkIntegrity
//    are analogous to those in Chapter 2. >
//  . . .
	public static int evaluatePostFix(String postfix) {
		ResizableArrayStack<Character> valueStack = new ResizableArrayStack<>();

		for (char ch : postfix.toCharArray()) { // iterate through each character in the String
			char nextChar = ch;
			System.out.println("ch: " + ch);

			if (Character.isDigit(nextChar)) { // currently push only numbers
				valueStack.push(ch);
				System.out.println("peek:" + valueStack.peek());
			} else if (nextChar == '+' || nextChar == '-' || nextChar == '*' || nextChar == '/') {
				int operandTwo = valueStack.pop();
				int operandOne = valueStack.pop(); // pop the top 2 items and do the necessary calculation
				int result = calc(ch, operandOne, operandTwo);

				System.out.println("result again: " + result);
				valueStack.push((char) (result + '0')); // converting int to char by adding 48 which is char 0
			}

			System.out.println("regular end peek: " + valueStack.peek());
			System.out.println("end peek: ");
			System.out.println(valueStack.peek() - '0');
			System.out.println("print: ");
			print(valueStack);
		}

		System.out.println("\nfinal answer: ");
		return valueStack.peek() - '0';
	} // end evaluatePostFix

	/**
	 * @param character that contains the operator
	 * @param integer   two
	 * @param integer   one
	 * @return The calculation between integer one and two.
	 */
	public static int calc(char ch, int two, int one) {
		int result = 0;
		two -= '0'; // converting from character into integer by subtracting 48
		one -= '0';

		System.out.println("\nchar: " + ch + " two: " + two + " one: " + one);
		switch (ch) {
		case '+':
			result = two + one;
			break;
		case '-':
			result = two - one;
			break;
		case '*':
			result = two * one;
			break;
		case '/':
			result = two / one;
			break;
		} // end switch case

		System.out.println("\ncalc\nresult: " + result + "\n");
		return result;
	} // end calc

	/**
	 * Prints all the elements in a stack.
	 * 
	 * @param a stack of characters
	 */
	public static void print(ResizableArrayStack<Character> c) {
		if (c.isEmpty())
			return; // stop when the stack is empty

		char temp = c.peek(); // temp is the top most value
		c.pop(); // pop the top
		System.out.println(temp); // print the top
		print(c); // print the rest of the stack
		c.push(temp); // when returning from recursive loop, push each temp back
	}

	public static void main(String[] args) {
		// String sample = "23*42-/56*+";
		// String sample = "26*35-/";

		// V this one don't work for some reason (ans -58)
		String sample = "234*5*-";

		// String sample = "234-/5*";
		// String sample = "6342^*+5-";
		System.out.println(evaluatePostFix(sample));
	}

} // end ArrayStack
