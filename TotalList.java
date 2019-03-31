package basicandroid.com.kadanerischoolreg;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class TotalList extends AppCompatActivity {

     ListView listView;
     ArrayList<Pojo> list;
     CustomAdapter adapter = null;

     static  DatabaseHelper myDb;
    Cursor result;
    Pojo pojo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_total_list);

        myDb = new DatabaseHelper(TotalList.this);

        listView = (ListView)findViewById(R.id.totallist);
        list = new ArrayList<>();
        adapter = new CustomAdapter(TotalList.this,R.layout.activity_custom_adapter,list);
        listView.setAdapter(adapter);

        result = MainActivity.myDb.getAllData("select * from Firsttable");
        list.clear();
        while (result.moveToNext()){
            int id = result.getInt(0);
            String name = result.getString(1);
            byte[] image = result.getBlob(2);

            list.add(new Pojo(id,name,image));

        }

        adapter.notifyDataSetChanged();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                Pojo pojo = list.get(position);

                Intent senddataToAnotherActivity = new Intent(TotalList.this,UpdateActivity.class);
                senddataToAnotherActivity.putExtra("STUDENT",pojo);
                startActivity(senddataToAnotherActivity);


            }

            });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long l) {

                pojo = list.get(position);

                AlertDialog.Builder builder = new AlertDialog.Builder(TotalList.this);
                builder.setTitle("Do you want delete?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int position) {

                        int id = pojo.getId();
                        int result = myDb.deleteData(id);
                        if(result == 1){
                            Toast.makeText(TotalList.this, "Deleted...", Toast.LENGTH_SHORT).show();
                            Intent gotoTotallist = new Intent(TotalList.this,TotalList.class);
                            startActivity(gotoTotallist);
                        }else {
                            Toast.makeText(TotalList.this, "Not-Deleted...", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });

                builder.show();



                return true;
            }
        });

    }
}
