package com.view.plugin.tvt;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.widget.Checkable;
import android.widget.TextView;

public class CheckableTextView extends TextView implements Checkable {

	protected static final int FLIP_WHATEVER = 1;
	protected static final int FLIP_NEGATIVE = 2;

	private int mFlipMode;
	private boolean mIsChecked = false;
	private int mActive_color = Color.DKGRAY;
	private int mActive_bg = Color.TRANSPARENT;
	private int mNormal_color = Color.BLACK;
	private int mNormal_bg = Color.TRANSPARENT;
	private String mActive_txt = "txt_on";
	private String mNormal_txt = "txt_off";
	private GradientDrawable mActive_drawable = null;
	private GradientDrawable mNormal_Drawable = null;
	private CheckableTextView[] mConflictCtv;
	private OnStateChangeListener mOnStateChangeListener;

	public CheckableTextView(Context context) {
		super(context);
		initView();
	}

	public CheckableTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView();
	}

	public CheckableTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initView();
	}

	public void initView() {
		setmFlipMode(FLIP_WHATEVER);
		setChecked(false);
		setClickable(true);
		setEnabled(true);
		configText(mNormal_txt, mActive_txt);
		configChecked(mActive_color, mActive_bg);
		configUnchecked(mNormal_color, mNormal_bg);
	}

	// ----------------$$$$$$$$$$$$$$$$$$$$$$---------------------------------

	public void configMode(int flip_mode) {
		mFlipMode = flip_mode;
	}

	public void configText(String txt_on, String txt_off) {
		mActive_txt = txt_on;
		mNormal_txt = txt_off;
		reflectChanges();
	}

	public void configChecked(int txt_color, int bg_color) {
		mActive_color = txt_color;
		mActive_bg = bg_color;
		reflectChanges();
	}

	public void configUnchecked(int txt_color, int bg_color) {
		mNormal_color = txt_color;
		mNormal_bg = bg_color;
		reflectChanges();
	}

	public void configChecked(int txt_color, int bg_color, float bg_corner, int border_color, int border_tickness) {
		mActive_drawable = new GradientDrawable();
		mActive_drawable.setColor(bg_color);
		mActive_drawable.setCornerRadius(bg_corner);
		mActive_drawable.setStroke(border_tickness, border_color);
		configChecked(txt_color, bg_color);
	}

	public void configUnchecked(int txt_color, int bg_color, float bg_corner, int border_color, int border_tickness) {
		mNormal_Drawable = new GradientDrawable();
		mNormal_Drawable.setColor(bg_color);
		mNormal_Drawable.setCornerRadius(bg_corner);
		mNormal_Drawable.setStroke(border_tickness, border_color);
		configUnchecked(txt_color, bg_color);
	}

	public void setConflictTo(final CheckableTextView... conflict) {
		mConflictCtv = conflict;
		setmFlipMode(FLIP_NEGATIVE);
		final OnStateChangeListener older = mOnStateChangeListener;
		final OnStateChangeListener newer = new OnStateChangeListener() {
			@Override
			public void onStateChanged(CheckableTextView view, boolean isChecked) {
				for (CheckableTextView ctv : conflict) {
					ctv.setChecked(!isChecked);
					if (older != null) {
						older.onStateChanged(view, isChecked);
					}
				}
			}
		};
		this.setOnStateChangeListener(newer);
	}

	public void setmFlipMode(int mFlipMode) {
		this.mFlipMode = mFlipMode;
	}

	// ----------------$$$$$$$$$$$$$$$$$$$$$$---------------------------------

	private void reflectChanges() {
		if (mIsChecked) {
			this.setText(mActive_txt);
			this.setTextColor(mActive_color);
			if (mActive_drawable != null) {
				this.setBackgroundDrawable(mActive_drawable);

			} else {
				this.setBackgroundColor(mActive_bg);
			}
		} else {
			this.setText(mNormal_txt);
			this.setTextColor(mNormal_color);
			if (mNormal_Drawable != null) {
				this.setBackgroundDrawable(mNormal_Drawable);
			} else {
				this.setBackgroundColor(mNormal_bg);
			}
		}
	}

	@Override
	public boolean performClick() {
		toggle();
		return super.performClick();
	}

	@Override
	public void toggle() {
		if (mFlipMode == FLIP_NEGATIVE) {
			if (mIsChecked == true) {
				return;
			}
		}
		setChecked(!mIsChecked);
	}

	@Override
	public void setChecked(boolean checked) {
		if (mIsChecked == checked) {
			return;
		}
		mIsChecked = checked;
		refreshDrawableState();
		reflectChanges();
		if (mOnStateChangeListener != null) {
			mOnStateChangeListener.onStateChanged(this, checked);
			try {
				for (CheckableTextView ctv : mConflictCtv) {
					ctv.setChecked(!checked);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public boolean isChecked() {
		return mIsChecked;
	}

	public void setOnStateChangeListener(OnStateChangeListener listener) {
		mOnStateChangeListener = listener;
	}

	public static interface OnStateChangeListener {
		public void onStateChanged(CheckableTextView view, boolean isChecked);
	}

}
