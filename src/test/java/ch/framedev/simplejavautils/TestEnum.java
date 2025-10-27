package ch.framedev.simplejavautils;

@SuppressWarnings("unused")
public enum TestEnum {
    VALUE_ONE("ValueOne"),
    VALUE_TWO("ValueTwo"),
    VALUE_THREE("ValueThree");

    private final String value;

    TestEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
