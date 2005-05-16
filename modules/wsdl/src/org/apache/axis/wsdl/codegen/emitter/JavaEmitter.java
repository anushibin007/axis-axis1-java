package org.apache.axis.wsdl.codegen.emitter;

import java.util.Collection;
import java.util.Iterator;

import org.apache.axis.wsdl.codegen.CodeGenConfiguration;
import org.apache.axis.wsdl.codegen.CodeGenerationException;
import org.apache.axis.wsdl.databinding.JavaTypeMapper;
import org.apache.axis.wsdl.databinding.TypeMapper;
import org.apache.crimson.tree.XmlDocument;
import org.apache.wsdl.WSDLBinding;
import org.apache.wsdl.WSDLInterface;
import org.apache.wsdl.WSDLOperation;
import org.w3c.dom.Attr;
import org.w3c.dom.Element;

/*
* Copyright 2004,2005 The Apache Software Foundation.
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*      http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*
* Java emiiter implementation
*/
public class JavaEmitter extends MultiLanguageClientEmitter{

    /**
     *
     * @param configuration
     */
    public JavaEmitter(CodeGenConfiguration configuration) {
        this.configuration = configuration;
        this.mapper = new JavaTypeMapper();

    }

    /**
     *
     * @param configuration
     * @param mapper
     */
    public JavaEmitter(CodeGenConfiguration configuration,TypeMapper mapper) {
        this.configuration = configuration;
        this.mapper =mapper;

    }

      

}