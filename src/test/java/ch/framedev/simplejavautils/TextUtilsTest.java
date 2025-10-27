package ch.framedev.simplejavautils;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TextUtilsTest {

    private TextUtils textUtils;

    @Before
    public void setUp() throws Exception {
        textUtils = new TextUtils();
    }

    @Test
    public void onReplace() {
        String result = textUtils.replaceObject("Hello, {name}!", "{name}", "World");
        assert result.equals("Hello, World!");

        String result2 = textUtils.replaceAndWithParagraph("Hello & World");
        assert result2.equals("Hello § World");
    }

    @Test
    public void onArrayToList() {
        String[] array = {"one", "two", "three"};
        List<String> list = textUtils.arrayToList(array);
        assert list.size() == 3;
        assert list.get(0).equals("one");
        assert list.get(1).equals("two");
        assert list.get(2).equals("three");
    }

    @Test
    public void onStringListToArray() {
        List<String> list = new ArrayList<>();
        Collections.addAll(list, "one", "two", "three");
        String[] array = textUtils.stringListToArray(list);
        assert array.length == 3;
        assert array[0].equals("one");
        assert array[1].equals("two");
        assert array[2].equals("three");
    }

    @Test
    public void onIntListToArray() {
        List<Integer> list = new ArrayList<>();
        Collections.addAll(list, 1, 2, 3);
        Integer[] array = textUtils.intListToArray(list);
        assert array.length == 3;
        assert array[0] == 1;
        assert array[1] == 2;
        assert array[2] == 3;
    }

    @Test
    public void onDoubleListToArray() {
        List<Double> list = new ArrayList<>();
        Collections.addAll(list, 1.1, 2.2, 3.3);
        Double[] array = textUtils.doubleListToArray(list);
        assert array.length == 3;
        assert array[0] == 1.1;
        assert array[1] == 2.2;
        assert array[2] == 3.3;
    }

    @Test
    public void onFloatListToArray() {
        List<Float> list = new ArrayList<>();
        Collections.addAll(list, 1.1f, 2.2f, 3.3f);
        Float[] array = textUtils.floatListToArray(list);
        assert array.length == 3;
        assert array[0] == 1.1f;
        assert array[1] == 2.2f;
        assert array[2] == 3.3f;
    }

    @Test
    public void onShortListToArray() {
        List<Short> list = new ArrayList<>();
        Collections.addAll(list, (short)1, (short)2, (short)3);
        Short[] array = textUtils.shortListToArray(list);
        assert array.length == 3;
        assert array[0] == 1;
        assert array[1] == 2;
        assert array[2] == 3;
    }

    @Test
    public void onLongListToArray() {
        List<Long> list = new ArrayList<>();
        Collections.addAll(list, 1L, 2L, 3L);
        Long[] array = textUtils.longListToArray(list);
        assert array.length == 3;
        assert array[0] == 1L;
        assert array[1] == 2L;
        assert array[2] == 3L;
    }

    @Test
    public void onByteListToArray() {
        List<Byte> list = new ArrayList<>();
        Collections.addAll(list, (byte) 1, (byte) 2, (byte) 3);
        Byte[] array = textUtils.byteListToArray(list);
        assert array.length == 3;
        assert array[0] == 1;
        assert array[1] == 2;
        assert array[2] == 3;
    }

    @Test
    public void onObjectListToArray() {
        List<Object> list = new ArrayList<>();
        Collections.addAll(list, "one", 2, 3.0);
        Object[] array = textUtils.objectListToArray(list);
        assert array.length == 3;
        assert array[0].equals("one");
        assert array[1].equals(2);
        assert array[2].equals(3.0);
    }

    @Test
    public void onNullInputs() {
        assert textUtils.replaceAndWithParagraph(null) == null;
        assert textUtils.replaceObject(null, "{name}", "World") == null;
    }

    @Test
    public void onEmptyInputs() {
        String result = textUtils.replaceObject("", "{name}", "World");
        assert result.isEmpty();

        String result2 = textUtils.replaceAndWithParagraph("");
        assert result2.isEmpty();
    }

    @Test
    public void onListToArray() {
        List<String> list = new ArrayList<>();
        Collections.addAll(list, "one", "two", "three");
        String[] array = textUtils.listToArray(list, String.class);
        assert array.length == 3;
        assert array[0].equals("one");
        assert array[1].equals("two");
        assert array[2].equals("three");
    }

    @Test
    public void onDoubleToInt() {
        int result = textUtils.doubleToInt(5.7);
        assert result == 5;

        int result2 = textUtils.doubleToInt(-3.2);
        assert result2 == -3;
    }

    @Test
    public void onLongToInt() {
        int result = textUtils.longToInt(123456789L);
        assert result == 123456789;

        int result2 = textUtils.longToInt(-987654321L);
        assert result2 == -987654321;
    }

    @Test
    public void onFloatToInt() {
        int result = textUtils.floatToInt(4.9f);
        assert result == 4;

        int result2 = textUtils.floatToInt(-2.6f);
        assert result2 == -2;
    }

    @Test
    public void onCenterTextWithSymbol() {
        String result = textUtils.centerTextWithSymbol("Hello", '*', 11);
        assert result.equals("*****Hello*****");

        String result2 = textUtils.centerTextWithSymbol("World", '-', 10);
        assert result2.equals("-----World-----");
    }

    @Test
    public void onPrintBox() {
        textUtils.printBox("Hello, World!", "This is a test box.");
    }

    @Test
    public void onPrintBoxWithEmptyLines() {
        textUtils.printBox("", "This is a test box with empty lines.", "");
    }

    @Test
    public void onPrintBoxWithLongText() {
        textUtils.printBox("This is a very long line of text that should be properly handled by the printBox method.", "Short line.");
    }

    @Test
    public void onTopBottomVoid() {
        textUtils.topBottomVoid(10);
    }

    @Test
    public void onGenerateBox() {
        String box = textUtils.generateBox("Hello, World!", "This is a test box.");
        System.out.println(box);
    }

    @Test
    public void onMilesToMeters() {
        double meters = textUtils.milesToMeters(1.0);
        assert meters == 1609.344;
    }

    @Test
    public void onMetersToMiles() {
        double miles = textUtils.metersToMiles(1609.34);
        assert Math.abs(miles - 1.0) < 0.0001;
    }
}
