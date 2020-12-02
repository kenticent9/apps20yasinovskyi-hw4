package ua.edu.ucu.collections;

import ua.edu.ucu.collections.immutable.ImmutableLinkedList;
import ua.edu.ucu.iterators.QueueIterator;

import java.util.Iterator;

public class Queue implements Iterable<String> {
    private ImmutableLinkedList list;
    private int size;

    public Queue() {
        list = new ImmutableLinkedList();
        size = 0;
    }

    public int getSize() {
        return size;
    }

    public Object peek() {
        return list.getFirst();
    }

    public Object dequeue() {
        Object e = list.getFirst();
        list = list.removeFirst();
        size--;
        return e;
    }

    public void enqueue(Object e) {
        list = list.addLast(e);
        size++;
    }

    @Override
    public Iterator<String> iterator() {
        return new QueueIterator(this);
    }
}
