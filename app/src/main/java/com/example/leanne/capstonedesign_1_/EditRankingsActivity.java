package com.example.leanne.capstonedesign_1_;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Chloe on 4/13/2016.
 * 맞춤형 랭킹 기준 수정 페이지
 */
public class EditRankingsActivity extends AppCompatActivity
		implements View.OnClickListener, DatePickerDialog.OnDateSetListener {
	DisplayMetrics displaymetrics = new DisplayMetrics();
	int screenWidth, screenHeight;

	private Button buttonMale;
	private Button buttonFemale;
	static boolean isFemaleClicked, isMaleClicked;

	private int year, month, day;
	private TextView textViewBirthday;

	private PopupWindow popupCompany;
	TextView tvSelectedComp;
	String selectedCompany;
	private PopupWindow popupWindowUni;
	private TextView textViewUniSearch;
	String selectedUni;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_rankings);

		getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
		screenHeight = displaymetrics.heightPixels;
		screenWidth = displaymetrics.widthPixels;

		initView();
	}

	private void initView() {
		this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

		Button editRankingsSave = (Button) findViewById(R.id.button_edit_rankings_save);
		editRankingsSave.setOnClickListener(this);

		buttonMale = (Button) findViewById(R.id.button_male);
		buttonFemale = (Button) findViewById(R.id.button_female);
		isMaleClicked = false;
		isFemaleClicked = false;

		final Calendar c = Calendar.getInstance();
		year = c.get(Calendar.YEAR);
		month = c.get(Calendar.MONTH);
		day = c.get(Calendar.DAY_OF_MONTH);

		textViewBirthday = (TextView) findViewById(R.id.text_birthday);
		textViewBirthday.setOnClickListener(this);
		tvSelectedComp = (TextView) findViewById(R.id.input_company);
		tvSelectedComp.setOnClickListener(this);
		textViewUniSearch = (TextView) findViewById(R.id.uni_extra_input);
		textViewUniSearch.setOnClickListener(this);

		Spinner spinnerMajor = (Spinner) findViewById(R.id.spinner_major);
		ArrayAdapter adapterMajor = ArrayAdapter.createFromResource(this, R.array.majors,
				android.R.layout.simple_spinner_item);
		adapterMajor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinnerMajor.setAdapter(adapterMajor);

		Spinner spinnerCompType = (Spinner) findViewById(R.id.spinner_company_type);
		ArrayAdapter adapterCompType = ArrayAdapter.createFromResource(this, R.array.company_types,
				android.R.layout.simple_spinner_item);
		adapterCompType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinnerCompType.setAdapter(adapterCompType);

		Spinner spinnerCompDuty = (Spinner) findViewById(R.id.spinner_company_duty);
		ArrayAdapter adapterCompDuty = ArrayAdapter.createFromResource(this, R.array.company_duties,
				android.R.layout.simple_spinner_item);
		adapterCompDuty.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinnerCompDuty.setAdapter(adapterCompDuty);

		//////////////////////////////////////////////////////////////////////////////////////////
		// INITIALIZE 맞춤형 랭킹 기준
		//////////////////////////////////////////////////////////////////////////////////////////

		updateDisplay();
	}

	public void showDatePickerDialog() {
		DatePickerFragment newFragment = new DatePickerFragment();
		newFragment.show(getSupportFragmentManager(), "datePicker");
	}

	@Override
	public void onDateSet(DatePicker view, int setYear, int setMonth, int setDay) {
		year = setYear;
		month = setMonth;
		day = setDay;
		updateDisplay();
	}

	private void updateDisplay() {
		this.textViewBirthday.setText(
				new StringBuilder().append(year).append(".").append(month + 1).append(".").append(day));
	}

	private void addCompany(final boolean first) {
		LayoutInflater inflaterPopupComp = (LayoutInflater) EditRankingsActivity.this
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View viewCompany = inflaterPopupComp.inflate(R.layout.activity_popup_company,
				(ViewGroup) findViewById(R.id.popup_element));
		popupCompany = new PopupWindow(viewCompany, screenWidth / 4 * 3, screenHeight / 4 * 3);
		popupCompany.showAtLocation(viewCompany, Gravity.CENTER, 0, 0);
		popupCompany.setFocusable(true);
		popupCompany.update();

//        EditText editTextSearchComp = (EditText) viewCompany.findViewById(R.id.comp_name);
//        String inputCompany = editTextSearchComp.getText().toString();
		ArrayList<String> arrayListCompanies = new ArrayList<>();
		Button buttonSearchComp = (Button) viewCompany.findViewById(R.id.button_search_comp);
		buttonSearchComp.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
//                inputCompany 값을 대학디비에서 찾는다
//                찾은 결과들은 arrayListCompanies에 저장
			}
		});
		// 일단 테스트를 위해 Apple로 지정
		arrayListCompanies.add("Apple");
		ListView listView = (ListView) viewCompany.findViewById(R.id.list_company);
		ArrayAdapter<String> adapterCompany = new ArrayAdapter<>(this,
				android.R.layout.simple_list_item_single_choice);
		listView.setAdapter(adapterCompany);
		for (int i = 0; i < arrayListCompanies.size(); i++) {
			adapterCompany.add(arrayListCompanies.get(i));
		}
		listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				selectedCompany = parent.getItemAtPosition(position).toString();
				Log.d("TAG", selectedCompany);
			}
		});
		Button buttonSaveComp = (Button) viewCompany.findViewById(R.id.button_save_comp);
		buttonSaveComp.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (first)
					tvSelectedComp.setText(selectedCompany);
