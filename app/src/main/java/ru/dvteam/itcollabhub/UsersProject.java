package ru.dvteam.itcollabhub;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.DecelerateInterpolator;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import ru.dvteam.itcollabhub.callbackclasses.CallBackInt4;
import ru.dvteam.itcollabhub.databinding.ActivityUsersProjectBinding;
import ru.dvteam.itcollabhub.retrofit.PostDatas;

public class UsersProject extends AppCompatActivity {

    ActivityUsersProjectBinding binding;
    String mail, id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setThemeActivity();

        super.onCreate(savedInstanceState);

        SharedPreferences sPref = getSharedPreferences("MyPref", MODE_PRIVATE);
        mail = sPref.getString("UserMail", "");

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        binding = ActivityUsersProjectBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        /*if(score < 100){
            Drawable progressDrawable = getResources().getDrawable(R.drawable.custom_progress_bar_bgreen);
            binding.projectProgress.setBackgroundResource(R.drawable.custom_progress_bar_bgreen);
            binding.projectProgress.setProgressDrawable(progressDrawable);
            binding.controlPanelMove.setBackgroundTintList(ContextCompat.getColorStateList(UsersProject.this, R.color.blue));
        }
        else if(score < 300){
            Drawable progressDrawable = getResources().getDrawable(R.drawable.custom_progress_bar_gbrown);
            binding.projectProgress.setBackgroundResource(R.drawable.custom_progress_bar_gbrown);
            binding.projectProgress.setProgressDrawable(progressDrawable);
            binding.controlPanelMove.setBackgroundTintList(ContextCompat.getColorStateList(UsersProject.this, R.color.green));
        }
        else if(score < 1000){
            Drawable progressDrawable = getResources().getDrawable(R.drawable.custom_progress_bar_brlg);
            binding.projectProgress.setBackgroundResource(R.drawable.custom_progress_bar_brlg);
            binding.projectProgress.setProgressDrawable(progressDrawable);
            binding.controlPanelMove.setBackgroundTintList(ContextCompat.getColorStateList(UsersProject.this, R.color.brown));
        }
        else if(score < 2500){
            Drawable progressDrawable = getResources().getDrawable(R.drawable.custom_progress_bar_lgoh);
            binding.projectProgress.setBackgroundResource(R.drawable.custom_progress_bar_lgoh);
            binding.projectProgress.setProgressDrawable(progressDrawable);
            binding.controlPanelMove.setBackgroundTintList(ContextCompat.getColorStateList(UsersProject.this, R.color.light_gray));
        }
        else if(score < 7000){
            Drawable progressDrawable = getResources().getDrawable(R.drawable.custom_progress_bar_ohred);
            binding.projectProgress.setBackgroundResource(R.drawable.custom_progress_bar_ohred);
            binding.projectProgress.setProgressDrawable(progressDrawable);
            binding.controlPanelMove.setBackgroundTintList(ContextCompat.getColorStateList(UsersProject.this, R.color.ohra));
        }
        else if(score < 17000) {
            Drawable progressDrawable = getResources().getDrawable(R.drawable.custom_progress_bar_redora);
            binding.projectProgress.setBackgroundResource(R.drawable.custom_progress_bar_redora);
            binding.projectProgress.setProgressDrawable(progressDrawable);
            binding.controlPanelMove.setBackgroundTintList(ContextCompat.getColorStateList(UsersProject.this, R.color.red));
        }
        else if(score < 30000){
            Drawable progressDrawable = getResources().getDrawable(R.drawable.custom_progress_bar_vo);
            binding.projectProgress.setBackgroundResource(R.drawable.custom_progress_bar_vo);
            binding.projectProgress.setProgressDrawable(progressDrawable);
            binding.controlPanelMove.setBackgroundTintList(ContextCompat.getColorStateList(UsersProject.this, R.color.orange));
        }
        else if(score < 50000){
            Drawable progressDrawable = getResources().getDrawable(R.drawable.custom_progress_bar_violetbluegreen);
            binding.projectProgress.setBackgroundResource(R.drawable.custom_progress_bar_violetbluegreen);
            binding.projectProgress.setProgressDrawable(progressDrawable);
            binding.controlPanelMove.setBackgroundTintList(ContextCompat.getColorStateList(UsersProject.this, R.color.violete));
        }
        else{
            Drawable progressDrawable = getResources().getDrawable(R.drawable.custom_progress_bar_violetbluegreen);
            binding.projectProgress.setBackgroundResource(R.drawable.custom_progress_bar_violetbluegreen);
            binding.projectProgress.setProgressDrawable(progressDrawable);
            binding.controlPanelMove.setBackgroundTintList(ContextCompat.getColorStateList(UsersProject.this, R.color.main_green));
        }*/

