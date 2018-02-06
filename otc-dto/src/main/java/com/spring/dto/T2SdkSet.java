package com.spring.dto;


import com.hundsun.t2sdk.interfaces.share.dataset.IDataset;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class T2SdkSet implements Serializable {

    private static final long serialVersionUID = -4516514951487996052L;
    private boolean success;
    private String errorNo;
    private String errorInfo;
    private String errorPathInfo;
    private List<Map<String, String>> dataset = new ArrayList<Map<String, String>>();
    private IDataset origDataSet = null;
    private List<String> dataStruct = new ArrayList<String>();
    private String kcbpString;

    public String getKcbpString() {
        return kcbpString;
    }

    public void setKcbpString(String kcbpString) {
        this.kcbpString = kcbpString;
    }

    public <T> List<T> getDataset(Class<T> clazz) {
        if (isSuccess() == true) {
            List<T> resultList = null;
            if (resultList == null && getDataset() != null
                    && getDataset().size() > 0) {
                resultList = new ArrayList<T>();
                for (Map<String, String> map : getDataset()) {
                    try {
                        T ob = clazz.newInstance();
                        resultList.add(ob);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }
            return resultList;
        } else {
            return new ArrayList<T>();
        }
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getErrorNo() {
        return errorNo;
    }

    public void setErrorNo(String errorNo) {
        this.errorNo = errorNo;
    }

    public String getErrorInfo() {
        return errorInfo;
    }

    public void setErrorInfo(String errorInfo) {
        this.errorInfo = errorInfo;
    }

    public String getErrorPathInfo() {
        return errorPathInfo;
    }

    public void setErrorPathInfo(String errorPathInfo) {
        this.errorPathInfo = errorPathInfo;
    }

    public List<Map<String, String>> getDataset() {
        if (isSuccess() == true) {
            return dataset;
        } else {
            return dataset;
        }
    }

    public void setDataset(List<Map<String, String>> dataset) {
        this.dataset = dataset;
    }

    public IDataset getOrigDataSet()  {
        return origDataSet;
    }

    public void setOrigDataSet(IDataset origDataSet) {
        this.origDataSet = origDataSet;
    }

    public List<String> getDataStruct() {
        return dataStruct;
    }

    public void setDataStruct(List<String> dataStruct) {
        this.dataStruct = dataStruct;
    }
}