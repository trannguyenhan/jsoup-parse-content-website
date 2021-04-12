package demo;

import java.io.IOException;
import java.util.List;

import org.jsoup.nodes.Element;

import elements.TextDocument;
import parse.ParseWebsite;
import elements.TextBlock;

public class Main {
	public static void main(String[] args) throws IOException {
		String url = "https://sohagame.vn/tin-tuc/vuong-than-mobile-vote-app-5-sao-nhan-qua-cuc-wow-26436.html";
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
		
		printResult(cnt, listTextBlock.size());
	}
	
	public static void printResult(int content, int total) {
		System.out.println("\n ------------------------------------\n| content / total (Block) = " + content + " / " + total + " |");
		System.out.println(" ------------------------------------");
	}
}
