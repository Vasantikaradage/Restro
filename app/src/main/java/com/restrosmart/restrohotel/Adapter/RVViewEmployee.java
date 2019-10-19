package com.restrosmart.restrohotel.Adapter;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.restrosmart.restrohotel.Admin.ActivityEmpolyeeProfile;
import com.restrosmart.restrohotel.Interfaces.ApiService;
import com.restrosmart.restrohotel.Interfaces.IResult;
import com.restrosmart.restrohotel.Model.EmployeeForm;
import com.restrosmart.restrohotel.R;
import com.restrosmart.restrohotel.RetrofitClientInstance;
import com.restrosmart.restrohotel.RetrofitService;
import com.restrosmart.restrohotel.Utils.Sessionmanager;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Response;

import static com.restrosmart.restrohotel.ConstantVariables.EMPLOYEE_STATUS;
import static com.restrosmart.restrohotel.Utils.Sessionmanager.BRANCH_ID;
import static com.restrosmart.restrohotel.Utils.Sessionmanager.HOTEL_ID;

/**
 * Created by SHREE on 10/9/2018.
 */
public class RVViewEmployee extends RecyclerView.Adapter<RVViewEmployee.MyHolder> {
    List<EmployeeForm> viewEmployees;
    private Context context;
    private View view;
    private int emp_id, status1;
    private Sessionmanager sessionmanager;
    private String branchId, hotelId, status_value;
    private Animator currentAnimator;
    private int shortAnimationDuration;
    private IResult mResultCallBack;
    private RetrofitService mRetrofitService;

    public RVViewEmployee(FragmentActivity activity, List<EmployeeForm> getEmployee) {
        this.context = activity;
        this.viewEmployees = getEmployee;
    }

