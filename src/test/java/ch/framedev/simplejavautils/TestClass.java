package ch.framedev.simplejavautils;

@SuppressWarnings({"FieldCanBeLocal", "unused", "FieldMayBeFinal"})
public class TestClass {
    private String secret = "hiddenValue";
    private static String secretStatic = "hiddenStaticValue";
    public String publicSecret = "visibleValue";
    public static String publicStaticSecret = "visibleStaticValue";

    private String getSecret() {
        return secret;
    }

    private static String getSecretStatic() {
        return secretStatic;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public void setPublicSecret(String publicSecret) {
        this.publicSecret = publicSecret;
    }
}