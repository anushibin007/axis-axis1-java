package org.apache.axis.impl.llom.factory;

import java.util.Stack;

import org.apache.axis.impl.llom.OMElementImpl;
import org.apache.axis.impl.llom.OMNamespaceImpl;
import org.apache.axis.impl.llom.OMTextImpl;
import org.apache.axis.impl.llom.SOAPBodyImpl;
import org.apache.axis.impl.llom.SOAPEnvelopeImpl;
import org.apache.axis.impl.llom.SOAPFaultImpl;
import org.apache.axis.impl.llom.SOAPHeaderBlockImpl;
import org.apache.axis.impl.llom.SOAPHeaderImpl;
import org.apache.axis.om.OMConstants;
import org.apache.axis.om.OMElement;
import org.apache.axis.om.OMFactory;
import org.apache.axis.om.OMNamespace;
import org.apache.axis.om.OMNode;
import org.apache.axis.om.OMText;
import org.apache.axis.om.OMXMLParserWrapper;
import org.apache.axis.om.SOAPBody;
import org.apache.axis.om.SOAPEnvelope;
import org.apache.axis.om.SOAPFault;
import org.apache.axis.om.SOAPHeader;
import org.apache.axis.om.SOAPHeaderBlock;

/**
 * Copyright 2001-2004 The Apache Software Foundation.
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * <p/>
 */
public class OMLinkedListImplFactory extends OMFactory {
	public static final int MAX_TO_POOL = 100;
	private Stack elements = new Stack();
	private Stack textNodes = new Stack();

	private OMElementImpl findElement(){
		OMElementImpl element = null;
		if(elements.isEmpty()){
			element = new OMElementImpl();
		}else{
			element = (OMElementImpl)elements.pop();
		}
		return element;
	}

	private OMTextImpl findText(){
		OMTextImpl text = null;
		if(elements.isEmpty()){
			text = new OMTextImpl();
		}else{
			text = (OMTextImpl)textNodes.pop();
		}
		return text;
	}
	
	public void free(OMNode node){
		int type = node.getType();
		switch(type){
			case OMNode.ELEMENT_NODE:
				if(elements.size() < MAX_TO_POOL){
					elements.push(node);				
				}
			break;
			case OMNode.TEXT_NODE:
			if(textNodes.size() < MAX_TO_POOL){
				textNodes.push(node);
			}
			break;
			default:
			//NOOP;
			
		}
	}


    public OMElement createOMElement(String localName, OMNamespace ns) {
		OMElementImpl element = findElement();
		element.init(localName, ns);
		return element;
    }

    public OMElement createOMElement(String localName, OMNamespace ns, OMElement parent, OMXMLParserWrapper builder) {
    	OMElementImpl element = findElement();
        element.init(localName, ns, parent, builder);
        return element;
    }

//    public OMNamedNode createOMNamedNode(String localName, OMNamespace ns, OMElement parent) {
//        return new OMNamedNodeImpl(localName, ns, parent);
//    }
//
//    public OMNamedNode createOMNamedNode(OMElement parent) {
//        return new OMNamedNodeImpl(parent);
//    }

    public OMNamespace createOMNamespace(String uri, String prefix) {
        return new OMNamespaceImpl(uri, prefix);
    }

//    public OMNode createOMNode(OMElement parent) {
//        return new OMNodeImpl(parent);
//    }

    public OMText createText(OMElement parent, String text) {
		OMTextImpl textNode = findText();
		textNode.init(parent, text);
		return textNode;
    }

    public OMText createText(String s) {
		OMTextImpl textNode = findText();
		textNode.init(s);
		return textNode;
    }

    public SOAPBody createSOAPBody(SOAPEnvelope envelope) {
        return new SOAPBodyImpl(envelope);
    }

    public SOAPBody createSOAPBody(String localName, OMNamespace ns, OMElement parent, OMXMLParserWrapper builder) {
        return new SOAPBodyImpl(localName, ns, parent, builder);
    }

    public SOAPEnvelope createSOAPEnvelope(OMNamespace ns, OMXMLParserWrapper builder) {
        return new SOAPEnvelopeImpl(ns, builder);
    }

    public SOAPEnvelope createSOAPEnvelope(OMNamespace ns) {
        return new SOAPEnvelopeImpl(ns);
    }

    public SOAPEnvelope createOMEnvelope(OMXMLParserWrapper parserWrapper) {
        throw new UnsupportedOperationException(); //TODO implement this
    }

    //TODO there should be a method to create an SOAPEnvelope giving OMXMLParserWrapper, as OMMessage is no longer there

    public SOAPHeader createSOAPHeader(SOAPEnvelope envelope) {
        return new SOAPHeaderImpl(envelope);
    }

    public SOAPHeader createSOAPHeader(String localName, OMNamespace ns, OMElement parent, OMXMLParserWrapper builder) {
        return new SOAPHeaderImpl(localName, ns, parent, builder);
    }

    public SOAPHeaderBlock createSOAPHeaderBlock(String localName, OMNamespace ns) {
        return new SOAPHeaderBlockImpl(localName, ns);
    }

    public SOAPHeaderBlock createSOAPHeaderBlock(String localName, OMNamespace ns, OMElement parent, OMXMLParserWrapper builder) {
        return new SOAPHeaderBlockImpl(localName, ns, parent, builder);
    }

    public SOAPFault createSOAPFault(SOAPBody parent, Exception e) {
        return new SOAPFaultImpl(parent, e);
    }

    public SOAPFault createSOAPFault(OMNamespace ns, SOAPBody parent, OMXMLParserWrapper builder) {
        return new SOAPFaultImpl(ns, parent, builder);
    }

    public SOAPEnvelope getDefaultEnvelope() {
        //Create an envelop
        OMNamespace ns = new OMNamespaceImpl(OMConstants.SOAP_ENVELOPE_NAMESPACE_URI, OMConstants.SOAPENVELOPE_NAMESPACE_PREFIX);
        SOAPEnvelopeImpl env = new SOAPEnvelopeImpl(ns);

        SOAPBodyImpl bodyImpl = new SOAPBodyImpl(env);
        bodyImpl.setComplete(true);
        env.addChild(bodyImpl);

        SOAPHeaderImpl headerImpl = new SOAPHeaderImpl(env);
        headerImpl.setComplete(true);
        env.addChild(headerImpl);

        return env;
    }
}