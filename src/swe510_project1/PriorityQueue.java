package swe510_project1;


// in the earlier versions, this class is created with Integer and enum "Priority"
// to be able to reuse this class, in this version it is changed to generic and P limited to comparable value by using the interface
// p.s., since there is no need to use import, i hope using Comparable and compareTo is valid in this project.
public class PriorityQueue<C, P extends Comparable<P>> {

	// creating Node class for the linked list elements with next to point to the next item in the list
	public class Node{
		C customer_id;
		P priority;
		Node next;
	
		// instead of CreateNode as in the PS, I added a constructor to this class
		public Node(C customer_id, P priority) {
			this.customer_id = customer_id;
			this.priority = priority;
			this.next = null;
		}
	}
			
	
	// this class is created to be able to return both queue and removed/added node after running the Priority Queue operations.
	public class ReturnedNodesFromPQ { 
		Node head;
		Node otherNode;
		public ReturnedNodesFromPQ(Node head, Node otherNode) {
			this.head = head;
			this.otherNode = otherNode;
		}
	}

	
	// creating priority queue methods (priority queue implementation)
	
	// creating a queue with two parameters that will used to create the head node.
	// since lack of next will result in null next, which will be the first element's expected feature, it is not used in this method.
	public Node initializeQueue(C customer_id, P priority) {
		Node head = new Node(customer_id, priority);
		return head;
	}


	// inserting a node into the queue
	// takes three parameters, the existing queue (the first element), customer_id, and priority and returns both the queue (head) and the inserted node
	public ReturnedNodesFromPQ insertNode(Node root, C customer_id, P priority) {
		Node head = root; // the first element of the queue
		Node newNode = new Node (customer_id, priority); // the element to be inserted to the queue
		
		// below, ordinal() was used for enum comparisons in the earlier versions. yet, to be able to make it generic, it is needed to use compareTo.
		if (head == null) { // if the queue is null, it will initialize a queue. it can happen when the last element is removed and a new one is tried to be inserted.
			head = initializeQueue(customer_id, priority);
		}
		else if (priority.compareTo(head.priority)<0) { // if the new element has has higher priority (less than) than the head, it points to the head of the queue and becomes the head.
			newNode.next = head;
			head = newNode;
		}
		else { // if the new element has less priority (more than) than the head, it moves through the queue element by element as long as it has 
			// less priority - > so, it should be after that element
			// the next item is not null - > so, it is not the last element. since we add it to the right of the point (root) in this loop.
			// and the next item is not of less or equal priority -> so, it goes last when there are elements with equal priority (for FIFO).
			while(root.priority.compareTo(priority) <= 0 && root.next != null && root.next.priority.compareTo(priority) <= 0) {
				root = root.next;
			}
			newNode.next = root.next; // the new one is added in-between.
			root.next = newNode;
		}
		return new ReturnedNodesFromPQ(head, newNode);
	}
	
	
	// removing a node from the queue
	// takes one parameter, the existing queue (the first element), as the element with highest priority and fits FIFO will be removed and returns both the queue (head) and the removed node
	public ReturnedNodesFromPQ removeNode (Node root) {
		Node head = root;
		if (head == null) { // to avoid problems with null queues.
			return new ReturnedNodesFromPQ(null, null);
		}
		Node ggNode = head;
		head = head.next; // head becomes the element it initially points. if head.next is null, then it will empty the queue
		return new ReturnedNodesFromPQ(head, ggNode);
	}
	
	
	// printing the queue
	// takes one parameter, the existing queue (the first element) and does not return anything but print during the run.
	public void printQueue (Node root) {
		if (root == null) { // if the queue is empty
			System.out.println("Good work! There is no waiting customer.");
		}
		else { // if the queue is not empty
			System.out.println("The customers waiting in the queue:");
			while (root != null) { // goes element by element to the last element
				System.out.println("Priority is: "+ root.priority +", CustomerID is: "+root.customer_id);
				root=root.next;
			}
		}
	}
			

}
