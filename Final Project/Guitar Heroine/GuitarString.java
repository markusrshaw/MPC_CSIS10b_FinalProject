/*************************************************************************
 * Name(s)      : 
 * NetID(s)     : 
 * Precept(s)   :
 *
 * Dependencies :
 * Description  : 
 *  
 *  This is a template file for GuitarString.java. It lists the constructors
 *  and methods you need, along with descriptions of what they're supposed
 *  to do.
 *  
 *  Note: it won't compile until you fill in the constructors and methods
 *        (or at least commment out the ones whose return type is non-void).
 *
 *****************************************************************************/

public class GuitarString {

    private RingBuffer buffer; // ring buffer
    // YOUR OTHER INSTANCE VARIABLES HERE
    private int N; 
    private int numTic = 0;
    // create guitar string of given frequency, using sampling rate of 44,100
    public GuitarString(double frequency) {
        N = (int)Math.round(44100/frequency);
        buffer = new RingBuffer(N);
        for (int k = 0; k < N; k++){
            buffer.enqueue(0);
        }
    }

    // create a guitar string with size & initial values given by the array
    public GuitarString(double[] init) {
        buffer = new RingBuffer(init.length);
        for(int k = 0; k < init.length; k++){
            buffer.enqueue(init[k]);
        }
    }

    // pluck the guitar string by replacing the buffer with white noise
    public void pluck() {
        for (int k = 0; k < buffer.size(); k++){
        double r = (Math.random() - 0.5);
        buffer.enqueue(r);
       }
    }

    // advance the simulation one time step
    public void tic() {
        double temp = ((buffer.dequeue()+buffer.peek())/2)*0.994;
        buffer.enqueue(temp);
        numTic++;
    }

    // return the current sample
    public double sample() {
       return buffer.peek();
    }

    // return number of times tic was called
    public int time() {
        return numTic;
    }

    public static void main(String[] args) {
        int N = 25;//Integer.parseInt(args[0]);
        double[] samples = { .2, .4, .5, .3, -.2, .4, .3, .0, -.1, -.3 };  
        GuitarString testString = new GuitarString(samples);
        for (int i = 0; i < N; i++) {
            int t = testString.time();
            double sample = testString.sample();
            System.out.printf("%6d %8.4f\n", t, sample);
            testString.tic();
        }
    }

}