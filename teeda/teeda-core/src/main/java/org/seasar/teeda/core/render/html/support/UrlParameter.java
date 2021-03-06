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
package org.seasar.teeda.core.render.html.support;

import org.seasar.framework.util.ArrayUtil;

/**
 * @author manhole
 */
public class UrlParameter {

    private String key;

    private String[] values = {};

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        if (ArrayUtil.isEmpty(values)) {
            return "";
        }
        return values[0];
    }

    public void setValue(String value) {
        values = new String[] { value };
    }

    public void addValue(String value) {
        this.values = (String[]) ArrayUtil.add(this.values, value);
    }

    public String[] getValues() {
        return values;
    }

    public void setValues(String[] values) {
        this.values = values;
    }

}
