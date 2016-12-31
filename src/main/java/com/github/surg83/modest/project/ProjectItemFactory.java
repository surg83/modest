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

import com.github.surg83.modest.project.item.FuncRequirementProjectItem;
import com.github.surg83.modest.project.item.GoalProjectIem;
import com.github.surg83.modest.project.item.NonFuncRequirementProjectItem;
import com.github.surg83.modest.project.item.RootProjectItem;
import com.github.surg83.modest.project.item.SectionProjectItem;

/**
 * ProjectItemFactory class.
 * 
 * Project item factory.
 * 
 * @author Michał Czudowski <michal.czudowski@gmail.com>
 */
public class ProjectItemFactory {

    /**
     * Create new project item.
     *
     * @param name name of item
     * @param type type of item
     * @param id unique identifier of item
     * @return
     */
    public ProjectItem create(String name, String type, String id) {
        ProjectItem item = null;
        switch (type) {
            case RootProjectItem.TYPE:
                item = new RootProjectItem(id, name);
                break;
            case GoalProjectIem.TYPE:
                item = new GoalProjectIem(id, name);
                break;
            case SectionProjectItem.TYPE:
                item = new SectionProjectItem(id, name);
                break;
            case FuncRequirementProjectItem.TYPE:
                item = new FuncRequirementProjectItem(id, name);
                break;
            case NonFuncRequirementProjectItem.TYPE:
                item = new NonFuncRequirementProjectItem(id, name);
                break;
        }
        return item;
    }
    
    /**
     * Create new project item.
     * Item unique identifier is generated automatically.
     * 
     * @param name name of item
     * @param type type of item
     * @return
     */
    public ProjectItem create(String name, String type) {
        return create(name, type, null);
    }

    /**
     * Get item types.
     *
     * @return
     */
    public String[] getTypes() {
        String[] types = {
            // RootProjectItem.TYPE is reserved
            GoalProjectIem.TYPE,
            SectionProjectItem.TYPE,
            FuncRequirementProjectItem.TYPE,
            NonFuncRequirementProjectItem.TYPE
        };
        return types;
    }

}
