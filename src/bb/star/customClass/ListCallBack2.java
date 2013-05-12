package bb.star.customClass;

import java.util.Vector;

import net.rim.device.api.system.Bitmap;
import net.rim.device.api.system.Display;
import net.rim.device.api.ui.Color;
import net.rim.device.api.ui.Font;
import net.rim.device.api.ui.Graphics;
import net.rim.device.api.ui.component.ListField;
import net.rim.device.api.ui.component.ListFieldCallback;
import bb.star.constant.Constants;

public class ListCallBack2 implements ListFieldCallback{
	private Vector listElements = new Vector();
	Bitmap image;
	
	Bitmap imagee =Bitmap.getBitmapResource(Constants.listBitmapArray2[0]);

	public void drawListRow(ListField list, Graphics g, int index, int y, int w) {

		Font font = g.getFont();
		int xPos = 30;
		int yPos = y + (list.getRowHeight() - imagee.getHeight()) / 2;
		int yPosTxt = y + (list.getRowHeight() - font.getHeight()) / 2;
		
		if (g.isDrawingStyleSet(Graphics.DRAWSTYLE_FOCUS)) {
			g.setBackgroundColor(0x00DC1F26);	
			g.clear();						
		} else {
			g.setColor(Color.WHITE);
		}

//		for (int i = 0; i < Constants.listBitmapArray2.length; i++) {
//			image =Bitmap.getBitmapResource(Constants.listBitmapArray2[index]);
//			g.drawBitmap(5, yPos+3, image.getWidth(), image.getHeight(), image, 0, 0);
//		}	
		
//		image =Bitmap.getBitmapResource(Constants.urlRec[index]);
		
		image = Constants.subResponseImageArray[index];
		g.drawBitmap(5, yPos+3, image.getWidth(), image.getHeight(), image, 0, 0);

		xPos = xPos + image.getWidth();
		String text = (String) listElements.elementAt(index);
		g.drawText(text, xPos, yPosTxt+3);
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
