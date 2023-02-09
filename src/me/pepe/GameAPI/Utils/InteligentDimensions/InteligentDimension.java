package me.pepe.GameAPI.Utils.InteligentDimensions;

import org.w3c.dom.Node;

import me.pepe.GameAPI.Utils.DOMUtils;
import me.pepe.GameAPI.Utils.InteligentDimensions.ExtendInteligentDimension.InteligentExtendiblePosibility;

/*
   </dimensions>
      <PixelInteligentDimension>
        <weidht>0</weidht>
        <height>0</height>
      </PixelInteligentDimension>
      <ExtendInteligentDimension> <!-- necesitar� el screen -->
      	<extendiblePosibility>WEIDHT</extendiblePosibility>
      	<offWeidht>0</offWeidht>
      	<offHeight>0</offHeight>
      	<axuDim>
      	  <PixelInteligentDimension>
        	<weidht>0</weidht>
        	<height>0</height>
      	  </PixelInteligentDimension>
      	</auxDim>
      </ExtendInteligentDimension>
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
				System.err.println(DOMUtils.getXML(node) + " no devolvó los valores esperados...");
			}
		} else if (DOMUtils.getChild(node, "ExtendInteligentDimension") != null) {
			Node inteligentResizeNode = DOMUtils.getChild(node, "ExtendInteligentDimension");
			try {
				InteligentExtendiblePosibility extendiblePosibility = InteligentExtendiblePosibility.valueOf(DOMUtils.getChild(inteligentResizeNode, "extendiblePosibility").getTextContent());
				int offWeidht = 0;
				int offHeight = 0;
				Node offWNode = DOMUtils.getChild(inteligentResizeNode, "offWeidht");
				Node offHNode = DOMUtils.getChild(inteligentResizeNode, "offHeight");
				if (offWNode != null) {
					offWeidht = Integer.valueOf(offWNode.getTextContent());
				}
				if (offHNode != null) {
					offHeight = Integer.valueOf(offHNode.getTextContent());
				}
				Node auxDim = DOMUtils.getChild(inteligentResizeNode, "auxDim");
				if (auxDim != null) {
					return new ExtendInteligentDimension(extendiblePosibility, build(auxDim), offWeidht, offHeight); // they add object and screen/menu on add in screen/menu
				} else {
					throw new NullPointerException("No se pudo recoger el 'auxDim'\n" + DOMUtils.getXML(inteligentResizeNode));
				}
			} catch (Exception ex) {
				System.err.println(DOMUtils.getXML(node) + " no devolvió los valores esperados...");
			}
		}
		return null;		
	}
}
