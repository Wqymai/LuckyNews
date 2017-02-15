package com.android.luckynews.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * SharePreference工具类
 */
public class SPUtils {

	/**
	 *
	 * @param context
	 * @param key
	 * @param object
	 */
	public static boolean setParam(String fileName, Context context, String key,
								   Object object) {

		String type = object.getClass().getSimpleName();
		SharedPreferences sp = context.getSharedPreferences(fileName,
				Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sp.edit();

		if ("String".equals(type)) {
			editor.putString(key, (String) object);
		} else if ("Integer".equals(type)) {
			editor.putInt(key, (Integer) object);
		} else if ("Boolean".equals(type)) {
			editor.putBoolean(key, (Boolean) object);
		} else if ("Float".equals(type)) {
			editor.putFloat(key, (Float) object);
		} else if ("Long".equals(type)) {
			editor.putLong(key, (Long) object);
		}

		return editor.commit();
//		editor.apply();
	}

	public static Object getParam(String fileName, Context context, String key,
								  Object defaultObject) {
		String type = defaultObject.getClass().getSimpleName();
		SharedPreferences sp = context.getSharedPreferences(fileName,
				Context.MODE_PRIVATE);

		if ("String".equals(type)) {
			return sp.getString(key, (String) defaultObject);
		} else if ("Integer".equals(type)) {
			return sp.getInt(key, (Integer) defaultObject);
		} else if ("Boolean".equals(type)) {
			return sp.getBoolean(key, (Boolean) defaultObject);
		} else if ("Float".equals(type)) {
			return sp.getFloat(key, (Float) defaultObject);
		} else if ("Long".equals(type)) {
			return sp.getLong(key, (Long) defaultObject);
		}

		return null;
	}

	public static void removeParams(String name, Context context, String key){
		SharedPreferences.Editor data = context.getSharedPreferences(name,
				Context.MODE_PRIVATE).edit();
		data.remove(key);
		data.commit();
	}
}
