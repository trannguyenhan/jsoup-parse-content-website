package algorithms;

import elements.TextBlock;

public class ClassifierAlgorithms implements IClassifierAlgorithms{
	protected TextBlock preBlock;
	protected TextBlock curBlock;
	protected TextBlock nextBlock;
	
	@Override
	public boolean process() {
		return false;
	}
	
	public boolean process(TextBlock preBlock, TextBlock curBlock, TextBlock nextBlock) {
		setBlock(preBlock, curBlock, nextBlock);
		return process();
	}
	
	public void setBlock(TextBlock preBlock, TextBlock curBlock, TextBlock nextBlock) {
		this.curBlock = curBlock;
		this.preBlock = preBlock;
		this.nextBlock = nextBlock;
	}
	
	protected boolean checkText() {
		String text = curBlock.getText();
		int ctext = curBlock.buildNumWords(text);
		if(ctext < 3) return false;
		
		return true;
	}
}
