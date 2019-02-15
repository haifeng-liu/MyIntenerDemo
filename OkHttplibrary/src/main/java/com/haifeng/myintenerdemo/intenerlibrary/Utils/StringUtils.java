package com.haifeng.myintenerdemo.intenerlibrary.Utils;

import android.app.Activity;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.zip.GZIPOutputStream;

public class StringUtils {

    public static String encodeUTF(String str) {
        if (isNotEmpty(str)) {
            try {
                return URLEncoder.encode(str, "UTF-8");
//                Html.escapeHtml("")
            } catch (UnsupportedEncodingException e) {
                return str;
            }
        } else {
            return "";
        }
    }

    public static String decodeUTF(String str) {
        if (isNotEmpty(str)) {
            try {
                return URLDecoder.decode(str, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                return str;
            }
        } else {
            return "";
        }
    }

    /**
     * URL解码
     *
     * @param str
     * @return
     */
    public static String decodeURL(String str) {
        return URLDecoder.decode(str);
    }

    public static boolean isEmpty(Object str) {
        return null == str || str.toString().length() == 0 || "None".equals(str.toString()) || str.toString().trim().length() == 0;
    }

    public static boolean isNotEmpty(Object str) {
        return !isEmpty(str);
    }
    public static  String  delzero(String num){
        if (isNotEmpty(num)) {
            double N = Double.parseDouble(num);
            if (N*100%100==0){
                return num.split("\\.")[0];
            }else if(N*100%10==0){
                String q=num.substring(0,num.length()-1);
                return q;
            }else{
                return num;
            }
        }
        return "0";
    }




    public static boolean equals(String str1,String str2) {
        return isNotEmpty(str1)&&isNotEmpty(str2)&&str1.equals(str2);
    }

    public static int toInt(String str) {
        int i = -1;
        if (isNotEmpty(str)) {
            try {
                i = Integer.parseInt(str);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return i;
    }

    public static double toDouble(String str) {
        double i = -1;
        if (isNotEmpty(str)) {
            try {
                i = Double.parseDouble(str);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return i;
    }

    public static float toFloat(String str) {
        float i = -1;
        if (isNotEmpty(str)) {
            try {
                i = Float.parseFloat(str);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return i;
    }

    /* 去掉时间为00:00:00 */
    public static String replaceTimeZero(String date) {
        if (date != null) {
            if (date.indexOf("00:00:00") > 0) {
                date = date.replaceAll("00:00:00", "");
            } else if (date.indexOf(":00") == 16) {
                date = date.substring(0, 16);
            }
        }
        return date;
    }

    public static boolean startWithFile(Object str) {
        return isNotEmpty(str) && str.toString().toLowerCase().startsWith("file://");
    }

    public static boolean startWithHttp(Object str) {
        return isNotEmpty(str) && (str.toString().toLowerCase().startsWith("http://") || str.toString().toLowerCase().startsWith("https://"));
    }

    public static boolean startWithTag(Object str, String tag) {
        return isNotEmpty(str) && isNotEmpty(tag) && str.toString().startsWith(tag);
    }

    public static boolean isMobile(Object str) {
        boolean one = isNotEmpty(str) && str.toString().length() == 11;
        if (one) {
//            one=startWithTag(str,"134")||startWithTag(str,"135")||startWithTag(str,"136")||startWithTag(str,"137")
//                    ||startWithTag(str,"138")||startWithTag(str,"139")||startWithTag(str,"150")||startWithTag(str,"151")
//                    ||startWithTag(str,"152")||startWithTag(str,"158")||startWithTag(str,"159")||startWithTag(str,"182")
//                    ||startWithTag(str,"183")||startWithTag(str,"184")||startWithTag(str,"157")||startWithTag(str,"187")
//                    ||startWithTag(str,"188")||startWithTag(str,"147")||startWithTag(str,"178")||startWithTag(str,"170")
//                    ||startWithTag(str,"130")||startWithTag(str,"131")||startWithTag(str,"132")||startWithTag(str,"133")
//                    ||startWithTag(str,"155")||startWithTag(str,"156")||startWithTag(str,"185")||startWithTag(str,"186")
//                    ||startWithTag(str,"145")||startWithTag(str,"176")||startWithTag(str,"153")||startWithTag(str,"180")
//                    ||startWithTag(str,"181")||startWithTag(str,"189")||startWithTag(str,"177");
            one = startWithTag(str, "13") || startWithTag(str, "14") || startWithTag(str, "15") || startWithTag(str, "17")
                    || startWithTag(str, "18");
        }
        return one;
    }

    public static String MD5(String plainText) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(plainText.getBytes());
            byte b[] = md.digest();

            int i;

            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }
            return buf.toString().toUpperCase();
        } catch (NoSuchAlgorithmException e) {
            return plainText;
        }
    }


    public static String buildUrl(String url, Map<String, Object> params) {
        if (null == params || params.size() <= 0) {
            return url;
        }
        StringBuffer sb = new StringBuffer();
        Iterator<String> keys = params.keySet().iterator();
        while (keys.hasNext()) {
            String key = keys.next();
            Object value = params.get(key);
            if (value != null) {
//                sb.append("&" + key + "=" + value);
                sb.append("&" + key + "=" + StringUtils.encodeUTF(value.toString()));
            }
        }
        if (isNotEmpty(url)) {
            return url + sb.replace(0, 1, "?");
        } else {
            return sb.replace(0, 1, "").toString();
        }
    }

    public static String getRealPathFromURI(Uri contentUri, Activity mContext) {

        // can post image  
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = mContext.managedQuery(contentUri,
                proj, // Which columns to return
                null,       // WHERE clause; which rows to return (all rows)
                null,       // WHERE clause selection arguments (none)
                null); // Order-by clause (ascending by name)
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();

        return cursor.getString(column_index);
    }

    /**
     * 切分url中的参数为map
     *
     * @param url
     * @return
     */
    public static Map<String, String> parseParams(String url) {
        Map<String, String> result = new LinkedHashMap();
        String[] params = url.replaceAll("(^[^\\?]*\\?)|(#[^#]*$)", "").split("&");
        for (String keyValue : params) {
            String[] param = keyValue.split("=");
            if (param.length >= 1) {
                result.put(param[0], param.length >= 2 ? StringUtils.decodeUTF(param[1]) : "");
            }
        }
        return result;
    }

    /**
     * 返回url中的参数值
     *
     * @param url
     * @param key
     * @return
     */
    public static String getUrlParamValue(String url, String key) {
        Map<String, String> map = parseParams(url);
        return map.containsKey(key) ? map.get(key) : "";
    }
//    public static String getUrlParamValue(String url,String key){
//        String value = "";
//        if(startWithHttp(url) && url.indexOf("?")>0 && !url.endsWith("?")){
//            value = url.substring(url.indexOf("?")+1);
//            if(value.indexOf("&")>0){
//                String[] str = value.split("&");
//                if(str!=null && str.length >0){
//                    value = "";
//                    for (int i=0;i<str.length;i++){
//                        if(str[i].startsWith(key) && str[i].indexOf("=")>0){
//                            value = str[i].substring(str[i].indexOf("=")+1);
//                            break;
//                        }
//                    }
//                }
//            }else if(value.indexOf("=")>0 && value.startsWith("key")){
//                value = value.substring(value.indexOf("=")+1);
//            }else{
//                value = "";
//            }
//        }
//        return value;
//    }

    public static String gzip(String str) {
        if (isEmpty(str)) {
            return str;
        }
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            GZIPOutputStream gzip = new GZIPOutputStream(out);
            gzip.write(str.getBytes());
            gzip.close();
            return out.toString("ISO-8859-1");
        } catch (Exception e) {
            e.printStackTrace();
            return str;
        }
    }

    private static final String ALGORITHM = "RSA";
    private static final String DEFAULT_CHARSET = "UTF-8";
    private static final String SIGN_ALGORITHMS = "SHA1WithRSA";

    public static String sign(String content, String key) {
        try {
            PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(Base64.decode(key));
            KeyFactory keyf = KeyFactory.getInstance(ALGORITHM);
            PrivateKey priKey = keyf.generatePrivate(priPKCS8);
            java.security.Signature signature = java.security.Signature.getInstance(SIGN_ALGORITHMS);
            signature.initSign(priKey);
            signature.update(content.getBytes(DEFAULT_CHARSET));
            byte[] signed = signature.sign();
            return Base64.encode(signed);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    //yyyy-MM-dd HH:mm:ss
    public static String getTimeStr(long time, String formatterType) {
        SimpleDateFormat formatter = new SimpleDateFormat(formatterType);
        if (time > 0) {
            return formatter.format(new Date(time));
        } else {
            return formatter.format(new Date(System.currentTimeMillis()));
        }
    }

}
