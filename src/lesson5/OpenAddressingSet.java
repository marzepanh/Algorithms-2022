package lesson5;

import org.jetbrains.annotations.NotNull;

import java.util.*;

public class OpenAddressingSet<T> extends AbstractSet<T> {

    private final int bits;

    private final int capacity;

    private final Object[] storage;

    private int size = 0;

    private enum DeletedCell {
        DELETED
    }

    private int startingIndex(Object element) {
        return element.hashCode() & (0x7FFFFFFF >> (31 - bits));
    }

    public OpenAddressingSet(int bits) {
        if (bits < 2 || bits > 31) {
            throw new IllegalArgumentException();
        }
        this.bits = bits;
        capacity = 1 << bits;
        storage = new Object[capacity];
    }

    @Override
    public int size() {
        return size;
    }

    /**
     * Проверка, входит ли данный элемент в таблицу
     */
    @Override
    public boolean contains(Object o) {
        int index = startingIndex(o);
        Object current = storage[index];
        while (current != null) {
            if (current.equals(o)) {
                return true;
            }
            index = (index + 1) % capacity;
            current = storage[index];
        }
        return false;
    }

    /**
     * Добавление элемента в таблицу.
     *
     * Не делает ничего и возвращает false, если такой же элемент уже есть в таблице.
     * В противном случае вставляет элемент в таблицу и возвращает true.
     *
     * Бросает исключение (IllegalStateException) в случае переполнения таблицы.
     * Обычно Set не предполагает ограничения на размер и подобных контрактов,
     * но в данном случае это было введено для упрощения кода.
     */
    @Override
    public boolean add(T t) {
        int startingIndex = startingIndex(t);
        int index = startingIndex;
        Object current = storage[index];
        while (current != null && current != DeletedCell.DELETED) {
            if (current.equals(t)) {
                return false;
            }
            index = (index + 1) % capacity;
            if (index == startingIndex) {
                throw new IllegalStateException("Table is full");
            }
            current = storage[index];
        }
        storage[index] = t;
        size++;
        return true;
    }

    /**
     * Удаление элемента из таблицы
     *
     * Если элемент есть в таблица, функция удаляет его из дерева и возвращает true.
     * В ином случае функция оставляет множество нетронутым и возвращает false.
     * Высота дерева не должна увеличиться в результате удаления.
     *
     * Спецификация: {@link Set#remove(Object)} (Ctrl+Click по remove)
     *
     * Средняя
     *
     * Затраты:
     *  T = O(n)
     *  R = O(1)
     */
    @Override
    public boolean remove(Object o) {
        if (o == DeletedCell.DELETED) return false;
        int index = startingIndex(o);
        int startingIndex = index;
        Object current = storage[index];
        while (current != null) {
            if (current.equals(o)) {
                storage[index] = DeletedCell.DELETED;
                size--;
                return true;
            }
            index = (index + 1) % capacity;
            if (index == startingIndex) return false;
            current = storage[index];
        }
        return false;
    }

    /**
     * Создание итератора для обхода таблицы
     *
     * Не забываем, что итератор должен поддерживать функции next(), hasNext(),
     * и опционально функцию remove()
     *
     * Спецификация: {@link Iterator} (Ctrl+Click по Iterator)
     *
     * Средняя (сложная, если поддержан и remove тоже)
     */

    @NotNull
    @Override
    public Iterator<T> iterator() {
        return new OpenAddressingIterator();
    }

    public class OpenAddressingIterator implements Iterator<T> {

        private int index = -1;

        private int iteration = 0;

        private final int startSize = size;

        private Object prev = null;

        /**
         * Затраты:
         *  T = O(1)
         *  R = O(1)
        */
        @Override
        public boolean hasNext() {
            return iteration < startSize;
        }

        /**
         * Затраты:
         *  T = O(n)
         *  R = O(1)
         */
        @Override
        public T next() {
            if (!hasNext()) throw new NoSuchElementException();
            index++;
            prev = storage[index];
            while (prev == null || prev == DeletedCell.DELETED) {
                index++;
                prev = storage[index];
            }
            iteration++;
            return (T) prev;
        }

        /**
         * Затраты:
         *  T = O(1)
         *  R = O(1)
         */
        @Override
        public void remove() {
            if (prev == null) throw new IllegalStateException();
            storage[index] = DeletedCell.DELETED;
            size--;
            prev = null;
        }
    }
}
