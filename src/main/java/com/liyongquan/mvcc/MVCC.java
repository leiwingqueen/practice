package com.liyongquan.mvcc;

/**
 * 最近在看tidb的时候发现一个MVCC的概念，想通过自己练手实践一下
 *
 * https://blog.csdn.net/Waves___/article/details/105295060
 * 这篇文章也刚好讲到MVCC的细节
 */
public class MVCC {
    private RowDataStorage rowDataStorage=new RowDataStorage();
    private UndoLogStorage undoLogStorage=new UndoLogStorage();
    private MvccContext context=new MvccContext();
    private int version=0;
    private int getVersion(){
        return version++;
    }
    public int insert(RowData rowData){
        ReadView readView = context.createReadView();
        ExtendRowData extendData=new ExtendRowData(rowData.getId(),rowData.getName(),
                null,readView.getLowTrixId(),null);
        return rowDataStorage.append(extendData);
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

    public static void main(String[] args) {
        MVCC mvcc=new MVCC();
        mvcc.insert(new Data(1,"li"));
        mvcc.insert(new Data(2,"wang"));
        mvcc.insert(new Data(3,"chen"));
        mvcc.update(new Data(1,"zhang"));
        mvcc.print();
    }
}
