public class DLPriorityQueue<T> implements PriorityQueueADT<T> //DLPriorityQueue class implements PriorityQueue class
{
	private DLinkedNode<T> front; //front of linked list 
	private DLinkedNode<T> rear; //rear of linked list
	private int count; //number of items in priority queue

	public DLPriorityQueue() //Creates an empty priority queue
	{
		front = null; //front is empty
		rear = null; //rear is empty
		count = 0; // length is zero
	}
	
	public void add (T dataItem, double priority) //Adds to the priority queue the given dataItem with its associated priority
	{
		DLinkedNode<T> ordernode = new DLinkedNode<T>(dataItem, priority); // ordernode is empty priority queue
	    
	    if (count == 0) /** if the linkedlist is empty the front and rear of the linked list is the ordernode **/
	    {
	        front = ordernode;
	        rear = ordernode;
	    }
	    
	    //data items are being added to linked list in non-decreasing order (to find shortest path from start to end)
	    
	    else if (priority > rear.getPriority()) //if priority greater than rear priority sets orderNode to rear (greatest priority at end of list)
	    {
	        rear.setNext(ordernode); //makes orderNode become rear
	        ordernode.setPrev(rear); //rear is now before ordernode
	        rear = ordernode; //ordernode is now rear
	    }
	    
	    else if (priority <= front.getPriority()) //if priority is less or equal to front priority (lowest priority at front of list)
	    {
	        front.setPrev(ordernode); //sets orderNode before front (ordernode is now front)
	        ordernode.setNext(front); //sets front after ordernode
	        front = ordernode; //updates front to orderNode
	    }
	    
	    else // if not front or rear (goes through middle of list)
	    {
	        DLinkedNode<T> current = front; //node current equals front of list
	        
	        while ( (current.getNext() != null) && (priority > current.getNext().getPriority()) ) //goes through entire list
	        {//while next element is not empty and priority is greater than the next priority data item
	            current = current.getNext(); //current is equal to the next node
	        }
	        
	        ordernode.setNext(current.getNext()); //next after ordernode is the next node after current
	        ordernode.setPrev(current); //sets current as order node previous
	        current.getNext().setPrev(ordernode); //current's next item is ordernode's previous item
	        current.setNext(ordernode); //current sets ordernode as the next node
	    }
	    count++; //adds one size to length of list after every item added
	}
	
	public void updatePriority (T dataItem, double newPriority) throws InvalidElementException //Changes the priority of the given dataItem to the new value
	{
		DLinkedNode<T> current = front; //current is front of linked list
	    boolean result = false; //result is a false boolean
	    
	    while (current != null && !result)  //goes through loop until current data item equals data item
	    {   //result will equal true and loop will end or result will not equal true and current will be null and exception will be thrown
	        if (current.getDataItem().equals(dataItem)) 
	        {
	            result = true; //if current dataItem is equal to data item then result turns to true
	        } 
	        else 
	        {
	            current = current.getNext(); // if not go to next current to check for data item
	        }
	    }

	    if (!result) //throw exception if result is false
	    {
	        throw new InvalidElementException("Data item is not found in priority queue!"); //exception is data item was not found in queue
	    }
	     
	    current.setPriority(newPriority); //current priority is updated with new priority
	    
	    //previous of current is not null and previous priority is greater than current priority
	    /** this while loop updates the priority of the linked list making sure the list is in non-decreasing order
	     when current priority is smaller than previous priority**/
	    while ( (current.getPrev() != null) && (current.getPriority() < current.getPrev().getPriority()) )
	    {
	    	DLinkedNode<T> prev = current.getPrev(); //prev node is current's previous
	        DLinkedNode<T> next = current.getNext(); // next node is current's next
	        
	        if (prev.getPrev() != null) //if previous of prev node is not empty
	        {
	            prev.getPrev().setNext(current); //sets previous of prev's next node as current
	        } 
	        
	        else if (prev.getPrev() == null) //if previous of prev node is empty
	        { 
	        	front = current; // if previous of prev is null then the front of the list is current
	        }
	        
	        current.setPrev(prev.getPrev()); //current's previous value is now the previous value of node prev
	        current.setNext(prev); //current's next value is prev
	        prev.setPrev(current); //previous value of prev node is current
	        prev.setNext(next); //after previous is the value of next node
	        
	        if (next != null)  //if next is not empty then next's previous value is prev
	        {
	        	next.setPrev(prev);
	        } 
	        else if (next == null) //if next is empty 
	        {
	            rear = prev; //the rear value becomes the previous node value
	        }
	    }
	    
	    //next of current is not empty and the current priority is larger than its next priority
	    /**this while loop updates the priority of the linked list confirming its non-decreasing order when current priority is larger
	     than the next priority**/
	    while ( (current.getNext() != null) && (current.getPriority() > current.getNext().getPriority()) )
	    {
	        DLinkedNode<T> next2 = current.getNext(); //new next node of current
	        DLinkedNode<T> prev2 = current.getPrev(); //new previous node of current
	        
	        if (next2.getNext() != null) //if the next value after next2 is not empty
	        {
	            next2.getNext().setPrev(current); //current is set to the previous of the value after next2
	        } 
	        
	        else if(next2.getNext() == null) //if the next value after next2 is empty
	        {
	            rear = current; //current is updated to rear as there is no next
	        }
	        
	        current.setPrev(next2); //current's previous value is next2
	        current.setNext(next2.getNext()); //current's next is set to the next value of next2
	        next2.setPrev(prev2); // next2's previous value is set to prev2
	        next2.setNext(current); //next after next2 node is set as current node
	        
	        if (prev2 != null)  //if prev2 value is not null
	        {
	            prev2.setNext(next2); //next after prev2 is next2
	        } 
	        
	        else if (prev2 == null)
	        {
	            front = next2; //if prev2 is null then the front of linked list is equal to next2 node which is the element after prev2
	        }
	    }
	}
	
	public T removeMin() throws EmptyPriorityQueueException //Removes and returns the data item in the priority queue with smallest priority
	{
		 if (isEmpty()) //if linked list is empty than throw exception
		 {
		   throw new EmptyPriorityQueueException("Priority queue is empty!");
		 }

		 T min = front.getDataItem(); //T min is front data item, the minimum item in linked list should be the front from method updatePriority()

		 front = front.getNext(); //front is now equal to the value after front/after min value
		    
		 if (front != null) 
		 {
			 front.setPrev(null); // if front is not null, make previous of front null, which removes min value from list
		 } 
		 
		 count--; //subtract linked list length by one

		 return min; //returns the minimum value
	}
	
	public boolean isEmpty()
	{
		//Returns true if the priority queue is empty
		return count == 0; //if count is zero, length of priority queue list is empty
	}
	
	public int size()
	{
		//Returns the number of data items in the priority queue
		return count; //count is equal to size/length
	}
	
	public String toString() 
	{
		//Returns a String representation of the priority queue
		String priortyString = ""; //priority queue string

	    DLinkedNode<T> current = front; //current node is front
	    
	    while (current != null) //while current value is not null (goes through every element in list)
	    {
	    	priortyString += current.getDataItem().toString(); //adds current data item to list in string format
	        current = current.getNext(); //goes to the next list element to go through the loop again
	    }
	    
	    return priortyString; //returns string of priority queue
	} 
	
	public DLinkedNode<T> getRear()
	{
		//Returns rear
		return rear; //returns rear value of priority queue
	}
}