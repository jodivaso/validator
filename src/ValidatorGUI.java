import java.awt.Dimension;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.Toolkit;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;

import javax.swing.JFrame;

import utils.Utils;

public class ValidatorGUI extends JFrame implements ComponentListener, WindowListener,  KeyEventDispatcher{
	
	
	static Validator theApplet;
	
	public ValidatorGUI () {
		File c = new File ("configure.txt");
		if (c.isFile())
		{						
			theApplet = new Validator(Utils.readConfigureFile());
		}
		else { //Default configuration
			theApplet = new Validator();
		}
        theApplet.init();   // invoke the applet's init() method
        theApplet.start();  // starts the applet

        // Create a window (JFrame) and make applet the content pane.
         this.setTitle("Validator of the requirements");
         //this.setExtendedState(this.getExtendedState() | JFrame.MAXIMIZED_BOTH);
       Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
       dim.setSize(dim.getWidth()*0.9,dim.getHeight()*0.9);
         this.setPreferredSize(dim);
         this.setContentPane(theApplet);
         this.addComponentListener(this);
         //Dimension dim=this.getSize();
         theApplet.setWidth((int)dim.getWidth());
         theApplet.setHeight((int)dim.getHeight());
         //this.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
         this.pack();              // Arrange the components.
         this.setVisible(true);    // Make the window visible.
         this.addWindowListener(this);
         KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
         manager.addKeyEventDispatcher(this);
         
		
	}

	@Override
	public void componentHidden(ComponentEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void componentMoved(ComponentEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void componentResized(ComponentEvent arg0) {
		// TODO Auto-generated method stub
		
		Dimension dim=this.getSize();
   	     theApplet.setWidth((int)dim.getWidth());
   	     theApplet.setHeight((int)dim.getHeight());
		 theApplet.draw();
		 theApplet.repaint();
//		 System.out.println("width:"+(int)dim.getWidth());
//	     System.out.println("width:"+(int)dim.getHeight());
	     this.setVisible(true);
		
	}

	@Override
	public void componentShown(ComponentEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosed(WindowEvent e) {
		// TODO Auto-generated method stub

		 
		
		
		
		
	}

	@Override
	public void windowClosing(WindowEvent e) {
		// TODO Auto-generated method stub
		theApplet.deleteFolder();
		//System.out.println("Cerrando Jframe");
		System.exit(0);
		
		
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	
	

	@Override
	public boolean dispatchKeyEvent(KeyEvent e) {
		// TODO Auto-generated method stub
if (e.getKeyCode() == KeyEvent.VK_F5) {
			
			System.out.println("F5");
				
				theApplet.startAnalysis();
				 theApplet.analize();

		}
	
		return false;
	}

}
