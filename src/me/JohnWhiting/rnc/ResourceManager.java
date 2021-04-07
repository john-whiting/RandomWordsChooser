package me.JohnWhiting.rnc;

import java.net.URL;

public class ResourceManager {
    public static URL getResource(String path) {
        ClassLoader cl = Thread.currentThread().getContextClassLoader();
        return cl.getResource("resources/"+path);
    }
}
