import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

// Antonio Peralta
// CMP 426
// Project #4

// each producer has its own queue, but the consumers can consume from either queue
public class ProducerConsumer {
	
	// variables
    private Queue<LocalDateTime> queue = new LinkedList<LocalDateTime>(); // create queue
    private Queue<LocalDateTime> queue2 = new LinkedList<LocalDateTime>();
    private int max;
    private static int numProducers;
    private static int numConsumers;
    private int numProduceItems;
    
    // Constructor
    public ProducerConsumer(int max, int numProducers, int numConsumers, int numProduceItems) {
    	this.max = max;
    	ProducerConsumer.numProducers = numProducers; 
    	ProducerConsumer.numConsumers = numConsumers; 
    	this.numProduceItems = numProduceItems;
    	queue = new LinkedBlockingQueue<LocalDateTime>(max);
    	queue2 = new LinkedBlockingQueue<LocalDateTime>(max);
    }
    // method to check if queue is empty
    private boolean isEmpty(){
        return queue.size() == 0;
    }
    // method to check if queue is full
    private boolean isFull(){
        return queue.size() == max;
    }
    // method to check if queue is empty
    private boolean isEmpty2(){
        return queue2.size() == 0;
    }
    // method to check if queue is full
    private boolean isFull2(){
        return queue2.size() == max;
    }
    
    // synchronized method to produce time stamps
    public synchronized void produceItem(LocalDateTime timestamp, String producerName){
    	// while queue is full wait
    	while(isFull()){
            try {
               wait();
            } catch (InterruptedException e) {
               e.printStackTrace();
            }
        }
    	// the number of messages that each producer should produce
    	queue.add(timestamp); // add number of time stamp to queue
    	// output producer message
        System.out.println(producerName + " produced time stamp " + timestamp + ". The queue has " + queue.size() + " elements");
        notifyAll();
	}
	// synchronized method to consume time stamps from queue
	public synchronized LocalDateTime consumeItem(String consumerName){
		
		// while queue is empty wait
		while(isEmpty()){
            try {
               wait();
            } catch (InterruptedException e) {
               e.printStackTrace();
            }
         }
		 LocalDateTime message = queue.peek();
		 queue.remove(); // remove time stamp from queue
		 System.out.println(consumerName + " got message " + message + ". The queue has " + queue.size() + " elements");
		 
		 
		 if (queue.size() > queue2.size()){
			// while queue is empty wait
			 while(isEmpty2()){
				 try {
					 wait();
		         } catch (InterruptedException e) {
		        	 e.printStackTrace();
		         }
		     }
		     LocalDateTime message2 = queue2.peek();
		     queue2.remove(); // remove time stamp from queue
		     
		     return message2;
		 }
		 notifyAll();
		 return message; // return time stamp that is removed  
	}
    
    
    // method to run tests of the ProducerConsumer class constructor
 	public void runSimulation(){  
 		
 		if ((numProducers == 1) && (numConsumers == 2)){ // 1 producer 2 consumer
 			Producer p1 = new Producer("P1");
 			Consumer c1 = new Consumer("C1");
 		    Consumer c2 = new Consumer("C2");
 		    p1.start();
 		    c1.start();
 		    c2.start();
 	
 		}
 		else if ((numProducers == 1) && (numConsumers == 0)){ // 1 producer
 			Producer p1 = new Producer("P1");
 			p1.start();
 		}
 		
 		else if((numProducers == 1) && (numConsumers == 1)){ // 1 producer and 1 consumer
 			
 			Producer p1 = new Producer("P1");
 		    Consumer c1 = new Consumer("C1");
 		    p1.start();
 		    c1.start();  
 		}
 		else if ((numProducers == 2) && (numConsumers == 2)){ // 2 producer and 2 consumer
 			Producer p1 = new Producer("P1");
 			Producer p2 = new Producer("P2");
 			Consumer c1 = new Consumer("C1");
 			Consumer c2 = new Consumer("C2");
 			p1.start();
 			p2.start();
 			c1.start();
 			c2.start();
 			
 		}
 		else if ((numProducers == 2) && (numConsumers == 1)){ // 2 producers 1 consumer
 			Producer p1 = new Producer("P1");
 			Producer p2 = new Producer("P2");
 			Consumer c1 = new Consumer("C1");
 			p1.start();
 		    p2.start();
 		    c1.start();
 		}
 		
 	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ProducerConsumer buffer = new ProducerConsumer(10,2,2,10);
		buffer.runSimulation(); 
	 
	}
	
	// producer class thread
	class Producer extends Thread{
		
		private LocalDateTime timestamp = null; // initialize time stamp
		// constructor
		public Producer(String threadName){
			setName(threadName); // set producer thread name to be accessed in buffer class
		}
		
		@Override
		public void run() {
			long start = System.nanoTime();
			for (int i =0; i < numProduceItems; i++){
				  
				try {
					Thread.sleep(100); // sleep thread by given parameter from buffer constructor
		    	} catch (InterruptedException e) {
		    		e.printStackTrace();
		    	}
				timestamp = LocalDateTime.now(); // set time stamp
		        produceItem(timestamp, getName()); // call method produceItem 
		        
		    } 
			long elapsedTime = System.nanoTime() - start;
			System.out.println("Average Time of items produced: " + getAvgTime(elapsedTime)); // print average for producer
			
		}
		
    }
    // consumer class thread
    class Consumer extends Thread{
    	// constructor
    	public Consumer(String threadName){
    		setName(threadName); // set consumer thread name to be accessed in buffer class
    	}
    	@Override
    	public void run() {
    	    long start = System.nanoTime();
    	    for (int i =0; i < numProduceItems; i++){
    			
    			try {
    				Thread.sleep(100); // sleep thread by given parameter from buffer constructor
    			} catch (InterruptedException e) {
    				e.printStackTrace();
    			}
    			consumeItem(getName()); // call method produceItem
    		}
    		long elapsedTime = System.nanoTime() - start;
			System.out.println("Average Time consumer waits: " + getAvgTime(elapsedTime)); // print average for consumer
			
    	}
	}
    // getAvgTime method for comparison
    public long getAvgTime(long elapsedTime){
 		long avgTime = elapsedTime / numProduceItems;
    	return avgTime;
    }

}
