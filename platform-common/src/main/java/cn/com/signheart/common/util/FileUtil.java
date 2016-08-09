
package cn.com.signheart.common.util;

import org.apache.commons.io.IOUtils;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;

public class FileUtil {
    public static String separator = System.getProperty("file.separator");
    public static String[] IMG_FIX = new String[]{"jpg", "bmp", "gif", "jpeg", "png"};
    public static int WRITE_MODE_OVERWRITE = 0;
    public static int WRITE_MODE_APPEND = 1;

    public FileUtil() {
    }

    public static File creatFile(String _file) throws Exception {
        String path = null;
        if(_file.lastIndexOf(separator) >= 0) {
            path = _file.substring(0, _file.lastIndexOf(separator));
        } else {
            path = _file;
        }

        File jarFile;
        if(path.length() > 2 && _file.lastIndexOf(separator) >= 0) {
            jarFile = new File(path);
            if(!jarFile.exists()) {
                jarFile.mkdirs();
            }
        }

        jarFile = new File(_file);
        if(!jarFile.exists()) {
            jarFile.createNewFile();
        }

        return jarFile;
    }

    public static File copy(String _sourceFile, String _newFile, boolean _overWrite) throws Exception {
        File outFile = new File(_newFile);
        File inFile = new File(_sourceFile);
        return copy(inFile, outFile, _overWrite);
    }

    public static File copy(File _sourceFile, String _newFile, boolean _overWrite) throws Exception {
        File outFile = new File(_newFile);
        return copy(_sourceFile, outFile, _overWrite);
    }

    public static File copy(String _sourceFile, File _newFile, boolean _overWrite) throws Exception {
        File inFile = new File(_sourceFile);
        return copy(inFile, _newFile, _overWrite);
    }

    public static File copy(File _sourceFile, File _newFile, boolean _overWrite) throws Exception {
        FileOutputStream is = null;
        FileInputStream rd = null;

        try {
            if(_newFile.isFile() && _overWrite && _newFile.exists()) {
                _newFile.delete();
            }

            if(_sourceFile.isFile() && !_overWrite && _newFile.exists()) {
                File var13 = _newFile;
                return var13;
            }

            is = new FileOutputStream(_newFile);
            rd = new FileInputStream(_sourceFile);
            byte[] fnfe = new byte[16384];

            for(int var14 = rd.read(fnfe); var14 != -1; var14 = rd.read(fnfe)) {
                is.write(fnfe, 0, var14);
            }
        } catch (FileNotFoundException var11) {
            if(!_sourceFile.isDirectory()) {
                throw var11;
            }

            File[] files = _sourceFile.listFiles();

            for(int i = 0; i < files.length; ++i) {
                if((new File(_sourceFile + File.separator + files[i].getName())).isDirectory() && !(new File(_newFile + File.separator + files[i].getName())).exists()) {
                    (new File(_newFile + File.separator + files[i].getName())).mkdirs();
                }

                copy(_sourceFile + File.separator + files[i].getName(), _newFile + File.separator + files[i].getName(), _overWrite);
            }
        } finally {
            IOUtils.closeQuietly(rd);
            IOUtils.closeQuietly(is);
        }

        return _newFile;
    }

    public static File writeFile(String _path, String _content, int _writeMode, String _encode) throws FileNotFoundException, UnsupportedEncodingException, IOException {
        File outFile = new File(_path);
        return writeFile(outFile, _content, _writeMode, _encode);
    }

    public static File writeFile(File _file, String _content, int _writeMode, String _encode) throws FileNotFoundException, UnsupportedEncodingException, IOException {
        return writeFile(_file, _content.getBytes(_encode), _writeMode);
    }

    public static File writeFile(File _file, byte[] _fileByte, int _writeMode) throws FileNotFoundException, UnsupportedEncodingException, IOException {
        if(_writeMode <= WRITE_MODE_APPEND && _writeMode >= WRITE_MODE_OVERWRITE) {
            FileOutputStream is = null;

            try {
                if(!_file.getParentFile().exists()) {
                    _file.getParentFile().mkdirs();
                }

                is = new FileOutputStream(_file.getPath(), WRITE_MODE_APPEND == _writeMode);
                IOUtils.write(_fileByte, is);
            } finally {
                IOUtils.closeQuietly(is);
            }

            return _file;
        } else {
            throw new IOException("错误的文件写入方式：" + _writeMode);
        }
    }

    public static File openFile(String _file) throws Exception {
        return openFile(_file, true);
    }

    public static File openFile(String _file, boolean _create) throws Exception {
        String path = null;
        if(_file.lastIndexOf(separator) >= 0) {
            path = _file.substring(0, _file.lastIndexOf(separator));
        } else {
            path = _file;
        }

        File newFile;
        if(path.length() > 2 && _file.lastIndexOf(separator) >= 0) {
            newFile = new File(path);
            if(!newFile.exists()) {
                newFile.mkdirs();
            }
        }

        newFile = new File(_file);
        if(_create && !newFile.exists()) {
            newFile.createNewFile();
        }

        return newFile;
    }

