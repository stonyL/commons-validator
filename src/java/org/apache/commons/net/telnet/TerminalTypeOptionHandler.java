package org.apache.commons.net.telnet;

/* ====================================================================
 * The Apache Software License, Version 1.1
 *
 * Copyright (c) 2003-2004 The Apache Software Foundation.  All rights
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
 * 4. The names "Apache" and "Apache Software Foundation" and
 *    "Apache Commons" must not be used to endorse or promote products
 *    derived from this software without prior written permission. For
 *    written permission, please contact apache@apache.org.
 *
 * 5. Products derived from this software may not be called "Apache",
 *    nor may "Apache" appear in their name, without
 *    prior written permission of the Apache Software Foundation.
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

/***
 * Implements the telnet terminal type option RFC 1091.
 * <p>
 * @author Bruno D'Avanzo
 ***/
public class TerminalTypeOptionHandler extends TelnetOptionHandler
{
    /***
     * Terminal type
     ***/
    private String termType = null;

    /***
     * Terminal type option
     ***/
    protected static final int TERMINAL_TYPE = 24;

    /***
     * Send (for subnegotiation)
     ***/
    protected static final int TERMINAL_TYPE_SEND =  1;

    /***
     * Is (for subnegotiation)
     ***/
    protected static final int TERMINAL_TYPE_IS =  0;

    /***
     * Constructor for the TerminalTypeOptionHandler. Allows defining desired
     * initial setting for local/remote activation of this option and
     * behaviour in case a local/remote activation request for this
     * option is received.
     * <p>
     * @param termtype - terminal type that will be negotiated.
     * @param initlocal - if set to true, a WILL is sent upon connection.
     * @param initremote - if set to true, a DO is sent upon connection.
     * @param acceptlocal - if set to true, any DO request is accepted.
     * @param acceptremote - if set to true, any WILL request is accepted.
     ***/
    public TerminalTypeOptionHandler(String termtype,
                                boolean initlocal,
                                boolean initremote,
                                boolean acceptlocal,
                                boolean acceptremote)
    {
        super(TelnetOption.TERMINAL_TYPE, initlocal, initremote,
                                      acceptlocal, acceptremote);
        termType = termtype;
    }

    /***
     * Constructor for the TerminalTypeOptionHandler. Initial and accept
     * behaviour flags are set to false
     * <p>
     * @param termtype - terminal type that will be negotiated.
     ***/
    public TerminalTypeOptionHandler(String termtype)
    {
        super(TelnetOption.TERMINAL_TYPE, false, false, false, false);
        termType = termtype;
    }

    /***
     * Implements the abstract method of TelnetOptionHandler.
     * <p>
     * @param suboptionData - the sequence received, whithout IAC SB & IAC SE
     * @param suboptionLength - the length of data in suboption_data
     * <p>
     * @return terminal type information
     ***/
    public int[] answerSubnegotiation(int suboptionData[], int suboptionLength)
    {
        if ((suboptionData != null) && (suboptionLength > 1)
            && (termType != null))
        {
            if ((suboptionData[0] == TERMINAL_TYPE)
                && (suboptionData[1] == TERMINAL_TYPE_SEND))
            {
                int response[] = new int[termType.length() + 2];

                response[0] = TERMINAL_TYPE;
                response[1] = TERMINAL_TYPE_IS;

                for (int ii = 0; ii < termType.length(); ii++)
                {
                    response[ii + 2] = (int) termType.charAt(ii);
                }

                return response;
            }
        }
        return null;
    }

    /***
     * Implements the abstract method of TelnetOptionHandler.
     * <p>
     * @return always null (no response to subnegotiation)
     ***/
    public int[] startSubnegotiationLocal()
    {
        return null;
    }

    /***
     * Implements the abstract method of TelnetOptionHandler.
     * <p>
     * @return always null (no response to subnegotiation)
     ***/
    public int[] startSubnegotiationRemote()
    {
        return null;
    }
}
