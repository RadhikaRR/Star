package bb.star.parser;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Vector;

import bb.star.constant.Constants;

public class XMLParser {

	InputStream ipStream;

	public static XMLParser INSTANCE = new XMLParser();

	public void parseMain(String xmlPath) throws Exception {

		try {
			if (Constants.LOCAL) {
				ipStream = getClass().getResourceAsStream(xmlPath);
			} else {
				ipStream = new ByteArrayInputStream(xmlPath.getBytes());
			}
			final XmlDomNode rootElement = XmlDomParser.parseTree(ipStream);

			if (rootElement.getName().equalsIgnoreCase("EBOOK")) {
				final Enumeration children = rootElement.getChildren();

				while (children.hasMoreElements()) {
					final XmlDomNode childNode = (XmlDomNode) children.nextElement();
					final String nodeName = childNode.getName();

					if (nodeName.equalsIgnoreCase("MAINMENU")) {
						final XmlDomNode categoriesNode = childNode.getChild("CATEGORIES");
						Constants.categories = parseCategories(categoriesNode);
					} else if (nodeName.equalsIgnoreCase("GENERALSHARE")) {
						Constants.generalShare = parseGeneralShare(childNode);
					}
				}
			}
		} catch (Exception e) {
			throw new Exception("Sorry!Details are not available");
		}
	}

	private static Vector parseCategories(final XmlDomNode node) throws Exception {
		Vector category = new Vector();
		try {
			final Enumeration children = node.getChildren();

			while (children.hasMoreElements()) {
				final XmlDomNode chileNode = (XmlDomNode) children.nextElement();
				final String nodeName = chileNode.getName();

				if (nodeName.equalsIgnoreCase("CATEGORY")) {
					final Category categoryNode = parseCategory(chileNode);
					category.addElement(categoryNode);
				}
			}
		} catch (Exception e) {
			throw new Exception("Sorry!Details are not available");
		}
		return category;
	}

	private static Category parseCategory(final XmlDomNode node) throws Exception {
		Category category = new Category();
		try {
			final XmlDomNode titleNode = node.getChild("TITLE");
			category.setTitle(titleNode.getText());

			category.printInfo();

			final XmlDomNode subcategoriesNode = node.getChild("SUBCATEGORIES");
			category.setSubcategories(parseSubcategories(subcategoriesNode));
		} catch (Exception e) {
			throw new Exception("Sorry!Details are not available");
		}
		return category;
	}

	private static Vector parseSubcategories(final XmlDomNode node) throws Exception {
		Vector subcategory = new Vector();
		try {
			final Enumeration children = node.getChildren();

			while (children.hasMoreElements()) {
				final XmlDomNode childNode = (XmlDomNode) children.nextElement();
				final String nodeName = childNode.getName();

				if (nodeName.equalsIgnoreCase("SUBCATEGORY")) {
					Vector subcategoryElement = new Vector();

					final XmlDomNode titleNode = childNode.getChild("TITLE");
					subcategoryElement.addElement(titleNode.getText());
					final XmlDomNode urlNode = childNode.getChild("URL");
					subcategoryElement.addElement(urlNode.getText());
					final XmlDomNode linkNode = childNode.getChild("LINK");
					subcategoryElement.addElement(linkNode.getText());
					subcategory.addElement(subcategoryElement);
				}
			}
		} catch (Exception e) {
			throw new Exception("Sorry!Details are not available");
		}
		return subcategory;
	}

	private static Vector parseGeneralShare(final XmlDomNode node) throws Exception {
		Vector generalShare = new Vector();
		try {
			GeneralShare generalShareNode = new GeneralShare();

			final XmlDomNode contentText = node.getChild("CONTENTTEXT");
			generalShareNode.setContentText(contentText.getText());
			final XmlDomNode shareURL = node.getChild("SHAREURL");
			generalShareNode.setShareURL(shareURL.getText());
			generalShare.addElement(generalShareNode);

			generalShareNode.printInfo();
		} catch (Exception e) {
			throw new Exception("Sorry!Details are not available");
		}
		return generalShare;
	}

	public Vector parseSub(final String xmlPath) throws Exception {

		Vector recipes = new Vector();
		try {
			if (Constants.LOCAL) {
				ipStream = getClass().getResourceAsStream(xmlPath);
			} else {
				ipStream = new ByteArrayInputStream(xmlPath.getBytes());
			}

			final XmlDomNode rootElement = XmlDomParser.parseTree(ipStream);

			if (rootElement.getName().equalsIgnoreCase("EBOOK")) {

				final XmlDomNode recipesNode = rootElement.getChild("RECIPES");
				final Enumeration children = recipesNode.getChildren();

				while (children.hasMoreElements()) {
					final XmlDomNode childNode = (XmlDomNode) children.nextElement();
					final String nodeName = childNode.getName();

					if (nodeName.equalsIgnoreCase("RECIPE")) {
						Vector subCategory = new Vector();

						final XmlDomNode titleNode = childNode.getChild("TITLE");
						subCategory.addElement(titleNode.getText());
						final XmlDomNode urlNode = childNode.getChild("URL");
						subCategory.addElement(urlNode.getText());
						final XmlDomNode linkNode = childNode.getChild("LINK");
						subCategory.addElement(linkNode.getText());

						recipes.addElement(subCategory);
					}
				}
			}
		} catch (Exception e) {
			throw new Exception("Sorry!Details are not available for Whats Cooking");
		}
		return recipes;
	}

