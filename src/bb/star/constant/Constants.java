package bb.star.constant;

import java.util.Vector;

import com.blackberry.facebook.Facebook;

import net.rim.device.api.system.Bitmap;
import net.rim.device.api.ui.Color;
import net.rim.device.api.ui.Font;
import net.rim.device.api.ui.Ui;
import net.rim.device.api.ui.XYEdges;
import net.rim.device.api.ui.decor.Border;
import net.rim.device.api.ui.decor.BorderFactory;

public class Constants {

	public static boolean LOCAL = true;
	
	public static Bitmap[] mealsImageArray;
	public static Bitmap[] festivalImageArray;
	public static Bitmap[] cuisineImageArray;
	public static Bitmap[] regionImageArray;
	public static Bitmap[] celebrityImageArray;
	
	public static Bitmap[] subResponseImageArray;

	public static String mainXML_URL = "http://192.168.2.51:8080/starxml";
	public static String CookTipsXML_URL = "http://192.168.2.51:8080/starxml";
	public static String IngredientsXML_URL = "http://192.168.2.51:8080/starxml";

	public static Bitmap _backgroundBitmap = Bitmap.getBitmapResource("header480x42.png");

	public static Bitmap transBorderBitmap = Bitmap.getBitmapResource("BlackTrans.png");
	public static XYEdges transPadding = new XYEdges(12, 12, 12, 12);
	public static Border transRoundedBorder = BorderFactory.createBitmapBorder(transPadding, transBorderBitmap);

	public static Bitmap spinnerBitmap = Bitmap.getBitmapResource("spinner.png");

	public static Bitmap roundedBorderBitmap = Bitmap.getBitmapResource("rounded-border.png");
	public static XYEdges roundedpadding = new XYEdges(12, 12, 12, 12);
	public static Border roundedBorder = BorderFactory.createBitmapBorder(roundedpadding, roundedBorderBitmap);

	public static Font font = Font.getDefault().derive(Font.PLAIN, 6, Ui.UNITS_pt);
	public static Font fontVerySmall = Font.getDefault().derive(Font.PLAIN, 5, Ui.UNITS_pt);
	public static Font fontBold = Font.getDefault().derive(Font.BOLD, 7, Ui.UNITS_pt);
	public static Font fontBold6 = Font.getDefault().derive(Font.BOLD, 6, Ui.UNITS_pt);
	public static Font fontBold5 = Font.getDefault().derive(Font.BOLD, 5, Ui.UNITS_pt);

	public static Bitmap titleBitmap = Bitmap.getBitmapResource("header320x32.png");

	public static Bitmap fbBitmap = Bitmap.getBitmapResource("fshare.png");
	public static Bitmap fbFocBitmap = Bitmap.getBitmapResource("fsharepressed.png");

	public static Bitmap twBitmap = Bitmap.getBitmapResource("twitter.png");
	public static Bitmap twFocBitmap = Bitmap.getBitmapResource("twitterpressed.png");

	public static Bitmap homeBitmap = Bitmap.getBitmapResource("home.png");
	public static Bitmap homeFocBitmap = Bitmap.getBitmapResource("homepressed.png");

	public static Bitmap cookBitmap = Bitmap.getBitmapResource("cookingtips.png");
	public static Bitmap cookFocBitmap = Bitmap.getBitmapResource("cookingtipsselected.png");

	public static Bitmap ingrBitmap = Bitmap.getBitmapResource("ingredients.png");
	public static Bitmap ingrFocBitmap = Bitmap.getBitmapResource("ingredientsselected.png");

	public static Vector categories;
	public static Vector generalShare;

	public static String[] titles;

	public static String[] title;
	public static String[] url;
	public static String[] link;

	public static String[] titleRec;
	public static String[] urlRec;
	public static String[] linkRec;

	public static String[] shareURL;
	public static String[] summary;
	public static String[] ingerdients;
	public static String[] procedure;

	public static String[] cookTips;
	public static String[] cookDesc;

	public static String[] ingredientsTitle;
	public static String[] ingredientsLink;

	public static String[] subIngredientsLink;
	public static String[] subIngredientsHindiName;

	public static String[] listBitmapArray = { "breakfast.png", "dessert.png","lowcaldiet.png","lunch.png" };
	public static String[] listBitmapArray2 = { "trial.png", "bgimag.PNG", "trial.png" };

	public static final int HOMELIST_SCREEN_FOCUSFONTCOLOR = 0x00FFFFFF;
	public static final int HOMELIST_SCREEN_FONTCOLOR = Color.RED;

	public static String[] recipTitles;

	public static Bitmap bgBitmap480 = Bitmap.getBitmapResource("bg480x360.png");
	public static Bitmap bgBitmap360 = Bitmap.getBitmapResource("bg360x480.png");
	public static Bitmap bgBitmap320 = Bitmap.getBitmapResource("bg320x240.png");
	public static Bitmap bgBitmap240 = Bitmap.getBitmapResource("bg240x320.png");

	public static Bitmap bitmap480 = Bitmap.getBitmapResource("splash480x360.png");
	public static Bitmap bitmap360 = Bitmap.getBitmapResource("splash360x480.png");
	public static Bitmap bitmap320 = Bitmap.getBitmapResource("splash320x240.png");
	public static Bitmap bitmap240 = Bitmap.getBitmapResource("splash240x320.png");

	public static String NEXT_URL = "http://www.facebook.com/connect/login_success.html/";
//	public static String NEXT_URL = "https://www.facebook.com/dialog/oauth?client_id=317581498274147&redirect_uri=http://starplus.startv.in/show/masterchef-india-2/home_126.aspx";
	public static String APPLICATION_ID = "177479935596775";//"317581498274147";
	public static String APPLICATION_SECRET = "19a89bce6faaffd204044b87041038a8";//"c8574f89758a91c54c91a915746c0598";
	public static String[] PERMISSIONS = Facebook.Permissions.USER_DATA_PERMISSIONS;

}
