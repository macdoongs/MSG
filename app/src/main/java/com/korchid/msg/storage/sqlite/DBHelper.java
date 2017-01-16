package com.korchid.msg.storage.sqlite;

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
    // TODO Modify sql query code - Databases changed


    /*
    * TABLE USER
    */
    private static final String TABLE_USER = "USER"; // Table name

    private static final String KEY_USER_ID = "_userId"; // Column name
    private static final String KEY_PHONE_NUMBER = "PhoneNumber";

    // Query
    private static final String CREATE_TABLE_USER = "CREATE TABLE " + TABLE_USER + "(" +
            KEY_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            KEY_PHONE_NUMBER + " CHAR(20)" +
            ");";

    /*
    * TABLE USER_INFO
    */
    private static final String TABLE_USER_INFO = "USER_INFO"; // Table name

    private static final String KEY_USER_INFO_ID = "_infoId"; // Column name
    private static final String KEY_NICKNAME = "Nickname";
    private static final String KEY_SEX = "Sex";
    private static final String KEY_BIRTHDAY = "Birthday";
    private static final String KEY_PROFILE = "Profile";

    // Query
    private static final String CREATE_TABLE_USER_INFO = "CREATE TABLE " + TABLE_USER_INFO + "(" +
            KEY_USER_INFO_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            KEY_NICKNAME + " CHAR(20)," +
            KEY_SEX + " CHAR(10)," +
            KEY_BIRTHDAY + " DATE," +
            KEY_PROFILE + " CHAR(30)" +
            ");";
    /*
    * TABLE USER_SETTING
    */
    private static final String TABLE_USER_SETTING = "USER_SETTING"; // Table name

    private static final String KEY_USER_SETTING_ID = "_settingId"; // Column name
    private static final String KEY_ENABLE = "Enable";
    private static final String KEY_ALERT = "Alert";
    private static final String KEY_WEEK_NUMBER = "WeekNumber";
    private static final String KEY_SEND_TIMES = "SendTimes";

    // Query
    private static final String CREATE_TABLE_USER_SETTING = "CREATE TABLE " + TABLE_USER_SETTING + "(" +
            KEY_USER_SETTING_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            KEY_USER_ID + " INT NOT NULL," +
            KEY_ENABLE + " BOOLEAN NOT NULL DEFAULT FALSE," +
            KEY_ALERT + " BOOLEAN NOT NULL DEFAULT FALSE," +
            KEY_WEEK_NUMBER + " INT," +
            KEY_SEND_TIMES + " INT" +
            ");";

    /*
    * TABLE MAP_USER
    */


    /*
    * TABLE INVITE_USER
    */

    /*
    * TABLE USER_ROLE
    */

    /*
    * TABLE CHOOSE_ROLE
    */


    /*
    * TABLE MESSAGE
    */
    private static final String TABLE_MESSAGE = "MESSAGE"; // Table name

    private static final String KEY_MESSAGE_ID = "_messageId"; // Column name
    private static final String KEY_CONTENT = "Content";

    // Query
    private static final String CREATE_TABLE_MESSAGE = "CREATE TABLE " + TABLE_MESSAGE + "(" +
            KEY_MESSAGE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            KEY_CONTENT + " LONGTEXT" +
            ");";

    /*
    * TABLE SEND_MESSAGE
    */
    private static final String TABLE_SEND_MESSAGE = "SEND_MESSAGE"; // Table name

    private static final String KEY_SEND_MESSAGE_ID = "_sendingId"; // Column name
    private static final String KEY_SENDER_ID = "_senderId";
    private static final String KEY_RECEIVER_ID = "_receiverId";
    private static final String KEY_SEND_TIME = "SendTime";

    // Query
    private static final String CREATE_TABLE_SEND_MESSAGE = "CREATE TABLE " + TABLE_SEND_MESSAGE + "(" +
            KEY_SEND_MESSAGE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            KEY_MESSAGE_ID + " INT NOT NULL," +
            KEY_SENDER_ID + " INT NOT NULL," +
            KEY_RECEIVER_ID + " INT NOT NULL," +
            KEY_SEND_TIME + " TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP" +
            ");";

    /*
    * TABLE RESERVATION_MESSAGE_TYPE
    */
    private static final String TABLE_RESERVATION_MESSAGE_TYPE = "RESERVATION_MESSAGE_TYPE"; // Table name

    private static final String KEY_TYPE_ID = "_typeId"; // Column name
    private static final String KEY_TYPE_NAME = "TypeName";

    // Query
    private static final String CREATE_TABLE_RESERVATION_MESSAGE_TYPE = "CREATE TABLE " + TABLE_RESERVATION_MESSAGE_TYPE + "(" +
            KEY_TYPE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            KEY_TYPE_NAME + " VARCHAR(20) NOT NULL" +
            ");";

    /*
    * TABLE RESERVATION_MESSAGE
    */
    private static final String TABLE_RESERVATION_MESSAGE = "RESERVATION_MESSAGE"; // Table name

    private static final String KEY_RESERVATION_MESSAGE_ID = "_reservationMessageId"; // Column name

    // Query
    private static final String CREATE_TABLE_RESERVATION_MESSAGE = "CREATE TABLE " + TABLE_RESERVATION_MESSAGE + "(" +
            KEY_RESERVATION_MESSAGE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            KEY_TYPE_ID + " INT NOT NULL," +
            KEY_CONTENT + " LONGTEXT" +
            ")";


    /*
    * TABLE RESERVE_MESSAGE
    */
    private static final String TABLE_RESERVE_MESSAGE = "RESERVE_MESSAGE"; // Table name

    private static final String KEY_RESERVE_MESSAGE_ID = "_reservingId"; // Column name
    private static final String KEY_RESERVE_TIME = "ReserveTime";

    private static final String CREATE_TABLE_RESERVE_MESSAGE = "CREATE TABLE " + TABLE_RESERVE_MESSAGE + "(" +
            KEY_RESERVE_MESSAGE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            KEY_SENDER_ID + " INT NOT NULL," +
            KEY_RECEIVER_ID + " INT NOT NULL," +
            KEY_RESERVATION_MESSAGE_ID + " INT NOT NULL," +
            KEY_RESERVE_TIME + " TIMESTAMP" +
            ");";

    /*
    * TABLE chatting
    */
    private static final String TABLE_CHATTING = "chatting";
    private static final String KEY_CHATTING_ID = "id";
    private static final String KEY_CHATTING_SENDER_ID = "sender_id";
    private static final String KEY_CHATTING_SENDER_NICKNAME = "sender_nickname";
    private static final String KEY_CHATTING_RECEIVER_ID = "receiver_id";
    private static final String KEY_CHATTING_TOPIC = "topic";
    private static final String KEY_CHATTING_TYPE = "type";
    private static final String KEY_CHATTING_CONTENT = "content";

    private static final String CREATE_TABLE_CHATTING = "CREATE TABLE " + TABLE_CHATTING + "(" +
            KEY_CHATTING_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            KEY_CHATTING_SENDER_ID + " INT NOT NULL," +
            KEY_CHATTING_SENDER_NICKNAME + " CHAR(30) NOT NULL," +
            KEY_CHATTING_RECEIVER_ID + " INT NOT NULL," +
            KEY_CHATTING_TOPIC + " CHAR(30) NOT NULL," +
            KEY_CHATTING_TYPE + " CHAR(30) NOT NULL," +
            KEY_CHATTING_CONTENT + " LONGTEXT NOT NULL" +
            ");";


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
            /*
            db.execSQL(CREATE_TABLE_MESSAGE);
            db.execSQL(CREATE_TABLE_SEND_MESSAGE);
            db.execSQL(CREATE_TABLE_RESERVATION_MESSAGE_TYPE);
            db.execSQL(CREATE_TABLE_RESERVATION_MESSAGE);
            db.execSQL(CREATE_TABLE_RESERVE_MESSAGE);
*/
        }catch (Exception e){
            Log.e(TAG, e.getMessage());
        }
    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d(TAG, "onUpgrade");

        String dropQuery = "DROP TABLE IF EXISTS ";
        String query = dropQuery + TABLE_MESSAGE + ";";
        db.execSQL(query);

        query = dropQuery + TABLE_SEND_MESSAGE + ";";
        db.execSQL(query);

        query = dropQuery + TABLE_RESERVATION_MESSAGE + ";";
        db.execSQL(query);

        query = dropQuery + TABLE_RESERVATION_MESSAGE_TYPE + ";";
        db.execSQL(query);

        query = dropQuery + TABLE_RESERVE_MESSAGE + ";";
        db.execSQL(query);

        query = dropQuery + TABLE_CHATTING + ";";
        db.execSQL(query);
        /*
        db.execSQL(CREATE_TABLE_MESSAGE);
        db.execSQL(CREATE_TABLE_SEND_MESSAGE);
        db.execSQL(CREATE_TABLE_RESERVATION_MESSAGE_TYPE);
        db.execSQL(CREATE_TABLE_RESERVATION_MESSAGE);
        db.execSQL(CREATE_TABLE_RESERVE_MESSAGE);
        */
        db.execSQL(CREATE_TABLE_CHATTING);
    }


    public void insertChatting(int senderId, String senderNickname, int receiverId, String topic, String type, String content){
        // 읽고 쓰기가 가능하게 DB 열기
        SQLiteDatabase db = getWritableDatabase();
        // DB에 입력한 값으로 행 추가
        try {
            db.execSQL("INSERT INTO " + TABLE_CHATTING + " VALUES (null,"
                    + senderId + ", '" + senderNickname + "', " + receiverId + ", '" + topic + "', '" + type + "', '" + content + "');");
        }catch (Exception e){
            Log.e(TAG, e.getMessage());
        }

        Log.d(TAG, "insertChatting");

        db.close();

    }

    public String explainTableChatting(){
        // 읽기가 가능하게 DB 열기
        SQLiteDatabase db = getReadableDatabase();
        String result = "";

        try {
            // DB에 있는 데이터를 쉽게 처리하기 위해 Cursor를 사용하여 테이블에 있는 모든 데이터 출력
            Cursor cursor = db.rawQuery("EXPLAIN " + TABLE_CHATTING, null);

            while (cursor.moveToNext()) {
                result += "" + cursor.getInt(0)
                        + " / "
                        + cursor.getInt(1)
                        + " / "
                        + cursor.getString(2)
                        + " / "
                        + cursor.getInt(3)
                        + " / "
                        + cursor.getString(4)
                        + " / "
                        + cursor.getString(5)
                        + " / "
                        + cursor.getString(6)
                        + "\n";
            }

            cursor.close();
        }catch (Exception e){
            Log.e(TAG, e.getMessage());
        }
        //Log.d(TAG, result);

        return result;

    }

    public String getTableChatting(String topic) {
        // 읽기가 가능하게 DB 열기
        SQLiteDatabase db = getReadableDatabase();
        String result = "";

        try {
            // DB에 있는 데이터를 쉽게 처리하기 위해 Cursor를 사용하여 테이블에 있는 모든 데이터 출력
            Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_CHATTING + " WHERE topic = '" + topic + "'", null);

            while (cursor.moveToNext()) {
                result += "" + cursor.getInt(0)
                        + " / "
                        + cursor.getInt(1)
                        + " / "
                        + cursor.getString(2)
                        + " / "
                        + cursor.getInt(3)
                        + " / "
                        + cursor.getString(4)
                        + " / "
                        + cursor.getString(5)
                        + " / "
                        + cursor.getString(6)
                        + "\n";
            }

            cursor.close();
        }catch (Exception e){
            Log.e(TAG, e.getMessage());
        }
        Log.d(TAG, result);

        return result;
    }



    public void insertMessage(String Content) {
        // 읽고 쓰기가 가능하게 DB 열기
        SQLiteDatabase db = getWritableDatabase();
        // DB에 입력한 값으로 행 추가
        try {
            db.execSQL("INSERT INTO " + TABLE_MESSAGE + " VALUES(null, '" + Content + "');");
        }catch (Exception e){
            Log.e(TAG, e.getMessage());
        }

        db.close();
    }

    public void insertSendMessage(int _sendingId, int _senderId, int _receiverId) {
        // 읽고 쓰기가 가능하게 DB 열기
        SQLiteDatabase db = getWritableDatabase();
        // DB에 입력한 값으로 행 추가
        //db.execSQL("INSERT INTO " + TABLE_SEND_MESSAGE + " VALUES(null, " + _senderId + ", " + _senderId + ", '" + Time + "', '" + Content + "');");

        db.close();
    }

    public void insertReservationMessage(int _mappingId, String Time, String Content) {
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

    public void deleteMessage(String item) {
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

            cursor.close();
        }catch (Exception e){
            Log.e(TAG, e.getMessage());
        }finally {

        }
        Log.d(TAG, result);

        return result;
    }

    public String getMessage() {
        // 읽기가 가능하게 DB 열기
        SQLiteDatabase db = getReadableDatabase();
        String result = "";

        try {
            // DB에 있는 데이터를 쉽게 처리하기 위해 Cursor를 사용하여 테이블에 있는 모든 데이터 출력
            Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_MESSAGE, null);

            while (cursor.moveToNext()) {
                result += "" + cursor.getInt(0)
                        + " / "
                        + cursor.getString(1)
                        + "\n";
            }

            cursor.close();
        }catch (Exception e){
            Log.e(TAG, e.getMessage());
        }
        Log.d(TAG, result);

        return result;
    }


}
