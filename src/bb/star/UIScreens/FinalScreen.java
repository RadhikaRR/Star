package bb.star.UIScreens;

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

public class FinalScreen extends MainScreenClass {

	BitmapField bitmap;
	BorderLabelFieldImpl borderLabelFieldImpl;

	public FinalScreen(int index) {
		super();
		
		delete(titleImage);
		
		delete(cookBtnLayout);

		VerticalFieldManager vfm = new VerticalFieldManager(Manager.VERTICAL_SCROLL);
		bitmap = new BitmapField(Constants.subResponseImageArray[index]);
		
		vfm.add(new CustomImageHori());

		HorizontalFieldManager labelHorizontalFieldManager = new HorizontalFieldManager();
		borderLabelFieldImpl = new BorderLabelFieldImpl("", Field.FOCUSABLE | USE_ALL_WIDTH | DrawStyle.HCENTER);
		borderLabelFieldImpl.setFont(Constants.fontBold);
		borderLabelFieldImpl.setFontColor(Color.WHITE);
		borderLabelFieldImpl.setBgColor(0x00E91E24);
		borderLabelFieldImpl.setMargin(1, 3, 10, 3);
		borderLabelFieldImpl.setText(Constants.summary[0]);
		labelHorizontalFieldManager.add(borderLabelFieldImpl);
		vfm.add(labelHorizontalFieldManager);
		

		HorizontalFieldManager ingredientsLabelHorizontalFieldManager = new HorizontalFieldManager(Field.FIELD_HCENTER);
		borderLabelFieldImpl = new BorderLabelFieldImpl("", Field.FOCUSABLE);
		borderLabelFieldImpl.setFont(Constants.fontBold);
		borderLabelFieldImpl.setFontColor(Color.WHITE);
		borderLabelFieldImpl.setBgColor(0x00E91E24);
		borderLabelFieldImpl.setText("INGREDIENTS");
		ingredientsLabelHorizontalFieldManager.add(borderLabelFieldImpl);
		vfm.add(ingredientsLabelHorizontalFieldManager);
		
		
		HorizontalFieldManager ingredientsHorizontalFieldManager = new HorizontalFieldManager();
		borderLabelFieldImpl = new BorderLabelFieldImpl("", USE_ALL_WIDTH | DrawStyle.HCENTER);
		borderLabelFieldImpl.setFont(Constants.fontBold);
		borderLabelFieldImpl.setFontColor(Color.WHITE);
		String ingredients = Constants.ingerdients[0].toString();
		borderLabelFieldImpl.setText(ingredients);
		ingredientsHorizontalFieldManager.add(borderLabelFieldImpl);
		vfm.add(ingredientsHorizontalFieldManager);
		
		
		HorizontalFieldManager procedureLabelHorizontalFieldManager = new HorizontalFieldManager(Field.FIELD_HCENTER);	
		procedureLabelHorizontalFieldManager.setMargin(10, 0, 0, 0);
		borderLabelFieldImpl = new BorderLabelFieldImpl("", Field.FOCUSABLE );
		borderLabelFieldImpl.setFont(Constants.fontBold);
		borderLabelFieldImpl.setFontColor(Color.WHITE);
		borderLabelFieldImpl.setBgColor(0x00E91E24);
		borderLabelFieldImpl.setText("PROCEDURE");
		procedureLabelHorizontalFieldManager.add(borderLabelFieldImpl);
		vfm.add(procedureLabelHorizontalFieldManager);
		
		
		HorizontalFieldManager provrdureHorizontalFieldManager = new HorizontalFieldManager();
		borderLabelFieldImpl = new BorderLabelFieldImpl("", USE_ALL_WIDTH | DrawStyle.HCENTER);
		borderLabelFieldImpl.setFont(Constants.fontBold);
		borderLabelFieldImpl.setFontColor(Color.WHITE);
		String procedure = Constants.procedure[0].toString();
		borderLabelFieldImpl.setText(procedure);
		provrdureHorizontalFieldManager.add(borderLabelFieldImpl);
		vfm.add(provrdureHorizontalFieldManager);		

		verticalScroll.add(vfm);
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
