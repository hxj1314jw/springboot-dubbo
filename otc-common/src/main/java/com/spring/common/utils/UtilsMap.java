package com.spring.common.utils;

import net.sf.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Created by Administrator on 2018/1/9.
 */
public class UtilsMap {

    public static String MapToSting(Map<String,String> map){
        //遍历出客户的券信息
        Iterator<String> iter = map.keySet().iterator();
        StringBuffer stringBuffer = new StringBuffer();
        while (iter.hasNext()){
            stringBuffer.append(map.get(iter.next()));
        }
        return  stringBuffer.toString();
    }

    public static Map<Object, Object> getParam(String param) throws Exception {
        JSONObject jo = null;
        if (param != null && !"".equals(param)) {
            try {
                jo = JSONObject.fromObject(param);
            } catch (Exception e) {
                e.printStackTrace();
                jo = null;
            }
        }

        Map<Object, Object> map;
        if (jo != null) {
            map = new HashMap<Object, Object>(jo.size());
            String value;
            for (String key : (Set<String>) jo.keySet()) {
                value = jo.getString(key).trim();
                value = "".equals(value) || "null".equals(value) ? null : value;
                map.put(key, value);
            }
        } else {
            throw new Exception("json入参格式错误！");
        }

        return map;
    }

    //Map object转String
    public static Map<String, String> objectToString(Map<Object, Object> mapInto) {
        Map<String, String> map = new HashMap<String, String>();
        for (Map.Entry<Object, Object> entry : mapInto.entrySet()) {
            map.put(entry.getKey().toString(), entry.getValue() == null ? "" : entry.getValue().toString());
        }
        return map;
    }

}
