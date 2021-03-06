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
package org.seasar.teeda.extension.annotation.handler;

import java.util.HashMap;
import java.util.Map;

import org.seasar.framework.beans.BeanDesc;
import org.seasar.framework.beans.factory.BeanDescFactory;
import org.seasar.framework.container.ComponentDef;
import org.seasar.framework.container.S2Container;
import org.seasar.framework.container.factory.SingletonS2ContainerFactory;

/**
 * @author higa
 *
 */
public abstract class AbstractScopeAnnotationHandler implements
        ScopeAnnotationHandler {

    public Map getPropertyScopes(String componentName) {
        S2Container container = SingletonS2ContainerFactory.getContainer();
        ComponentDef cd = container.getComponentDef(componentName);
        BeanDesc beanDesc = BeanDescFactory.getBeanDesc(cd.getComponentClass());
        Map ret = new HashMap();
        setupPropertyScopes(beanDesc, ret);
        return ret;
    }

    protected abstract void setupPropertyScopes(BeanDesc beanDesc, Map scopes);
}
