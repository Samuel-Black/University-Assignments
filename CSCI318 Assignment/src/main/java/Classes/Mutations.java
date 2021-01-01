package Classes;

import ObjectCreation.AddInteger;
import ObjectCreation.AddObject;
import ObjectCreation.AddDouble;
import ObjectCreation.AddChar;
import ObjectCreation.AddString;
import static Program.CSCI318.generator;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.util.Vector;

public class Mutations {

    public static Object createObject(AddObject obj) {
        Object object = null;
        Type[] types = obj.getConstructor().getParameterTypes();
        Vector otherInfo = obj.getInfo();

        Object[] parameters = new Object[types.length];

        for (int i = 0; i < types.length; i++) {
            if (null == types[i].getTypeName()) {
                AddObject info = (AddObject) otherInfo.get(i);
                parameters[i] = createObject(info);
            } else {
                switch (types[i].getTypeName()) {
                    case "java.lang.String": {
                        AddString info = (AddString) otherInfo.get(i);
                        parameters[i] = new String(getString(info.getSize(), info.getLowerLimit(), info.getUpperLimit()));
                        break;
                    }
                    case "int": {
                        AddInteger info = (AddInteger) otherInfo.get(i);
                        parameters[i] = new Integer(getInt(info.getLowerLimit(), info.getUpperLimit()));
                        break;
                    }
                    case "double": {
                        AddDouble info = (AddDouble) otherInfo.get(i);
                        parameters[i] = new Double(getDouble(info.getLowerLimit(), info.getUpperLimit()));
                        break;
                    }
                    case "boolean":
                        parameters[i] = new Boolean(getBoolean());
                        break;
                    case "char": {
                        AddChar info = (AddChar) otherInfo.get(i);
                        parameters[i] = new Character(getChar(65, 122));
                        break;
                    }
                    default: {
                        AddObject info = (AddObject) otherInfo.get(i);
                        parameters[i] = createObject(info);
                        break;
                    }
                }
            }
        }

        try {
            object = obj.getConstructor().newInstance(parameters);
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException
                | InvocationTargetException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return object;
    }

    private static boolean getBoolean() {
        return generator.nextBoolean();
    }

    private static char getChar(int lowerLimit, int upperLimit) {
        return (char) (lowerLimit + (int) (generator.nextFloat() * (upperLimit - lowerLimit + 1)));
    }

    private static int getInt(int lowerLimit, int upperLimit) {

        // Handles max random range generation
        if (lowerLimit == Integer.MIN_VALUE && upperLimit == Integer.MAX_VALUE) {
            int posNeg = generator.nextInt(2);
            int limit = generator.nextInt(upperLimit);
            if (posNeg == 0) {
                limit = limit * -1;
            }
            //System.out.println("MIX random!: " + limit);
            return limit;
        }

        // Handles negative range generation
        int limited = (upperLimit - lowerLimit);
        if (limited <= 0) {
            int posLimit = Math.abs(limited + 2);
            int limit = generator.nextInt(posLimit) * -1;
            //System.out.println("NEG Random: " + limit + "\n");
            return limit;
        }

        // Handles positive range generation
        int limit = generator.nextInt(upperLimit - (lowerLimit)) + lowerLimit + 1;
        //System.out.println("POS Random: " + limit + "\n");
        return limit;
    }

    private static double getDouble(double lowerLimit, double upperLimit) {
//        if (upperLimit < lowerLimit) {
//            double temp = lowerLimit;
//            lowerLimit = upperLimit;
//            upperLimit = temp;
//        }
//
//        return generator.nextDouble() * (upperLimit - lowerLimit) + lowerLimit;
//        
        // Handles max random range generation
        if (lowerLimit == Integer.MIN_VALUE && upperLimit == Integer.MAX_VALUE) {
            int posNeg = generator.nextInt(2);
            double limit = generator.nextDouble() * upperLimit;
            if (posNeg == 0) {
                limit = limit * -1;
            }
            //System.out.println("MIX random!: " + limit);
            return limit;
        }

        // Handles negative range generation
        double limited = (upperLimit - lowerLimit);
        if (limited <= 0) {
            double posLimit = Math.abs(limited + 2);
            double limit = (generator.nextDouble() * posLimit) * -1;
            //System.out.println("NEG Random: " + limit + "\n");
            return limit;
        }

        // Handles positive range generation
        double limit = (generator.nextDouble() * upperLimit - (lowerLimit)) + lowerLimit + 1;
        //System.out.println("POS Random: " + limit + "\n");
        return limit;

    }

    private static String getString(int size, int lowerLimit, int upperLimit) {
        // Create a StringBuffer to store the result 
        StringBuffer r = new StringBuffer(size);

        for (int i = 0; i < size; i++) {
            // take a random value between lower limit and upper limit 
            int nextRandomChar = lowerLimit + (int) (generator.nextFloat() * (upperLimit - lowerLimit + 1));

            //
            // The following block removes spacing, new line and null characters from the char generation
            // Remove for 'pure' experience
            //
            // Remove tab, space and null character
            if (nextRandomChar == 9 || nextRandomChar == 0 || nextRandomChar == 32) {
                nextRandomChar = 35;
            }

            // Remove new line and carriage feed
            if (nextRandomChar == 10 || nextRandomChar == 13) {
                nextRandomChar = 35;
            }

            // End space cleaning block
            // append a character at the end of bs 
            r.append((char) nextRandomChar);
        }

        // return the resultant string 
        return r.toString();
    }
}
