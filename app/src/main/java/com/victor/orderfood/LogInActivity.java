package com.victor.orderfood;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.victor.DAO.EmployeeDAO;
import com.victor.DTO.EmployeeDTO;
import com.victor.Helper.LocaleHelper;

import java.util.List;
import java.util.Locale;

/**
 * Created by Victor on 07/07/2017.
 */

public class LogInActivity extends AppCompatActivity implements View.OnClickListener{

    EditText etUsername, etPassword;
    Button btLogIn, btSignIn;
    EmployeeDAO employeeDAO;
    ImageButton btUs, btVi;
    TextView tvTitle;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        addControls();

        List<EmployeeDTO> listEmploy = employeeDAO.getAllList();
        if(listEmploy.isEmpty()){
            btSignIn.setVisibility(View.VISIBLE);
        } else {
            btSignIn.setVisibility(View.GONE);
        }

        setUpLanguage();
    }

    private void setUpLanguage() {
        sharedPreferences = getSharedPreferences("current_language",MODE_PRIVATE);
        String lang = sharedPreferences.getString("language","en");
        switch (lang){
            case "en":
                btVi.setImageResource(R.drawable.vi_black);
                updateViews("en");
                break;
            case "vi":
                btUs.setImageResource(R.drawable.us_black);
                updateViews("vi");
                break;
        }
    }

    @Override
    protected void onResume() {
        // update GUI, data,.... when SignInActivity close
        super.onResume();
    }

    private void addControls() {
        etUsername = (EditText) findViewById(R.id.et_usernameLogin);
        etPassword = (EditText) findViewById(R.id.et_passwordLogin);
        btLogIn = (Button) findViewById(R.id.bt_loginLogin);
        btSignIn = (Button) findViewById(R.id.bt_signinLogin);
        btUs = (ImageButton) findViewById(R.id.bt_us);
        btVi = (ImageButton) findViewById(R.id.bt_vi);
        tvTitle = (TextView) findViewById(R.id.tv_login_title);

        btLogIn.setOnClickListener(this);
        btSignIn.setOnClickListener(this);
        btUs.setOnClickListener(this);
        btVi.setOnClickListener(this);

        employeeDAO = new EmployeeDAO(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        sharedPreferences = getSharedPreferences("current_language",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        switch (id){
            case R.id.bt_loginLogin:
                checkLogIn();
                break;
            case R.id.bt_signinLogin:
                Intent intent = new Intent(this,SignInActivity.class);
                intent.putExtra("firstSignin",1);
                startActivity(intent);
                break;
            case R.id.bt_us:
                btUs.setImageResource(R.drawable.us);
                btVi.setImageResource(R.drawable.vi_black);
                editor.putString("language","en");
                editor.commit();
                updateViews("en");
                break;
            case R.id.bt_vi:
                btVi.setImageResource(R.drawable.vi);
                btUs.setImageResource(R.drawable.us_black);
                editor.putString("language","vi");
                editor.commit();
                updateViews("vi");
                break;
        }
    }

    private void updateViews(String languageCode) {
        Context context = LocaleHelper.setLocale(this, languageCode);
        Resources resources = context.getResources();

        etUsername.setHint(resources.getString(R.string.user_name));
        etPassword.setHint(resources.getString(R.string.pass_word));
        btSignIn.setText(resources.getString(R.string.sign_in));
        btLogIn.setText(resources.getString(R.string.log_in));
        tvTitle.setText(resources.getString(R.string.log_in));
    }

    private void checkLogIn(){
        String username = etUsername.getText().toString();
        String password = etPassword.getText().toString();
        int idEmploy  = employeeDAO.checkEmployees(username,password);
        int idRule = employeeDAO.getRule(idEmploy);
        if(idEmploy > 0){
            SharedPreferences sharedPreferences = getSharedPreferences("save_rule", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt("id_rule",idRule);
            editor.commit();

            Intent intent = new Intent(this,MainActivity.class);
            intent.putExtra("T_Username",username);
            intent.putExtra("T_idEmploy",idEmploy);
            startActivity(intent);
            overridePendingTransition(R.anim.effect_activity_in,R.anim.effect_activity_out);
        } else{
            Toast.makeText(this, "Đăng nhập không thành công!", Toast.LENGTH_SHORT).show();
        }

    }
}
