/*
 * Portions Copyright  2000-2009 Sun Microsystems, Inc. All Rights
 * Reserved.  Use is subject to license terms.
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

/*
 * Copyright (c) 2006 World Wide Web Consortium,
 *
 * (Massachusetts Institute of Technology, European Research Consortium for
 * Informatics and Mathematics, Keio University). All Rights Reserved. This
 * work is distributed under the W3C(r) Software License [1] in the hope that
 * it will be useful, but WITHOUT ANY WARRANTY; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 *
 * [1] http://www.w3.org/Consortium/Legal/2002/copyright-software-20021231
 */

package org.w3c.dom.events;

import org.w3c.dom.Node;

/**
 * The <code>MutationEvent</code> interface provides specific contextual 
 * information associated with Mutation events. 
 * <p>See also the 
 * <a href='http://www.w3.org/TR/2000/REC-DOM-Level-2-Events-20001113'>
 * Document Object Model (DOM) Level 2 Events Specification</a>.
 * @since DOM Level 2
 */
public interface MutationEvent extends Event {
    // attrChangeType
    /**
     * The <code>Attr</code> was modified in place.
     */
    public static final short MODIFICATION              = 1;
    /**
     * The <code>Attr</code> was just added.
     */
    public static final short ADDITION                  = 2;
    /**
     * The <code>Attr</code> was just removed.
     */
    public static final short REMOVAL                   = 3;

    /**
     *  <code>relatedNode</code> is used to identify a secondary node related 
     * to a mutation event. For example, if a mutation event is dispatched 
     * to a node indicating that its parent has changed, the 
     * <code>relatedNode</code> is the changed parent. If an event is 
     * instead dispatched to a subtree indicating a node was changed within 
     * it, the <code>relatedNode</code> is the changed node. In the case of 
     * the DOMAttrModified event it indicates the <code>Attr</code> node 
     * which was modified, added, or removed. 
     */
    public Node getRelatedNode();

    /**
     *  <code>prevValue</code> indicates the previous value of the 
     * <code>Attr</code> node in DOMAttrModified events, and of the 
     * <code>CharacterData</code> node in DOMCharDataModified events. 
     */
    public String getPrevValue();

    /**
     *  <code>newValue</code> indicates the new value of the <code>Attr</code> 
     * node in DOMAttrModified events, and of the <code>CharacterData</code> 
     * node in DOMCharDataModified events. 
     */
    public String getNewValue();

    /**
     *  <code>attrName</code> indicates the name of the changed 
     * <code>Attr</code> node in a DOMAttrModified event. 
     */
    public String getAttrName();

    /**
     *  <code>attrChange</code> indicates the type of change which triggered 
     * the DOMAttrModified event. The values can be <code>MODIFICATION</code>
     * , <code>ADDITION</code>, or <code>REMOVAL</code>. 
     */
    public short getAttrChange();

    /**
     *  The <code>initMutationEvent</code> method is used to initialize the 
     * value of a <code>MutationEvent</code> object and has the same 
     * behavior as <code>Event.initEvent()</code>. 
     * @param typeArg  Refer to the <code>Event.initEvent()</code> method for 
     *   a description of this parameter. 
     * @param canBubbleArg  Refer to the <code>Event.initEvent()</code> 
     *   method for a description of this parameter. 
     * @param cancelableArg  Refer to the <code>Event.initEvent()</code> 
     *   method for a description of this parameter. 
     * @param relatedNodeArg  Specifies <code>MutationEvent.relatedNode</code>.
     *   This value may be null.
     * @param prevValueArg  Specifies <code>MutationEvent.prevValue</code>. 
     *   This value may be null. 
     * @param newValueArg  Specifies <code>MutationEvent.newValue</code>. 
     *   This value may be null. 
     * @param attrNameArg  Specifies <code>MutationEvent.attrname</code>. 
     *   This value may be null. 
     * @param attrChangeArg  Specifies <code>MutationEvent.attrChange</code>. 
     */
    public void initMutationEvent(String typeArg, 
                                  boolean canBubbleArg, 
                                  boolean cancelableArg, 
                                  Node relatedNodeArg, 
                                  String prevValueArg, 
                                  String newValueArg, 
                                  String attrNameArg, 
                                  short attrChangeArg);

    /**
     *  The <code>initMutationEventNS</code> method is used to initialize the 
     * value of a <code>MutationEvent</code> object and has the same 
     * behavior as <code>Event.initEventNS()</code>. 
     * @param namespaceURIArg  Refer to the <code>Event.initEventNS()</code> 
     *   method for a description of this parameter. 
     * @param typeArg  Refer to the <code>Event.initEventNS()</code> method 
     *   for a description of this parameter. 
     * @param canBubbleArg  Refer to the <code>Event.initEventNS()</code> 
     *   method for a description of this parameter. 
     * @param cancelableArg  Refer to the <code>Event.initEventNS()</code> 
     *   method for a description of this parameter. 
     * @param relatedNodeArg  Refer to the 
     *   <code>MutationEvent.initMutationEvent()</code> method for a 
     *   description of this parameter. 
     * @param prevValueArg  Refer to the 
     *   <code>MutationEvent.initMutationEvent()</code> method for a 
     *   description of this parameter. 
     * @param newValueArg  Refer to the 
     *   <code>MutationEvent.initMutationEvent()</code> method for a 
     *   description of this parameter. 
     * @param attrNameArg  Refer to the 
     *   <code>MutationEvent.initMutationEvent()</code> method for a 
     *   description of this parameter. 
     * @param attrChangeArg  Refer to the 
     *   <code>MutationEvent.initMutationEvent()</code> method for a 
     *   description of this parameter.
     */
    public void initMutationEventNS(String namespaceURIArg, 
                                    String typeArg, 
                                    boolean canBubbleArg, 
                                    boolean cancelableArg, 
                                    Node relatedNodeArg, 
                                    String prevValueArg, 
                                    String newValueArg, 
                                    String attrNameArg, 
                                    short attrChangeArg);
}
