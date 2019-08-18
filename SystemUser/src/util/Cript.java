package util;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class Cript {

    public String UTF8encode(byte[] data) {
        return new String(data, StandardCharsets.UTF_8);
    }

    public byte[] UTF8decode(String data) {
        return data.getBytes(StandardCharsets.UTF_8);
    }

    public String BASE64encode(byte[] data) {
        String encoded = Base64.getEncoder().encodeToString(data).replaceAll("-", "");
        return encoded;
    }

    public byte[] BASE64decode(String data) {
        data = data.replaceAll("-", "");
        return Base64.getDecoder().decode(data);
    }

}
