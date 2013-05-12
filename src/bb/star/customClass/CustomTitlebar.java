package bb.star.customClass;

import net.rim.device.api.system.Bitmap;
import net.rim.device.api.ui.DrawStyle;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.Font;
import net.rim.device.api.ui.Graphics;
import bb.star.constant.Constants;

public class CustomTitlebar extends Field {
	private Bitmap image;	

	private int foregroundColor;
	private int backgroundColor;

	public CustomTitlebar(Bitmap image, long style) {
		super(style);
		this.image = image;
	}

	public CustomTitlebar(long style) {
		super(style);
	}

	protected void layout(int width, int height) {
		if ((getStyle() & Field.USE_ALL_WIDTH) == Field.USE_ALL_WIDTH) {
			setExtent(width, Math.min(height, getPreferredHeight()));
		} else {
			setExtent(getPreferredWidth(), getPreferredHeight());
		}
	}

	protected void paint(Graphics graphics) {	
		if (image != null) {
			int imageY = (getHeight() - image.getHeight()) / 2;
			graphics.drawBitmap(0, imageY, image.getWidth(), image.getHeight(),
					image, 0, 0);			
		}
	}

	public int getPreferredHeight() {
		return Math.max(getFont().getHeight(), image.getHeight());
	}

	public int getPreferredWidth() {
//		int width = getFont().getAdvance(title);
//		if (image != null) {
//			width += image.getWidth();
//		}
		return image.getWidth();
	}
}
