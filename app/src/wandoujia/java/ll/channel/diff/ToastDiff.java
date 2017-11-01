package ll.channel.diff;

import android.content.Context;
import android.widget.Toast;

import com.withwings.ibasemodule.R;

/**
 * 测试 productFlavors
 * 创建：WithWings 时间：2017/11/1.
 * Email:wangtong1175@sina.com
 */
public class ToastDiff {

    public static void showFrom(Context context){
        Toast.makeText(context, "豌豆荚" + context.getString(R.string.channel_diff), Toast.LENGTH_SHORT).show();
    }

}
