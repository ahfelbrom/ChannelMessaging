package alexis.boulet.channelmessaging;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.List;

/**
 * Created by bouleta on 27/01/2017.
 */
public class MySecondArrayAdapter extends ArrayAdapter<Message> {
    private final Context context;
    private final List<Message> values;


    public MySecondArrayAdapter(Context context, List<Message> values) {
        super(context, R.layout.layout_lane, values);
        this.context = context;
        this.values = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.layout_chan_mess, parent, false);
        TextView tvUserId = (TextView) rowView.findViewById(R.id.tvUserId);
        TextView tvMessage = (TextView) rowView.findViewById(R.id.tvMessage);
        final ImageView ivUser = (ImageView) rowView.findViewById(R.id.imageViewProfil);
        Message mess = values.get(position);
        tvUserId.setText("" + mess.getUsername());
        tvMessage.setText("" + mess.getMessage());
        context.getExternalFilesDir(Environment.DIRECTORY_PICTURES).mkdirs();
        String filepath = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)+"/profile"+mess.getUsername()+".jpg";
        if(!new File(filepath).exists()) {
            final DownloadImageUrl diu = new DownloadImageUrl(mess.getImageUrl().toString(),filepath);
            diu.addOnDownloadCompleteListener(new OnDownloadCompleteListener() {
                @Override
                public void onDownloadComplete(String content, int requestCode) {
                    Bitmap imageRounded = getRoundedCornerBitmap(BitmapFactory.decodeFile(diu.getFilePath()));
                    ivUser.setImageBitmap(imageRounded);
                }
            });
            diu.execute();
        }
        else {
            Bitmap imageRounded = getRoundedCornerBitmap(BitmapFactory.decodeFile(filepath));
            ivUser.setImageBitmap(imageRounded);
        }

        return rowView;
    }

    public static Bitmap getRoundedCornerBitmap(Bitmap bitmap) {
        if(bitmap != null) {
            Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                    bitmap.getHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(output);

            final int color = 0xff424242;
            final Paint paint = new Paint();
            final Rect rect = new Rect(0, 0, 390, 390);
            final RectF rectF = new RectF(rect);
            final float roundPx = 200;

            paint.setAntiAlias(true);
            canvas.drawARGB(0, 0, 0, 0);
            paint.setColor(color);
            canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
            canvas.drawBitmap(bitmap, rect, rect, paint);
            return output;
        } else {
            return null;
        }
    }

}
