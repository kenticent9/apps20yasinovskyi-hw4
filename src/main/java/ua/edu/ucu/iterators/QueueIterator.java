package ua.edu.ucu.iterators;

import ua.edu.ucu.collections.Queue;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class QueueIterator implements Iterator<String> {
    private Queue queue;
    private int currentPosition = 0;
    private List<String> objects = new ArrayList<>();

    public QueueIterator(Queue queue) {
        this.queue = queue;
    }

    private void lazyLoad() {
        if (objects.size() == 0) {
            for (int i = queue.getSize(); i > 0; i--) {
                Object e = queue.dequeue();
                objects.add((String) e);
                queue.enqueue(e);
            }
        }
    }

    @Override
    public boolean hasNext() {
        lazyLoad();
        return currentPosition < objects.size();
    }

    @Override
    public String next() {
        if (!hasNext()) {
            return null;
        }
        String object = objects.get(currentPosition);
        currentPosition++;
        return object;
    }
}
