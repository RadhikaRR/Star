package bb.star.customClass;

import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.FieldChangeListener;
import net.rim.device.api.ui.Manager;
import net.rim.device.api.ui.component.ButtonField;
import net.rim.device.api.ui.component.LabelField;
import net.rim.device.api.ui.container.HorizontalFieldManager;
import net.rim.device.api.ui.container.PopupScreen;
import net.rim.device.api.ui.container.VerticalFieldManager;
import bb.star.constant.Constants;

public class CustomDialogScreen extends PopupScreen {

	private LabelField msgTextMessage;
	private ButtonField ok;

	public CustomDialogScreen(String msg) {

		super(new VerticalFieldManager(), Field.FOCUSABLE);

		this.setBorder(Constants.transRoundedBorder);

		VerticalFieldManager vfm = new VerticalFieldManager(Manager.VERTICAL_SCROLL);

		msgTextMessage = new LabelField(msg, Field.FIELD_HCENTER | Field.NON_FOCUSABLE);

		HorizontalFieldManager hfm = new HorizontalFieldManager(FIELD_HCENTER);

		ok = new ButtonField("OK", Field.FIELD_HCENTER);
		ok.setChangeListener(new FieldChangeListener() {
			public void fieldChanged(Field field, int context) {
				if (field == ok) {	
//					UiApplication.getUiApplication().invokeLater(new Runnable() {
//						public void run() {
//							Screen screen = UiApplication.getUiApplication().getActiveScreen();
//							if (screen instanceof PopupSpinnerScreen) {
//								UiApplication.getUiApplication().popScreen(screen);
//							}
//						}
//					});
					close();
				}
			}
		});

		vfm.add(msgTextMessage);
		vfm.add(new LabelField());
		hfm.add(ok);
		vfm.add(hfm);

		add(vfm);
	}
}
