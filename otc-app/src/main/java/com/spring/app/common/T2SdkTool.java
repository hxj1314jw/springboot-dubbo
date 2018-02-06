package com.spring.app.common;

import com.hundsun.t2sdk.impl.util.AbstractLogAdapter;
import com.hundsun.t2sdk.impl.util.CommonLogAdapter;
import com.hundsun.t2sdk.interfaces.IClient;
import com.hundsun.t2sdk.interfaces.share.dataset.IDataset;
import com.hundsun.t2sdk.interfaces.share.event.EventTagdef;
import com.hundsun.t2sdk.interfaces.share.event.IEvent;
import org.springframework.stereotype.Component;
import com.spring.dto.T2SdkSet;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: JGN
 * Date: 16-8-2
 * Time: 上午10:17
 * To change this template use File | Settings | File Templates.
 */
@Component
public class T2SdkTool {
    private IClient client = null;
    private Long WAIT_TIME = 15000l;
    private static AbstractLogAdapter logAdapter = new CommonLogAdapter();

    /**
     * @return 结果
     */
    public T2SdkSet getT2SdkInfo(String functionId,
                                 Map<Object, Object> inMap) {
        // 功能号
        // 类路径
        // 返回结果中间量
        try {
            // 转换入参格式
            // 获取功能号
            // 获取类路径
            // 调接口返回结果
            IEvent envet = sendMessage(functionId, inMap, WAIT_TIME);
            long date = System.currentTimeMillis();
            logAdapter.log("功能号(" + functionId + ")的入参:" + inMap.toString());
            if (envet == null) {
                logAdapter.log("1111");
                return null;
            } else {
                return getSdkSet(envet, date,functionId);
            }
        } catch (Exception e) {
            e.getStackTrace();
            logAdapter.log(e.getMessage());
            logAdapter.log("3333");
            return  null;
        }
    }

    /**
     * @param entity 实体对象
     * @param field  字段
     * @param value  值
     * @return 结果
     */
    public void setField(Object entity, String field, Object value) throws Exception {
        // 使用符合JavaBean规范的属性访问器
        PropertyDescriptor pd = new PropertyDescriptor(field, entity.getClass());
        // 调用setter 设置返回值相关属性
        Method writeMethod = pd.getWriteMethod();
        writeMethod.invoke(entity, value);
    }


    /**
     * @param functionId  功能号
     * @param paramResult 参数
     * @param timeOut     延迟时间
     * @return 结果
     */
    private IEvent sendMessage(String functionId, Map<Object, Object> paramResult, long timeOut) {

        try {
            client = T2ClientService.getInstance();
            Map<String, Object> tagMap = new HashMap<String, Object>();
            tagMap.put(EventTagdef.TAG_FUNCTION_ID, functionId);
            if (paramResult.get("sysNo") != null) {
                String sysNo = paramResult.get("sysNo").toString();
                tagMap.put(EventTagdef.TAG_SYSTEM_NO, sysNo);
            }
            IEvent envet = client.sendReceive(tagMap, paramResult, timeOut);
            return envet;
        } catch (Exception e) {
            T2ClientService.destroyNull();
            logAdapter.log(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    /**
     * @param event 事件
     * @return 结果
     */
    public T2SdkSet getSdkSet(IEvent event, long date,String functionId) {
        T2SdkSet t2SdkSet = new T2SdkSet();
        int returnCode = event.getReturnCode();
        if ("122".equals(returnCode)) {
            t2SdkSet.setSuccess(false);
        }
        t2SdkSet.setSuccess(returnCode == 0 ? true : false);

        String errorNo = null;
        String errorInfo = null;
        String errorPathInfo = null;

        if (!t2SdkSet.isSuccess()) {
            if (event.getEventDatas().getDatasetCount() == 1) {
                IDataset iDataset = event.getEventDatas().getDataset(0);
                t2SdkSet.setOrigDataSet(event.getEventDatas().getDataset(0));
                iDataset.beforeFirst();
                while (iDataset.hasNext()) {
                    iDataset.next();
                    errorNo = iDataset.getString("error_no");
                    errorInfo = iDataset.getString("error_info");
                    errorPathInfo = iDataset.getString("error_pathinfo");
                }
                iDataset.beforeFirst();
            } else {
                errorNo = event.getErrorNo();
                errorInfo = event.getErrorInfo();
                errorPathInfo = "";
            }
            t2SdkSet.setErrorNo(errorNo);
            t2SdkSet.setErrorInfo(errorInfo);
            t2SdkSet.setErrorPathInfo(errorPathInfo);
            long date1 = System.currentTimeMillis();
            long date3 = date1 - date;
            String logInfo =  "总线接口(" + functionId + ")调用不成功：调用接口时长date=" + date3 + "ms,errorNo=" + errorNo + ",errorInfo=" + errorInfo
                    + ",errorPathInfo=" + errorPathInfo;
            logAdapter.log(logInfo);

            if (errorInfo.lastIndexOf("[") > 0 && (errorInfo.lastIndexOf("]") > errorInfo.lastIndexOf("["))) {// 去除中间件返回的标准字段与对应的错误数据
                String tempString = errorInfo.substring(errorInfo.lastIndexOf("["), errorInfo.lastIndexOf("]"));
                if (tempString.matches("(.*?)=(.*?)") == true) {// 看对应的错误信息中是否含有xxx=xxx的参数错误提示信息
                    errorInfo = errorInfo.substring(0, errorInfo.lastIndexOf("["));
                }
            }
        }

        if (event.getEventDatas().getDatasetCount() == 1) {
            StringBuilder logInfo = new StringBuilder();
            long date2 = System.currentTimeMillis();
            long date3 = date2 - date;
            if (t2SdkSet.isSuccess() == true) {
                logInfo.append( "总线接口(" + functionId + ")调用成功,调用接口时长date=" + (date3) + "ms,");
            }
            IDataset iDataset = event.getEventDatas().getDataset(0);
            t2SdkSet.setOrigDataSet(event.getEventDatas().getDataset(0));
            for (int j = 1; j <= iDataset.getColumnCount(); j++) {
                t2SdkSet.getDataStruct().add(iDataset.getColumnName(j));
            }
            iDataset.beforeFirst();
            int row = 0;
            while (iDataset.hasNext()) {
                iDataset.next();
                Map<String, String> trMap = new HashMap<String, String>();
                for (int i = 1; i <= iDataset.getColumnCount(); i++) {
                    trMap.put(iDataset.getColumnName(i), iDataset.getString(i));
                }
                t2SdkSet.getDataset().add(trMap);
                row++;
                if (t2SdkSet.isSuccess() == true) {
                    String aLog = "总线接口(" +functionId + ")返回数据第" + String.valueOf(row) + "行:" + trMap.toString();
                    logInfo.append(aLog);
                }
            }
            logAdapter.log(logInfo.toString());
            iDataset.beforeFirst();
        }
        return t2SdkSet;
    }


}
