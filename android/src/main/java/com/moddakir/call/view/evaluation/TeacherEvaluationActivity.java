package com.moddakir.call.view.evaluation;


import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.material.card.MaterialCardView;
import com.moddakir.call.Model.User;
import com.moddakir.call.base.BaseMVVMActivity;
import com.moddakir.call.view.widget.ButtonCalibriBold;
import com.moddakir.call.view.widget.PlayGifView;
import com.moddakir.call.viewModel.TeacherEvaluationViewModel;
import com.moddakir.call.Model.CheckBoxModel;
import com.moddakir.call.utils.Utils;
import com.moddakir.call.view.widget.TextViewUniqueLight;
import com.moddakir.call.R;
import com.moddakir.call.utils.Perference;

import java.util.ArrayList;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class TeacherEvaluationActivity extends BaseMVVMActivity<TeacherEvaluationViewModel> {

    private final ArrayList<CheckBoxModel> rateLikesList = new ArrayList<>();
    private final ArrayList<String> selectedLikesList = new ArrayList<>();
    private CircleImageView civTeacherImage;
    private TextViewUniqueLight tvTeacherName,connectionEvaluationTv,teacherEvaluate;
    ImageView smile1_evaluate_connection, smile2_evaluate_connection, smile3_evaluate_connection, smile4_evaluate_connection, smile5_evaluate_connection;
    MaterialCardView cardEvaluateConnection, cardTeacherRating;
    ImageView smile1_evaluate_teacher, smile2_evaluate_teacher, smile3_evaluate_teacher,
            smile4_evaluate_teacher, smile5_evaluate_teacher;
    private ButtonCalibriBold btnSave, btnCancel;
    private String teacher_id;
    private String call_id;
    private AlertDialog progress;
    private Boolean userIsChild;
    ConstraintLayout teacherLayout;
    String teacher_name;
    String teacher_avatar;
    int evaluateTeacherNum = 0,primaryColor;
    int evaluateConnectionNum = 0;


    public static void start(Context context, String teacher_id, String teacher_name, String
            call_id, String Avatar, User user, int primaryColor) {

        Intent starter = new Intent(context, TeacherEvaluationActivity.class);
        starter.putExtra("TEACHER_ID", teacher_id);
        starter.putExtra("TEACHER_NAME", teacher_name);
        starter.putExtra("TEACHER_IMAGE", Avatar);
        starter.putExtra("CALL_ID", call_id);
        starter.putExtra("primaryColor", primaryColor);
        starter.putExtra("isChild", user.isChildDependent());
        starter.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(starter);
    }

    @Override
    protected void initOnCreate(Bundle savedInstanceState) {
        progress = Perference.ShowProgress(this);
    }

    @Override
    protected Class<TeacherEvaluationViewModel> getViewModelClass() {
        return TeacherEvaluationViewModel.class;
    }

    @Override
    protected int getContentResourceID() {
        return R.layout.activity_teacher_evaluation;
    }

    @Override
    protected void initViews() {
        userIsChild = false;
        teacherLayout = findViewById(R.id.teacherLayout);
        civTeacherImage = findViewById(R.id.civ_teacher_image);
        tvTeacherName = findViewById(R.id.tv_teacher_name);
        connectionEvaluationTv = findViewById(R.id.connection_evaluation_tv);
        teacherEvaluate = findViewById(R.id.teacher_evaluate);
        smile1_evaluate_connection = findViewById(R.id.smile1_evaluate_connection);
        smile2_evaluate_connection = findViewById(R.id.smile2_evaluate_connection);
        smile3_evaluate_connection = findViewById(R.id.smile3_evaluate_connection);
        smile4_evaluate_connection = findViewById(R.id.smile4_evaluate_connection);
        smile5_evaluate_connection = findViewById(R.id.smile5_evaluate_connection);
        cardEvaluateConnection = findViewById(R.id.card_evaluate_connection);
        cardTeacherRating = findViewById(R.id.card_teacher_rating);
        smile1_evaluate_teacher = findViewById(R.id.smile1_evaluate_teacher);
        smile2_evaluate_teacher = findViewById(R.id.smile2_evaluate_teacher);
        smile3_evaluate_teacher = findViewById(R.id.smile3_evaluate_teacher);
        smile4_evaluate_teacher = findViewById(R.id.smile4_evaluate_teacher);
        smile5_evaluate_teacher = findViewById(R.id.smile5_evaluate_teacher);
        btnSave = findViewById(R.id.btn_save);
        btnCancel = findViewById(R.id.btn_cancel);
        primaryColor = getIntent().getIntExtra("primaryColor",getColor(R.color.colorPrimary));
        cardEvaluateConnection.setCardBackgroundColor(getColor(R.color.colorRateCard));
        cardTeacherRating.setCardBackgroundColor(getColor(R.color.colorRateCard));
        if (primaryColor!=0) {
            btnSave.setTextColor(primaryColor);
            btnSave.setTextColor(getColor(R.color.white));
            cardEvaluateConnection.setStrokeColor(primaryColor);
            cardTeacherRating.setStrokeColor(primaryColor);
            btnSave.setBackgroundTintList(ColorStateList.valueOf(primaryColor));
        }else {
            cardEvaluateConnection.setStrokeColor(getColor(R.color.colorPrimary));
            cardTeacherRating.setStrokeColor(getColor(R.color.colorPrimary));
        }
    }


    private void smile1EvaluateTeacherClicked() {
        smile1_evaluate_teacher.setOnClickListener(view -> {
            evaluateTeacherNum = 5;
            smile1EvaluateTeacherSelected();
            smile2EvaluateTeacherUnSelected();
            smile3EvaluateTeacherUnSelected();
            smile4EvaluateTeacherUnSelected();
            smile5EvaluateTeacherUnSelected();

        });
    }

    private void smile2EvaluateTeacherClicked() {
        smile2_evaluate_teacher.setOnClickListener(view -> {
            evaluateTeacherNum = 4;

            smile1EvaluateTeacherUnSelected();
            smile2EvaluateTeacherSelected();
            smile3EvaluateTeacherUnSelected();
            smile4EvaluateTeacherUnSelected();
            smile5EvaluateTeacherUnSelected();
        });
    }

    private void smile3EvaluateTeacherClicked() {
        smile3_evaluate_teacher.setOnClickListener(view -> {
            evaluateTeacherNum = 3;
            smile1EvaluateTeacherUnSelected();
            smile2EvaluateTeacherUnSelected();
            smile3EvaluateTeacherSelected();
            smile4EvaluateTeacherUnSelected();
            smile5EvaluateTeacherUnSelected();

        });
    }

    private void smile4EvaluateTeacherClicked() {
        smile4_evaluate_teacher.setOnClickListener(view -> {
            evaluateTeacherNum = 2;
            smile1EvaluateTeacherUnSelected();
            smile2EvaluateTeacherUnSelected();
            smile3EvaluateTeacherUnSelected();
            smile4EvaluateTeacherSelected();
            smile5EvaluateTeacherUnSelected();

        });
    }

    private void smile5EvaluateTeacherClicked() {
        smile5_evaluate_teacher.setOnClickListener(view -> {
            evaluateTeacherNum = 1;
            smile1EvaluateTeacherUnSelected();
            smile2EvaluateTeacherUnSelected();
            smile3EvaluateTeacherUnSelected();
            smile4EvaluateTeacherUnSelected();
            smile5EvaluateTeacherSelected();
        });
    }

    public void smile1EvaluateTeacherSelected() {
        smile1_evaluate_teacher.setImageResource(R.drawable.ic_smile1_selected);
    }

    public void smile1EvaluateTeacherUnSelected() {
        smile1_evaluate_teacher.setImageResource(R.drawable.ic_smile1_unselected);
    }

    public void smile2EvaluateTeacherSelected() {
        smile2_evaluate_teacher.setImageResource(R.drawable.smile2_selected);

    }

    public void smile2EvaluateTeacherUnSelected() {
        smile2_evaluate_teacher.setImageResource(R.drawable.ic_smile2_unselected);

    }

    public void smile3EvaluateTeacherSelected() {
        smile3_evaluate_teacher.setImageResource(R.drawable.smile3_selected);

    }

    public void smile3EvaluateTeacherUnSelected() {
        smile3_evaluate_teacher.setImageResource(R.drawable.ic_smile3_unselected);

    }

    public void smile4EvaluateTeacherSelected() {
        smile4_evaluate_teacher.setImageResource(R.drawable.ic_smile4_selected);

    }

    public void smile4EvaluateTeacherUnSelected() {
        smile4_evaluate_teacher.setImageResource(R.drawable.ic_smile4_unselected);

    }

    public void smile5EvaluateTeacherSelected() {
        smile5_evaluate_teacher.setImageResource(R.drawable.ic_smile5_selected);

    }

    public void smile5EvaluateTeacherUnSelected() {
        smile5_evaluate_teacher.setImageResource(R.drawable.ic_smile5_unselected);

    }

    //evaluate connection....
    private void smile1EvaluateConnectionClicked() {
        smile1_evaluate_connection.setOnClickListener(view -> {
            evaluateConnectionNum = 5;
            smile1EvaluateConnectionSelected();
            smile2EvaluateConnectionUnSelected();
            smile3EvaluateConnectionUnSelected();
            smile4EvaluateConnectionUnSelected();
            smile5EvaluateConnectionUnSelected();

        });
    }

    private void smile2EvaluateConnectionClicked() {
        smile2_evaluate_connection.setOnClickListener(view -> {
            evaluateConnectionNum = 4;
            smile1EvaluateConnectionUnSelected();
            smile2EvaluateConnectionSelected();
            smile3EvaluateConnectionUnSelected();
            smile4EvaluateConnectionUnSelected();
            smile5EvaluateConnectionUnSelected();

        });
    }

    private void smile3EvaluateConnectionClicked() {
        smile3_evaluate_connection.setOnClickListener(view -> {
            evaluateConnectionNum = 3;
            smile1EvaluateConnectionUnSelected();
            smile2EvaluateConnectionUnSelected();
            smile3EvaluateConnectionSelected();
            smile4EvaluateConnectionUnSelected();
            smile5EvaluateConnectionUnSelected();

        });
    }

    private void smile4EvaluateConnectionClicked() {
        smile4_evaluate_connection.setOnClickListener(view -> {
            evaluateConnectionNum = 2;
            smile1EvaluateConnectionUnSelected();
            smile2EvaluateConnectionUnSelected();
            smile3EvaluateConnectionUnSelected();
            smile4EvaluateConnectionSelected();
            smile5EvaluateConnectionUnSelected();

        });
    }

    private void smile5EvaluateConnectionClicked() {
        smile5_evaluate_connection.setOnClickListener(view -> {
            evaluateConnectionNum = 1;
            smile1EvaluateConnectionUnSelected();
            smile2EvaluateConnectionUnSelected();
            smile3EvaluateConnectionUnSelected();
            smile4EvaluateConnectionUnSelected();
            smile5EvaluateConnectionSelected();

        });
    }

    private void smile1EvaluateConnectionUnSelected() {
        smile1_evaluate_connection.setImageResource(R.drawable.ic_smile1_unselected);

    }

    private void smile2EvaluateConnectionUnSelected() {
        smile2_evaluate_connection.setImageResource(R.drawable.ic_smile2_unselected);

    }

    private void smile3EvaluateConnectionUnSelected() {
        smile3_evaluate_connection.setImageResource(R.drawable.ic_smile3_unselected);

    }

    private void smile4EvaluateConnectionUnSelected() {
        smile4_evaluate_connection.setImageResource(R.drawable.ic_smile4_unselected);
    }

    private void smile5EvaluateConnectionUnSelected() {
        smile5_evaluate_connection.setImageResource(R.drawable.ic_smile5_unselected);
    }

    private void smile1EvaluateConnectionSelected() {
        smile1_evaluate_connection.setImageResource(R.drawable.ic_smile1_selected);

    }

    private void smile2EvaluateConnectionSelected() {
        smile2_evaluate_connection.setImageResource(R.drawable.smile2_selected);

    }

    private void smile3EvaluateConnectionSelected() {
        smile3_evaluate_connection.setImageResource(R.drawable.smile3_selected);

    }

    private void smile4EvaluateConnectionSelected() {
        smile4_evaluate_connection.setImageResource(R.drawable.ic_smile4_selected);
    }

    private void smile5EvaluateConnectionSelected() {
        smile5_evaluate_connection.setImageResource(R.drawable.ic_smile5_selected);
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void setViewsData() {
        userIsChild = getIntent().getBooleanExtra("isChild", false);
        teacher_id = getIntent().getStringExtra("TEACHER_ID");
        teacher_name = getIntent().getStringExtra("TEACHER_NAME");
        teacher_avatar = getIntent().getStringExtra("TEACHER_IMAGE");
        call_id = getIntent().getStringExtra("CALL_ID");

        Utils.loadAvatar(this, teacher_avatar, civTeacherImage);
        tvTeacherName.setText(teacher_name);

        //evaluate teacher ...
        smile1EvaluateTeacherClicked();
        smile2EvaluateTeacherClicked();
        smile3EvaluateTeacherClicked();
        smile4EvaluateTeacherClicked();
        smile5EvaluateTeacherClicked();

        //evaluate connection ...
        smile1EvaluateConnectionClicked();
        smile2EvaluateConnectionClicked();
        smile3EvaluateConnectionClicked();
        smile4EvaluateConnectionClicked();
        smile5EvaluateConnectionClicked();

        btnCancel.setOnClickListener(view -> moveToNext());

        btnSave.setOnClickListener(view -> {
            if (evaluateTeacherNum == 0) {
                Toast.makeText(TeacherEvaluationActivity.this, getResources().getString(R.string.teacher_evaluation_required), Toast.LENGTH_LONG).show();
                return;
            }
            if (evaluateConnectionNum == 0) {
                Toast.makeText(TeacherEvaluationActivity.this, getResources().getString(R.string.connection_evaluation_required), Toast.LENGTH_LONG).show();
                return;
            }
            fillLikesSelectedList();
            HashMap<String, Object> map = new HashMap<>();
            map.put("teacherId", teacher_id);
            map.put("rate", String.valueOf(Math.round(evaluateTeacherNum)));
            map.put("connectionQualityRate", String.valueOf(Math.round(evaluateConnectionNum)));
            map.put("comment", "");
            map.put("reasons", selectedLikesList);
            map.put("callId", call_id);
            viewModel.rateRequest(map,TeacherEvaluationActivity.this);
        });


    }


    @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
    @Override
    protected void initViewModelListeners() {
        viewModel.getRateObserver().observe(this, response -> {
            switch (response.whichState()) {
                case LOADING:
                    if (response.isLoading())
                        progress.show();
                    else progress.hide();
                    break;
                case SUCCESS:
                    if (response.fetchData().getStatusCode() == 200) {
                        final Dialog dialog = new Dialog(this);
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.setCancelable(false);
                        dialog.setContentView(R.layout.evaluation_done);
                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                        ImageView close = dialog.findViewById(R.id.close_iv);
                        PlayGifView pGif = dialog.findViewById(R.id.done_iv);
                        pGif.setImageResource(R.drawable.success);
                        close.setOnClickListener(view -> {
                            dialog.dismiss();
                            moveToNext();
                        });
                        dialog.show();

                    } else {
                        Toast.makeText(TeacherEvaluationActivity.this, response.fetchData().getMessage(), Toast.LENGTH_LONG).show();
                    }

                    break;
                case ERROR:
                    Toast.makeText(this, getResources().getString(response.fetchError().getMessageResourceId()), Toast.LENGTH_LONG).show();

            }
        });


    }


    private void moveToNext() {
        finish();
    }




    private void fillLikesSelectedList() {
        selectedLikesList.clear();
        for (CheckBoxModel likeModel : rateLikesList) {
            if (likeModel.isSelected()) {
                selectedLikesList.add(likeModel.getId());
            }
        }
    }

    @Override
    public void onBackPressed() {
        Toast.makeText(this, getResources().getString(R.string.rate_teacher_first), Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        if (progress != null) {
            progress.dismiss();
            progress = null;
        }
        super.onDestroy();
    }
}
