package ObjectCreation;

import java.lang.reflect.Constructor;
import java.util.Vector;

public class AddObject {

    Vector info;
    String name;
    Constructor constructor;

    public AddObject(String name, Constructor constructor, Vector info) {
        this.info = info;
        this.name = name;
        this.constructor = constructor;
    }

    public Vector getInfo() {
        return info;
    }

    public String getName() {
        return name;
    }

    public Constructor getConstructor() {
        return constructor;
    }
}
