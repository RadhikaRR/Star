package bb.star.parser;

import java.util.Vector;

import net.rim.device.api.system.Bitmap;
import net.rim.device.api.ui.Screen;
import net.rim.device.api.ui.UiApplication;
import bb.star.UIScreens.CookTipsScreen;
import bb.star.UIScreens.FinalScreen;
import bb.star.UIScreens.GetStartedScreen;
import bb.star.UIScreens.IngredientsScreen;
import bb.star.UIScreens.MainMenuScreen;
import bb.star.UIScreens.SubIngredientsScreen;
import bb.star.UIScreens.WhatsCookingScreen;
import bb.star.connectionManager.Downloader;
import bb.star.constant.Constants;
import bb.star.controller.Controller;
import bb.star.customClass.CustomDialogScreen;
import bb.star.customClass.PopupSpinnerScreen;

public class ParserHandler {
	public static ParserHandler INSTANCE = new ParserHandler();

	int entities = 0;

	public void parseMainResponse(String xmlPath) throws Exception {
		try {
			XMLParser.INSTANCE.parseMain(xmlPath);
			System.out.println("");
			entities = Constants.categories.size();
			Constants.titles = new String[entities];

			for (int i = 0; i < entities; i++) {
				Category category = (Category) Constants.categories.elementAt(i);
				Constants.titles[i] = category.getTitle();
			}
			UiApplication.getUiApplication().invokeLater(new Runnable() {
				public void run() {
					UiApplication ui = UiApplication.getUiApplication();
					int screenCount = ui.getScreenCount();
					for (int i = 0; i < screenCount; i++) {
						Screen activeScreen = ui.getActiveScreen();
						ui.popScreen(activeScreen);
					}
					Controller.showScreen(new MainMenuScreen());
				}
			});
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	public void parseMeals(final int count) {
		try {
			System.out.println("");
			Category category = (Category) Constants.categories.elementAt(count);
			Constants.titles[count] = category.getTitle();

			Constants.title = new String[entities];
			Constants.url = new String[entities];
			Constants.link = new String[entities];

			for (int i = 0; i < entities; i++) {
				final Vector v = (Vector) category.getSubcategories();
				final Vector v2 = (Vector) v.elementAt(i);

				Constants.title[i] = v2.elementAt(0).toString();
				Constants.url[i] = v2.elementAt(1).toString();
				Constants.link[i] = v2.elementAt(2).toString();
				System.out.println("");
			}

			if (Constants.mealsImageArray.length == 0 || Constants.mealsImageArray == null) {

				byte[] stream = null;
				Constants.mealsImageArray = new Bitmap[Constants.url.length];
				try {
					for (int i = 0; i < Constants.url.length; i++) {
						stream = new Downloader().downloadResourceAsStream(Constants.url[i]);
						Bitmap image = Bitmap.createBitmapFromBytes(stream, 0, stream.length, 1);
						Constants.mealsImageArray[i] = image;
					}
				} catch (final Exception e) {
					UiApplication.getUiApplication().invokeLater(new Runnable() {
						public void run() {
							UiApplication ui = UiApplication.getUiApplication();
							int screenCount = ui.getScreenCount();
							for (int i = 0; i < screenCount; i++) {
								Screen activeScreen = ui.getActiveScreen();
								if (activeScreen instanceof MainMenuScreen) {
									break;
								} else {
									ui.popScreen(activeScreen);
								}
							}
							Controller.showScreen(new CustomDialogScreen("Unable to fetch images" + e.getMessage()));
						}
					});
				}
			}
			System.out.println("");

			if (Constants.mealsImageArray != null) {
				UiApplication.getUiApplication().invokeLater(new Runnable() {
					public void run() {
						UiApplication ui = UiApplication.getUiApplication();
						int screenCount = ui.getScreenCount();
						for (int i = 0; i < screenCount; i++) {
							Screen activeScreen = ui.getActiveScreen();
							System.out.println("");
							if (activeScreen instanceof MainMenuScreen) {
								break;
							} else {
								ui.popScreen(activeScreen);
							}
						}
						Controller.showScreen(new WhatsCookingScreen(count));
					}
				});
			}
		} catch (Exception e) {
			UiApplication.getUiApplication().invokeLater(new Runnable() {
				public void run() {
					UiApplication ui = UiApplication.getUiApplication();
					int screenCount = ui.getScreenCount();
					for (int i = 0; i < screenCount; i++) {
						Screen activeScreen = ui.getActiveScreen();
						System.out.println("");
						if (activeScreen instanceof MainMenuScreen) {
							break;
						} else {
							ui.popScreen(activeScreen);
						}
					}
					Controller.showScreen(new CustomDialogScreen("Sorry! Details are not available for Meals"));
				}
			});
		}
	}

	public void parseFestival(final int count) {
		try {
			System.out.println("");
			Category category = (Category) Constants.categories.elementAt(count);
			Constants.titles[count] = category.getTitle();

			Constants.title = new String[entities];
			Constants.url = new String[entities];
			Constants.link = new String[entities];

			for (int i = 0; i < entities; i++) {
				final Vector v = (Vector) category.getSubcategories();
				final Vector v2 = (Vector) v.elementAt(i);

				Constants.title[i] = v2.elementAt(0).toString();
				Constants.url[i] = v2.elementAt(1).toString();
				Constants.link[i] = v2.elementAt(2).toString();
				System.out.println("");
			}
			if (Constants.festivalImageArray == null || Constants.festivalImageArray.length == 0) {

				byte[] stream = null;
				Constants.festivalImageArray = new Bitmap[Constants.url.length];
				try {
					for (int i = 0; i < Constants.url.length; i++) {
						stream = new Downloader().downloadResourceAsStream(Constants.url[i]);
						Bitmap image = Bitmap.createBitmapFromBytes(stream, 0, stream.length, 1);
						Constants.festivalImageArray[i] = image;
					}
				} catch (final Exception e) {
					UiApplication.getUiApplication().invokeLater(new Runnable() {
						public void run() {
							UiApplication ui = UiApplication.getUiApplication();
							int screenCount = ui.getScreenCount();
							for (int i = 0; i < screenCount; i++) {
								Screen activeScreen = ui.getActiveScreen();
								if (activeScreen instanceof MainMenuScreen) {
									break;
								} else {
									ui.popScreen(activeScreen);
								}
							}
							Controller.showScreen(new CustomDialogScreen("Unable to fetch images" + e.getMessage()));
						}
					});
				}
			}
			System.out.println("");
			if (Constants.festivalImageArray != null) {
				UiApplication.getUiApplication().invokeLater(new Runnable() {
					public void run() {
						UiApplication ui = UiApplication.getUiApplication();
						int screenCount = ui.getScreenCount();
						for (int i = 0; i < screenCount; i++) {
							Screen activeScreen = ui.getActiveScreen();
							System.out.println("");
							if (activeScreen instanceof MainMenuScreen) {
								break;
							} else {
								ui.popScreen(activeScreen);
							}
						}
						Controller.showScreen(new WhatsCookingScreen(count));
					}
				});
			}
		} catch (Exception e) {
			UiApplication.getUiApplication().invokeLater(new Runnable() {
				public void run() {
					UiApplication ui = UiApplication.getUiApplication();
					int screenCount = ui.getScreenCount();
					for (int i = 0; i < screenCount; i++) {
						Screen activeScreen = ui.getActiveScreen();
						System.out.println("");
						if (activeScreen instanceof MainMenuScreen) {
							break;
						} else {
							ui.popScreen(activeScreen);
						}
					}
					Controller.showScreen(new CustomDialogScreen("Sorry! Details are not available for Festival"));
				}
			});
		}
	}

	public void parseCuisine(final int count) {
		try {
			System.out.println("");
			Category category = (Category) Constants.categories.elementAt(count);
			Constants.titles[count] = category.getTitle();

			Constants.title = new String[entities];
			Constants.url = new String[entities];
			Constants.link = new String[entities];

			for (int i = 0; i < entities; i++) {
				final Vector v = (Vector) category.getSubcategories();
				final Vector v2 = (Vector) v.elementAt(i);

				Constants.title[i] = v2.elementAt(0).toString();
				Constants.url[i] = v2.elementAt(1).toString();
				Constants.link[i] = v2.elementAt(2).toString();
				System.out.println("");
			}

			if (Constants.cuisineImageArray == null || Constants.cuisineImageArray.length == 0) {
				byte[] stream = null;
				Constants.cuisineImageArray = new Bitmap[Constants.url.length];
				try {
					for (int i = 0; i < Constants.url.length; i++) {
						stream = new Downloader().downloadResourceAsStream(Constants.url[i]);
						Bitmap image = Bitmap.createBitmapFromBytes(stream, 0, stream.length, 1);
						Constants.cuisineImageArray[i] = image;
					}
				} catch (final Exception e) {
					UiApplication.getUiApplication().invokeLater(new Runnable() {
						public void run() {
							UiApplication ui = UiApplication.getUiApplication();
							int screenCount = ui.getScreenCount();
							for (int i = 0; i < screenCount; i++) {
								Screen activeScreen = ui.getActiveScreen();
								if (activeScreen instanceof MainMenuScreen) {
									break;
								} else {
									ui.popScreen(activeScreen);
								}
							}
							Controller.showScreen(new CustomDialogScreen("Unable to fetch images" + e.getMessage()));
						}
					});
				}
			}
			System.out.println("");

			if (Constants.cuisineImageArray != null) {
				UiApplication.getUiApplication().invokeLater(new Runnable() {
					public void run() {
						UiApplication ui = UiApplication.getUiApplication();
						int screenCount = ui.getScreenCount();
						for (int i = 0; i < screenCount; i++) {
							Screen activeScreen = ui.getActiveScreen();
							System.out.println("");
							if (activeScreen instanceof MainMenuScreen) {
								break;
							} else {
								ui.popScreen(activeScreen);
							}
						}
						Controller.showScreen(new WhatsCookingScreen(count));
					}
				});
			}
		} catch (Exception e) {
			UiApplication.getUiApplication().invokeLater(new Runnable() {
				public void run() {
					UiApplication ui = UiApplication.getUiApplication();
					int screenCount = ui.getScreenCount();
					for (int i = 0; i < screenCount; i++) {
						Screen activeScreen = ui.getActiveScreen();
						System.out.println("");
						if (activeScreen instanceof MainMenuScreen) {
							break;
						} else {
							ui.popScreen(activeScreen);
						}
					}
					Controller.showScreen(new CustomDialogScreen("Sorry! Details are not available for Cuisine"));
				}
			});
		}
	}

	public void parseRegion(final int count) {
		try {
			System.out.println("");
			Category category = (Category) Constants.categories.elementAt(count);
			Constants.titles[count] = category.getTitle();

			Constants.title = new String[entities];
			Constants.url = new String[entities];
			Constants.link = new String[entities];

			for (int i = 0; i < entities; i++) {
				final Vector v = (Vector) category.getSubcategories();
				final Vector v2 = (Vector) v.elementAt(i);

				Constants.title[i] = v2.elementAt(0).toString();
				Constants.url[i] = v2.elementAt(1).toString();
				Constants.link[i] = v2.elementAt(2).toString();
				System.out.println("");
			}
			if (Constants.regionImageArray == null || Constants.regionImageArray.length == 0) {

				byte[] stream = null;
				Constants.regionImageArray = new Bitmap[Constants.url.length];
				try {
					for (int i = 0; i < Constants.url.length; i++) {
						stream = new Downloader().downloadResourceAsStream(Constants.url[i]);
						Bitmap image = Bitmap.createBitmapFromBytes(stream, 0, stream.length, 1);
						Constants.regionImageArray[i] = image;
					}
				} catch (final Exception e) {
					UiApplication.getUiApplication().invokeLater(new Runnable() {
						public void run() {
							UiApplication ui = UiApplication.getUiApplication();
							int screenCount = ui.getScreenCount();
							for (int i = 0; i < screenCount; i++) {
								Screen activeScreen = ui.getActiveScreen();
								if (activeScreen instanceof MainMenuScreen) {
									break;
								} else {
									ui.popScreen(activeScreen);
								}
							}
							Controller.showScreen(new CustomDialogScreen("Unable to fetch images" + e.getMessage()));
						}
					});
				}
			}
			System.out.println("");

			if (Constants.regionImageArray != null) {
				UiApplication.getUiApplication().invokeLater(new Runnable() {
					public void run() {
						UiApplication ui = UiApplication.getUiApplication();
						int screenCount = ui.getScreenCount();
						for (int i = 0; i < screenCount; i++) {
							Screen activeScreen = ui.getActiveScreen();
							System.out.println("");
							if (activeScreen instanceof MainMenuScreen) {
								break;
							} else {
								ui.popScreen(activeScreen);
							}
						}
						Controller.showScreen(new WhatsCookingScreen(count));
					}
				});
			}

		} catch (Exception e) {
			UiApplication.getUiApplication().invokeLater(new Runnable() {
				public void run() {
					UiApplication ui = UiApplication.getUiApplication();
					int screenCount = ui.getScreenCount();
					for (int i = 0; i < screenCount; i++) {
						Screen activeScreen = ui.getActiveScreen();
						System.out.println("");
						if (activeScreen instanceof MainMenuScreen) {
							break;
						} else {
							ui.popScreen(activeScreen);
						}
					}
					Controller.showScreen(new CustomDialogScreen("Sorry! Details are not available for Region"));
				}
			});
		}
	}

	public void parseCelebrity(final int count) {
		try {
			System.out.println("");
			Category category = (Category) Constants.categories.elementAt(count);
			Constants.titles[count] = category.getTitle();

			Constants.title = new String[entities];
			Constants.url = new String[entities];
			Constants.link = new String[entities];

			for (int i = 0; i < entities; i++) {
				final Vector v = (Vector) category.getSubcategories();
				final Vector v2 = (Vector) v.elementAt(i);

				Constants.title[i] = v2.elementAt(0).toString();
				Constants.url[i] = v2.elementAt(1).toString();
				Constants.link[i] = v2.elementAt(2).toString();
				System.out.println("");
			}
			if (Constants.celebrityImageArray == null || Constants.celebrityImageArray.length == 0) {

				byte[] stream = null;
				Constants.celebrityImageArray = new Bitmap[Constants.url.length];
				try {
					for (int i = 0; i < Constants.url.length; i++) {
						stream = new Downloader().downloadResourceAsStream(Constants.url[i]);
						Bitmap image = Bitmap.createBitmapFromBytes(stream, 0, stream.length, 1);
						Constants.celebrityImageArray[i] = image;
					}
				} catch (final Exception e) {
					UiApplication.getUiApplication().invokeLater(new Runnable() {
						public void run() {
							UiApplication ui = UiApplication.getUiApplication();
							int screenCount = ui.getScreenCount();
							for (int i = 0; i < screenCount; i++) {
								Screen activeScreen = ui.getActiveScreen();
								if (activeScreen instanceof MainMenuScreen) {
									break;
								} else {
									ui.popScreen(activeScreen);
								}
							}
							Controller.showScreen(new CustomDialogScreen("Unable to fetch images" + e.getMessage()));
						}
					});
				}
			}
			System.out.println("");

			if (Constants.celebrityImageArray != null) {
				UiApplication.getUiApplication().invokeLater(new Runnable() {
					public void run() {
						UiApplication ui = UiApplication.getUiApplication();
						int screenCount = ui.getScreenCount();
						for (int i = 0; i < screenCount; i++) {
							Screen activeScreen = ui.getActiveScreen();
							System.out.println("");
							if (activeScreen instanceof MainMenuScreen) {
								break;
							} else {
								ui.popScreen(activeScreen);
							}
						}
						Controller.showScreen(new WhatsCookingScreen(count));
					}
				});
			}
		} catch (Exception e) {
			UiApplication.getUiApplication().invokeLater(new Runnable() {
				public void run() {
					UiApplication ui = UiApplication.getUiApplication();
					int screenCount = ui.getScreenCount();
					for (int i = 0; i < screenCount; i++) {
						Screen activeScreen = ui.getActiveScreen();
						System.out.println("");
						if (activeScreen instanceof MainMenuScreen) {
							break;
						} else {
							ui.popScreen(activeScreen);
						}
					}
					Controller.showScreen(new CustomDialogScreen("Sorry! Details are not available for Celebrity"));
				}
			});
		}
	}

	public void parseSubResponse(String xmlPath) throws Exception {
		try {
			Vector v = XMLParser.INSTANCE.parseSub(xmlPath);

			if (v.size() != 0) {
				Constants.titleRec = new String[v.size()];
				Constants.urlRec = new String[v.size()];
				Constants.linkRec = new String[v.size()];

				for (int j = 0; j < v.size(); j++) {
					Vector abc = (Vector) v.elementAt(j);
					Constants.titleRec[j] = abc.elementAt(0).toString();
					Constants.urlRec[j] = abc.elementAt(1).toString();
					Constants.linkRec[j] = abc.elementAt(2).toString();
				}

				if (Constants.subResponseImageArray == null || Constants.subResponseImageArray.length == 0) {

					byte[] stream = null;
					Constants.subResponseImageArray = new Bitmap[Constants.urlRec.length];
					try {
						for (int i = 0; i < Constants.urlRec.length; i++) {
							stream = new Downloader().downloadResourceAsStream(Constants.urlRec[i]);
							Bitmap image = Bitmap.createBitmapFromBytes(stream, 0, stream.length, 1);
							Constants.subResponseImageArray[i] = image;
						}
					} catch (final Exception e) {
						UiApplication.getUiApplication().invokeLater(new Runnable() {
							public void run() {
								UiApplication ui = UiApplication.getUiApplication();
								int screenCount = ui.getScreenCount();
								for (int i = 0; i < screenCount; i++) {
									Screen activeScreen = ui.getActiveScreen();
									if (activeScreen instanceof MainMenuScreen) {
										break;
									} else {
										ui.popScreen(activeScreen);
									}
								}
								Controller
										.showScreen(new CustomDialogScreen("Unable to fetch images" + e.getMessage()));
							}
						});
					}
				}
				if (Constants.subResponseImageArray != null) {

					UiApplication.getUiApplication().invokeLater(new Runnable() {
						public void run() {
							UiApplication ui = UiApplication.getUiApplication();
							int screenCount = ui.getScreenCount();
							for (int i = 0; i < screenCount; i++) {
								Screen activeScreen = ui.getActiveScreen();
								System.out.println("");
								if (activeScreen instanceof WhatsCookingScreen) {
									break;
								} else {
									ui.popScreen(activeScreen);
								}
							}
							Controller.showScreen(new GetStartedScreen());
						}
					});
				}
			}
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	public void parseRecipeResponse(String xmlPath, final int index) throws Exception {
		try {
			Vector v = XMLParser.INSTANCE.parseRecipes(xmlPath);

			Constants.shareURL = new String[v.size()];
			Constants.summary = new String[v.size()];
			Constants.ingerdients = new String[v.size()];
			Constants.procedure = new String[v.size()];

			for (int j = 0; j < v.size(); j++) {
				Vector abc = (Vector) v.elementAt(j);
				Constants.shareURL[j] = abc.elementAt(0).toString();
				Constants.summary[j] = abc.elementAt(1).toString();
				Constants.ingerdients[j] = abc.elementAt(2).toString();
				Constants.procedure[j] = abc.elementAt(3).toString();
			}

			UiApplication.getUiApplication().invokeLater(new Runnable() {
				public void run() {
					UiApplication ui = UiApplication.getUiApplication();
					int screenCount = ui.getScreenCount();
					for (int i = 0; i < screenCount; i++) {
						Screen activeScreen = ui.getActiveScreen();
						System.out.println("");
						if (activeScreen instanceof GetStartedScreen) {
							break;
						} else {
							ui.popScreen(activeScreen);
						}
					}
					Controller.showScreen(new FinalScreen(index));
				}
			});

		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	public void parseCookTipsResponse(String xmlPath) throws Exception {
		try {
			Vector v = XMLParser.INSTANCE.parseCookTips(xmlPath);

			Constants.cookTips = new String[v.size()];
			Constants.cookDesc = new String[v.size()];

			for (int j = 0; j < v.size(); j++) {
				Vector abc = (Vector) v.elementAt(j);
				Constants.cookTips[j] = abc.elementAt(0).toString();
				Constants.cookDesc[j] = abc.elementAt(1).toString();
			}
			System.out.println("");
			UiApplication.getUiApplication().invokeLater(new Runnable() {
				public void run() {
					UiApplication ui = UiApplication.getUiApplication();
					int screenCount = ui.getScreenCount();
					for (int i = 0; i < screenCount; i++) {
						Screen activeScreen = ui.getActiveScreen();
						if (activeScreen instanceof MainMenuScreen) {
							break;
						} else {
							ui.popScreen(activeScreen);
						}
					}
					Controller.showScreen(new CookTipsScreen());
				}
			});
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	public void parseIngredientsResponse(String xmlPath) throws Exception {
		try {
			Vector v = XMLParser.INSTANCE.parseIngredientsTips(xmlPath);

			Constants.ingredientsTitle = new String[v.size()];
			Constants.ingredientsLink = new String[v.size()];

			for (int j = 0; j < v.size(); j++) {
				Vector abc = (Vector) v.elementAt(j);
				Constants.ingredientsTitle[j] = abc.elementAt(0).toString();
				Constants.ingredientsLink[j] = abc.elementAt(1).toString();
			}

			System.out.println("");
			UiApplication.getUiApplication().invokeLater(new Runnable() {
				public void run() {
					Screen screen = UiApplication.getUiApplication().getActiveScreen();
					if (screen instanceof PopupSpinnerScreen) {
						UiApplication.getUiApplication().popScreen(screen);
					}
					Controller.showScreen(new IngredientsScreen());
				}
			});
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	Bitmap imageFinal = null;

	public void parseSubIngredientsResponse(String xmlPath, final int index) throws Exception {

		try {
			Vector v = XMLParser.INSTANCE.parseSubIngredients(xmlPath);

			Constants.subIngredientsLink = new String[v.size()];
			Constants.subIngredientsHindiName = new String[v.size()];

			for (int j = 0; j < v.size(); j++) {
				Vector abc = (Vector) v.elementAt(j);
				Constants.subIngredientsLink[j] = abc.elementAt(0).toString();
				Constants.subIngredientsHindiName[j] = abc.elementAt(1).toString();
			}

			byte[] stream = null;
			try {
				stream = new Downloader().downloadResourceAsStream(Constants.subIngredientsLink[index]);
				imageFinal = Bitmap.createBitmapFromBytes(stream, 0, stream.length, 1);
			} catch (final Exception e) {
				UiApplication.getUiApplication().invokeLater(new Runnable() {
					public void run() {
						UiApplication ui = UiApplication.getUiApplication();
						int screenCount = ui.getScreenCount();
						for (int i = 0; i < screenCount; i++) {
							Screen activeScreen = ui.getActiveScreen();
							if (activeScreen instanceof MainMenuScreen) {
								break;
							} else {
								ui.popScreen(activeScreen);
							}
						}
						Controller.showScreen(new CustomDialogScreen("Unable to fetch images" + e.getMessage()));
					}
				});
			}

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
					Controller.showScreen(new SubIngredientsScreen(index, imageFinal));
				}
			});
		} catch (final Exception e) {
			throw new Exception(e.getMessage());
		}
	}
}
