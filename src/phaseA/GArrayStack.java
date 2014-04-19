package phaseA;
import java.util.Arrays;
import java.util.EmptyStackException;

import providedCode.*;

/**
 * @author Reggie Jones
 *
 * CSE 332
 * TA: HyeIn Kim
 * Project 2
 * GArrayStack implements a generic stack ADT using an Array
 * 
 * @param <T> for generics
 */
public class GArrayStack<T> implements GStack<T> {
	private static final int ORIGINAL_SIZE = 10;
	private static final int RESIZE_FACTOR = 2;
	private T[] stack;
	private int stackSize;
	
	/**
	 *  Constructs an empty GArrayStack
	 */
	@SuppressWarnings("unchecked")
	public GArrayStack() {
		stack = (T[]) new Object[ORIGINAL_SIZE];
		stackSize = 0; 
	}
	
	/** 
	 * isEmpty checks to see if the GArrayStack is empty
     * @return a boolean of if the stack is empty or not
     */
    public boolean isEmpty() {
    	return stackSize == 0;
    }

    /**
     * push adds a generic T to the top of the stack
     * @param t a value to be pushed onto the stack
     */
    public void push(T t) {
    	if (stack.length == stackSize) {
    		makeRoom();
    	}
    	stack[stackSize++] = t;
    }
    
    /**
     * pop removes the element off the top of the stack
     * @return the deleted generic T from the top of the stack
     * @throws EmptyStackException if stack is  already empty
     */
    public T pop() {
    	if (isEmpty()) {
    		throw new EmptyStackException();
    	}
    	T top = stack[--stackSize];
    	stack[stackSize] = null;
    	return top;
    }
    
    /**
     * peek looks at the element on top of the stack and returns it
     * @return the generic T on top of the stack
     * @throws EmptyStackException if stack is empty
     */
    public T peek() {
    	if (isEmpty()) {
    		throw new EmptyStackException();
    	}
    	return stack[stackSize - 1];
    }

    /**
     * toString for printing in test function for visual representation of the stack
     * @return String representation of the stack
     */
    public String toString() {
    	return Arrays.toString(stack);
    }
    
    /**
     * makeRoom helper function makes sure there is enough room in array stack
     *  to push another generic
     */
    @SuppressWarnings("unchecked")
	private void makeRoom() {
    	int newLength = stack.length * RESIZE_FACTOR;
    	T[] newStack = (T[]) new Object[newLength];
    	for (int i = 0; i < stack.length; i++) {
    		newStack[i] = stack[i];
    	}
    	stack = newStack;
    }
}

