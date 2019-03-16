package resources;

import java.io.InputStream;
import java.net.URL;

public class Resource {
    private static final Class<?> cls = Resource.class;
    public static URL getResource(String name) {
        return cls.getResource(name);
    }

    public static InputStream getResourceAsStream(String name) {
        return cls.getResourceAsStream(name);
    }
}
