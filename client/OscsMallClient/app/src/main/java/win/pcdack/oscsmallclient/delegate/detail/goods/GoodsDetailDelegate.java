package win.pcdack.oscsmallclient.delegate.detail.goods;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ToxicBakery.viewpager.transforms.DefaultTransformer;
import com.bigkoo.convenientbanner.ConvenientBanner;
import com.joanzapata.iconify.widget.IconTextView;
import com.zzhoujay.richtext.RichText;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import cn.sharesdk.onekeyshare.OnekeyShare;
import me.yokeyword.fragmentation.anim.DefaultVerticalAnimator;
import me.yokeyword.fragmentation.anim.FragmentAnimator;
import win.pcdack.creamsoda_core.delegates.CreamSodaDelegate;
import win.pcdack.creamsoda_core.ui.banner.HolderCreator;
import win.pcdack.creamsoda_core.ui.loader.CreamSodaLoader;
import win.pcdack.creamsoda_core.ui.widget.CircleTextView;
import win.pcdack.oscsmallclient.R;
import win.pcdack.oscsmallclient.delegate.detail.goods.good_info.GoodsInfoLay;

/**
 * Created by pcdack on 17-11-26.
 *
 */

public class GoodsDetailDelegate extends CreamSodaDelegate implements AppBarLayout.OnOffsetChangedListener, GoodsDetailContract.View {
    private final static String TAG_PRODUCT_ID = "my_product_id";
    private int productId;
    private GoodsDetailContract.Presenter presenter;
    @BindView(R.id.goods_detail_toolbar)
    Toolbar mToolbar;
    @BindView(R.id.detail_banner)
    ConvenientBanner<String> banner;
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout toolbarLayout;
    @BindView(R.id.app_bar)
    AppBarLayout barLayout;
    @BindView(R.id.goods_detail_centent_tv)
    TextView goodsDetailTextView;
    @BindView(R.id.icon_shop_cart)
    IconTextView shopCartIcon;
    @BindView(R.id.shop_cart_count)
    CircleTextView myCircleTextView;
    @BindView(R.id.add_into_shopping_cart)
    Button addIntoCartBtn;
    @OnClick(R.id.add_into_shopping_cart)
    void onAddBtnClick(){
        presenter.addIntoCart();
    }
    @OnClick(R.id.icon_goods_back)
    void onBackIconClick() {
        getSupportDelegate().pop();
    }
    @OnClick(R.id.icon_share)
    void onSharedClick(){
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();
        oks.setTitle("标题");
        oks.setText("我是分享文本");
        oks.show(getContext());
    }

    public static GoodsDetailDelegate create(int goodsId) {
        final Bundle bundle = new Bundle();
        bundle.putInt(TAG_PRODUCT_ID, goodsId);
        final GoodsDetailDelegate goodsDetailDelegate = new GoodsDetailDelegate();
        goodsDetailDelegate.setArguments(bundle);
        return goodsDetailDelegate;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle myBundle = this.getArguments();
        productId = myBundle.getInt(TAG_PRODUCT_ID);
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_goods_detail;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {
        presenter = new GoodsDetailPresenter(this);
        toolbarLayout.setContentScrimColor(Color.WHITE);
        myCircleTextView.setVisibility(View.INVISIBLE);
        RichText.initCacheDir(getContext());
        barLayout.addOnOffsetChangedListener(this);
        presenter.initData(productId);
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void setLoadingStart() {
        CreamSodaLoader.showLoader(getProxyActivity());
    }

    @Override
    public void setLoadingFinish() {
        CreamSodaLoader.cancelLoader();
    }

    @Override
    public void setErrorInfo(int code, String info) {
        getProxyActivity().showErrorMassage(code, info);
    }

    @Override
    public void setWarningInfo() {
        getProxyActivity().showFailureMassage();
    }

    @Override
    public void initBanner(ArrayList<String> arrayList) {
        banner.setPages(new HolderCreator(), arrayList)
                .setPageIndicator(new int[]{R.drawable.dot_normal, R.drawable.dot_focus})
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL)
                .setPageTransformer(new DefaultTransformer())
                .startTurning(3000)
                .setCanLoop(true);
    }

    @Override
    public void initRichText(String content) {
        RichText.from(content).into(goodsDetailTextView);
    }

    @Override
    public void initGoodsInfo(GoodsInfoBean bean) {
        getSupportDelegate().loadRootFragment(R.id.frame_goods_info, GoodsInfoLay.create(bean));
    }

    @Override
    public void setCircleTextViewNum(int count) {
        myCircleTextView.setVisibility(View.VISIBLE);
        myCircleTextView.setText(String.valueOf(count));
    }

    @Override
    public FragmentAnimator onCreateFragmentAnimator() {
        return new DefaultVerticalAnimator();
    }
}
