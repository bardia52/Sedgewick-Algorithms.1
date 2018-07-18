/******************************************************************************
 *  Name:    Bardia Alavi
 *  NetID:   bardia
 *  Precept: P01
 *
 *  Partner Name:    N/A
 *  Partner NetID:   N/A
 *  Partner Precept: N/A
 * 
 *  Description:  Model a Double Queue data structure.
 ******************************************************************************/
import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

    private Node first = null;
    private Node last = null;
    private int size = 0;

    private class Node
    {
        Item item;
        Node next;
        Node previous;
    }

    public Deque()                       // construct an empty deque
    { }
    
    public boolean isEmpty()             // is the deque empty?
    {
        return (this.size == 0);
    }

    public int size()                    // return the number of items on the deque
    {
        return this.size;
    }

    public void addFirst(Item item)      // add the item to the front
    {
        if (item == null)
        {
            throw new IllegalArgumentException("Cannot add null items");
        }

        // Create a new node
        Node newNode = new Node();
        newNode.item = item;

        this.size++;

        // It there is only 1 node, first and last point to it
        if (size == 1)
        {
            this.last = newNode;
            this.first = newNode; 

        } else
        {
            // rearrange the pointers using a temporary pointer
            Node tempFirst = this.first;
            this.first = newNode;
            newNode.next = tempFirst;
            tempFirst.previous = newNode;
        }
    }

    public void addLast(Item item)       // add the item to the end
    {
        if (item == null)
        {
            throw new IllegalArgumentException("Cannot add null items");
        }

        // Create a new node
        Node newNode = new Node();
        newNode.item = item;

        this.size++;

        // If there is only 1 node, first and last point to it
        if (size == 1) {
            this.first = newNode;
            this.last = newNode;
            
        } else {
            // Insert new node at rear
            Node tempLast = this.last;
            this.last = newNode;
            newNode.previous = tempLast;
            tempLast.next = newNode;
        }
    }

    public Item removeFirst()            // remove and return the item from the front
    {
        if (size() == 0)
        {
            throw new NoSuchElementException("Deque is empty");
        }

        // Fetch item to return
        Item item = this.first.item;
        
        this.size--;

        // Reorder pointers
        this.first = this.first.next;

        if (size() == 0)
        {
            this.last = null;
        } else
        {
            this.first.previous = null;
        }

        return item;
    }

    public Item removeLast()             // remove and return the item from the end
    {
        if (size() == 0) {
            throw new NoSuchElementException("Deque is empty");
        }

        // Fetch item to return
        Item item = this.last.item;

        this.size--;

        // Rearrange pointers
        this.last = this.last.previous;

        if (size() == 0) {
            this.first = null;
        } else {
            this.last.next = null;
        }

        return item;
    }

    public static void main(String[] args) // unit testing (optional)
    {
        // TODO Auto-generated method stub
    }

    // return an iterator over items in order from front to end
    public Iterator<Item> iterator()
    {
        return new ListIterator();
    }

    private class ListIterator implements Iterator<Item>
    {
        private Node current = first;

        /** Returns true if there is an item next in the deque */
        @Override
        public boolean hasNext() {
            return (current != null);
        }

        /** returns the current item and increments the pointer */
        @Override
        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException("No more objects to "+
                                                 "iterate through");
            }
            
            Item item = current.item;
            current = current.next;
            
            return item;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("Iterator remove "+
                                                    "function not supported.");
        }
    }
}
