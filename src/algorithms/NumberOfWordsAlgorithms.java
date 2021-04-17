package algorithms;

public class NumberOfWordsAlgorithms extends ClassifierAlgorithms {
	@Override
	public boolean process() {
		if(!checkText()) {
			return false;
		}
		
		double curLinkDensity = curBlock.getLinkDensity();
		double prevLinkDensity = preBlock.getLinkDensity();
		int currWords = curBlock.getNumWords();
		int nextWords = nextBlock.getNumWords();
		int preWords = preBlock.getNumWords();
		
//		System.out.println(curBlock.getText());
//		System.out.println(curLinkDensity);
//		System.out.println("\n----------\n");
		
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
