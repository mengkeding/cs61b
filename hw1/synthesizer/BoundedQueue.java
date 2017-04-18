
package synthesizer;

import java.util.Iterator;


/**
 * Created by Administrator on 2017/3/28 0028.
 */


public interface BoundedQueue extends Iterable  {
    int capacity();          // return size of the buffer
    int fillCount();         // return number of items currently in the buffer
    void enqueue(double x);  // add item x to the end
    double dequeue();        // delete and return item from the front
    double peek();           // return (but do not delete) item from the front
    default boolean isEmpty(){ // is the buffer empty (fillCount equals zero)?
        return fillCount() == 0;
    }

    default boolean isFull(){// is the buffer full (fillCount is same as capacity)?
        return fillCount() == capacity();
    }




















}
