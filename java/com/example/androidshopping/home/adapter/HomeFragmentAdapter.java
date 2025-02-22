package com.example.androidshopping.home.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;


import com.bumptech.glide.Glide;
import com.example.androidshopping.R;
import com.example.androidshopping.app.GoodsInfoActivity;
import com.example.androidshopping.home.bean.GoodsBean;
import com.example.androidshopping.home.bean.ResultBeanData;
import com.example.androidshopping.utils.Constants;



import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.transform.Transformer;

/**
 * 作用：RecyclerView的适配器
 */
public class HomeFragmentAdapter extends RecyclerView.Adapter {

    /**
     * 广告条幅类型
     */
    public static final int BANNER = 0;

    /**
     * 频道类型
     */
    public static final int CHANNEL = 1;
    /**
     * 活动类型
     */
    public static final int ACT = 2;
    /**
     * 秒杀类型
     */
    public static final int SECKILL = 3;
    /**
     * 推荐类型
     */
    public static final int RECOMMEND = 4;
    /**
     * 热卖
     */
    public static final int HOT = 5;
    private static final String GOODS_BEAN = "goodsBean";
    /**
     * 用来初始化布局
     */
    private LayoutInflater mLayoutInflater;
    private Context mContext;
    /**
     * 数据
     */
    private ResultBeanData.ResultBean resultBean;

    /**
     * 当前类型
     */
    private int currentType = BANNER;

