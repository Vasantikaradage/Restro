package com.restrosmart.restrohotel.SuperAdmin.Activities;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;


import com.github.ybq.android.spinkit.SpinKitView;
import com.google.gson.JsonObject;
import com.restrosmart.restrohotel.Interfaces.ApiService;
import com.restrosmart.restrohotel.Interfaces.IResult;
import com.restrosmart.restrohotel.Interfaces.PositionListener;
import com.restrosmart.restrohotel.R;
import com.restrosmart.restrohotel.RetrofitClientInstance;
import com.restrosmart.restrohotel.RetrofitService;
import com.restrosmart.restrohotel.SuperAdmin.Adapters.RVCuisineDisplay;
import com.restrosmart.restrohotel.SuperAdmin.Adapters.RVHotelImageDetailsAdapter;
import com.restrosmart.restrohotel.SuperAdmin.Adapters.RVTagsDisplay;
import com.restrosmart.restrohotel.SuperAdmin.Interfaces.ImageSelectedListerner;
import com.restrosmart.restrohotel.SuperAdmin.Models.CuisineForm;
import com.restrosmart.restrohotel.SuperAdmin.Models.HotelForm;
import com.restrosmart.restrohotel.SuperAdmin.Models.HotelImageForm;
import com.restrosmart.restrohotel.SuperAdmin.Models.TagsForm;
import com.restrosmart.restrohotel.Utils.FilePath;
import com.restrosmart.restrohotel.Utils.ImageFilePath;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import retrofit2.Response;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static com.restrosmart.restrohotel.ConstantVariables.EDIT_HOTEL_IMAGE;
import static com.restrosmart.restrohotel.ConstantVariables.HOTEL_ADD_PHOTO;
import static com.restrosmart.restrohotel.ConstantVariables.PICK_GALLERY_IMAGE;
import static com.restrosmart.restrohotel.ConstantVariables.REQUEST_PERMISSION;

public class ActivityViewHotelDetails extends AppCompatActivity {
    private HotelForm hotelForm;
    private RecyclerView rvCuisine, rvTags, rvHotelImage;
    private ArrayList<CuisineForm> cuisineFormArrayList;
    private ArrayList<TagsForm> tagsFormArrayList;
    private ArrayList<HotelImageForm> hotelImageFormArrayList;
    private ImageView ivHotelFullImage,ivImageEdit;
    private RVHotelImageDetailsAdapter rvHotelImageDetailsAdapter;

