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
package javax.faces.internal;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;

/**
 * @author shot
 *
 * This class might be changed without notice. Please do not use it
 * excluding the JSF specification part.
 */
public class ConvertUtil {

    public static ConverterException wrappedByConverterException() {
        return new ConverterException();
    }

    public static ConverterException wrappedByConverterException(String message) {
        return new ConverterException(message);
    }

    public static ConverterException wrappedByConverterException(Throwable t) {
        return wrappedByConverterException(null, t);
    }

    public static ConverterException wrappedByConverterException(
            String message, Throwable t) {
        return new ConverterException(message, t);
    }

    /**
     * @deprecated
     */
    public static ConverterException wrappedByConverterException(
            Converter converter, FacesContext context, Object[] args) {
        return wrappedByConverterException(converter, context, args, null);
    }

    /**
     * @deprecated
     */
    public static ConverterException wrappedByConverterException(
            Converter converter, FacesContext context, Object[] args,
            Throwable t) {
        FacesMessage facesMessage = null;
        for (Class c = converter.getClass(); c != null && c != Object.class; c = c
                .getSuperclass()) {
            String conversionMessage = createConversionMessage(c);
            facesMessage = FacesMessageUtil.getMessage(context,
                    conversionMessage, args);
            if (facesMessage.getSummary() != null
                    || facesMessage.getDetail() != null) {
                break;
            }
        }
        return new ConverterException(facesMessage, t);
    }

    public static String createConversionMessage(Class c) {
        return c.getName() + ".CONVERSION";
    }

    public static String createConversionMessage(Converter converter) {
        return converter.getClass().getName() + ".CONVERSION";
    }

    public static Object[] createExceptionMessageArgs(UIComponent component,
            Object value) {
        return new Object[] { UIComponentUtil.getLabel(component), value };
    }
}
