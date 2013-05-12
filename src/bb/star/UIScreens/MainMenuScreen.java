package bb.star.UIScreens;

import net.rim.device.api.system.Bitmap;
import net.rim.device.api.system.Display;
import net.rim.device.api.ui.Manager;
import net.rim.device.api.ui.component.BitmapField;
import net.rim.device.api.ui.container.VerticalFieldManager;
import bb.star.constant.Constants;
import bb.star.controller.Controller;
import bb.star.controller.MainScreenClass;
import bb.star.customClass.BitmapButtonField;
import bb.star.customClass.CustomTransperentDialog;
import bb.star.customClass.PopupSpinnerScreen;
import bb.star.customFields.Utils;
import bb.star.parser.ParserHandler;

public class MainMenuScreen extends MainScreenClass {

	public MainMenuScreen() {
		bf = new BitmapField(mainMenuBitmap);
		titleImage.add(bf);

		verticalScroll.add(new MediaControlStyleField());
	}

	public boolean onClose() {
		Controller.showScreen(new CustomTransperentDialog("Do you want to EXIT?"));
		return true;
	}
}

class MediaControlStyleField extends VerticalFieldManager {
	MediaControlStyleField() {
		super(Manager.FIELD_HCENTER);

		for (int i = 0; i < Constants.titles.length; i++) {
			
			if (Constants.titles[i].equalsIgnoreCase("Meals")) {

				Bitmap mealsBitmap = Utils.resizeBitmap(Bitmap.getBitmapResource("meals.png"), Display.getWidth(),
						Display.getHeight(), false);
				Bitmap mealsBitmapFoc = Utils.resizeBitmap(Bitmap.getBitmapResource("mealsonclick.png"), Display
						.getWidth(), Display.getHeight(), false);

				add(new BitmapButtonField(mealsBitmap, mealsBitmapFoc) {
					protected boolean navigationClick(int status, int time) {
						Controller.showScreen(new PopupSpinnerScreen("Please Wait..."));
						Thread threadd = new Thread() {
							public void run() {
								ParserHandler.INSTANCE.parseMeals(0);
							}
						};
						threadd.start();
						return true;
					}
				});
			} else if (Constants.titles[i].equalsIgnoreCase("Festival")) {

				Bitmap festivalBitmap = Utils.resizeBitmap(Bitmap.getBitmapResource("festivals.png"), Display
						.getWidth(), Display.getHeight(), false);
				Bitmap festivalBitmapFoc = Utils.resizeBitmap(Bitmap.getBitmapResource("festivalonclick.png"), Display
						.getWidth(), Display.getHeight(), false);

				add(new BitmapButtonField(festivalBitmap, festivalBitmapFoc) {
					protected boolean navigationClick(int status, int time) {
						Controller.showScreen(new PopupSpinnerScreen("Wait..."));
						Thread threadd = new Thread() {
							public void run() {
								ParserHandler.INSTANCE.parseFestival(1);
							}
						};
						threadd.start();
						return true;
					}
				});
			}

			else if (Constants.titles[i].equalsIgnoreCase("Cuisine")) {

				Bitmap cuisineBitmap = Utils.resizeBitmap(Bitmap.getBitmapResource("cuisine.png"), Display.getWidth(),
						Display.getHeight(), false);
				Bitmap cuisineBitmapFoc = Utils.resizeBitmap(Bitmap.getBitmapResource("cuisineonclick.png"), Display
						.getWidth(), Display.getHeight(), false);
				add(new BitmapButtonField(cuisineBitmap, cuisineBitmapFoc) {
					protected boolean navigationClick(int status, int time) {
						Controller.showScreen(new PopupSpinnerScreen("Wait..."));
						Thread threadd = new Thread() {
							public void run() {
								ParserHandler.INSTANCE.parseCuisine(2);
							}
						};
						threadd.start();
						return true;
					}
				});
			}else if (Constants.titles[i].equalsIgnoreCase("Region")) {
				
				Bitmap regionBitmap = Utils.resizeBitmap(Bitmap.getBitmapResource("region.png"), Display.getWidth(),
						Display.getHeight(), false);
				Bitmap regionBitmapFoc = Utils.resizeBitmap(Bitmap.getBitmapResource("regiononclick.png"), Display
						.getWidth(), Display.getHeight(), false);
				add(new BitmapButtonField(regionBitmap, regionBitmapFoc) {
					protected boolean navigationClick(int status, int time) {
						Controller.showScreen(new PopupSpinnerScreen("Wait..."));
						Thread threadd = new Thread() {
							public void run() {
								ParserHandler.INSTANCE.parseRegion(3);
							}
						};
						threadd.start();
						return true;
					}
				});
			} else if (Constants.titles[i].equalsIgnoreCase("Celebrity Reviewed")) {

				Bitmap celebrityreviewedBitmap = Utils.resizeBitmap(Bitmap.getBitmapResource("celebrityreviewed.png"),
						Display.getWidth(), Display.getHeight(), false);
				Bitmap celebrityreviewedBitmapFoc = Utils.resizeBitmap(Bitmap
						.getBitmapResource("celebrityreviewedonclick.png"), Display.getWidth(), Display.getHeight(),
						false);
				add(new BitmapButtonField(celebrityreviewedBitmap, celebrityreviewedBitmapFoc) {
					protected boolean navigationClick(int status, int time) {
						Controller.showScreen(new PopupSpinnerScreen("Wait..."));
						Thread threadd = new Thread() {
							public void run() {
								ParserHandler.INSTANCE.parseCelebrity(4);
							}
						};
						threadd.start();
						return true;
					}
				});
			}
		}
	}
}
