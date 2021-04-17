package parse;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;

import parse.support.TextNormalize;

public class ParseElement {
	private TextNormalize textNormalize;
	private Element root;
	private String tag;
	
	public ParseElement(Element root) {
		if(root != null) {
			this.root = root;
			tag = this.root.tagName();
		}
		textNormalize = new TextNormalize();
	}
	
	public ParseElement() {
		this(null);
		String html = "<h1>This is a Heading<p>This is a paragraph.</p><a>Xin chao<h2>Hello<h3><h4></h4>Blo</h3></h2></a></h1>";
		root = Jsoup.parse(html).getElementsByTag("h1").get(0);
		tag = root.tagName();
	}
	
	/* method only get text in owner tag
	 * for example : <body>Hello <p>World</p></body>, method return "Hello", not return "Hello World"
	 * */
	public String getTextInTag() {
		if(root.tagName().contains("table")) {
			return textInTableTag();
		}
		
		List<Node> listChildNodes = root.childNodes();
		List<String> listContent = new ArrayList<String>();
		
		for(Node node : listChildNodes) {
			String text = node.outerHtml();
			if(checkHtmlChildNode(text) 
					|| node.nodeName().equals("b") 
					|| node.nodeName().equals("mark")
					|| node.nodeName().equals("em")
					|| node.nodeName().equals("sub")
					|| node.nodeName().equals("a")
					|| node.nodeName().equals("h1")
					|| node.nodeName().equals("h2")
					|| node.nodeName().equals("h3")
					|| node.nodeName().equals("h4")
					|| node.nodeName().equals("h5")
					|| node.nodeName().equals("h6")) {
				if(node.nodeName().equals("a")) {
					text = Jsoup.parse(node.outerHtml()).getElementsByTag(node.nodeName()).text();
				}
				
				listContent.add(text);
			}
			
		}
		
		String content = "";
		for(String s : listContent) {
			content = content + "\n" + s;
		}
		
		content = textNormalize.normalize(content);
		return content;
	}
	
	/* With table tag, little but condensed information, and harder to work with than other cards
	 * So, We write a separate function to get information from the table tag
	 * */
	private String textInTableTag() {
		String content = "";
		Elements elementstr = root.getElementsByTag("tr");
		for(Element etr : elementstr) {
			Elements elementstd = etr.getElementsByTag("td");
			for(Element etd : elementstd) {
				content += " " + etd.text();
			}
		}
		
		return content;
	}
	
	/* method return anchor text in owner tag
	 * it is every text in tag a (tag link)
	 * */
	public String getAnchorTextInTag() {
		List<Element> elementsATag = new ArrayList<Element>();
		Element rootSibling;
		
		// get a tag in previous element
		rootSibling = root.previousElementSibling();
		if(rootSibling != null) {
			if(rootSibling.tagName().contains("a")) {
				elementsATag.add(rootSibling);
			} 
		}
		
		// get a tag in current element
		root.children().forEach(e -> {
			if(e.tagName().equals("a")) {
				elementsATag.add(e);
			}
		});
		
		// get a tag in next element
		rootSibling = root.nextElementSibling();
		if(rootSibling != null) {
			if(rootSibling.tagName().contains("a")) {
				elementsATag.add(rootSibling);
			} 
		}
		
		String textATag = "";
		for(Element e : elementsATag) {
			String text = e.text();
			textATag = textATag + "\n" + text;
		}
		
//		System.out.println(getTextInTag());
//		System.out.println(textATag + "\n-----------------\n");
		
		textATag = textNormalize.normalize(textATag);
		return textATag;
	}
	
	/* return false if html have tag, for example :  <tag>text here</tag>
	 * return true if html only text, for example : text here
	 * */
	public boolean checkHtmlChildNode(String text) {
		if(text.contains("</") 
				|| text.contains("<img") 
				|| text.contains("<input") 
				|| text.contains("<!--")) {
			return false;
		}
		
		return true;
	}
	
	public void setElement(Element element) {
		this.root = element;
	}

	public String getTag() {
		return tag;
	}	
}
