package com.korchid.msg;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by mac0314 on 2016-12-03.
 */

// SQLite helper
public class DBHelper extends SQLiteOpenHelper {
    private static final String TAG = "DBHelper";

    // DBhelper constructor - DB name, version information
    // http://blog.naver.com/PostView.nhn?blogId=hee072794&logNo=220619425456
    public DBHelper (Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(TAG, "onCreate");
        // 새로운 테이블 생성
        try{
            db.execSQL("CREATE TABLE USER (_userId INT NOT NULL AUTOINCREMENT, PhoneNumber VARCHAR(30), Password VARCHAR(20), PRIMARY KEY (_userId));");
            db.execSQL("CREATE TABLE MAPPING (_mappingId INT NOT NULL AUTOINCREMENT, _parentId INT NOT NULL, _childId INT NOT NULL, Topic VARCHAR(30), PRIMARY KEY (_mappingId));");
            db.execSQL("CREATE TABLE USER_INFO (_infoId INT NOT NULL AUTOINCREMENT, _userId INT NOT NULL, Profile VARCHAR(30), Birthday VARCHAR(20), Sex VARCHAR(10), PRIMARY KEY (_infoId), FOREIGN KEY (_userId) REFERENCES USER(_userId) ON DELETE CASCADE ON UPDATE CASCADE);");
            db.execSQL("CREATE TABLE SEND_MESSAGE (_messageId INT NOT NULL AUTOINCREMENT, _mappingId INT, _senderId INT, Time VARCHAR(30), Content LONGTEXT, PRIMARY KEY (_messageId), FOREIGN KEY (_mappingId) REFERENCES MAPPING(_mappingId) ON DELETE CASCADE ON UPDATE CASCADE);");
            db.execSQL("CREATE TABLE RESERVED_MESSAGE (_reservationId INT NOT NULL AUTOINCREMENT, _mappingId INT, Time VARCHAR(30), Content LONGTEXT, PRIMARY KEY(_reservationId), FOREIGN KEY (_mappingId) REFERENCES MAPPING(_mappingId) ON DELETE CASCADE ON UPDATE CASCADE);");
            db.execSQL("CREATE TABLE ERROR (_errorId INT NOT NULL AUTOINCREMENT, _userId INT, Log LONGTEXT, PRIMARY KEY(_errorId), FOREIGN KEY (_userId) REFERENCES USER(_userId) ON UPDATE CASCADE);");

        }catch (Exception e){
            Log.e(TAG, e.getMessage());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    /*
    public void insert(String create_at, String item, int price) {
        // 읽고 쓰기가 가능하게 DB 열기
        SQLiteDatabase db = getWritableDatabase();
        // DB에 입력한 값으로 행 추가

        db.execSQL("INSERT INTO MONEYBOOK VALUES(null, '" + item + "', " + price + ", '" + create_at + "');");

        db.close();
    }
    */

    public void insertUser(String PhoneNumber, String Password) {
        // 읽고 쓰기가 가능하게 DB 열기
        SQLiteDatabase db = getWritableDatabase();
        // DB에 입력한 값으로 행 추가
        try {
            db.execSQL("INSERT INTO USER VALUES(null, '" + PhoneNumber + "', '" + Password + "');");
        }catch (Exception e){
            Log.e(TAG, e.getMessage());
        }


        db.close();
    }

    public void insertMapping(int _parentId, int _childId, String Topic) {
        // 읽고 쓰기가 가능하게 DB 열기
        SQLiteDatabase db = getWritableDatabase();
        // DB에 입력한 값으로 행 추가

        db.execSQL("INSERT INTO MAPPING VALUES(null, " + _parentId + ", " + _childId + ", '" + Topic + "');");

        db.close();
    }

    public void insertUserInfo(int _userId, String Profile, String Birthday, String Sex) {
        // 읽고 쓰기가 가능하게 DB 열기
        SQLiteDatabase db = getWritableDatabase();
        // DB에 입력한 값으로 행 추가

        db.execSQL("INSERT INTO USER_INFO VALUES(null, " + _userId + ", '" + Profile + "', '" + Birthday + "', '" + Sex + "');");

        db.close();
    }

    public void insertSendMessage(int _mappingId, int _senderId, String Time, String Content) {
        // 읽고 쓰기가 가능하게 DB 열기
        SQLiteDatabase db = getWritableDatabase();
        // DB에 입력한 값으로 행 추가
        db.execSQL("INSERT INTO SEND_MESSAGE VALUES(null, " + _mappingId + ", " + _senderId + ", '" + Time + "', '" + Content + "');");

        db.close();
    }

    public void insertReservedMessage(int _mappingId, String Time, String Content) {
        // 읽고 쓰기가 가능하게 DB 열기
        SQLiteDatabase db = getWritableDatabase();
        // DB에 입력한 값으로 행 추가
        db.execSQL("INSERT INTO SEND_MESSAGE VALUES(null, " + _mappingId + ", '" + Time + "', '" + Content + "');");

        db.close();
    }

    public void update(String item, int price) {
        SQLiteDatabase db = getWritableDatabase();
        // 입력한 항목과 일치하는 행의 가격 정보 수정
        /*
        db.execSQL("UPDATE MONEYBOOK SET price=" + price + " WHERE item='" + item + "';");
        */
        db.close();
    }

    public void delete(String item) {
        SQLiteDatabase db = getWritableDatabase();
        // 입력한 항목과 일치하는 행 삭제
        /*
        db.execSQL("DELETE FROM MONEYBOOK WHERE item='" + item + "';");
        */
        db.close();
    }

    public String getUser() {
        // 읽기가 가능하게 DB 열기
        SQLiteDatabase db = getReadableDatabase();
        String result = "";

        try {
            // DB에 있는 데이터를 쉽게 처리하기 위해 Cursor를 사용하여 테이블에 있는 모든 데이터 출력
            Cursor cursor = db.rawQuery("SELECT * FROM USER", null);

            while (cursor.moveToNext()) {
                result += "" + cursor.getInt(0)
                        + " / "
                        + cursor.getString(1)
                        + " / "
                        + cursor.getString(2)
                        + "\n";
            }
        }catch (Exception e){
            Log.e(TAG, e.getMessage());
        }
        Log.d(TAG, result);

        return result;
    }



}