    public HomeFragmentAdapter(Context mContext, ResultBeanData.ResultBean resultBean) {
        this.mContext = mContext;
        this.resultBean = resultBean;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    /**
     * 相当于getView 创建ViewHolder部分代码
     * 创建ViewHolder
     *
     * @param parent
     * @param viewType 当前的类型
     * @return
     */
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == BANNER) {
            //todo
            return new BannerViewHolder(mContext, mLayoutInflater.inflate(R.layout.banner_viewpager, null));
        } else if (viewType == CHANNEL) {
            return new ChannelViewHolder(mContext, mLayoutInflater.inflate(R.layout.channel_item, null));
        } else if (viewType == ACT) {
            return new ActViewHolder(mContext, mLayoutInflater.inflate(R.layout.act_item, null));
        }else if (viewType == SECKILL) {
            return new SeckillViewHolder(mContext, mLayoutInflater.inflate(R.layout.seckill_item, null));
        }else if(viewType == RECOMMEND ){
            return new RecommendViewHolder(mContext, mLayoutInflater.inflate(R.layout.recommend_item, null));
        }else if(viewType == HOT){
            return new HotViewHolder(mContext, mLayoutInflater.inflate(R.layout.hot_item, null));
        }
        return null;
    }

    /**
     * 相当于getview中的绑定数据模块
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == BANNER) {
            BannerViewHolder bannerViewHolder = (BannerViewHolder) holder;
            bannerViewHolder.setData();
        } else if (getItemViewType(position) == CHANNEL) {
            ChannelViewHolder channelViewHolder = (ChannelViewHolder) holder;
            channelViewHolder.setData(resultBean.getChannel_info());
        } else if (getItemViewType(position) == ACT) {
            ActViewHolder actViewHolder = (ActViewHolder) holder;
            actViewHolder.setData(resultBean.getAct_info());
        }else if(getItemViewType(position) == SECKILL){
            SeckillViewHolder seckillViewHolder = (SeckillViewHolder) holder;
            seckillViewHolder.setData(resultBean.getSeckill_info());
        }else if(getItemViewType(position) == RECOMMEND){
            RecommendViewHolder recommendViewHolder = (RecommendViewHolder) holder;
            recommendViewHolder.setData(resultBean.getRecommend_info());
        }else if(getItemViewType(position)==HOT){
            HotViewHolder hotViewHolder = (HotViewHolder) holder;
            hotViewHolder.setData(resultBean.getHot_info());
        }

    }

    class HotViewHolder extends RecyclerView.ViewHolder{

        private final Context mContext;
      //实例化，可操作
        private TextView tv_more_hot;
        private GridView gv_hot;

        public HotViewHolder(final Context mContext, View itemView) {
            super(itemView);
            this.mContext = mContext;
            //实例化，可操作
            tv_more_hot = (TextView) itemView.findViewById(R.id.tv_more_hot);
            gv_hot = (GridView) itemView.findViewById(R.id.gv_hot);

        }

        public void setData(final List<ResultBeanData.ResultBean.HotInfoBean> hot_info) {
            //1.有数据
            //2.设置GridView的适配器，即填充HotGridView
            HotGridViewAdapter  adapter = new HotGridViewAdapter(mContext,hot_info);
            gv_hot.setAdapter(adapter);


            //设置item的监听
            gv_hot.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Toast.makeText(mContext, "position=="+position, Toast.LENGTH_SHORT).show();
                    //热卖商品信息类
                    ResultBeanData.ResultBean.HotInfoBean hotInfoBean =  hot_info.get(position);
                    //商品信息类
                    GoodsBean goodsBean = new GoodsBean();
                    goodsBean.setCover_price(hotInfoBean.getCover_price());
                    goodsBean.setFigure(hotInfoBean.getFigure());
                    goodsBean.setName(hotInfoBean.getName());
                    goodsBean.setProduct_id(hotInfoBean.getProduct_id());
                    startGoodsInfoActivity(goodsBean);
                }
            });
        }
    }


    class RecommendViewHolder extends RecyclerView.ViewHolder{

        private final Context mContext;

        //实例化，即可以进行单击等操作
        private TextView tv_more_recommend;
        private GridView gv_recommend;

        private RecommendGridViewAdapter adapter;

        public RecommendViewHolder(final Context mContext, View itemView) {
            super(itemView);
            this.mContext = mContext;
            //实例化，即可以进行单击等操作
            tv_more_recommend = (TextView) itemView.findViewById(R.id.tv_more_recommend);
            gv_recommend = (GridView) itemView.findViewById(R.id.gv_recommend);


        }

        public void setData(final List<ResultBeanData.ResultBean.RecommendInfoBean> recommend_info) {
            //1.有数据了
            //2.设置适配器
            adapter = new RecommendGridViewAdapter(mContext,recommend_info);
            gv_recommend.setAdapter(adapter);

            gv_recommend.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Toast.makeText(mContext, "position=="+position, Toast.LENGTH_SHORT).show();
                    ResultBeanData.ResultBean.RecommendInfoBean recommendInfoBean = recommend_info.get(position);

                    GoodsBean goodsBean = new GoodsBean();
                    goodsBean.setCover_price(recommendInfoBean.getCover_price());
                    goodsBean.setFigure(recommendInfoBean.getFigure());
                    goodsBean.setName(recommendInfoBean.getName());
                    goodsBean.setProduct_id(recommendInfoBean.getProduct_id());
                    startGoodsInfoActivity(goodsBean);
                }
            });
        }
    }


    class SeckillViewHolder extends RecyclerView.ViewHolder{
        private final Context mContext;
        private TextView tv_time_seckill;
        private TextView tv_more_seckill;
        private RecyclerView rv_seckill;
        private SeckillRecyclerViewAdapter adapter;

        /**
         * 相差多少时间-毫秒
         */
        private long dt = 0;

        private Handler handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                dt = dt - 1000;//handler每发送一次信息，剩余时间减少一秒
                SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
                String time = formatter.format(new Date(dt));
                tv_time_seckill.setText(time);

                handler.removeMessages(0);//移除缓存消息
                handler.sendEmptyMessageDelayed(0,1000);//隔1秒后发送信息
                if(dt <=0){
                    //把消息移除(停止发送信息，即销毁handler)
                    handler.removeCallbacksAndMessages(null);
                }

            }
        };

        public SeckillViewHolder(Context mContext, View itemView) {
            super(itemView);
            tv_time_seckill = (TextView) itemView.findViewById(R.id.tv_time_seckill);
            tv_more_seckill = (TextView) itemView.findViewById(R.id.tv_more_seckill);
            rv_seckill = (RecyclerView) itemView.findViewById(R.id.rv_seckill);
            this.mContext = mContext;
        }

        public void setData(final ResultBeanData.ResultBean.SeckillInfoBean seckill_info) {
            //1.得到数据了
            //2.设置数据：文本和RecyclerView的数据
            adapter = new SeckillRecyclerViewAdapter(mContext,seckill_info.getList());
            rv_seckill.setAdapter(adapter);

            //设置布局管理器
            rv_seckill.setLayoutManager(new LinearLayoutManager(mContext,LinearLayoutManager.HORIZONTAL,false));
            //设置item的点击事件
            adapter.setOnSeckillRecyclerView(new SeckillRecyclerViewAdapter.OnSeckillRecyclerView() {
                @Override
                public void onItemClick(int position) {
                    Toast.makeText(mContext, "秒杀"+position, Toast.LENGTH_SHORT).show();

                    ResultBeanData.ResultBean.SeckillInfoBean.ListBean listBean = seckill_info.getList().get(position);


                    GoodsBean goodsBean = new GoodsBean();
                    goodsBean.setCover_price(listBean.getCover_price());
                    goodsBean.setFigure(listBean.getFigure());
                    goodsBean.setName(listBean.getName());
                    goodsBean.setProduct_id(listBean.getProduct_id());
                    startGoodsInfoActivity(goodsBean);
                }
            });


            //秒杀倒计时 -毫秒
            dt =   Integer.valueOf(seckill_info.getEnd_time()) - Integer.valueOf(seckill_info.getStart_time());


            handler.sendEmptyMessageDelayed(0,1000);//隔1秒后发送信息
        }
    }

    class ActViewHolder extends RecyclerView.ViewHolder {
        private Context mContext;
        private ViewPager act_viewpager;

        public ActViewHolder(Context mContext, View itemView) {
            super(itemView);
            this.mContext = mContext;
            act_viewpager = (ViewPager) itemView.findViewById(R.id.act_viewpager);
        }

        public void setData(final List<ResultBeanData.ResultBean.ActInfoBean> act_info) {
            //设置前后图片的间距
            act_viewpager.setPageMargin(20);
//            act_viewpager.setOffscreenPageLimit(3);//>=3
//
//      //setPageTransformer 决定动画效果
//            act_viewpager.setPageTransformer(true, new
//                    ScaleInTransformer());
            //1.有数据了
            //2.设置适配器，ViewPager类型自带适配器，但需要实现其中的方法
            act_viewpager.setAdapter(new PagerAdapter() {
                @Override
                public int getCount() {
                    return act_info.size();
                }

                /**
                 *
                 * @param view 页面
                 * @param object instantiateItem方法返回的值
                 * @return
                 */
                @Override
                public boolean isViewFromObject(View view, Object object) {
                    return view == object;
                }

                /**
                 *
                 * @param container ViewPager
                 * @param position 对应页面的位置
                 * @return
                 */
                @Override
                public Object instantiateItem(ViewGroup container, final int position) {
                    ImageView imageView = new ImageView(mContext);
                    imageView.setScaleType(ImageView.ScaleType.FIT_XY);

                    Glide.with(mContext).load(Constants.BASE_URL_IMAGE + act_info.get(position).getIcon_url()).into(imageView);
                    //添加到容器中
                    container.addView(imageView);

                    //设置点击事件
                    imageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
//                            Toast.makeText(mContext, "position=="+position, Toast.LENGTH_SHORT).show();
                        }
                    });


                    return imageView;
                }

                @Override
                public void destroyItem(ViewGroup container, int position, Object object) {
                    container.removeView((View) object);
                }
            });
        }
    }

    class ChannelViewHolder extends RecyclerView.ViewHolder {
        private Context mContext;
        private GridView gv_channel;
        private ChannelAdapter adapter;

        public ChannelViewHolder(final Context mContext, View itemView) {
            super(itemView);
            this.mContext = mContext;
            gv_channel = (GridView) itemView.findViewById(R.id.gv_channel);

            //设置item的点击事件
            gv_channel.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView parent, View view, int position, long id) {
                    Toast.makeText(mContext, "position" + position, Toast.LENGTH_SHORT).show();
                }
            });
        }

        public void setData(List<ResultBeanData.ResultBean.ChannelInfoBean> channel_info) {
            //得到数据了
            //设置GridView的适配器
            adapter = new ChannelAdapter(mContext, channel_info);
            gv_channel.setAdapter(adapter);
        }
    }


    class BannerViewHolder extends RecyclerView.ViewHolder {
        private Context mContext;
        private ImageView banner;

        public BannerViewHolder(Context mContext, View itemView) {
            super(itemView);
            this.mContext = mContext;
            this.banner = (ImageView) itemView.findViewById(R.id.banner);
        }
        public void setData(){
            banner.setImageResource(R.drawable.banner1);
        }






    }

    /**
     * 启动商品信息列表页面
     * @param goodsBean
     */
    private void startGoodsInfoActivity(GoodsBean goodsBean) {
        Intent intent = new Intent(mContext, GoodsInfoActivity.class);
        //在intent中传递对象，需要将对象序列化
        intent.putExtra(GOODS_BEAN,goodsBean);
        mContext.startActivity(intent);
    }


    /**
     * 得到类型
     *
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(int position) {
        switch (position) {
            case BANNER:
                currentType = BANNER;
                break;
            case CHANNEL:
                currentType = CHANNEL;
                break;
            case ACT:
                currentType = ACT;
                break;
            case SECKILL:
                currentType = SECKILL;
                break;
            case RECOMMEND:
                currentType = RECOMMEND;
                break;
            case HOT:
                currentType = HOT;
                break;
        }
        return currentType;
    }

    /**
     * 总共有多少个item
     *
     * @return
     */
    @Override
    public int getItemCount() {
        //开发过程中从1-->6(布局的个数)
        return 6;
    }
}
