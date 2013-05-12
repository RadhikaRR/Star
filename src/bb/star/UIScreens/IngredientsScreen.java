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
import bb.star.customClass.PopupSpinnerScreen;
import bb.star.customClass.SimpleListCallBack;
import bb.star.parser.ParserHandler;
import bb.star.startup.SplashScreen;

public class IngredientsScreen extends MainScreenClass {
	private ListField _lf;
	String response;
	private byte[] postdata = null;

	public IngredientsScreen() {
		super();

		bf = new BitmapField(finalClassBitmap);
		titleImage.add(bf);

		delete(cookBtnLayout);
		delete(ingrBtnLayout);

		showList();
		VerticalFieldManager vfm = new VerticalFieldManager(VERTICAL_SCROLL | VERTICAL_SCROLLBAR);
		vfm.add(_lf);
		verticalScroll.add(vfm);
	}

	int index = 0;

	public void showList() {
		_lf = new ListField() {
			protected boolean navigationClick(int status, int time) {
				index = _lf.getSelectedIndex();
				UiApplication.getUiApplication().invokeLater(new Runnable() {
					public void run() {

						Controller.showScreen(new PopupSpinnerScreen("Wait..."));
						Thread thread = new Thread() {
							public void run() {
								try {
									if (Constants.LOCAL) {
										ParserHandler.INSTANCE.parseSubIngredientsResponse("/"+ Constants.ingredientsLink[index], index);
									} else {
										response = new Communicator("/" + Constants.ingredientsLink[index], postdata)
												.communicate();
										ParserHandler.INSTANCE.parseSubIngredientsResponse(response, index);
									}
								} catch (final Exception e) {
									UiApplication.getUiApplication().invokeLater(new Runnable() {
										public void run() {
											UiApplication ui = UiApplication.getUiApplication();
											int screenCount = ui.getScreenCount();
											for (int i = 0; i < screenCount; i++) {
												Screen activeScreen = ui.getActiveScreen();
												if (activeScreen instanceof IngredientsScreen) {
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
		SimpleListCallBack _callback = new SimpleListCallBack();
		_lf.setCallback(_callback);

		for (int i = 0; i < Constants.ingredientsTitle.length; i++) {
			_lf.insert(i);
			_callback.insert(Constants.ingredientsTitle[i], i);
		}
		if (Display.getWidth() == 320) {
			_lf.setRowHeight(50);
		} else {
			_lf.setRowHeight(80);
		}
		_lf.setPadding(2, 0, 2, 0);
	}
}
