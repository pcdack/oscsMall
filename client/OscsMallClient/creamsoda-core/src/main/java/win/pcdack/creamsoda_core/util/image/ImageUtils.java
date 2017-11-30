package win.pcdack.creamsoda_core.util.image;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.util.Base64;
import android.widget.ImageView;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;


/**
 * Created by pcdack on 17-11-25.
 * func:头像图片工具
 */

public class ImageUtils {
    public static String putImg2Base64(BitmapDrawable drawable) {
//        BitmapDrawable drawable= (BitmapDrawable) circleImageView.getDrawable();
        Bitmap bitmap = drawable.getBitmap();
        //将bitmap压缩成字节输出流
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 80, byteArrayOutputStream);
        //利用Based64将我们的字节数组输出流转换为String
        byte[] bytes = byteArrayOutputStream.toByteArray();
        @SuppressWarnings("RedundantStringConstructorCall")
        String imgStr = new String(Base64.encodeToString(bytes, Base64.DEFAULT));
        return imgStr;
    }

    public static void getImgByBase64(String content, ImageView circleImageView) {
        //利用Base64转化为图像
        byte[] bytes = Base64.decode(content, Base64.DEFAULT);
        ByteArrayInputStream byInputStream = new ByteArrayInputStream(bytes);
        Bitmap bitmap = BitmapFactory.decodeStream(byInputStream);
        circleImageView.setImageBitmap(bitmap);
    }
}
