package com.example.leanne.capstonedesign_1_;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.media.Image;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.logging.LoggingMXBean;

/**
 * Created by Chloe on 4/13/2016.
 */
public class ListViewDemoAdaptor extends ArrayAdapter<ListViewItem> {

    private LoggedInUser loggedInUser;
    private String updateFavs;
    private String deleteFavs;

    public ListViewDemoAdaptor(Context context, List<ListViewItem> items) {
        super(context, R.layout.listview_item, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;

        if (convertView == null) {
            // inflate the GridView item layout
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.listview_item, parent, false);

            // initialize the view holder
            viewHolder = new ViewHolder();
            viewHolder.ranking = (TextView) convertView.findViewById(R.id.ranking_num);
            viewHolder.id = (TextView) convertView.findViewById(R.id.rankings_id);
            viewHolder.major = (TextView) convertView.findViewById(R.id.rankings_major);
            viewHolder.wish_duty = (TextView) convertView.findViewById(R.id.rankings_wish_duty);
            viewHolder.certificates = (TextView) convertView.findViewById(R.id.rankings_certificates);
            viewHolder.toeicScore = (TextView) convertView.findViewById(R.id.rankings_toeicScore);
            viewHolder.fav = (ImageView) convertView.findViewById(R.id.heart_white);

            convertView.setTag(viewHolder);
        } else {
            // recycle the already inflated view
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // update the item view
        final ListViewItem item = getItem(position);
        viewHolder.ranking.setText(Integer.toString(position + 1));
        viewHolder.id.setText(item.getId());
        viewHolder.major.setText(item.getMajor());
        viewHolder.wish_duty.setText(item.getWish_duty());
        viewHolder.certificates.setText(item.getCertificates());
        viewHolder.toeicScore.setText(item.getToeicScore());

        if (item.getIsFav() == true)
            viewHolder.fav.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.heart_red));
        else
            viewHolder.fav.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.heart_white));

        viewHolder.fav.setOnClickListener(new View.OnClickListener() {  // 하트 클릭 리스너
            @Override
            public void onClick(View v) {
                if (item.getIsFav() == false) {  // 흰 하트일 때
                    viewHolder.fav.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.heart_red));    // 빨간 줄 뜨면 무시..
                    item.setIsFav(true);
                    LoggedInUser.getLoggedinUser().setFav_ids(item.getId().substring(5));   // 앞에 있는 "ID : " 빼고 나머지 아이디 부분만 저장
                    Toast.makeText(getContext(), "clicked white heart:" + item.getId() + "!", Toast.LENGTH_SHORT).show();
                    String cutId = item.getId().substring(5);
                    updateFavs = "7;add;" + cutId;
                    //-------------------------
                    RequestMsgSender favsMsgSender = (RequestMsgSender) new RequestMsgSender().execute(updateFavs);
                    String favAddResult = null;
                    try {
                        favAddResult = favsMsgSender.get();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }
                    if(favAddResult.equals(false)) {
                        Toast.makeText(getContext(), "실패", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(getContext(), "성공", Toast.LENGTH_SHORT).show();
                    }
                    //-------------------------
                } else {   // 빨간 하트일 때
                    viewHolder.fav.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.heart_white));  // 빨간 줄 뜨면 무시..
                    item.setIsFav(false);
                    LoggedInUser.getLoggedinUser().deleteFav_id(item.getId().substring(5));    // 앞에 있는 "ID : " 뺸 아이디 부분 찾아서 삭제
                    Toast.makeText(getContext(), "clicked red heart:" + item.getId() + "!", Toast.LENGTH_SHORT).show();
                    String cutId = item.getId().substring(5);
                    deleteFavs = "7;del;" + cutId;
                    //-------------------------
                    RequestMsgSender favsMsgSender = (RequestMsgSender) new RequestMsgSender().execute(deleteFavs);
                    String favDelResult = null;
                    try {
                        favDelResult = favsMsgSender.get();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }
                    if(favDelResult.equals(false)) {
                        Toast.makeText(getContext(), "실패", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(getContext(), "성공", Toast.LENGTH_SHORT).show();
                    }
                    //-------------------------*/
                }
            }
        });
        return convertView;
    }

    private static class ViewHolder {
        TextView ranking, id, major, wish_duty, certificates, toeicScore;
        ImageView fav;
    }
}