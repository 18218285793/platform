package cn.com.signheart.common.exception;

public class ConfigLoadExcetion extends DefaultException {
    public ConfigLoadExcetion(int errCode, String errMsg) {
        super(String.valueOf(errCode), errMsg);
    }
}