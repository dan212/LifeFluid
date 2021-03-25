import java.awt.Color;

public class FluidCell {

	private float value;
	
	private boolean alive;
	
	public FluidCell (float intV) {
		value = intV;
		if (value > 0f) {
			alive = true;
		} else {
			alive = false;
		}
	}
	
	public void reset() {
		value = 0f;
		alive = false;
	}
	
	public void setValue(float inpV) {
		value = inpV;
		if (value > 0.0) {
			alive = true;
		} else {
			alive = false;
		}
	}
	
	public float getValue() {
		return value;
	}
	
	public boolean isAlive() {
		return alive;
	}
	
	public int getColour() {
		int rgbColor = 0;
		if (alive) {
			rgbColor = Color.HSBtoRGB(value + 1f, 1f, 1f);
		}
		else {
			rgbColor = 0;
		}
		return rgbColor;
	}
}