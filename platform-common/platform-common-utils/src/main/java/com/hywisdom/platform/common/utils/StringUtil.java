package com.hywisdom.platform.common.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class StringUtil
{
    /**
     * <根据key从map中取值>
     * 
     * @param pM
     * @param key
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static String getValueFromMapByKey(Map<String, Object> pM, String key)
    {
        try
        {
            return pM.get(key).toString();
        }
        catch (Exception ex)
        {
        
        }
        return "";
    }
    
    /**
     * <根据key从map中取值>
     * 
     * @param pM
     * @param key
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static Double getDoubleValueFromMapByKey(Map<String, Object> pM, String key)
    {
        try
        {
            return Double.parseDouble(pM.get(key).toString());
        }
        catch (Exception ex)
        {
        
        }
        return 0.0;
    }
    /**
     * <判断string是否为null或者空>
     * 
     * @param pS
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static boolean isStringNullorEmpty(String pS)
    {
        if (pS == null || pS.equals("") || pS.isEmpty())
        {
            return true;
        }
        return false;
    }

    
    public static double[] getDoubleFromString(String s)
    {
        double[] pRes = new double[3];
        if(!isStringNullorEmpty(s))
        {
            String[]  pS=s.split(",");
            pRes[0]=Double.parseDouble(pS[0]);
            pRes[1]=Double.parseDouble(pS[1]);
        }
        return pRes;
    }

    /**
     * @Method isNotBlank
     * @Description:  判断字符串不为空
     * @param pS
     * @return：boolean
     * @exception
     * @Author: xfl
     * @Date: 2018/7/23
    **/
    public static boolean isNotBlank(String pS) {
        if (pS == null || pS.equals("") || pS.isEmpty())
        {
            return false;
        }
        return true;
    }

    /**
     * @Method isExitInArray
     * @Description:  判断是否存在
     * @param arrayStr
     * @param curS
     * @return：boolean
     * @exception
     * @Author: xfl
     * @Date: 2018/7/24
    **/
    public static boolean isExitInArray(String[] arrayStr,String curS){
        for(int i=0;i<arrayStr.length;i++){
            if(curS.toLowerCase().contains(arrayStr[i])){
                return true;
            }
        }
        return false;
    }

    /**
     * 判断String是否位数字
     *
     * @param str
     * @return
     */
    public static boolean isInteger(String str) {
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
        return pattern.matcher(str).matches();
    }

    /**
     * @Method stringToIntList
     * @Description:  string转换为int list，分隔符默认为','
     * @param pStr
     * @return：java.util.List<java.lang.Integer>
     * @exception
     * @Author: xfl
     * @Date: 2018/8/10
    **/
    public static List<Integer> stringToIntList(String pStr)
    {
        return stringToIntList(pStr,null);
    }

    /**
     * @Method stringToIntList
     * @Description:  string转换为int list，分隔符默认为','
     * @param pStr
     * @param splitChar
     * @return：java.util.List<java.lang.Integer>
     * @exception
     * @Author: xfl
     * @Date: 2018/8/10
     **/
    public static List<Integer> stringToIntList(String pStr, String splitChar)
    {
        if(isStringNullorEmpty(splitChar)){
            splitChar=",";
        }
        String[] ids = pStr.split(splitChar);
        List<Integer> idStr = new ArrayList<>();
        for (int i = 0; i < ids.length; i++) {
            idStr.add(Integer.parseInt(ids[i]));
        }
        return idStr;
    }

    /**
     * @Method stringToLongList
     * @Description:  string转换为long list，分隔符默认为','
     * @param pStr
     * @return：java.util.List<java.lang.Long>
     * @exception
     * @Author: xfl
     * @Date: 2018/8/13
    **/
    public static List<Long> stringToLongList(String pStr)
    {
        return stringToLongList(pStr,null);
    }

    /**
     * @Method stringToLongList
     * @Description:  string转换为long list，分隔符默认为','
     * @param pStr
     * @param splitChar
     * @return：java.util.List<java.lang.Long>
     * @exception
     * @Author: xfl
     * @Date: 2018/8/13
    **/
    public static List<Long> stringToLongList(String pStr, String splitChar)
    {
        if(isStringNullorEmpty(splitChar)){
            splitChar=",";
        }
        String[] ids = pStr.split(splitChar);
        List<Long> idStr = new ArrayList<>();
        for (int i = 0; i < ids.length; i++) {
            idStr.add(Long.parseLong(ids[i]));
        }
        return idStr;
    }

    /**
     * @Method stringToStringList
     * @Description:  string转换为string list，分隔符默认为','
     * @param pStr
     * @return：java.util.List<java.lang.String>
     * @exception
     * @Author: xfl
     * @Date: 2018/8/10
    **/
    public static List<String> stringToStringList(String pStr)
    {
        return stringToStringList(pStr,null);
    }

    /**
     * @Method stringToStringList
     * @Description:  string转换为string list，分隔符默认为','
     * @param pStr
     * @param splitChar
     * @return：java.util.List<java.lang.String>
     * @exception
     * @Author: xfl
     * @Date: 2018/8/10
    **/
    public static List<String> stringToStringList(String pStr, String splitChar)
    {
        if(isStringNullorEmpty(splitChar)){
            splitChar=",";
        }
        if(isStringNullorEmpty(pStr)){
            return null;
        }
        String[] ids = pStr.split(splitChar);
        List<String> idStr = new ArrayList<>();
        for (int i = 0; i < ids.length; i++) {
            idStr.add(ids[i]);
        }
        return idStr;
    }

    /**
     * @Method listToString
     * @Description:  采用Stringbuilder.append()的方式追加
     * @param mList
     * @param split
     * @return：java.lang.String
     * @exception
     * @Author: xfl
     * @Date: 2019/5/22
    **/
    public static String listToString(List<String> mList,String split) {
        String SEPARATOR = ",";
        if(!isStringNullorEmpty(split)){
            SEPARATOR=split;
        }
        StringBuilder sb = new StringBuilder();
        String convertedListStr = "";
        if (null != mList && mList.size() > 0) {
            for (String item : mList) {
                sb.append(item);
                sb.append(SEPARATOR);
            }
            convertedListStr = sb.toString();
            convertedListStr = convertedListStr.substring(0, convertedListStr.length()
                    - SEPARATOR.length());
        }
        return convertedListStr;
    }

    /**
     * @Method listToString
     * @Description:  采用Stringbuilder.append()的方式追加
     * @param mList
     * @return：java.lang.String
     * @exception
     * @Author: xfl
     * @Date: 2019/5/22
    **/
    public static String listToString(List<String> mList) {
        return listToString(mList,null);
    }
}
