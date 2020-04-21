package com.example.search_friends;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mvp.BaseActivity;
import com.example.my_association.adapter.ManagerAdapter;
import com.example.user_store.R;
import com.example.user_store.R2;
import com.example.utils.LogUtil;
import com.example.utils.PhoneNumUtil;

import org.w3c.dom.Text;

import butterknife.BindView;

public class SearchFriendsActivity extends BaseActivity<SearchFriendsView, SearchFriendsPresenter> implements SearchFriendsView {


    @BindView(R2.id.search_friends_edit)
    EditText searchFriendsEdit;
    @BindView(R2.id.search_friends_text)
    TextView searchFriendsText;
    @BindView(R2.id.search_friends_rec)
    RecyclerView searchFriendsRec;

    @Override
    public int getLayoutId() {
        return R.layout.activity_search_friends;
    }

    @Override
    public void initData() {

    }

    @Override
    public void initClick() {
        searchFriendsText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ("取消".equals(searchFriendsText.getText().toString())) {
                    finish();
                } else {
//                    searchFriendsText.setText("取消");
//                    searchFriendsEdit.setText("");
                    if (!PhoneNumUtil.isMobileNO(searchFriendsEdit.getText().toString())) {
                        Toast.makeText(SearchFriendsActivity.this, "手机号格式不正确请检查手机号", Toast.LENGTH_SHORT).show();
                    } else {
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(SearchFriendsActivity.this, LinearLayoutManager.VERTICAL, false);
                        searchFriendsRec.setLayoutManager(linearLayoutManager);
                        presenter.searchFriend(searchFriendsEdit.getText().toString());
                    }
                }
            }
        });

        searchFriendsEdit.addTextChangedListener(new TextWatcher() {
            private String mBefore;// 用于记录变化前的文字
            private int mCursor;// 用于记录变化时光标的位置

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                mBefore = s.toString();
                mCursor = start;
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().contains(" ")) {
                    LogUtil.e("onTextChanged: 包含空格, 设置" + mBefore);
                    searchFriendsEdit.removeTextChangedListener(this);
                    searchFriendsEdit.setText(mBefore);
                    searchFriendsEdit.addTextChangedListener(this);
                    searchFriendsEdit.setSelection(mCursor);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (searchFriendsEdit.getText().length() == 0) {
                    searchFriendsText.setText("取消");
                } else {
                    searchFriendsText.setText("搜索");
                }
            }
        });
    }

    @Override
    public SearchFriendsView createView() {
        return this;
    }

    @Override
    public SearchFriendsPresenter createPresenter() {
        return new SearchFriendsPresenter(this);
    }

    @Override
    public void loadAdapter(ManagerAdapter managerAdapter) {
        searchFriendsRec.setAdapter(managerAdapter);
    }
}
