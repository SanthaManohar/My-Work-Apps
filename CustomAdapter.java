package basicandroid.com.kadanerischoolreg;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomAdapter extends BaseAdapter {

    private Context context;
    private int layout;
    private ArrayList<Pojo> arrayList;

    public CustomAdapter(Context context, int layout, ArrayList<Pojo> arrayList) {
        this.context = context;
        this.layout = layout;
        this.arrayList = arrayList;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return arrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public class viewHolder{
        TextView textname;
        ImageView imageView;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        View row = view;
        viewHolder holder = new viewHolder();

        if(row == null){
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(layout,null);

            holder.textname = (TextView)row.findViewById(R.id.name);
            holder.imageView = (ImageView) row.findViewById(R.id.image);
            row.setTag(holder);


        }else{
            holder = (viewHolder)row.getTag();
        }

        Pojo pojo = arrayList.get(i);

        holder.textname.setText(pojo.getName());
        byte[] pojoadmin = pojo.getImage();
        Bitmap bitmap = BitmapFactory.decodeByteArray(pojoadmin,0,pojoadmin.length);
        holder.imageView.setImageBitmap(bitmap);

        return row;
    }
}
