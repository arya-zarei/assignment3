public class DLinkedNode<T> //DLinkedNode class
{
	private T dataItem; //data item variable stored in the node
	private double priority; //priority of the data item stored in the node
	private DLinkedNode<T> next; //next node in the linked list
	private DLinkedNode<T> prev; //previous node in the linked list
	
	public DLinkedNode(T data, double prio) 
	{ 
		/** constructor initializes objects, originally next and previous are null and data is stored in dataItem
		 and prio is stored in priority**/
		next = null; 
        prev = null;
		dataItem = data;
        priority = prio;
	}
	
	 public DLinkedNode()
	 {
		 //creates empty node with null dataItem and zero as priority
		 dataItem = null;
		 priority = 0;
	 }
	 
	 //setter and getter methods
	 
	 public double getPriority () 
	 {
		 return priority; //returns double variable priority
	 }
	 
	 public T getDataItem()
	 {
		 return dataItem; //returns T variable dataItem 
	 }
	 
	 public DLinkedNode<T> getNext()
	 {
		 return next; //returns next node in the linked list
	 }
	 
	 public DLinkedNode<T> getPrev()
	 {
		 return prev; //returns previous node in the linked list
	 }
	 
	 public void setPriority(double prio)
	 {
		 priority = prio; //sets priority as prio
	 }
	 
	 public void setDataItem(T data)
	 {
		 dataItem = data; //sets dataItem as data
	 }
	 
	 public void setNext(DLinkedNode<T> node)
	 {
		 next = node; //sets next as DlinkedNode linked list node
	 }
	 
	 public void setPrev(DLinkedNode<T> node)
	 {
		 prev = node; //sets previous as DlinkedNode linked list node
	 }
}