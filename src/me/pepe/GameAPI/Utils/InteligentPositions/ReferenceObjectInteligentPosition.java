package me.pepe.GameAPI.Utils.InteligentPositions;

import me.pepe.GameAPI.Game.Objects.GameObject;
import me.pepe.GameAPI.Utils.RenderLimits;
import me.pepe.GameAPI.Utils.RenderType;

public class ReferenceObjectInteligentPosition extends InteligentPosition {
	private GameObject reference;
	private RenderType renderType;
	private int initialX; // posición relativa
	private int initialY;
	private int offSetX; // posición respecto al objeto
	private int offSetY;
	public ReferenceObjectInteligentPosition(GameObject reference, int initialX, int initialY) {
		this(reference, RenderType.PIXEL, initialX, initialY, 0, 0, null);
	}
	public ReferenceObjectInteligentPosition(GameObject reference, RenderType renderType, int initialX, int initialY) {
		this(reference, renderType, initialX, initialY, 0, 0, null);
	}
	public ReferenceObjectInteligentPosition(GameObject reference, RenderType renderType, int initialX, int initialY, int offSetX, int offSetY) {
		this(reference, renderType, initialX, initialY, offSetX, offSetY, null);
	}
	public ReferenceObjectInteligentPosition(GameObject reference, RenderType renderType, int initialX, int initialY, int offSetX, int offSetY, RenderLimits renderLimits) {
		super(renderLimits);
		this.reference = reference;
		this.renderType = renderType;
		this.initialX = initialX;
		this.initialY = initialY;
		this.offSetX = offSetX;
		this.offSetY = offSetY;
	}
	public GameObject getReference() {
		return reference;
	}
	@Override
	protected int calcX() {
		return renderType.equals(RenderType.PIXEL) ? (int) (reference.getX() + initialX + offSetX): 0;
	}
	@Override
	protected int calcY() {
		return renderType.equals(RenderType.PIXEL) ? (int) (reference.getY() + initialY + offSetY): 0;
	}
}
