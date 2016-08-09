package cn.com.signheart.common.reflation;

import cn.com.signheart.common.util.AssertUtil;
import cn.com.signheart.common.util.StringUtil;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by ao.ouyang on 15-11-3.
 */
public class ConvertUtil {


        static Pattern datePattern = Pattern.compile("\\b(\\d{1,4})[-\\/](\\d{1,4})[-\\/](\\d{1,4})(\\s+(\\d{1,2}):(\\d{1,2}):(\\d{1,2})\\b)*");

        public ConvertUtil() {
        }

        public static BigDecimal cvStBD(String st) {
            BigDecimal rb = null;
            if(st != null) {
                if(st.trim().equals("")) {
                    rb = null;
                } else {
                    rb = new BigDecimal(st);
                }
            }

            return rb;
        }

        public static String cvBDtS(BigDecimal bd) {
            String rs = null;
            if(bd != null) {
                rs = bd.toString();
            }

            return rs;
        }

        public static Date cvStDate(String st) {
            return AssertUtil.isEmpty(st)?null:new Date(cvStUtildate(st).getTime());
        }

        public static void main(String[] args) {
            String s = "";
            System.out.println(cvStUtildate(s));
        }

        public static java.util.Date cvStUtildate(String st) {
            if(AssertUtil.isEmpty(st)) {
                return null;
            } else {
                Matcher matcher = datePattern.matcher(st);
                if(matcher.find()) {
                    int groupSize = matcher.groupCount();
                    StringBuilder sb = new StringBuilder();
                    if(matcher.group(1).length() == 4) {
                        sb.append(matcher.group(1));
                        sb.append("/");
                        sb.append(matcher.group(2));
                        sb.append("/");
                        sb.append(matcher.group(3));
                    } else {
                        sb.append(matcher.group(3));
                        sb.append("/");
                        sb.append(matcher.group(1));
                        sb.append("/");
                        sb.append(matcher.group(2));
                    }

                    sb.append(" ");
                    if(groupSize > 3) {
                        sb.append(matcher.group(5) == null?"0":matcher.group(5));
                        sb.append(":");
                        sb.append(matcher.group(6) == null?"0":matcher.group(6));
                        sb.append(":");
                        sb.append(matcher.group(7) == null?"0":matcher.group(7));
                    }

                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/M/d H:m:s");

                    try {
                        return sdf.parse(sb.toString());
                    } catch (ParseException var6) {
                        var6.printStackTrace();
                        return null;
                    }
                } else {
                    return null;
                }
            }
        }

        public static Timestamp cvStTims(String st) {
            if(AssertUtil.isEmpty(st)) {
                return null;
            } else {
                try {
                    int ex = 0;
                    Object rd = null;
                    if(st != null) {
                        if(!(st = st.trim()).equals("")) {
                            if(st.length() <= 10) {
                                return new Timestamp(cvStDate(st).getTime());
                            }

                            String[] timeSt = StringUtil.splitString(st, " ");
                            String[] d = StringUtil.splitString(timeSt[0], timeSt[0].substring(4, 5));
                            if(timeSt[1].length() > 8) {
                                ex = getInt(timeSt[1].substring(timeSt[1].indexOf(".") + 1, timeSt[1].length()));
                                timeSt[1] = timeSt[1].substring(0, timeSt[1].indexOf("."));
                            }

                            String[] s = StringUtil.splitString(timeSt[1], timeSt[1].substring(2, 3));
                            return new Timestamp(Integer.parseInt(d[0]) - 1900, Integer.parseInt(d[1]) - 1, Integer.parseInt(d[2]), Integer.parseInt(s[0]), Integer.parseInt(s[1]), Integer.parseInt(s[2]), ex);
                        }

                        rd = null;
                    }

                    return (Timestamp)rd;
                } catch (Exception var6) {
                    var6.printStackTrace();
                    return null;
                }
            }
        }

        public static String cvTimstS(Timestamp tims) {
            return tims.toString();
        }

        public static String cvDatetS(Date da) {
            String rs = null;
            if(da != null) {
                rs = da.toString();
            }

            return rs;
        }

        public static String cvUtilDatetStr(java.util.Date da) {
            String rs = null;
            if(da != null) {
                rs = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(da);
            }

            return rs;
        }

        public static Integer cvStIntg(String st) {
            if(AssertUtil.isEmpty(st)) {
                return null;
            } else {
                Integer intg = null;
                if(AssertUtil.isEmpty(st)) {
                    intg = null;
                } else {
                    try {
                        intg = new Integer(st);
                    } catch (Exception var3) {
                        var3.printStackTrace();
                        intg = null;
                    }
                }

                return intg;
            }
        }

        public static String cvItS(Integer intg) {
            String rs = null;
            if(intg != null) {
                rs = intg.toString();
            }

            return rs;
        }

