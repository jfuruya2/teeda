/*
 * Copyright 2004-2005 the Seasar Foundation and the Others.
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
package org.seasar.teeda.core.webapp;

import javax.faces.FactoryFinder;
import javax.faces.context.ExternalContext;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.seasar.teeda.core.config.FacesConfigBuilder;
import org.seasar.teeda.core.config.assembler.AssemblerAssembler;
import org.seasar.teeda.core.config.element.FacesConfig;
import org.seasar.teeda.core.config.webapp.WebappConfigBuilder;
import org.seasar.teeda.core.config.webapp.element.WebappConfig;
import org.seasar.teeda.core.util.DIContainerUtil;

/**
 * @author shot
 */
public class JsfConfigureListener implements ServletContextListener {

    private static final String FACES_INIT_DONE = JsfConfigureListener.class
            .getName()
            + ".FACES_INIT_DONE";

    private static final String WEB_XML_PATH = "/WEB-INF/web.xml";

    public void contextInitialized(ServletContextEvent event) {
        initializeFaces(event.getServletContext());
    }

    public void contextDestroyed(ServletContextEvent event) {
        FactoryFinder.releaseFactories();
    }

    private void initializeFaces(ServletContext context) {

        Boolean b = (Boolean) context.getAttribute(FACES_INIT_DONE);
        boolean isAlreadyInitialized = (b != null) ? b.booleanValue() : false;
        if (!isAlreadyInitialized) {

            FacesConfigBuilder facesConfigBuilder = (FacesConfigBuilder) DIContainerUtil
                    .getComponent(FacesConfigBuilder.class);

            FacesConfig facesConfig = facesConfigBuilder.createFacesConfigs();

            AssemblerAssembler assembler = (AssemblerAssembler) DIContainerUtil
                    .getComponent(AssemblerAssembler.class);

            assembler.assembleFactories(facesConfig);

            assembler.assembleApplication(facesConfig);

            assembler.assembleManagedBeans(facesConfig);

            assembler.assmbleNavigationRules(facesConfig);

            assembler.assembleLifecycle(facesConfig);

            assembler.assembleRenderKits(facesConfig);

            WebappConfigBuilder webAppConfigBuilder = (WebappConfigBuilder) DIContainerUtil
                    .getComponent(WebappConfigBuilder.class);

            ExternalContext externalContext = (ExternalContext) DIContainerUtil
                    .getComponent(ExternalContext.class);

            WebappConfig webappConfig = webAppConfigBuilder
                    .build(externalContext.getResourceAsStream(WEB_XML_PATH));

            externalContext.getApplicationMap().put(webappConfig.getClass().getName(), webappConfig);
            
            context.setAttribute(FACES_INIT_DONE, Boolean.TRUE);

        }

    }

}
