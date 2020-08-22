package com.example.bienestarproveedores;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

public class HeaderLayout extends ConstraintLayout {

  private TextView headerTextView;
  private ImageView headerImageView;

  public HeaderLayout(Context context) {
    super(context);
  }

  public HeaderLayout(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public HeaderLayout(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }

  @Override
  protected void onFinishInflate() {
    super.onFinishInflate();
    headerImageView = findViewById(R.id.bienestarPLogo);
    headerTextView = findViewById(R.id.textView);
  }

  /**
   * Sets the description displayed on the header layout.
   */
  public void setDescription(String description) {
    headerTextView.setText(description);
  }

  /**
   * Sets the size of the text.
   * @param size the scaled pixel size.
   */
  public void setTextSize(float size) {
    headerTextView.setTextSize(size);
  }

  /**
   * Sets the scale of the header image.
   * @param scaleX: scale on the X axis.
   * @param scaleY: scale on the Y axis.
   */
  public void setImageScales(float scaleX, float scaleY) {
    headerImageView.setScaleX(scaleX);
    headerImageView.setScaleY(scaleY);
  }
}
