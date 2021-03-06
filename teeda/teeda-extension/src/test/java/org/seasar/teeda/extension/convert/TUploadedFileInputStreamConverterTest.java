/*
 * Copyright 2004-2011 the Seasar Foundation and the Others.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package org.seasar.teeda.extension.convert;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.seasar.teeda.core.JsfConstants;
import org.seasar.teeda.extension.component.html.THtmlInputFile;

/**
 * @author koichik
 */
public class TUploadedFileInputStreamConverterTest extends
        AbstractUploadedFileConverterTest {

    protected AbstractUploadedFileConverter getConverter() {
        return new TUploadedFileInputStreamConverter();
    }

    protected Object getValue() {
        return new ByteArrayInputStream("aaa".getBytes());
    }

    public void testGetAsObject() throws Exception {
        TUploadedFileInputStreamConverter converter = new TUploadedFileInputStreamConverter();
        converter.setTarget("aaa");
        getFacesContext().getExternalContext().getRequestMap().put(
                JsfConstants.SUBMITTED_COMMAND, "aaa");
        MockUploadedFile uploadedFile = new MockUploadedFile();
        uploadedFile.setString("aaa");
        THtmlInputFile inputFile = new THtmlInputFile();
        inputFile.setSubmittedValue(uploadedFile);
        InputStream is = (InputStream) converter.getAsObject(getFacesContext(),
                inputFile, "");
        assertEquals("aaa".getBytes().length, is.available());
    }

}
