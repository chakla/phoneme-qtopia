/*
 *
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

#include <midpport_eventqueue.h>
#include <midp_logging.h>
#include <javacall_eventqueue.h>


/**
 * Creates the event queue lock.
 */
void
midp_createEventQueueLock(void) {
    javacall_create_event_queue_lock();
}

/**
 * Destroys the event queue lock.
 */
void
midp_destroyEventQueueLock(void) {
    javacall_destroy_event_queue_lock();
}

/**
 * Waits to get the event queue lock and then locks it.
 */
void
midp_waitAndLockEventQueue(void) {
    javacall_wait_and_lock_event_queue();
}

/**
 * Unlocks the event queue.
 */
void
midp_unlockEventQueue(void) {
    javacall_unlock_event_queue();
}

