package cpp.cs3560.assignment2;

import java.awt.EventQueue;
import java.util.ArrayList;

	public class Driver {
		/**
		 * Launch the application.
		 */
		public static void main(String[] args) {
			EventQueue.invokeLater(new Runnable() {
				public void run() {
					try {
						AdminControlPanel.getFrame().setVisible(true);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
			
			
		}
}
