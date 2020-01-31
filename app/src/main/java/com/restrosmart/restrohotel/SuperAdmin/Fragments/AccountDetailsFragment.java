package com.restrosmart.restrohotel.SuperAdmin.Fragments;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.github.ybq.android.spinkit.SpinKitView;
import com.google.gson.JsonObject;
import com.restrosmart.restrohotel.Interfaces.ApiService;
import com.restrosmart.restrohotel.Interfaces.IResult;
import com.restrosmart.restrohotel.R;
import com.restrosmart.restrohotel.RetrofitClientInstance;
import com.restrosmart.restrohotel.RetrofitService;
import com.restrosmart.restrohotel.SuperAdmin.Models.HotelForm;
import com.restrosmart.restrohotel.SuperAdmin.Models.HotelImageForm;
import com.restrosmart.restrohotel.SuperAdmin.Models.ImageDataForm;
import com.restrosmart.restrohotel.Utils.FilePath;
import com.restrosmart.restrohotel.Utils.ImageFilePath;
import com.restrosmart.restrohotel.Utils.Sessionmanager;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Response;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.app.Activity.RESULT_OK;
import static com.restrosmart.restrohotel.ConstantVariables.EDIT_HOTEL;
import static com.restrosmart.restrohotel.ConstantVariables.EDIT_HOTEL_IMAGE;
import static com.restrosmart.restrohotel.ConstantVariables.HOTEL_ADD_PHOTO;
import static com.restrosmart.restrohotel.ConstantVariables.HOTEL_REGISTRATION;
import static com.restrosmart.restrohotel.ConstantVariables.IMAGE1;
import static com.restrosmart.restrohotel.ConstantVariables.IMAGE2;
import static com.restrosmart.restrohotel.ConstantVariables.IMAGE3;
import static com.restrosmart.restrohotel.ConstantVariables.IMAGE4;
import static com.restrosmart.restrohotel.ConstantVariables.IMAGE5;
import static com.restrosmart.restrohotel.ConstantVariables.IMAGE6;
import static com.restrosmart.restrohotel.ConstantVariables.REQUEST_PERMISSION;
import static com.restrosmart.restrohotel.Utils.Sessionmanager.EMP_ID;

public class AccountDetailsFragment extends Fragment implements View.OnClickListener {

