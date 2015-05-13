package gui.graphui.listener;

import gui.graphui.GSGraphicGraph;

import java.awt.event.KeyEvent;
import java.nio.charset.Charset;

import org.graphstream.ui.graphicGraph.GraphicGraph;
import org.graphstream.ui.view.View;
import org.graphstream.ui.view.util.ShortcutManager;

public class GSGraphicGraphKeyListener implements ShortcutManager{
	public static final int NONE = -1;	
	Integer key_pressed = null;
	private GSGraphicGraph gs;
	public GSGraphicGraphKeyListener(GSGraphicGraph gsGraphicGraph){
		this.gs = gsGraphicGraph;
	}
	
	public Integer getKeyPressed(){
		if(key_pressed == null){
			return NONE;
		}
		else return key_pressed; 
		
	}
	@Override
	public void keyPressed(KeyEvent arg0) {
		
		key_pressed = arg0.getKeyCode();
		System.out.println(key_pressed);
		switch(key_pressed){
			case KeyEvent.VK_DELETE:
				gs.removeSelectedElement();
				break;

		}
		
		
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		key_pressed = null;
		
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
