package nfnlab.in.linkpreview;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.net.Uri;
import android.support.annotation.ColorInt;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

/**
 * Created by arun on 23/1/18.
 */

public class LinkPreview extends RelativeLayout {
     Context context;
     ImageView ivLink;
     CardView cvImage;
     TextView tvLinkTitle,tvLinkDesc,tvLinkUrl;
     LinearLayout ll;
     CardView cardView;
     @ColorInt
     int titleColor,urlColor,descColor,progressColor;


    public LinkPreview(Context context) {
        super(context);
        this.context = context;
        setView();
    }

    public LinkPreview(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.LinkPreview);
        titleColor = ta.getColor(R.styleable.LinkPreview_titleColor, Color.BLACK);
        urlColor = ta.getColor(R.styleable.LinkPreview_baseurlColor, Color.BLACK);
        descColor = ta.getColor(R.styleable.LinkPreview_descColor, Color.BLACK);
        progressColor = ta.getColor(R.styleable.LinkPreview_progressColor, getContext().getResources().getColor(R.color.progress));
        ta.recycle();
        setView();
    }

    public LinkPreview(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.LinkPreview);
        titleColor = ta.getColor(R.styleable.LinkPreview_titleColor, Color.BLACK);
        urlColor = ta.getColor(R.styleable.LinkPreview_baseurlColor, Color.BLACK);
        descColor = ta.getColor(R.styleable.LinkPreview_descColor, Color.BLACK);
        progressColor = ta.getColor(R.styleable.LinkPreview_progressColor, getContext().getResources().getColor(R.color.progress));
        ta.recycle();
        setView();
    }

    public void setView()
    {
        inflate(context, R.layout.preview_layout,this);
        tvLinkTitle=(TextView)findViewById(R.id.tvLinkTitle);
        tvLinkDesc=(TextView)findViewById(R.id.tvLinkDesc);
        tvLinkUrl=(TextView)findViewById(R.id.tvLinkUrl);
        ivLink=(ImageView)findViewById(R.id.ivLink);
        cvImage=(CardView)findViewById(R.id.cv_image);
        ll=(LinearLayout)findViewById(R.id.ll_linkdata);
        cardView=(CardView)findViewById(R.id.cv_linkPreview);
        cvImage.setVisibility(GONE);
        setUserStyle();

    }

    public void setUserStyle(){
        tvLinkTitle.setTextColor(titleColor);
        tvLinkDesc.setTextColor(descColor);
        tvLinkUrl.setTextColor(urlColor);

    }

    public void setLink(final String url){
        new PreviewData(url, new MetaDataResponse() {
            @Override
            public void linkDataResponse(LinkData linkData) {
                ll.setVisibility(VISIBLE);
                tvLinkDesc.setText(linkData.getDesc());
                tvLinkTitle.setText(linkData.getTitle());
                Toast.makeText(context, ""+linkData.getBaseUrl(), Toast.LENGTH_SHORT).show();
                tvLinkUrl.setText(linkData.getBaseUrl());
                if(linkData.imageUrl!=null) {
                    cvImage.setVisibility(VISIBLE);
                    Glide.with(context)
                            .load(linkData.imageUrl)
                            .into(ivLink);
                }

            }

            @Override
            public void Error(String Message) {
                Toast.makeText(context,""+Message,Toast.LENGTH_LONG).show();
            }
        });

        cardView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if(url!=null){
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(url));
                    context.startActivity(i);
                }

            }
        });
    }

}
