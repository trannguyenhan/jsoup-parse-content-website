package algorithms.classifier;

public class DensitometricClassifier extends ClassifierAlgorithms {	
	@Override
	public boolean process() {
		if(!checkText()) {
			return false;
		}
		
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
