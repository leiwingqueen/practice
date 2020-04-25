package com.liyongquan.mvcc;

import java.util.ArrayList;
import java.util.List;

/**
 * 最近在看tidb的时候发现一个MVCC的概念，想通过自己练手实践一下
 */
public class MVCC {
    private List<ExtendData> list=new ArrayList<ExtendData>();
    private int version=0;
    private int getVersion(){
        return version++;
    }
    public int insert(Data data){
        ExtendData extendData=new ExtendData(data,getVersion());
        list.add(extendData);
        return 1;
    }

    public int update(Data data){
        int version = getVersion();
        ExtendData maxVersion=null;
        for (ExtendData extendData : list) {
            if (extendData.id==data.id&&(maxVersion==null||version>extendData.createVersion)) {
                maxVersion=extendData;
            }
        }
        maxVersion.deleteVersion=version;
        ExtendData extendData=new ExtendData(data,getVersion());
        list.add(extendData);
        return 1;
    }

    public int delete(long id){
        int version = getVersion();
        ExtendData data = (ExtendData) get(id);
        data.deleteVersion=version;
        return 1;
    }

    public Data get(long id){
        ExtendData maxVersion=null;
        for (ExtendData extendData : list) {
            if (extendData.id==id&&(maxVersion==null||version>extendData.createVersion)) {
                maxVersion=extendData;
            }
        }
        return maxVersion;
    }

    public void print(){
        for (ExtendData extendData : list) {
            System.out.println(extendData);
        }
    }

    private static class Data{
        long id;
        String name;
        public Data(){}
        public Data(long id,String name){
            this.id=id;
            this.name=name;
        }
    }

    private static class ExtendData extends Data{
        //mvcc版本信息
        int createVersion;
        int deleteVersion;
        public ExtendData(Data data,int version){
            this.id=data.id;
            this.name=data.name;
            this.createVersion=version;
            this.deleteVersion=0;
        }

        @Override
        public String toString() {
            return "ExtendData{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    ", createVersion=" + createVersion +
                    ", deleteVersion=" + deleteVersion +
                    '}';
        }
    }

    public static void main(String[] args) {
        MVCC mvcc=new MVCC();
        mvcc.insert(new Data(1,"li"));
        mvcc.insert(new Data(2,"wang"));
        mvcc.insert(new Data(3,"chen"));
        mvcc.update(new Data(1,"zhang"));
        mvcc.print();
    }
}
