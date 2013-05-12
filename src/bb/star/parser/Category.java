package bb.star.parser;

import java.util.Vector;

public class Category {

	private String title;
	private Vector Subcategories;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Vector getSubcategories() {
		return Subcategories;
	}

	public void setSubcategories(Vector subcategories) {
		this.Subcategories = subcategories;
	}

	public void printInfo() {
		System.out.println(">>> Category: " + title);
	}
}
