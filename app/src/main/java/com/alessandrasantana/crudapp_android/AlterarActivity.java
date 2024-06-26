package com.alessandrasantana.crudapp_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AlterarActivity extends AppCompatActivity {
    private SQLiteDatabase bancoDados;
    public Button buttonAlterar;
    public EditText editTextNomeAlterar;
    public Integer id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alterar);

        buttonAlterar = (Button) findViewById(R.id.buttonAlterar);
        editTextNomeAlterar = (EditText) findViewById(R.id.editTextNomeAlterar);

        Intent intent = getIntent();
        id = intent.getIntExtra("id", 0);

        carregarDados();

        buttonAlterar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alterar();
            }
        });

    }

    public void carregarDados(){
        try {
            bancoDados = openOrCreateDatabase("crudapp1", MODE_PRIVATE, null);
            Cursor cursor = bancoDados.rawQuery("SELECT id, nome FROM coisa WHERE id = " + id.toString(), null);
            cursor.moveToFirst();
            editTextNomeAlterar.setText(cursor.getString(1));

        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void alterar(){
        String valueNome;
        valueNome = editTextNomeAlterar.getText().toString();
        try{
            bancoDados = openOrCreateDatabase("crudapp1", MODE_PRIVATE, null);
            String sql = "UPDATE coisa SET nome=? WHERE id=?";
            SQLiteStatement stmt = bancoDados.compileStatement(sql);
            stmt.bindString(1,valueNome);
            stmt.bindLong(2,id);
            stmt.executeUpdateDelete();
            bancoDados.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        finish();
    }
}