	public Vector parseRecipes(final String xmlPath) throws Exception {
		Vector recipes = new Vector();
		try {
			if (Constants.LOCAL) {
				ipStream = getClass().getResourceAsStream(xmlPath);
			} else {
				ipStream = new ByteArrayInputStream(xmlPath.getBytes());
			}
			final XmlDomNode rootElement = XmlDomParser.parseTree(ipStream);

			if (rootElement.getName().equalsIgnoreCase("EBOOK")) {

				final XmlDomNode recipesNode = rootElement.getChild("RECIPES");
				final Enumeration children = recipesNode.getChildren();

				while (children.hasMoreElements()) {
					final XmlDomNode childNode = (XmlDomNode) children.nextElement();
					final String nodeName = childNode.getName();

					if (nodeName.equalsIgnoreCase("RECIPE")) {
						Vector subCategory = new Vector();

						final XmlDomNode shareNode = childNode.getChild("SHAREURL");
						subCategory.addElement(shareNode.getText());
						final XmlDomNode summNode = childNode.getChild("SUMMARY");
						subCategory.addElement(summNode.getText());
						final XmlDomNode ingrNode = childNode.getChild("INGREDIENT");
						subCategory.addElement(ingrNode.getText());
						final XmlDomNode procNode = childNode.getChild("PROCEDURE");
						subCategory.addElement(procNode.getText());

						recipes.addElement(subCategory);
					}
				}
			}
		} catch (Exception e) {
			throw new Exception("Sorry!Details are not available for GetStarted");
		}
		return recipes;
	}

	public Vector parseCookTips(final String xmlPath) throws Exception {

		Vector recipes = new Vector();
		try {
			if (Constants.LOCAL) {
				ipStream = getClass().getResourceAsStream(xmlPath);
			} else {
				ipStream = new ByteArrayInputStream(xmlPath.getBytes());
			}
			final XmlDomNode rootElement = XmlDomParser.parseTree(ipStream);

			if (rootElement.getName().equalsIgnoreCase("EBOOK")) {

				final XmlDomNode recipesNode = rootElement.getChild("COOKINGTIPS");
				final Enumeration children = recipesNode.getChildren();

				while (children.hasMoreElements()) {
					final XmlDomNode childNode = (XmlDomNode) children.nextElement();
					final String nodeName = childNode.getName();

					if (nodeName.equalsIgnoreCase("COOKINGTIP")) {
						Vector subCategory = new Vector();

						final XmlDomNode titleNode = childNode.getChild("TITLE");
						subCategory.addElement(titleNode.getText());
						final XmlDomNode urlNode = childNode.getChild("DESC");
						subCategory.addElement(urlNode.getText());

						recipes.addElement(subCategory);
					}
				}
			}
		} catch (Exception e) {
			throw new Exception("Sorry!Details are not available for Cooking Tips");
		}
		return recipes;
	}

	public Vector parseIngredientsTips(final String xmlPath) throws Exception {

		if (Constants.LOCAL) {
			ipStream = getClass().getResourceAsStream(xmlPath);
		} else {
			ipStream = new ByteArrayInputStream(xmlPath.getBytes());
		}

		final XmlDomNode rootElement = XmlDomParser.parseTree(ipStream);

		Vector recipes = new Vector();
		try {
			if (rootElement.getName().equalsIgnoreCase("EBOOK")) {

				final XmlDomNode recipesNode = rootElement.getChild("INGREDIENTS");
				final Enumeration children = recipesNode.getChildren();

				while (children.hasMoreElements()) {
					final XmlDomNode childNode = (XmlDomNode) children.nextElement();
					final String nodeName = childNode.getName();

					if (nodeName.equalsIgnoreCase("INGREDIENT")) {
						Vector subCategory = new Vector();

						final XmlDomNode titleNode = childNode.getChild("TITLE");
						subCategory.addElement(titleNode.getText());
						final XmlDomNode urlNode = childNode.getChild("LINK");
						subCategory.addElement(urlNode.getText());

						recipes.addElement(subCategory);
					}
				}
			}
		} catch (Exception e) {
			throw new Exception("Sorry!Details are not available for Ingredients");
		}
		return recipes;
	}

	public Vector parseSubIngredients(final String xmlPath) throws Exception {
		Vector recipes = new Vector();
		try {
			if (Constants.LOCAL) {
				ipStream = getClass().getResourceAsStream(xmlPath);
			} else {
				ipStream = new ByteArrayInputStream(xmlPath.getBytes());
			}
			final XmlDomNode rootElement = XmlDomParser.parseTree(ipStream);

			if (rootElement.getName().equalsIgnoreCase("EBOOK")) {

				final XmlDomNode recipesNode = rootElement.getChild("INGREDIENTS");
				final Enumeration children = recipesNode.getChildren();

				while (children.hasMoreElements()) {
					final XmlDomNode childNode = (XmlDomNode) children.nextElement();
					final String nodeName = childNode.getName();

					if (nodeName.equalsIgnoreCase("INGREDIENT")) {
						Vector subCategory = new Vector();

						final XmlDomNode titleNode = childNode.getChild("URL");
						subCategory.addElement(titleNode.getText());
						final XmlDomNode urlNode = childNode.getChild("HINDINAME");
						subCategory.addElement(urlNode.getText());

						recipes.addElement(subCategory);
					}
				}
			}
		} catch (Exception e) {
			throw new Exception("Sorry!Details are not available for Sub-Ingredients");
		}
		return recipes;
	}
}
