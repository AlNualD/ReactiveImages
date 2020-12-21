package ru.devegang.reactiveimages;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Path;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.ImageView;

import java.util.ArrayList;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class MainActivity extends AppCompatActivity {


    private ImageView imageView1;
    private ImageView imageView2;

    private Observable<String> imagesPaths;
    private Observable<String> imagesPath2;
    private Observer<String> imagesPathObserver;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView1 = (ImageView) findViewById(R.id.imageView1);
        imageView2 = (ImageView) findViewById(R.id.imageView2);


        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)//READ_CALL_LOG)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    // Manifest.permission.READ_CALL_LOG))
                    Manifest.permission.READ_EXTERNAL_STORAGE))
            {
                // Show an explanation to the user
            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 0);
            }
        }
        else {
            //
            //images = getImagesPath(this);
            //showImages();
            getImagesPaths(this)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<String>() {
                @Override
                public void onCompleted() {

                }

                @Override
                public void onError(Throwable e) {

                }

                @Override
                public void onNext(String s) {
                    System.out.println("observer1 " + s);
                    BitmapFactory.Options bmpFactoryOptions = new BitmapFactory.Options();
                    //bmpFactoryOptions.inJustDecodeBounds = true;
                    //Bitmap bmp = BitmapFactory.decodeFile(imageFilePath, bmpFactoryOptions);
                    //bmpFactoryOptions.inSampleSize = 2;
                    bmpFactoryOptions.inJustDecodeBounds = false;
                    Bitmap bmp = BitmapFactory.decodeFile(s, bmpFactoryOptions);
                imageView1.setImageBitmap(bmp);

                }
            });

            getImagesPaths(this)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<String>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onNext(String s) {
                            System.out.println("observer2 " + s);
                            BitmapFactory.Options bmpFactoryOptions = new BitmapFactory.Options();
                            //bmpFactoryOptions.inJustDecodeBounds = true;
                            //Bitmap bmp = BitmapFactory.decodeFile(imageFilePath, bmpFactoryOptions);
                            //bmpFactoryOptions.inSampleSize = 2;
                            bmpFactoryOptions.inJustDecodeBounds = false;
                            Bitmap bmp = BitmapFactory.decodeFile(s, bmpFactoryOptions);
                            imageView2.setImageBitmap(bmp);

                        }
                    });

        }


    }


    public Observable<String> getImagesPaths(Activity activity) {
        return Observable.create(observableEmitter -> {
            Uri uri;
  //          ArrayList<String> listOfAllImages = new ArrayList<String>();
            Cursor cursor;
            int column_index_data, column_index_folder_name;
            String PathOfImage = null;
            uri = android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

            String[] projection = { MediaStore.MediaColumns.DATA,
                    MediaStore.Images.Media.BUCKET_DISPLAY_NAME };

            cursor = activity.getContentResolver().query(uri, projection, null,
                    null, null);

            column_index_data = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
            column_index_folder_name = cursor
                    .getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME);
            while (cursor.moveToNext()) {
                PathOfImage = cursor.getString(column_index_data);


                System.out.println("observable " + PathOfImage);
                observableEmitter.onNext(PathOfImage);
                int g =0;
                for (int i = 0; i < 10000000; i++) {
                    g++;
                }
                //listOfAllImages.add(PathOfImage);
            }
            observableEmitter.onCompleted();
        });
    }


    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 0: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //permisson granted
                    getImagesPaths(this)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Observer<String>() {
                                @Override
                                public void onCompleted() {

                                }

                                @Override
                                public void onError(Throwable e) {

                                }

                                @Override
                                public void onNext(String s) {
                                    System.out.println("observer1" + s);
                                    BitmapFactory.Options bmpFactoryOptions = new BitmapFactory.Options();
                                    //bmpFactoryOptions.inJustDecodeBounds = true;
                                    //Bitmap bmp = BitmapFactory.decodeFile(imageFilePath, bmpFactoryOptions);
                                    //bmpFactoryOptions.inSampleSize = 2;
                                    bmpFactoryOptions.inJustDecodeBounds = false;
                                    Bitmap bmp = BitmapFactory.decodeFile(s, bmpFactoryOptions);
                                    imageView1.setImageBitmap(bmp);

                                }
                            });

                    getImagesPaths(this)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Observer<String>() {
                                @Override
                                public void onCompleted() {

                                }

                                @Override
                                public void onError(Throwable e) {

                                }

                                @Override
                                public void onNext(String s) {
                                    System.out.println("observer2" + s);
                                    BitmapFactory.Options bmpFactoryOptions = new BitmapFactory.Options();
                                    //bmpFactoryOptions.inJustDecodeBounds = true;
                                    //Bitmap bmp = BitmapFactory.decodeFile(imageFilePath, bmpFactoryOptions);
                                    //bmpFactoryOptions.inSampleSize = 2;
                                    bmpFactoryOptions.inJustDecodeBounds = false;
                                    Bitmap bmp = BitmapFactory.decodeFile(s, bmpFactoryOptions);
                                    imageView2.setImageBitmap(bmp);

                                }
                            });

                }

                else {
                    // permission denied, boo! Disable the functionality that depends on this permission.
                }
                return;
            }
        }
    }


    public static ArrayList<String> getImagesPath(Activity activity) {
        Uri uri;
        ArrayList<String> listOfAllImages = new ArrayList<String>();
        Cursor cursor;
        int column_index_data, column_index_folder_name;
        String PathOfImage = null;
        uri = android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

        String[] projection = { MediaStore.MediaColumns.DATA,
                MediaStore.Images.Media.BUCKET_DISPLAY_NAME };

        cursor = activity.getContentResolver().query(uri, projection, null,
                null, null);

        column_index_data = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        column_index_folder_name = cursor
                .getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME);
        while (cursor.moveToNext()) {
            PathOfImage = cursor.getString(column_index_data);


            listOfAllImages.add(PathOfImage);
        }
        return listOfAllImages;
    }




}