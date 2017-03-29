//Make sure this class is public
package synthesizer;



/**
 * GuitarString, which uses an ArrayRingBuffer<Double> to implement
 * the Karplus-Strong algorithm to synthesize a guitar string sound.
 */


public class GuitarString {
    /** Constants. Do not change. In case you're curious, the keyword final means
     * the values cannot be changed at runtime. We'll discuss this and other topics
     * in lecture on Friday. */
    private static final int SR = 44100;      // Sampling Rate
    private static final double DECAY = .996; // energy decay factor

    /* Buffer for storing sound data. */
    private BoundedQueue buffer;




    /* Create a guitar string of the given frequency.  */
    public GuitarString(double frequency) {
        // TODO: Create a buffer with capacity = SR / frequency. You'll need to
        //       cast the result of this divsion operation into an int. For better
        //       accuracy, use the Math.round() function before casting.
        //       Your buffer should be initially filled with zeros.

        int cap = (int) Math.round(SR / frequency);
        buffer = new ArrayRingBuffer(cap);
        if(!buffer.isFull()){
            buffer.enqueue(0.0);
        }
    }



    /* Pluck the guitar string by replacing the buffer with white noise. */
    public void pluck() {
        // TODO: Dequeue everything in the buffer, and replace it with random numbers
        //       between -0.5 and 0.5. You can get such a number by using:
        //       double r = Math.random() - 0.5;
        //
        //       Make sure that your random numbers are different from each other.

        while(!buffer.isEmpty()){
            buffer.dequeue();
        }

        double [] nonDuplicateRandomDouble = new double[buffer.capacity()];
        boolean isDuplicate = false;
        int count = 0;
        while(count < nonDuplicateRandomDouble.length){
            double r = Math.random() - 0.5;
            for(int i = 0; i < nonDuplicateRandomDouble.length; i++){
                if(r == nonDuplicateRandomDouble[i]){
                    isDuplicate = true;
                    break;
                }
            }
            if(!isDuplicate) {
                nonDuplicateRandomDouble[count] = r;
                count += 1;
                buffer.enqueue(r);
            }
        }
    }


    /* Advance the simulation one time step by performing one iteration of
     * the Karplus-Strong algorithm. 
     */
    public void tic() {
        // TODO: Dequeue the front sample and enqueue a new sample that is
        //       the average of the two multiplied by the DECAY factor.
        //       Do not call StdAudio.play().
        double oldFront = buffer.dequeue();
        double doubleToEnqueue =  (buffer.peek() + oldFront) / 2 * DECAY;
        buffer.enqueue(doubleToEnqueue);
    }

    /* Return the double at the front of the buffer. */
    public double sample() {
        // TODO: Return the correct thing.

        return buffer.peek();
    }
}
