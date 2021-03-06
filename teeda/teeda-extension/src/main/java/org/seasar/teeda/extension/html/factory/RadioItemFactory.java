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
package org.seasar.teeda.extension.html.factory;

import org.seasar.teeda.core.JsfConstants;
import org.seasar.teeda.extension.ExtensionConstants;
import org.seasar.teeda.extension.html.ActionDesc;
import org.seasar.teeda.extension.html.ElementNode;
import org.seasar.teeda.extension.html.PageDesc;

/**
 * te:radioItem
 * 
 * @author manhole
 */
public class RadioItemFactory extends AbstractElementProcessorFactory {

    private static final String TAG_NAME = "radioItem";

    protected String getTagName() {
        return TAG_NAME;
    }

    protected String getUri() {
        return ExtensionConstants.TEEDA_EXTENSION_URI;
    }

    public boolean isMatch(final ElementNode elementNode,
            final PageDesc pageDesc, final ActionDesc actionDesc) {
        if (pageDesc == null) {
            return false;
        }
        if (!JsfConstants.INPUT_ELEM.equalsIgnoreCase(elementNode.getTagName())) {
            return false;
        }
        if (!JsfConstants.RADIO_VALUE.equals(elementNode
                .getProperty(JsfConstants.TYPE_ATTR))) {
            return false;
        }
        final String name = elementNode.getProperty(JsfConstants.NAME_ATTR);
        if (name == null) {
            return false;
        }
        final ElementNode inputRadioNode = findParentInputRadioNode(
                elementNode, pageDesc, name);
        if (inputRadioNode == null) {
            return false;
        }
        return true;
    }

    private ElementNode findParentInputRadioNode(final ElementNode elementNode,
            final PageDesc pageDesc, final String name) {
        for (ElementNode parentNode = elementNode.getParent(); parentNode != null; parentNode = parentNode
                .getParent()) {
            final String tagName = parentNode.getTagName();
            if (!JsfConstants.SPAN_ELEM.equalsIgnoreCase(tagName) &&
                    !JsfConstants.DIV_ELEM.equalsIgnoreCase(tagName)) {
                continue;
            }
            final String id = parentNode.getId();
            if (id == null) {
                continue;
            }
            if (!pageDesc.hasProperty(id)) {
                continue;
            }
            if (!name.equals(id)) {
                continue;
            }
            return parentNode;
        }
        return null;
    }

    public boolean isLeaf() {
        return true;
    }

}
