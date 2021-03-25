
public class ChaosRule implements FluidRule {

	@Override
	public float applyRule(float summ, FluidCell targCell) {
		java.util.Random rng = new java.util.Random();
		float res = 1f * rng.nextFloat();
		if (res > 1f) {
			res = 1f;
		}
		else if (res < 0f) {
			res = 0f;
		}
		return res;
	}

}
