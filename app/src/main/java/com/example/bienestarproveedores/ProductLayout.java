package com.example.bienestarproveedores;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

public class ProductLayout extends CardView {

  private TextView descriptionTextView;
  private ImageView iconImageView;

  public ProductLayout(Context context) {
    super(context);
  }

  public ProductLayout(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public ProductLayout(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }

  @Override
  protected void onFinishInflate() {
    super.onFinishInflate();
    iconImageView = findViewById(R.id.product_image);
    descriptionTextView = findViewById(R.id.product_description);
  }

  /**
   * Sets the description displayed on the layout.
   */
  public void setDescription(String description) {
    descriptionTextView.setText(description);
  }

  /**
   * Sets the product's image resource.
   */
  public void setImageResource(int resource) {
    iconImageView.setImageResource(resource);
  }

}
