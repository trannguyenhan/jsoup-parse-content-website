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
		this("https://tiki.vn/dien-thoai-bphone-3-pro-hang-chinh-hang-p68521343.html?spid=68521347");
	}

	public ParseWebsite(String url) throws IOException {
		document = Jsoup.connect(url).ignoreHttpErrors(true).get();
		listElements = new ArrayList<Element>();
		
		buildContent();
	}

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
		
//		elements = document.getElementsByTag("a");
//		elements.forEach(e -> listElements.add(e));
		
		elements = document.getElementsByTag("li");
		elements.forEach(e -> {
			if(e.parent().parent().tagName() != "td") { // first parent is ul or ol tag, second parent can is tag td
				listElements.add(e);
			}
		});
		
//		elements = document.getElementsByTag("h1");
//		elements.forEach(e -> listElements.add(e));
//		
//		elements = document.getElementsByTag("h2");
//		elements.forEach(e -> listElements.add(e));
//		
//		elements = document.getElementsByTag("h3");
//		elements.forEach(e -> listElements.add(e));
//		
//		elements = document.getElementsByTag("h4");
//		elements.forEach(e -> listElements.add(e));
//		
//		elements = document.getElementsByTag("h5");
//		elements.forEach(e -> listElements.add(e));
//		
//		elements = document.getElementsByTag("h6");
//		elements.forEach(e -> listElements.add(e));
		
//		ParseElement parseElement = new ParseElement();
//		for (Element es : listElements) {
//			parseElement.setElement(es);
//			String text = parseElement.getTextInTag();
//			if(text != "") {
//				System.out.println(text + "\n---------------\n");
//			}
//		}
		
		List<Element> listTmpElement = new ArrayList<Element>();
		for(Element e : listElements) {
			if(checkBody(e)) {
				listTmpElement.add(e);
			}
		}
		
		listElements.clear();
		listElements.addAll(listTmpElement);
	}
	
	public String getTitle() {
		return "\"title\" : " + "\"" + document.title() + "\"";
	}

	public List<Element> getListElements() {
		return listElements;
	}	
	
	/*Del footer and header*/
	public boolean checkBody(Element element) {
		Element p = element;
		while(p.nodeName() != "html") {
			p = element.parent();
			if(p.nodeName() == "footer" || p.nodeName() == "head" || p.className().contains("footer")) {
				return false;
			}
			
			element = element.parent();
		}
		
		return true;
	}
}
