package Classes;

import java.util.List;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import org.apache.commons.text.similarity.LevenshteinDistance;
import org.springframework.util.ClassUtils;

public class RecursiveObjDiff {  //get the length of the path by traversing the inheritance tree

    private int tracePathLength(String goal, Object clazz) {
        int pathLength = 0;
        //for class of s1
        Class<?> parent = clazz.getClass();
        while (goal != parent.getName() && parent.getName() != "java.lang.Object") {
            pathLength++;
            parent = parent.getSuperclass();
        }
        return pathLength;
    }

    //get number of non-shared fields
    private int countNonsharedFields(String goal, Object clazz) {
        int count = 0;
        //for class of s1
        Class<?> parent = clazz.getClass();
        while (goal != parent.getName() && parent.getName() != "java.lang.Object") {
            count += parent.getDeclaredFields().length;
            parent = parent.getSuperclass();
        }
        return count;
    }

    //type distance = sum(pathlength(s1, ancestor), pathlength(s2, ancestor)) + number of non-shared fields
    private double typeDistance(Object s1, Object s2) {
        double diff = 0;
        int pathLength = 0;
        int fieldCount = 0;
        Class<?> commonAncestor = ClassUtils.determineCommonAncestor(s1.getClass(), s2.getClass());

        if (commonAncestor == null) { //no common ancestor other than Object
            pathLength += tracePathLength("java.lang.Object", s1);
            pathLength += tracePathLength("java.lang.Object", s2);
            fieldCount += countNonsharedFields("java.lang.Object", s1);
            fieldCount += countNonsharedFields("java.lang.Object", s2);
        } else {
            //calc sum of class paths
            String ancestor = commonAncestor.getName();
            pathLength += tracePathLength(ancestor, s1);
            pathLength += tracePathLength(ancestor, s2);
            fieldCount += countNonsharedFields(ancestor, s1);
            fieldCount += countNonsharedFields(ancestor, s2);
        }
        return diff + pathLength + fieldCount;
    }

    private double fieldDistance(Object s1, Object s2, List<String> changedProperties,
            String parent, double diff, int sharedFields) throws IllegalArgumentException, IllegalAccessException, InstantiationException {
        Field fields[];//store fields that need to be calculated later 
        if (sharedFields == -1) {//objects of same class
            fields = s1.getClass().getDeclaredFields();
            Class<?> parentz = s1.getClass().getSuperclass();
            while (parentz.getName() != "java.lang.Object") {
                fields = (Field[]) concatArray(parentz.getDeclaredFields(), fields);
                parentz = parentz.getClass().getSuperclass();
            }
        } else {//objects of different classes
            Class<?> ancestor = ClassUtils.determineCommonAncestor(s1.getClass(), s2.getClass());
            try {
                fields = ancestor.getDeclaredFields();
                Class<?> parentz = ancestor.getClass().getSuperclass();
                while (parentz.getName() != "java.lang.Object") {
                    fields = (Field[]) concatArray(parentz.getDeclaredFields(), fields);
                    parentz = parentz.getClass().getSuperclass();
                }
            } catch (NullPointerException e) {
                fields = new Field[0];
            }

        }

        //compare each field
        for (Field field : fields) {
            //get class of first field
            if (parent == null) {
                parent = s2.getClass().getSimpleName();
            }

            //set field to public for accessibility
            field.setAccessible(true);
            //get field values of each object
            Object value1, value2;

            try {
                value1 = field.get(s1);
                value2 = field.get(s2);
            } catch (IllegalArgumentException e) {
                System.out.println("field distance error");
                continue;
            }

            if (value1 == null && value2 == null) { //if both fields are empty
                diff += 0;
            }
            if (value1 == null || value2 == null) {	//if one of them is empty
                changedProperties.add(parent + "." + field.getName());
                diff += R;
            } else {
                if (isBaseType(value1.getClass())) { //if they are of primitive types
                    if (!Objects.equals(value1, value2)) {
                        changedProperties.add(parent + "." + field.getName());
                        if (isBoolChar(value1.getClass())) {
                            if (value1 != value2) {
                                diff += B; //constant value referenced from paper 4
                            }
                        } else if (isNumericType(value1.getClass())) { //numeric type
                            diff += normalize(Math.abs(asDouble(value1) - asDouble(value2)));
                        } else { //string type
                            LevenshteinDistance lev = new LevenshteinDistance();
                            diff += normalize(lev.apply(value1.toString(), value2.toString()));
                        }
                    }
                } else { //they are referenced objects
                    //calc the difference for the nested objects recursively
                    double refDiff = fieldDistance(value1, value2, changedProperties, parent, 0, 1);
                    if (refDiff != 0) { //if the references are identical
                        diff += R;
                    }
                }
            }
        }
        return diff;
    }

