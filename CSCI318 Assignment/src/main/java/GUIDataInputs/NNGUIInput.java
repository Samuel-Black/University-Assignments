package GUIDataInputs;

import javax.swing.JButton;

public abstract class NNGUIInput extends javax.swing.JPanel {

    public abstract Object createObject();

    public abstract JButton getAcceptButton();
}
