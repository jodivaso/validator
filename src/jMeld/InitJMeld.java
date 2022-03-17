package jMeld;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import org.jmeld.ui.JMeldPanel;
import org.jmeld.ui.util.ImageUtil;
import org.jmeld.ui.util.LookAndFeelManager;
import org.jmeld.util.prefs.WindowPreference;

public class InitJMeld{

	public void initJMeld(String fichero, String fichero_descargado) {
	       List<String> arg = new ArrayList<String>();
	       arg.add(fichero);
	       arg.add(fichero_descargado);
	       
	        	   JFrame frame;

	               LookAndFeelManager.getInstance().install();

	               frame = new JFrame("JMeld");
	               JMeldPanel jmeldPanel = new JMeldPanel();
	               frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	               frame.add(jmeldPanel);
	               frame.setIconImage(ImageUtil.getImageIcon("jmeld-small").getImage());
	               new WindowPreference(frame.getTitle(), frame);
	               //addWindowListener(jmeldPanel.getWindowListener());
	               frame.setVisible(true);
	               frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
	               frame.toFront();

	               jmeldPanel.openComparison(arg);
	}

}
