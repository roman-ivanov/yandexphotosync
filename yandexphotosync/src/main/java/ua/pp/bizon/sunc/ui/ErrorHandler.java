package ua.pp.bizon.sunc.ui;

import javax.swing.JOptionPane;

public class ErrorHandler {

   public void handle(Exception ex){
       JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
   }

}