    private String mGstnNo, mHotelCgst, mHotelSgst, mHotelName, mHotelMob, mHotelPhone, mHotelAddress, mHotelEmail, mCuisine, mTags, mGstn, mCgst, mSgst, mArea, mStartTime, mEndTime;
    private int countryId, stateId, cityId, hoteltypeId, tableCount;
    private View view;
    private Button btnSave, btnUpdate;
    private TextInputEditText edtGstnNo, edtHotelCgst, edtHotelSgst;
    private RadioButton radioButtonYes, radioButtonNo;
    private RadioGroup radioGroupGstn;
    private LinearLayout linearLayout;
    private RetrofitService mRetrofitService;
    private IResult mResultCallBack;
    private Sessionmanager sessionmanager;
    private HashMap<String, String> superAdminInfo;
    private Bundle bundle;
    ArrayList<ImageDataForm> imageDataForms;
    private int hotelId;
    private FrameLayout frameLayout1, frameLayout2, frameLayout3, frameLayout4, frameLayout5, frameLayout6;
    private String selectedFilePath, extension, selectedData;
    private File selectedFile;
    private Bitmap bitmapImage;
    private ImageView imageView1, imageView2, imageView3, imageView4, imageView5, imageView6;
    private Drawable drawable;
    private String image1Data, image2Data, image3Data, image4Data, image5Data, image6Data;
    private String image1Ext, image2Ext, image3Ext, image4Ext, image5Ext, image6Ext;
    private SpinKitView skLoading;
    private HotelForm hotelForm;
    private ArrayList<HotelImageForm> hotelImageFormArrayList;
    int x = 1;
    private TextView tvImageTitle;
    private CardView cvHotelImages;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_account_details, null);
        init();
        skLoading.setVisibility(View.GONE);
        superAdminInfo = sessionmanager.getSuperAdminDetails();

        bundle = getArguments();
        Toast.makeText(getActivity(), "success3", Toast.LENGTH_SHORT).show();
        if (bundle != null) {
            mHotelName = bundle.getString("hotelName");


            mHotelMob = bundle.getString("hotelMob");
            mHotelPhone = bundle.getString("hotelPhone");
            mHotelAddress = bundle.getString("hotelAddress");
            mHotelEmail = bundle.getString("hotelEmail");

            hoteltypeId = bundle.getInt("hoteltypeId");
            mCuisine = bundle.getString("mCuisine");
            mTags = bundle.getString("mTags");

            countryId = bundle.getInt("countryId");
            stateId = bundle.getInt("stateId");
            cityId = bundle.getInt("cityId");
            mArea = bundle.getString("area");
            tableCount = bundle.getInt("tableCount");
            mStartTime = bundle.getString("startTime");
            mEndTime = bundle.getString("endTime");

            hotelForm = bundle.getParcelable("hotelInfo");
            hotelImageFormArrayList = bundle.getParcelableArrayList("hotelImags");

            if (hotelForm != null) {
                btnUpdate.setVisibility(View.VISIBLE);
                btnSave.setVisibility(View.GONE);
                edtGstnNo.setVisibility(View.VISIBLE);
                edtHotelCgst.setVisibility(View.VISIBLE);
                edtHotelSgst.setVisibility(View.VISIBLE);
                linearLayout.setVisibility(View.VISIBLE);

                tvImageTitle.setVisibility(View.GONE);
                cvHotelImages.setVisibility(View.GONE);





                edtGstnNo.setText(hotelForm.getGstnNo());
                edtHotelCgst.setText(hotelForm.getHotelCgst());
                edtHotelSgst.setText(hotelForm.getHotelSgst());



                btnUpdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        skLoading.setVisibility(View.VISIBLE);

                        initRetrofitCallBack();
                        ApiService service = RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);
                        mRetrofitService = new RetrofitService(mResultCallBack, getActivity());
                        mRetrofitService.retrofitData(EDIT_HOTEL, (service.editHotel(hotelForm.getHotelId(),
                                mHotelMob,
                                mHotelPhone,
                                mHotelEmail,
                                edtHotelCgst.getText().toString(),
                                edtHotelSgst.getText().toString(),
                                edtGstnNo.getText().toString(), mStartTime, mEndTime,
                                mHotelAddress,
                                mCuisine,
                                mTags
                        )));

                    }
                });
            } else {
                btnUpdate.setVisibility(View.GONE);
                btnSave.setVisibility(View.VISIBLE);
                tvImageTitle.setVisibility(View.VISIBLE);
                cvHotelImages.setVisibility(View.VISIBLE);

            }

        }

        radioGroupGstn.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (radioButtonYes.isChecked()) {
                    edtGstnNo.setVisibility(View.VISIBLE);
                    edtHotelCgst.setVisibility(View.VISIBLE);
                    edtHotelSgst.setVisibility(View.VISIBLE);
                    linearLayout.setVisibility(View.VISIBLE);


                } else {
                    edtGstnNo.setVisibility(View.GONE);
                    edtHotelCgst.setVisibility(View.GONE);
                    edtHotelSgst.setVisibility(View.GONE);
                    linearLayout.setVisibility(View.GONE);
                }

            }
        });

        frameLayout1.setOnClickListener(this);
        frameLayout2.setOnClickListener(this);
        frameLayout3.setOnClickListener(this);
        frameLayout4.setOnClickListener(this);
        frameLayout5.setOnClickListener(this);
        frameLayout6.setOnClickListener(this);

      /*  if (hotelImageFormArrayList != null) {

            for (int i = 0; i < hotelImageFormArrayList.size(); i++) {
                if (i == 0) {
                    Picasso.with(getActivity()).load(hotelImageFormArrayList.get(i).getHotelImageName())
                            .memoryPolicy(MemoryPolicy.NO_CACHE)
                            .memoryPolicy(MemoryPolicy.NO_STORE)
                            .into(imageView1);
                } else if (i == 1) {
                    Picasso.with(getActivity()).load(hotelImageFormArrayList.get(i).getHotelImageName())
                            .memoryPolicy(MemoryPolicy.NO_CACHE)
                            .memoryPolicy(MemoryPolicy.NO_STORE)
                            .into(imageView2);

                } else if (i == 2) {
                    Picasso.with(getActivity()).load(hotelImageFormArrayList.get(i).getHotelImageName())
                            .memoryPolicy(MemoryPolicy.NO_CACHE)
                            .memoryPolicy(MemoryPolicy.NO_STORE)
                            .into(imageView3);

                } else if (i == 3) {
                    Picasso.with(getActivity()).load(hotelImageFormArrayList.get(i).getHotelImageName())
                            .memoryPolicy(MemoryPolicy.NO_CACHE)
                            .memoryPolicy(MemoryPolicy.NO_STORE)
                            .into(imageView4);
                } else if (i == 4) {
                    Picasso.with(getActivity()).load(hotelImageFormArrayList.get(i).getHotelImageName())
                            .memoryPolicy(MemoryPolicy.NO_CACHE)
                            .memoryPolicy(MemoryPolicy.NO_STORE)
                            .into(imageView5);
                } else if (i == 5) {
                    Picasso.with(getActivity()).load(hotelImageFormArrayList.get(i).getHotelImageName())
                            .memoryPolicy(MemoryPolicy.NO_CACHE)
                            .memoryPolicy(MemoryPolicy.NO_STORE)
                            .into(imageView6);
                }
            }
        }*/


        btnSave.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                skLoading.setVisibility(View.VISIBLE);
                initRetrofitCallBack();
                ApiService service = RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);
                mRetrofitService = new RetrofitService(mResultCallBack, getActivity());
                mRetrofitService.retrofitData(HOTEL_REGISTRATION, (service.hotelRegistration(mHotelName,
                        mHotelMob,
                        mHotelPhone,
                        mHotelEmail,
                        countryId,
                        stateId,
                        cityId,
                        mArea,
                        mHotelAddress,
                        Integer.parseInt((superAdminInfo.get(EMP_ID))),
                        hoteltypeId,
                        tableCount,
                        mCuisine,
                        mTags,
                        edtGstnNo.getText().toString(),
                        edtHotelCgst.getText().toString(),
                        edtHotelSgst.getText().toString(), mStartTime, mEndTime)));

            }
        });
        return view;
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions((Activity) getContext(), new String[]{READ_EXTERNAL_STORAGE, WRITE_EXTERNAL_STORAGE}, REQUEST_PERMISSION);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
       /* switch (requestCode) {

            case PICK_GALLERY_IMAGE:*/
        if (resultCode == RESULT_OK) {
            if (data == null) {
                //no data present
                return;
            }

            Uri selectedFileUri = data.getData();
            selectedFilePath = FilePath.getPath(getContext(), selectedFileUri);

            Bitmap categoryBitmap = null;
            try {
                categoryBitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), selectedFileUri);
            } catch (IOException e) {
                e.printStackTrace();
            }

            String picturePath = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
                picturePath = ImageFilePath.getPath(getContext(), selectedFileUri);
            }

            selectedFile = new File(selectedFilePath);
            int file_size = Integer.parseInt(String.valueOf(selectedFile.length() / 1024));     //calculate size of image in KB
            if (file_size <= 1024) {

                extension = getFileExtension(selectedFile);
                bitmapImage = exifInterface(picturePath, categoryBitmap);
                selectedData = getImageString(bitmapImage);

                ImageDataForm imageDataForm = new ImageDataForm();
                imageDataForm.setImageExt(extension);
                imageDataForm.setImageData(selectedData);

                imageDataForms.add(imageDataForm);
            }


            if (selectedFilePath != null && !selectedFilePath.equals("")) {

                byte[] decodedString = Base64.decode(selectedData, Base64.DEFAULT);
                Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                drawable = new BitmapDrawable(decodedByte);
                Toast.makeText(getActivity(), "requestCode" + resultCode, Toast.LENGTH_SHORT).show();

                switch (requestCode) {
                    case IMAGE1:
                        frameLayout1.setBackground(drawable);
                        imageView1.setVisibility(View.GONE);
                        image1Data = selectedData;
                        image1Ext = extension;
                        break;

                    case IMAGE2:
                        frameLayout2.setBackground(drawable);
                        imageView2.setVisibility(View.GONE);
                        image2Data = selectedData;
                        image2Ext = extension;
                        break;

                    case IMAGE3:
                        frameLayout3.setBackground(drawable);
                        imageView3.setVisibility(View.GONE);
                        image3Data = selectedData;
                        image3Ext = extension;
                        break;

                    case IMAGE4:
                        frameLayout4.setBackground(drawable);
                        imageView4.setVisibility(View.GONE);
                        image4Data = selectedData;
                        image4Ext = extension;
                        break;

                    case IMAGE5:
                        frameLayout5.setBackground(drawable);
                        imageView5.setVisibility(View.GONE);
                        image5Data = selectedData;
                        image5Ext = extension;

                        break;

                    case IMAGE6:
                        frameLayout6.setBackground(drawable);
                        imageView6.setVisibility(View.GONE);
                        image6Data = selectedData;
                        image6Ext = extension;
                        break;
                }
            } else {
                Toast.makeText(getContext(), R.string.cannot_upload_file, Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getContext(), R.string.selected_image_size, Toast.LENGTH_SHORT).show();
        }
    }

    private void addPhoto(String imageData, String imageExe) {
        skLoading.setVisibility(View.VISIBLE);
        initRetrofitCallBack();
        ApiService service = RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);
        mRetrofitService = new RetrofitService(mResultCallBack, getActivity());
        mRetrofitService.retrofitData(HOTEL_ADD_PHOTO, (service.saveHotelImage(hotelId, imageData, imageExe)));

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

    private static String getFileExtension(File file) {
        String fileName = file.getName();
        if (fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0)
            return fileName.substring(fileName.lastIndexOf(".") + 1);
        else return "";
    }

    public String getImageString(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        return Base64.encodeToString(imageBytes, Base64.DEFAULT);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_PERMISSION:
                if (grantResults.length > 0) {

                    boolean StoragePermission = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean RecordPermission = grantResults[1] == PackageManager.PERMISSION_GRANTED;

                    if (StoragePermission && RecordPermission) {
                        Toast.makeText(getContext(), R.string.permission_granted, Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getContext(), R.string.permission_denied, Toast.LENGTH_LONG).show();
                    }
                }
                break;
        }
    }


    private void openFileChooser(int image) {
        Intent imageIntent = new Intent();
        imageIntent.setType("image/*");
        imageIntent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(imageIntent, "Select Image"), image);
    }

    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(getContext(), READ_EXTERNAL_STORAGE);
        int result1 = ContextCompat.checkSelfPermission(getContext(), WRITE_EXTERNAL_STORAGE);
        return result == PackageManager.PERMISSION_GRANTED && result1 == PackageManager.PERMISSION_GRANTED;
    }

    private void initRetrofitCallBack() {
        mResultCallBack = new IResult() {
            @Override
            public void notifySuccess(int requestId, Response<JsonObject> response) {
                JsonObject jsonObject = response.body();
                String objectInfo = jsonObject.toString();

                switch (requestId) {
                    case HOTEL_REGISTRATION:
                        try {
                            JSONObject object = new JSONObject(objectInfo);
                            int status = object.getInt("status");
                            if (status == 1) {
                                Toast.makeText(getActivity(), object.getString("message"), Toast.LENGTH_SHORT).show();
                                hotelId = object.getInt("Hotel_Id");
                                if (imageDataForms != null && imageDataForms.size() > 0) {
                                    addPhoto(imageDataForms.get(0).getImageData(), imageDataForms.get(0).getImageExt());
                                }

                            } else {
                                Toast.makeText(getActivity(), object.getString("message"), Toast.LENGTH_SHORT).show();
                                //    getActivity().finish();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        break;

                    case HOTEL_ADD_PHOTO:
                        try {
                            JSONObject object = new JSONObject(objectInfo);
                            int status = object.getInt("status");
                            //  Toast.makeText(getActivity(), object.getString("message"), Toast.LENGTH_SHORT).show();
                            if (status == 1) {
                                //Toast.makeText(getActivity(), object.getString("message"), Toast.LENGTH_SHORT).show();
                                if (imageDataForms != null && imageDataForms.size() > 0) {
                                    if (x != imageDataForms.size()) {
                                        addPhoto(imageDataForms.get(x).getImageData(), imageDataForms.get(x).getImageExt());
                                        x++;
                                    } else {
                                        skLoading.setVisibility(View.GONE);
                                        Toast.makeText(getActivity(), object.getString("message"), Toast.LENGTH_SHORT).show();
                                        getActivity().finish();
                                    }
                                }
                            } else {
                                Toast.makeText(getActivity(), object.getString("message"), Toast.LENGTH_SHORT).show();
                            }
                            skLoading.setVisibility(View.GONE);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        break;

                    case EDIT_HOTEL:
                        try {
                            JSONObject object = new JSONObject(objectInfo);
                            int status = object.getInt("status");
                            if (status == 1) {
                                Toast.makeText(getActivity(), object.getString("message"), Toast.LENGTH_SHORT).show();
                                // retrofitCall();
                               /* hotelId =hotelForm.getHotelId();
                                if (imageDataForms != null && imageDataForms.size() > 0) {
                                    editPhoto(imageDataForms.get(0).getImageData(), imageDataForms.get(0).getImageExt());
                                }
*/
                            } else {
                                Toast.makeText(getActivity(), object.getString("message"), Toast.LENGTH_SHORT).show();
                            }
                            skLoading.setVisibility(View.GONE);
                            // getActivity().getFragmentManager().popBackStack();
                            getActivity().finish();


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        break;
                }
            }

            @Override
            public void notifyError(int requestId, Throwable error) {
                Log.d("", "RequestId" + requestId);
                Log.d("", "RetrofitError" + error);
            }
        };
    }

  /*  private void editPhoto(String imageData, String imageExt) {
        skLoading.setVisibility(View.VISIBLE);
        initRetrofitCallBack();
        ApiService service = RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);
        mRetrofitService = new RetrofitService(mResultCallBack, getActivity());
        mRetrofitService.retrofitData(EDIT_HOTEL, (service.editHotelImage(hotelId,"", imageData,
                imageExt,1)));
    }*/

    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle("Hotel Account Details");
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString("gstnNo", mGstnNo);
        outState.putString("hotelCgst", mHotelCgst);
        outState.putString("hotelSgst", mHotelSgst);
     /*   outState.putString("hotelRanking", mHotelRanking);
        outState.putString("hotelRating", mHotelRating);
        outState.putString("hotelVisitCnt",mHotelVisitCnt);
        outState.putString("hotelStatus",mHotelStatus);*/

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (savedInstanceState != null) {

            mGstnNo = savedInstanceState.getString("gstnNo");
            mHotelCgst = savedInstanceState.getString("hotelCgst");
            mHotelSgst = savedInstanceState.getString("hotelSgst");

            edtGstnNo.setText(mGstnNo);
            edtHotelCgst.setText(mHotelCgst);
            edtHotelSgst.setText(mHotelSgst);


        }
    }


    private void init() {
        edtGstnNo = view.findViewById(R.id.edt_hote_gstn_no);

        edtHotelCgst = view.findViewById(R.id.edt_hotel_cgst);
        edtHotelSgst = view.findViewById(R.id.edt_hotel_sgst);
        linearLayout = view.findViewById(R.id.linear_layout);

        btnSave = view.findViewById(R.id.btn_account_hotel_details);
        radioButtonYes = view.findViewById(R.id.radio_btn_yes);
        radioButtonNo = view.findViewById(R.id.radio_btn_no);
        radioGroupGstn = view.findViewById(R.id.radio_group);
        sessionmanager = new Sessionmanager(getActivity());
        frameLayout1 = view.findViewById(R.id.fl_image1);
        frameLayout2 = view.findViewById(R.id.fl_image2);
        frameLayout3 = view.findViewById(R.id.fl_image3);
        frameLayout4 = view.findViewById(R.id.fl_image4);
        frameLayout5 = view.findViewById(R.id.fl_image5);
        frameLayout6 = view.findViewById(R.id.fl_image6);


        imageView1 = view.findViewById(R.id.image1);
        imageView2 = view.findViewById(R.id.image2);
        imageView3 = view.findViewById(R.id.image3);
        imageView4 = view.findViewById(R.id.image4);
        imageView5 = view.findViewById(R.id.image5);
        imageView6 = view.findViewById(R.id.image6);
        skLoading = view.findViewById(R.id.skLoading);

        imageDataForms = new ArrayList<>();
        btnUpdate = view.findViewById(R.id.btn_account_hotel_update);
        tvImageTitle=view.findViewById(R.id.tv_hotel_image);
        cvHotelImages=view.findViewById(R.id.card_hotel_images);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.fl_image1:
                selectImage(IMAGE1);
                break;

            case R.id.fl_image2:
                selectImage(IMAGE2);
                break;

            case R.id.fl_image3:
                selectImage(IMAGE3);
                break;

            case R.id.fl_image4:
                selectImage(IMAGE4);
                break;

            case R.id.fl_image5:
                selectImage(IMAGE5);
                break;

            case R.id.fl_image6:
                selectImage(IMAGE6);
                break;


        }
    }


    private void selectImage(int image) {
        if (checkPermission()) {
            openFileChooser(image);
        } else {
            requestPermission();
        }
    }
}
