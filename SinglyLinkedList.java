import java.util.HashSet;

/*A singly linked list which holds integer data .
 * 
 * Date 21/4/2018
 * 
 * @author:Debapriya Biswas
 * 
 * 
 **/

public class SinglyLinkedList {

	SinglyLinkedListNode head;
	SinglyLinkedListNode tail;

	class SinglyLinkedListNode {
		int data;
		SinglyLinkedListNode next;

		public SinglyLinkedListNode(int data) {
			this.data = data;
			this.next = null;

		}

	}

	SinglyLinkedList() {
		this.head = this.tail = null;
	}

	/*
	 * Inserts a node at the end of the list. If the list a empty this node
	 * becomes the head
	 * 
	 */
	public void insertNode(int data) {
		SinglyLinkedListNode node = new SinglyLinkedListNode(data);
		if (head == null)
			head = node;
		else
			tail.next = node;
		tail = node;

	}

	/*
	 * Reverses a linked list . the size and the number of nodes in the list
	 * remains unchanged
	 */
	public SinglyLinkedListNode reverse() {
		SinglyLinkedListNode prev = null;
		SinglyLinkedListNode current = head;
		SinglyLinkedListNode next;
		while (current != null) {
			next = current.next;
			current.next = prev;
			prev = current;
			current = next;
		}
		return prev;
	}

	/*
	 * Sorts a Linked List into batches of k Returns head of the result list
	 * 
	 */
	public SinglyLinkedListNode kSort(int k) {
		return kSort(k, head);
	}

	private SinglyLinkedListNode kSort(int k, SinglyLinkedListNode list) {
		SinglyLinkedListNode pointer = list;
		SinglyLinkedListNode nextPointer = list;
		SinglyLinkedList result = new SinglyLinkedList();
		while (nextPointer != null) {
			pointer = list;
			for (int i = 1; i < k; i++) {
				pointer = pointer.next;
			}
			if (pointer == null)
				nextPointer = null;
			else {
				nextPointer = pointer.next;
				pointer.next = null;
			}
			if (result.head == null)
				result.head = sort(list);
			else {
				SinglyLinkedListNode track = result.head;
				while (track.next != null)
					track = track.next;

				track.next = sort(list);
			}
			list = nextPointer;
		}
		return result.head;
	}

	/* Sorts a linked list */
	public SinglyLinkedListNode sort(SinglyLinkedListNode list) {
		return mergeSort(list);
	}

	private SinglyLinkedListNode mergeSort(SinglyLinkedListNode list) {
		if (list == null || list.next == null)
			return list;
		SinglyLinkedListNode middle = getMiddle(list);
		SinglyLinkedListNode midNext = middle.next;
		middle.next = null;
		SinglyLinkedListNode left = mergeSort(list);
		SinglyLinkedListNode right = mergeSort(midNext);
		SinglyLinkedListNode result = merge(left, right);
		return result;
	}

	/*
	 * Gets the middle of the linked list if the number of nodes in the linked
	 * list is even then the first out of the two possibilities is returned , In
	 * case of odd middle element is trivial
	 * 
	 */
	public SinglyLinkedListNode getMiddle(SinglyLinkedListNode list) {
		if (list == null)
			return null;
		SinglyLinkedListNode fastPtr = list.next;
		SinglyLinkedListNode slowPtr = list;
		while (fastPtr != null) {
			fastPtr = fastPtr.next;
			if (fastPtr != null) {

				slowPtr = slowPtr.next;
				fastPtr = fastPtr.next;
			}
		}

		return slowPtr;
	}

	private SinglyLinkedListNode merge(SinglyLinkedListNode a, SinglyLinkedListNode b) {

		SinglyLinkedListNode result = null;

		if (a == null)
			return b;
		if (b == null)
			return a;
		if (a.data <= b.data) {
			result = a;
			result.next = merge(a.next, b);
		} else {

			result = b;
			result.next = merge(a, b.next);

		}

		return result;
	}

	/* Removes Duplicate elements in the linked list */
	public void removeDuplicates() {

		this.head = sort(this.head);
		SinglyLinkedListNode e = head;
		while (e.next != null) {

			if (e.data == e.next.data)
				e.next = e.next.next;
			else
				e = e.next;
		}

	}

	private void addOneUtil() {
		SinglyLinkedListNode next = head;
		SinglyLinkedListNode temp = null;
		int carry = 1;
		int sum = 0;

		while (next != null) {
			sum = carry + next.data;
			carry = sum >= 10 ? 1 : 0;
			sum = sum % 10;
			next.data = sum;
			temp = next;
			next = next.next;
		}
		if (carry >= 1)
			temp.next = new SinglyLinkedListNode(carry);

	}

	/* Adds 1 to a number where each digits is a node is a linked list */
	public void addOne(SinglyLinkedListNode node) {
		head = reverse();
		addOneUtil();
		head = reverse();

	}

	public String toString() {
		SinglyLinkedListNode pointer = this.head;
		StringBuilder sb = new StringBuilder();
		while (pointer != null) {
			sb.append(pointer.data).append("->");
			pointer = pointer.next;
		}
		return sb.toString();

	}
	/* Detects loop in a linked list */

	public boolean detectLoop(SinglyLinkedListNode h) {
		/*
		 * works only if equals() and hashCode() in SinglyLinkedListNode is not
		 * overidden
		 */
		HashSet<SinglyLinkedListNode> s = new HashSet<SinglyLinkedListNode>();
		while (h != null) {
			if (s.contains(h))
				return true;
			s.add(h);

			h = h.next;
		}
		return false;
	}
}
