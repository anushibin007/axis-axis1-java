/*
 * The Apache Software License, Version 1.1
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

package org.apache.axis.handlers.http;

import org.apache.axis.AxisFault;
import org.apache.axis.MessageContext;
import org.apache.axis.handlers.BasicHandler;
import org.apache.axis.utils.JavaUtils;
import org.apache.axis.utils.Messages;

import org.apache.axis.components.logger.LogFactory;
import org.apache.commons.logging.Log;


/** An <code>HTTPActionHandler</code> simply sets the context's TargetService
 * property from the HTTPAction property.  We expect there to be a
 * Router on the chain after us, to dispatch to the service named in
 * the SOAPAction.
 *
 * In the real world, this might do some more complex mapping of
 * SOAPAction to a TargetService.
 *
 * @author Glen Daniels (gdaniels@allaire.com)
 * @author Doug Davis (dug@us.ibm.com)
 */
public class HTTPActionHandler extends BasicHandler
{
    protected static Log log =
        LogFactory.getLog(HTTPActionHandler.class.getName());

    public void invoke(MessageContext msgContext) throws AxisFault
    {
        log.debug("Enter: HTTPActionHandler::invoke");

        /** If there's already a targetService then just return.
         */
        if ( msgContext.getService() == null ) {
            String action = (String) msgContext.getSOAPActionURI();
            log.debug( "  HTTP SOAPAction: " + action );
            
            /** The idea is that this handler only goes in the chain IF this
            * service does a mapping between SOAPAction and target.  Therefore
            * if we get here with no action, we're in trouble.
            */
            if (action == null) {
                throw new AxisFault( "Server.NoHTTPSOAPAction",
                    Messages.getMessage("noSOAPAction00"),
                    null, null );
            }
            
            action = action.trim();

            // handle empty SOAPAction
            if (action.length() > 0 && action.charAt(0) == '\"') {
                // assertTrue(action.endsWith("\"")
                if (action.equals("\"\"")) {
                    action = "";
                } else {
                    action = action.substring(1, action.length() - 1);
                }
            }
            
            // if action is zero-length string, don't set anything
            if (action.length() > 0) {
                msgContext.setTargetService( action );
            }
        }

        log.debug("Exit: HTTPActionHandler::invoke");
    }
}
