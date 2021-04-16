package parse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class ParseWebsite {
	private Document document;
	private List<Element> listElements;
	
	public ParseWebsite() throws IOException {
		this("https://tiki.vn/dien-thoai-bphone-3-pro-hang-chinh-hang-p68521343.html");
	}

	public ParseWebsite(String url) throws IOException {
		document = Jsoup.connect(url).ignoreHttpErrors(true).get();
		listElements = new ArrayList<Element>();
		
		boolean isAll = true;
		if(isAll) {
			buildAllContent();
		} else {
			buildContent();
		}		
	}

	/* Build content with some tag, some tag cann't content such as : div, span, p, li, td, article
	 * */
	private void buildContent() {
		Elements elements;
		
		elements = document.getElementsByTag("div");
		for(Element e : elements) {
			listElements.add(e);
		}
		
		elements = document.getElementsByTag("span");
		for(Element e : elements) {
			listElements.add(e);
		}
		
		elements = document.getElementsByTag("p");
		elements.forEach(e -> listElements.add(e));
		
		elements = document.getElementsByTag("article");
		elements.forEach(e -> listElements.add(e));
		
		elements = document.getElementsByTag("blockquote");
		elements.forEach(e -> listElements.add(e));
		
		elements = document.getElementsByTag("li");
		elements.forEach(e -> {
			if(e.parent().parent().tagName() != "td") { // first parent is ul or ol tag, second parent can is tag td
				listElements.add(e);
			}
		});
		
		List<Element> listTmpElement = new ArrayList<Element>();
		for(Element e : listElements) {
			if(checkBody(e)) {
				listTmpElement.add(e);
			}
		}
		
		listElements.clear();
		listElements.addAll(listTmpElement);
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
	
	/* Return title web site
	 * */
	public String getTitle() {
		return "\"title\" : " + "\"" + document.title() + "\"";
	}
	
	/*Delete footer and header*/
	public boolean checkBody(Element element) {
		if(element == null) {
			return false;
		} else {
			if(element.tagName().toLowerCase().equals("a")) {
				return false;
			}
		}
		
		if(element.parent() != null) {
			if(element.parent().tagName().toLowerCase().equals("a")){
				return false;
			}
		}
		
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
