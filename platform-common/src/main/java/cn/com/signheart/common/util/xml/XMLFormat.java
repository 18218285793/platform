
package cn.com.signheart.common.util.xml;

public class XMLFormat {
    private boolean newlines = false;
    private String encoding = "UTF-8";
    private boolean textNormalize = false;
    private boolean expandEmptyElements = false;
    private boolean omitDeclaration = false;
    private boolean omitEncoding = false;

    public XMLFormat() {
    }

    public boolean isNewlines() {
        return this.newlines;
    }

    public void setNewlines(boolean newlines) {
        this.newlines = newlines;
    }

    public String getEncoding() {
        return this.encoding;
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    public boolean isTextNormalize() {
        return this.textNormalize;
    }

    public void setTextNormalize(boolean textNormalize) {
        this.textNormalize = textNormalize;
    }

    public boolean isExpandEmptyElements() {
        return this.expandEmptyElements;
    }

    public void setExpandEmptyElements(boolean expandEmptyElements) {
        this.expandEmptyElements = expandEmptyElements;
    }

    public boolean isOmitDeclaration() {
        return this.omitDeclaration;
    }

    public void setOmitDeclaration(boolean omitDeclaration) {
        this.omitDeclaration = omitDeclaration;
    }

    public boolean isOmitEncoding() {
        return this.omitEncoding;
    }

    public void setOmitEncoding(boolean omitEncoding) {
        this.omitEncoding = omitEncoding;
    }
}
