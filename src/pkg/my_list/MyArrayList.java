
package pkg.my_list;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class MyArrayList<T> implements Iterable<T> {

    private Object[] elements;
    private int size;

    public MyArrayList() {
        elements = new Object[10];
        size = 0;
    }

    public void add(T element) {
        ensureCapacity();
        elements[size++] = element;
    }

    @SuppressWarnings("unchecked")
    public T get(int index) {
        checkIndex(index);
        return (T) elements[index];
    }

    public T remove(int index) {
        checkIndex(index);

        T removedElement = get(index);
        int elementsToMove = size - index - 1;

        if (elementsToMove > 0) {
            System.arraycopy(
                    elements, index + 1,
                    elements, index,
                    elementsToMove
            );
        }

        elements[--size] = null;
        return removedElement;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    private void ensureCapacity() {
        if (size == elements.length) {
            elements = Arrays.copyOf(elements, elements.length * 2);
        }
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(
                    "Индекс: " + index + ", размер: " + size
            );
        }
    }

    // Поддержка Iterable и цикла for-each
    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private int index = 0;

            @Override
            public boolean hasNext() {
                return index < size;
            }

            @Override
            public T next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }

                return get(index++);
            }
        };
    }

    // Поддержка Stream API
    public Stream<T> stream() {
        return StreamSupport.stream(spliterator(), false);
    }
}
