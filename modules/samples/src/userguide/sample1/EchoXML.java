
package userguide.sample1;

import org.apache.axis.om.OMElement;

public class EchoXML {
    public OMElement echo(OMElement element){
        element.getNextSibling();
        element.detach();
        return element;
    }
}