    private Field[] concatArray(Field array1[], Field array2[]) {
        Field[] result = new Field[array1.length + array2.length];
        System.arraycopy(array1, 0, result, 0, array1.length);
        System.arraycopy(array2, 0, result, array1.length, array2.length);
        return result;
    }

    //function for calculating the distance
    //we could divide it into smaller functions for calculating field distance and type distance
    //changedProperties list stores the properties that have differ values for the two compared objects
    //parent string is the currently-compared field we are at
    //diff is the resulting numeric value for the distance
    protected double difference(Object s1, Object s2, List<String> changedProperties,
            String parent, double diff) throws IllegalAccessException, InstantiationException {
        // ======= If objects of different classes ======//
        // =========TO-DO=============== //
        if (s1.getClass() != s2.getClass()) {
            diff += typeDistance(s1, s2);
            //get common ancestor
            Class<?> ancestor = ClassUtils.determineCommonAncestor(s1.getClass(), s2.getClass());
            if (ancestor != null) {
                diff = fieldDistance(s1, s2, changedProperties, parent, diff, 1);
            }
        } else {
            // ======== If objects of the same class ======== //
            // This occurs when the objects are of the same class in the first place 
            // or after they are up-casted to their common superclass
            diff = fieldDistance(s1, s2, changedProperties, parent, diff, -1);
        }
        return diff;
    }

    //cast Object to Double
    public Double asDouble(Object o) {
        Double val = null;
        if (o instanceof Number) {
            val = ((Number) o).doubleValue();
        }
        return val;
    }

    public double normalize(double value) {
        return 1 - (1 / (1 + value));
    }

    private static final Set<Class> NUMERIC_TYPES = new HashSet(Arrays.asList(
            Boolean.class, Byte.class, Short.class, Integer.class,
            Long.class, Float.class, Double.class, Void.class));

    private static final Set<Class> BOOLEAN_CHAR_TYPE = new HashSet(Arrays.asList(
            Boolean.class, Character.class));

    private static final Set<Class> STRING_TYPES = new HashSet(Arrays.asList(String.class));

    private static final Set<Class> BASE_TYPES = new HashSet<Class>(Arrays.asList(
            String.class, Boolean.class, Character.class, Byte.class, Short.class,
            Integer.class, Long.class, Float.class, Double.class, Void.class));

    private static final Set<Class> VOID_TYPE = new HashSet<Class>(Arrays.asList(Void.class));

    public static boolean isNumericType(Class clazz) {
        //return BASE_TYPES.contains(clazz);
        return NUMERIC_TYPES.contains(clazz);
    }

    public static boolean isBoolChar(Class clazz) {
        //return BASE_TYPEs.contains(clazz)
        return BOOLEAN_CHAR_TYPE.contains(clazz);
    }

    public static boolean isBaseType(Class clazz) {
        return BASE_TYPES.contains(clazz);
    }

    public static boolean isVoid(Class clazz) {
        return VOID_TYPE.contains(clazz);
    }

    // ========= CONSTANTS ============ //
    /*
	  * B = 10 : distance between two unequal boolean values.
	  * R = 10 : distance between two unequal, non-void references.
     */
    public static final double B = 10, R = 10;
}


/*  	TO-DO:
	- add all constant
 */
