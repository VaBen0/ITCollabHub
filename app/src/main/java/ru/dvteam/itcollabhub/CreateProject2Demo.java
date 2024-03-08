package ru.dvteam.itcollabhub;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import ru.dvteam.itcollabhub.databinding.ActivityCreateProject2Binding;
import ru.dvteam.itcollabhub.databinding.ActivityCreateProject2DemoBinding;

public class CreateProject2Demo extends AppCompatActivity {

    ActivityCreateProject2DemoBinding binding;
    String mail;

    private ImageView Img;
    private EditText description;
    private static final int PICK_IMAGES_CODE = 0;
    private String purposes_name = "", purposes = "", tasks_name = "", tasks = "";
    private String id1 = "";
    private String mediaPath = "", uriPath = "";
    private Boolean acces = false;
    private static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 1;
    ActivityResultLauncher<Intent> resultLauncher;
    int score;
    String title, description1;
    Fragment fragmentDifferentActivityDemo, fragmentParticipantDemo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityCreateProject2DemoBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        SharedPreferences sPref = getSharedPreferences("MyPref", MODE_PRIVATE);
        mail = sPref.getString("UserMail", "");
        score = sPref.getInt("UserScore", 0);

        setActivityFormat();
        Bundle arguments = getIntent().getExtras();

        assert arguments != null;
        mediaPath = arguments.getString("mediaPath");
        uriPath = arguments.getString("uriPath");
        title = arguments.getString("title");
        description1 = arguments.getString("prDescription");

        if(!uriPath.isEmpty()){
            Uri uri = Uri.parse(uriPath);
            binding.prLogo.setImageURI(uri);
        }

        binding.nameProject.setText(title);

