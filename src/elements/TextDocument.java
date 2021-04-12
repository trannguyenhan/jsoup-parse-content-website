package elements;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Element;

import algorithms.ClassifierAlgorithms;
import algorithms.DensitometricClassifier;
import algorithms.NumberOfWordsAlgorithms;

public class TextDocument {
	List<TextBlock> listTextBlocks;
	ClassifierAlgorithms algorithm;
	ClassifierAlgorithms algorithm1;
	ClassifierAlgorithms algorithm2;

	public TextDocument(List<Element> listElement) {
		listTextBlocks = new ArrayList<TextBlock>();
		algorithm = 
				new DensitometricClassifier();
				// new NumberOfWordsAlgorithms();
		algorithm1 = new DensitometricClassifier();
		algorithm2 = new NumberOfWordsAlgorithms();
		
		for(Element e : listElement) {
			TextBlock textBlock = new TextBlock(e);
			listTextBlocks.add(textBlock);
		}

		buildContent();
	}

	private void buildContent() {
		TextBlock preBlock = TextBlock.START_BLOCK;
		TextBlock nextBlock = TextBlock.END_BLOCK;
		TextBlock curBlock = null;
		
		int size = listTextBlocks.size();
		int i = 0;
		while (i < size - 1) {
			if (i != 0) {
				preBlock = listTextBlocks.get(i-1);
			}
			curBlock = listTextBlocks.get(i);
			if (i != size - 1) {
				nextBlock = listTextBlocks.get(i+1);
			}

//			boolean curIsContent = algorithm.process(preBlock, curBlock, nextBlock);
			boolean curIsContent1 = algorithm1.process(preBlock, curBlock, nextBlock);
			boolean curIsContent2 = algorithm2.process(preBlock, curBlock, nextBlock);
			curBlock.setContent(curIsContent1 | curIsContent2);

			// next block
			i++;
		}

	}

	public List<TextBlock> getListTextBlocks() {
		return listTextBlocks;
	}
}
