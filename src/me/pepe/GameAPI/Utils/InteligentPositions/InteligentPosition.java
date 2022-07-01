package me.pepe.GameAPI.Utils.InteligentPositions;

import me.pepe.GameAPI.Utils.RenderLimits;
import me.pepe.GameAPI.Utils.InteligentResize.InteligentResize;

public abstract class InteligentPosition {
	private RenderLimits renderLimits;
	public InteligentPosition() {}
	public InteligentPosition(RenderLimits renderLimits) {
		this.renderLimits = renderLimits;
	}
	public RenderLimits getRenderLimits() {
		return renderLimits;
	}
	public boolean hasRenderLimits() {
		return renderLimits != null;
	}
	protected abstract int calcX();
	protected abstract int calcY();
	public int calculateX() {
		return calcX() + (renderLimits != null ? renderLimits.getX() : 0);
	}
	public int calculateY() {
		return calcY() + (renderLimits != null ? renderLimits.getY() : 0);
	}
	// El porcent indica en que porcentaje del objeto empieza a pintar...
	public int calculateX(InteligentResize intRes, int porcent) {
		return calcX() + (renderLimits != null ? renderLimits.getX() : 0) + ((intRes.calcHeight()  * porcent) / 100);
	}
	public int calculateY(InteligentResize intRes, int porcent) {
		return calcY() + (renderLimits != null ? renderLimits.getY() : 0) + ((intRes.calcHeight()  * porcent) / 100);
	}
}