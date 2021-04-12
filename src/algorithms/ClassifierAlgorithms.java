package algorithms;

import elements.TextBlock;

/* Assign content with heuristic Classifier
 * */
public interface ClassifierAlgorithms {
	/* Writer your's heuristic in here 
	 * */
	public boolean process(TextBlock preBlock, TextBlock curBlock, TextBlock nextBlock);
}
