/*
 * Copyright 2011 JBoss Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.drools.guvnor.client.widgets.wizards;

import com.google.gwt.user.client.ui.IsWidget;

/**
 * A page for a Wizard
 */
public interface WizardPage
    extends
    IsWidget {

    /**
     * Page title
     * 
     * @return
     */
    String getTitle();

    /**
     * Is the page completed
     * 
     * @return
     */
    boolean isComplete();

    /**
     * Initialise the page with things that don't change between page visits
     */
    void initialise();

    /**
     * Prepare the page before it is displayed with things that can change
     * between page visits
     */
    void prepareView();

}
