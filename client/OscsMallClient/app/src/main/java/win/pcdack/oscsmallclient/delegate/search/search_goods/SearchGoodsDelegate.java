package win.pcdack.oscsmallclient.delegate.search.search_goods;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;

import win.pcdack.creamsoda_core.ui.loader.CreamSodaLoader;
import win.pcdack.creamsoda_core.ui.recycler.BaseDecoration;
import win.pcdack.creamsoda_core.ui.recycler.MultipleItemEntity;
import win.pcdack.ec.fast_dev.delegate.normalDelegateWithBackIcon.NormalDelegateWithBackIcon;
import win.pcdack.oscsmallclient.R;

/**
 * Created by pcdack on 17-11-26.
 *
 */

public class SearchGoodsDelegate extends NormalDelegateWithBackIcon implements SearchGoodsContract.View{
    private final static String KEY_WORD="key_word";
    private final static String CATEGORY_ID="category_id";

    private String myKeyWord;
    private int categoryId;
    private win.pcdack.oscsmallclient.delegate.search.SearchAdapter adapter;
    private SearchGoodsPresenter presenter;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle myBundle = this.getArguments();
        myKeyWord = myBundle.getString(KEY_WORD);
        categoryId= myBundle.getInt(CATEGORY_ID);
    }

    public static SearchGoodsDelegate create(String keyWords) {
        final Bundle bundle = new Bundle();
        bundle.putString(KEY_WORD, keyWords);
        bundle.putInt(CATEGORY_ID,0);
        final SearchGoodsDelegate searchGoodsDelegate = new SearchGoodsDelegate();
        searchGoodsDelegate.setArguments(bundle);
        return searchGoodsDelegate;
    }
    public static SearchGoodsDelegate create(String keyWords,int categoryId) {
        final Bundle bundle = new Bundle();
        bundle.putString(KEY_WORD, keyWords);
        bundle.putInt(CATEGORY_ID,categoryId);
        final SearchGoodsDelegate searchGoodsDelegate = new SearchGoodsDelegate();
        searchGoodsDelegate.setArguments(bundle);
        return searchGoodsDelegate;
    }

    @Override
    protected String titleName() {
        return "逛一逛";
    }

    @Override
    protected int barColor() {
        return ContextCompat.getColor(getContext(), R.color.background_color);
    }

    @Override
    protected void initRecycleView(RecyclerView recyclerView) {
        LinearLayoutManager manager=new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(manager);
        recyclerView.addItemDecoration(new BaseDecoration(ContextCompat.getColor(getContext(),R.color.gray),3));
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {
    super.onBindView(savedInstanceState, rootView);
        presenter=new SearchGoodsPresenter(this);
        if (categoryId == 0){
            presenter.initSearchByKeyWord(myKeyWord);
        }else {
            presenter.initSearchByKeyWordAndCategory(myKeyWord,categoryId);
        }

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
    public void setData(ArrayList<MultipleItemEntity> entities) {
        if (entities.size()>0) {
            adapter = new win.pcdack.oscsmallclient.delegate.search.SearchAdapter(entities);
            getRecyclerView().setAdapter(adapter);
        }else if (entities.size()>=10){
            adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
                @Override
                public void onLoadMoreRequested() {

                }
            },getRecyclerView());
        }
    }
}
