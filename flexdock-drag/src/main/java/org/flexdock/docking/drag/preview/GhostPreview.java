/*
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package org.flexdock.docking.drag.preview;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Component;
import java.awt.Composite;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Map;

import org.flexdock.docking.Dockable;
import org.flexdock.docking.DockingPort;
import org.flexdock.docking.drag.effects.DefaultPreview;
import org.flexdock.util.SwingUtility;

/**
 * @author Christopher Butler
 */
public class GhostPreview extends DefaultPreview {
    private static final String PREVIOUS_BOUNDS = "TestPreview.PREVIOUS_BOUNDS";
    private static final String PREVIEW_IMG = "TestPreview.PREVIEW_IMG";

    @Override
    public Polygon createPreviewPolygon(Component dockable, DockingPort port, Dockable hover, String targetRegion, Component paintingTarget, Map dragInfo) {
        // create the standard preview polygon
        Polygon polygon = super.createPreviewPolygon(dockable, port, hover, targetRegion, paintingTarget, dragInfo);
        if(polygon==null) {
            return null;
        }

        // check to see if the current polygon bounds differ from the
        // last time we calculated them.
        Rectangle prevBounds = (Rectangle)dragInfo.get(PREVIOUS_BOUNDS);
        Rectangle bounds = polygon.getBounds();
        // if the polygon bounds have changed, create a new BufferedImage
        // to represent the dockable preview.  this image will be used
        // in drawPreview()
        if(!bounds.equals(prevBounds)) {
            // store the original dockable size
            Dimension origSize = dockable.getSize();
            // change the dockable to match the preview size while
            // we generate an image off of it
            dockable.setSize(bounds.getSize());
            BufferedImage img = SwingUtility.createImage(dockable);
            // store the image for use in drawPreview()
            dragInfo.put(PREVIEW_IMG, img);
            // restore the original dockable size
            dockable.setSize(origSize);
        }
        // store the current preview bounds so we'll know the next time we need
        // to regenerate the preview image
        dragInfo.put(PREVIOUS_BOUNDS, bounds);
        // return the polygon
        return polygon;
    }

    @Override
    public void drawPreview(Graphics2D g, Polygon poly, Dockable dockable, Map dragInfo) {
        // grab the preview image created in createPreviewPolygon()
        BufferedImage image = (BufferedImage)dragInfo.get(PREVIEW_IMG);
        if(image==null) {
            return;
        }

        // create a solid preview outline
        Rectangle bounds = poly.getBounds();
        g.setColor(Color.BLACK);
        g.drawRect(bounds.x, bounds.y, bounds.width, bounds.height);

        // make the graphics 50% translucent
        Composite composite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f);
        g.setComposite(composite);
        // now draw the preview image
        g.drawImage(image, bounds.x, bounds.y, null);
    }

    @Override
    protected Rectangle createTabbedPaneRect(DockingPort port, Component hover) {
        if(hover!=null) {
            return hover.getBounds();
        }
        return super.createTabbedPaneRect(port, hover);
    }
}
