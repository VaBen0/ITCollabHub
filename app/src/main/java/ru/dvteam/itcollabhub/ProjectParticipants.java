package ru.dvteam.itcollabhub;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import ru.dvteam.itcollabhub.callbackclasses.CallBackInt5;
import ru.dvteam.itcollabhub.databinding.ActivityProjectParticipantsBinding;
import ru.dvteam.itcollabhub.retrofit.PostDatas;

public class ProjectParticipants extends AppCompatActivity {

    ActivityProjectParticipantsBinding binding;
    String id, title, description, prPhoto, mail;
    ArrayList<Integer> indexViews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setThemeActivity();
        super.onCreate(savedInstanceState);

        binding = ActivityProjectParticipantsBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        SharedPreferences sPref = getSharedPreferences("MyPref", MODE_PRIVATE);
        mail = sPref.getString("UserMail", "");
        int score = sPref.getInt("UserScore", 0);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        Bundle arguments = getIntent().getExtras();

        assert arguments != null;
        id = arguments.getString("projectId");
        title = arguments.getString("projectTitle");
        prPhoto = arguments.getString("projectUrlPhoto");
        description = arguments.getString("projectDescription");

        binding.projectName.setText(title);
        Glide
                .with(ProjectParticipants.this)
                .load(prPhoto)
                .into(binding.prLogo);

        binding.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProjectParticipants.this, AddParticipant.class);
                startActivity(intent);
            }
        });
        binding.notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProjectParticipants.this, NotifyInParticipants.class);
                intent.putExtra("projectTitle", title);
                intent.putExtra("projectUrlPhoto", prPhoto);
                intent.putExtra("projectId", id);
                startActivity(intent);
            }
        });
        binding.find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                indexViews = new ArrayList<>();
                if(binding.nameFriend.getText().toString().isEmpty()){
                    Toast.makeText(ProjectParticipants.this, "Вы ничего не ввели", Toast.LENGTH_SHORT).show();
                }
                else{
                    for(int i = 0; i < binding.linMain.getChildCount() - 1; i++){
                        View custom = binding.linMain.getChildAt(i);
                        TextView nameu = (TextView) custom.findViewById(R.id.textView3);
                        if(nameu.getText().toString().contains(binding.nameFriend.getText().toString())){
                            indexViews.add(i);
                        }
                    }
                    if(indexViews.isEmpty()){
                        Toast.makeText(ProjectParticipants.this, "Таких участников не существует", Toast.LENGTH_SHORT).show();
                    }else{
                        binding.cancel.setVisibility(View.VISIBLE);
                        for(int i = 0; i < binding.linMain.getChildCount() - 1; i++){
                            if(!indexViews.contains(i)) {
                                binding.linMain.getChildAt(i).setVisibility(View.GONE);
                            }
                            else{
                                binding.linMain.getChildAt(i).setVisibility(View.VISIBLE);
                            }
                        }
                    }
                }

            }
        });
        binding.cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(int i = 0; i < binding.linMain.getChildCount(); i++){
                    binding.linMain.getChildAt(i).setVisibility(View.VISIBLE);
                }
                binding.cancel.setVisibility(View.GONE);
                binding.nameFriend.setText("");
            }
        });

        PostDatas post = new PostDatas();
        post.postDataGetProjectParticipant("GetPeoplesFromProjects", id, mail, new CallBackInt5() {
            @Override
            public void invoke(String ids, String names, String photos) {
                binding.linMain.removeAllViews();
                String[] idsArr = ids.split("\uD83D\uDD70");
                String[] namesArr = names.split("\uD83D\uDD70");
                String[] photosArr = photos.split("\uD83D\uDD70");

                for (int i = 0; i < idsArr.length; i++) {
                    View custom = getLayoutInflater().inflate(R.layout.friend_window, null);
                    TextView nameu = (TextView) custom.findViewById(R.id.textView3);
                    ImageView loadImage = (ImageView) custom.findViewById(R.id.log);
                    ImageView userCircle = (ImageView) custom.findViewById(R.id.user_circle);
                    TextView project1 = (TextView) custom.findViewById(R.id.projects1);
                    project1.setVisibility(View.GONE);
                    ImageView messege = (ImageView) custom.findViewById(R.id.notban);
                    messege.setImageResource(R.drawable.delete_black);
                    userCircle.setVisibility(View.GONE);
                    System.out.println(photosArr[i]);

                    Glide
                            .with(ProjectParticipants.this)
                            .load(photosArr[i])
                            .into(loadImage);
                    nameu.setText(namesArr[i]);

                    messege.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                        }
                    });

                    binding.linMain.addView(custom);
                }
                View empty = getLayoutInflater().inflate(R.layout.emty_obj, null);
                binding.linMain.addView(empty);
            }
        });


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