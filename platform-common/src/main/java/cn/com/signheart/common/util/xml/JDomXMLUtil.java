
package cn.com.signheart.common.util.xml;

import cn.com.signheart.common.util.AssertUtil;
import cn.com.signheart.common.util.StringUtil;
import org.apache.commons.io.IOUtils;
import org.jdom.*;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
import org.jdom.xpath.XPath;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JDomXMLUtil {
    private SAXBuilder builder = new SAXBuilder(false);
    private Document doc;
    private Element rootElement;
    private String fileName;
    private Map nsMap = new HashMap();
    private XMLFormat outFormatObj = new XMLFormat();

    public JDomXMLUtil() throws Exception {
        this.builder.setEntityResolver(new JDomXMLUtil.XmlEntityResolver());
    }

    public void parseNamespace() {
        List list = this.rootElement.getAdditionalNamespaces();

        for(int i = 0; i < list.size(); ++i) {
            Namespace n = (Namespace)list.get(i);
            this.nsMap.put(n.getPrefix(), n);
        }

    }

    public JDomXMLUtil(URL _url) throws Exception {
        this.builder.setEntityResolver(new JDomXMLUtil.XmlEntityResolver());
        System.out.println("_url=" + _url.getFile());
        System.out.println("_url=" + _url.getPath());
        this.doc = this.builder.build(_url);
        this.rootElement = this.doc.getRootElement();
        this.parseNamespace();
        this.fileName = _url.getFile();
    }

    public JDomXMLUtil(Reader _reader) throws Exception {
        this.builder.setEntityResolver(new JDomXMLUtil.XmlEntityResolver());
        this.doc = this.builder.build(_reader);
        this.rootElement = this.doc.getRootElement();
        this.parseNamespace();
    }

    public JDomXMLUtil(InputStream _reader) throws Exception {
        this.builder.setEntityResolver(new JDomXMLUtil.XmlEntityResolver());
        this.doc = this.builder.build(_reader);
        this.rootElement = this.doc.getRootElement();
        this.parseNamespace();
    }

    public JDomXMLUtil(String _file) throws Exception {
        this.builder.setEntityResolver(new JDomXMLUtil.XmlEntityResolver());
        this.fileName = _file;
        this.doc = this.builder.build(new File(_file));
        this.rootElement = this.doc.getRootElement();
        this.parseNamespace();
    }

    public JDomXMLUtil(File _file) throws Exception {
        this.builder.setEntityResolver(new JDomXMLUtil.XmlEntityResolver());
        this.fileName = _file.getPath();
        this.doc = this.builder.build(_file);
        this.rootElement = this.doc.getRootElement();
        this.parseNamespace();
    }

    public void parseXML(String _xmlString) throws JDOMException, IOException {
        this.doc = this.builder.build(new StringReader(_xmlString));
        this.rootElement = this.doc.getRootElement();
        this.parseNamespace();
    }

    public Element getRootElement() {
        return this.rootElement;
    }

    public String[] getPropertie(String _propertie) throws Exception {
        return this.getPropertieByParent(this.rootElement, _propertie);
    }

    public String getSinglePropertie(String _propertie) throws Exception {
        return this.getSinglePropertieByParent(this.rootElement, _propertie);
    }

    public String[] getPropertieByParent(Element _parent, String _propertie) throws Exception {
        Element[] list = this.getElementsByParent(_parent, _propertie);
        if(AssertUtil.isEmpty(list)) {
            return null;
        } else {
            String[] rs = new String[list.length];

            for(int j = 0; j < list.length; ++j) {
                rs[j] = list[j].getText();
            }

            return rs;
        }
    }

    public String getSinglePropertieByParent(Element _parent, String _propertie) throws Exception {
        Element list = this.getSingleElementsByParent(_parent, _propertie);
        return list != null?list.getText():null;
    }

    public String[] getPropertieByParentValue(String _parent, String _parentValue, String _propertie) throws Exception {
        Element[] element = this.getElementByNameAndValue(_parent, _parentValue);
        ArrayList allList = new ArrayList();
        if(!AssertUtil.isEmpty(element)) {
            for(int rs = 0; rs < element.length; ++rs) {
                Element[] j = this.getElementsByParent(element[rs], _propertie);
                if(!AssertUtil.isEmpty(j)) {
                    allList.add(j);
                }
            }
        }

        if(allList == null) {
            return null;
        } else {
            String[] var8 = new String[allList.size()];

            for(int var9 = 0; var9 < allList.size(); ++var9) {
                var8[var9] = ((Element)allList.get(var9)).getText();
            }

            return var8;
        }
    }

    public Namespace getNamespace(String _perfix) {
        Object obj = this.nsMap.get(_perfix);
        return obj == null?null:(Namespace)obj;
    }

    public Element[] getElements(String _propertie) throws Exception {
        return this.getElementsByParent(this.rootElement, _propertie);
    }

    public Element[] getElements(String _propertie, Namespace _nameSpace) throws Exception {
        return this.getElementsByParent(this.rootElement, _propertie, _nameSpace);
    }

    public Element[] getElementsByParentAndAttrib(Element _parent, String _attribName, String _attribValue, String _propertie) throws Exception {
        Element[] list = this.getElementsByParent(_parent, _propertie);
        if(!AssertUtil.isEmpty(list)) {
            ArrayList tempList = new ArrayList();

            for(int rel = 0; rel < list.length; ++rel) {
                if(_attribValue.equals(list[rel].getAttributeValue(_attribName))) {
                    tempList.add(list[rel]);
                }
            }

            if(tempList != null) {
                Element[] var8 = new Element[tempList.size()];
                tempList.toArray(var8);
                return var8;
            }
        }

        return null;
    }

    public Element[] getElementsByParent(Element _parent, String _propertie) throws Exception {
        if(AssertUtil.isEmpty(_propertie)) {
            throw new Exception("未将属性名传入!");
        } else {
            _propertie = StringUtil.replace(_propertie, "\\.", "{point_tag_yang}");
            String[] path = StringUtil.splitString(_propertie, ".");
            List list = null;

            for(int rel = 0; rel < path.length; ++rel) {
                path[rel] = StringUtil.replace(path[rel], "{point_tag_yang}", ".");
                String[] spath = StringUtil.splitString(path[rel], ":");
                if(spath.length > 1) {
                    if(rel == 0) {
                        list = _parent.getChildren(spath[1], (Namespace)this.nsMap.get(spath[0]));
                    } else {
                        list = this.getChildrens(list, spath[1], (Namespace)this.nsMap.get(spath[0]));
                    }
                } else if(rel == 0) {
                    list = _parent.getChildren(path[rel]);
                } else {
                    list = this.getChildrens(list, path[rel]);
                }
            }

            if(list != null) {
                Element[] var7 = new Element[list.size()];
                list.toArray(var7);
                return var7;
            } else {
                return null;
            }
        }
    }

    public Element getSingleElementsByParent(Element _parent, String _propertie) throws Exception {
        if(AssertUtil.isEmpty(_propertie)) {
            throw new Exception("未将属性名传入!");
        } else {
            _propertie = StringUtil.replace(_propertie, "\\.", "{point_tag_yang}");
            String[] path = StringUtil.splitString(_propertie, ".");
            List list = null;

            for(int i = 0; i < path.length; ++i) {
                path[i] = StringUtil.replace(path[i], "{point_tag_yang}", ".");
                String[] spath = StringUtil.splitString(path[i], ":");
                if(spath.length > 1) {
                    if(i == 0) {
                        list = _parent.getChildren(spath[1], (Namespace)this.nsMap.get(spath[0]));
                    } else {
                        list = this.getChildrens(list, spath[1], (Namespace)this.nsMap.get(spath[0]));
                    }
                } else if(i == 0) {
                    list = _parent.getChildren(path[i]);
                } else {
                    list = this.getChildrens(list, path[i]);
                }
            }

            if(list != null && list.size() > 0) {
                return (Element)list.get(0);
            } else {
                return null;
            }
        }
    }

    public Element[] getElementsByParent(Element _parent, String _propertie, Namespace _nameSpace) throws Exception {
        if(AssertUtil.isEmpty(_propertie)) {
            throw new Exception("未将属性名传入!");
        } else {
            String[] path = StringUtil.splitString(_propertie, ".");
            List list = null;

            for(int rel = 0; rel < path.length; ++rel) {
                if(rel == 0) {
                    list = _parent.getChildren(path[rel], _nameSpace);
                } else {
                    list = this.getChildrens(list, path[rel], _nameSpace);
                }
            }

            if(list != null) {
                Element[] var7 = new Element[list.size()];
                list.toArray(var7);
                return var7;
            } else {
                return null;
            }
        }
    }

    public Element[] getElementsByParentAndSelfValue(Element _parent, String _selfValue, String _propertie) throws Exception {
        Element[] list = this.getElementsByParent(_parent, _propertie);
        ArrayList rs = new ArrayList();
        if(!AssertUtil.isEmpty(list)) {
            for(int temp = 0; temp < list.length; ++temp) {
                if(list[temp].getText().equals(_selfValue)) {
                    rs.add(list[temp]);
                }
            }
        }

        if(rs != null && rs.size() > 0) {
            Element[] var7 = new Element[rs.size()];
            return (Element[])((Element[])rs.toArray(var7));
        } else {
            return null;
        }
    }

    public List getChildrens(List _list, String _child) {
        ArrayList rs = new ArrayList();
        if(_list != null) {
            for(int i = 0; i < _list.size(); ++i) {
                List tempList = ((Element)_list.get(i)).getChildren(_child);
                if(tempList != null) {
                    rs.addAll(tempList);
                }
            }
        }

        return rs;
    }

    public List getChildrens(List _list, String _child, Namespace _nameSpace) {
        ArrayList rs = new ArrayList();
        if(_list != null) {
            for(int i = 0; i < _list.size(); ++i) {
                List tempList = ((Element)_list.get(i)).getChildren(_child, _nameSpace);
                if(tempList != null) {
                    rs.addAll(tempList);
                }
            }
        }

        return rs;
    }

    public Element[] addElement(String _parent, String _elementName) throws Exception {
        Element[] parentEle = this.getElements(_parent);
        Element[] re = null;
        if(!AssertUtil.isEmpty(parentEle)) {
            re = new Element[parentEle.length];

            for(int i = 0; i < parentEle.length; ++i) {
                re[i] = this.addElement(parentEle[i], _elementName);
            }
        }

        return re;
    }

    public Element[] addElement(String _parent, String _elementName, String _value) throws Exception {
        Element[] el = this.addElement(_parent, _elementName);
        if(!AssertUtil.isEmpty(el)) {
            for(int i = 0; i < el.length; ++i) {
                el[i].setText(_value);
            }
        }

        return el;
    }

    public Element addElement(Element _element, String _elementName) throws Exception {
        if(AssertUtil.isEmpty(_elementName)) {
            throw new Exception("错误，新增加的元素没有名称");
        } else {
            String[] path = StringUtil.splitString(_elementName, ".");
            Element ele = _element;

            for(int i = 0; i < path.length; ++i) {
                Element temp = new Element(path[i]);
                ele.addContent(temp);
                ele = temp;
            }

            return ele;
        }
    }

    public Element addElement(Element _element, String _elementName, String _value) throws Exception {
        Element ele = this.addElement(_element, _elementName);
        ele.setText(_value);
        return ele;
    }

    public Element addElement(Element _element, String _elementName, String _attrName, String _attrValue) throws Exception {
        Element ele = this.addElement(_element, _elementName);
        ele.setAttribute(_attrName, _attrValue);
        return ele;
    }

    public void removeElement(Element _element, String _elementName) throws Exception {
        Element[] ele = this.getElementsByParent(_element, _elementName);
        if(!AssertUtil.isEmpty(ele)) {
            for(int i = 0; i < ele.length; ++i) {
                ele[i].getParent().removeContent(ele[i]);
            }
        }

    }

    public Document getDoc() {
        return this.doc;
    }

    public void save(String _path) throws Exception {
        FileOutputStream writer = null;

        try {
            Format formater = Format.getPrettyFormat();
            formater.setIndent("  ");
            formater.setIgnoreTrAXEscapingPIs(true);
            formater.setEncoding(this.outFormatObj.getEncoding());
            writer = new FileOutputStream(_path);
            formater.setExpandEmptyElements(this.outFormatObj.isExpandEmptyElements());
            formater.setOmitDeclaration(this.outFormatObj.isOmitDeclaration());
            formater.setOmitEncoding(this.outFormatObj.isOmitEncoding());
            XMLOutputter outputter = new XMLOutputter(formater);
            outputter.output(this.doc, writer);
        } finally {
            IOUtils.closeQuietly(writer);
        }

    }

    public void save() throws Exception {
        this.save(this.fileName);
    }

    public List getElementsByXPath(String _path) throws JDOMException {
        XPath xPathPaser = XPath.newInstance(_path);
        return xPathPaser.selectNodes(this.rootElement);
    }

    public Element getElementByXPath(String _path) throws JDOMException {
        XPath xPathPaser = XPath.newInstance(_path);
        Object obj = xPathPaser.selectSingleNode(this.rootElement);
        return obj == null?null:(Element)obj;
    }

    public Element[] getElementByNameAndValue(String _path, String _value) throws Exception {
        Element[] list = this.getElements(_path);
        ArrayList rl = new ArrayList();
        if(!AssertUtil.isEmpty(list)) {
            for(int el = 0; el < list.length; ++el) {
                if(list[el].getText().equals(_value)) {
                    rl.add(list[el]);
                }
            }
        }

        Element[] var6 = new Element[rl.size()];
        rl.toArray(var6);
        return var6;
    }

    public Element[] getElementByNameAndAttribValue(String _path, String _attribName, String _attribValue) throws Exception {
        Element[] list = this.getElements(_path);
        ArrayList rl = new ArrayList();
        if(!AssertUtil.isEmpty(list)) {
            for(int el = 0; el < list.length; ++el) {
                if(_attribValue.equals(list[el].getAttributeValue(_attribName))) {
                    rl.add(list[el]);
                }
            }
        }

        Element[] var7 = new Element[rl.size()];
        rl.toArray(var7);
        return var7;
    }

    public String[] getElementAttrib(String _path, String _attribName) throws Exception {
        Element[] el = this.getElements(_path);
        String[] rs = null;
        if(!AssertUtil.isEmpty(el)) {
            rs = new String[el.length];

            for(int i = 0; i < el.length; ++i) {
                rs[i] = el[i].getAttributeValue(_attribName);
            }
        }

        return rs;
    }

    public void addAttribute(Element _element, String _attName, String _attValue) throws Exception {
        _element.setAttribute(_attName, _attValue);
    }

    public void setValue(Element _parentElement, String _elementName, String _value) throws Exception {
        Element[] element = this.getElementsByParent(_parentElement, _elementName);
        if(AssertUtil.isEmpty(element)) {
            this.addElement(_parentElement, _elementName, _value);
        } else {
            for(int i = 0; i < element.length; ++i) {
                element[i].setText(_value);
            }
        }

    }

    public void setAttrib(Element _parentElement, String _elementName, String _atrName, String _atrValue) throws Exception {
        Element[] element = this.getElementsByParent(_parentElement, _elementName);
        if(AssertUtil.isEmpty(element)) {
            this.addElement(_parentElement, _elementName).setAttribute(_atrName, _atrValue);
        } else {
            for(int i = 0; i < element.length; ++i) {
                element[i].setAttribute(_atrName, _atrValue);
            }
        }

    }

    public void setDocType(DocType _type) {
        this.doc.setDocType(_type);
    }

    public DocType getDocType() {
        return this.doc.getDocType();
    }

    public void addContent(ProcessingInstruction _proc) {
        this.doc.addContent(_proc);
    }

    public ProcessingInstruction getProcess() {
        List list = this.doc.getContent();
        if(list != null && list.size() > 0) {
            for(int i = 0; i < list.size(); ++i) {
                if(list.get(i).getClass().isInstance(ProcessingInstruction.class)) {
                    return (ProcessingInstruction)list.get(i);
                }
            }
        }

        return null;
    }

    public void setProcess(ProcessingInstruction _pro) {
        List list = this.doc.getContent();
        if(list != null && list.size() > 0) {
            for(int i = 0; i < list.size(); ++i) {
                if(list.get(i).getClass().isInstance(ProcessingInstruction.class)) {
                    ProcessingInstruction pro = (ProcessingInstruction)list.get(i);
                    if(pro.getTarget().equals(_pro.getTarget())) {
                        this.doc.removeContent((ProcessingInstruction)list.get(i));
                    }
                }
            }
        }

        this.doc.addContent(_pro);
    }

    public String toString() {
        XMLOutputter outer = new XMLOutputter();
        return outer.outputString(this.getDoc());
    }

    public void setNewlines(boolean newlines) {
        this.outFormatObj.setNewlines(newlines);
    }

    public void setEncoding(String encoding) {
        this.outFormatObj.setEncoding(encoding);
    }

    public void setTextNormalize(boolean textNormalize) {
        this.outFormatObj.setTextNormalize(textNormalize);
    }

    public void setExpandEmptyElements(boolean expandEmptyElements) {
        this.outFormatObj.setExpandEmptyElements(expandEmptyElements);
    }

    public void setOmitDeclaration(boolean omitDeclaration) {
        this.outFormatObj.setOmitDeclaration(omitDeclaration);
    }

    public void setOmitEncoding(boolean omitEncoding) {
        this.outFormatObj.setOmitEncoding(omitEncoding);
    }

    static class XmlEntityResolver implements EntityResolver {
        XmlEntityResolver() {
        }

        public InputSource resolveEntity(String publicId, String systemId) {
            return new InputSource(new StringReader(""));
        }
    }
}
