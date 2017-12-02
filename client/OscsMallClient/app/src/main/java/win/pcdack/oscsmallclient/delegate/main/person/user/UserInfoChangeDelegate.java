package win.pcdack.oscsmallclient.delegate.main.person.user;

import android.content.DialogInterface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import win.pcdack.oscsmallclient.GlobalUrlVal;
import win.pcdack.creamsoda_core.delegates.CreamSodaDelegate;
import win.pcdack.creamsoda_core.net.RestClient;
import win.pcdack.creamsoda_core.net.callback.IError;
import win.pcdack.creamsoda_core.net.callback.IFailure;
import win.pcdack.creamsoda_core.net.callback.ISuccess;
import win.pcdack.creamsoda_core.util.callback.CallBackManager;
import win.pcdack.creamsoda_core.util.callback.CallBackType;
import win.pcdack.creamsoda_core.util.callback.IGlobalCallback;
import win.pcdack.creamsoda_core.util.image.ImageUtils;
import win.pcdack.ec.fast_dev.list.ListAdapter;
import win.pcdack.ec.fast_dev.list.ListBean;
import win.pcdack.ec.fast_dev.list.ListItemType;
import win.pcdack.oscsmallclient.R;
import win.pcdack.oscsmallclient.database.DatabaseManager;
import win.pcdack.oscsmallclient.database.UserProfile;
import win.pcdack.oscsmallclient.database.UserProfileDao;

import static win.pcdack.oscsmallclient.delegate.main.person.user.UserInfoChangeDelegate.type.AVATAR;

/**
 * Created by pcdack on 17-10-29.
 *
 */

public class UserInfoChangeDelegate extends CreamSodaDelegate {
    private List<ListBean> been = new ArrayList<>();
    private ListAdapter adapter;
    private String base64Code;
    @BindView(R.id.user_info_rv)
    RecyclerView userInfoRv;

    private void initRv() {
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        userInfoRv.setLayoutManager(manager);
    }

    @OnClick(R.id.user_info_change_back_icon)
    void onBackClick() {
        getSupportDelegate().pop();
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_user_info_change;
    }

