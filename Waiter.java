  /*
  * This class is the process queue within the system. It uses a ReentrantLock
  * with a Condition to control allocation of dishes to/from the
  * Consumer/Producer and avoid deadlocks.
  */

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Waiter {

  private String currentDish;
  private ReentrantLock lock = new ReentrantLock();
  private Condition emptyTray = lock.newCondition();
  private boolean hasDish = false;

  public boolean hasDish() {
      return hasDish;
  }

  public void takeDishFromChef(String dish) {

    lock.lock();

    try {
      while (hasDish) {
        emptyTray.await();
      }
      currentDish = dish;
      hasDish = true;
      emptyTray.signalAll();
      System.out.println("Waiter gets " + currentDish + " from chef");
    } catch (InterruptedException e) {
    } finally {
        lock.unlock();
    }
  }

  public String giveDishToCustomer() {

    lock.lock();

    try {
      while (! hasDish) {
        emptyTray.await();
      }
      hasDish = false;
      emptyTray.signalAll();
      System.out.println("Waiter gives " + currentDish + " to customer");
    } catch (InterruptedException e) {
    } finally {
        lock.unlock();
    }
    return currentDish;
  }
}
