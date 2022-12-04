package me.pepe.GameAPI.Utils.InteligentPositions;

import org.w3c.dom.Node;

import me.pepe.GameAPI.Game.Objects.ScreenObjects.Menu;
import me.pepe.GameAPI.Screen.Screen;
import me.pepe.GameAPI.Utils.DOMUtils;
import me.pepe.GameAPI.Utils.RenderLimits;
import me.pepe.GameAPI.Utils.Borders.HorizontalBorders;
import me.pepe.GameAPI.Utils.Borders.VerticalBorders;
import me.pepe.GameAPI.Utils.InteligentDimensions.InteligentDimension;

/*
   <positions>
    <position>
     <PixelInteligentPosition>
       <x>0</x>
       <y>0</y>
    </PixelInteligentPosition>q
  </position>
  <position>
      <PorcentInteligentPosition> <!-- Será necesario meter o el menu o el windows -->
       <x>0</x>
       <y>0</y>
     </PorcentInteligentPosition>
    </position>
    <position>
     <ReferenceBordersInteligentPosition> <!-- Será necesario meter o el menu o el windows -->
       <x>0</x>
       <y>0</y>
       <VerticalBorders>RIGHT</VerticalBorders>
       <HorizontalBorders>UP</HorizontalBorders>
     </ReferenceBordersInteligentPosition>
    </position>
    <position>
     <ReferenceObjectInteligentPosition> <!-- Será necesraio meter el objeto-->
        <objectID>null</objectID>
        <x>0</x>
        <y>0</y>
     </ReferenceObjectInteligentPosition>
    </position>
  </positions>
 */
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
	public int calculateX(InteligentDimension intRes, int porcent) {
		return calcX() + (renderLimits != null ? renderLimits.getX() : 0) + ((intRes.calcWeidht()  * porcent) / 100);
	}
	public int calculateY(InteligentDimension intRes, int porcent) {
		return calcY() + (renderLimits != null ? renderLimits.getY() : 0) + ((intRes.calcHeight()  * porcent) / 100);
	}
	public static InteligentPosition build(Object screenOrMenu, Node node) {
		if (screenOrMenu instanceof Screen || screenOrMenu instanceof Menu) {
			Screen screen = screenOrMenu instanceof Screen ? (Screen) screenOrMenu : null;
			Menu menu = screenOrMenu instanceof Menu ? (Menu) screenOrMenu : null;
			if (DOMUtils.getChild(node, "PorcentInteligentPosition") != null) {
				Node inteligentPositionNode = DOMUtils.getChild(node, "PorcentInteligentPosition");
				try {
					int x = Integer.valueOf(DOMUtils.getChild(inteligentPositionNode, "x").getTextContent());
					int y = Integer.valueOf(DOMUtils.getChild(inteligentPositionNode, "y").getTextContent());
					if (screen != null) {
						return new PorcentInteligentPosition(screen.getWindows(), x, y);
					} else if (menu != null) {
						return new PorcentInteligentPosition(menu, x, y);
					}
				} catch (Exception ex) {
					System.err.println(DOMUtils.getXML(node) + " no devolvió los valores esperados...");
				}
			} else if (DOMUtils.getChild(node, "ReferenceBordersInteligentPosition") != null) {
				Node inteligentPositionNode = DOMUtils.getChild(node, "ReferenceBordersInteligentPosition");
				try {
					int x = Integer.valueOf(DOMUtils.getChild(inteligentPositionNode, "x").getTextContent());
					int y = Integer.valueOf(DOMUtils.getChild(inteligentPositionNode, "y").getTextContent());
					VerticalBorders vertical = VerticalBorders.valueOf(DOMUtils.getChild(inteligentPositionNode, "VerticalBorders").getTextContent());
					HorizontalBorders horizontal = HorizontalBorders.valueOf(DOMUtils.getChild(inteligentPositionNode, "HorizontalBorders").getTextContent());
					if (screen != null) {
						return new ReferenceBordersInteligentPosition(x, y, screen.getWindows(), vertical, horizontal);
					} else if (menu != null) {
						return new ReferenceBordersInteligentPosition(x, y, menu, vertical, horizontal);
					}
				} catch (Exception ex) {
					System.err.println(DOMUtils.getXML(node) + " no devolvió los valores esperados...");
				}
			} else if (DOMUtils.getChild(node, "ReferenceObjectInteligentPosition") != null) {
				Node inteligentPositionNode = DOMUtils.getChild(node, "ReferenceObjectInteligentPosition");
				try {
					int x = Integer.valueOf(DOMUtils.getChild(inteligentPositionNode, "x").getTextContent());
					int y = Integer.valueOf(DOMUtils.getChild(inteligentPositionNode, "y").getTextContent());
					String objectID = DOMUtils.getChild(inteligentPositionNode, "objectID").getTextContent();
					if (screen != null) {
						if (screen.getGameObject(objectID) != null) {
							return new ReferenceObjectInteligentPosition(screen.getGameObject(objectID), x, y);
						} else {
							return new PixelInteligentPosition(0, 0);
						}
					} else if (menu != null) {
						if (menu.getGameObject(objectID) != null) {
							return new ReferenceObjectInteligentPosition(menu.getGameObject(objectID), x, y);
						} else {
							return new PixelInteligentPosition(0, 0);
						}
					}
				} catch (Exception ex) {
					System.err.println(DOMUtils.getXML(node) + " no devolvió los valores esperados...");
				}
			} else if (DOMUtils.getChild(node, "PixelInteligentPosition") != null) {
				Node inteligentPositionNode = DOMUtils.getChild(node, "PixelInteligentPosition");
				try {
					int x = Integer.valueOf(DOMUtils.getChild(inteligentPositionNode, "x").getTextContent());
					int y = Integer.valueOf(DOMUtils.getChild(inteligentPositionNode, "y").getTextContent());
					return new PixelInteligentPosition(x, y);
				} catch (Exception ex) {
					System.err.println(DOMUtils.getXML(node) + " no devolvió los valores esperados...");
				}
			}
		} else {
			System.err.println("El objeto introducido por la variable no es ni un Screen ni un Menu...");
		}
		return null;
	}
}