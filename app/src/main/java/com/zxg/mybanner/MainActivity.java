package com.zxg.mybanner;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.zxg.mybanner.bean.AdvicesBackBean;
import com.zxg.mybanner.widget.BannerOnClickListener;
import com.zxg.mybanner.widget.BannerRelaytiveLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements BannerOnClickListener{
    private BannerRelaytiveLayout mMyRelaytiveLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mMyRelaytiveLayout = (BannerRelaytiveLayout) findViewById(R.id.mainfgt_banner);



        List<AdvicesBackBean.ContentBean> lists = new ArrayList<>();
        AdvicesBackBean.ContentBean mContentBean = new AdvicesBackBean.ContentBean();
        mContentBean.setName("我的名字");
        mContentBean.setUrl("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1523853619054&di=b94e32ee6ec068d2ce6db4f24739f116&imgtype=0&src=http%3A%2F%2Fe.hiphotos.baidu.com%2Fbaike%2Fpic%2Fitem%2Fa9d3fd1f4134970a34d8956994cad1c8a6865dc8.jpg");
        lists.add(mContentBean);
        AdvicesBackBean.ContentBean mContentBean2 = new AdvicesBackBean.ContentBean();
        mContentBean2.setName("我的名字2");
        mContentBean2.setUrl("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1523853619054&di=56834cd4c8c64a35614360783bdadcc4&imgtype=0&src=http%3A%2F%2Fimg5.duitang.com%2Fuploads%2Fitem%2F201407%2F04%2F20140704230315_wdE4k.thumb.700_0.jpeg");
        lists.add(mContentBean2);
        AdvicesBackBean.ContentBean mContentBean3 = new AdvicesBackBean.ContentBean();
        mContentBean3.setName("我的名字2");
        mContentBean3.setUrl("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1523853619053&di=bdca79b44bf184c60713e25f2fa00172&imgtype=0&src=http%3A%2F%2Fp2.gexing.com%2Fqqpifu%2F20130114%2F2046%2F50f3fe1658a0d.jpg");
        lists.add(mContentBean3);
        try {
            mMyRelaytiveLayout.setBannerOnClickListener(this);
            mMyRelaytiveLayout.updateImages(lists);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void imageClick(int position) {
        Log.e("点击的图片序号=",position+"");
    }
}
