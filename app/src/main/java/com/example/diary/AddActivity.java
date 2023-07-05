package com.example.diary;

import androidx.appcompat.app.AppCompatActivity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.example.diary.bean.Note;
import com.example.diary.util.ToastUtil;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AddActivity extends AppCompatActivity {

    private EditText etTitle,etContent,etAuthor;

    private  NoteDbOpenHelper mNoteDbOpenHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        etTitle = findViewById(R.id.et_title);
        etContent = findViewById(R.id.et_content);
        etAuthor = findViewById(R.id.et_author);
        mNoteDbOpenHelper = new NoteDbOpenHelper(this);
    }

    public void add(View view) {
        String title = etTitle.getText().toString();
        String content = etContent.getText().toString();
        String author = etAuthor.getText().toString();
        SharedPreferences spRecord = getSharedPreferences("spRecord", MODE_PRIVATE);
        if (TextUtils.isEmpty(title)) {
            ToastUtil.toastShort(this, "标题不能为空！");
            return;
        }
        if(TextUtils.isEmpty(author)){
            author = "zhang";
        }
        Note note = new Note();

        note.setTitle(title);
        note.setContent(content);
        note.setCreatedTime(getCurrentTimeFormat());
        SharedPreferences.Editor edit = spRecord.edit();
        edit.putString("author",author);
        edit.commit();
        String aut = spRecord.getString("author","zhang");
        note.setAuthor(aut);
        long row = mNoteDbOpenHelper.insertData(note);
        if(row != -1){
            ToastUtil.toastShort(this,"添加成功");
            this.finish();
        }else{
            ToastUtil.toastShort(this,"添加失败");
        }
    }
    private String getCurrentTimeFormat(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYY年MM月dd日 HH:mm:ss");
        Date date = new Date();
        return simpleDateFormat.format(date);
    }
}