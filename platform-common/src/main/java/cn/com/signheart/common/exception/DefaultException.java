//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package cn.com.signheart.common.exception;


import cn.com.signheart.common.util.AssertUtil;

public class DefaultException extends Throwable {
    private static final long serialVersionUID = 6937304029901546157L;
    private String errMsg = "";
    private String errCode = "";

    public DefaultException() {
    }

    public DefaultException(Throwable exception) {
        super(exception);
    }

    public DefaultException(String msg, Throwable exception) {
        super(msg, exception);
        this.errMsg = msg;
    }

    public DefaultException(String code, String msg, Throwable exception) {
        super(msg, exception);
        this.errMsg = msg;
        this.errCode = code;
    }

    public DefaultException(String code, String msg) {
        super(msg);
        this.errMsg = msg;
        this.errCode = code;
    }

    public DefaultException(String msg) {
        super(msg);
        this.errMsg = msg;
    }

    public String getMessage() {
        return AssertUtil.isEmpty(this.errMsg)?super.getMessage():this.errMsg;
    }

    public String getErrMsg() {
        return this.errMsg;
    }

    public String getErrCode() {
        return this.errCode;
    }
}
