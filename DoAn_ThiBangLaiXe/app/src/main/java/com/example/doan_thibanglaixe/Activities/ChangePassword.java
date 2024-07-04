package com.example.doan_thibanglaixe.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.doan_thibanglaixe.Database.DatabaseHelper;
import com.example.doan_thibanglaixe.R;
import com.example.doan_thibanglaixe.Utilities.Constants;

public class ChangePassword extends AppCompatActivity {

    private EditText editTextOldPassword, editTextNewPassword;
    private Button buttonChangePassword;
    private DatabaseHelper db;
    private String userId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_change_password);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        event();

        String userId = getIntent().getStringExtra("USER_ID");

        db = new DatabaseHelper(this);

        // Lấy ID của người dùng từ Intent
        db = new DatabaseHelper(this);
        // Đặt lắng nghe sự kiện khi nút "Đổi mật khẩu" được nhấn
        buttonChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changePassword(userId);
            }
        });
    }

    public void event(){
        editTextOldPassword = findViewById(R.id.editTextOldPassword);
        editTextNewPassword = findViewById(R.id.editTextNewPassword);
        buttonChangePassword = findViewById(R.id.buttonChangePassword);
    }

    private void changePassword(String userId) {
        // Đọc dữ liệu từ các EditText
        String oldPassword = editTextOldPassword.getText().toString();
        String newPassword = editTextNewPassword.getText().toString();

        // Kiểm tra xem mật khẩu cũ có đúng hay không cho userId đã nhận được
        if (db.checkOldPassword(userId, oldPassword)) {
            // Mật khẩu cũ đúng, tiếp tục với quá trình đổi mật khẩu

            // Cập nhật mật khẩu mới trong cơ sở dữ liệu
            if (db.updatePassword(userId, newPassword)) {
                // Mật khẩu đã được cập nhật thành công

                // Hiển thị thông báo cho người dùng
                Toast.makeText(this, "Đã thay đổi mật khẩu!", Toast.LENGTH_SHORT).show();

                // Đóng Activity và quay lại màn hình trước đó
                finish();
            } else {
                // Đã xảy ra lỗi khi cập nhật mật khẩu
                Toast.makeText(this, "Không thể cập nhật mật khẩu!", Toast.LENGTH_SHORT).show();
            }
        } else {
            // Mật khẩu cũ không đúng, hiển thị thông báo lỗi cho người dùng
            Toast.makeText(this, "Mật khẩu cũ không đúng!", Toast.LENGTH_SHORT).show();
        }
    }
}