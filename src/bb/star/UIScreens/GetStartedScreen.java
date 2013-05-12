package bb.star.UIScreens;

import net.rim.device.api.system.Display;
import net.rim.device.api.ui.Screen;
import net.rim.device.api.ui.UiApplication;
import net.rim.device.api.ui.component.BitmapField;
import net.rim.device.api.ui.component.ListField;
import net.rim.device.api.ui.container.VerticalFieldManager;
import bb.star.connectionManager.Communicator;
import bb.star.constant.Constants;
import bb.star.controller.Controller;
import bb.star.controller.MainScreenClass;
import bb.star.customClass.CustomDialogScreen;
import bb.star.customClass.ListCallBack2;
import bb.star.customClass.PopupSpinnerScreen;
import bb.star.parser.ParserHandler;

public class GetStartedScreen extends MainScreenClass {
	private ListField _lf;
	String response;
	private byte[] postdata = null;

	public GetStartedScreen() {
		super();

		bf = new BitmapField(getStartedBitmap);
		titleImage.add(bf);

		delete(cookBtnLayout);
		showList();
		VerticalFieldManager vfm = new VerticalFieldManager();
		vfm.add(_lf);
		verticalScroll.add(vfm);
	}

	public void showList() {

		_lf = new ListField() {
			protected boolean navigationClick(int status, int time) {
				final int index = _lf.getSelectedIndex();
				UiApplication.getUiApplication().invokeLater(new Runnable() {
					public void run() {
						Controller.showScreen(new PopupSpinnerScreen("Wait..."));
						Thread thread = new Thread() {
							public void run() {
								try {
									if (Constants.LOCAL) {
										ParserHandler.INSTANCE.parseRecipeResponse("/" + Constants.linkRec[index],index);
									} else {
										response = new Communicator("/" + Constants.linkRec[index], postdata)
												.communicate();
										ParserHandler.INSTANCE.parseRecipeResponse(response,index);
									}
								} catch (final Exception e) {
									UiApplication.getUiApplication().invokeLater(new Runnable() {
										public void run() {
											UiApplication ui = UiApplication.getUiApplication();
											int screenCount = ui.getScreenCount();
											for (int i = 0; i < screenCount; i++) {
												Screen activeScreen = ui.getActiveScreen();
												if (activeScreen instanceof GetStartedScreen) {
													break;
												} else {
													ui.popScreen(activeScreen);
												}
											}
											Controller.showScreen(new CustomDialogScreen(e.getMessage()));
										}
									});
								}
							}
						};
						thread.start();
					}
				});
				return true;
			}
		};
		ListCallBack2 _callback = new ListCallBack2();
		_lf.setCallback(_callback);

		for (int i = 0; i < Constants.titleRec.length; i++) {
			_lf.insert(i);
			_callback.insert(Constants.titleRec[i], i);
		}
		if (Display.getWidth() == 320) {
			_lf.setRowHeight(50);
		} else {
			_lf.setRowHeight(80);
		}
		_lf.setPadding(2, 0, 2, 0);
	}
}
