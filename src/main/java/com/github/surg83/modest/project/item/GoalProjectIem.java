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

package com.github.surg83.modest.project.item;

import com.github.surg83.modest.project.ProjectItem;

/**
 * GoalProjectIem class.
 * 
 * Goal project item.
 * 
 * @author Michał Czudowski <michal.czudowski@gmail.com>
 */
public class GoalProjectIem extends ProjectItem {
    
    /**
     * Goal type.
     */
    public static final String TYPE = "goal";

    /**
     * GoalProjectIem class constructor.
     * 
     * @param id
     * @param name 
     */
    public GoalProjectIem(String id, String name) {
        super(id, name);
        this.type = TYPE;
        this.icon = "/com/github/surg83/modest/project/item/"
                + "GoalProjectItemIcon.png";
        this.fieldForm.put("description", ProjectItem.FORM_TEXTAREA);
    }

}
