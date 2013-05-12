package bb.star.UIScreens;

import net.rim.device.api.system.Bitmap;
import net.rim.device.api.system.Display;
import net.rim.device.api.ui.Color;
import net.rim.device.api.ui.DrawStyle;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.Manager;
import net.rim.device.api.ui.container.HorizontalFieldManager;
import net.rim.device.api.ui.container.VerticalFieldManager;
import bb.star.constant.Constants;
import bb.star.controller.MainScreenClass;
import bb.star.customClass.BitmapButtonField;
import bb.star.customFields.BorderLabelFieldImpl;

public class cookDescScreen extends MainScreenClass {

	NextPreview np;
	BitmapButtonField btnNext, btnpreview;
	BorderLabelFieldImpl cookDescLabel, cookTipsTitleLabel;
	int indexx;

	public cookDescScreen(int index) {
		super.deleteAll();
		this.indexx = index;

		Bitmap next = Bitmap.getBitmapResource("nextbt.png");
		Bitmap nextPres = Bitmap.getBitmapResource("nextbtpressed.png");

		Bitmap prev = Bitmap.getBitmapResource("prevbt.png");
		Bitmap prevPres = Bitmap.getBitmapResource("prevbtpressed.png");

		VerticalFieldManager vfm = new VerticalFieldManager(VERTICAL_SCROLL | VERTICAL_SCROLLBAR);

		HorizontalFieldManager labelHorizontalFieldManager = new HorizontalFieldManager();
		cookTipsTitleLabel = new BorderLabelFieldImpl(Constants.cookTips[index], Field.FOCUSABLE | USE_ALL_WIDTH
				| DrawStyle.HCENTER);
		cookTipsTitleLabel.setFont(Constants.fontBold);
		cookTipsTitleLabel.setFontColor(Color.WHITE);
		cookTipsTitleLabel.setBgColor(0x00E91E24);
		cookTipsTitleLabel.setMargin(1, 3, 10, 3);
		labelHorizontalFieldManager.add(cookTipsTitleLabel);

		VerticalFieldManager vfmDesc = new VerticalFieldManager();
		cookDescLabel = new BorderLabelFieldImpl(Constants.cookDesc[index], USE_ALL_WIDTH | DrawStyle.HCENTER);
		cookDescLabel.setFont(Constants.fontBold);
		cookDescLabel.setFontColor(Color.WHITE);
		vfmDesc.add(cookDescLabel);

		btnNext = new BitmapButtonField(next, nextPres, DrawStyle.HCENTER) {
			protected boolean navigationClick(int status, int time) {
				indexx += 1;
				int length = Constants.cookTips.length - 1;
				if (indexx <= length) {
					cookTipsTitleLabel.setText(Constants.cookTips[indexx]);
					cookDescLabel.setText(Constants.cookDesc[indexx]);
				} else {
					indexx = Constants.cookTips.length - 1;
				}
				return true;
			}
		};
		btnpreview = new BitmapButtonField(prev, prevPres, DrawStyle.HCENTER) {
			protected boolean navigationClick(int status, int time) {
				indexx -= 1;
				if (indexx >= 0) {
					cookTipsTitleLabel.setText(Constants.cookTips[indexx]);
					cookDescLabel.setText(Constants.cookDesc[indexx]);
				} else {
					indexx = 0;
				}
				return true;
			}
		};

		np = new NextPreview();
		np.add(btnpreview);
		np.add(btnNext);

		vfm.add(labelHorizontalFieldManager);
		vfm.add(vfmDesc);
		vfm.add(np);

		add(vfm);
	}

	public class NextPreview extends Manager {
		public NextPreview() {
			super(Manager.NO_HORIZONTAL_SCROLL | Manager.NO_VERTICAL_SCROLL);
		}

		protected void sublayout(int width, int height) {
			layoutChild(btnpreview, Display.getWidth(), Display.getHeight());
			setPositionChild(btnpreview, 0, 0);

			if (Display.getWidth() == 360) {
				layoutChild(btnNext, Display.getWidth(), Display.getHeight());
				setPositionChild(btnNext, Display.getWidth() - (Display.getWidth() / 7) + 26, 0);
			} else if (Display.getWidth() == 320) {
				layoutChild(btnNext, Display.getWidth(), Display.getHeight());
				setPositionChild(btnNext, Display.getWidth() - (Display.getWidth() / 7) + 21, 0);
			} else {
				layoutChild(btnNext, Display.getWidth(), Display.getHeight());
				setPositionChild(btnNext, Display.getWidth() - (Display.getWidth() / 7) + 42, 0);
			}

			setExtent(Display.getWidth(), btnpreview.getHeight());
		}
	}
}
