package ua.edu.ucu.collections.immutable;

public final class ImmutableLinkedList implements ImmutableList {
    private Node head;
    private int len;

    static class Node {
        private Object data;
        private Node next;

        Node(Object data) {
            this.data = data;
            next = null;
        }

        public Object getData() {
            return data;
        }

        public void setData(Object toSet) {
            data = toSet;
        }

        public Node getNext() {
            return next;
        }

        public void setNext(Node toSet) {
            next = toSet;
        }

        /* Analog of Python's deepcopy */
        public Node myCopy() {
            Node newNode = new Node(data);

            Node curNode1 = newNode;
            Node curNode2 = next;
            while (curNode2 != null) {
                curNode1.setNext(new Node(curNode2.getData()));
                curNode1 = curNode1.getNext();
                curNode2 = curNode2.getNext();
            }
            return newNode;
        }
    }

    public ImmutableLinkedList() {
        head = null;
        len = 0;
    }

    public ImmutableLinkedList(Node head, int len) {
        this.head = head;
        this.len = len;
    }

    public ImmutableLinkedList(Object[] c) {
        head = arrayToList(c);
        len = c.length;
    }

    @Override
    public ImmutableLinkedList add(Object e) {
        return addAll(len, new Object[] {e});
    }

    @Override
    public ImmutableLinkedList add(int index, Object e) {
        return addAll(index, new Object[] {e});
    }

    @Override
    public ImmutableLinkedList addAll(Object[] c) {
        return addAll(len, c);
    }

    private static Node insert(Node node, Object data)
    {
        Node temp = new Node(data);
        if (node == null) {
            return temp;
        }
        Node curNode = node;
        while (curNode.getNext() != null) {
            curNode = curNode.getNext();
        }
        curNode.setNext(temp);
        return node;
    }

    private static Node arrayToList(Object[] c) {
        Node newHead = null;
        for (Object e : c) {
            newHead = insert(newHead, e);
        }
        return newHead;
    }

    @Override
    public ImmutableLinkedList addAll(int index, Object[] c) {
        if (index < 0 || index > len) {
            throw new IndexOutOfBoundsException();
        }
        Node newHead;
        if (head == null) {
            newHead = arrayToList(c);
        } else if (index == 0) {
            newHead = arrayToList(c);
            Node curNode = newHead;
            while (curNode.getNext() != null) {
                curNode = curNode.getNext();
            }
            curNode.setNext(head.myCopy());
        } else {
            newHead = head.myCopy();
            Node curNode = newHead;
            while (--index > 0) {
                curNode = curNode.getNext();
            }
            Node temp = curNode.getNext();
            curNode.setNext(arrayToList(c));
            while (curNode.getNext() != null) {
                curNode = curNode.getNext();
            }
            curNode.setNext(temp);
        }
        return new ImmutableLinkedList(newHead, len+c.length);
    }

    @Override
    public Object get(int index) {
        if (index < 0 || index >= len) {
            throw new IndexOutOfBoundsException();
        }
        Node curNode = head;
        while (index-- > 0) {
            curNode = curNode.getNext();
        }
        return curNode.getData();
    }

    @Override
    public ImmutableLinkedList remove(int index) {
        if (index < 0 || index >= len) {
            throw new IndexOutOfBoundsException();
        }
        Node newHead = head.myCopy();
        if (index == 0) {
            newHead = newHead.getNext();
        } else {
            Node curNode = newHead;
            while (--index > 0) {
                curNode = curNode.getNext();
            }
            curNode.setNext(curNode.getNext().getNext());
        }
        return new ImmutableLinkedList(newHead, len-1);
    }

    @Override
    public ImmutableLinkedList set(int index, Object e) {
        if (index < 0 || index >= len) {
            throw new IndexOutOfBoundsException();
        }
        Node newHead = head.myCopy();
        Node curNode = newHead;
        while (index-- > 0) {
            curNode = curNode.getNext();
        }
        curNode.setData(e);
        return new ImmutableLinkedList(newHead, len);
    }

    @Override
    public int indexOf(Object e) {
        int i = 0;
        Node curNode = head;
        while (curNode != null && !curNode.getData().equals(e)) {
            curNode = curNode.getNext();
            i++;
        }
        return (curNode != null) ? i : -1;
    }

    @Override
    public int size() {
        return len;
    }

    @Override
    public ImmutableLinkedList clear() {
        return new ImmutableLinkedList();
    }

    @Override
    public boolean isEmpty() {
        return len == 0;
    }

    @Override
    public Object[] toArray() {
        Object[] array = new Object[len];
        Node curNode = head;
        for (int i = 0; i < len; i++) {
            array[i] = curNode.getData();
            curNode = curNode.getNext();
        }
        return array;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        Node curNode = head;
        while (curNode != null) {
            sb.append(curNode.getData())
                    .append(" ");
            curNode = curNode.getNext();
        }
        return sb.toString();
    }

    public ImmutableLinkedList addFirst(Object e) {
        return add(0, e);
    }

    public ImmutableLinkedList addLast(Object e) {
        return add(len, e);
    }

    public Object getFirst() {
        return get(0);
    }

    public Object getLast() {
        return get(len-1);
    }

    public ImmutableLinkedList removeFirst() {
        return remove(0);
    }

    public ImmutableLinkedList removeLast() {
        return remove(len-1);
    }
}
