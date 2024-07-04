package com.example.doan_thibanglaixe.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.ActivityChooserView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.doan_thibanglaixe.Database.DatabaseHelper;
import com.example.doan_thibanglaixe.Object.User;
import com.example.doan_thibanglaixe.R;
import com.example.doan_thibanglaixe.Utilities.Constants;
import com.example.doan_thibanglaixe.Utilities.PreferenceManager;
import com.example.doan_thibanglaixe.databinding.ActivitySignInBinding;
import com.example.doan_thibanglaixe.databinding.ActivitySignUpBinding;


public class SignIn extends AppCompatActivity {
    private ActivitySignInBinding binding;
    private com.example.doan_thibanglaixe.Utilities.PreferenceManager preferenceManager;
    private DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_in);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        binding = ActivitySignInBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        db = new DatabaseHelper(this);
        preferenceManager = new com.example.doan_thibanglaixe.Utilities.PreferenceManager(getApplicationContext());

        setListeners();
    }

    private void setListeners() {
        binding.textCreateNewAccount.setOnClickListener(v ->
                startActivity(new Intent(getApplicationContext(), SignUp.class)));

        binding.buttonSignIn.setOnClickListener(v -> {
            if (isValidSignIn()) {
                signIn();
            }
        });
    }

    private void loading(Boolean isLoading) {
        if (isLoading) {
            binding.buttonSignIn.setVisibility(View.INVISIBLE);
            binding.processBar.setVisibility(View.VISIBLE);
        } else {
            binding.processBar.setVisibility(View.INVISIBLE);
            binding.buttonSignIn.setVisibility(View.VISIBLE);
        }
    }

    private void signIn() {
        loading(true);
        String email = binding.inputEmail.getText().toString();
        String password = binding.inputPassword.getText().toString();

        if (db.checkUser(email, password)) {
            User user = db.getUserByEmail(email);
            // Lưu id của người dùng vào SharedPreferences
            preferenceManager.putString(Constants.KEY_USER_ID, String.valueOf(user.getId()));
            preferenceManager.putBoolean(Constants.KEY_IS_SIGN_IN, true);
            preferenceManager.putString(Constants.KEY_EMAIL, email);
            // Chuyển sang màn hình MainActivity và truyền id của người dùng
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            intent.putExtra(Constants.KEY_USER_ID, user.getId());
            startActivity(intent);
            finish();
        } else {
            loading(false);
            showToast("Đăng nhập không thành công");
        }
    }

    private void showToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    private Boolean isValidSignIn() {
        if (binding.inputEmail.getText().toString().trim().isEmpty()) {
            showToast("Vui lòng nhập Email");
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(binding.inputEmail.getText().toString()).matches()) {
            showToast("Nhập đúng định dạng Email");
            return false;
        } else if (binding.inputPassword.getText().toString().trim().isEmpty()) {
            showToast("Vui lòng nhập mật khẩu");
            return false;
        } else {
            return true;
        }
    }
}