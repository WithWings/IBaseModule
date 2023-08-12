package com.withwings.baseutils.base;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.NonNull;


/**
 * SQLite 控制类
 * 创建：WithWings 时间 19/1/8
 * Email:wangtong1175@sina.com
 */
public abstract class BaseSQLiteOpenHelper extends SQLiteOpenHelper {

    /**
     * 构造函数
     * @param context 上下文
     * @param name 数据库文件名 存储路径为 /data/data/包名/${name}
     * @param factory 如果CursorFactory 为 null，会使用一个默认的。
     * @param version 数据库的版本 用于在 onUpgrade 函数中扩展字段等操作
     */
    public BaseSQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    /**
     * 构造函数
     * @param context 上下文
     * @param name 数据库文件名 存储路径为 /data/data/包名/${name}
     * @param factory 如果CursorFactory 为 null，会使用一个默认的。
     * @param version 数据库的版本 用于在 onUpgrade 函数中扩展字段等操作
     * @param errorHandler new DefaultDatabaseErrorHandler()  onCorruption 函数会回调损坏的数据库的对象 SQLiteDatabase
     */
    public BaseSQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    // 1.SQLiteOpenHelper
    // 关闭所有打开的数据库对象
    // close();
    // 创建或打开可以读/写的数据库:通过返回的SQLiteDatabase对象对数据库进行操作
    // getWritableDatabase();
    // 创建或打开可读的数据库:通过返回的SQLiteDatabase对象对数据库进行操作
    // getReadableDatabase();

    // 2.SQLiteDatabase
    // 关闭数据库对象
    // db.close();
    // 新增
    // db.insert();
    // 删除
    // db.delete();
    // 更新
    // db.update();
    // 查询
    // db.query();
    // db.rawQuery();
    // 执行SQL：可进行增删改操作, 不能进行查询操作
    // db.execSQL();

    /**
     * 该方法是当没有数据库存在才会执行 用以创建 数据库:该方法不会在对象创建后自动执行  而是在 getWritableDatabase 或 getReadableDatabase 的时候执行
     * 这里可以创建多个表 当然  在 onUpgrade 里面创建也可以
     * @param db 数据库操作对象 用以执行语句
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        // SQLite数据创建支持的数据类型： 整型数据，字符串类型，日期类型，二进制的数据类型
        // Demo:创建了一个名为person的表
        // String sql = "create table person(id integer primary key autoincrement,name varchar(64),address varchar(64))";
        // execSQL用于执行SQL语句
        // 完成数据库的创建
        db.execSQL(createSQL());
    }

    public abstract  @NonNull String createSQL();

    /**
     * 该方法是数据库存更新才会执行：version 变更，曾经支持负数  现在不支持了
     * 建议内部抽取多层函数对 version 进行循环升级  因为即使用户跨版本安装  这里也只会调用一次
     * @param db 数据库操作对象 用以执行语句
     * @param oldVersion 当前版本
     * @param newVersion 新版本
     * version 准升不准降 否则会抛出异常 SQLiteException: Can't downgrade database from version 2 to 1
     */
    @Override
    public abstract void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion);

    /**
     * 插入数据
     * @param tableName 表名
     * @param nullColumnHack 当 contentValues 参数为空或者里面没有内容的时候，我们insert是会失败的（底层数据库不允许插入一个空行：
     *                       因为底层是在拼接sql字符串）insert into tableName()values(); 与 insert into tableName (nullColumnHack)values(null);
     * @param contentValues 插入的数据 ContentValues Key只能是String类型，Value只能存储基本类型数据，不能存储对象
     *                      注：ContentValues内部实现就是HashMap，但是两者还是有差别的
     * @return 插入数据的行位置 -1 代表插入失败
     */
    public long insert(String tableName, String nullColumnHack, ContentValues contentValues) {//1. 创建并打开数据库
        SQLiteDatabase db = getWritableDatabase();
        // 第一个参数：要操作的表名称
        // 第二个参数：SQl不允许一个空列，如果ContentValues是空的，那么这一列被明确的指明为NULL值
        // 第三个参数：ContentValues对象
        long insert = db.insert(tableName, nullColumnHack, contentValues);
        db.close();
        return insert;
        // db.execSQL("insert into user (id,name) values (1,'张三')")  也可以
    }

    /**
     * 删除数据
     * @param tableName 表名
     * @param whereClause where选择语句, 选择哪些行要被删除, 如果为null, 就删除所有行;
     * @param whereArgs where语句的参数, 逐个替换where语句中的 "?" 占位符;
     * @return 删除的行数
     */
    public int delete(String tableName, String whereClause, String[] whereArgs) {
        SQLiteDatabase db = getWritableDatabase();
        // db.delete("user", "id=?", new String[]{"1"});
        int delete = db.delete(tableName, whereClause, whereArgs);
        db.close();
        return delete;
    }

    /**
     * 更新数据
     * @param tableName 表名
     * @param values ContentValues对象（需要修改的）
     * @param whereClause WHERE表达式，where选择语句, 选择那些行进行数据的更新, 如果该参数为 null, 就会修改所有行;？号是占位符
     * @param whereArgs where选择语句的参数, 逐个替换 whereClause 中的占位符;
     * @return 修改的行数
     */
    public int update(String tableName, ContentValues values, String whereClause, String[] whereArgs) {
        SQLiteDatabase db = getWritableDatabase();
        int update = db.update(tableName, values, whereClause, whereArgs);
        db.close();
        return update;
    }

    /**
     * 查询语句
     * @param distinct 可以指定“true”或“false”表示要不要过滤重复值
     * @param tableName 表名
     * @param columns 查询的列所有名称集
     * @param selection WHERE之后的条件语句，可以使用占位符
     * @param selectionArgs where选择语句的参数, 逐个替换 selection 中的占位符;
     * @param groupBy 指定分组的列名
     * @param having 指定分组条件，配合groupBy使用
     * @param orderBy 指定排序的列名
     * @param limit 指定分页参数
     * @return 查询游标
     * //Cursor对象常用方法如下：
        c.move(int offset); //以当前位置为参考,移动到指定行
        c.moveToFirst();    //移动到第一行
        c.moveToLast();     //移动到最后一行
        c.moveToPosition(int position); //移动到指定行
        c.moveToPrevious(); //移动到前一行
        c.moveToNext();     //移动到下一行
        c.isFirst();        //是否指向第一条
        c.isLast();     //是否指向最后一条
        c.isBeforeFirst();  //是否指向第一条之前
        c.isAfterLast();    //是否指向最后一条之后
        c.isNull(int columnIndex);  //指定列是否为空(列基数为0)
        c.isClosed();       //游标是否已关闭
        c.getCount();       //总数据项数
        c.getPosition();    //返回当前游标所指向的行数
        c.getColumnIndex(String columnName);//返回某列名对应的列索引值
        c.getString(int columnIndex);   //返回当前行指定列的值
     */
    public Cursor query(boolean distinct, String tableName, String[] columns,
                        String selection, String[] selectionArgs, String groupBy,
                        String having, String orderBy, String limit) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor query = db.query(distinct, tableName, columns, selection, selectionArgs, groupBy, having, orderBy, limit);
        db.close();
        return query;

    }
}
