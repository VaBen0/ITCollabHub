package ru.dvteam.itcollabhub;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import ru.dvteam.itcollabhub.adapter.ProblemAdapter;
import ru.dvteam.itcollabhub.adapter.ProblemsParticipAdapter;
import ru.dvteam.itcollabhub.callbackclasses.CallBackDelOrChangeAd;
import ru.dvteam.itcollabhub.callbackclasses.CallBackInt;
import ru.dvteam.itcollabhub.databinding.ActivityProblemsBinding;
import ru.dvteam.itcollabhub.databinding.ActivityProblemsParticipBinding;
import ru.dvteam.itcollabhub.retrofit.PostDatas;
import ru.dvteam.itcollabhub.view.projectmenusviews.activities.EditProblem;
import ru.dvteam.itcollabhub.view.projectmenusviews.activities.Problems;
import ru.dvteam.itcollabhub.viewmodel.projectmenusviewmodels.ProblemViewModel;

public class ProblemsParticip extends AppCompatActivity {

    ActivityProblemsParticipBinding binding;
    ProblemViewModel problemViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setThemeActivity();
        super.onCreate(savedInstanceState);

        binding = ActivityProblemsParticipBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());
        problemViewModel = new ViewModelProvider(this).get(ProblemViewModel.class);

        TypedValue typedValue = new TypedValue();
        getTheme().resolveAttribute(R.attr.statusBarColor, typedValue, true);
        int color = ContextCompat.getColor(ProblemsParticip.this, typedValue.resourceId);
        getWindow().setStatusBarColor(color);

        binding.nameProject.setText(problemViewModel.getProjectTitle());
        Glide
                .with(ProblemsParticip.this)
                .load(problemViewModel.getProjectLog())
                .into(binding.prLogo);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        binding.problemsPlace.setLayoutManager(layoutManager);

        problemViewModel.getProblemsArray().observe(this, purposeInformations -> {
            ProblemsParticipAdapter adapter = new ProblemsParticipAdapter(purposeInformations, this);
            binding.problemsPlace.setAdapter(adapter);
        });

        problemViewModel.setProblems();
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