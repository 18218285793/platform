package cn.com.signheart.common.util;

import cn.com.signheart.common.reflation.ClassTypeUtil;
import cn.com.signheart.common.reflation.ConvertUtil;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;

import java.io.*;
import java.lang.Character.UnicodeBlock;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

public class StringUtil {
    private static final char[] small = new char[]{'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
    private static final char[] big = new char[]{'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
    public static String[] hexCode = new String[]{"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F"};

    public StringUtil() {
    }

    public static String[] splitString(String str, String ident) {
        try {
            if(AssertUtil.isEmpty(str)) {
                return null;
            } else {
                String[] ex = new String[countMatches(str, ident) + 1];

                for(int i = 0; i < ex.length; ++i) {
                    int j = str.indexOf(ident);
                    if(j == -1) {
                        ex[i] = str;
                    } else {
                        ex[i] = str.substring(0, j);
                        str = subString(str, j + ident.length(), str.length());
                    }
                }

                return ex;
            }
        } catch (Exception var5) {
            var5.printStackTrace();
            return null;
        }
    }

    public static int countMatches(String str, String sub) {
        if(str == null) {
            return 0;
        } else {
            int count = 0;

            for(int idx = 0; (idx = str.indexOf(sub, idx)) != -1; idx += sub.length()) {
                ++count;
            }

            return count;
        }
    }

    public static String subString(String str, int Start, int End) throws Exception {
        if(Start >= 0 && End <= str.length()) {
            char[] chrArry = str.toCharArray();
            char[] tempArry = new char[End - Start];
            int n = 0;

            for(int i = Start; i < End; ++i) {
                tempArry[n] = chrArry[i];
                ++n;
            }

            return new String(tempArry);
        } else {
            throw new Exception("错误：输入的长度在字符串中不被认可");
        }
    }

    public static String[] splitString(String str, char chr) throws Exception {
        char[] chrAry = new char[]{chr};
        return splitString(str, new String(chrAry));
    }

    public static String uniteArry(String[] ary, char chr) {
        return uniteArry(ary, Chr2Str(chr));
    }

    public static String uniteArry(String[] ary, String str) {
        if(ary != null && ary.length >= 1) {
            StringBuilder sb = new StringBuilder();

            for(int i = 0; i < ary.length; ++i) {
                if(!AssertUtil.isEmpty(ary[i])) {
                    if(i != 0) {
                        sb.append(str);
                    }

                    sb.append(ary[i]);
                }
            }

            return sb.toString();
        } else {
            return null;
        }
    }

    public static String uniteArry(Object[] _obj, String str) {
        if(_obj != null && _obj.length >= 1) {
            StringBuilder sb = new StringBuilder();

            for(int i = 0; i < _obj.length; ++i) {
                if(!AssertUtil.isEmpty(_obj[i])) {
                    if(i != 0) {
                        sb.append(str);
                    }

                    sb.append(_obj[i].toString());
                }
            }

            return sb.toString();
        } else {
            return null;
        }
    }

    public static String[] uniteArry(String[] ary1, String[] ary2) {
        if(ary1 == null) {
            return ary2;
        } else if(ary2 == null) {
            return ary1;
        } else {
            String[] rs = new String[ary1.length + ary2.length];
            int n = 0;

            int i;
            for(i = 0; i < ary1.length; ++i) {
                if(ary1[i] != null) {
                    rs[n] = ary1[i];
                    ++n;
                }
            }

            for(i = 0; i < ary2.length; ++i) {
                if(ary2[i] != null) {
                    rs[n] = ary2[i];
                    ++n;
                }
            }

            return rs;
        }
    }

    public static String Chr2Str(char chr) {
        char[] chrAry = new char[]{chr};
        return new String(chrAry);
    }

    public static boolean strCstr(String str1, String str2) {
        return str1 != null && str2 != null?str1.equals(str2):str1 == null && str2 == null;
    }

    public static String cutSpace(String str) {
        if(str == null) {
            return null;
        } else {
            char[] chr = str.toCharArray();
            StringBuilder sb = new StringBuilder();

            for(int i = 0; i < chr.length; ++i) {
                if(chr[i] != 32 && chr[i] != 12288 && chr[i] != 32) {
                    sb.append(chr[i]);
                }
            }

            return sb.toString();
        }
    }

    public static int findChar(char[] arry, char chr) {
        for(int i = 0; i < arry.length; ++i) {
            if(arry[i] == chr) {
                return i;
            }
        }

        return -1;
    }

    public static char getRefChar(char chr) throws Exception {
        boolean i = false;
        int i1;
        return (i1 = findChar(small, chr)) > -1?big[i1]:((i1 = findChar(big, chr)) > -1?small[i1]:chr);
    }

    public static String CnvBigChr(String str) {
        boolean n = false;
        char[] chrArry = str.toCharArray();

        for(int i = 0; i < chrArry.length; ++i) {
            int var4;
            if((var4 = findChar(small, chrArry[i])) > -1) {
                chrArry[i] = big[var4];
            }
        }

        return new String(chrArry);
    }

    public static String CnvSmallChr(String str) {
        boolean n = false;
        char[] chrArry = str.toCharArray();

        for(int i = 0; i < chrArry.length; ++i) {
            int var4;
            if((var4 = findChar(big, chrArry[i])) > -1) {
                chrArry[i] = small[var4];
            }
        }

        return new String(chrArry);
    }

    public static boolean isBigChr(char chr) {
        return findChar(big, chr) > -1;
    }

    public static boolean isSmallChr(char chr) {
        return findChar(small, chr) > -1;
    }

    public static boolean isInArry(String strArry, String str, char chr) throws Exception {
        return isInContainer(splitString(strArry, chr), str);
    }

    public static boolean isInArry(String strArry, String str, String chr) throws Exception {
        return isInContainer(splitString(strArry, chr), str);
    }

    public static boolean isInContainer(Object[] objArry, Object obj) throws Exception {
        if(objArry != null && objArry.length >= 1) {
            for(int i = 0; i < objArry.length; ++i) {
                if(ConvertUtil.objCobj(objArry[i], obj)) {
                    return true;
                }
            }

            return false;
        } else {
            return obj == null;
        }
    }

    public static boolean isNullEmpty(String text) {
        return text == null?true:text.length() == 0;
    }

    public static boolean hasText(String text) {
        return text != null && text.length() > 0;
    }

    public static int IndexInArry(Object[] objArry, Object obj) throws Exception {
        if(objArry != null && objArry.length >= 1) {
            for(int i = 0; i < objArry.length; ++i) {
                if(ConvertUtil.objCobj(objArry[i], obj)) {
                    return i;
                }
            }

            return -1;
        } else {
            return obj != null?-1:-1;
        }
    }

    public static int LastInArry(Object[] objArry, Object obj) throws Exception {
        if(objArry != null && objArry.length >= 1) {
            for(int i = objArry.length - 1; i >= 0; --i) {
                if(ConvertUtil.objCobj(objArry[i], obj)) {
                    return i;
                }
            }

            return -1;
        } else {
            return obj != null?-1:-1;
        }
    }

    public static Collection Arry2Coll(Object[] obj) {
        Vector rc = new Vector();

        for(int i = 0; i < obj.length; ++i) {
            rc.add(obj[i]);
        }

        return rc;
    }

    public static String char2Str(char chr) {
        char[] chaArr = new char[]{chr};
        return new String(chaArr);
    }

    public static String replace(String line, String oldString, String newString) {
        if(line == null) {
            return null;
        } else {
            byte i = 0;
            int i1;
            if((i1 = line.indexOf(oldString, i)) < 0) {
                return line;
            } else {
                char[] line2 = line.toCharArray();
                char[] newString2 = newString.toCharArray();
                int oLength = oldString.length();
                StringBuilder buf = new StringBuilder(line2.length);
                buf.append(line2, 0, i1).append(newString2);
                i1 += oLength;

                int j;
                for(j = i1; (i1 = line.indexOf(oldString, i1)) > 0; j = i1) {
                    buf.append(line2, j, i1 - j).append(newString2);
                    i1 += oLength;
                }

                buf.append(line2, j, line2.length - j);
                return buf.toString();
            }
        }
    }

    public static String bigFirstChar(String str) throws Exception {
        return AssertUtil.isEmpty(str)?str:CnvBigChr(str.substring(0, 1)) + str.substring(1, str.length());
    }

    public static String smallFirstChar(String str) throws Exception {
        return AssertUtil.isEmpty(str)?str:CnvSmallChr(str.substring(0, 1)) + str.substring(1, str.length());
    }

    public static String replaceAt(String oldString, int index, String newString) throws Exception {
        if(oldString != null && newString != null) {
            char[] oc = oldString.toCharArray();
            char[] nc = newString.toCharArray();
            int j = 0;

            for(int i = 0; i < oc.length; ++i) {
                if(i >= index && j < nc.length) {
                    oc[i] = nc[j];
                    ++j;
                }
            }

            return new String(oc);
        } else {
            throw new Exception("错误，不能将空符串做为此方法参数");
        }
    }

    public static Iterator Coll2Iter(Collection c) {
        return c != null && !c.isEmpty()?c.iterator():null;
    }

    public static String[] getStrings(List _list) {
        String[] rs = new String[_list.size()];

        for(int i = 0; i < _list.size(); ++i) {
            rs[i] = (String)_list.get(i);
        }

        return rs;
    }

    public static String byteArrayToHexString(byte[] b) {
        StringBuilder result = new StringBuilder();

        for(int i = 0; i < b.length; ++i) {
            result.append(byteToHexString(b[i]));
        }

        return result.toString();
    }

    public static String byteToHexString(byte b) {
        int n = b;
        if(b < 0) {
            n = b + 256;
        }

        int d1 = n / 16;
        int d2 = n % 16;
        return hexCode[d1] + hexCode[d2];
    }

    public static String byteArrayToString(byte[] byteArray) {
        if(byteArray == null) {
            return null;
        } else if(byteArray.length == 0) {
            return null;
        } else {
            try {
                return new String(byteArray, "UTF8");
            } catch (UnsupportedEncodingException var1) {
                return new String(byteArray);
            }
        }
    }

    public static String toBase64Encode(String text) {
        try {
            return byteArrayToString(Base64.encodeBase64(text.getBytes("UTF8")));
        } catch (UnsupportedEncodingException var1) {
            return null;
        }
    }

    public static String toBase64Decode(String base64) {
        try {
            return byteArrayToString(Base64.decodeBase64(base64.getBytes("UTF8")));
        } catch (UnsupportedEncodingException var1) {
            return null;
        }
    }

    public static byte uniteBytes(byte src0, byte src1) {
        byte _b0 = Byte.decode("0x" + new String(new byte[]{src0})).byteValue();
        _b0 = (byte)(_b0 << 4);
        byte _b1 = Byte.decode("0x" + new String(new byte[]{src1})).byteValue();
        byte ret = (byte)(_b0 ^ _b1);
        return ret;
    }

    public static byte[] HexString2Bytes(String src) {
        byte[] ret = new byte[src.length() / 2];
        byte[] tmp = src.getBytes();

        for(int i = 0; i < src.length() / 2; ++i) {
            ret[i] = uniteBytes(tmp[i * 2], tmp[i * 2 + 1]);
        }

        return ret;
    }

    public static String splitStrAndReplace(String str, String split, String rep) {
        String re = "";
        String[] strs = str.split(split);

        for(int i = 0; i < strs.length; ++i) {
            if(i == 0) {
                re = rep + strs[i] + rep;
            } else {
                re = re + split + rep + strs[i] + rep;
            }
        }

        return re;
    }

    public static String toBase64EncodeDefault(String text) {
        try {
            return byteArrayToString(Base64.encodeBase64(text.getBytes()));
        } catch (Exception var1) {
            return null;
        }
    }

    public static String byteArrayToStringDefault(byte[] byteArray) {
        if(byteArray == null) {
            return null;
        } else if(byteArray.length == 0) {
            return null;
        } else {
            try {
                return new String(byteArray);
            } catch (Exception var1) {
                return new String(byteArray);
            }
        }
    }

    public static boolean isChinese(char c) {
        UnicodeBlock ub = UnicodeBlock.of(c);
        return ub == UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS || ub == UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS || ub == UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A || ub == UnicodeBlock.GENERAL_PUNCTUATION || ub == UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION || ub == UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS;
    }

    public static boolean isChinese(byte c) {
        return isChinese((char) c);
    }

    public static String abbr(String str, int maxWidth) {
        return abbreviate(str, maxWidth);
    }

    public static String abbreviate(String str, int maxWidth) {
        return abbreviate(str, 0, maxWidth);
    }

    public static String getStringValue(Object obj, String formate, String defaultValue) throws Exception {
        if(obj == null) {
            return defaultValue;
        } else {
            switch(ClassTypeUtil.getTypeByClass(obj.getClass())) {
                case 6:
                    return DateUtil.format((Date)obj, formate);
                case 7:
                    return DateUtil.format((Timestamp)obj, formate);
                case 8:
                case 9:
                default:
                    return obj.toString();
                case 10:
                    return DateUtil.format((java.util.Date)obj, formate);
            }
        }
    }

    private static String abbreviate(String str, int offset, int maxWidth) {
        if(str == null) {
            return null;
        } else if(maxWidth < 4) {
            throw new IllegalArgumentException("maxWidth的值最小必须为4");
        } else if(str.length() <= maxWidth) {
            return str;
        } else {
            if(offset > str.length()) {
                offset = str.length();
            }

            if(str.length() - offset < maxWidth - 3) {
                offset = str.length() - (maxWidth - 3);
            }

            if(offset <= 4) {
                return str.substring(0, maxWidth - 3) + "...";
            } else if(maxWidth < 7) {
                throw new IllegalArgumentException("Minimum abbreviation width with offset is 7");
            } else {
                return offset + (maxWidth - 3) < str.length()?"..." + abbreviate(str.substring(offset), maxWidth - 3):"..." + str.substring(str.length() - (maxWidth - 3));
            }
        }
    }

    public static String getStringFromStream(InputStream is, String charSet) throws IOException {
        if(is == null) {
            return null;
        } else {
            BufferedReader reader = null;
            StringBuilder sb = null;

            try {
                reader = AssertUtil.isEmpty(charSet)?new BufferedReader(new InputStreamReader(is)):new BufferedReader(new InputStreamReader(is, charSet));
                sb = new StringBuilder();

                String line;
                while((line = reader.readLine()) != null) {
                    sb.append(line).append("\n");
                }
            } finally {
                IOUtils.closeQuietly(reader);
            }

            return sb.length() == 0?null:sb.toString();
        }
    }

}
