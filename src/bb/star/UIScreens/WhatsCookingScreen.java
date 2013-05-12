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
import bb.star.customClass.ListCallBack;
import bb.star.customClass.PopupSpinnerScreen;
import bb.star.parser.ParserHandler;

public class WhatsCookingScreen extends MainScreenClass {
	private ListField _lf;
	private byte[] postdata = null;
	String response;	
	int count = 0;

	public WhatsCookingScreen(int count) {
		super();
		
		this.count = count;

		bf = new BitmapField(whatsCookingBitmap);
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
										ParserHandler.INSTANCE.parseSubResponse("/" + Constants.link[index]);
									} else {
										response = new Communicator("/" + Constants.link[index], postdata)
												.communicate();
										ParserHandler.INSTANCE.parseSubResponse(response);
									}
								} catch (final Exception e) {
									System.out.println("2222----------------------------------" + e.getMessage());
									UiApplication.getUiApplication().invokeLater(new Runnable() {
										public void run() {
											UiApplication ui = UiApplication.getUiApplication();
											int screenCount = ui.getScreenCount();
											for (int i = 0; i < screenCount; i++) {
												Screen activeScreen = ui.getActiveScreen();
												if (activeScreen instanceof WhatsCookingScreen) {
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
		ListCallBack _callback = new ListCallBack(count);
		_lf.setCallback(_callback);

		for (int i = 0; i < Constants.title.length; i++) {
			_lf.insert(i);
			_callback.insert(Constants.title[i], i);
		}
		if (Display.getWidth() == 320) {
			_lf.setRowHeight(50);
		} else {
			_lf.setRowHeight(80);
		}
		_lf.setPadding(2, 0, 2, 0);
	}
}
