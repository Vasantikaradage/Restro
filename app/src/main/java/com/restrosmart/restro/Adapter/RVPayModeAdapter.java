package com.restrosmart.restro.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.medialablk.easygifview.EasyGifView;
import com.restrosmart.restro.Model.PayModeModel;
import com.restrosmart.restro.R;
import com.restrosmart.restro.User.ActivityUserHome;
import com.restrosmart.restro.Utils.ScratchImageView;
import com.restrosmart.restro.Utils.Sessionmanager;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class RVPayModeAdapter extends RecyclerView.Adapter<RVPayModeAdapter.ItemViewHolder> {

    private Context mContext;
    private ArrayList<PayModeModel> arrayList;
    private Sessionmanager sessionmanager;

    public RVPayModeAdapter(Context context, ArrayList<PayModeModel> payModeModelArrayList) {
        this.mContext = context;
        this.arrayList = payModeModelArrayList;
        sessionmanager = new Sessionmanager(mContext);
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.payment_mode_item, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        holder.tvPayModeName.setText(arrayList.get(position).getPayModeName());

        Picasso.with(mContext)
                .load(arrayList.get(position).getPayModeImage())
                .memoryPolicy(MemoryPolicy.NO_CACHE)
                .networkPolicy(NetworkPolicy.NO_CACHE)
                .into(holder.ivPayModeImg);
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {

        private CircleImageView ivPayModeImg;
        private TextView tvPayModeName;
        private AlertDialog payConfirmDialog, scratchCardDialog, feedbackdialog;

        ItemViewHolder(View itemView) {
            super(itemView);

            ivPayModeImg = itemView.findViewById(R.id.ivPayModeImg);
            tvPayModeName = itemView.findViewById(R.id.tvPayModeName);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    PayModeModel payModeModel = arrayList.get(getAdapterPosition());

                    if (payModeModel.getPayModeName().equalsIgnoreCase("Cash") || payModeModel.getPayModeName().equalsIgnoreCase("Card")) {
                        confirmationDialog(payModeModel);
                    } else {
                        detailsDialog(payModeModel);
                    }
                }
            });
        }

        private void detailsDialog(PayModeModel payModeModel) {
            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(mContext);
            LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View dialogView = null;
            if (layoutInflater != null) {
                dialogView = layoutInflater.inflate(R.layout.payment_mode_details_dialog, null);
            }
            dialogBuilder.setView(dialogView);

            payConfirmDialog = dialogBuilder.create();
            payConfirmDialog.setCanceledOnTouchOutside(false);
            payConfirmDialog.setCancelable(false);
            payConfirmDialog.show();

            TextView payModeTitle = dialogView.findViewById(R.id.payModeTitle);
            ImageView payModeImg = dialogView.findViewById(R.id.payModeImg);
            TableRow trBankName = dialogView.findViewById(R.id.trBankName);
            TableRow trHolderName = dialogView.findViewById(R.id.trHolderName);
            TableRow trAccountNo = dialogView.findViewById(R.id.trAccountNo);
            TableRow trIFSCCode = dialogView.findViewById(R.id.trIFSCCode);
            TableRow trBranchName = dialogView.findViewById(R.id.trBranchName);
            TableRow trMobileNo = dialogView.findViewById(R.id.trMobileNo);
            TableRow trUPICode = dialogView.findViewById(R.id.trUPICode);
            TableRow trLink = dialogView.findViewById(R.id.trLink);

            TextView tvBankName = dialogView.findViewById(R.id.tvBankName);
            TextView tvHolderName = dialogView.findViewById(R.id.tvHolderName);
            TextView tvAccountNo = dialogView.findViewById(R.id.tvAccountNo);
            TextView tvIFSCCode = dialogView.findViewById(R.id.tvIFSCCode);
            TextView tvBranchName = dialogView.findViewById(R.id.tvBranchName);
            TextView tvMobileNo = dialogView.findViewById(R.id.tvMobileNo);
            TextView tvUPICode = dialogView.findViewById(R.id.tvUPICode);
            TextView tvLink = dialogView.findViewById(R.id.tvLink);

            TextView tvOR = dialogView.findViewById(R.id.tvOR);
            ImageView ivQRCode = dialogView.findViewById(R.id.ivQRCode);

            Button btnPay = dialogView.findViewById(R.id.btnPay);
            Button btnCancelMode = dialogView.findViewById(R.id.btnCancelMode);

            payModeTitle.setText(payModeModel.getPayModeName());
            Picasso.with(mContext)
                    .load(payModeModel.getPayModeImage())
                    .into(payModeImg);

            if (!payModeModel.getPayBankName().equalsIgnoreCase("")) {
                tvBankName.setText(payModeModel.getPayBankName());
                trBankName.setVisibility(View.VISIBLE);
            }
            if (!payModeModel.getPayAccountNo().equalsIgnoreCase("0")) {
                tvAccountNo.setText(payModeModel.getPayAccountNo());
                trAccountNo.setVisibility(View.VISIBLE);
            }
            if (!payModeModel.getPayACHolderName().equalsIgnoreCase("")) {
                tvHolderName.setText(payModeModel.getPayACHolderName());
                trHolderName.setVisibility(View.VISIBLE);
            }
            if (!payModeModel.getPayBankName().equalsIgnoreCase("")) {
                tvBankName.setText(payModeModel.getPayBankName());
                trBankName.setVisibility(View.VISIBLE);
            }
            if (!payModeModel.getPayIFSCCode().equalsIgnoreCase("")) {
                tvIFSCCode.setText(payModeModel.getPayIFSCCode());
                trIFSCCode.setVisibility(View.VISIBLE);
            }
            if (!payModeModel.getPayBranchName().equalsIgnoreCase("")) {
                tvBranchName.setText(payModeModel.getPayBranchName());
                trBranchName.setVisibility(View.VISIBLE);
            }
            if (!payModeModel.getPayContact().equalsIgnoreCase("0")) {
                tvMobileNo.setText(payModeModel.getPayContact());
                trMobileNo.setVisibility(View.VISIBLE);
            }
            if (!payModeModel.getPayUpiCode().equalsIgnoreCase("")) {
                tvUPICode.setText(payModeModel.getPayUpiCode());
                trUPICode.setVisibility(View.VISIBLE);
            }
            if (!payModeModel.getPayLink().equalsIgnoreCase("")) {
                tvLink.setText(payModeModel.getPayLink());
                trLink.setVisibility(View.VISIBLE);
            }
            if (!payModeModel.getPayQRimage().equalsIgnoreCase("")) {

                Picasso.with(mContext)
                        .load(payModeModel.getPayQRimage())
                        .into(ivQRCode);
                tvOR.setVisibility(View.VISIBLE);
                ivQRCode.setVisibility(View.VISIBLE);
            }

            btnPay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    payConfirmDialog.dismiss();
                    sessionmanager.deleteFoodCartList();
                    openScratchCardDialog();
                }
            });

            btnCancelMode.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    payConfirmDialog.dismiss();
                }
            });
        }

        private void confirmationDialog(PayModeModel payModeModel) {
            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(mContext);
            LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View dialogView = null;
            if (layoutInflater != null) {
                dialogView = layoutInflater.inflate(R.layout.payment_mode_dialog, null);
            }
            dialogBuilder.setView(dialogView);

            payConfirmDialog = dialogBuilder.create();
            payConfirmDialog.setCanceledOnTouchOutside(false);
            payConfirmDialog.setCancelable(false);
            payConfirmDialog.show();

            TextView payModeTitle = dialogView.findViewById(R.id.payModeTitle);
            ImageView payModeImg = dialogView.findViewById(R.id.payModeImg);
            TextView tvPayLabel = dialogView.findViewById(R.id.tvPayLabel);
            Button btnPay = dialogView.findViewById(R.id.btnPay);
            Button btnCancelMode = dialogView.findViewById(R.id.btnCancelMode);

            payModeTitle.setText(payModeModel.getPayModeName());
            tvPayLabel.setText("Are you sure want to pay by " + payModeModel.getPayModeName() + " ?");

            Picasso.with(mContext)
                    .load(payModeModel.getPayModeImage())
                    .into(payModeImg);

            btnPay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    payConfirmDialog.dismiss();
                    sessionmanager.deleteFoodCartList();
                    openScratchCardDialog();
                }
            });

            btnCancelMode.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    payConfirmDialog.dismiss();
                }
            });
        }

        private void openScratchCardDialog() {
            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(mContext);
            LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View dialogView = null;
            if (layoutInflater != null) {
                dialogView = layoutInflater.inflate(R.layout.scratch_card_dialog, null);
            }
            dialogBuilder.setView(dialogView);

            scratchCardDialog = dialogBuilder.create();
            scratchCardDialog.setCanceledOnTouchOutside(false);
            scratchCardDialog.setCancelable(false);
            scratchCardDialog.show();

            ScratchImageView ivScratch = dialogView.findViewById(R.id.sratchImage);
            final TextView tvScratchName = dialogView.findViewById(R.id.tvScratchName);
            final Button btnScratchOk = dialogView.findViewById(R.id.btnScratchOk);

            Picasso.with(mContext).load("https://data.whicdn.com/images/8411081/original.jpg").into(ivScratch);

            ivScratch.setRevealListener(new ScratchImageView.IRevealListener() {
                @Override
                public void onRevealed(ScratchImageView siv) {
                    // on reveal
                    Toast.makeText(mContext, "Revealed", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onRevealPercentChangedListener(ScratchImageView siv, float percent) {
                    // on image percent reveal
                    if (percent > 0.5) {
                        siv.reveal();
                        tvScratchName.setVisibility(View.VISIBLE);
                        btnScratchOk.setVisibility(View.VISIBLE);
                        Toast.makeText(mContext, "winner", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            btnScratchOk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    scratchCardDialog.dismiss();
                    // Open feedback
                    //openFeedBackDialog();
                    openFeedbackBottomSheet();
                }
            });
        }

        private void openFeedbackBottomSheet() {
            View view1 = ((Activity) mContext).getLayoutInflater().inflate(R.layout.feedback_dialog, null);
            final BottomSheetDialog feedbackdialog = new BottomSheetDialog(mContext);
            feedbackdialog.setContentView(view1);
            feedbackdialog.setCancelable(false);
            feedbackdialog.setCanceledOnTouchOutside(false);

            EasyGifView eGVVeryBad = feedbackdialog.findViewById(R.id.eGVVeryBad);
            EasyGifView eGVBad = feedbackdialog.findViewById(R.id.eGVBad);
            EasyGifView eGVNormal = feedbackdialog.findViewById(R.id.eGVNormal);
            EasyGifView eGVGood = feedbackdialog.findViewById(R.id.eGVGood);
            EasyGifView eGVVeryGood = feedbackdialog.findViewById(R.id.eGVVeryGood);
            Button btnFeedbackSkip = feedbackdialog.findViewById(R.id.btnFeedbackSkip);
            Button btnFeedbackSend = feedbackdialog.findViewById(R.id.btnFeedbackSend);

            eGVVeryBad.setGifFromResource(R.drawable.ic_smiley_very_bad);
            eGVBad.setGifFromResource(R.drawable.ic_smiley_bad);
            eGVNormal.setGifFromResource(R.drawable.ic_smiley_normal);
            eGVGood.setGifFromResource(R.drawable.ic_smiley_good);
            eGVVeryGood.setGifFromResource(R.drawable.ic_smiley_very_good);

            feedbackdialog.show();

            btnFeedbackSend.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    feedbackdialog.dismiss();

                    ((Activity) mContext).finishAffinity();
                    Intent intent = new Intent(mContext, ActivityUserHome.class);

                    // Closing all the Activities
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                    // Add new Flag to start new Activity
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                    // Staring Login Activity
                    mContext.startActivity(intent);
                }
            });

            btnFeedbackSkip.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    feedbackdialog.dismiss();

                    ((Activity) mContext).finishAffinity();
                    Intent intent = new Intent(mContext, ActivityUserHome.class);

                    // Closing all the Activities
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                    // Add new Flag to start new Activity
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                    // Staring Login Activity
                    mContext.startActivity(intent);
                }
            });
        }

        /*private void openFeedBackDialog() {
            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(mContext);
            LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View dialogView = null;
            if (layoutInflater != null) {
                dialogView = layoutInflater.inflate(R.layout.feedback_dialog, null);
            }
            dialogBuilder.setView(dialogView);

            feedbackdialog = dialogBuilder.create();
            feedbackdialog.setCanceledOnTouchOutside(false);
            feedbackdialog.setCancelable(false);
            feedbackdialog.show();

            EasyGifView eGVVeryBad = dialogView.findViewById(R.id.eGVVeryBad);
            EasyGifView eGVBad = dialogView.findViewById(R.id.eGVBad);
            EasyGifView eGVNormal = dialogView.findViewById(R.id.eGVNormal);
            EasyGifView eGVGood = dialogView.findViewById(R.id.eGVGood);
            EasyGifView eGVVeryGood = dialogView.findViewById(R.id.eGVVeryGood);
            Button btnFeedbackSkip = dialogView.findViewById(R.id.btnFeedbackSkip);
            Button btnFeedbackSend = dialogView.findViewById(R.id.btnFeedbackSend);

            eGVVeryBad.setGifFromResource(R.drawable.ic_smiley_very_bad);
            eGVBad.setGifFromResource(R.drawable.ic_smiley_bad);
            eGVNormal.setGifFromResource(R.drawable.ic_smiley_normal);
            eGVGood.setGifFromResource(R.drawable.ic_smiley_good);
            eGVVeryGood.setGifFromResource(R.drawable.ic_smiley_very_good);

            btnFeedbackSend.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    feedbackdialog.dismiss();

                    ((Activity) mContext).finishAffinity();
                    Intent intent = new Intent(mContext, ActivityUserHome.class);

                    // Closing all the Activities
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                    // Add new Flag to start new Activity
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                    // Staring Login Activity
                    mContext.startActivity(intent);
                }
            });

            btnFeedbackSkip.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    feedbackdialog.dismiss();

                    ((Activity) mContext).finishAffinity();
                    Intent intent = new Intent(mContext, ActivityUserHome.class);

                    // Closing all the Activities
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                    // Add new Flag to start new Activity
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                    // Staring Login Activity
                    mContext.startActivity(intent);
                }
            });
        }*/
    }
}
