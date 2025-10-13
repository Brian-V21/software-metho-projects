package software.metho.project1.util;
import java.util.Iterator;

/** 
import java.util.ListIterator;
*/

public class List<E> implements Iterable<E> {
    private E[] objects;
    private int size;

    @SuppressWarnings("unchecked")
    public List() {
        objects = (E[]) new Object[4]; //capacity 4
        size = 0;
    }

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

    public boolean contains(E e) {
        return find(e) != -1;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    @Override
    public Iterator<E> iterator() {
        return new ListIter();
    }

    public E get(int index) {
        if  (index < 0 || index >= size) throw new IndexOutOfBoundsException(index);
        return objects[index];
    }

    public void set (int index, E e) {
        if  (index < 0 || index >= size) throw new IndexOutOfBoundsException(index);
        objects[index] = e;
    }

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
