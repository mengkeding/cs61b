public class LinkedListDeque<Item>{
 
  private class Node{
    private Item item;
    private Node next, prev;

    public Node(Item i, Node n, Node p){
        this.item = i;
        this.next = n;
        this.prev = p;
    }
  }

  private int size = 0;
  private Node sentinel, last;
  
  //Creates an empty linked list deque.
  public LinkedListDeque(){
    this.sentinel = new Node(null, null, null);
    this.sentinel.prev = this.sentinel;
    this.sentinel.next = this.sentinel;


  } 


  //Adds an item to the front of the Deque.
  public void addFirst(Item item){
    Node newNode = new Node(item, this.sentinel.next, this.sentinel);
    this.sentinel.next.prev = newNode;
    this.sentinel.next = newNode;
    size += 1;
  } 



  //Adds an item to the back of the Deque.
  //add and remove operations must not involve any looping or recursion. 
  //A single such operation must take "constant time", 
  //i.e. execution time should not depend on the size of the Deque.
  public void addLast(Item item) {
    size += 1;
    Node newNode = new Node(item, this.sentinel, this.sentinel.prev);
    sentinel.prev.next = newNode;
    sentinel.prev = newNode;
  }


  //Returns true if deque is empty, false otherwise.
  public boolean isEmpty(){
    return size == 0;

  } 

  //Returns the number of items in the Deque.
  public int size(){
    return size;

  } 

  //Prints the items in the Deque from first to last, separated by a space.
  public void printDeque(){
    Node p = this.sentinel.next;
    while(true){
        System.out.print(p.item + " ");
        if(p == sentinel.prev) {
            break;
        }
        p = p.next;
    }
  }


  //Removes and returns the item at the front of the Deque. If no such item exists, returns null.
  public Item removeFirst(){
    if(sentinel.next == null){
        return null;
    }
    
    Item item = (Item) sentinel.next.item;
    sentinel.next = sentinel.next.next;
    size -= 1;
    return item;
  }

  //Removes and returns the item at the back of the Deque. If no such item exists, returns null.
  public Item removeLast(){
    Item item = (Item)last.item;
    last = last.prev;
    last.next = sentinel;
    size -= 1;
    return item;
  }


  // Gets the item at the given index, where 0 is the front, 1 is the next item, and so forth. 
  // If no such item exists, returns null. Must not alter the deque!
   public Item get(int index){
     if(this.size() == 0){
      return null;
      }else{
       Node p = this.sentinel.next;
       while(index != 0 ){
         if(p.next == this.sentinel){
           return null;
         }

         p = p.next;
         index -= 1;
       }

       return p.item;
     }
   }
   

  //Same as get, but uses recursion.
 
  // how to write a recursion
  //1. Base case
  //2. Get closer to the base case(s)
  //3. How does the previous call help us determine the answer


   
   public Item getRecursive(int index){
     if (index == 0){
         return this.sentinel.next.item;
     }
         Node p = this.sentinel.next;
         return getHelper(index,p);
     }
   

   private Item getHelper(int index, Node p){
     if(index == 0){
       return p.item;
     }else{
         p = p.next;
         return this.getHelper(index - 1, p);
     }
   
  }
 }