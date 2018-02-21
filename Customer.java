/*
 * This class is the Consumer within the system and runs on its own thread.
 * The Customer object stores a reference to the Waiter (queue) and will
 * continually call the Waiter's giveDishToCustomer() method 
 */

public class Customer extends Thread {

    private String currentCourse;
    private Waiter waiter;

    public Customer(Waiter waiter) {
        this.waiter = waiter;
    }

    @Override
    public void run() {
        while (true) {
            currentCourse = waiter.giveDishToCustomer();
            System.out.println("Customer has eaten dish: " + currentCourse);
        }
    }
}
