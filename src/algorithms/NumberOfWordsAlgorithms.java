package algorithms;

import elements.TextBlock;

public class NumberOfWordsAlgorithms implements ClassifierAlgorithms {
	@Override
	public boolean process(TextBlock preBlock, TextBlock curBlock, TextBlock nextBlock) {
		double curLinkDensity = curBlock.getLinkDensity();
		double prevLinkDensity = preBlock.getLinkDensity();
		int currWords = curBlock.getNumWords();
		int nextWords = nextBlock.getNumWords();
		int preWords = preBlock.getNumWords();
		
		if (curLinkDensity <= 0.333333) {
			if (prevLinkDensity <= 0.555556) {
				if (currWords <= 16) {
					if (nextWords <= 15) {
						if (preWords <= 4) {
							return false;
						} else {
							return true;
						}
					} else {
						return true;
					}
				} else {
					return true;
				}
			} else if (currWords <= 40) {
				if (nextWords <= 17) {
					return false;
				} else {
					return true;
				}
			} else {
				return true;
			}
		} else {
			return false;
		}
	}
}