    public static File ChooseDir(Component _cp, String _title, String _currPath) throws Exception {
        JFileChooser choose = FileChooser(_currPath, _title, 1);
        return getFile(_cp, choose);
    }

    public static File ChooseOpenFile(Component _cp, String _title, String _currPath) throws Exception {
        JFileChooser choose = FileChooser(_currPath, _title, 0);
        return getFile(_cp, choose);
    }

    public static File ChooseSaveFile(Component _cp, String _title, String _currPath) throws Exception {
        JFileChooser choose = FileChooser(_currPath, _title, 1);
        return getFile(_cp, choose);
    }

    public static File[] ChooseDirs(Component _cp, String _title, String _currPath) throws Exception {
        JFileChooser choose = mulitFileChooser(_currPath, _title, 1);
        return getFiles(_cp, choose);
    }

    public static File[] ChooseOpenFiles(Component _cp, String _title, String _currPath) throws Exception {
        JFileChooser choose = mulitFileChooser(_currPath, _title, 0);
        return getFiles(_cp, choose);
    }

    public static File[] ChooseSaveFiles(Component _cp, String _title, String _currPath) throws Exception {
        JFileChooser choose = mulitFileChooser(_currPath, _title, 1);
        return getFiles(_cp, choose);
    }

    public static JFileChooser FileChooser(String _currPath, String _title, int _Mod) {
        JFileChooser choose = null;
        if(AssertUtil.isEmpty(_currPath)) {
            choose = new JFileChooser();
        } else {
            choose = new JFileChooser(_currPath);
        }

        choose.setFileSelectionMode(_Mod);
        choose.setMultiSelectionEnabled(false);
        choose.setDialogTitle(_title);
        return choose;
    }

    public static JFileChooser mulitFileChooser(String _currPath, String _title, int _Mod) {
        JFileChooser choose = null;
        if(AssertUtil.isEmpty(_currPath)) {
            choose = new JFileChooser();
        } else {
            choose = new JFileChooser(_currPath);
        }

        choose.setFileSelectionMode(_Mod);
        choose.setMultiSelectionEnabled(true);
        choose.setDialogTitle(_title);
        return choose;
    }

    public static File getFile(Component _cp, JFileChooser _choose) {
        int n = _choose.showOpenDialog(_cp);
        return n == 0?_choose.getSelectedFile():null;
    }

    public static File[] getFiles(Component _cp, JFileChooser _choose) {
        int n = _choose.showOpenDialog(_cp);
        return n == 0?_choose.getSelectedFiles():null;
    }

    public static boolean isImage(String _fileName) throws Exception {
        if(AssertUtil.isEmpty(_fileName)) {
            throw new Exception("传入的参数错误：为空");
        } else {
            int startPoint = _fileName.lastIndexOf(".");
            if(startPoint >= 0) {
                String fix = _fileName.substring(startPoint + 1, _fileName.length());
                if(StringUtil.isInContainer(IMG_FIX, fix)) {
                    return true;
                }
            }

            return false;
        }
    }

    public static boolean isImage(File _file) throws Exception {
        return isImage(_file.getPath());
    }

    public static String[] getContentListFromTxt(String _fileName) {
        return getContentListFromTxt(new File(_fileName));
    }

    public static String[] getContentListFromTxt(File _file) {
        BufferedReader fr = null;
        ArrayList strList = new ArrayList();

        try {
            fr = new BufferedReader(new FileReader(_file));
            String e = "";

            while((e = fr.readLine()) != null) {
                strList.add(e);
            }
        } catch (IOException var7) {
            var7.printStackTrace();
        } finally {
            IOUtils.closeQuietly(fr);
        }

        return strList.isEmpty()?new String[0]:(String[])strList.toArray(new String[strList.size()]);
    }

    public static void main(String[] args) {
        try {
            copy("d:\\test.txt", "d:\\test_test\\test.txt", true);
        } catch (Exception var2) {
            var2.printStackTrace();
        }

    }

    public static String readFileContext(String file) throws IOException {
        return readFileContext(file, "utf-8");
    }

    public static String readFileContext(String file, String encode) throws IOException {
        File f = new File(file);
        if(f.exists() && f.isFile()) {
            FileInputStream reader = new FileInputStream(file);

            String var4;
            try {
                var4 = IOUtils.toString(reader, encode);
            } finally {
                IOUtils.closeQuietly(reader);
            }

            return var4;
        } else {
            return "读取文件异常";
        }
    }

    public static String buildFilePath(String... path) {
        StringBuilder rsb = new StringBuilder();
        String[] arr$ = path;
        int len$ = path.length;

        for(int i$ = 0; i$ < len$; ++i$) {
            String midlePath = arr$[i$];
            if(rsb.length() > 0) {
                rsb.append(separator);
            }

            rsb.append(midlePath);
        }

        return rsb.toString();
    }
}
