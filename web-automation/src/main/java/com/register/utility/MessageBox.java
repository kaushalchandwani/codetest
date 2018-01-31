/**
 * @author Kaushal Chandwani
 *
 */
package com.register.utility;

import javax.swing.JDialog;
import javax.swing.JOptionPane;

public class MessageBox 
{
	public static void printMessage(String message, final int milliseconds) 
	{
		 JOptionPane opt = new JOptionPane(message, JOptionPane.INFORMATION_MESSAGE, JOptionPane.DEFAULT_OPTION, null, new Object[]{}); // no buttons
		  final JDialog dlg = opt.createDialog("");
		  new Thread(new Runnable()
		        {
		          public void run()
		          {
		            try
		            {
		              Thread.sleep(milliseconds);
		              dlg.dispose();

		            }
		            catch ( Throwable th )
		            {
		              
		            }
		          }
		        }).start();
		  dlg.setVisible(true);
	}
}
