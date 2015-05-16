package gui.graphui.listener;

import gui.graphui.GSGraphicGraph;

import java.awt.event.KeyEvent;
import java.nio.charset.Charset;

import org.graphstream.ui.graphicGraph.GraphicGraph;
import org.graphstream.ui.view.View;
import org.graphstream.ui.view.util.ShortcutManager;

public class GSGraphicGraphKeyListener implements ShortcutManager{
	public static final int NONE = 0;	
	public static final int ADD_EDGES = 1;
	public static final int CTRL_PRESSED = 2;
	Integer key_pressed = null;
	private Integer modifier = null;
	private GSGraphicGraph gs;
	public GSGraphicGraphKeyListener(GSGraphicGraph gsGraphicGraph){
		this.gs = gsGraphicGraph;
	}
	
	public Integer getKeyPressed(){
		if(key_pressed == null){
			return NONE;
		}
		if(key_pressed == KeyEvent.VK_A && ((this.modifier & KeyEvent.CTRL_MASK) != 0 || (this.modifier & KeyEvent.META_MASK) != 0)){
			return ADD_EDGES;
		}
			
		if(key_pressed == KeyEvent.VK_CONTROL || key_pressed == KeyEvent.VK_META){
			return CTRL_PRESSED;
		}
		return key_pressed; 
		
	}
	@Override
	public void keyPressed(KeyEvent arg0) {
		
		key_pressed = arg0.getKeyCode();
		modifier = arg0.getModifiers();
		switch(key_pressed){
			case KeyEvent.VK_DELETE:
				gs.removeSelectedElement();
				break;
		}
		if(key_pressed == KeyEvent.VK_Z && ((this.modifier & KeyEvent.CTRL_MASK) != 0 || (this.modifier & KeyEvent.META_MASK) != 0)){
			this.gs.previous_step();
		}
		else if(key_pressed == KeyEvent.VK_Y && ((this.modifier & KeyEvent.CTRL_MASK) != 0 || (this.modifier & KeyEvent.META_MASK) != 0)){
			this.gs.next_step();
		}
		else if(key_pressed == KeyEvent.VK_R && ((this.modifier & KeyEvent.CTRL_MASK) != 0 || (this.modifier & KeyEvent.META_MASK) != 0)){
			this.gs.updateStyle();
		}
		
		
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		key_pressed = null;
		modifier = null;
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void init(GraphicGraph arg0, View arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void release() {
		// TODO Auto-generated method stub
		
	}

}
