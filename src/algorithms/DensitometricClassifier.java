package algorithms;

import elements.TextBlock;

public class DensitometricClassifier implements ClassifierAlgorithms {	
	@Override
	public boolean process(TextBlock preBlock, TextBlock curBlock, TextBlock nextBlock) {
		double curLinkDensity = curBlock.getLinkDensity();
		double prevLinkDensity = preBlock.getLinkDensity();
		double nextTextDensity = nextBlock.getTextDensity();
		double curTextDensity = curBlock.getTextDensity();
		double prevTextDensity = preBlock.getTextDensity();
		
		if (curLinkDensity <= 0.333333) {
			if (prevLinkDensity <= 0.555556) {
				if (curTextDensity <= 9) {
					if (nextTextDensity <= 10) {
						if (prevTextDensity <= 4) {
							return false;
						} else {
							return true;
						}
					} else {
						return true;
					}
				} else if (nextTextDensity == 0) {
					return false;
				} else {
					return true;
				}
			} else if (nextTextDensity <= 11) {
				return false;
			} else {
				return true;
			}
		} else {
			return false;
		}
	}
}
