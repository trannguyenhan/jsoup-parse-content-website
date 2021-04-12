package demo;

import java.io.IOException;
import java.util.List;

import org.jsoup.nodes.Element;

import elements.TextDocument;
import parse.ParseWebsite;
import elements.TextBlock;

public class Main {
	public static void main(String[] args) throws IOException {
		String url = "https://tiki.vn/thung-bia-tiger-24-lon-330ml-lon-p912679.html";
		ParseWebsite parse = new ParseWebsite(url);
		List<Element> listElement = parse.getListElements();
		TextDocument document = new TextDocument(listElement);
		List<TextBlock> listTextBlock = document.getListTextBlocks();

		int cnt = 0;
		for(TextBlock t : listTextBlock) {
			if(t.isContent()) {
				System.out.println(t.getText());
//				System.out.println(t.getText());
				cnt++;
			}
//			System.out.println(t.getText());
		}		
		System.out.println("\n ----------------------------\n| content / total  = " + cnt + " / " + listTextBlock.size() + " |");
		System.out.println(" ----------------------------");
		
	}
}
