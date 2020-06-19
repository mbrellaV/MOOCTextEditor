package textgen;

import java.util.AbstractList;


/** A class that implements a doubly linked list
 * 
 * @author UC San Diego Intermediate Programming MOOC team
 *
 * @param <E> The type of the elements stored in the list
 */
public class MyLinkedList<E> extends AbstractList<E> {
	LLNode<E> head;
	LLNode<E> tail;
	int size;
	int start_i = -1;

	/** Create a new empty LinkedList */
	public MyLinkedList() {
		this.head = new LLNode<E>();
		this.tail = new LLNode<E>();
		this.head.setNext(this.tail);
		this.tail.setPrev(this.head);
		this.size = 0;
	}

	/**
	 * Appends an element to the end of the list
	 * @param element The element to add
	 */
	public boolean add(E element) throws NullPointerException
	{
		LLNode<E> tmp;
		LLNode<E> newNode;

		if (element == null)
			throw new NullPointerException();
		tmp = this.head;
		while (tmp.getNext().getNext() != null)
		{
			tmp = tmp.getNext();
		}
		newNode = new LLNode<E>();
		newNode.setPrev(tmp);
		newNode.setNext(tmp.getNext());
		tmp.setNext(newNode);
		newNode.getNext().setPrev(newNode);
		newNode.setData(element);
		this.size++;
		return (true);
	}

	/** Get the element at position index 
	 * @throws IndexOutOfBoundsException if the index is out of bounds. */
	public E get(int index) throws IndexOutOfBoundsException
	{
		LLNode<E> tmp;
		int i = start_i;

		tmp = this.head;
		if (index < 0 || index > this.size - 1 || this.size == 0)
			throw new IndexOutOfBoundsException();
		while (tmp.getNext().getNext() != null && i != index) {
			tmp = tmp.getNext();
			i++;
		}
		return (tmp.getData());
	}

	/**
	 * Add an element to the list at the specified index
	 * @param  index where the element should be added
	 * @param element The element to add
	 */
	public void add(int index, E element) throws IndexOutOfBoundsException, NullPointerException
	{
		LLNode<E> tmp;
		LLNode<E> newNode;
		int i = start_i;

		if (element == null)
			throw new NullPointerException();
		tmp = this.head;
		if (index < 0 || index > this.size)
			throw new IndexOutOfBoundsException();
		while (tmp.getNext().getNext() != null && i != (index - 1)) {
			tmp = tmp.getNext();
			i++;
		}
		newNode = new LLNode<E>();
		newNode.setPrev(tmp);
		newNode.setNext(tmp.getNext());
		tmp.setNext(newNode);
		newNode.getNext().setPrev(newNode);
		newNode.setData(element);
		this.size++;
	}


	/** Return the size of the list */
	public int size() 
	{
		return this.size;
	}

	/** Remove a node at the specified index and return its data element.
	 * @param index The index of the element to remove
	 * @return The data element removed
	 * @throws IndexOutOfBoundsException If index is outside the bounds of the list
	 * 
	 */
	public E remove(int index) throws IndexOutOfBoundsException
	{
		LLNode<E> dop;
		LLNode<E> tmp;
		int i = start_i;

		tmp = this.head;
		if (index < 0 || (index > (this.size - 1)) || this.size == 0)
			throw new IndexOutOfBoundsException();
		while (tmp.getNext().getNext() != null && i != index) {
			tmp = tmp.getNext();
			i++;
		}
		dop = tmp;
		dop.getPrev().setNext(dop.getNext());
		dop.getNext().setPrev(dop.getPrev());
		this.size--;
		return (dop.data);
	}

	/**
	 * Set an index position in the list to a new element
	 * @param index The index of the element to change
	 * @param element The new element
	 * @return The element that was replaced
	 * @throws IndexOutOfBoundsException if the index is out of bounds.
	 */
	public E set(int index, E element) throws IndexOutOfBoundsException, NullPointerException
	{
		LLNode<E>	tmp;
		E			dop;
		int i = start_i;

		if (element == null)
			throw new NullPointerException();
		tmp = this.head;
		if (index < 0 || index > this.size - 1)
			throw new IndexOutOfBoundsException();
		while (tmp.getNext().getNext() != null && i != index) {
			tmp = tmp.getNext();
			i++;
		}
		dop = tmp.getData();
		tmp.setData(element);
		return (dop);
	}   
}

class LLNode<E> 
{
	LLNode<E> prev;
	LLNode<E> next;
	E data;

	// TODO: Add any other methods you think are useful here
	// E.g. you might want to add another constructor

	public LLNode()
	{
		this.data = null;
		this.prev = null;
		this.next = null;
	}

	public LLNode(E e) 
	{
		this.data = e;
		this.prev = null;
		this.next = null;
	}

	public void setNext(LLNode<E> next) {
		this.next = next;
	}

	public void setPrev(LLNode<E> prev) {
		this.prev = prev;
	}

	public void setData(E data) {
		this.data = data;
	}

	public LLNode<E>getNext()
	{
		return (this.next);
	}

	public LLNode<E> getPrev() {
		return (this.prev);
	}

	public E getData() {
		return (this.data);
	}
}