    @NonNull
    @Override
    public RVViewEmployee.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_view_employee_list, parent, false);
        MyHolder vh = new MyHolder(view); // pass the view to View Holder
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull final RVViewEmployee.MyHolder holder, final int position) {
        holder.mName.setText(viewEmployees.get(position).getEmpName());
        holder.mDesignation.setText(viewEmployees.get(position).getRole());
        status1 = (viewEmployees.get(position).getActiveStatus());

        sessionmanager = new Sessionmanager(context);
        HashMap<String, String> name_info = sessionmanager.getHotelDetails();

        hotelId = name_info.get(HOTEL_ID);

        String path = viewEmployees.get(position).getEmpImg();
        Picasso.with(context).load(path).into(holder.circleImageView);

    /*    holder.circleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.circleImageView.setAnimation(AnimationUtils.loadAnimation(context, R.anim.zoom_in));
            }
        });

        shortAnimationDuration = context.getResources().getInteger(
                android.R.integer.config_shortAnimTime);
*/
        if (status1 == 1) {
            holder.status.setChecked(true);
        } else {
            holder.status.setChecked(false);
        }

        holder.status.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                emp_id = viewEmployees.get(position).getEmpId();
                if (holder.status.isChecked()) {
                    status_value = "1";
                    statusChange();
                } else {
                    status_value = "0";
                    statusChange();
                }
            }
        });

        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ActivityEmpolyeeProfile.class);
                intent.putExtra("empId", viewEmployees.get(position).getEmpId());
                intent.putExtra("empRole", viewEmployees.get(position).getRole());
                intent.putParcelableArrayListExtra("Emp_detail", (ArrayList<? extends Parcelable>) viewEmployees);
                context.startActivity(intent);
            }
        });
    }

    private void zoomImageFromThumb(final CircleImageView circleImageView, String toString) {


        // If there's an animation in progress, cancel it
        // immediately and proceed with this one.
        if (currentAnimator != null) {
            currentAnimator.cancel();
        }

        // Load the high-resolution "zoomed-in" image.
        final ImageView expandedImageView = view.findViewById(
                R.id.expanded_image);
        // expandedImageView.setImageResource(toString);

        // Calculate the starting and ending bounds for the zoomed-in image.
        // This step involves lots of math. Yay, math.
        final Rect startBounds = new Rect();
        final Rect finalBounds = new Rect();
        final Point globalOffset = new Point();

        // The start bounds are the global visible rectangle of the thumbnail,
        // and the final bounds are the global visible rectangle of the container
        // view. Also set the container view's offset as the origin for the
        // bounds, since that's the origin for the positioning animation
        // properties (X, Y).
        circleImageView.getGlobalVisibleRect(startBounds);

        view.findViewById(R.id.container)
                .getGlobalVisibleRect(finalBounds, globalOffset);
        startBounds.offset(-globalOffset.x, -globalOffset.y);
        finalBounds.offset(-globalOffset.x, -globalOffset.y);

        // Adjust the start bounds to be the same aspect ratio as the final
        // bounds using the "center crop" technique. This prevents undesirable
        // stretching during the animation. Also calculate the start scaling
        // factor (the end scaling factor is always 1.0).
        float startScale;
        if ((float) finalBounds.width() / finalBounds.height()
                > (float) startBounds.width() / startBounds.height()) {
            // Extend start bounds horizontally
            startScale = (float) startBounds.height() / finalBounds.height();
            float startWidth = startScale * finalBounds.width();
            float deltaWidth = (startWidth - startBounds.width()) / 2;
            startBounds.left -= deltaWidth;
            startBounds.right += deltaWidth;
        } else {
            // Extend start bounds vertically
            startScale = (float) startBounds.width() / finalBounds.width();
            float startHeight = startScale * finalBounds.height();
            float deltaHeight = (startHeight - startBounds.height()) / 2;
            startBounds.top -= deltaHeight;
            startBounds.bottom += deltaHeight;
        }

        // Hide the thumbnail and show the zoomed-in view. When the animation
        // begins, it will position the zoomed-in view in the place of the
        // thumbnail.
        circleImageView.setAlpha(0f);
        expandedImageView.setVisibility(View.VISIBLE);

        // Set the pivot point for SCALE_X and SCALE_Y transformations
        // to the top-left corner of the zoomed-in view (the default
        // is the center of the view).
        expandedImageView.setPivotX(0f);
        expandedImageView.setPivotY(0f);

        // Construct and run the parallel animation of the four translation and
        // scale properties (X, Y, SCALE_X, and SCALE_Y).
        AnimatorSet set = new AnimatorSet();
        set
                .play(ObjectAnimator.ofFloat(expandedImageView, View.X,
                        startBounds.left, finalBounds.left))
                .with(ObjectAnimator.ofFloat(expandedImageView, View.Y,
                        startBounds.top, finalBounds.top))
                .with(ObjectAnimator.ofFloat(expandedImageView, View.SCALE_X,
                        startScale, 1f))
                .with(ObjectAnimator.ofFloat(expandedImageView,
                        View.SCALE_Y, startScale, 1f));
        set.setDuration(shortAnimationDuration);
        set.setInterpolator(new DecelerateInterpolator());
        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                currentAnimator = null;
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                currentAnimator = null;
            }
        });
        set.start();
        currentAnimator = set;

        // Upon clicking the zoomed-in image, it should zoom back down
        // to the original bounds and show the thumbnail instead of
        // the expanded image.
        final float startScaleFinal = startScale;
        expandedImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentAnimator != null) {
                    currentAnimator.cancel();
                }

                // Animate the four positioning/sizing properties in parallel,
                // back to their original values.
                AnimatorSet set = new AnimatorSet();
                set.play(ObjectAnimator
                        .ofFloat(expandedImageView, View.X, startBounds.left))
                        .with(ObjectAnimator
                                .ofFloat(expandedImageView,
                                        View.Y, startBounds.top))
                        .with(ObjectAnimator
                                .ofFloat(expandedImageView,
                                        View.SCALE_X, startScaleFinal))
                        .with(ObjectAnimator
                                .ofFloat(expandedImageView,
                                        View.SCALE_Y, startScaleFinal));
                set.setDuration(shortAnimationDuration);
                set.setInterpolator(new DecelerateInterpolator());
                set.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        circleImageView.setAlpha(1f);
                        expandedImageView.setVisibility(View.GONE);
                        currentAnimator = null;
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {
                        circleImageView.setAlpha(1f);
                        expandedImageView.setVisibility(View.GONE);
                        currentAnimator = null;
                    }
                });
                set.start();
                currentAnimator = set;
            }
        });
    }

    private void statusChange() {
        retrofitCallBack();
        ApiService apiService = RetrofitClientInstance.getRetrofitInstance().create(ApiService.class);
        mRetrofitService = new RetrofitService(mResultCallBack, context);
        mRetrofitService.retrofitData(EMPLOYEE_STATUS, (apiService.updateStatus(emp_id,
                status_value,
                (Integer.parseInt(hotelId)))));
    }

    private void retrofitCallBack() {
        mResultCallBack = new IResult() {
            @Override
            public void notifySuccess(int requestId, Response<JsonObject> response) {
                JsonObject jsonObject = response.body();
                String objectInfo = jsonObject.toString();
                try {
                    JSONObject object = new JSONObject(objectInfo);
                    int status = object.getInt("status");
                    if (status == 1) {
                        Toast.makeText(context, "Status Updated Successfully..", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, "Something went wrong.. try again later", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void notifyError(int requestId, Throwable error) {
                Log.d("", "requestId" + requestId);
                Log.d("", "RetrofitError" + error);
            }
        };
    }

    @Override
    public int getItemCount() {
        return viewEmployees.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        private CircleImageView circleImageView;
        private TextView mName;
        private TextView mDesignation;
        private RelativeLayout relativeLayout;
        private Switch status;

        public MyHolder(View itemView){
            super(itemView);
            circleImageView = (CircleImageView) itemView.findViewById(R.id.civ_emp_profile);
            mDesignation = (TextView) itemView.findViewById(R.id.tv_emp_designation);
            mName = (TextView) itemView.findViewById(R.id.tv_emp_name);
            status = (Switch) itemView.findViewById(R.id.switch_status);
            relativeLayout = (RelativeLayout) itemView.findViewById(R.id.relative_employee);
        }
    }
}
