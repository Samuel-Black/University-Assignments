package Classes;

import ObjectCreation.AddObject;
import ObjectCreation.AddBoolean;
import GUI.Colors;
import GUIDataInputs.CharInputFrame;
import GUIDataInputs.DoubleInputFrame;
import GUIDataInputs.IntegerInputFrame;
import GUIDataInputs.NNGUIInput;
import GUIDataInputs.StringInputFrame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Constructor;
import java.lang.reflect.Type;
import java.net.URLClassLoader;
import java.util.Arrays;
import java.util.Scanner;
import java.util.Vector;
import javax.swing.JDialog;
import javax.swing.UIManager;

public class TestEnvironment {

    private URLClassLoader classLoader = null;
    private static final Scanner input = new Scanner(System.in);

    public TestEnvironment(URLClassLoader ClassLoader) {
        classLoader = ClassLoader;
        //Set dark mode for Option dialog
        UIManager.put("OptionPane.background", Colors.backgroundDark);
        UIManager.put("OptionPane.messagebackground", Colors.backgroundDark);
        UIManager.put("Panel.background", Colors.backgroundDark);
    }

    public AddObject objectDescription(String targetClass, Constructor con) {
        //object constructor parameters
        Type[] types = con.getParameterTypes();
        System.out.println(Arrays.deepToString(types));
        Vector info = new Vector();

        for (int i = 0; i < types.length; i++) {

            String typeName = types[i].getTypeName();
            switch (typeName) {
                case "java.lang.String":
                    StringInputFrame stringInput = new StringInputFrame(i, con);
                    info.add(generateOptionPane(stringInput));
                    break;

                case "int":
                    IntegerInputFrame intInput = new IntegerInputFrame(i, con);
                    info.add(generateOptionPane(intInput));
                    break;

                case "double":
                    DoubleInputFrame doubleInput = new DoubleInputFrame(i, con);
                    info.add(generateOptionPane(doubleInput));
                    break;

                case "boolean":
                    info.add((Object) new AddBoolean());
                    break;

                case "char":
                    CharInputFrame charInput = new CharInputFrame(i, con);
                    info.add(generateOptionPane(charInput));
                    break;

                default:
                    String refClass = types[i].getTypeName();
                    info.add((Object) objectDescription(refClass, con));
                    break;
            }
        }

        AddObject obj = new AddObject(targetClass, con, info);

        return obj;
    }

    public Object generateOptionPane(NNGUIInput panel) {
        final JDialog dialog = new JDialog();
        dialog.setTitle("Select Parameter Range");
        dialog.setModal(true);
        dialog.setContentPane(panel);
        dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        dialog.pack();
        dialog.setLocationRelativeTo(null);

        panel.getAcceptButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                dialog.dispose();
            }
        });

        dialog.setVisible(true);

        return panel.createObject();
    }
}
