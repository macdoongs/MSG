package com.korchid.msg.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.korchid.msg.ui.CustomActionbar;
import com.korchid.msg.global.GlobalApplication;
import com.korchid.msg.R;
import com.korchid.msg.ui.StatusBar;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import static android.content.Intent.ACTION_MEDIA_SCANNER_SCAN_FILE;
import static com.korchid.msg.global.QuickstartPreferences.SHARED_PREF_USER_INFO;
import static com.korchid.msg.global.QuickstartPreferences.SHARED_PREF_USER_LOGIN;
import static com.korchid.msg.global.QuickstartPreferences.USER_LOGIN_STATE;
import static com.korchid.msg.global.QuickstartPreferences.USER_PROFILE;

// Modify and register profile image
public class ProfileActivity  extends AppCompatActivity implements View.OnClickListener{
    private static final String TAG = "ProfileActivity";
    private static final int PICK_FROM_CAMERA = 0;
    private static final int PICK_FROM_ALBUM = 1;
    private static final int CROP_IMAGE = 2;

    private Uri mImageCaptureUri;
    private ImageView iv_profile;

    private Button btn_upload;
    private Button btn_register;


    //private ImageReceiver imageReceiver;

    private int viewId;
    private String absolutePath;
    private String profilePath;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        initView();

        //IntentFilter intentFilter = new IntentFilter(ACTION_MEDIA_SCANNER_SCAN_FILE);

