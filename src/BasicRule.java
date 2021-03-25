import java.util.Random;
public class BasicRule implements FluidRule {

	@Override
	public float applyRule(float summ, FluidCell targCell) {
		Random rng = new Random();
		float cur = targCell.getValue();
		if (targCell.isAlive()) {
			if (summ > 5f) {
				cur = 0f;
			} else {
				cur += summ/20f;
			}
			//cur += 0.5f*rng.nextFloat() - 0.15f;
			cur += 0.5f*FluidScreen.getRandVal()- 0.15f;
		}
		else if (summ > 1f) {
			//cur =rng.nextFloat()*0.3f;
			cur =FluidScreen.getRandVal()*0.3f;
		}
		if (cur > 1f) {
			cur = 1f;
		}
		return cur;
	}

}
