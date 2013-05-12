package bb.star.customClass;

import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.FieldChangeListener;
import net.rim.device.api.ui.Manager;
import net.rim.device.api.ui.component.ButtonField;
import net.rim.device.api.ui.component.LabelField;
import net.rim.device.api.ui.container.PopupScreen;
import net.rim.device.api.ui.container.VerticalFieldManager;
import bb.star.constant.Constants;

public class CustomTransperentDialog extends PopupScreen {

	private LabelField msgTextMessage;
	private ButtonField ok, okExit;

	public CustomTransperentDialog(String msg) {

		super(new VerticalFieldManager(), Field.FOCUSABLE);
		
		this.setBorder(Constants.transRoundedBorder);	

		VerticalFieldManager vfm = new VerticalFieldManager(Manager.VERTICAL_SCROLL);

		msgTextMessage = new LabelField(msg, Field.FIELD_HCENTER | Field.NON_FOCUSABLE);	

		okExit = new ButtonField("OK", Field.FIELD_HCENTER);
		okExit.setChangeListener(new FieldChangeListener() {
			public void fieldChanged(Field field, int context) {
				if (field == okExit) {
					System.exit(0);
				}
			}
		});	
		
		ok = new ButtonField("CANCEL", Field.FIELD_HCENTER);
		ok.setChangeListener(new FieldChangeListener() {
			public void fieldChanged(Field field, int context) {
				if (field == ok) {
					close();
				}
			}
		});

		vfm.add(msgTextMessage);
		vfm.add(new LabelField());		
		vfm.add(okExit);
		vfm.add(ok);
		
		add(vfm);
	}
}
