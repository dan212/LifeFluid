
public class ClassicRules implements FluidRule {

	@Override
	public float applyRule(float summ, FluidCell targCell) {
		if (targCell.isAlive()) {
			if (summ == 2f | summ == 3f) {
				return 1f;
			}
			else {
				return 0f;
			}
		}
		else {
			if (summ == 3f) {
				return 1f;
			}
		}
		return 0;
	}

}
