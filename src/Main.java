import gui.SecundomerForm;

import javax.swing.*;


/**
 * Created by Mykhailo on 18.09.2016.
 */
public class Main {
    public static void main(String[] args) {

        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            // If Nimbus is not available, you can set the GUI to another look and feel.
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                SecundomerForm secundomerForm = new SecundomerForm();
                secundomerForm.setVisible(true);
            }

        });
    }
}