        fragmentDifferentActivityDemo = Fragment.instantiate(this, DifferentActivityDemo.class.getName());
        fragmentParticipantDemo = Fragment.instantiate(this, CreateProjectParticipantPartEmpty.class.getName());

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.nav_host_fragment_demo, fragmentDifferentActivityDemo, "mainFragment")
                .commit();

        binding.adParticipiant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(score < 100){
                    binding.linearProjects.setBackgroundColor(0);
                    binding.linearFriends.setBackgroundResource(R.drawable.blue_line);
                }
                else if(score < 300){
                    binding.linearProjects.setBackgroundColor(0);
                    binding.linearFriends.setBackgroundResource(R.drawable.green_line);
                }
                else if(score < 1000){
                    binding.linearProjects.setBackgroundColor(0);
                    binding.linearFriends.setBackgroundResource(R.drawable.brown_line);
                }
                else if(score < 2500){
                    binding.linearProjects.setBackgroundColor(0);
                    binding.linearFriends.setBackgroundResource(R.drawable.light_gray_line);
                }
                else if(score < 7000){
                    binding.linearProjects.setBackgroundColor(0);
                    binding.linearFriends.setBackgroundResource(R.drawable.ohra_line);
                }
                else if(score < 17000){
                    binding.linearProjects.setBackgroundColor(0);
                    binding.linearFriends.setBackgroundResource(R.drawable.red_line);
                }
                else if(score < 30000){
                    binding.linearProjects.setBackgroundColor(0);
                    binding.linearFriends.setBackgroundResource(R.drawable.orange_line);
                }
                else if(score < 50000){
                    binding.linearProjects.setBackgroundColor(0);
                    binding.linearFriends.setBackgroundResource(R.drawable.violete_line);
                }
                else{
                    binding.linearProjects.setBackgroundColor(0);
                    binding.linearFriends.setBackgroundResource(R.drawable.blue_green_line);
                }

                Bundle bundle = new Bundle();
                bundle.putString("mail", mail);
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.nav_host_fragment_demo, fragmentParticipantDemo, "mainFragment")
                        .commit();
            }
        });
        binding.adActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(score < 100){
                    binding.linearFriends.setBackgroundColor(0);
                    binding.linearProjects.setBackgroundResource(R.drawable.blue_line);
                }
                else if(score < 300){
                    binding.linearFriends.setBackgroundColor(0);
                    binding.linearProjects.setBackgroundResource(R.drawable.green_line);
                }
                else if(score < 1000){
                    binding.linearFriends.setBackgroundColor(0);
                    binding.linearProjects.setBackgroundResource(R.drawable.brown_line);
                }
                else if(score < 2500){
                    binding.linearFriends.setBackgroundColor(0);
                    binding.linearProjects.setBackgroundResource(R.drawable.light_gray_line);
                }
                else if(score < 7000){
                    binding.linearFriends.setBackgroundColor(0);
                    binding.linearProjects.setBackgroundResource(R.drawable.ohra_line);
                }
                else if(score < 17000){
                    binding.linearFriends.setBackgroundColor(0);
                    binding.linearProjects.setBackgroundResource(R.drawable.red_line);
                }
                else if(score < 30000){
                    binding.linearFriends.setBackgroundColor(0);
                    binding.linearProjects.setBackgroundResource(R.drawable.orange_line);
                }
                else if(score < 50000){
                    binding.linearFriends.setBackgroundColor(0);
                    binding.linearProjects.setBackgroundResource(R.drawable.violete_line);
                }
                else{
                    binding.linearFriends.setBackgroundColor(0);
                    binding.linearProjects.setBackgroundResource(R.drawable.blue_green_line);
                }
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.nav_host_fragment_demo, fragmentDifferentActivityDemo, "mainFragment")
                        .commit();
            }
        });

        binding.send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String purposeMain = "";
                String taskMain = "";
                String mainId;

                String[] purpose1 = purposes_name.split("✴\uFE0F");
                String[] purpose2 = purposes.split("✴\uFE0F");
                String[] task1 = tasks_name.split("✴\uFE0F");
                String[] task2 = tasks.split("✴\uFE0F");

                if (id1.isEmpty()) {
                    mainId = "Пользователи были не выбраны";
                } else {
                    mainId = id1;
                }
                if (purposes_name.isEmpty()) {
                    Toast.makeText(CreateProject2Demo.this, "Нет целей", Toast.LENGTH_SHORT).show();
                } else if (tasks_name.isEmpty()) {
                    Toast.makeText(CreateProject2Demo.this, "Нет задач", Toast.LENGTH_SHORT).show();
                } else {
                    for (int i = 0; i < purpose1.length; i++) {
                        if (i != purpose1.length - 1) {
                            purposeMain = purposeMain + purpose1[i] + "\uD83D\uDD70" + purpose2[i] + "\uD83D\uDD70";
                        } else {
                            purposeMain = purposeMain + purpose1[i] + "\uD83D\uDD70" + purpose2[i];
                        }
                    }
                    for (int i = 0; i < task1.length; i++) {
                        if (i != task1.length - 1) {
                            taskMain = taskMain + task1[i] + "\uD83D\uDD70" + task2[i] + "\uD83D\uDD70";
                        } else {
                            taskMain = taskMain + task1[i] + "\uD83D\uDD70" + task2[i];
                        }
                    }

                    if (mediaPath.isEmpty()) {
                        SharedPreferences sPref = getSharedPreferences("MyPref", MODE_PRIVATE);
                        SharedPreferences.Editor ed = sPref.edit();
                        ed.putBoolean("DemoProject", true);
                        ed.putString("DemoProjectTitle", title);
                        ed.putString("DemoProjectDescription", description1);
                        ed.putString("DemoProjectPurposes", purposeMain);
                        ed.putString("DemoProjectProblems", taskMain);
                        ed.putString("UriPath", uriPath);
                        ed.putBoolean("IsEnd", false);
                        ed.apply();

                        Intent intent = new Intent(CreateProject2Demo.this, ActivityProject.class);
                        startActivity(intent);
                    } else {
                        SharedPreferences sPref = getSharedPreferences("MyPref", MODE_PRIVATE);
                        SharedPreferences.Editor ed = sPref.edit();
                        ed.putBoolean("DemoProject", true);
                        ed.putString("DemoProjectTitle", title);
                        ed.putString("DemoProjectDescription", description1);
                        ed.putString("DemoProjectPurposes", purposeMain);
                        ed.putString("DemoProjectProblems", taskMain);
                        ed.putBoolean("IsEnd", false);
                        ed.apply();

                        Intent intent = new Intent(CreateProject2Demo.this, ActivityProject.class);
                        startActivity(intent);
                    }
                }
            }
        });

    }

    private void setActivityFormat(){
        if(score < 100){
            binding.bguser.setBackgroundResource(R.drawable.gradient_blue);
            getWindow().setStatusBarColor(ContextCompat.getColor(CreateProject2Demo.this,R.color.blue));
            binding.linearProjects.setBackgroundResource(R.drawable.blue_line);
            binding.send.setBackgroundTintList(ContextCompat.getColorStateList(CreateProject2Demo.this, R.color.blue));
        }
        else if(score < 300){
            binding.bguser.setBackgroundResource(R.drawable.gradient_green);
            getWindow().setStatusBarColor(ContextCompat.getColor(CreateProject2Demo.this,R.color.green));
            binding.linearProjects.setBackgroundResource(R.drawable.green_line);
            binding.send.setBackgroundTintList(ContextCompat.getColorStateList(CreateProject2Demo.this, R.color.green));
        }
        else if(score < 1000){
            binding.bguser.setBackgroundResource(R.drawable.gradient_brown);
            getWindow().setStatusBarColor(ContextCompat.getColor(CreateProject2Demo.this,R.color.brown));
            binding.linearProjects.setBackgroundResource(R.drawable.brown_line);
            binding.send.setBackgroundTintList(ContextCompat.getColorStateList(CreateProject2Demo.this, R.color.brown));
        }
        else if(score < 2500){
            binding.bguser.setBackgroundResource(R.drawable.gradient_light_gray);
            getWindow().setStatusBarColor(ContextCompat.getColor(CreateProject2Demo.this,R.color.light_gray));
            binding.linearProjects.setBackgroundResource(R.drawable.light_gray_line);
            binding.send.setBackgroundTintList(ContextCompat.getColorStateList(CreateProject2Demo.this, R.color.light_gray));
        }
        else if(score < 7000){
            binding.bguser.setBackgroundResource(R.drawable.gradient_ohra);
            getWindow().setStatusBarColor(ContextCompat.getColor(CreateProject2Demo.this,R.color.ohra));
            binding.linearProjects.setBackgroundResource(R.drawable.ohra_line);
            binding.send.setBackgroundTintList(ContextCompat.getColorStateList(CreateProject2Demo.this, R.color.ohra));
        }
        else if(score < 17000){
            binding.bguser.setBackgroundResource(R.drawable.gradient_red);
            getWindow().setStatusBarColor(ContextCompat.getColor(CreateProject2Demo.this,R.color.red));
            binding.linearProjects.setBackgroundResource(R.drawable.red_line);
            binding.send.setBackgroundTintList(ContextCompat.getColorStateList(CreateProject2Demo.this, R.color.red));
        }
        else if(score < 30000){
            binding.bguser.setBackgroundResource(R.drawable.gradient_orange);
            getWindow().setStatusBarColor(ContextCompat.getColor(CreateProject2Demo.this,R.color.orange));
            binding.linearProjects.setBackgroundResource(R.drawable.orange_line);
            binding.send.setBackgroundTintList(ContextCompat.getColorStateList(CreateProject2Demo.this, R.color.orange));
        }
        else if(score < 50000){
            binding.bguser.setBackgroundResource(R.drawable.gradient_violete);
            getWindow().setStatusBarColor(ContextCompat.getColor(CreateProject2Demo.this,R.color.violete));
            binding.linearProjects.setBackgroundResource(R.drawable.violete_line);
            binding.send.setBackgroundTintList(ContextCompat.getColorStateList(CreateProject2Demo.this, R.color.violete));
        }
        else{
            binding.bguser.setBackgroundResource(R.drawable.gradient_blue_green);
            getWindow().setStatusBarColor(ContextCompat.getColor(CreateProject2Demo.this,R.color.main_green));
            binding.linearProjects.setBackgroundResource(R.drawable.blue_green_line);
            binding.send.setBackgroundTintList(ContextCompat.getColorStateList(CreateProject2Demo.this, R.color.main_green));
        }
    }

    public void setPurp(String purpName, String purp){
        if(purposes_name.isEmpty()){
            purposes_name += purpName;
            purposes += purp;
        }
        else{
            purposes_name = purposes_name + "✴\uFE0F" + purpName;
            purposes = purposes + "✴\uFE0F" + purp;
        }
    }
    public void setTask(String taskName, String task){
        if(tasks_name.isEmpty()){
            tasks_name += taskName;
            tasks = task;
        }
        else{
            tasks_name = tasks_name + "✴\uFE0F" + taskName;
            tasks = tasks + "✴\uFE0F" + task;
        }
    }
    public void setId(String id){
        if(id1.isEmpty()){
            id1 = id;
        }
        else{
            id1 = id1 + "," + id;
        }
        Toast.makeText(this, "Участник добавлен", Toast.LENGTH_SHORT).show();
    }

    public int getScore(){
        return score;
    }
    public String getMail(){
        return mail;
    }

    public String getPurposes_name(){
        return purposes_name;
    }
    public String getPurposes(){
        return purposes;
    }
    public String getTasks_name(){
        return tasks_name;
    }
    public String getTasks(){
        return tasks;
    }
    public void setEdit1(String name, String purp){
        purposes_name = name;
        purposes = purp;
    }
    public String getUriPath(){
        return uriPath;
    }
    public String getPrName(){
        return title;
    }
    public void setEdit2(String name, String problem){
        tasks_name = name;
        tasks = problem;
    }
}