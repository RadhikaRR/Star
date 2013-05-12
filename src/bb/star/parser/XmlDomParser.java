package bb.star.parser;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Hashtable;

/**
 * <p>Parses XML documents in a single step.</p>
 * <p>This is more comfortable than using the XmlPullParser but uses somewhat more memory since the complete document 
 *    is held memory.
 * </p>
 * <p>Following example code demonstrates the usage:</p>
 * <pre>
	String document = &quot;&lt;rootelement&gt;&lt;childnode x=\&quot;10\&quot;&gt;textvalue10&lt;/childnode&gt;&lt;childnode x=\&quot;20\&quot;&gt;textvalue20&lt;/childnode&gt;&lt;/rootelement&gt;&quot;;
	XmlDomNode root = XmlDomParser.parseTree(document);
	System.out.println(&quot;root &quot; + root.getName() + &quot;, has &quot; + root.getChildCount() + &quot; children&quot;);
	// indirect access:
	for (int i=0; i &lt; root.getChildCount(); i++ ) {
		XmlDomNode child = root.getChild(i);
		System.out.println(&quot;child: &quot; + child.getName() );
		System.out.println(&quot;child: &quot; + child.getName() + &quot;, x=&quot; + child.getAttribute(&quot;x&quot;) + &quot;, text=&quot; + child.getText() );
	}
	// direct access:
	XmlDomNode child = root.getChild(&quot;childnode&quot;);		
	System.out.println(&quot;first child: &quot; + child.getName() + &quot;, x=&quot; + child.getAttribute(&quot;x&quot;) + &quot;, text=&quot; + child.getText() );

 * </pre>
 * 
 * 
 * <p>Copyright Enough Software 2006 - 2009</p>
 * 
 * <pre>
 * history
 *        Apr 8, 2006 - mkoch creation
 * </pre>
 * 
 * @author Michael Koch, michael.koch@enough.de
 */
public class XmlDomParser
{

	/**
	 * Parses an XML document and returns it's root element as an XmlDomNode.
	 * When the XML document has no root element, it will return an artificial root element that contains
	 * all root elelements of the document.
	 * 
	 * @param document the document.
	 * @return the root element of the document as XmlDomNode
	 * @throws RuntimeException when the parsing fails
	 */
	public static XmlDomNode parseTree(String document)
	{
		ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(document.getBytes());
		return parseTree(byteArrayInputStream);
	}

	/**
	 * Parses an XML document and returns it's root element as an XmlDomNode.
	 * When the XML document has no root element, it will return an artificial root element that contains
	 * all root elements of the document.
	 * 
	 * @param in input stream of the document.
	 * @return the root element of the document as XmlDomNode
	 * @throws RuntimeException when the parsing fails
	 */
	public static XmlDomNode parseTree(InputStream in)
    {
    	XmlPullParser parser;
        InputStreamReader inputStreamReader = new InputStreamReader(in);

        try {
            parser = new XmlPullParser(inputStreamReader);
        } catch (IOException exception) {
            throw new RuntimeException("Could not create xml parser."+exception);
        }

        XmlDomNode root = new XmlDomNode(null, null, -1);
        XmlDomNode currentNode = root;
        String newName;
        int newType;
        
        try {
            while ((parser.next()) != SimplePullParser.END_DOCUMENT) {
                newName = parser.getName();
                newType = parser.getType();
                
                if(newType == SimplePullParser.START_TAG) {
                	Hashtable attributes = null;
                	int attributeCount = parser.getAttributeCount(); 

                	if (attributeCount > 0) {
                		attributes = new Hashtable();

                		for (int i = 0; i < attributeCount; i++) {
                			attributes.put(parser.getAttributeName(i), parser.getAttributeValue(i));
                		}
                	}

                    XmlDomNode newNode = new XmlDomNode(currentNode, newName, attributes, newType);
                    currentNode = newNode;
                }
                
                else if(newType == SimplePullParser.END_TAG) {
                    currentNode = currentNode.getParent();
                }
                
                else if(newType == SimplePullParser.TEXT) {
                    String text = parser.getText();
                    currentNode.setText(text);
                }
            }
        } catch (Exception exception) {
            throw new RuntimeException("parse error:"+exception);
        }
        if (root.getChildCount() == 1) {
        	return root.getChild(0);
        } else {
        	return root;
        }
    }
}

