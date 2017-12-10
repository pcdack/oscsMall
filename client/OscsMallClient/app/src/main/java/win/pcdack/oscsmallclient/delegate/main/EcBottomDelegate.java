package win.pcdack.oscsmallclient.delegate.main;

import android.graphics.Color;

import java.util.LinkedHashMap;

import win.pcdack.creamsoda_core.delegates.bottom.BaseBottomDelegates;
import win.pcdack.creamsoda_core.delegates.bottom.BottomItemDelegate;
import win.pcdack.creamsoda_core.delegates.bottom.BottomTabBean;
import win.pcdack.creamsoda_core.delegates.bottom.ItemBuilder;
import win.pcdack.oscsmallclient.delegate.main.cart.CartDelegate;
import win.pcdack.oscsmallclient.delegate.main.discover.DiscoverDelegate;
import win.pcdack.oscsmallclient.delegate.main.index.IndexDelegate;
import win.pcdack.oscsmallclient.delegate.main.person.PersonDelegate;
import win.pcdack.oscsmallclient.delegate.main.sort.SortDelegate;

/**
 * Created by pcdack on 17-10-21.
 *
 */

public class EcBottomDelegate extends BaseBottomDelegates {
    @Override
    public LinkedHashMap<BottomTabBean, BottomItemDelegate> setItem(ItemBuilder itemBuilder) {
        LinkedHashMap<BottomTabBean,BottomItemDelegate> items=new LinkedHashMap<>();
        items.put(new BottomTabBean("{fa-home}","主页"),new IndexDelegate());
        items.put(new BottomTabBean("{fa-sort}","分类"),new SortDelegate());
        items.put(new BottomTabBean("{fa-compass}","寻觅"),new DiscoverDelegate());
        items.put(new BottomTabBean("{fa-shopping-cart}","购物车"),new CartDelegate());
        items.put(new BottomTabBean("{fa-user}","我的"),new PersonDelegate());
        return itemBuilder.setItems(items).build();
    }

    @Override
    public int setClickColor() {
        return Color.parseColor("#FFFF5722");
    }

    @Override
    public int setIndexDelegate() {
        return 0;
    }
}
