package bb.star.customClass;

import java.util.Vector;

import net.rim.device.api.system.Display;
import net.rim.device.api.ui.Color;
import net.rim.device.api.ui.Font;
import net.rim.device.api.ui.Graphics;
import net.rim.device.api.ui.component.ListField;
import net.rim.device.api.ui.component.ListFieldCallback;

public class SimpleListCallBack implements ListFieldCallback {

	private Vector listElements = new Vector();

	public void drawListRow(ListField list, Graphics g, int index, int y, int w) {

		Font font = g.getFont();
		int xPos = 30;
		int yPosTxt = y + (list.getRowHeight() - font.getHeight()) / 2;
		
		if (g.isDrawingStyleSet(Graphics.DRAWSTYLE_FOCUS)) {
			g.setBackgroundColor(0x00DC1F26);	
			g.clear();						
		} else {
			g.setColor(Color.WHITE);
		}

		String text = (String) listElements.elementAt(index);
		g.drawText(text, xPos, yPosTxt + 3);
	}

	public Object get(ListField list, int index) {
		return listElements.elementAt(index);
	}

	public int getPreferredWidth(ListField list) {
		return Display.getWidth();
	}

	public void insert(String toInsert, int index) {
		listElements.insertElementAt(toInsert, index);
	}

	public void erase() {
		listElements.removeAllElements();
	}

	public int indexOfList(ListField listField, String prefix, int start) {
		return listElements.indexOf(listField);
	}
}
