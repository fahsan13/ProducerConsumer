/*
 * Class containing the main method. We initialise a Waiter (process queue)
 * and pass it as a parameter to the Chef (producer) and Customer (consumer).
 */

public class ProducerConsumer {
  
    public static void main(String[] args) {
        Waiter waiter = new Waiter();
        Chef chef = new Chef(waiter);
        Customer customer = new Customer(waiter);
        chef.start();
        customer.start();
    }
}