//				else
//					tvSelectedCompExp.setText(selectedCompany);
				selectedCompany = "";
				popupCompany.dismiss();
			}
		});
		Button buttonCloseCompPopup = (Button) viewCompany.findViewById(R.id.button_cancel_comp);
		buttonCloseCompPopup.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				selectedCompany = "";
				popupCompany.dismiss();
			}
		});
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.input_company:
				addCompany(true);
				break;
			case R.id.uni_extra_input:
				LayoutInflater inflaterUni = (LayoutInflater) EditRankingsActivity.this
						.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				View layoutUni = inflaterUni.inflate(R.layout.activity_popup_university,
						(ViewGroup) findViewById(R.id.popup_element));
				popupWindowUni = new PopupWindow(layoutUni, screenWidth / 4 * 3, screenHeight / 4 * 3);
				popupWindowUni.showAtLocation(layoutUni, Gravity.CENTER, 0, 0);
				popupWindowUni.setFocusable(true);
				popupWindowUni.update();

//                EditText editTextSearchUni = (EditText) layoutUni.findViewById(R.id.uni_name);
//                String inputUni = editTextSearchUni.getText().toString();
				ArrayList<String> arrayListUni = new ArrayList<>();
				Button buttonSearchUni = (Button) layoutUni.findViewById(R.id.button_search_uni);
				buttonSearchUni.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						// inputUni 값을 대학디비에서 찾는다
						// 찾은 결과들은 arrayListUni에 저장
					}
				});
				// 일단 테스트를 위해 중앙대학교로 지정
				arrayListUni.add("중앙대학교");
				final ListView listViewUni = (ListView) layoutUni.findViewById(R.id.list_uni);
				final ArrayAdapter<String> adapterUni = new ArrayAdapter<>(this,
						android.R.layout.simple_list_item_single_choice);
				listViewUni.setAdapter(adapterUni);
				for (int i = 0; i < arrayListUni.size(); i++) {
					adapterUni.add(arrayListUni.get(i));
				}
				listViewUni.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

				listViewUni.setOnItemClickListener(new AdapterView.OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
						selectedUni = parent.getItemAtPosition(position).toString();
						Log.d("TAG", selectedUni);
					}
				});
				Button buttonSaveUni = (Button) layoutUni.findViewById(R.id.button_choose_uni);
				buttonSaveUni.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						textViewUniSearch.setText(selectedUni);
						popupWindowUni.dismiss();
					}
				});
				Button buttonCloseUniPopup = (Button) layoutUni.findViewById(R.id.button_cancel_uni);
				buttonCloseUniPopup.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						popupWindowUni.dismiss();
					}
				});
				break;
			case R.id.button_male:
				if (buttonMale.getBackground() == buttonFemale.getBackground()) {
					buttonMale.setBackgroundResource(R.drawable.button_border_after);
					buttonMale.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.mint));
					isMaleClicked = true;
				} else if (buttonMale.getBackground() != buttonFemale.getBackground()) {
					buttonMale.setBackgroundResource(R.drawable.button_border_after);
					buttonMale.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.mint));
					isMaleClicked = true;
					buttonFemale.setBackgroundResource(R.drawable.button_boarder_before);
					buttonFemale.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.light_gray));
					isFemaleClicked = false;
				}
				break;
			case R.id.button_female:
				if (buttonMale.getBackground() == buttonFemale.getBackground()) {
					buttonFemale.setBackgroundResource(R.drawable.button_border_after);
					buttonFemale.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.mint));
					isFemaleClicked = true;
				} else if (buttonMale.getBackground() != buttonFemale.getBackground()) {
					buttonFemale.setBackgroundResource(R.drawable.button_border_after);
					buttonFemale.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.mint));
					isFemaleClicked = true;
					buttonMale.setBackgroundResource(R.drawable.button_boarder_before);
					buttonMale.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.light_gray));
					isMaleClicked = false;
				}
				break;
			case R.id.text_birthday:
				showDatePickerDialog();
				break;
			case R.id.button_edit_rankings_save:
				// save info on DB
				// if DB save success
				Toast.makeText(this, "저장 완료", Toast.LENGTH_SHORT).show();
				// if DB save failure
				// Toast.makeText(this, "저장 실패", Toast.LENGTH_SHORT.show());
				onBackPressed();
				break;
		}
	}

	@Override
	public void onBackPressed() {
		this.finish();
		overridePendingTransition(R.anim.animation_enter_left2right, R.anim.animation_leave_left2right);
	}
}
