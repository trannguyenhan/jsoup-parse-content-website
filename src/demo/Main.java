package demo;

import java.io.IOException;
import java.util.List;

import org.jsoup.nodes.Element;

import elements.TextDocument;
import parse.ParseWebsite;
import elements.TextBlock;

public class Main {
	public static void main(String[] args) throws IOException {
		String url = "https://kenh14.vn/cap-bao-mot-thanh-vien-cot-can-cua-running-man-ban-viet-can-nhac-thay-the-vi-muon-banh-truong-20210409155850973.chn";
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
