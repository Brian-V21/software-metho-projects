package org.example.project3softwaremetho.util;
import java.util.Iterator;
//hello
/** 
import java.util.ListIterator;
*/

public class List<E> implements Iterable<E> {
    private E[] objects;
    private int size;

    /**
     * Constructor for List
     */
    @SuppressWarnings("unchecked")
    public List() {
        objects = (E[]) new Object[4]; //capacity 4
        size = 0;
    }

    /**
     *
     * @param e generalized object to find
     * @return the index of the object in the collection
     */
    private int find(E e) {
        if (e == null) {
            for (int i = 0; i < size; i++) {
                if (objects[i] == null) {
                    return i;
                }
            }
            return -1;
        } else {
            for (int i = 0; i < size; i++) {
                if (objects[i].equals(e)) {
                    return i;
                }
            }
            return -1;
        }
    }

    /**
     * Increase capacity size of the array of objects
     */
    @SuppressWarnings("unchecked")
    private void grow() {
        if (this.size == this.objects.length) {
            E[] newObject = (E[]) new Object[this.objects.length + 4];
            for (int i = 0; i < size; i++) {
                newObject[i] = objects[i];
            }
            objects = newObject;
        }
    }

    /**
     * add object into the list
     * @param e object being added to the list
     */
    public void add(E e) {
        if (contains(e)) {
            return;
        }
        if (size == this.objects.length) {
            grow();
        }
        objects[size] = e;
        size++;

    }

    /**
     * remove object from list
     * @param e the object being removed form it respective list
     */
    public void remove(E e) {
        int idx = find(e);
        if (idx == -1) {
            return;
        }

        for (int i = idx; i < size - 1; i++) {
            objects[i] = objects[i + 1];
        }
        objects[size - 1] = null;
        size--;
    }

    /**
     * Checks to see if object is in the list
     * @param e object being checked
     * @return true if the object is in the collection
     */
    public boolean contains(E e) {
        return find(e) != -1;
    }

    /**
     * checks to see if the collection has 0 objects
     * @return true if the collection is empty
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     *
     * @return the size of the collection/list
     */
    public int size() {
        return size;
    }

    @Override
    public Iterator<E> iterator() {
        return new ListIter();
    }

    /**
     *
     * @param index the element number in the collection
     * @return the object of the specified index
     */
    public E get(int index) {
        if  (index < 0 || index >= size) throw new IndexOutOfBoundsException(index);
        return objects[index];
    }

    /**
     *
     * @param index element number in the collection
     * @param e the object being changed into a new object
     */
    public void set (int index, E e) {
        if  (index < 0 || index >= size) throw new IndexOutOfBoundsException(index);
        objects[index] = e;
    }

    /**
     *
     * @param e object being searched for its index
     * @return the index of the object
     */
    public int indexOf(E e) {
        return find(e);
    }

    private class ListIter implements Iterator<E> {
        int current = 0;
        @Override
        public boolean hasNext() {
            return (current < size);
        }
        @Override
        public E next() {
            return objects[current++];
        }
    }
}
