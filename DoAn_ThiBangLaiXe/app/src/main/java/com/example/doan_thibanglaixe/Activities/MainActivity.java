package com.example.doan_thibanglaixe.Activities;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Base64;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.doan_thibanglaixe.Database.DatabaseHelper;
import com.example.doan_thibanglaixe.Object.User;
import com.example.doan_thibanglaixe.R;
import com.example.doan_thibanglaixe.Utilities.Constants;
import com.example.doan_thibanglaixe.Utilities.PreferenceManager;
import com.example.doan_thibanglaixe.databinding.ActivityMainBinding;
import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {
    private ActivityMainBinding binding;
    private PreferenceManager preferenceManager;
    private static  final int FRAGMENT_TTCN = 0;
    Dialog dialogThiSatHach, dialogMeoThucHanh;
    LinearLayout bt_meoGhiNho, bt_meoThucHanh, bt_lichSuBaiThi, btThiSatHach, bt_BienBao, bt_LyThuyet;
    private ActionBarDrawerToggle toggle;
    private int mCurrentFragment = FRAGMENT_TTCN;
    private DrawerLayout mDrawerLayout;
    private DatabaseHelper databaseHelper;
    Button bt_a122, bt_b122, bt_cancel2;
    public static int dem8=0;
    public static boolean checkTime = false;

    Button bt_a121, bt_b121, bt_cancel1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        preferenceManager = new PreferenceManager(getApplicationContext());

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Remove default title text
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        mDrawerLayout = findViewById(R.id.main);
        toggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);

        // Lấy thông tin người dùng từ cơ sở dữ liệu và hiển thị lên navigation
        displayUserInfoInNavigation();

        btThiSatHach = findViewById(R.id.bt_thiSatHach);
        btThiSatHach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDialogThiSatHach();
            }
        });

        addControl();
    }
    private void addControl(){
        bt_meoGhiNho = findViewById(R.id.bt_meoGhiNho);
        bt_meoGhiNho.setOnClickListener(this);
        bt_meoThucHanh = findViewById(R.id.bt_meoThucHanh);
        bt_meoThucHanh.setOnClickListener(this);
        bt_lichSuBaiThi = findViewById(R.id.bt_lichSuBaiThi);
        bt_lichSuBaiThi.setOnClickListener(this);
        bt_BienBao = findViewById(R.id.bt_bienBao);
        bt_BienBao.setOnClickListener(this);
        bt_LyThuyet = findViewById(R.id.bt_lyThuyet);
        bt_LyThuyet.setOnClickListener(this);
    }
    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.bt_meoGhiNho){
            Intent intentMeoGhiNho = new Intent(this, MeoGhiNhoActivity.class);
            startActivity(intentMeoGhiNho);

        }
        else if(view.getId() == R.id.bt_meoThucHanh){
            setDialogMeoThucHanh();
        }
        else if(view.getId() == R.id.bt_a122){
            Intent intentThucHanhA = new Intent(this,MeoThucHanhAActivity.class);
            startActivity(intentThucHanhA);
            dialogMeoThucHanh.dismiss();
        }
        else if(view.getId() == R.id.bt_b122){
            Intent intentThucHanhB = new Intent(this,MeoThucHanhBActivity.class);
            startActivity(intentThucHanhB);
            dialogMeoThucHanh.dismiss();
        }
        else if(view.getId() == R.id.bt_cancel2){
            dialogMeoThucHanh.dismiss();
        }
        else if(view.getId() == R.id.bt_lichSuBaiThi)
        {
            Intent intentLichSu = new Intent(this, LichSuBaiThiActivity.class);
            startActivity(intentLichSu);
            if (dialogMeoThucHanh != null && dialogMeoThucHanh.isShowing()) {
                dialogMeoThucHanh.dismiss();
            }
        }
        else if(view.getId() == R.id.bt_bienBao){
            Intent intentBienBao = new Intent(this, BienBaoActivity.class);
            startActivity(intentBienBao);
        }
        else if(view.getId() == R.id.bt_lyThuyet){
            Intent intentLyThuyet = new Intent(this, LyThuyetActivity.class);
            startActivity(intentLyThuyet);
        }

    }
    public void setDialogMeoThucHanh(){
        dialogMeoThucHanh = new Dialog(this);
        dialogMeoThucHanh.setContentView(R.layout.custom_dialog_meothuchanh);
        dialogMeoThucHanh.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogMeoThucHanh.setCanceledOnTouchOutside(true);
        dialogMeoThucHanh.show();
        bt_a122 = dialogMeoThucHanh.findViewById(R.id.bt_a122);
        bt_b122 = dialogMeoThucHanh.findViewById(R.id.bt_b122);
        bt_cancel2 = dialogMeoThucHanh.findViewById(R.id.bt_cancel2);
        bt_a122.setOnClickListener(this);
        bt_b122.setOnClickListener(this);
        bt_cancel2.setOnClickListener(this);
    }
    private void displayUserInfoInNavigation() {
        databaseHelper = new DatabaseHelper(this); // Khởi tạo databaseHelper
        // Lấy userId từ PreferenceManager
        String userId = preferenceManager.getString(Constants.KEY_USER_ID);
        // Lấy thông tin người dùng từ cơ sở dữ liệu
        User user = databaseHelper.getUserById(userId);
        if (user != null) {
            // Thiết lập thông tin người dùng lên navigation
            NavigationView navigationView = findViewById(R.id.navigation_view);
            View headerView = navigationView.getHeaderView(0);
            TextView textViewName = headerView.findViewById(R.id.textViewName);
            TextView textViewEmail = headerView.findViewById(R.id.textViewEmail);
            ImageView imageViewProfile = headerView.findViewById(R.id.imageViewProfile);

            textViewName.setText(user.getName());
            textViewEmail.setText(user.getEmail());
            // Load hình ảnh người dùng (nếu có)
            if (user.getImage() != null && !user.getImage().isEmpty()) {
                // Giải mã chuỗi Base64 thành mảng byte
                byte[] decodedString = Base64.decode(user.getImage(), Base64.DEFAULT);
                // Chuyển đổi mảng byte thành đối tượng Bitmap
                Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                // Tạo bitmap hình tròn từ bitmap đã giải mã
//                Bitmap circularBitmap = getRoundedBitmap(decodedByte);
                // Hiển thị hình ảnh trong ImageView
                imageViewProfile.setImageBitmap(decodedByte);
            }
        }
    }

    // Phương thức để tạo bitmap hình tròn từ bitmap ban đầu
    private Bitmap getRoundedBitmap(Bitmap bitmap) {
        // Tạo một bitmap trống có kích thước bằng với bitmap ban đầu
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        // Tạo một vòng cung để vẽ bitmap
        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);
        final float roundPx = bitmap.getWidth() / 2;

        // Thiết lập các thuộc tính cho paint
        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);

        // Vẽ vòng cung
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

        // Chỉ hiển thị phần bitmap được cắt
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        return output;
    }

    private void logout() {
        // Xóa tất cả dữ liệu SharedPreferences khi đăng xuất
        preferenceManager.clear();

        // Chuyển hướng đến màn hình đăng nhập và xóa tất cả các Activity khác khỏi stack
        Intent intent = new Intent(this, SignIn.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish(); // Kết thúc Activity hiện tại để ngăn người dùng quay lại khi nhấn nút Back
    }

    private void changePassword() {
        // Lấy userId từ PreferenceManager
        String userId = preferenceManager.getString(Constants.KEY_USER_ID);
        // Chuyển hướng đến Activity mới để người dùng có thể đổi mật khẩu
        Intent intent = new Intent(this, ChangePassword.class);
        // Truyền userId tới activity ChangePassword
        intent.putExtra("USER_ID", userId);
        startActivity(intent);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();
        if (id == R.id.nav_dangxuat) {
            logout(); // Gọi phương thức logout() khi người dùng chọn mục menu "Đăng xuất"

        }else if (id == R.id.doimatkhau) {
            changePassword(); // Gọi phương thức changePassword() khi người dùng chọn mục menu "Đổi mật khẩu"
        }
        return true;
    }

    public void setDialogThiSatHach(){
        dialogThiSatHach = new Dialog(this);
        dialogThiSatHach.setContentView(R.layout.custom_dialog_thisathach);
        dialogThiSatHach.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogThiSatHach.setCanceledOnTouchOutside(true);
        dialogThiSatHach.show();

        bt_a121 = (Button) dialogThiSatHach.findViewById(R.id.bt_a121);
        bt_b121 = (Button) dialogThiSatHach.findViewById(R.id.bt_b121);
        bt_cancel1 = (Button) dialogThiSatHach.findViewById(R.id.bt_cancel1);

        // Button A121 Click Listener
        bt_a121.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intentThiSatHachA = new Intent(MainActivity.this,ThiSatHachActivity.class);
                intentThiSatHachA.putExtra("tenBaiThi",'a');
                startActivity(intentThiSatHachA);
                dialogThiSatHach.dismiss();
            }
        });

        // Button B121 Click Listener
        bt_b121.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the B121 driving test activity
                Intent intentThiSatHachB = new Intent(MainActivity.this,ThiSatHachActivity.class);
                intentThiSatHachB.putExtra("tenBaiThi",'b');
                startActivity(intentThiSatHachB);
                dialogThiSatHach.dismiss(); // Dismiss the dialog
            }
        });

        // Cancel Button Click Listener
        bt_cancel1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogThiSatHach.dismiss(); // Dismiss the dialog
            }
        });
    }

}