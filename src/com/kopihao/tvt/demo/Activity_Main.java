package com.kopihao.tvt.demo;

import com.kopihao.tvt.R;
import com.view.plugin.tvt.CheckableTextView;
import com.view.plugin.tvt.YetAnotherCTV;
import com.view.plugin.tvt.CheckableTextView.OnStateChangeListener;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Toast;

/**
 * This project demo a TextView which able to persist its state<br>
 * This UI component is absolutely inherited from native TextView.<br>
 *
 * <u>Why use this</u><br>
 * Switch and toggle has their own unique UI to present <br>
 * However I need a UI component which<br>
 * looks like a Text<br>
 * changes BG with its state<br>
 * And tada... I wrote it naively.<br>
 * </p>
 *
 * @author Jasper
 *
 */
public class Activity_Main extends Activity {

	private CheckableTextView ctvDemo2;
	private CheckableTextView ctvDemo1;
	private YetAnotherCTV ctvDemo3;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ac_main);

		// initialize CTV programmatically
		ctvDemo1 = (CheckableTextView) findViewById(R.id.ctvDemo1);
		ctvDemo1.configText("EN", "EN");
		ctvDemo1.configChecked(Color.WHITE, Color.RED, 1, Color.BLACK, 1);
		ctvDemo1.configUnchecked(Color.BLACK, Color.GRAY, 0, Color.BLACK, 1);
		ctvDemo1.setOnStateChangeListener(oscl_ctvDemo);
		ctvDemo1.setChecked(true);

		// initialize CTV programmatically
		ctvDemo2 = (CheckableTextView) findViewById(R.id.ctvDemo2);
		ctvDemo2.configText("CH", "CH");
		ctvDemo2.configChecked(Color.WHITE, Color.RED, 1, Color.BLACK, 1);
		ctvDemo2.configUnchecked(Color.BLACK, Color.GRAY, 0, Color.BLACK, 1);
		ctvDemo2.setOnStateChangeListener(oscl_ctvDemo);
		ctvDemo2.setChecked(false);

		// ctvDemo1 and ctvDemo2 has conflict interest
		// something like radio button in radio group
		ctvDemo1.setConflictTo(ctvDemo2);
		ctvDemo2.setConflictTo(ctvDemo1);

		// initialize custom CTV programmatically
		ctvDemo3 = (YetAnotherCTV) findViewById(R.id.ctvDemo3);

		ctvDemo3.setOnStateChangeListener(new OnStateChangeListener() {
			@Override
			public void onStateChanged(CheckableTextView view, boolean isChecked) {
				Toast.makeText(getBaseContext(), view.getText() + "", Toast.LENGTH_SHORT).show();
			}
		});

	}

	OnStateChangeListener oscl_ctvDemo = new OnStateChangeListener() {
		@Override
		public void onStateChanged(CheckableTextView view, boolean isChecked) {
			if (isChecked)
				Toast.makeText(getBaseContext(), view.getText() + "", Toast.LENGTH_SHORT).show();
		}
	};

}
