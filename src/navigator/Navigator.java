package navigator;


import org.eclipse.swt.*;
import org.eclipse.swt.browser.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;

public class Navigator {

	static int BUTTONS_PER_ROW = 1;

	public static void initNavigator(String url, String post, String [] headers, String nombreFichero) {
		
	Display display = new Display();
	Shell shell = new Shell(display);
	org.eclipse.swt.layout.GridLayout gl = new org.eclipse.swt.layout.GridLayout(BUTTONS_PER_ROW, true);
	shell.setLayout(gl);

	final Browser browser;
	try {
		browser = new Browser(shell, SWT.ON_TOP);
	} catch (SWTError es) {
		System.out.println("Could not instantiate Browser: " + es.getMessage());
		display.dispose();
		return;
	}
	browser.setUrl(url,post, headers);
	
	GridData data = new GridData(GridData.FILL_BOTH);
	data.horizontalSpan = BUTTONS_PER_ROW;
	browser.setLayoutData(data);
	
	shell.setSize(1000, 1000);
	shell.setText("Errors when validating " + nombreFichero);
	shell.open();
	while (!shell.isDisposed()) {
		if (!display.readAndDispatch())
			display.sleep();
	}
	display.dispose();
}
	}