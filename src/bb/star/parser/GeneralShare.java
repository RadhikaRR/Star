package bb.star.parser;

public class GeneralShare {

	private String contentText;
	private String shareURL;

	public String getContentText() {
		return contentText;
	}

	public void setContentText(String contentText) {
		this.contentText = contentText;
	}

	public String getShareURL() {
		return shareURL;
	}

	public void setShareURL(String shareURL) {
		this.shareURL = shareURL;
	}

	public void printInfo() {
		System.out.println(">>> GeneralShare: " + " >> ContentText: "
				+ contentText + " >> ShareURL: " + shareURL);
	}
}
