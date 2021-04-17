package parse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class ParseWebsite {
	public static String TITLE = "";
	
	private Document document;
	private List<Element> listElements;
	
	/* Constructor default, if no define document 
	 * */
	public ParseWebsite() throws IOException {
		this("https://tiki.vn/dien-thoai-bphone-3-pro-hang-chinh-hang-p68521343.html");
	}

	public ParseWebsite(String url) throws IOException {
		document = Jsoup.connect(url).ignoreHttpErrors(true).get();
		listElements = new ArrayList<Element>();
		TITLE = document.title();
		
		buildAllContent();	
	}
	
	/* Build content with all element with all tag in DOM
	 * */
	private void buildAllContent() {
		List<Element> listTmpElements = new ArrayList<Element>();
		Elements elements = document.getAllElements();
		for(Element e : elements) {
			listTmpElements.add(e);
		}
		
		for(Element e : listTmpElements) {
			if(checkBody(e)) {
				listElements.add(e);
			}
		}
	}
	
	/*Delete footer and header and other tag can isn't content*/
	public boolean checkBody(Element element) {
		if(element == null) {
			return false;
		} else {
			if(element.tagName().toLowerCase().equals("a")
					|| element.tagName().toLowerCase().equals("strong")
					|| element.tagName().toLowerCase().equals("b")
					|| element.tagName().toLowerCase().equals("em")
					|| element.tagName().toLowerCase().equals("h1")
					|| element.tagName().toLowerCase().equals("h2")
					|| element.tagName().toLowerCase().equals("h3")
					|| element.tagName().toLowerCase().equals("h4")
					|| element.tagName().toLowerCase().equals("h5")
					|| element.tagName().toLowerCase().equals("h6")
					|| element.tagName().toLowerCase().equals("mark")) {
				return false;
			} else if(element.parent() != null) {
				if(element.parent().tagName().toLowerCase().equals("a")){
					return false;
				}
			}
		}
		
		// tag below is tag no content, we can't remove them
		if(element.tagName().toLowerCase().contains("script")
				|| element.tagName().toLowerCase().contains("style")
				|| element.className().toLowerCase().contains("info")
				|| element.className().toLowerCase().contains("contact")
				|| element.className().toLowerCase().contains("title")
				|| element.tagName().toLowerCase().contains("button")
				|| (element.tagName().toLowerCase().contains("input") 
					&& (element.attr("type").toLowerCase().contains("submit")
							|| element.attr("type").toLowerCase().contains("reset")))) {
			return false;
		}
		
		// tag in footer and header usually only contains the information link system, 
		// the characteristics of the website without the content
		// using backtracking find tag in footer and header
		while(element.nodeName() != "html") {
			if(element.nodeName().contains("footer") 
					|| element.nodeName().toLowerCase().contains("head") 
					|| element.className().toLowerCase().contains("footer")){
				return false;
			}
				
			element = element.parent();
			if(element == null) {
				return true;
			}
		}
		
		return true;
	}
	
	public List<Element> getListElements() {
		return listElements;
	}	
}