        binding.projectProgress.setMax(100);

        Bundle arguments = getIntent().getExtras();

        assert arguments != null;
        id = arguments.getString("projectId");
        getMainInfo();
    }

    private void getMainInfo(){
        PostDatas postDatas = new PostDatas();
        postDatas.postDataGetProjectInformation("GetProjectMainInformation", id, mail, new CallBackInt4() {
            @Override
            public void invoke(String name, String photoUrl, String descript, double isend, String purposes,
                               String problems, String peoples, String time, String time1, String tg, String vk, String webs,
                               String purposesids, String problemsids, String isl, String tasks, String isOpen, String isVisible) {
                binding.projectName.setText(name);
                Glide
                        .with(UsersProject.this)
                        .load(photoUrl)
                        .into(binding.prLogo);
                String percents = (int) isend + ".0%";
                binding.projectPercents.setText(percents);
                binding.tgIcon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(UsersProject.this, tg, Toast.LENGTH_SHORT).show();
                    }
                });
                binding.vkIcon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(UsersProject.this, vk, Toast.LENGTH_SHORT).show();

                    }
                });
                binding.webIcon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(UsersProject.this, webs, Toast.LENGTH_SHORT).show();
                    }
                });

                ObjectAnimator animation = ObjectAnimator.ofInt(binding.projectProgress, "progress", 0, (int) isend);
                animation.setStartDelay(300);
                animation.setDuration(1000);
                animation.setAutoCancel(true);
                animation.setInterpolator(new DecelerateInterpolator());
                animation.start();

                final ValueAnimator anim = ValueAnimator.ofFloat(0, (int) isend);
                anim.setStartDelay(300);
                anim.setDuration(1000);
                anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(@NonNull ValueAnimator animation) {
                        String res = anim.getAnimatedValue().toString();
                        if(res.length() >= 4){
                            res = res.substring(0, 4) + "%";
                        }
                        else if(res.length() == 3){
                            res = res.substring(0, 3) + "%";
                        }
                        else{
                            res = res.substring(0, 2) + "0%";
                        }

                        binding.projectPercents.setText(res);
                    }
                });

                anim.setInterpolator(new DecelerateInterpolator());
                anim.start();
                if(isl.equals("1")){
                    binding.controlPanelMove.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(UsersProject.this, ControlPanel.class);
                            intent.putExtra("projectId", id);
                            startActivity(intent);
                        }
                    });
                }

                else{
                    binding.controlPanelMove.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(UsersProject.this, ControlPanel2.class);
                            intent.putExtra("projectId", id);
                            startActivity(intent);
                        }
                    });
                }

                binding.description.setText(descript);
                String purpose = "Выполненных целей: " + purposes;
                String problem = "Выполненных задач: " + problems;
                String peopleCount = "Количество участников: " + peoples;
                binding.completePurposes.setText(purpose);
                binding.completeProblems.setText(problem);
                binding.numOfPeoples.setText(peopleCount);
                binding.date.setText(time);
                binding.time.setText(time1);
                binding.tgIcon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(UsersProject.this, tg, Toast.LENGTH_SHORT).show();
                    }
                });
                binding.vkIcon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(UsersProject.this, vk, Toast.LENGTH_SHORT).show();
                    }
                });
                binding.webIcon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(UsersProject.this, webs, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    @Override
    protected void onRestart() {
        getMainInfo();
        super.onRestart();
    }

    public void setThemeActivity(){
        int themeType = UsersChosenTheme.getThemeNum();

        switch (themeType) {
            case (1):
                setTheme(R.style.Theme_ITCollabHub_Blue);
                break;
            case (2):
                setTheme(R.style.Theme_ITCollabHub_Green);
                break;
            case (3):
                setTheme(R.style.Theme_ITCollabHub_Brown);
                break;
            case (4):
                setTheme(R.style.Theme_ITCollabHub_PinkGold);
                break;
            case (5):
                setTheme(R.style.Theme_ITCollabHub_Ohra);
                break;
            case (6):
                setTheme(R.style.Theme_ITCollabHub_Red);
                break;
            case (7):
                setTheme(R.style.Theme_ITCollabHub_Orange);
                break;
            case (8):
                setTheme(R.style.Theme_ITCollabHub_Violete);
                break;
            case (9):
                setTheme(R.style.Theme_ITCollabHub_BlueGreen);
                break;
        }

    }
}