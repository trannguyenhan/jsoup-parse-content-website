package demo.ui;

import java.io.IOException;
import java.util.List;

import org.jsoup.nodes.Element;

import elements.TextBlock;
import elements.TextDocument;
import parse.ParseWebsite;

public class Show {
	/* Run application
	 * */
	public static void run(String url) throws IOException {
		ParseWebsite parse = new ParseWebsite(url);
		List<Element> listElement = parse.getListElements();
		TextDocument document = new TextDocument(listElement);
		List<TextBlock> listTextBlock = document.getListTextBlocks();

		int cnt = 0;
		for(TextBlock t : listTextBlock) {
			if(t.isContent()) {
				System.out.println(t.getText());
				//System.out.println("\n----------\n");
				cnt++;
			}
			//System.out.println(t.getText());
			//System.out.println("\n----------------\n");
		}		
		Show.printResult(cnt, listTextBlock.size());
	}
	
	/* Show number of Block content / number of Block
	 * */
	public static void printResult(int content, int total) {
		System.out.println("\n ------------------------------------\n| content / total (Block) = " + content + " / " + total + " |");
		System.out.println(" ------------------------------------");
	}
}
