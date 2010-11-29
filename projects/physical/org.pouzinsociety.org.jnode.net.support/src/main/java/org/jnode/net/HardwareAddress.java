/*
 * $Id: HardwareAddress.java 4213 2008-06-08 02:02:10Z crawley $
 *
 * JNode.org
 * Copyright (C) 2003-2006 JNode.org
 *
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published
 * by the Free Software Foundation; either version 2.1 of the License, or
 * (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful, but 
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public 
 * License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this library; If not, write to the Free Software Foundation, Inc., 
 * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 */
/*
 * 2008 - 2010 (c) Waterford Institute of Technology
 *		   TSSG, EU ICT 4WARD
 *
 * 2010 (c) Pouzin Society
 *   - Forked from EU ICT 4WARD Open Source Distribution.
 *   - Organisation Strings updated to reflect fork.
 *
 *
 * Author        : pphelan(at)tssg.org
 *
 * Modifications : Changes to port JNode code base to OSGi platform.
 *                 - multicast support.
 *
 */
package org.jnode.net;

/**
 * Interface for network hardware addresses, such as an ethernet address.
 * @author epr
 */
public interface HardwareAddress {

    /**
     * Is this address equal to the given address.
     * @param o
     */
    public boolean equals(HardwareAddress o);

    /**
     * Gets the length of this address in bytes
     */
    public int getLength();

    /**
     * Gets the address-byte at a given index
     * @param index
     */
    public byte get(int index);

    /**
     * Write this address to a given offset in the given buffer
     * @param skbuf
     * @param skbufOffset
     */
    public void writeTo(SocketBuffer skbuf, int skbufOffset);

    /**
     * Is this a broadcast address?
     */
    public boolean isBroadcast();

    /**
     * Is this a multicast address?
     */
    public boolean isMulticast();
    
    /**
     * Gets the default broadcast address for this kind of hardware address.
     */
    public HardwareAddress getDefaultBroadcastAddress();

    /**
     * Gets the type of this address.
     * This type is used by (e.g.) ARP.
     */
    public int getType();

}