        public static Object Obj2Form(Object Obj, Object Form) throws Exception {
            Field[] objFields = ClassUtil.getFields(Obj.getClass());
            Field[] formFields = ClassUtil.getFields(Form.getClass());

            for(int i = 0; i < formFields.length; ++i) {
                String formFieldName = formFields[i].getName();
                if(!Modifier.isFinal(formFields[i].getModifiers())) {
                    for(int j = 0; j < objFields.length; ++j) {
                        if(objFields[j].getName().equals(formFieldName)) {
                            Object tempObj = objFields[j].get(Obj);
                            if(!AssertUtil.isEmpty(tempObj)) {
                                formFields[i].set(Form, tempObj.toString());
                            }
                        }
                    }
                }
            }

            return Form;
        }

        public static Object Form2Obj(Object Obj, Object Form) throws Exception {
            new Object();
            Field[] objFields = ClassUtil.getFields(Obj.getClass());
            Field[] formFields = ClassUtil.getFields(Form.getClass());

            for(int i = 0; i < objFields.length; ++i) {
                Class objType = objFields[i].getType();
                String objFieldName = objFields[i].getName();
                if(!Modifier.isFinal(objFields[i].getModifiers())) {
                    for(int j = 0; j < formFields.length; ++j) {
                        if(formFields[j].getName().equals(objFieldName)) {
                            Object tempForm = formFields[j].get(Form);
                            if(!AssertUtil.isEmpty(tempForm)) {
                                Class[] paraCls = new Class[]{ClassType.stringType};
                                Object[] paraObj = new Object[]{tempForm.toString()};
                                Object value;
                                if(objType.equals(ClassType.dateType)) {
                                    value = cvStDate(tempForm.toString());
                                } else if(objType.equals(ClassType.sqlTimeType)) {
                                    value = cvStTims(tempForm.toString());
                                } else {
                                    Constructor objConst = objType.getConstructor(paraCls);
                                    value = objConst.newInstance(paraObj);
                                }

                                if(!AssertUtil.isEmpty(value)) {
                                    objFields[i].set(Obj, value);
                                }
                            }
                        }
                    }
                }
            }

            return Obj;
        }

        public static Object copySuperValue(Object _super, Object _obj) throws Exception {
            Field[] superFiled = ClassUtil.getFields(_super.getClass());

            for(int i = 0; i < superFiled.length; ++i) {
                String fieldName = superFiled[i].getName();
                Field tf = null;

                try {
                    tf = ClassUtil.getField(_obj.getClass().getSuperclass(), fieldName);
                } catch (Exception var7) {
                    var7.printStackTrace();
                }

                Object temp = superFiled[i].get(_super);
                if(temp != null && tf != null && !Modifier.isFinal(tf.getModifiers())) {
                    tf.set(_obj, temp);
                }
            }

            return _obj;
        }

        public static Object Obj2Obj(Object Obj, Object Form) throws Exception {
            Field[] objFields = ClassUtil.getFields(Obj.getClass());
            Field[] formFields = ClassUtil.getFields(Form.getClass());

            for(int i = 0; i < objFields.length; ++i) {
                String objFieldName = objFields[i].getName();
                if(!Modifier.isFinal(objFields[i].getModifiers())) {
                    for(int j = 0; j < formFields.length; ++j) {
                        if(formFields[j].isAccessible() && formFields[j].getName().equals(objFieldName)) {
                            Object tempForm = formFields[j].get(Form);
                            if(!AssertUtil.isEmpty(tempForm)) {
                                objFields[i].set(Obj, tempForm);
                            }
                        }
                    }
                }
            }

            return Obj;
        }

        public static boolean objCobj(Object obj_1, Object obj_2) throws Exception {
            int n = 0;
            boolean flag = false;
            if(obj_1 == null) {
                return obj_2 == null;
            } else if(obj_2 == null) {
                return false;
            } else {
                Class obj1 = obj_1.getClass();
                Class obj2 = obj_2.getClass();
                if(obj1.equals(ClassType.stringType) && obj2.equals(ClassType.stringType)) {
                    return obj_1.equals(obj_2);
                } else {
                    Field[] obj1Fields = ClassUtil.getFields(obj1);
                    Field[] obj2Fields = ClassUtil.getFields(obj2);
                    if(obj1Fields.length != obj2Fields.length) {
                        return false;
                    } else if(obj1Fields.length == 0 && obj1.equals(obj2)) {
                        return true;
                    } else {
                        for(int i = 0; i < obj1Fields.length; ++i) {
                            Class obj1Type = obj1Fields[i].getType();
                            String obj1FieldName = obj1Fields[i].getName();

                            for(int j = 0; j < obj2Fields.length; ++j) {
                                Class obj2Type = obj2Fields[j].getType();
                                if(obj2Fields[j].getName().equals(obj1FieldName)) {
                                    if(!obj1Type.equals(obj2Type)) {
                                        return false;
                                    }

                                    ++n;
                                    Object tempobj2 = obj2Fields[j].get(obj_2);
                                    Object tempobj1 = obj1Fields[i].get(obj_1);
                                    if(!AssertUtil.isEmpty(tempobj1)) {
                                        if(AssertUtil.isEmpty(tempobj2)) {
                                            return false;
                                        }

                                        if(obj1Type.equals(ClassType.dateType)) {
                                            flag = tempobj1.toString().substring(0, 10).equals(tempobj2.toString().substring(0, 10));
                                        } else if(obj1Type.equals(ClassType.sqlTimeType)) {
                                            flag = tempobj1.toString().substring(0, 19).equals(tempobj2.toString().substring(0, 19));
                                        } else {
                                            flag = tempobj1.equals(tempobj2);
                                        }

                                        if(!flag) {
                                            return false;
                                        }
                                    } else {
                                        if(!AssertUtil.isEmpty(tempobj2)) {
                                            return false;
                                        }

                                        flag = true;
                                    }
                                }
                            }
                        }

                        if(n != obj1Fields.length) {
                            return false;
                        } else {
                            return flag;
                        }
                    }
                }
            }
        }