    @OnClick(R.id.confirm_the_change_btn)
    void confirmBtnClick() {
        RestClient.builder()
                .url(GlobalUrlVal.UPDATE_INFORMATION)
                .params("avatar", base64Code)
                .params("username", been.get(1).getValue())
                .params("email", been.get(2).getValue())
                .params("phone", been.get(3).getValue())
                .loader(getContext())
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        //获取更新后的用户信息，然后更新本地数据库
                        //没有本地数据的APP，每次打开APP都请求API，获取信息
                        getProxyActivity().showInfoMassage("更新成功");
                        JSONObject object = JSON.parseObject(response).getJSONObject("data");
                        String msg = JSON.parseObject(response).getString("msg");
                        if (object != null) {
                            UserProfile userProfile = new UserProfile(object.getInteger("id")
                                    , object.getString("username")
                                    , object.getString("avatar")
                                    , object.getString("email")
                                    , object.getString("phone"));
                            UserProfileDao userProfileDao = DatabaseManager.getInstance().getDao();
                            userProfileDao.update(userProfile);
                        } else {
                            getProxyActivity().showErrorMassage(msg);
                        }

                    }
                })
                .error(new IError() {
                    @Override
                    public void onError(int errorCode, String errorMsg) {
                        getProxyActivity().showErrorMassage(errorCode, errorMsg);
                    }
                })
                .failure(new IFailure() {
                    @Override
                    public void onFailure() {
                        getProxyActivity().showFailureMassage();
                    }
                })
                .build()
                .post();
    }

    public enum type {
        AVATAR(0, "头像"),
        NAME(1, "用户名"),
        EMAIL(2, "email"),
        PHONE(3, "电话");
        private int code;
        private String des;

        type(int code, String des) {
            this.code = code;
            this.des = des;
        }

        public int getCode() {
            return code;
        }

        public String getDes() {
            return des;
        }
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {
        initRv();
        adapter = new ListAdapter(been);
        userInfoRv.setAdapter(adapter);
        RestClient.builder()
                .url(GlobalUrlVal.GET_INFORMATION)
                .loader(getContext())
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        JSONObject jsonObject = JSON.parseObject(response).getJSONObject("data");
//                        final int id = jsonObject.getInteger("id");
                        final String username = jsonObject.getString("username");
//                        final String password = jsonObject.getString("password");
                        final String email = jsonObject.getString("email");
                        final String phone = jsonObject.getString("phone");
//                        final String question = jsonObject.getString("question");
//                        final String answer = jsonObject.getString("answer");
                        final String avatar = jsonObject.getString("avatar");
                        ListBean avatarBean = new ListBean.Builder()
                                .setImageUrl(avatar)
                                .setItemType(ListItemType.ITEM_AVATAR)
                                .setText(AVATAR.getDes())
                                .setId(AVATAR.getCode())
                                .build();
                        ListBean userNameBean = new ListBean.Builder()
                                .setItemType(ListItemType.ITEM_NORMAL)
                                .setValue(username)
                                .setText(type.NAME.getDes())
                                .setId(type.NAME.getCode())
                                .build();
                        ListBean userEmail = new ListBean.Builder()
                                .setItemType(ListItemType.ITEM_NORMAL)
                                .setText(type.EMAIL.getDes())
                                .setValue(email)
                                .setId(type.EMAIL.getCode())
                                .build();
                        ListBean userPhone = new ListBean.Builder()
                                .setItemType(ListItemType.ITEM_NORMAL)
                                .setText(type.PHONE.getDes())
                                .setValue(phone)
                                .setId(type.PHONE.getCode())
                                .build();
                        been.add(avatarBean);
                        been.add(userNameBean);
                        been.add(userEmail);
                        been.add(userPhone);
                        adapter.notifyDataSetChanged();
                    }
                }).
                failure(new IFailure() {
                    @Override
                    public void onFailure() {
                        getProxyActivity().showFailureMassage();
                    }
                }).
                error(new IError() {
                    @Override
                    public void onError(int errorCode, String errorMsg) {
                        getProxyActivity().showErrorMassage(errorCode, errorMsg);
                    }
                })
                .build().post();

        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(final BaseQuickAdapter adapter, final View view, final int position) {
                if (been.size() > 0) {
                    final ListBean currentListBean = been.get(position);
                    final int id = currentListBean.getId();
                    if (id == AVATAR.getCode()) {
                        CallBackManager.getInstance().addCallback(CallBackType.ON_CROP, new IGlobalCallback<Uri>() {
                            @Override
                            public void executeCallback(@Nullable Uri args) {
                                final ImageView avatar =view.findViewById(R.id.arrow_with_avatar_imageview);
                                Glide.with(getContext())
                                        .load(args)
                                        .into(new SimpleTarget<Drawable>() {
                                            @Override
                                            public void onResourceReady(Drawable resource, Transition<? super Drawable> transition) {
                                                avatar.setImageDrawable(resource);
                                                base64Code = ImageUtils.putImg2Base64((BitmapDrawable) resource);
                                            }
                                        });


                            }
                        });
                        startCameraWithCheck();
                    } else if (id == type.NAME.getCode()) {
                        showDialog(type.NAME.getDes(), currentListBean, position);
                    } else if (id == type.EMAIL.getCode()) {
                        showDialog(type.EMAIL.getDes(), currentListBean, position);
                    } else if (id == type.PHONE.getCode()) {
                        showDialog(type.PHONE.getDes(), currentListBean, position);
                    }
                }

            }
        });
    }

    private void showDialog(String typeDes, final ListBean currentListBean, final int position) {
        final EditText myUserNameEt = new EditText(getContext());
        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext())
                .setView(myUserNameEt)
                .setTitle(typeDes)
                .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String value = myUserNameEt.getText().toString();
                        if (!TextUtils.isEmpty(value)) {
                            currentListBean.setmValue(value);
                            adapter.notifyItemChanged(position);
                        }
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        final AlertDialog dialog = builder.create();
        dialog.show();
    }
}
