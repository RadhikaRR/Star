package bb.star.controller;

import net.rim.device.api.system.Bitmap;
import net.rim.device.api.system.Display;
import net.rim.device.api.ui.DrawStyle;
import net.rim.device.api.ui.Graphics;
import net.rim.device.api.ui.Manager;
import net.rim.device.api.ui.Screen;
import net.rim.device.api.ui.UiApplication;
import net.rim.device.api.ui.component.BitmapField;
import net.rim.device.api.ui.component.Dialog;
import net.rim.device.api.ui.component.LabelField;
import net.rim.device.api.ui.component.Status;
import net.rim.device.api.ui.container.HorizontalFieldManager;
import net.rim.device.api.ui.container.MainScreen;
import net.rim.device.api.ui.container.VerticalFieldManager;
import net.rim.device.api.ui.decor.BackgroundFactory;
import bb.star.UIScreens.FinalScreen;
import bb.star.UIScreens.MainMenuScreen;
import bb.star.connectionManager.Communicator;
import bb.star.constant.Constants;
import bb.star.customClass.BitmapButtonField;
import bb.star.customClass.CustomDialogScreen;
import bb.star.customClass.PopupSpinnerScreen;
import bb.star.customFields.LabelFieldImpl;
import bb.star.customFields.Utils;
import bb.star.parser.ParserHandler;

import com.blackberry.facebook.ApplicationSettings;
import com.blackberry.facebook.Facebook;
import com.blackberry.facebook.FacebookException;
import com.blackberry.facebook.inf.User;

public class MainScreenClass extends MainScreen {

	protected LabelFieldImpl WelcomeNameLbl;
	protected LabelFieldImpl DisplayTimeLbl;
	protected HorizontalFieldManager horizontalWelcomeLabel;
	protected VerticalFieldManager vfmm;
	private HorizontalFieldManager subManager;

	public Bitmap mainMenuBitmap, whatsCookingBitmap, getStartedBitmap, finalClassBitmap, CookingBitmap;

	public int deviceWidth = Display.getWidth();
	public int deviceHeight = Display.getHeight();

	BitmapButtonField btnFBB;
	BitmapButtonField btnTWW;
	BitmapButtonField btnHome;

	public BitmapButtonField btnCook;
	public BitmapButtonField btnIngr, btnIngrr;

	BitmapField bitImageFB = new BitmapField(Bitmap.getBitmapResource("fshare.png"));
	BitmapField bitImageTW = new BitmapField(Bitmap.getBitmapResource("twittershare.png"));

	public BitmapField bf;

	protected VerticalFieldManager verticalScroll;

	Bitmap resizebitmapBG;
	Bitmap resizebitmap;

	public CustomButtonLayoutHori cookBtnLayout;
	public CustomIngredientsButton ingrBtnLayout;
	public customTitleImage titleImage;

	String response;
	private byte[] postdata = null;

