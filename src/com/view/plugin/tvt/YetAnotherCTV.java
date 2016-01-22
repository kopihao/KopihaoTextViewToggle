package com.view.plugin.tvt;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;

public class YetAnotherCTV extends CheckableTextView {

	public YetAnotherCTV(final Context context) {
		super(context);
	}

	public YetAnotherCTV(final Context context, final AttributeSet attrs) {
		super(context, attrs);
	}

	public YetAnotherCTV(final Context context, final AttributeSet attrs, final int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	public void initView() {
		super.initView();
		this.configText("YES", "NAY");
		this.configChecked(Color.WHITE, Color.GREEN, 1, Color.BLACK, 1);
		this.configUnchecked(Color.GREEN, Color.WHITE, 0, Color.BLACK, 1);
	}

}
