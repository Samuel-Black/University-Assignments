package Classes;

import ObjectCreation.AddObject;
import java.util.ArrayList;
import java.util.Vector;

public class ARTOO { //psuedo-code in paper 4 

    private AddObject objDescription;
    private Vector usedObjects = new Vector(); //Used object set

    public ARTOO() {
    }

    public void setObjDescription(AddObject objDescription) {
        this.objDescription = objDescription;
    }

    public void clear() {
        usedObjects.clear();
    }

    public Object generateObject() throws IllegalAccessException, InstantiationException {
        double furthestDistance = 0; // Instantiate double to MIN
        Object furthestObject = null;
        double currentAccumulation = 0;
        if (usedObjects.isEmpty()) {
            Object object = Mutations.createObject(objDescription);
            furthestObject = object;
            usedObjects.add((Object) object);
        } else {
            Object[] candidates = new Object[k]; //Candidate objects

            for (int i = 0; i < k; i++) {
                //generate 10 random candidate objects
                //uncomment these lines to dynamically generate objects
                Object object = Mutations.createObject(objDescription);
                candidates[i] = (Object) object;
            }

            for (Object v0 : candidates) {
                currentAccumulation = 0;
                for (int i = 0; i < usedObjects.size(); i++) {
                    if (usedObjects.get(i) == null) {
                        System.out.println("usedObject" + i + " " + usedObjects.size());
                    }
                    currentAccumulation = currentAccumulation + getDistance(v0, usedObjects.get(i));
                }
                if (currentAccumulation > furthestDistance) {
                    furthestDistance = currentAccumulation;
                    furthestObject = (Object) v0;
                }
            }

            if (furthestObject == null) {
                System.out.println("object is null");
            }
            usedObjects.add((Object) furthestObject);
        }

        return furthestObject;
    }

    private double getDistance(Object obj1, Object obj2) throws IllegalAccessException, InstantiationException {
        RecursiveObjDiff comparator = new RecursiveObjDiff();
        ArrayList<String> changedProperties = new ArrayList<>();
        double distance = comparator.difference(obj1, obj2, changedProperties, null, 0);

        return distance;
    }

    // ====== Constants ======= //
    private final int k = 10; //size of candidate set
}
