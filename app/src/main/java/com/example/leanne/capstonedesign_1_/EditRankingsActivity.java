package com.example.leanne.capstonedesign_1_;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
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
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

/**
 * Created by Chloe on 4/13/2016.
 * 맞춤형 랭킹 기준 수정 페이지
 */
public class EditRankingsActivity extends AppCompatActivity implements View.OnClickListener {
	DisplayMetrics displaymetrics = new DisplayMetrics();
	int screenWidth, screenHeight;

	private Button buttonMale;
	private Button buttonFemale;
	static boolean isFemaleClicked, isMaleClicked;

	private PopupWindow popupCompany;
	TextView tvCompSearch;
	ArrayList<String> arrayListCompanies;
	String selectedCompany;
	private PopupWindow popupUniversity;
	private TextView textViewUniSearch;
	ArrayList<String> arrayListUni;
	String selectedUni;

	Spinner spinnerMajor;
	Spinner spinnerCompType;
	Spinner spinnerCompDuty;

	EditText editTextAge;

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

		tvCompSearch = (TextView) findViewById(R.id.input_company);
		tvCompSearch.setOnClickListener(this);
		textViewUniSearch = (TextView) findViewById(R.id.uni_extra_input);
		textViewUniSearch.setOnClickListener(this);

		arrayListUni = new ArrayList<>();
		arrayListCompanies = new ArrayList<>();

		spinnerMajor = (Spinner) findViewById(R.id.spinner_major);
		ArrayAdapter adapterMajor = ArrayAdapter.createFromResource(this, R.array.majors,
				android.R.layout.simple_spinner_item);
		adapterMajor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinnerMajor.setAdapter(adapterMajor);

		spinnerCompType = (Spinner) findViewById(R.id.spinner_company_type);
		ArrayAdapter adapterCompType = ArrayAdapter.createFromResource(this, R.array.company_types,
				android.R.layout.simple_spinner_item);
		adapterCompType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinnerCompType.setAdapter(adapterCompType);

		spinnerCompDuty = (Spinner) findViewById(R.id.spinner_company_duty);
		ArrayAdapter adapterCompDuty = ArrayAdapter.createFromResource(this, R.array.company_duties,
				android.R.layout.simple_spinner_item);
		adapterCompDuty.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinnerCompDuty.setAdapter(adapterCompDuty);

		editTextAge = (EditText) findViewById(R.id.input_age);

