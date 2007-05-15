/*
 * Copyright 2004-2007 the Seasar Foundation and the Others.
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
package org.seasar.teeda.extension.component;

import java.util.Iterator;

import javax.faces.component.ComponentUtil_;
import javax.faces.component.UIComponent;
import javax.faces.component.UIComponentBase;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;

import org.seasar.framework.util.AssertionUtil;

/**
 * @author shot
 */
public class TCondition extends UIComponentBase {

    public static final String COMPONENT_TYPE = "org.seasar.teeda.extension.Condition";

    public static final String COMPONENT_FAMILY = "org.seasar.teeda.extension.Condition";

    private static final String DEFAULT_RENDERER_TYPE = "org.seasar.teeda.extension.Condition";

    private Boolean submitted = null;

    public TCondition() {
        super.setRendererType(DEFAULT_RENDERER_TYPE);
    }

    public boolean isRendered() {
        /*
         if (FacesMessageUtil.hasErrorOrFatalMessage(getFacesContext())
         && submitted != null) {
         return submitted.booleanValue();
         } else {
         return super.isRendered();
         }
         */
        if (submitted != null) {
            return submitted.booleanValue();
        }
        return super.isRendered();
    }

    public String getFamily() {
        return COMPONENT_FAMILY;
    }

    /**
     * @return Returns the submitted.
     */
    public Boolean isSubmitted() {
        return submitted;
    }

    /**
     * @param submitted The submitted to set.
     */
    public void setSubmitted(Boolean b) {
        this.submitted = b;
    }

    public void processDecodes(FacesContext context) {
        AssertionUtil.assertNotNull("context", context);
        decode(context);
        processAppropriateAction(context, PhaseId.APPLY_REQUEST_VALUES);
    }

    public void processUpdates(FacesContext context) {
        AssertionUtil.assertNotNull("context", context);
        processUpdateModelAction(context, PhaseId.UPDATE_MODEL_VALUES);
    }

    public void processValidators(FacesContext context) {
        AssertionUtil.assertNotNull("context", context);
        processAppropriateAction(context, PhaseId.PROCESS_VALIDATIONS);
    }

    protected void processUpdateModelAction(FacesContext context, PhaseId phase) {
        if (submitted != null) {
            boolean b = submitted.booleanValue();
            submitted = null;
            if (!b) {
                return;
            }
        }
        for (Iterator children = getFacetsAndChildren(); children.hasNext();) {
            UIComponent component = (UIComponent) children.next();
            ComponentUtil_.processAppropriatePhaseAction(context, component,
                    phase);
        }
    }

    protected void processAppropriateAction(FacesContext context, PhaseId phase) {
        if (submitted != null && !submitted.booleanValue()) {
            return;
        }
        for (Iterator children = getFacetsAndChildren(); children.hasNext();) {
            UIComponent component = (UIComponent) children.next();
            ComponentUtil_.processAppropriatePhaseAction(context, component,
                    phase);
        }
    }
}
