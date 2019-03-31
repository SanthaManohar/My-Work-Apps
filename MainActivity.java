package basicandroid.com.kadanerischoolreg;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;

public class MainActivity extends AppCompatActivity {

    //Declaration

    private ImageView imageView;
    private EditText name;
    private Button save,view;

    private static final int CAMERA_REQUEST = 1000;
    private static final int GALLERY_REQUEST = 1001;

    //Call DatabaseHelper Class
    static DatabaseHelper myDb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Inidialization DatabaseHelper method
        myDb = new DatabaseHelper(MainActivity.this);


        //Initialization

        imageView = (ImageView)findViewById(R.id.profile_image);
        name = (EditText)findViewById(R.id.name);
        save = (Button)findViewById(R.id.saveButton);
        view = (Button)findViewById(R.id.viewButton);

        //select camera or galley image
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String[] options = {"Camera","Gallery","Cancel"};
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Image");
                builder.setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(options[i].equals("Camera")){

                            cameraNew();

                        }else if(options[i].equals("Gallery")){
                            galleryNew();
                        }else if(options[i].equals("Cancel")){
                            dialogInterface.dismiss();
                        }
                    }
                });

                builder.show();

            }
        });

        //Save data into SQlite

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addData();
            }
        });

        //View data

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotoTotalList = new Intent(MainActivity.this,TotalList.class);
                startActivity(gotoTotalList);
            }
        });


    }

    //save method
    private void addData() {

        String name_string = name.getText().toString().trim();
       // String img_url = imageViewToByteArray(imageView).toString();

        Boolean result = myDb.insertData(name_string,imageViewToByteArray(imageView));

        if(result == true){
            Toast.makeText(this, "Saved...", Toast.LENGTH_SHORT).show();
            name.setText("");
            imageView.setImageResource(R.mipmap.ic_launcher);
        }else {
            Toast.makeText(this, "Not-Saved...", Toast.LENGTH_SHORT).show();
        }


    }

    private byte[] imageViewToByteArray(ImageView imageView) {

        Bitmap bitmap = ((BitmapDrawable)imageView.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100,stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;

    }

    private void galleryNew() {

        Intent galleryIntent = new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent,GALLERY_REQUEST);

    }

    private void cameraNew() {

        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent,CAMERA_REQUEST);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == CAMERA_REQUEST && resultCode == RESULT_OK){
            Bundle bundle = data.getExtras();
            Bitmap bitmap = (Bitmap)bundle.get("data");
            imageView.setImageBitmap(bitmap);
        }

        if(requestCode == GALLERY_REQUEST){
            Uri uri = data.getData();
            imageView.setImageURI(uri);
        }

    }



}
