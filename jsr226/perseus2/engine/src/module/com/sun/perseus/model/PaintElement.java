/*
 * $RCSfile: PaintElement.java,v $
 *
 * Copyright  1990-2009 Sun Microsystems, Inc. All Rights Reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License version
 * 2 only, as published by the Free Software Foundation. 
 * 
 * This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License version 2 for more details (a copy is
 * included at /legal/license.txt). 
 * 
 * You should have received a copy of the GNU General Public License
 * version 2 along with this work; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA 
 * 
 * Please contact Sun Microsystems, Inc., 4150 Network Circle, Santa
 * Clara, CA 95054 or visit www.sun.com if you need additional
 * information or have any questions. 
 */
package com.sun.perseus.model;

import com.sun.perseus.j2d.Transform;

import com.sun.perseus.j2d.Box;
import com.sun.perseus.j2d.PaintDef;
import com.sun.perseus.j2d.PaintServer;
import com.sun.perseus.j2d.PaintTarget;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

/**
 * Abstract base class for all elements which are paint server implementations.
 *
 * @version $Id: PaintElement.java,v 1.7 2006/06/29 10:47:33 ln156897 Exp $
 */
public abstract class PaintElement extends ElementNode  {
    /**
     * Defines whether the paint is in objectBoundingBox
     * space or in userSpaceOnUse space.
     */
    boolean isObjectBBox = false;

    /**
     * An array holding all current references (References instances) to this
     * PaintElement.
     */
    Vector references = new Vector(1);

    /**
     * @ownerDocument the DocumentNode this element belongs to.
     */
    public PaintElement(final DocumentNode ownerDocument) {
        super(ownerDocument);
    }

    /**
     * @param paintType the type of paint for which a PaintServer should be 
     *        computed. (e.g., "fill" or "stroke");
     * @param paintTarget the PaintTarget for which the PaintServer should be
     *        computed.
     * @return the PaintDef generated by the server.
     */
    public PaintServer getPaintServer(final String paintType, 
                                      final PaintTarget paintTarget) {
        Reference ref = new Reference();
        ref.setPaintTarget(paintType, paintTarget);
        return ref;
    }

    /**
     * Notifies all references that the Paint definitions has been modified.
     */
    protected void notifyPaintChange() {
        final int n = references.size();
        for (int i = 0; i < n; i++) {
            Reference ref = (Reference) references.elementAt(i);
            ref.paintTarget.onPaintServerUpdate(ref.paintType, ref);
        }
    }

    /**
     * Computes the paint in user space on use.
     *
     * @return the computed PaintDef.
     */
    protected abstract PaintDef computePaint();

    /**
     * The Reference class is used to bind a paint property (e.g., fill or 
     * stroke) to its related PaintElement.
     */
    class Reference implements PaintServer {
        /**
         * The referencing node.
         */
        protected PaintTarget paintTarget;

        /**
         * The referencing paintType.
         */
        protected String paintType;

        /**
         * @return the PaintDef generated by the server.
         */
        public PaintDef getPaintDef() {
            return computePaint();
        }

        /**
         * @param paintType a key that the PaintTarget can use to characterize 
         *        its interest in the PaintServer. For example, a PaintTarget 
         *        may be interested in the Paint both for stroking and filling 
         *        purposes.
         * @param paintTarget the PaintServerTarget listening to changes to the
         *        paint generated by this PaintServer.
         */
        public void setPaintTarget(final String paintType,
                                   final PaintTarget paintTarget) {
            this.paintType = paintType;
            this.paintTarget = paintTarget;
            references.addElement(this);
        }
        
        /**
         * Called when the PaintServer is no longer used. 
         */
        public void dispose() {
            references.removeElement(this);
        }
    }
}
