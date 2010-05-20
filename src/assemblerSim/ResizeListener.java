package assemblerSim;

import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
/**
 * @author Marco "Don" Kaulea
 * Listens for resizingEvents and initiates the resizing of all components
 */
public class ResizeListener extends ComponentAdapter implements
		ComponentListener 
{
	
	GUIFrame parent;
	/**
	 * 
	 * @param nparent parent of this objects for callbacks
	 */
	public ResizeListener(GUIFrame nparent) 
	{
		parent = nparent;
	}
	
	/**
	 * on resizing initiate update of all sizes
	 */
	public void componentResized(ComponentEvent e) 
	{
        parent.updateSize();            
    }

}
