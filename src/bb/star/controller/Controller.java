package bb.star.controller;

import net.rim.device.api.ui.Screen;
import net.rim.device.api.ui.UiApplication;
import bb.star.connectionManager.Communicator;
import bb.star.constant.Constants;
import bb.star.customClass.CustomDialogScreen;
import bb.star.customClass.PopupSpinnerScreen;
import bb.star.parser.ParserHandler;
import bb.star.startup.SplashScreen;

public class Controller extends UiApplication {

	String response;
	private byte[] postdata = null;

	public Controller() {
		showScreen(new SplashScreen());
		invokeLater(runnable);
	}

	Runnable runnable = new Runnable() {
		public void run() {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			Controller.showScreen(new PopupSpinnerScreen("Please Wait..."));
			Thread threadd = new Thread() {
				public void run() {
					try {
						if (Constants.LOCAL) {
							ParserHandler.INSTANCE.parseMainResponse("/main.xml");
						} else {
							response = new Communicator(Constants.mainXML_URL, postdata).communicate();
							ParserHandler.INSTANCE.parseMainResponse(response);
						}
					} catch (final Exception e) {
						UiApplication.getUiApplication().invokeLater(new Runnable() {
							public void run() {
								UiApplication ui = UiApplication.getUiApplication();
								int screenCount = ui.getScreenCount();
								for (int i = 0; i < screenCount; i++) {
									Screen activeScreen = ui.getActiveScreen();
									if (activeScreen instanceof SplashScreen) {
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
			threadd.start();
		}
	};

	public static void main(String[] args) {
		Controller controller = new Controller();
		controller.enterEventDispatcher();
	}

	// public static void showScreen(Screen screen) {
	// if (screen == null) {
	// return;
	// }
	// synchronized (getEventLock()) {
	// getUiApplication().pushScreen(screen);
	// }
	// }

	public static void showScreen(Screen screen) {
		synchronized (UiApplication.getEventLock()) {
			Screen activeScreen = UiApplication.getUiApplication().getActiveScreen();
			if (activeScreen != null) {
				Class screenClass = activeScreen.getClass();
				if (screen.getClass().equals(screenClass)) {
					UiApplication.getUiApplication().popScreen(activeScreen);
				}
			}
			UiApplication.getUiApplication().pushScreen(screen);
		}
	}
}