        //imageReceiver = new ImageReceiver();
        //registerReceiver(imageReceiver, intentFilter);

    }

    @Override
    protected void onDestroy() {
        //unregisterReceiver(imageReceiver);

        super.onDestroy();
    }

    private void initView(){
        StatusBar statusBar = new StatusBar(this);

        CustomActionbar customActionbar = new CustomActionbar(this, R.layout.actionbar_content, "Profile");

        iv_profile = (ImageView) findViewById(R.id.iv_profile);
        btn_upload = (Button) findViewById(R.id.btn_upload);
        btn_upload.setOnClickListener(this);

        btn_register = (Button) findViewById(R.id.btn_register);
        btn_register.setOnClickListener(this);
        btn_register.setEnabled(false);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != RESULT_OK)
            return;

        switch (requestCode) {
            case PICK_FROM_ALBUM: {
                Log.d(TAG, "PICK_FROM_ALBUM");
                mImageCaptureUri = data.getData();
                Log.i("NR", mImageCaptureUri.getPath().toString());

                // 이후의 처리가 카메라 부분과 같아 break 없이 진행
            }
            case PICK_FROM_CAMERA: {
                Log.d(TAG, "PICK_FROM_CAMERA");
                Intent intent = new Intent("com.android.camera.action.CROP");
                intent.setDataAndType(mImageCaptureUri, "image/*");

                // crop한 이미지를 저장할때 200x200 크기로 저장
                intent.putExtra("outputX", 200); // crop한 이미지의 x축 크기
                intent.putExtra("outputY", 200); // crop한 이미지의 y축 크기
                intent.putExtra("aspectX", 2); // crop 박스의 x축 비율
                intent.putExtra("aspectY", 1); // crop 박스의 y축 비율
                intent.putExtra("scale", true);
                intent.putExtra("return-data", true);
                startActivityForResult(intent, CROP_IMAGE);

                btn_register.setEnabled(true);
                btn_register.setBackgroundResource(R.color.colorPrimary);

                break;
            }
            case CROP_IMAGE: {
                Log.d(TAG, "CROP_IMAGE");
                final Bundle extras = data.getExtras();
                // crop된 이미지를 저장하기 위한 파일 경로
                String filePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/temp/" + System.currentTimeMillis() + ".jpg";


                if (extras != null) {
                    Bitmap photo = extras.getParcelable("data"); // crop된 bitmap
                    storeCropImage(photo, filePath);
                    sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + Environment.getExternalStorageDirectory()))); // 갤러리를 갱신하기 위해..

                }

                profilePath = filePath;
                /*
                File file = new File(mImageCaptureUri.getPath());
                if (file.exists()) {
                    Boolean isDeleted = file.delete();
                    Log.d(TAG, "exist");
                    if(isDeleted){
                        Log.d(TAG, "delete");
                    }
                }
                */

                break;
            }
            default:{
                Log.d(TAG, "default");
                break;
            }
        }
    }

    private void storeCropImage(Bitmap bitmap, String filePath) {
        Log.d(TAG, "storeCropImage");
        File copyFile = new File(filePath);
        BufferedOutputStream out = null;

        try {
            Boolean isCreated = copyFile.createNewFile();
            if(isCreated){
                Log.d(TAG, "create");
            }

            out = new BufferedOutputStream(new FileOutputStream(copyFile));
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);

            iv_profile.setImageBitmap(bitmap);

            out.flush();
            out.close();
        } catch (Exception e) {
            Log.e(TAG, "Error : " +e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v){
        viewId = v.getId();

        switch (viewId){
            case R.id.btn_register:{
                // Use Environmental variable 'SharedPreference'
                SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF_USER_INFO, 0);

                SharedPreferences.Editor editor = sharedPreferences.edit();

                editor.putString(USER_PROFILE, profilePath);

                Toast.makeText(this, "Complete join", Toast.LENGTH_LONG).show();

                iv_profile.buildDrawingCache();
                Bitmap bitmap = iv_profile.getDrawingCache();

                Intent intent = new Intent();
                intent.putExtra("profilePath", profilePath);
                setResult(RESULT_OK, intent);

                this.finish();
                break;
            }
            case R.id.btn_upload:{
                DialogInterface.OnClickListener cameraListener = new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        getPhotoFromCamera();
                    }
                };

                DialogInterface.OnClickListener albumListener = new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        getPhotoFromGallery();
                    }
                };

                DialogInterface.OnClickListener cancelListener = new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                };

                new AlertDialog.Builder(this)
                        .setTitle("Choose Image to upload")
                        .setPositiveButton("Take pictures", cameraListener)
                        .setNeutralButton("Choose album", albumListener)
                        .setNegativeButton("Cancel", cancelListener)
                        .show();
                break;
            }
            default:{
                break;
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //ActionBar 메뉴 클릭에 대한 이벤트 처리

        int id = item.getItemId();
        switch (id){
            case android.R.id.home:
                this.finish();
                break;
            default:
                break;
        }

        return true;
    }

    private void getPhotoFromCamera() { // 카메라 촬영 후 이미지 가져오기
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        // 임시로 사용할 파일의 경로를 생성
        String url = "tmp_" + String.valueOf(System.currentTimeMillis()) + ".jpg";
        mImageCaptureUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(), url));

        intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, mImageCaptureUri);
        startActivityForResult(intent, PICK_FROM_CAMERA);
    }

    private void getPhotoFromGallery() { // 갤러리에서 이미지 가져오기
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(android.provider.MediaStore.Images.Media.CONTENT_TYPE);
        startActivityForResult(intent, PICK_FROM_ALBUM);
    }


    /*
    // Save Bitmap image
    private void storeCropImage(Bitmap bitmap, String filePath){
        Log.d(TAG, "storeCropImage");
        // Create MSG directory and save image
        String dirPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/MSG";
        File directoryMSG = new File(dirPath);

        Log.d(TAG, "dirPath : " + dirPath);

        boolean isDirectoryCreated= directoryMSG.exists();
        if (!isDirectoryCreated) {
            isDirectoryCreated= directoryMSG.mkdirs();
        }

        File copyFile = new File(filePath);
        BufferedOutputStream outputStream = null;

        try{
            boolean isFileCreated= copyFile.exists();
            if (!isFileCreated) {
                isFileCreated= copyFile.createNewFile();
            }

            outputStream = new BufferedOutputStream(new FileOutputStream(copyFile));
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);

            // sendBroadcast - renew image cropped
            sendBroadcast(new Intent(ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(copyFile)));

            Log.d(TAG, "Uri : " + Uri.fromFile(copyFile).toString());

            iv_profile.setImageURI( Uri.fromFile(copyFile));

            outputStream.flush();
            outputStream.close();

        }catch (Exception e){
            Log.e(TAG, "storeCropImage Error : " + e.getMessage());
            e.printStackTrace();
        }

    }



    private class ImageReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d(TAG, "onReceive");

            iv_profile.setImageURI(intent.getData());
        }
    }
*/
}