	public MainScreenClass() {

		super(NO_VERTICAL_SCROLL | NO_VERTICAL_SCROLLBAR);

		if (deviceWidth == 480) {
			resizebitmap = Constants.bgBitmap480;
		} else if (deviceWidth == 320) {
			resizebitmap = Constants.bgBitmap320;
		} else {
			resizebitmap = Utils.resizeBitmap(Constants.bgBitmap240, Display.getWidth(), Display.getHeight(), false);
		}

		this.getMainManager().setBackground(BackgroundFactory.createBitmapBackground(resizebitmap));

		Bitmap mainMenuBit = Bitmap.getBitmapResource("mainmenu.png");
		mainMenuBitmap = Utils.resizeBitmap(mainMenuBit, Display.getWidth(), Display.getHeight(), false);
		Bitmap whatsCookingBit = Bitmap.getBitmapResource("whtscooking.png");
		whatsCookingBitmap = Utils.resizeBitmap(whatsCookingBit, Display.getWidth(), Display.getHeight(), false);
		Bitmap getStartedBit = Bitmap.getBitmapResource("getstarted.png");
		getStartedBitmap = Utils.resizeBitmap(getStartedBit, Display.getWidth(), Display.getHeight(), false);
		Bitmap finalClassBit = Bitmap.getBitmapResource("ingredientsglossary.png");
		finalClassBitmap = Utils.resizeBitmap(finalClassBit, Display.getWidth(), Display.getHeight(), false);
		Bitmap CookingTipsBit = Bitmap.getBitmapResource("cookingtipsheading.png");
		CookingBitmap = Utils.resizeBitmap(CookingTipsBit, Display.getWidth(), Display.getHeight(), false);

		resizebitmapBG = Utils
				.resizeBitmap(Constants._backgroundBitmap, Display.getWidth(), Display.getHeight(), false);

		VerticalFieldManager vfm = new VerticalFieldManager(Manager.NO_VERTICAL_SCROLL | Manager.NO_VERTICAL_SCROLLBAR
				| USE_ALL_WIDTH | FIELD_RIGHT) {
			public void paint(Graphics graphics) {
				graphics.clear();
				graphics.drawBitmap(0, 0, resizebitmapBG.getWidth(), resizebitmapBG.getHeight(), resizebitmapBG, 0, 0);
				super.paint(graphics);
			}
		};

		subManager = new HorizontalFieldManager(HorizontalFieldManager.FIELD_RIGHT | USE_ALL_WIDTH) {
			protected void sublayout(int maxWidth, int maxHeight) {
				super.sublayout(deviceWidth, deviceHeight);
				setExtent(deviceWidth, resizebitmapBG.getHeight());
			}
		};

		btnFBB = new BitmapButtonField(Constants.fbBitmap, Constants.fbFocBitmap) {
			protected boolean navigationClick(int status, int time) {
				try {
					String[] PERMISSIONS = Facebook.Permissions.USER_DATA_PERMISSIONS;
					ApplicationSettings as = new ApplicationSettings(Constants.NEXT_URL, Constants.APPLICATION_ID,
							Constants.APPLICATION_SECRET, PERMISSIONS);
					Facebook fb = Facebook.getInstance(as);

					User user = fb.getCurrentUser();

					user.publishStatus("Rahul's Test post from bb app");
					String publishPost = user
							.publishPost(
									"This is the message",
									"http://supportforums.blackberry.com/t5/Java-Development/Twitter-Facebook-integration-with-oauth/td-p/1275815",
									"http://115.108.53.237/sports.png", "Link name", "Link caption",
									"Link description", "link source");
					// String publishPost = user.publishPost("pMessage",
					// "pLink", "pPictureurl", "pName", "pCaption",
					// "pDescription", "pSource");

					add(new LabelField("publishPost:" + publishPost));

					String email = user.getEmail();

					add(new LabelField("Email:" + email));

					add(new LabelField("Test done"));

				} catch (FacebookException e) {
					synchronized (UiApplication.getEventLock()) {
						Dialog.alert("FacebookException:" + e.getMessage());
					}
					e.printStackTrace();
				}

				// Status.show("Fb clicked");

				// final ApplicationSettings as = new
				// ApplicationSettings(Constants.NEXT_URL,
				// Constants.APPLICATION_ID,
				// Constants.APPLICATION_SECRET, Constants.PERMISSIONS);
				// Facebook fb = Facebook.getInstance(as);
				// try {
				// User user = fb.getCurrentUser();
				// } catch (FacebookException e) {
				// // TODO Auto-generated catch block
				// e.printStackTrace();
				// }

				// UiApplication.getUiApplication().invokeLater(new Runnable() {
				// public void run() {
				// Browser.getDefaultSession().displayPage(Constants.NEXT_URL);
				// }
				// });
				// HttpConnection con = null;
				// System.out.println("");
				// try {
				// URLEncodedPostData endr = new URLEncodedPostData(null,
				// false);
				// endr.append("api_key", "203562003054515");
				//
				// // con = (HttpConnection)
				// // Connector.open("http://api.facebook.com/restserver.php");
				// con = (HttpConnection) javax.microedition.io.Connector
				// .open("http://api.facebook.com/restserver.php");
				// con.setRequestMethod(HttpConnection.POST);
				// con.setRequestProperty(HttpProtocolConstants.HEADER_CONTENT_TYPE,
				// HttpProtocolConstants.CONTENT_TYPE_APPLICATION_X_WWW_FORM_URLENCODED);
				// con.setRequestProperty(HttpProtocolConstants.HEADER_CONTENT_LENGTH,
				// String
				// .valueOf(endr.getBytes().length));
				//
				// OutputStream otput = con.openOutputStream();
				// otput.write(endr.getBytes());
				//
				// if (con.getResponseCode() == HttpConnection.HTTP_OK) {
				// System.out.println("1111-------------------------");
				// }
				// System.out.println("");
				// } catch (java.io.IOException e) {
				// System.out.println("");
				// } finally {
				// if (con != null) {
				// try {
				// con.close();
				// } catch (IOException e) {
				// }
				// }
				// }
				return true;
			}
		};

		btnTWW = new BitmapButtonField(Constants.twBitmap, Constants.twFocBitmap) {
			protected boolean navigationClick(int status, int time) {
				Status.show("Tw clicked");
				return true;
			}
		};

		btnHome = new BitmapButtonField(Constants.homeBitmap, Constants.homeFocBitmap) {
			protected boolean navigationClick(int status, int time) {
				Screen screen = UiApplication.getUiApplication().getActiveScreen();
				if (screen instanceof MainMenuScreen) {

				} else {
					Controller.showScreen(new MainMenuScreen());
				}
				return true;
			}
		};

		Bitmap btnCookRZ = Utils.resizeBitmap(Constants.cookBitmap, Display.getWidth(), Display.getHeight(), false);
		Bitmap btnCookRZFoc = Utils.resizeBitmap(Constants.cookFocBitmap, Display.getWidth(), Display.getHeight(),
				false);
		btnCook = new BitmapButtonField(btnCookRZ, btnCookRZFoc, DrawStyle.HCENTER) {
			protected boolean navigationClick(int status, int time) {
				Controller.showScreen(new PopupSpinnerScreen("Wait..."));
				Thread thread = new Thread() {
					public void run() {
						try {
							if (Constants.LOCAL) {
								ParserHandler.INSTANCE.parseCookTipsResponse("/cookingtips.xml");
							} else {
								response = new Communicator(Constants.CookTipsXML_URL, postdata).communicate();
								ParserHandler.INSTANCE.parseCookTipsResponse(response);
							}
						} catch (final Exception e) {
							System.out.println("2222----------------------------------" + e.getMessage());
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
									Controller.showScreen(new CustomDialogScreen(e.getMessage()));
								}
							});
						}
					}
				};
				thread.start();
				return true;
			}
		};

		Bitmap btnIngrrRZ = Utils.resizeBitmap(Constants.ingrBitmap, Display.getWidth(), Display.getHeight(), false);
		Bitmap btnIngrrRZFoc = Utils.resizeBitmap(Constants.ingrFocBitmap, Display.getWidth(), Display.getHeight(),
				false);
		btnIngrr = new BitmapButtonField(btnIngrrRZ, btnIngrrRZFoc, DrawStyle.HCENTER) {
			protected boolean navigationClick(int status, int time) {
				Controller.showScreen(new PopupSpinnerScreen("Wait..."));
				Thread thread = new Thread() {
					public void run() {
						try {
							if (Constants.LOCAL) {
								ParserHandler.INSTANCE.parseIngredientsResponse("/ingredients.xml");
							} else {
								response = new Communicator(Constants.IngredientsXML_URL, postdata).communicate();
								ParserHandler.INSTANCE.parseIngredientsResponse(response);
							}
						} catch (final Exception e) {
							System.out.println("2222----------------------------------" + e.getMessage());

							UiApplication.getUiApplication().invokeLater(new Runnable() {
								public void run() {
									Screen screen = UiApplication.getUiApplication().getActiveScreen().getScreenBelow();
									if (screen instanceof PopupSpinnerScreen) {
										UiApplication.getUiApplication().popScreen(screen);
									}
									Controller.showScreen(new CustomDialogScreen(e.getMessage()));
								}
							});
						}
					}
				};
				thread.start();
				return true;
			}
		};

		subManager.add(new CustomLayoutManager());

		vfm.add(subManager);

		setTitle(vfm);

		titleImage = new customTitleImage();
		add(titleImage);

		cookBtnLayout = new CustomButtonLayoutHori();
		add(cookBtnLayout);

		verticalScroll = new VerticalFieldManager(VERTICAL_SCROLL | VERTICAL_SCROLLBAR) {
			protected void sublayout(int maxWidth, int maxHeight) {
				super.sublayout(Display.getWidth(), Display.getHeight());
				Screen screen = UiApplication.getUiApplication().getActiveScreen();
				System.out.println("");
				if (screen instanceof MainMenuScreen) {
					if (deviceWidth == 320) {
						setExtent(Display.getWidth(), 142);
					} else if (deviceWidth == 360) {
						setExtent(Display.getWidth(), 367);
					} else if (deviceWidth == 640) {
						setExtent(Display.getWidth(), 293);
					} else {
						setExtent(Display.getWidth(), 215);
					}
				} else if (screen instanceof FinalScreen) {
					if (deviceWidth == 320) {
						setExtent(Display.getWidth(), 185);
					} else if (deviceWidth == 360) {
						setExtent(Display.getWidth(), 415);
					} else if (deviceWidth == 640) {
						setExtent(Display.getWidth(), 380);
					} else {
						setExtent(Display.getWidth(), 278);
					}
				} else {
					if (deviceWidth == 320) {
						setExtent(Display.getWidth(), 163);
					} else if (deviceWidth == 360) {
						setExtent(Display.getWidth(), 391);
					} else if (deviceWidth == 640) {
						setExtent(Display.getWidth(), 335);
					} else {
						setExtent(Display.getWidth(), 250);
					}
				}
			}
		};
		add(verticalScroll);

		ingrBtnLayout = new CustomIngredientsButton();
		add(ingrBtnLayout);
	}

	public class customTitleImage extends Manager {
		public customTitleImage() {
			super(Manager.NO_HORIZONTAL_SCROLL | Manager.NO_VERTICAL_SCROLL);
		}

		protected void sublayout(int width, int height) {
			layoutChild(bf, Display.getWidth(), Display.getHeight());
			setPositionChild(bf, 0, 0);
			setExtent(Display.getWidth(), bf.getHeight());
		}
	}

	class CustomButtonLayoutHori extends Manager {
		protected CustomButtonLayoutHori() {
			super(Manager.NO_HORIZONTAL_SCROLL | Manager.NO_VERTICAL_SCROLL);
			add(btnCook);
		}

		protected void sublayout(int width, int height) {
			layoutChild(btnCook, Display.getWidth(), Display.getHeight());
			setPositionChild(btnCook, 0, 0);
			setExtent(Display.getWidth(), btnCook.getHeight());
		}
	}

	class CustomLayoutManager extends Manager {
		protected CustomLayoutManager() {
			super(Manager.NO_HORIZONTAL_SCROLL | Manager.NO_VERTICAL_SCROLL);
			add(btnFBB);
			add(btnTWW);
			add(btnHome);
		}

		protected void sublayout(int width, int height) {

			if (deviceWidth == 320) {
				layoutChild(btnFBB, width, height);
				setPositionChild(btnFBB, Display.getWidth() - (Display.getWidth() / 4) - 20, 0);

				layoutChild(btnTWW, width, height);
				setPositionChild(btnTWW, (Display.getWidth() - (Display.getWidth() / 4)) + btnFBB.getWidth() - 20, 0);

				layoutChild(btnHome, width, height);
				setPositionChild(btnHome, (Display.getWidth() - (Display.getWidth() / 4)) + (btnFBB.getWidth() * 2)
						- 20, 0);

				setExtent(Display.getWidth(), resizebitmapBG.getHeight());
			} else if (deviceWidth == 360) {
				layoutChild(btnFBB, width, height);
				setPositionChild(btnFBB, Display.getWidth() - (Display.getWidth() / 4) - 5,
						(Display.getHeight() - Display.getHeight()) / 2);

				layoutChild(btnTWW, width, height);
				setPositionChild(btnTWW, (Display.getWidth() - (Display.getWidth() / 4)) + btnFBB.getWidth() - 5,
						(Display.getHeight() - Display.getHeight()) / 2);

				layoutChild(btnHome, width, height);
				setPositionChild(btnHome,
						(Display.getWidth() - (Display.getWidth() / 4)) + (btnFBB.getWidth() * 2) - 5, (Display
								.getHeight() - Display.getHeight()) / 2);

				setExtent(Display.getWidth(), resizebitmapBG.getHeight());
			} else if (deviceWidth == 640) {
				layoutChild(btnFBB, width, height);
				setPositionChild(btnFBB, Display.getWidth() - (Display.getWidth() / 4) + 40, 12);

				layoutChild(btnTWW, width, height);
				setPositionChild(btnTWW, (Display.getWidth() - (Display.getWidth() / 4)) + btnFBB.getWidth() + 45, 12);

				layoutChild(btnHome, width, height);
				setPositionChild(btnHome, (Display.getWidth() - (Display.getWidth() / 4)) + btnFBB.getWidth()
						+ btnHome.getWidth() + 50, 12);

				setExtent(Display.getWidth(), resizebitmapBG.getHeight());
			} else {
				layoutChild(btnFBB, width, height);
				setPositionChild(btnFBB, Display.getWidth() - (Display.getWidth() / 4) + 10, 9);

				layoutChild(btnTWW, width, height);
				setPositionChild(btnTWW, (Display.getWidth() - (Display.getWidth() / 4)) + btnFBB.getWidth() + 10, 9);

				layoutChild(btnHome, width, height);
				setPositionChild(btnHome, (Display.getWidth() - (Display.getWidth() / 4)) + btnFBB.getWidth() + 45, 9);

				setExtent(Display.getWidth(), resizebitmapBG.getHeight());
			}
		}
	}

	class CustomIngredientsButton extends Manager {
		protected CustomIngredientsButton() {
			super(Manager.NO_HORIZONTAL_SCROLL | Manager.NO_VERTICAL_SCROLL);
			add(btnIngrr);
		}

		protected void sublayout(int width, int height) {
			layoutChild(btnIngrr, Display.getWidth(), Display.getHeight());
			setPositionChild(btnIngrr, 0, 0);

			setExtent(Display.getWidth(), btnIngrr.getHeight());
		}
	}
}
