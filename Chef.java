/*
 * This class is the Producer within the system. The Chef will repeatedly call
 * the Waiter's takeDishFromChef() method for as long as it has dishes to give.
 */

public class Chef extends Thread {

    private String[] dish = {"starter", "main", "dessert", "coffee"};
    private Waiter waiter;

    public Chef(Waiter waiter) {
        this.waiter = waiter;
    }

    @Override
    public void run() {
      for (int i = 0; i < dish.length; i++) {
        waiter.takeDishFromChef(dish[i]);
      }
    }
}
