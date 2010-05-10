package assemblerSim;

import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

public class ResizeListener extends ComponentAdapter implements
		ComponentListener 
{
	
	GUIFrame parent;

	public ResizeListener(GUIFrame nparent) 
	{
		parent = nparent;
	}
	
	public void componentResized(ComponentEvent e) 
	{
        parent.updateSize();            
    }

}
