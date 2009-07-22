/*
 * $RCSfile: ScalableImage.java,v $
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

package javax.microedition.m2g;

import java.io.IOException;

/**
 *
 */
public abstract class ScalableImage {

    /**
     *
     */
    protected ScalableImage() {
    }

    /**
     *
     */
    public static ScalableImage createImage(java.io.InputStream stream, 
                                            ExternalResourceHandler handler) throws IOException {
        return SVGImage.createImage(stream, handler);
    }

    /**
     *
     */
    public static ScalableImage createImage(String URL, 
                                            ExternalResourceHandler handler) throws IOException {
        return SVGImage.createImage(URL, handler);
    }

    /**
     *
     */
    public abstract void setViewportWidth(int width);

    /**
     *
     */
    public abstract void setViewportHeight(int height);

    /**
     *
     */
    public abstract int getViewportWidth();

    /**
     *
     */
    public abstract int getViewportHeight();

    /**
     *
     */
    public abstract void requestCompleted( String URI, 
                                           java.io.InputStream resourceData ) throws IOException;

}
