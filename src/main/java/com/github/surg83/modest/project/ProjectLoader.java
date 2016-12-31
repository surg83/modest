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
import java.io.IOException;;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * ProjectLoader class.
 * 
 * Loading of project file.
 * 
 * @author Michał Czudowski <michal.czudowski@gmail.com>
 */
public class ProjectLoader extends ProjectIO {
    
    /**
     * Project item factory.
     */
    private final ProjectItemFactory itemFactory;
    
    /**
     * ProjectLoader class constructor.
     * 
     * @param itemFactory 
     */
    public ProjectLoader(ProjectItemFactory itemFactory) {
        this.itemFactory = itemFactory;
    }
    
    /**
     * Load tree model.
     * 
     * @param filepath project file path
     * @return
     * @throws ParserConfigurationException
     * @throws SAXException
     * @throws IOException 
     */
    public ProjectTreeModel loadTreeModel(String filepath)
            throws ParserConfigurationException, SAXException, IOException {
        return loadTreeModel(filepath, false);
    }
    
    /**
     * Load tree model.
     * 
     * @param filepath project file path
     * @param resetIds reset project elements ids
     * @return
     * @throws ParserConfigurationException
     * @throws SAXException
     * @throws IOException 
     */
    public ProjectTreeModel loadTreeModel(String filepath, boolean resetIds)
            throws ParserConfigurationException, SAXException, IOException {
        Document doc = getDocFromFile(filepath);
        return loadTreeModel(doc, resetIds);
    }

    /**
     * Load tree model.
     * 
     * @param source
     * @param resetIds
     * @return
     * @throws ParserConfigurationException
     * @throws SAXException
     * @throws IOException 
     */
    public ProjectTreeModel loadTreeModel(InputSource source, boolean resetIds)
            throws ParserConfigurationException, SAXException, IOException {
        Document doc = getDocFromInputSource(source);
        return loadTreeModel(doc, resetIds);
    }
    
    /**
     * Load tree model.
     * 
     * @param doc
     * @param resetIds
     * @return
     */
    public ProjectTreeModel loadTreeModel(Document doc, boolean resetIds) {
        Element root = doc.getDocumentElement();
        ProjectTreeNode rootTreeNode = getTreeNodeWithChildren(root, resetIds);
        return new ProjectTreeModel(rootTreeNode);
    }

    /**
     * Get document from file.
     * 
     * @param projectFile
     * @return
     * @throws ParserConfigurationException
     * @throws SAXException
     * @throws IOException 
     */
    private Document getDocFromFile(String filepath)
            throws ParserConfigurationException, SAXException, IOException {
        File projectFile = new File(filepath);
        return getDocBuilder().parse(projectFile);
    }

    /**
     * Get document from input source.
     * 
     * @param source
     * @return
     * @throws ParserConfigurationException
     * @throws SAXException
     * @throws IOException 
     */
    private Document getDocFromInputSource(InputSource source)
            throws ParserConfigurationException, SAXException, IOException {
        return getDocBuilder().parse(source);
    }
    
    /**
     * Get document builder.
     * 
     * @return
     * @throws ParserConfigurationException 
     */
    private DocumentBuilder getDocBuilder()
            throws ParserConfigurationException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        return factory.newDocumentBuilder();
    }

    /**
     * Get tree node with children.
     * 
     * @param elem
     * @param resetIds
     * @return
     */
    private ProjectTreeNode getTreeNodeWithChildren(Element elem,
            boolean resetIds) {
        ProjectTreeNode treeNode = getTreeNode(elem, resetIds);
        String[] projectItemTypes = itemFactory.getTypes();
        for (String elemType: projectItemTypes) {
            NodeList domNodeList = elem.getElementsByTagName(elemType);
            for (int i=0; i<domNodeList.getLength(); i++) {
                Element childElem = (Element) domNodeList.item(i);
                if (childElem.getParentNode().isSameNode(elem)) {
                    ProjectTreeNode childTreeNode
                            = getTreeNodeWithChildren(childElem, resetIds);
                    treeNode.add(childTreeNode);
                }
            }
        }
        return treeNode;
    }
    
    /**
     * Get tree node.
     * 
     * @param elem
     * @param resetIds
     * @return
     */
    private ProjectTreeNode getTreeNode(Element elem, boolean resetIds) {
        return new ProjectTreeNode(getItem(elem, resetIds));
    }
    
    /**
     * Get project item.
     * 
     * @param elem
     * @param resetIds
     * @return
     */
    private ProjectItem getItem(Element elem, boolean resetIds) {
        String name = elem.getElementsByTagName("name").item(0)
                .getChildNodes().item(0).getNodeValue();
        String type = elem.getTagName();
        String id = elem.getAttributes().getNamedItem("id").getNodeValue();
        ProjectItem item = itemFactory.create(name, type, id);
        if (resetIds) {
            item.resetId();
        }
        return item;
    }
    
    /**
     * Load project item fields.
     * 
     * @param projectPath
     * @param item
     * @return
     * @throws ParserConfigurationException
     * @throws SAXException
     * @throws IOException 
     */
    public ProjectItem loadItemFields(String projectPath, ProjectItem item)
            throws ParserConfigurationException, SAXException, IOException {
        String itemPath = getItemPath(projectPath, item);
        Document doc = getDocFromFile(itemPath);
        Element elem = doc.getDocumentElement();
        item = getItemFields(elem, item);
        return item;
    }
    
    /**
     * Get project item fields.
     * 
     * @param elem
     * @param item
     * @return 
     */
    private ProjectItem getItemFields(Element elem, ProjectItem item) {
        for (String key: item.getFieldForm().keySet()) {
            Node fieldNode = elem.getElementsByTagName(key).item(0)
                    .getChildNodes().item(0);
            if (fieldNode != null) {
                item.setFieldValue(key, fieldNode.getNodeValue());
            }
        }
        return item;
    }
}
