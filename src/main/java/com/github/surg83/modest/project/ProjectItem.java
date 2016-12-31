/**
 * MIT License
 * Copyright (c) 2016 Michal Czudowski
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.github.surg83.modest.project;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.UUID;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * ProjectItem class.
 * 
 * Project item base.
 * 
 * @author Michał Czudowski <michal.czudowski@gmail.com>
 */
abstract public class ProjectItem {
    
    /**
     * Form textfield.
     */
    public final static String FORM_TEXTFIELD = "textfield";
    
    /**
     * Form textarea.
     */
    public final static String FORM_TEXTAREA = "textarea";
    
    /**
     * Unique identifier of item.
     */
    private String id;

    /**
     * Get unique identifier of item.
     * 
     * @return 
     */
    public String getId() {
        return id;
    }
    
    /**
     * Type of item.
     */
    protected String type;

    /**
     * Get type of item.
     * 
     * @return 
     */
    public String getType() {
        return type;
    }

    /**
     * Name of item.
     */
    private String name;

    /**
     * Get name of item.
     * 
     * @return 
     */
    public String getName() {
        return name;
    }

    /**
     * Set name of item.
     * 
     * @param name 
     */
    public void setName(String name) {
        this.name = name;
    }
    
    /**
     * Item icon.
     */
    protected String icon;

    /**
     * Get icon.
     * 
     * @return 
     */
    public String getIcon() {
        return icon;
    }
    
    /**
     * Root element.
     */
    protected boolean root = false;

    /**
     * Is root element.
     *
     * @return 
     */
    public boolean isRoot() {
        return root;
    }
    
    /**
     * Fields definition.
     */
    protected LinkedHashMap<String, String> fieldForm = new LinkedHashMap<>();

    /**
     * Get fields definition.
     * @return
     */
    public LinkedHashMap<String, String> getFieldForm() {
        return fieldForm;
    }
    
    /**
     * Fields values.
     */
    protected HashMap<String, String> fieldValue = new HashMap<>();

    /**
     * ProjectItem class constructor.
     * 
     * @param id
     * @param name 
     */
    public ProjectItem(String id, String name) {
        this.id = id;
        this.name = name;
    }
    
    /**
     * Get field value.
     * 
     * @param field
     * @return 
     */
    public String getFieldValue(String field) {
        String value = fieldValue.get(field);
        return (value != null) ? value : "";
    }
    
    /**
     * Set field value.
     * 
     * @param field
     * @param value 
     */
    public void setFieldValue(String field, String value) {
        fieldValue.put(field, value);
    }
    
    /**
     * Get element.
     * 
     * @param doc
     * @return 
     */
    public Element getElement(Document doc) {
        Element childElem;
        Element elem = doc.createElement(getType());
        elem.setAttribute("id", getId());
        childElem = doc.createElement("name");
        childElem.appendChild(doc.createTextNode(getName()));
        elem.appendChild(childElem);
        for (String key: fieldForm.keySet()) {
            childElem = getFormElement(doc, key);
            elem.appendChild(childElem);
        }
        return elem;
    }
    
    /**
     * Get form element.
     * 
     * @param doc
     * @param field
     * @return 
     */
    protected Element getFormElement(Document doc, String field) {
        Element elem = doc.createElement(field);
        elem.appendChild(doc.createTextNode(getFieldValue(field)));
        return elem;
    }

    @Override
    public String toString() {
        return name;
    }

    /**
     * Reset unique identifier of item.
     */
    public void resetId() {
        id = UUID.randomUUID().toString();
    }

}