		//////////////////////////////////////////////////////////////////////////////////////////
		// INITIALIZE 맞춤형 랭킹 기준
		//////////////////////////////////////////////////////////////////////////////////////////
		String s;
		s = LoggedInUser.getLoggedinUser().getSearch_Univ();
		if (!s.equals(""))
			textViewUniSearch.setText(s);
		s = LoggedInUser.getLoggedinUser().getSearch_Major();
		if (!s.equals("")) {
			spinnerMajor.setSelection(adapterMajor.getPosition(s));
		}
		s = LoggedInUser.getLoggedinUser().getSearch_Com_type();
		if (!s.equals(""))
			spinnerCompType.setSelection(adapterCompType.getPosition(s));
		s = LoggedInUser.getLoggedinUser().getSearch_Duty();
		if (!s.equals(""))
			spinnerCompDuty.setSelection(adapterCompDuty.getPosition(s));
		s = LoggedInUser.getLoggedinUser().getSearch_Com_name();
		if (!s.equals(""))
			tvCompSearch.setText(s);
		s = LoggedInUser.getLoggedinUser().getSearch_Gender() + "";
		if (!s.equals("")) {
			if (s.equals("false")) { // 남자면
				buttonMale.setBackgroundResource(R.drawable.button_border_after);
				buttonMale.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.mint));
			} else {  // 여자면
				buttonFemale.setBackgroundResource(R.drawable.button_border_after);
				buttonFemale.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.mint));
			}
		}
		s = LoggedInUser.getLoggedinUser().getSearch_Age() + "";
		if (!s.equals(""))
			editTextAge.setText(s);
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

		final ListView listView = (ListView) viewCompany.findViewById(R.id.list_company);
		final ArrayAdapter<String> adapterCompany = new ArrayAdapter<>(this,
				android.R.layout.simple_list_item_single_choice);
		listView.setAdapter(adapterCompany);
		listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

		final EditText editTextSearchComp = (EditText) viewCompany.findViewById(R.id.comp_name);
		Button buttonSearchComp = (Button) viewCompany.findViewById(R.id.button_search_comp);
		buttonSearchComp.setOnClickListener(new View.OnClickListener() {
			@TargetApi(Build.VERSION_CODES.KITKAT)
			@Override
			public void onClick(View v) {
				String inputComp = editTextSearchComp.getText().toString();
				Log.d("inputComp", inputComp);
				if (Objects.equals(inputComp, ""))
					inputComp = "!";
				arrayListCompanies.clear();
				adapterCompany.clear();
				RequestMsgSender companySearchMsgSender =
						(RequestMsgSender) new RequestMsgSender().execute("15;" + inputComp + ";");
				String compSearchResult = null;
				try {
					compSearchResult = companySearchMsgSender.get();
					Log.d("Reply MSG", compSearchResult);
				} catch (InterruptedException | ExecutionException e) {
					e.printStackTrace();
				}
				assert compSearchResult != null;
				String[] tokens = compSearchResult.split(";");
				Collections.addAll(arrayListCompanies, tokens);
				adapterCompany.notifyDataSetChanged();
				for (int i = 0; i < arrayListCompanies.size(); i++) {
					adapterCompany.add(arrayListCompanies.get(i));
				}
				listView.invalidateViews();
			}
		});

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
					tvCompSearch.setText(selectedCompany);
				else
					tvCompSearch.setText(selectedCompany);
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
				popupUniversity = new PopupWindow(layoutUni, screenWidth / 4 * 3, screenHeight / 4 * 3);
				popupUniversity.showAtLocation(layoutUni, Gravity.CENTER, 0, 0);
				popupUniversity.setFocusable(true);
				popupUniversity.update();

				final ListView listViewUni = (ListView) layoutUni.findViewById(R.id.list_uni);
				final ArrayAdapter<String> adapterUni = new ArrayAdapter<>(this,
						android.R.layout.simple_list_item_single_choice);
				listViewUni.setAdapter(adapterUni);
				listViewUni.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

				final EditText editTextSearchUni = (EditText) layoutUni.findViewById(R.id.uni_name);
				Button buttonSearchUni = (Button) layoutUni.findViewById(R.id.button_search_uni);
				buttonSearchUni.setOnClickListener(new View.OnClickListener() {
					@TargetApi(Build.VERSION_CODES.KITKAT)
					@Override
					public void onClick(View v) {
						String inputUni = editTextSearchUni.getText().toString();
						Log.d("inputUni", inputUni);
						if (Objects.equals(inputUni, ""))
							inputUni = "!";
						arrayListUni.clear();
						adapterUni.clear();
						// inputUni 값을 대학디비에서 찾는다
						RequestMsgSender uniSearchMsgSender =
								(RequestMsgSender) new RequestMsgSender().execute("11;" + inputUni + ";");
						String uniSearchResult = null;
						try {
							uniSearchResult = uniSearchMsgSender.get();
							Log.d("Reply MSG", uniSearchResult);
						} catch (InterruptedException | ExecutionException e) {
							e.printStackTrace();
						}
						// 찾은 결과들은 arrayListUni에 저장
						assert uniSearchResult != null;
						String[] tokens = uniSearchResult.split(";");
						Collections.addAll(arrayListUni, tokens);
						adapterUni.notifyDataSetChanged();
						for (int i = 0; i < arrayListUni.size(); i++) {
							adapterUni.add(arrayListUni.get(i));
						}
						listViewUni.invalidateViews();
					}
				});

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
						popupUniversity.dismiss();
					}
				});
				Button buttonCloseUniPopup = (Button) layoutUni.findViewById(R.id.button_cancel_uni);
				buttonCloseUniPopup.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						popupUniversity.dismiss();
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
			case R.id.button_edit_rankings_save:
				LoggedInUser.getLoggedinUser().setSearch_com_type(spinnerCompType.getSelectedItem().toString());
				LoggedInUser.getLoggedinUser().setSearch_duty(spinnerCompDuty.getSelectedItem().toString());
				LoggedInUser.getLoggedinUser().setSearch_com_name(tvCompSearch.getText().toString());
				boolean gender = false;
				if (isFemaleClicked)
					gender = true;
				if (isMaleClicked)
					gender = false;
				LoggedInUser.getLoggedinUser().setSearch_gender(gender);
				LoggedInUser.getLoggedinUser().setSearch_age(Integer.parseInt(editTextAge.getText().toString()));

				String infoUpdateMsg = "6;" + LoggedInUser.getLoggedinUser().getSearch_Major() + ";"
						+ LoggedInUser.getLoggedinUser().getSearch_Com_type() + ";"
						+ LoggedInUser.getLoggedinUser().getSearch_Duty() + ";"
						+ LoggedInUser.getLoggedinUser().getSearch_Com_name() + ";"
						+ LoggedInUser.getLoggedinUser().getSearch_Gender() + ";"
						+ LoggedInUser.getLoggedinUser().getSearch_Age() + ";"
						+ LoggedInUser.getLoggedinUser().getSearch_Univ() + ";";
				Log.d("infoUpdateMsg", infoUpdateMsg);

				while (infoUpdateMsg.contains(";;") || infoUpdateMsg.contains(";null") || infoUpdateMsg.contains("-선택-")) {
					infoUpdateMsg = infoUpdateMsg.replace(";;", ";!;");
					infoUpdateMsg = infoUpdateMsg.replace(";null", ";!");
					infoUpdateMsg = infoUpdateMsg.replace("-선택-", "!");
				}
				Log.d("infoUpdateMsg", infoUpdateMsg);

				RequestMsgSender updateMsgSender = (RequestMsgSender) new RequestMsgSender().execute(infoUpdateMsg);
				String updateResult;
				try {
					updateResult = updateMsgSender.get();
					Log.d("updateResult", updateResult);
				} catch (InterruptedException | ExecutionException e) {
					e.printStackTrace();
				}

				Toast.makeText(this, "저장 완료", Toast.LENGTH_SHORT).show();
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
