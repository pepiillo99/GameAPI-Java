package me.pepe.GameAPI.Utils.InteligentDimensions;

import org.w3c.dom.Node;

import me.pepe.GameAPI.Utils.DOMUtils;

/*
   </dimensions>
      <PixelInteligentDimension>
        <weidht>0</weidht>
        <height>0</height>
      </PixelInteligentDimension>
    </dimensions>
 */
public abstract class InteligentDimension {
	public abstract int calcWeidht(); // x
	public abstract int calcHeight(); // z
	public static InteligentDimension build(Node node) {
		if (DOMUtils.getChild(node, "PixelInteligentDimension") != null) {
			Node inteligentResizeNode = DOMUtils.getChild(node, "PixelInteligentDimension");
			try {
				int weidht = Integer.valueOf(DOMUtils.getChild(inteligentResizeNode, "weidht").getTextContent());
				int height = Integer.valueOf(DOMUtils.getChild(inteligentResizeNode, "height").getTextContent());
				return new PixelInteligentDimension(weidht, height);
			} catch (Exception ex) {
				System.err.println(DOMUtils.getXML(node) + " no devolvió los valores esperados...");
			}
		}
		return null;		
	}
}
