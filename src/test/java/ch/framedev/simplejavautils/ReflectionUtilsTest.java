package ch.framedev.simplejavautils;

import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Field;

public class ReflectionUtilsTest {

    private ReflectionUtils reflectionUtils;

    @Before
    public void setUp() throws Exception {
        reflectionUtils = new ReflectionUtils(true);
    }

    @Test
    public void getClassName() {
        String className = reflectionUtils.getClassName(TestClass.class);
        assert className.equals("ch.framedev.simplejavautils.TestClass") : "Class name should be 'ch.framedev.simplejavautils.TestClass'";
    }

    @Test
    public void getEnumValue() {
        String className = reflectionUtils.getClassName(TestEnum.class);
        Object enumValue = reflectionUtils.getEnumValue(className, "VALUE_TWO");
        assert enumValue != null : "Enum value should not be null";
        assert enumValue.toString().equals("VALUE_TWO") : "Enum value should be 'VALUE_TWO'";
    }

    @Test
    public void getEnumValues() {
        String className = reflectionUtils.getClassName(TestEnum.class);
        Object[] enumValues = reflectionUtils.getEnumValues(className);
        assert enumValues != null : "Enum values should not be null";
        assert enumValues.length == 3 : "Enum should have 3 values";
    }

    @Test
    public void getField() {
        String className = reflectionUtils.getClassName(TestClass.class);
        Field secretValue = reflectionUtils.getField(className, "secret");
        assert secretValue != null : "Field 'secret' should not be null";
        assert secretValue.getName().equals("secret") : "Field name should be 'secret'";
    }
}
