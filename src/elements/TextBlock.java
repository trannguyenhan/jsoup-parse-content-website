package elements;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import parse.ParseElement;

public class TextBlock {
	private Element element;
	private boolean isContent;
	private String text;

	private int numWordsInAnchorText;
	private int numWords;
	private int numTags;
	private double textDensity;
	private double linkDensity;

	private ParseElement parseElement;
	
	/* Constructor and 2 attribute below only use initialization configuration start and end of TextBlock
	 * */
	public static final TextBlock START_BLOCK = new TextBlock(0, 0, 0, 0);
	public static final TextBlock END_BLOCK = new TextBlock(0, 0, 0, 0);
	
	public TextBlock(int numWords, int numTags, double textDensity, double linkDensity) {
		this.numWords = numWords;
		this.numTags = numTags;
		this.textDensity = textDensity;
		this.linkDensity = linkDensity;
	}
	
	/* Initialization Object with html
	 * */
	public TextBlock(Element element) {
		this.element = element;
		isContent = false;
		parseElement = new ParseElement(element);
		
		buildBlock();
	}

	/* Build any parameter in block with html, call by constructor
	 * */
	private void buildBlock() {
		text = parseElement.getTextInTag();
		
		numWords = countChar(element);
		numTags = countTags(element);
		textDensity = (double) numWords / numTags;
		
		String anchorText = parseElement.getAnchorTextInTag();
		numWordsInAnchorText = buildNumWords(anchorText);
		
		if(numWords == 0) {
			linkDensity = 0;
		} else {
			linkDensity = (double) numWordsInAnchorText / (numWords);
		}
		
		numWords += numWordsInAnchorText;
	}
	

	
	/* initialization number of word in tag
	 * */
	public int countChar(Element node) {
		//String text = node.text();
		return buildNumWords(parseElement.getTextInTag());
	}
	
	public int buildNumWords(String text) {
		int lens = text.length();
		int cnt = 0;
		for(int i=0; i<lens; i++) {
			if(text.charAt(i) == ' ' || text.charAt(i) == '\n') {
				cnt++;
			}
		}
		
		return cnt + 2;
	}
	
	/* initialization numTags
	 * */
	public int countTags(Element node) {
		Elements allElements = node.getAllElements();
		return allElements.size();
	}

	/* getter
	 * setter
	 * */
	
	public Element getElement() {
		return element;
	}

	public void setElement(Element element) {
		this.element = element;
	}

	public boolean isContent() {
		return isContent;
	}

	public void setContent(boolean isContent) {
		this.isContent = isContent;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public int getNumWordsInAnchorText() {
		return numWordsInAnchorText;
	}

	public void setNumWordsInAnchorText(int numWordsInAnchorText) {
		this.numWordsInAnchorText = numWordsInAnchorText;
	}

	public int getNumWords() {
		return numWords;
	}

	public void setNumWords(int numWords) {
		this.numWords = numWords;
	}

	public int getNumTags() {
		return numTags;
	}

	public void setNumTags(int numTags) {
		this.numTags = numTags;
	}

	public double getTextDensity() {
		return textDensity;
	}

	public void setTextDensity(double textDensity) {
		this.textDensity = textDensity;
	}

	public double getLinkDensity() {
		return linkDensity;
	}

	public void setLinkDensity(double linkDensity) {
		this.linkDensity = linkDensity;
	}
}
