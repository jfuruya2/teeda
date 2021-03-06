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
package javax.faces.el;

/**
 * @author shot
 */
public abstract class PropertyResolver {

    public PropertyResolver() {
    }

    public abstract Class getType(Object base, int index)
            throws EvaluationException, PropertyNotFoundException;

    public abstract Class getType(Object base, Object property)
            throws EvaluationException, PropertyNotFoundException;

    public abstract Object getValue(Object base, int index)
            throws EvaluationException, PropertyNotFoundException;

    public abstract Object getValue(Object base, Object property)
            throws EvaluationException, PropertyNotFoundException;

    public abstract boolean isReadOnly(Object base, int index)
            throws EvaluationException, PropertyNotFoundException;

    public abstract boolean isReadOnly(Object base, Object property)
            throws EvaluationException, PropertyNotFoundException;

    public abstract void setValue(Object base, int index, Object value)
            throws EvaluationException, PropertyNotFoundException;

    public abstract void setValue(Object base, Object property, Object value)
            throws EvaluationException, PropertyNotFoundException;

}
