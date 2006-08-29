/*
 * Copyright 2004-2006 the Seasar Foundation and the Others.
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
package org.seasar.teeda.extension;

/**
 * @author shot
 *
 */
public interface ExtensionConstants {

    //URI
    String TEEDA_EXTENSION_URI = "http://www.seasar.org/teeda/extension";

    //Elements
    String BODY_ELEM = "body";

    String TITLE_ELEM = "title";

    //Attributes
    String FRACTION_ATTR = "fraction";

    String GROUPING_SEPARATOR_ATTR = "groupingSeparator";

    String FRACTION_SEPARATOR_ATTR = "fractionSeparator";

    String VALIDATOR_STACK_ATTR = "javax.faces.webapp.VALIDATOR_STORE_ATTR";

    String CONVERTER_STACK_ATTR = "javax.faces.webapp.CONVERTER_STORE_ATTR";

    String PAGE_NAME_ATTR = "pageName";

    String ITEMS_NAME_ATTR = "itemsName";

    String ITEMS_ATTR = "items";

    String KEY_ATTR = "key";

    String PROPERTIES_NAME_ATTR = "propertiesName";

    //Suffix and prefix
    String GO_PREFIX = "go";

    String DO_PREFIX = "do";

    String ITEMS_SUFFIX = "Items";

    String MESSAGE_SUFFIX = "Message";

    String FORM_SUFFIX = "Form";

    String FRACTION_SUFFIX = "Fraction";

    String GROUPING_SEPARATOR_SUFFIX = "GroupingSeparator";

    String FRACTION_SEPARATOR_SUFFIX = "FractionSeparator";

    String TEEDA_CURRENY_STYLE_CLASS = "T_currency";

    String GRID = "Grid";

    String GRID_X = "GridX";

    String GRID_Y = "GridY";

    String GRID_XY = "GridXY";

    String SCROLL_HORIZONTAL = "scrollHorizontal";

    String SCROLL_VERTICAL = "scrollVertical";

}
