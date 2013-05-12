package bb.star.customClass;

import java.util.Vector;

import net.rim.device.api.system.Bitmap;
import net.rim.device.api.system.Display;
import net.rim.device.api.ui.Color;
import net.rim.device.api.ui.Font;
import net.rim.device.api.ui.Graphics;
import net.rim.device.api.ui.XYRect;
import net.rim.device.api.ui.component.ListField;
import net.rim.device.api.ui.component.ListFieldCallback;
import bb.star.constant.Constants;
import bb.star.customFields.BitMapListElement;

public class BitmapListCallBack implements ListFieldCallback {

	private static int SPACING = 5;
	private Vector listElements = new Vector();
	private ListField listField;

	public BitmapListCallBack(ListField listField) {
		this.listField = listField;
	}

	public void drawListRow(ListField list, Graphics g, int index, int y, int width) {

		XYRect clippingRect = g.getClippingRect();
		int rowX = clippingRect.x;

		if (g.isDrawingStyleSet(Graphics.DRAWSTYLE_FOCUS)) {
			g.setBackgroundColor(0x00DC1F26);	
			g.clear();						
		} else {
			g.setColor(Color.WHITE);
		}
		
		g.fillRect(clippingRect.x, y, width, listField.getRowHeight());

		BitMapListElement bitMapListElement = (BitMapListElement) listElements.elementAt(index);
		String text = bitMapListElement.getTitle();
		String imagepath = bitMapListElement.getBitmap();

		Bitmap image = null;

		int textStartPoint = 0;

		if (imagepath != null) {

			image = Bitmap.getBitmapResource(imagepath);

			XYRect bitmapXYrect = new XYRect(rowX + SPACING, y + (listField.getRowHeight() - image.getHeight()) / 2,
					image.getWidth(), image.getHeight());

			g.drawBitmap(bitmapXYrect, image, 0, 0);

			textStartPoint = clippingRect.x + image.getWidth() + 2 * SPACING;

		}

		if (text != null) {

			Font font = g.getFont();

			g.setFont(font);

			if (g.isDrawingStyleSet(Graphics.DRAWSTYLE_FOCUS)) {
				g.setColor(Constants.HOMELIST_SCREEN_FOCUSFONTCOLOR);
			} else {
				g.setColor(Constants.HOMELIST_SCREEN_FONTCOLOR);
			}

			g.drawText(text, textStartPoint, y + (listField.getRowHeight() - font.getHeight()) / 2, Graphics.TOP
					| Graphics.LEFT, width);
		}

		int x = clippingRect.x;
		int height = listField.getRowHeight();

		g.drawRect(x, y, width, height);

	}

	public Object get(ListField list, int index) {
		return listElements.elementAt(index);
	}

	public int indexOfList(ListField list, String prefix, int string) {
		// return listElements.indexOf(prefix, string);
		return -1;
	}

	public int getPreferredWidth(ListField list) {
		return Display.getWidth();
	}

	public void addElement(Object object) {
		listElements.addElement(object);
	}

}
