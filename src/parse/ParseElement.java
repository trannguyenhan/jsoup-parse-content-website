package parse;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;

public class ParseElement {
	public Element root;
	private String tag;
	
	public ParseElement(Element root) {
		this.root = root;
		tag = this.root.tagName();
	}
	
	public ParseElement() {
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
					|| node.nodeName().equals("a")) {
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
		
		content = normalize(content);
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
			} else {
				if(rootSibling != null) {
					rootSibling.getElementsByTag("a").forEach(e -> {
						elementsATag.add(e);
					});
				}
			}
		}
		
		// get a tag in current element
		root.getElementsByTag("a").forEach(e -> {
			elementsATag.add(e);
		});
		
		// get a tag in next element
		rootSibling = root.nextElementSibling();
		if(rootSibling != null) {
			if(rootSibling.tagName().contains("a")) {
				elementsATag.add(rootSibling);
			} else {
				rootSibling.getElementsByTag("a").forEach(e -> {
					elementsATag.add(e);
				});
			}
		}
		

		
		String textATag = "";
		for(Element e : elementsATag) {
			String text = e.text();
			textATag = textATag + "\n" + text;
		}
		
		textATag = normalize(textATag);
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
	
	/* Normalize strings, remove space, enter beginning and end of line
	 * */
	public String normalize(String text) {
		// remove <strong> tag, <b> tag
		text = text.replace("<strong>", "");
		text = text.replace("</strong>", "");
		text = text.replace("<b>", "");
		text = text.replace("</b>", "");
		text = text.replace("<mark>", "");
		text = text.replace("</mark>", "");
		text = text.replace("<em>", "");
		text = text.replace("</em>", "");
		text = text.replace("</a>", "");
	
		// remove rag <br>
		text = text.replace("<br>", "");
		
		int lens = text.length();
		
		int start = 0;
		for(int i=0; i<lens; i++) { // find start
			if(text.charAt(i) != ' ' && text.charAt(i) != '\n') {
				start = i;
				break;
			}
		}
		
		int last = lens - 1;
		for(int i=lens-1; i>=0; i--) { // fine end
			if(text.charAt(i) != ' ' && text.charAt(i) != '\n') {
				last = i;
				break;
			}
		}
		
		String newText = text.substring(start, last+1);

		// remove space if duplicate
		while(newText.contains("  ")) {
			newText = newText.replace("  ", " ");
		}
		
		// remove space after \n
		while(newText.contains("\n ")) {
			newText = newText.replace("\n ", "\n");
		}
		
		// remove \n if duplicate
		while(newText.contains("\n\n")) {
			newText = newText.replace("\n\n", "\n");
		}
	
		return newText;
	}
	
	public void setElement(Element element) {
		this.root = element;
	}

	public String getTag() {
		return tag;
	}	
}
