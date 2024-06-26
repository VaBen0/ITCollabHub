package ru.dvteam.itcollabhub.view.authorizeviews;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;

import javax.inject.Inject;

import ru.dvteam.itcollabhub.callbackclasses.CallBackBoolean;
import ru.dvteam.itcollabhub.databinding.ActivityLogInBinding;
import ru.dvteam.itcollabhub.di.component.AppComponent;
import ru.dvteam.itcollabhub.di.component.DaggerAppComponent;
import ru.dvteam.itcollabhub.di.modules.SharedPreferencesModule;
import ru.dvteam.itcollabhub.view.profileviews.activities.Profile;
import ru.dvteam.itcollabhub.viewmodel.authorizeviewmodels.LoginViewModel;

public class LogIn extends AppCompatActivity {

    ActivityLogInBinding binding;

    LoginViewModel viewModel;
    private AppComponent sharedPreferenceComponent;
    @Inject
    SharedPreferences sharedPreferences;

    private Boolean correctEmail = false;
    private Boolean emptyPass = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);
        super.onCreate(savedInstanceState);

        binding = ActivityLogInBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        sharedPreferenceComponent = DaggerAppComponent.builder().sharedPreferencesModule(
                new SharedPreferencesModule(this)).build();

        sharedPreferenceComponent.inject(this);

        viewModel = new ViewModelProvider(this).get(LoginViewModel.class);

        Typeface face=Typeface.createFromAsset(getAssets(),"font/ArchitectsDaughter-Regular.ttf");
        binding.it.setTypeface(face);
        binding.hub.setTypeface(face);
        binding.collaborotory.setTypeface(face);

        initEditTexts();

        viewModel.getEmailValid().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                correctEmail = aBoolean;
            }
        });

        viewModel.getPassNormLength().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                emptyPass = aBoolean;
            }
        });

        binding.enterBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!correctEmail){
                    Toast.makeText(LogIn.this, "Вы ввели не почту", Toast.LENGTH_SHORT).show();
                }else if(!emptyPass){
                    Toast.makeText(LogIn.this, "Длина пароля слишком маленькая", Toast.LENGTH_SHORT).show();
                }else {
                    viewModel.onLoginClick(sharedPreferences, new CallBackBoolean() {
                        @Override
                        public void invoke(Boolean res) {
                            if (res) {
                                Toast.makeText(LogIn.this, "Успешно", Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(LogIn.this, Profile.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            } else {
                                Toast.makeText(LogIn.this, "Ошибка", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

        binding.regBut.setOnClickListener(v -> {
            Intent intent = new Intent(LogIn.this, Register.class);
            startActivity(intent);
        });

        binding.forgotBut.setOnClickListener(v -> {
            Intent intent = new Intent(LogIn.this, Forgot.class);
            startActivity(intent);
        });
    }

    public void initEditTexts(){
        binding.mailu.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                viewModel.setEmail(binding.mailu.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        binding.passu.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                viewModel.setPass(binding.passu.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }
}