    private RVCuisineDisplay rvCuisineDisplay;
    private RVTagsDisplay rvTagsDisplay;
    private RatingBar ratingBar;
    private Toolbar toolbar;
    private TextView toolBarText;
    private SpinKitView skLoading;
    private PositionListener positionListener;
    private String selectedFilePath, extension, selectedData, selectedImage, subString;
    private File selectedFile;
    private Bitmap bitmapImage;
    private RetrofitService mRetrofitService;
    private IResult mResultCallBack;
    private  int editedItemImageId,editPos;
    private TextView tvName, tvEmail, tvNoImage, tvPhone, tvRatingBar, tvTiming, tvMob, tvAddress, tvCountry, tvState, tvCity, tvHotelType, tvGstn, tvCgst, tvSgst, tvTableCount;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activtiy_view_hotel_details);
        init();
        setUpToolBar();


        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            skLoading.setVisibility(View.GONE);
            hotelForm = bundle.getParcelable("hotelInfo");

            tvName.setText(hotelForm.getHotelName());
            tvEmail.setText(hotelForm.getHotelEmail());
            tvMob.setText(hotelForm.getHotelMob() + " / " + hotelForm.getHotelPhone());
            tvAddress.setText(hotelForm.getHotelAddress());
            tvGstn.setText(hotelForm.getGstnNo());
            tvSgst.setText(hotelForm.getHotelSgst());
            tvCgst.setText(hotelForm.getHotelCgst());
            tvCountry.setText(hotelForm.getHotelCountry());
            tvState.setText(hotelForm.getHotelState());
            tvCity.setText(hotelForm.getHotelCity());
            tvTiming.setText(hotelForm.getHotelStartTime() + " : " + hotelForm.getHotelEndTime());

            tvHotelType.setText("(" + hotelForm.getHoteltype() + ")");

            Drawable drawable = ratingBar.getProgressDrawable();
            drawable.setColorFilter(Color.parseColor("#FFC107"), PorterDuff.Mode.SRC_ATOP);
            ratingBar.setRating(Integer.parseInt(hotelForm.getHotelRating()));


            hotelImageFormArrayList = bundle.getParcelableArrayList("hotelImags");
            if (hotelImageFormArrayList.size() != 0) {
                tvNoImage.setVisibility(View.GONE);
                Picasso.with(this).load(hotelImageFormArrayList.get(0).getHotelImageName()).into(ivHotelFullImage);
                editedItemImageId=hotelImageFormArrayList.get(0).getHotelImageId();
                editPos=0;

                //Image Adapter
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
                rvHotelImage.setLayoutManager(linearLayoutManager);
                rvHotelImage.setHasFixedSize(true);
                rvHotelImage.getLayoutManager().setMeasurementCacheEnabled(false);
                rvHotelImageDetailsAdapter = new RVHotelImageDetailsAdapter(this, hotelImageFormArrayList, new PositionListener() {
                    @Override
                    public void positionListern(int position) {
                        if (checkPermission()) {

                            Intent imageIntent = new Intent();
                            imageIntent.setType("image/*");
                            imageIntent.setAction(Intent.ACTION_GET_CONTENT);
                            startActivityForResult(Intent.createChooser(imageIntent, "Select Image"), PICK_GALLERY_IMAGE);
                        } else {
                            requestPermission();
                        }
                    }
                }, new ImageSelectedListerner() {
                    @Override
                    public void SelectedImage(int adapterPosition) {
                        //editedItemImageId =adapterPosition;
                        editedItemImageId=hotelImageFormArrayList.get(adapterPosition).getHotelImageId();
                        editPos=adapterPosition;
                        Picasso.with(ActivityViewHotelDetails.this).load(hotelImageFormArrayList.get(adapterPosition).getHotelImageName()).into(ivHotelFullImage);
                    }
                });

                rvHotelImage.setAdapter(rvHotelImageDetailsAdapter);
                rvHotelImage.setNestedScrollingEnabled(false);

            } else {
                tvNoImage.setVisibility(View.VISIBLE);
            }
            cuisineFormArrayList = bundle.getParcelableArrayList("CuisineList");


            //cuisineAdapter
            GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
            rvCuisine.setLayoutManager(gridLayoutManager);
            rvCuisine.setHasFixedSize(true);
            rvCuisine.getLayoutManager().setMeasurementCacheEnabled(false);
            rvCuisineDisplay = new RVCuisineDisplay(this, cuisineFormArrayList);
            rvCuisine.setAdapter(rvCuisineDisplay);

            //Tag adapter
            tagsFormArrayList = bundle.getParcelableArrayList("TagsList");
            GridLayoutManager gridLayoutManager1 = new GridLayoutManager(this, 2);
            rvTags.setLayoutManager(gridLayoutManager1);
            rvTags.setHasFixedSize(true);
            rvTags.getLayoutManager().setMeasurementCacheEnabled(false);
            rvTagsDisplay = new RVTagsDisplay(getApplicationContext(), tagsFormArrayList);
            rvTags.setAdapter(rvTagsDisplay);

            ivImageEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (checkPermission()) {
                        Intent imageIntent = new Intent();
                        imageIntent.setType("image/*");
                        imageIntent.setAction(Intent.ACTION_GET_CONTENT);
                        startActivityForResult(Intent.createChooser(imageIntent, "Select Image"), PICK_GALLERY_IMAGE);
                    } else {
                        requestPermission();
                    }
                }
            });


        }
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions((Activity) this, new String[]{READ_EXTERNAL_STORAGE, WRITE_EXTERNAL_STORAGE}, REQUEST_PERMISSION);

    }

    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(this, READ_EXTERNAL_STORAGE);
        int result1 = ContextCompat.checkSelfPermission(this, WRITE_EXTERNAL_STORAGE);
        return result == PackageManager.PERMISSION_GRANTED && result1 == PackageManager.PERMISSION_GRANTED;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.hotel_detail_edit, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.edit_hotel) {
            Intent intent = new Intent(ActivityViewHotelDetails.this, ActivityAddHotel.class);
            Bundle bundle = new Bundle();
            // bundle.putSerializable("hotelInfo", (hotelForm));
            bundle.putParcelableArrayList("hotelImags", hotelImageFormArrayList);
            bundle.putParcelableArrayList("CuisineList", cuisineFormArrayList);
            bundle.putParcelableArrayList("TagsList", tagsFormArrayList);
            intent.putExtras(bundle);
            intent.putExtra("hotelInfo", (hotelForm));
            startActivity(intent);
            finish();
            return true;

        }
        return super.onOptionsItemSelected(item);
    }


    private void setUpToolBar() {
        toolBarText.setText("Hotel Details");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        if (resultCode == RESULT_OK) {
            if (data == null) {
                //no data present
                return;
            }

            Uri selectedFileUri = data.getData();
            String selectedFilePath = FilePath.getPath(this, selectedFileUri);
            Bitmap categoryBitmap = null;
            try {
                categoryBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedFileUri);
            } catch (IOException e) {
                e.printStackTrace();
            }

            String picturePath = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
                picturePath = ImageFilePath.getPath(this, selectedFileUri);
            }

            selectedFile = new File(selectedFilePath);
            int file_size = Integer.parseInt(String.valueOf(selectedFile.length() / 1024));     //calculate size of image in KB
            if (file_size <= 1024) {

                extension = getFileExtension(selectedFile);
                bitmapImage = exifInterface(picturePath, categoryBitmap);
                selectedData = getImageString(bitmapImage);

                byte[] decodedString = Base64.decode(selectedData, Base64.NO_WRAP);
                InputStream inputStream = new ByteArrayInputStream(decodedString);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);

                if(editedItemImageId !=0)
                {
                    skLoading.setVisibility(View.VISIBLE);
                    String image=hotelImageFormArrayList.get(editPos).getHotelImageName().substring(hotelImageFormArrayList.get(editPos).getHotelImageName().lastIndexOf("/") + 1);
                    initRetrofitCallBack();
                    ApiService service = RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);
                    mRetrofitService = new RetrofitService(mResultCallBack, ActivityViewHotelDetails.this);
                    mRetrofitService.retrofitData(EDIT_HOTEL_IMAGE, (service.editHotelImage(hotelForm.getHotelId(),image, selectedData, extension,editedItemImageId)));
                }else {
                    skLoading.setVisibility(View.VISIBLE);
                    initRetrofitCallBack();
                    ApiService service = RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);
                    mRetrofitService = new RetrofitService(mResultCallBack, ActivityViewHotelDetails.this);
                    mRetrofitService.retrofitData(HOTEL_ADD_PHOTO, (service.saveHotelImage(hotelForm.getHotelId(), selectedData, extension)));
                }
                //imageView.setImageBitmap(bitmap);

                /*if (selectedFilePath != null && !selectedFilePath.equals("")) {
 //tvStudyFileName.setText(selectedFilePath);
                   // alertDialog.dismiss();
                } else {
                    Toast.makeText(ActivityNewAddEmployee.this, "cannot upload image", Toast.LENGTH_SHORT).show();
                }*/
            } else {
                Toast.makeText(ActivityViewHotelDetails.this, "upload image up to 1 mb", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void initRetrofitCallBack() {
        mResultCallBack = new IResult() {
            @Override
            public void notifySuccess(int requestId, Response<JsonObject> response) {
                JsonObject jsonObject = response.body();
                String objectInfo = jsonObject.toString();

                switch (requestId) {
                    case HOTEL_ADD_PHOTO:
                        try {
                            JSONObject object = new JSONObject(objectInfo);
                            int status = object.getInt("status");

                            if (status == 1) {
                                skLoading.setVisibility(View.GONE);
                                Toast.makeText(ActivityViewHotelDetails.this, object.getString("message"), Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(ActivityViewHotelDetails.this, object.getString("message"), Toast.LENGTH_SHORT).show();
                            }
                            finish();
                        } catch (Exception e) {
                            e.printStackTrace();

                        }
                        break;

                    case EDIT_HOTEL_IMAGE:

                        try {
                            JSONObject object = new JSONObject(objectInfo);
                            int status = object.getInt("status");

                            if (status == 1) {
                                skLoading.setVisibility(View.GONE);
                                Toast.makeText(ActivityViewHotelDetails.this, object.getString("message"), Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(ActivityViewHotelDetails.this, object.getString("message"), Toast.LENGTH_SHORT).show();
                            }
                            finish();
                        } catch (Exception e) {
                            e.printStackTrace();

                        }
                        break;
                }
            }


            @Override
            public void notifyError(int requestId, Throwable error) {
                Log.d("", "requestId" + requestId);
                Log.d("", "retrofitError" + error);


            }
        };
    }

    public String getImageString(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        return Base64.encodeToString(imageBytes, Base64.DEFAULT);
    }

    private Bitmap exifInterface(String filePath, Bitmap bitmap) {
        ExifInterface exif = null;
        try {
            exif = new ExifInterface(filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert exif != null;
        int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                ExifInterface.ORIENTATION_UNDEFINED);

        return rotateBitmap(bitmap, orientation);
    }

    private Bitmap rotateBitmap(Bitmap bitmap, int orientation) {
        Matrix matrix = new Matrix();
        switch (orientation) {
            case ExifInterface.ORIENTATION_NORMAL:
                return bitmap;
            case ExifInterface.ORIENTATION_FLIP_HORIZONTAL:
                matrix.setScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_ROTATE_180:
                matrix.setRotate(180);
                break;
            case ExifInterface.ORIENTATION_FLIP_VERTICAL:
                matrix.setRotate(180);
                matrix.postScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_TRANSPOSE:
                matrix.setRotate(90);
                matrix.postScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_ROTATE_90:
                matrix.setRotate(90);
                break;
            case ExifInterface.ORIENTATION_TRANSVERSE:
                matrix.setRotate(-90);
                matrix.postScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_ROTATE_270:
                matrix.setRotate(-90);
                break;
            default:
                return bitmap;
        }
        try {
            Bitmap bmRotated = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
            bitmap.recycle();
            return bmRotated;
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
            return null;
        }
    }


    private String getFileExtension(File selectedFile) {
        String fileName = selectedFile.getName();
        if (fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0)
            return fileName.substring(fileName.lastIndexOf(".") + 1);
        else return "";
    }


    private void init() {
        toolbar = findViewById(R.id.toolbar);
        toolBarText = toolbar.findViewById(R.id.tx_title);
        tvName = findViewById(R.id.tv_hotel_name);
        tvEmail = findViewById(R.id.tv_hotel_email);
        tvMob = findViewById(R.id.tv_mob);
        tvAddress = findViewById(R.id.tv_address);
        tvGstn = findViewById(R.id.tv_gstn_no);
        tvSgst = findViewById(R.id.tv_sgstn_no);
        tvCgst = findViewById(R.id.tv_cgstn_no);
        tvTableCount = findViewById(R.id.tv_table_count);

        rvCuisine = findViewById(R.id.rv_cuisine);
        rvTags = findViewById(R.id.rv_tags_details);

        ivHotelFullImage = findViewById(R.id.ivFullImage);
        rvHotelImage = findViewById(R.id.rvHotelImages);

        ratingBar = findViewById(R.id.ratingBar);
        tvHotelType = findViewById(R.id.tv_hotel_type);
        tvTiming = findViewById(R.id.tv_timing);
        tvNoImage = findViewById(R.id.tv_no_image);
        tvCountry = findViewById(R.id.tv_country);
        tvState = findViewById(R.id.tv_state);
        tvCity = findViewById(R.id.tv_city);
        skLoading = findViewById(R.id.skLoading);
        ivImageEdit=findViewById(R.id.ivBtn_image_edit);
    }
}
