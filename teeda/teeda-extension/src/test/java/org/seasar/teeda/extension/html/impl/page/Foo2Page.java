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
package org.seasar.teeda.extension.html.impl.page;

public class Foo2Page {

    private boolean initialized = false;

    private String aaa;

    private int bbb;

    private AaaDto aaaDto;

    /**
     * @return Returns the bbb.
     */
    public int getBbb() {
        return bbb;
    }

    /**
     * @param bbb The bbb to set.
     */
    public void setBbb(int bbb) {
        this.bbb = bbb;
    }

    public String initialize() {
        initialized = true;
        return null;
    }

    public boolean isInitialized() {
        return initialized;
    }

    public String getAaa() {
        return aaa;
    }

    public void setAaa(String aaa) {
        this.aaa = aaa;
    }

    public String doBbb() {
        return null;
    }

    /**
     * @return Returns the aaaDto.
     */
    public AaaDto getAaaDto() {
        return aaaDto;
    }

    /**
     * @param aaaDto The aaaDto to set.
     */
    public void setAaaDto(AaaDto aaaDto) {
        this.aaaDto = aaaDto;
    }
}
