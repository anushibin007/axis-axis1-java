/*
 * The Apache Software License, Version 1.1
 *
 *
 * Copyright (c) 2001 The Apache Software Foundation.  All rights
 * reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in
 *    the documentation and/or other materials provided with the
 *    distribution.
 *
 * 3. The end-user documentation included with the redistribution,
 *    if any, must include the following acknowledgment:
 *       "This product includes software developed by the
 *        Apache Software Foundation (http://www.apache.org/)."
 *    Alternately, this acknowledgment may appear in the software itself,
 *    if and wherever such third-party acknowledgments normally appear.
 *
 * 4. The names "Axis" and "Apache Software Foundation" must
 *    not be used to endorse or promote products derived from this
 *    software without prior written permission. For written
 *    permission, please contact apache@apache.org.
 *
 * 5. Products derived from this software may not be called "Apache",
 *    nor may "Apache" appear in their name, without prior written
 *    permission of the Apache Software Foundation.
 *
 * THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED.  IN NO EVENT SHALL THE APACHE SOFTWARE FOUNDATION OR
 * ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF
 * USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT
 * OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 * ====================================================================
 *
 * This software consists of voluntary contributions made by many
 * individuals on behalf of the Apache Software Foundation.  For more
 * information on the Apache Software Foundation, please see
 * <http://www.apache.org/>.
 */

/**
 * 
 *  UUIDGen adopted from the juddi project
 *  (http://sourceforge.net/projects/juddi/)
 * 
 */

package org.apache.axis.ime.internal.util.uuid;

import org.apache.axis.i18n.Messages;

/**
 * A Universally Unique Identifier (UUID) is a 128 bit number generated
 * according to an algorithm that is garanteed to be unique in time and space
 * from all other UUIDs. It consists of an IEEE 802 Internet Address and
 * various time stamps to ensure uniqueness. For a complete specification,
 * see ftp://ietf.org/internet-drafts/draft-leach-uuids-guids-01.txt [leach].
 *
 * @author  Steve Viens
 * @version 1.0 11/7/2000
 * @since   JDK1.2.2
 */
public abstract class UUIDGenFactory {
    private static final String defaultUUIDGenClassName = "org.apache.axis.ime.internal.util.uuid.SimpleUUIDGen";

    /**
     * getInstance
     *
     * Returns the singleton instance of UUIDGen
     */
    public static UUIDGen getUUIDGen(String uuidgenClassName) {
        UUIDGen uuidgen = null;

        if ((uuidgenClassName == null) || (uuidgenClassName.length() == 0)) {
            // use the default UUIDGen implementation
            uuidgenClassName = defaultUUIDGenClassName;
        }

        Class uuidgenClass = null;
        try {
            // instruct the class loader to load the UUIDGen implementation
            uuidgenClass = java.lang.Class.forName(uuidgenClassName);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(Messages.getMessage("uuidGenFactoryCNFE00", uuidgenClassName));
        }

        try {
            // try to instantiate the UUIDGen subclass
            uuidgen = (UUIDGen) uuidgenClass.newInstance();
        } catch (java.lang.Exception e) {
            throw new RuntimeException(Messages.getMessage("uuidGenFactoryException02", uuidgenClass.getName(), e.getMessage()));
        }

        return uuidgen;
    }

    /**
     * Release any aquired external resources and stop any background threads.
     */
    public static void destroyUUIDGen(UUIDGen uuidgen) {
        if (uuidgen != null)
            uuidgen.destroy();
    }
}