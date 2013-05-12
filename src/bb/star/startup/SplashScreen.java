package bb.star.startup;

import net.rim.device.api.system.Bitmap;
import net.rim.device.api.system.Display;
import net.rim.device.api.ui.component.BitmapField;
import net.rim.device.api.ui.container.MainScreen;
import net.rim.device.api.ui.decor.BackgroundFactory;
import bb.star.constant.Constants;
import bb.star.customFields.Utils;

public class SplashScreen extends MainScreen {
	
	private Bitmap resizebitmap;
	private BitmapField bitmapField;

	public SplashScreen() {
		show();
	}

	private void show() {
		this.getMainManager().setBackground(
				BackgroundFactory.createLinearGradientBackground(0x00FFFFFF, 0x00FFFFFF, 0x0064AFF2, 0x0064AFF2));

		if (Display.getWidth() == 360) {
			bitmapField = new BitmapField(Constants.bitmap360);
		} else {
			resizebitmap = Utils.resizeBitmap(Constants.bitmap480, Display.getWidth(), Display.getHeight(), false);
			bitmapField = new BitmapField(resizebitmap);
		}
		add(bitmapField);
	}

	public boolean onClose() {
		System.exit(0);
		return true;
	}
}
