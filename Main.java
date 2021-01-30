Producer-Consumer problem
It is a classical multi-process synchronization problem that is we are trying to achieve synchronization between multiple processes

Problem
1.	To make sure that the producer won’t try to add data into the buffer if it’s full.
2.	And that the consumer won’t try to remove data from an empty buffer.


Java program to implement solution of producer consumer problem.

import java.util.*;
public class Main 
{
public static void main(String[] args) 
{
List list = new ArrayList(); //Same memory is shared by both producers and consumers
int size = 8;  // Memory Size
Thread t1 = new Thread(new Producer(list, size), "Producer");
Thread t2 = new Thread(new Consumer(list), "Consumer");
t1.start();
t2.start();
}
}

class Producer implements Runnable 
{
private final List list;
private final int size;

public Producer(List list, int size) 
{
this.list = list;
this.size = size;
}

public void run() 
{
try 
{
produce();
} 
catch (InterruptedException e) 
{
e.printStackTrace();
}
}

private void produce() throws InterruptedException 
{
int i = 0;
while (true) 
{
synchronized (list) 
{
while (list.size() == size) 
{
System.out.println("List is full." + Thread.currentThread().getName() + " is waiting. Size:" + list.size());
list.wait();
}
System.out.println("Produce :" + i);
list.add(i++);
Thread.sleep(5000);
list.notify();
}
}
}
}

class Consumer implements Runnable 
{
private final List list;
public Consumer(List list) 
{
this.list = list;
}

public void run() 
{
try 
{
consume();
} 
catch (InterruptedException e) 
{
e.printStackTrace();
}
}

private void consume() throws InterruptedException 
{
while (true) 
{
synchronized (list) 
{
while (list.isEmpty()) 
{
System.out.println("List is empty. " + Thread.currentThread().getName() + " is waiting. Size:" + list.size());
list.wait();
}
System.out.println("Consumed item:" + list.remove(0));
Thread.sleep(5000);
list.notify();
}
}
}
}
