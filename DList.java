
	
// DList1 is mutable, doubly-linked list (No sentinel, no circularly linked)
public class DList {
 
    protected DListNode head;
    protected DListNode tail;
    protected int size;
     
    // construct for an empty DList1
    public DList () {
        head = null;
        tail = null;
        size = 0;
    }
     
    //construct for a one-node DList1
    public DList (String a){
        head = new DListNode();
        tail = head;
        head.item = a;
        size = 1;
    }
     
    //construct a two_nodes DList1
    public DList (String a, String b) {
        head = new DListNode();
        head.item = a;
        tail = new DListNode();
        tail.item = b;
        head.next = tail;
        tail.prev = head;
        size = 2;
    }
     
    public void insertFront (String i) {
        if (this.size == 0){
            head = new DListNode (i);
            tail = head;
        } else if (this.size == 1) {
            head = new DListNode (i);
            head.next = tail;
            tail.prev = head;
        } else {
            head = new DListNode (i);
             
        }
        size++;
    }
     
    public void removeFront () {
        if (this.size > 1){
            head.next.prev = null;
            head = head.next;
            size--;
        } else if (this.size == 1) {
            head = null;
            tail = null;
            size = 0;
        } else {
            System.out.println("ERROR! Can not remove elements on Empty list.");
        }
    }
     
    public String toString () {
        String result = "[ ";
        DListNode current = head;
        while (current != null) {
            result = result + current.item + " ";
            current = current.next;
        }
        return result + "]";
    }
}
   