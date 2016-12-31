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

package com.github.surg83.modest;

import com.github.surg83.modest.project.ProjectItem;
import com.github.surg83.modest.project.ProjectItemFactory;
import com.github.surg83.modest.project.ProjectLoader;
import com.github.surg83.modest.project.ProjectSaver;
import com.github.surg83.modest.project.ProjectTreeModel;
import com.github.surg83.modest.project.ProjectTreeNode;
import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * Project class.
 * 
 * Project handling.
 * 
 * @author Michal Czudowski michal.czudowski@gmail.com
 */
public class Project {
    
    /**
     * Project path.
     */
    private String path;

    /**
     * Get project path.
     * 
     * @return
     */
    public String getPath() {
        return path;
    }
    
    /**
     * Tree model.
     */
    private ProjectTreeModel model;

    /**
     * Get tree model.
     * 
     * @return 
     */
    public ProjectTreeModel getModel() {
        return model;
    }
    
    /**
     * Loader.
     */
    private final ProjectLoader loader;
    
    /**
     * Saver.
     */
    private final ProjectSaver saver;
    
    /**
     * Item factory.
     */
    private final ProjectItemFactory itemFactory;

    /**
     * Project class constructor.
     *
     * @param loader
     * @param saver 
     * @param itemFactory 
     */
    public Project(ProjectLoader loader, ProjectSaver saver,
            ProjectItemFactory itemFactory) {
        this.loader = loader;
        this.saver = saver;
        this.itemFactory = itemFactory;
    }
    
    /**
     * Open project as template.
     * 
     * @param filepath
     * @return
     * @throws ParserConfigurationException
     * @throws SAXException
     * @throws IOException 
     */
    public ProjectTreeModel openTemplate(String filepath)
            throws ParserConfigurationException, SAXException, IOException {
        model = loader.loadTreeModel(filepath, true);
        path = null;
        return model;
    }

    /**
     * Open project template.
     * 
     * @param projectTemplate
     * @return
     * @throws ParserConfigurationException
     * @throws SAXException
     * @throws IOException 
     */
    public ProjectTreeModel openTemplate(InputSource projectTemplate)
            throws ParserConfigurationException, SAXException, IOException {
        model = loader.loadTreeModel(projectTemplate, true);
        path = null;
        return model;
    }

    /**
     * Open project.
     * 
     * @param filepath
     * @return
     * @throws ParserConfigurationException
     * @throws SAXException
     * @throws IOException 
     */
    public ProjectTreeModel open(String filepath)
            throws ParserConfigurationException, SAXException, IOException {
        model = loader.loadTreeModel(filepath);
        path = filepath;
        return model;
    }

    /**
     * Save project.
     *
     * @param filepath
     * @throws ParserConfigurationException
     * @throws TransformerException 
     * @throws IOException
     */
    public void save(String filepath)
            throws ParserConfigurationException, TransformerException,
            IOException {
        saver.saveTreeModel(model, filepath);
        path = filepath;
    }
    
    /**
     * Save project.
     * 
     * @throws ParserConfigurationException
     * @throws TransformerException 
     * @throws java.io.IOException 
     */
    public void save()
            throws ParserConfigurationException, TransformerException,
            IOException {
        save(path);
    }
    
    /**
     * Get project item types.
     * 
     * @return 
     */
    public String[] getItemTypes() {
        return itemFactory.getTypes();
    }
    
    /**
     * Add new item.
     * 
     * @param itemType
     * @param parentNode
     * @return 
     */
    public ProjectTreeNode addItem(String itemType,
            ProjectTreeNode parentNode) {
        if (parentNode == null) {
            parentNode = model.getRoot();
        }
        ProjectItem newItem 
                = itemFactory.create(getNewItemName(itemType), itemType);
        newItem.resetId();
        ProjectTreeNode childNode = new ProjectTreeNode(newItem);
        model.insertNodeInto(childNode, parentNode, parentNode.getChildCount());
        return childNode;
    }
    
    /**
     * Get new item name.
     * TODO replace with translation
     * 
     * @param itemType
     * @return 
     */
    private String getNewItemName(String itemType) {
        String name = itemType;
        switch (itemType) {
            case "goal":
                name = "New goal";
                break;
            case "section":
                name = "New section";
                break;
            case "funcRequirement":
                name = "New functional requirement";
                break;
            case "nonFuncRequirement":
                name= "New non-functional requirement";
                break;
        }
        return name;
    }
    
    /**
     * Remove item.
     * 
     * @param node 
     */
    public void removeItem(ProjectTreeNode node) {
        ProjectTreeNode parentNode = (ProjectTreeNode) node.getParent();
        if (parentNode != null) {
            model.removeNodeFromParent(node);
        }
    }
    
    /**
     * Load item fields.
     * 
     * @param item
     * @return 
     * @throws javax.xml.parsers.ParserConfigurationException 
     * @throws org.xml.sax.SAXException 
     * @throws java.io.IOException 
     */
    public ProjectItem loadItemFields(ProjectItem item)
            throws ParserConfigurationException, SAXException, IOException {
        if (path != null) {
            item = loader.loadItemFields(path, item);
        }
        return item;
    }
}
