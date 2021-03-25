
public class GrowRule implements FluidRule {

	@Override
	public float applyRule(float summ, FluidCell targCell) {
		float res = 0f;
		if (!targCell.isAlive()&summ > 0f) {
			res = 0.1f;
		}
		else if (targCell.isAlive()) {
			if (summ > 5f) {
				res = targCell.getValue() - 0.1f;
			}
			else {
				res = targCell.getValue() + 0.1f;
			}
		}
		if (res > 1f) {
			res = 1f;
		}
		else if (res < 0f) res = 0f;
		return res;
	}

}
