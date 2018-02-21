## Producer Consumer
An outline of a solution to the Producer Consumer problem using locks and conditions in Java.

### Context
The [Producer Consumer](https://en.wikipedia.org/wiki/Producer–consumer_problem) problem involves the sychronisation of two processes, a Producer and Consumer, as they input/output data respectively into a buffer used as a queue. I first encountered this problem in a lab for an Advanced Programming class in which we were required to implement a solution using locks and conditions.

Thread switching occurs in an unpredictable manner, yet in many programs the results of computation depend on the order of execution of threads. Such programs are susceptible to race conditions. Java provides mechanisms for thread safety (e.g. the `synchronized` keyword for blocks and methods) when accessing shared resources. Locks provide a similar purpose while allowing for more granular control. However, improper usage of locks may result in system deadlock. Deadlocks may be avoided by using conditions. These allow for a thread in possession of a given lock to temporarily suspend execution until signaled by another thread.

### Compiling and Running

Enter the following commands into your terminal:

    javac *.java
    java ProducerConsumer

The code will run until manually stopped.

### Implementation
The code contains four classes in which the problem is modelled as decribed below:

* `ProducerConsumer.java` contains the main method. A Waiter is created and passed into Chef and Customer objects, after which the `run()` methods of Chef and Customer are started.
* `Chef.java` (analogous to the Producer) extends `Thread`. The Chef calls the Waiter's `takeDishFromChef()` method for as long as it still has dishes to send.
* `Customer.java` (analogous to the Consumer) extends `Thread`. The Customer will forever try to call the Waiter's `giveDishToCustomer()` method.
* `Waiter.java` acts as a shared buffer (holding only a single item) for dishes sent from Chef to Customer and is where the bulk of the system logic is contained.

When each thread accesses the shared Waiter object, they lock the associated lock. The Chef checks a conditional statement to see if the Waiter currently has a dish; if not, it will try again to send one. If the Waiter has a dish, a call to the condition's `await()` method causes the Chef's thread to be temporarily suspended until it is signaled by another thread: the Customer. When signaled by the Customer, the Chef's thread once again rechecks the conditional statement. It may end up waiting again or be allowed to proceed. Once it it done, it signals the waiting Customer thread before unlocking the lock.

Meanwhile, the Customer's thread follows a similar process but instead checks the condition to see if the waiter doesn't have a dish. If the Waiter has nothing to give, the Customer has nothing to take and so its thread is temporarily suspended until it recieves a signal to resume. If it is allowed to resume, it too will signal the waiting Chef thread before unlocking the lock. Using conditions with locks as described means that the system should not enter a situation where both threads are blocked.
