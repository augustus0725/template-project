/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.processors.myprocessor;

import org.apache.nifi.annotation.behavior.InputRequirement;
import org.apache.nifi.components.PropertyDescriptor;
import org.apache.nifi.flowfile.FlowFile;
import org.apache.nifi.annotation.behavior.WritesAttribute;
import org.apache.nifi.annotation.behavior.WritesAttributes;
import org.apache.nifi.annotation.lifecycle.OnScheduled;
import org.apache.nifi.annotation.documentation.CapabilityDescription;
import org.apache.nifi.annotation.documentation.SeeAlso;
import org.apache.nifi.annotation.documentation.Tags;
import org.apache.nifi.processor.AbstractProcessor;
import org.apache.nifi.processor.ProcessContext;
import org.apache.nifi.processor.ProcessSession;
import org.apache.nifi.processor.ProcessorInitializationContext;
import org.apache.nifi.processor.Relationship;
import org.apache.nifi.processor.io.InputStreamCallback;
import org.apache.nifi.processor.io.OutputStreamCallback;
import org.apache.nifi.processor.util.StandardValidators;
import org.apache.nifi.stream.io.StreamUtils;

import java.io.*;
import java.util.*;

@Tags({"strings"})
@InputRequirement(InputRequirement.Requirement.INPUT_REQUIRED)
@CapabilityDescription("Append a string to the flowfile content.")
@SeeAlso({})
// Processor 会读取的一些属性
//@ReadsAttributes({@ReadsAttribute(attribute="", description="")})
// Processor 会新写入的一些属性
@WritesAttributes({@WritesAttribute(attribute="string.total.length", description="The length after appending")})
public class MyProcessor extends AbstractProcessor {

    public static final PropertyDescriptor STRING_APPENDED = new PropertyDescriptor
            .Builder().name("String Appended")
            .displayName("String Appended")
            .description("The string will append it to the content of flowfile")
            .required(true)
            .addValidator(StandardValidators.NON_EMPTY_VALIDATOR)
            .build();

    public static final Relationship REL_SUCCESS = new Relationship.Builder()
            .name("success")
            .description("All strings that appended will route to success")
            .build();

    private List<PropertyDescriptor> descriptors;

    private Set<Relationship> relationships;

    @Override
    protected void init(final ProcessorInitializationContext context) {
        descriptors = new ArrayList<>();
        descriptors.add(STRING_APPENDED);
        descriptors = Collections.unmodifiableList(descriptors);

        relationships = new HashSet<>();
        relationships.add(REL_SUCCESS);
        relationships = Collections.unmodifiableSet(relationships);
    }

    @Override
    public Set<Relationship> getRelationships() {
        return this.relationships;
    }

    @Override
    public final List<PropertyDescriptor> getSupportedPropertyDescriptors() {
        return descriptors;
    }

    @OnScheduled
    public void onScheduled(final ProcessContext context) {

    }

    @Override
    public void onTrigger(final ProcessContext context, final ProcessSession session) {
        FlowFile flowFile = session.get();
        if ( flowFile == null ) {
            return;
        }
        // TODO implement
        String appendString = context.getProperty(STRING_APPENDED).getValue();
        final ByteArrayOutputStream baos = new ByteArrayOutputStream((int) flowFile.getSize() + appendString.length());
        session.read(flowFile, in -> {
            try (BufferedInputStream input = new BufferedInputStream(in)) {
                StreamUtils.copy(input, baos);
            }
            baos.close();
        });
        try {
            baos.write(appendString.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        // update flowfile
        FlowFile updateFlowFile = session.write(flowFile, new OutputStreamCallback() {
            @Override
            public void process(OutputStream out) throws IOException {
                out.write(baos.toByteArray());
            }
        });
        // transfer
        session.transfer(updateFlowFile, REL_SUCCESS);
    }
}
