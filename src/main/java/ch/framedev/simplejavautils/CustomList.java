// java
package ch.framedev.simplejavautils;

import java.io.Serializable;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

@SuppressWarnings({"unchecked", "UnusedReturnValue"})
public class CustomList<T> implements Iterable<T>, Serializable {

    private static final long serialVersionUID = 1L;

    private static final int DEFAULT_CAPACITY = 10;

    private T[] items;
    private int size;

    public CustomList() {
        this(DEFAULT_CAPACITY);
    }

    public CustomList(int initialCapacity) {
        int cap = Math.max(1, initialCapacity);
        items = (T[]) new Object[cap];
        size = 0;
    }

    private void ensureCapacity(int minCapacity) {
        if (items == null) {
            items = (T[]) new Object[Math.max(DEFAULT_CAPACITY, minCapacity)];
            return;
        }
        if (minCapacity <= items.length) return;
        int newCapacity = Math.max(items.length * 2, minCapacity);
        T[] newItems = (T[]) new Object[newCapacity];
        System.arraycopy(items, 0, newItems, 0, size);
        items = newItems;
    }

    public void add(T item) {
        ensureCapacity(size + 1);
        items[size++] = item;
    }

    public void add(int index, T item) {
        checkPositionIndex(index); // allow index == size
        ensureCapacity(size + 1);
        System.arraycopy(items, index, items, index + 1, size - index);
        items[index] = item;
        size++;
    }

    public T get(int index) {
        checkElementIndex(index);
        return items[index];
    }

    public T get(T item) {
        int idx = indexOf(item);
        if (idx == -1) return null;
        return items[idx];
    }

    public T set(int index, T item) {
        checkElementIndex(index);
        T old = items[index];
        items[index] = item;
        return old;
    }

    public T remove(int index) {
        checkElementIndex(index);
        T old = items[index];
        int moved = size - index - 1;
        if (moved > 0) {
            System.arraycopy(items, index + 1, items, index, moved);
        }
        items[--size] = null;
        return old;
    }

    public boolean remove(T item) {
        int idx = indexOf(item);
        if (idx == -1) return false;
        remove(idx);
        return true;
    }

    public boolean contains(T item) {
        return indexOf(item) >= 0;
    }

    public int indexOf(T item) {
        for (int i = 0; i < size; i++) {
            if (Objects.equals(items[i], item)) return i;
        }
        return -1;
    }

    public int lastIndexOf(T item) {
        for (int i = size - 1; i >= 0; i--) {
            if (Objects.equals(items[i], item)) return i;
        }
        return -1;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void clear() {
        for (int i = 0; i < size; i++) items[i] = null;
        size = 0;
    }

    public void trimToSize() {
        if (items.length == size) return;
        T[] newItems = (T[]) new Object[Math.max(size, 1)];
        System.arraycopy(items, 0, newItems, 0, size);
        items = newItems;
    }

    public Object[] toArray() {
        Object[] arr = new Object[size];
        System.arraycopy(items, 0, arr, 0, size);
        return arr;
    }

    public T[] toArray(T[] a) {
        if (a.length < size) {
            T[] r = (T[]) java.lang.reflect.Array.newInstance(a.getClass().getComponentType(), size);
            System.arraycopy(items, 0, r, 0, size);
            return r;
        }
        System.arraycopy(items, 0, a, 0, size);
        if (a.length > size) a[size] = null;
        return a;
    }

    @Override
    public Iterator<T> iterator() {
        return new Itr();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < size; i++) {
            sb.append(items[i]);
            if (i < size - 1) sb.append(", ");
        }
        sb.append("]");
        return sb.toString();
    }

    // --- helpers ---

    private void checkElementIndex(int index) {
        if (index < 0 || index >= size)
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
    }

    private void checkPositionIndex(int index) {
        if (index < 0 || index > size)
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
    }

    private class Itr implements Iterator<T> {
        private int cursor = 0;
        private int lastRet = -1;

        @Override
        public boolean hasNext() {
            return cursor != size;
        }

        @Override
        public T next() {
            if (cursor >= size) throw new NoSuchElementException();
            lastRet = cursor++;
            return items[lastRet];
        }

        @Override
        public void remove() {
            if (lastRet < 0) throw new IllegalStateException();
            CustomList.this.remove(lastRet);
            cursor = lastRet;
            lastRet = -1;
        }
    }
}