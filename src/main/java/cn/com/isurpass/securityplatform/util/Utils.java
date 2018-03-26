package cn.com.isurpass.securityplatform.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;

public class Utils 
{
	public static boolean isJsonArrayContaints(String jsonarray , int value)
	{
		if ( jsonarray == null ||  jsonarray.length() == 0 )
			return false ;
		JSONArray ja = JSON.parseArray(jsonarray);
		return ja.contains(value);
	}
	
	public static String jsonArrayRemove(String jsonarray , int value)
	{
		if ( jsonarray == null || jsonarray.length() == 0 )
			jsonarray = "[]";
		JSONArray ja = JSON.parseArray(jsonarray);
		for ( int i = 0 ; i < ja.size() ; i ++ )
			if ( ja.getInteger(i) == value )
			{
				ja.remove(i);
				return ja.toJSONString();
			}
				
		return ja.toJSONString();
	}
	
	public static String jsonArrayAppend(String jsonarray , int value)
	{
		if ( jsonarray == null || jsonarray.length() == 0 )
			jsonarray = "[]";
		JSONArray ja = JSON.parseArray(jsonarray);
		ja.add(value);
		return ja.toJSONString();
	}
}
