/*
As your second of two Deque implementations, you'll build the ArrayDeque class. 
This Deque must use arrays as the core data structure. In addition to the methods listed in "The Deque API", 

For this implementation, your operations are subject to the following rules:

add and remove must take constant time, except during resizing operations.
get and size must take constant time.

The amount of memory that your program uses at any given time must be proportional to the number of items. 
For example, if you add 10,000 items to the Deque, and then remove 9,999 items, you shouldn't still be using an array of length 10,000ish. 
For arrays of length 16 or more, your usage factor should always be at least 25%. For smaller arrays, your usage factor can be arbitrarily low.

We strongly recommend that you treat your array as circular for this exercise. In other words, if your front pointer is at position zero, 
and you addFirst, the front pointer should loop back around to the end of the array (so the new front item in the deque will be the last item 
in the underlying array). This will result in far fewer headaches than non-circular approaches. See the project 1 demo slides for more details.

For ArrayDeque, consider not doing resizing at all until you know your code works without it. 
Resizing is a performance optimization (and is required for full credit).

Make sure you think carefully about what happens if the data structure goes from empty, to some non-zero size (e.g. 4 items) back down to zero again, and then back to some non-zero size. This is a common oversight.
Sentinel nodes make life much easier, once you understand them.

Circular data structures make life easier for both implementations (but especially the ArrayDeque).
Consider a helper function to do little tasks like compute array indices. For example, in my implementation of ArrayDeque, 
I wrote a function called int minusOne(int index) that computed the index immediately "before" a given index.
*/

public class ArrayDeque<Item>{
  private Item[] arr;
  private int size = 0;
  private int nextFirst = 0;
  private int nextLast = 1;
  //viewed the source code of java.util.ArrayDeque
  //The starting size of your array should be 8.
  private static final int MIN_INITIAL_CAPACITY = 8;
  


//The signature of the constructor should be public ArrayDeque(). That is, you need only worry about initializing empty ArrayDeques.
//your ArrayDeque class must include a zero argument constructor that creates an empty Deque.

  public ArrayDeque(){
  	arr =  (Item[]) new Object[MIN_INITIAL_CAPACITY];
  }

//Adds an item to the front of the Deque.
//if your front pointer is at position zero, and you addFirst, the front pointer should loop back around 
//to the end of the array (so the new front item in the deque will be the last item in the underlying array). 

  public void addFirst(Item item){
  	if (item == null){
  		 throw new NullPointerException();
  	}       
    arr[nextFirst] = item;
    if(this.nextFirst == 0){
  		nextFirst = this.arr.length - 1;	
  	}else{
  		this.nextFirst -= 1;
    }
    // arr[nextFirst = (nextFirst - 1) & (arr.length - 1)] = item;
    size += 1;
    if(nextFirst == nextLast){
    	resize();
    }
  }


//Adds an item to the back of the Deque.
  public void addLast(Item item){ 
  	if (item == null){
  	  throw new NullPointerException();	
  	}     
    arr[nextLast] = item;
    // if(nextLast == arr.length - 1){
    // 	nextLast = 0;
    // }else{
    // 	nextLast += 1;
    // } 
    nextLast = (nextLast + 1) % (this.arr.length - 1);
    size += 1;
    //copied from source code for ArrayDeque, don't really understand
    if ( (nextLast = (nextLast + 1) & (arr.length - 1)) == nextFirst){
    	resize();
    }
  }


//Returns true if deque is empty, false otherwise.	
  public boolean isEmpty(){
	return size() == 0;
  }

// Returns the number of items in the Deque.
  public int size(){
	return this.size;
  }

//Prints the items in the Deque from first to last, separated by a space.
  public void printDeque(){
    while( nextFirst < this.size){
      System.out.print(this.arr[nextFirst + 1] + " ");
      nextFirst += 1;	
    }
    
  }

//Removes and returns the item at the front of the Deque. If no such item exists, returns null.	
  public Item removeFirst(){
    if(size == 0){
    	return null;
    }
  	size -= 1;
  	if(nextFirst == arr.length - 1){
  		Item item = (Item) arr[0];
  		arr[0] = null;
  		nextFirst = 0;
  		return item;
  	}else{
  		Item item = (Item) arr[nextFirst + 1];
  		arr[nextFirst + 1] = null;
  	    nextFirst += 1;
  	    return item;
  	}
  	// size -= 1;
  	// return item;
  }

//Removes and returns the item at the back of the Deque. If no such item exists, returns null.
  public Item removeLast(){
  	if(size == 0){
  		return null;
  	}
  	size -= 1;
  	if(nextLast == 0){
  		//Item item = (Item) arr[arr.length - 1];
  		Item item = arr[arr.length - 1];
  		arr[arr.length - 1] = null;
  		nextLast = arr.length - 1;
  		return item;
	}else{
		Item item = (Item) arr[nextLast - 1];
		arr[nextLast - 1] = null;
		nextLast -= 1;
		return item;
	}
	// size -= 1;
	// return item;
  }

  public void resize(){
  	//copied from soucecode, don't know assert
  	assert nextFirst == nextLast;
  	int n = arr.length;
  	int p = nextFirst;
  	int r = n - p;
  	int newCapacity = n << 1;
  	if(newCapacity < 0){
  		throw new IllegalStateException("Sorry, deque too big");
  	}
    Item[] a = (Item[]) new Object[newCapacity];
    System.arraycopy(arr, p, a, 0, r);
    System.arraycopy(arr, 0, a, r, p);
    arr = a;
    nextFirst = 0;
    nextLast = n;
  }

//Gets the item at the given index, where 0 is the front, 1 is the next item, and so forth. 
//If no such item exists, returns null. Must not alter the deque!
  public Item get(int index){
    return arr[index];
  }


}