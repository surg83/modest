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

import java.io.File;

/**
 * ProjectIO class.
 * 
 * Project input/output base.
 * 
 * @author Michał Czudowski <michal.czudowski@gmail.com>
 */
abstract public class ProjectIO {
    
    /**
     * Get project item type path.
     * 
     * @param projectPath
     * @param item
     * @return 
     */
    public String getItemTypePath(String projectPath, ProjectItem item) {
        String path = new File(projectPath).getParent()
                + File.separator + item.getType();
        return path;
    }
    
    /**
     * Get project item path.
     * 
     * @param projectPath
     * @param item
     * @return 
     */
    public String getItemPath(String projectPath, ProjectItem item) {
        String path = getItemTypePath(projectPath, item)
                + File.separator + item.getId() + ".xml";
        return path;
    }
    
}
