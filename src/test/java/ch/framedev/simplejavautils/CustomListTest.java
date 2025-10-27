package ch.framedev.simplejavautils;

import org.junit.Test;

public class CustomListTest {

    @Test
    public void testAddAndGet() {
        CustomList<String> list = new CustomList<>();
        list.add("Hello");
        list.add("World");
        assert list.get(0).equals("Hello");
        assert list.get(1).equals("World");
    }

    @Test
    public void testSize() {
        CustomList<Integer> list = new CustomList<>();
        assert list.isEmpty();
        list.add(1);
        list.add(2);
        assert list.size() == 2;
    }

    @Test
    public void testRemove() {
        CustomList<String> list = new CustomList<>();
        list.add("A");
        list.add("B");
        list.add("C");
        assert list.remove("B");
        assert list.size() == 2;
        assert !list.contains("B");
    }

    @Test
    public void testIndexOf() {
        CustomList<String> list = new CustomList<>();
        list.add("X");
        list.add("Y");
        list.add("Z");
        assert list.indexOf("Y") == 1;
        assert list.indexOf("A") == -1;
    }

    @Test
    public void testSet() {
        CustomList<String> list = new CustomList<>();
        list.add("First");
        list.add("Second");
        list.set(1, "Updated");
        assert list.get(1).equals("Updated");
    }

    @Test
    public void testClear() {
        CustomList<Double> list = new CustomList<>();
        list.add(1.1);
        list.add(2.2);
        list.clear();
        assert list.isEmpty();
    }

    @Test
    public void testContains() {
        CustomList<Character> list = new CustomList<>();
        list.add('a');
        list.add('b');
        assert list.contains('a');
        assert !list.contains('c');
    }

    @Test
    public void testLastIndexOf() {
        CustomList<String> list = new CustomList<>();
        list.add("dup");
        list.add("unique");
        list.add("dup");
        assert list.lastIndexOf("dup") == 2;
        assert list.lastIndexOf("unique") == 1;
    }

    @Test
    public void testIteration() {
        CustomList<Integer> list = new CustomList<>();
        list.add(10);
        list.add(20);
        list.add(30);
        int sum = 0;
        for (Integer num : list) {
            sum += num;
        }
        assert sum == 60;
    }

    @Test
    public void testGetByValue() {
        CustomList<String> list = new CustomList<>();
        list.add("apple");
        list.add("banana");
        list.add("cherry");
        assert list.get("banana").equals("banana");
        assert list.get("date") == null;
    }

    @Test
    public void testRemoveByIndex() {
        CustomList<String> list = new CustomList<>();
        list.add("one");
        list.add("two");
        list.add("three");
        String removed = list.remove(1);
        assert removed.equals("two");
        assert list.size() == 2;
        assert !list.contains("two");
    }

    @Test
    public void testExpandCapacity() {
        CustomList<Integer> list = new CustomList<>();
        for (int i = 0; i < 20; i++) {
            list.add(i);
        }
        assert list.size() == 20;
        for (int i = 0; i < 20; i++) {
            assert list.get(i) == i;
        }
    }

    @Test
    public void testAddAtIndex() {
        CustomList<String> list = new CustomList<>();
        list.add("A");
        list.add("C");
        list.add(1, "B");
        assert list.get(1).equals("B");
        assert list.size() == 3;
    }

    @Test
    public void testToString() {
        CustomList<String> list = new CustomList<>();
        list.add("x");
        list.add("y");
        list.add("z");
        assert list.toString().equals("[x, y, z]");
    }

    @Test
    public void testIsEmpty() {
        CustomList<Object> list = new CustomList<>();
        assert list.isEmpty();
        list.add(new Object());
        assert !list.isEmpty();
    }

    @Test
    public void testMultipleDataTypes() {
        CustomList<Object> list = new CustomList<>();
        list.add("String");
        list.add(100);
        list.add(45.67);
        list.add(true);
        assert list.size() == 4;
        assert list.get(0).equals("String");
        assert list.get(1).equals(100);
        assert list.get(2).equals(45.67);
        assert list.get(3).equals(true);
    }

    @Test
    public void testNullValues() {
        CustomList<String> list = new CustomList<>();
        list.add(null);
        list.add("NotNull");
        assert list.size() == 2;
        assert list.get(0) == null;
        assert list.contains(null);
        assert list.indexOf(null) == 0;
        list.remove(null);
        assert list.size() == 1;
        assert !list.contains(null);
    }

    @Test
    public void testLargeNumberOfElements() {
        CustomList<Integer> list = new CustomList<>();
        int numElements = 1000;
        for (int i = 0; i < numElements; i++) {
            list.add(i);
        }
        assert list.size() == numElements;
        for (int i = 0; i < numElements; i++) {
            assert list.get(i) == i;
        }
    }

    @Test
    public void testExceptionOnInvalidIndex() {
        CustomList<String> list = new CustomList<>();
        list.add("A");
        list.add("B");
        boolean caught = false;
        try {
            list.get(2);
        } catch (IndexOutOfBoundsException e) {
            caught = true;
        }
        assert caught;

        caught = false;
        try {
            list.set(-1, "C");
        } catch (IndexOutOfBoundsException e) {
            caught = true;
        }
        assert caught;

        caught = false;
        try {
            list.remove(5);
        } catch (IndexOutOfBoundsException e) {
            caught = true;
        }
        assert caught;
    }

    @Test
    public void trimToSizeTest() {
        CustomList<Integer> list = new CustomList<>();
        for (int i = 0; i < 50; i++) {
            list.add(i);
        }
        int capacityBefore = list.getCapacity();
        list.trimToSize();
        int capacityAfter = list.getCapacity();
        assert capacityAfter == list.size();
        assert capacityAfter < capacityBefore;
    }

    @Test
    public void toArrayTest() {
        CustomList<String> list = new CustomList<>();
        list.add("one");
        list.add("two");
        list.add("three");
        String[] array = list.toArray(new String[0]);
        assert array.length == 3;
        assert array[0].equals("one");
        assert array[1].equals("two");
        assert array[2].equals("three");
    }

    @Test
    public void toArrayObjectTest() {
        CustomList<String> list = new CustomList<>();
        list.add("alpha");
        list.add("beta");
        Object[] array = list.toArray();
        assert array.length == 2;
        assert array[0].equals("alpha");
        assert array[1].equals("beta");
    }
}
