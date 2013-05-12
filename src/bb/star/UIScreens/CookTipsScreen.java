package bb.star.UIScreens;

import net.rim.device.api.system.Display;
import net.rim.device.api.ui.component.BitmapField;
import net.rim.device.api.ui.component.ListField;
import net.rim.device.api.ui.container.VerticalFieldManager;
import bb.star.constant.Constants;
import bb.star.controller.Controller;
import bb.star.controller.MainScreenClass;
import bb.star.customClass.SimpleListCallBack;

public class CookTipsScreen extends MainScreenClass {

	private ListField _lf;

	public CookTipsScreen() {
		super();
				
		bf = new BitmapField(CookingBitmap);
		titleImage.add(bf);
		
		delete(cookBtnLayout);
		delete(ingrBtnLayout);

		showList();
		VerticalFieldManager vfm = new VerticalFieldManager();
		vfm.add(_lf);
		verticalScroll.add(vfm);
	}

	int index = 0;

	public void showList() {
		_lf = new ListField() {
			protected boolean navigationClick(int status, int time) {
				index = _lf.getSelectedIndex();
				Controller.showScreen(new cookDescScreen(index));
				return true;
			}
		};
		SimpleListCallBack _callback = new SimpleListCallBack();
		_lf.setCallback(_callback);

		for (int i = 0; i < Constants.cookTips.length; i++) {
			_lf.insert(i);
			_callback.insert(Constants.cookTips[i], i);
		}
		if (Display.getWidth() == 320) {
			_lf.setRowHeight(50);
		} else {
			_lf.setRowHeight(80);
		}
		_lf.setPadding(2, 0, 2, 0);
	}
}