        public static String Chr2Str(char chr) {
            char[] chrAry = new char[]{chr};
            return new String(chrAry);
        }

        public static String Bool2Str(Boolean bl) {
            return bl == null?null:bl.toString();
        }

        public static Boolean Str2Bool(String str) {
            return str == null?null:Boolean.valueOf(str);
        }

        public static int getInt(String str) {
            if(AssertUtil.isEmpty(str)) {
                return 0;
            } else {
                String s = "0";
                boolean i = false;
                int i1;
                if((i1 = str.indexOf(".")) > -1) {
                    s = str.substring(i1 + 1, i1 + 2);
                    str = str.substring(0, i1);
                }

                return (new Integer(s)).intValue() >= 5?(new Integer(str)).intValue() + 1:(new Integer(str)).intValue();
            }
        }

        public static long getLong(String str) {
            if(AssertUtil.isEmpty(str)) {
                return 0L;
            } else {
                String s = "0";
                boolean i = false;
                int i1;
                if((i1 = str.indexOf(".")) > -1) {
                    s = str.substring(i1 + 1, i1 + 2);
                    str = str.substring(0, i1);
                }

                return (new Integer(s)).intValue() >= 5?(new Long(str)).longValue() + 1L:(long)(new Long(str)).intValue();
            }
        }

        public static String Int2Str(int i) {
            return Integer.toString(i);
        }

        public static String Long2Str(long l) {
            return Long.toString(l);
        }

        public static String Double2Str(Double d) {
            String str = String.valueOf(d);
            int index = str.indexOf(".");
            return str.substring(0, index);
        }

        public static int long2int(long l) {
            return Integer.parseInt(Long2Str(l));
        }

        public static Float Str2Float(String str) {
            return AssertUtil.isEmpty(str)?null:new Float(str);
        }

        public static Double Str2Double(String str) {
            return AssertUtil.isEmpty(str)?null:new Double(str);
        }

        public static String Float2Str(Float flt) {
            return AssertUtil.isEmpty(flt)?null:flt.toString();
        }

        public static List List2List(List in, List out) throws Exception {
            if(in == null) {
                in = (List)out.getClass().newInstance();
            }

            if(out == null) {
                return in;
            } else {
                in.addAll(out);
                return in;
            }
        }

        public static void swap(Object[] x, int a, int b) {
            Object t = x[a];
            x[a] = x[b];
            x[b] = t;
        }

        public static void swap(List list, int a, int b) {
            Object t = list.get(a);
            list.set(a, list.get(b));
            list.set(b, t);
        }

        public static long getRSSTime(String _time) throws ParseException {
            SimpleDateFormat sdf = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss Z", Locale.US);
            long ts = 0L;

            try {
                ts = sdf.parse(_time).getTime();
            } catch (Exception var4) {
                ts = cvStTims(_time).getTime();
            }

            return ts;
        }

        public static Timestamp getRssTimestamp(String _time) throws ParseException {
            return new Timestamp(getRSSTime(_time));
        }

        public static Date getRssDate(String _time) throws ParseException {
            return new Date(getRSSTime(_time));
        }

        public static Long cvStLong(String s) {
            return AssertUtil.isEmpty(s)?Long.valueOf(0L):new Long(s);
        }

        public static Byte cvStByte(String str) {
            return AssertUtil.isEmpty(str)?null:new Byte(str);
        }

        public static String cvStream2String(InputStream is) throws IOException {
            if(is == null) {
                return null;
            } else {
                BufferedReader reader = null;
                StringBuilder sb = null;

                try {
                    reader = new BufferedReader(new InputStreamReader(is));
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

        public static String cvStream2String(InputStream is, String charSet) throws IOException {
            if(is != null && !StringUtils.isEmpty(charSet)) {
                BufferedReader reader = null;
                StringBuilder sb = null;

                try {
                    reader = new BufferedReader(new InputStreamReader(is, charSet));
                    sb = new StringBuilder();
                    String line = null;

                    while((line = reader.readLine()) != null) {
                        sb.append(line).append("\n");
                    }
                } finally {
                    IOUtils.closeQuietly(reader);
                }

                return sb.length() == 0?null:sb.toString();
            } else {
                return null;
            }
        }

}
