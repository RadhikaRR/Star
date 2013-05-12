package bb.star.UIScreens;

import net.rim.device.api.system.Bitmap;
import net.rim.device.api.system.Display;
import net.rim.device.api.ui.Color;
import net.rim.device.api.ui.DrawStyle;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.Manager;
import net.rim.device.api.ui.component.BitmapField;
import net.rim.device.api.ui.container.HorizontalFieldManager;
import net.rim.device.api.ui.container.VerticalFieldManager;
import bb.star.constant.Constants;
import bb.star.controller.MainScreenClass;
import bb.star.customFields.BorderLabelFieldImpl;

public class SubIngredientsScreen extends MainScreenClass {

	BitmapField bitmap;
	BorderLabelFieldImpl borderLabelFieldImpl;

	public SubIngredientsScreen(int index,Bitmap image) {
		super();
		super.deleteAll();

		VerticalFieldManager vfm = new VerticalFieldManager(Manager.VERTICAL_SCROLL);
		bitmap = new BitmapField(image);
		vfm.add(new CustomImageHori());

		HorizontalFieldManager labelHorizontalFieldManager = new HorizontalFieldManager();
		borderLabelFieldImpl = new BorderLabelFieldImpl(Constants.ingredientsTitle[index], Field.FOCUSABLE | USE_ALL_WIDTH | DrawStyle.HCENTER);
		borderLabelFieldImpl.setFont(Constants.fontBold);
		borderLabelFieldImpl.setFontColor(Color.WHITE);
		borderLabelFieldImpl.setBgColor(0x00E91E24);
		borderLabelFieldImpl.setMargin(1, 3, 10, 3);
		labelHorizontalFieldManager.add(borderLabelFieldImpl);
		vfm.add(labelHorizontalFieldManager);
		
		HorizontalFieldManager ingredientsHorizontalFieldManager = new HorizontalFieldManager();
		borderLabelFieldImpl = new BorderLabelFieldImpl(Constants.subIngredientsHindiName[0], USE_ALL_WIDTH | DrawStyle.HCENTER);
		borderLabelFieldImpl.setFont(Constants.font);
		borderLabelFieldImpl.setFontColor(Color.WHITE);
		ingredientsHorizontalFieldManager.add(borderLabelFieldImpl);
		vfm.add(ingredientsHorizontalFieldManager);
		
		add(vfm);
	}

	class CustomImageHori extends Manager {
		protected CustomImageHori() {
			super(Manager.NO_HORIZONTAL_SCROLL | Manager.NO_VERTICAL_SCROLL);
			add(bitmap);
		}

		protected void sublayout(int width, int height) {
			layoutChild(bitmap, Display.getWidth(), Display.getHeight());
			setPositionChild(bitmap, (Display.getWidth() - bitmap.getWidth()) / 2, 3);

			setExtent(Display.getWidth(), bitmap.getHeight());
		}
	}
}
