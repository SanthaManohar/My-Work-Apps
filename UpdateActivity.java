package basicandroid.com.kadanerischoolreg;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;

public class UpdateActivity extends AppCompatActivity {

    EditText name;
    ImageView imageView;

    Button update;

    static DatabaseHelper myDb;

    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        myDb = new DatabaseHelper(UpdateActivity.this);

        name = (EditText)findViewById(R.id.name);
        imageView = (ImageView)findViewById(R.id.image);
        update = (Button) findViewById(R.id.updateButton);


       Pojo pojo= (Pojo) getIntent().getExtras().getSerializable("STUDENT");
       id = pojo.getId();
       name.setText(pojo.getName());
       byte[] sample = pojo.getImage();
        Bitmap bitmap = BitmapFactory.decodeByteArray(sample,0,sample.length);
        imageView.setImageBitmap(bitmap);

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateData(view);
            }
        });


    }

    private void updateData(View view) {

        String name_string = name.getText().toString().trim();

        Pojo pojo = new Pojo(id,name_string,imageViewToByteArray(imageView));

        boolean result = myDb.updateData(pojo);
        if(result == true){
            Toast.makeText(this, "Updated..", Toast.LENGTH_SHORT).show();
            Intent gotoTotallist = new Intent(UpdateActivity.this,TotalList.class);
            startActivity(gotoTotallist);
        }else {
            Toast.makeText(this, "Not-Updated..", Toast.LENGTH_SHORT).show();

        }

    }

    private byte[] imageViewToByteArray(ImageView imageView) {

        Bitmap bitmap = ((BitmapDrawable)imageView.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100,stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;

    }


}
