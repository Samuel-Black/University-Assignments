package GUI;

import java.awt.Color;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.filechooser.FileFilter;

public class FileChooserPanel extends javax.swing.JPanel {

    public FileChooserPanel() {
        initComponents();
        setDarkMode();
        this.setVisible(true);
    }

    public File getFile() {
        fileChooser.setAcceptAllFileFilterUsed(false);
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));

        int returnVal = fileChooser.showOpenDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            System.out.println("You chose to open this file: "
                    + fileChooser.getSelectedFile().getName());
            return fileChooser.getSelectedFile();
        }
        this.setVisible(false);
        return null;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        fileChooser = new javax.swing.JFileChooser();
        //fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        fileChooser.setFileFilter(new FileFilter() {

        	   public String getDescription() {
        	       return "JAR Files (*.jar)";
        	   }

        	   public boolean accept(File f) {
        	       if (f.isDirectory()) {
        	           return true;
        	       } else {
        	           String filename = f.getName().toLowerCase();
        	           return filename.endsWith(".jar") || filename.endsWith(".jar") ;
        	       }
        	   }
        	});
        
        //fileChooser.setBackground(new java.awt.Color(51, 51, 51));
        //fileChooser.setForeground(new java.awt.Color(51, 51, 51));

        fileChooser.setBackground(Colors.headerDark);
        fileChooser.setForeground(Colors.headerDark);
        
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(fileChooser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(fileChooser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void setDarkMode() {
        UIManager.put("Panel.background", Colors.backgroundDark);

        UIManager.put("Button.background", Colors.headerDark);
        UIManager.put("Button.foreground", new java.awt.Color(187, 134, 252));
        UIManager.put("Button.border", javax.swing.BorderFactory.createCompoundBorder(
                javax.swing.BorderFactory.createLineBorder(new java.awt.Color(187, 134, 252), 1),
                javax.swing.BorderFactory.createLineBorder(Colors.headerDark, 5)));

        UIManager.put("ComboBox.background", Colors.headerDark);
        UIManager.put("ComboBox.foreground", new java.awt.Color(204, 204, 204));

        UIManager.put("Label.background", Colors.contentDark);
        UIManager.put("Label.foreground", new java.awt.Color(204, 204, 204));
        UIManager.put("TextField.background", Colors.contentDark);
        UIManager.put("TextField.foreground", new java.awt.Color(204, 204, 204));
        UIManager.put("ToolBar.background", Colors.backgroundDark);

        UIManager.put("Viewport.background", Colors.panelDark);
        UIManager.put("ScrollPane.background", Colors.panelDark);
        UIManager.put("List.background", Colors.panelDark);
        UIManager.put("List.foreground", Color.white);

        SwingUtilities.updateComponentTreeUI(this);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JFileChooser fileChooser;
    // End of variables declaration//GEN-END:variables

}
