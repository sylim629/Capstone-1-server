package com.example.leanne.capstonedesign_1_;

import android.annotation.TargetApi;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

/**
 * Created by imsuyeon on 16. 4. 4..
 * 회원가입 후 뜨는 추가정보 입력 화면. 일생에 한번밖에 안뜨는 화면.ㅋ
 */
public class ExtraInfoActivity extends AppCompatActivity
        implements View.OnClickListener, DatePickerDialog.OnDateSetListener {
    DisplayMetrics displaymetrics = new DisplayMetrics();
    int screenWidth, screenHeight;
    private Button buttonMale;
    private Button buttonFemale;
    static boolean isFemaleClicked, isMaleClicked;

    private int year, month, day;
    private TextView textViewBirthday;

    EditText inputGPA;
    EditText inputExpMonths;

    Spinner spinnerMajor;
    Spinner spinnerGPA;
    Spinner spinnerCompType;
    Spinner spinnerCompDuty;
    Spinner spinnerWorkExp;

    private PopupWindow popupCompany;
    ArrayList<String> arrayListCompanies;
    TextView tvSelectedComp;
    TextView tvSelectedCompExp;
    String selectedCompany;
    private PopupWindow popupWindowUni;
    ArrayList<String> arrayListUni;
    TextView textViewUniSearch;
    String selectedUni;
    private PopupWindow popupWindowCert;
    TextView textViewAddCert;
    ArrayList<String> arrayListCerts;
    ArrayList<String> selectedCertList;
    ArrayList<TextView> newCertTextViews;
    boolean isAlreadyExists;    // in selectedCertList

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_extra_info);

        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        screenHeight = displaymetrics.heightPixels;
        screenWidth = displaymetrics.widthPixels;

        initView();
    }

    public void initView() {
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        buttonMale = (Button) findViewById(R.id.button_male);
        buttonFemale = (Button) findViewById(R.id.button_female);
        isMaleClicked = false;
        isFemaleClicked = false;

        inputGPA = (EditText) findViewById(R.id.input_gpa);
        inputExpMonths = (EditText) findViewById(R.id.comp_exp_months);

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
        tvSelectedCompExp = (TextView) findViewById(R.id.input_company_exp);
        tvSelectedCompExp.setOnClickListener(this);
        textViewAddCert = (TextView) findViewById(R.id.certif_input);
        textViewAddCert.setOnClickListener(this);

        arrayListCerts = new ArrayList<>();
        selectedCertList = new ArrayList<>();
        newCertTextViews = new ArrayList<>();

        spinnerMajor = (Spinner) findViewById(R.id.spinner_major);
        ArrayAdapter adapterMajor = ArrayAdapter.createFromResource(this, R.array.majors,
                android.R.layout.simple_spinner_item);
        adapterMajor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMajor.setAdapter(adapterMajor);

        spinnerGPA = (Spinner) findViewById(R.id.spinner_gpamax);
        ArrayAdapter adapterGPA = ArrayAdapter.createFromResource(this, R.array.max_gpa,
                android.R.layout.simple_spinner_item);
        adapterGPA.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerGPA.setAdapter(adapterGPA);

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

        spinnerWorkExp = (Spinner) findViewById(R.id.spinner_workexp_type);
        ArrayAdapter adapterWorkExp = ArrayAdapter.createFromResource(this, R.array.work_exp,
                android.R.layout.simple_spinner_item);
        adapterWorkExp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerWorkExp.setAdapter(adapterWorkExp);

        updateDisplay();
    }

    // attach to an onclick handler to show the date picker
    public void showDatePickerDialog() {
        DatePickerFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    // handle the date selected
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

    private void addFirstCertificate() {
        LayoutInflater inflater = (LayoutInflater) ExtraInfoActivity.this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View popupLayout = inflater.inflate(R.layout.activity_popup_certificate,
                (ViewGroup) findViewById(R.id.popup_element));
        popupWindowCert = new PopupWindow(popupLayout, screenWidth / 4 * 3, screenHeight / 4 * 3);
        popupWindowCert.showAtLocation(popupLayout, Gravity.CENTER, 0, 0);
        popupWindowCert.setFocusable(true);
        popupWindowCert.update();

	    final ListView listCerts = (ListView) popupLayout.findViewById(R.id.list_cert);
	    final ArrayAdapter<String> adapterCert = new ArrayAdapter<>(this,
			    android.R.layout.simple_list_item_single_choice);
	    listCerts.setAdapter(adapterCert);
	    listCerts.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        EditText editTextSearchCert = (EditText) popupLayout.findViewById(R.id.cert_name);
        final String inputCert = editTextSearchCert.getText().toString();
        Button buttonSearchCert = (Button) popupLayout.findViewById(R.id.button_search_cert);
        buttonSearchCert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                arrayListCerts.clear();
                // inputCert에 해당하는 자격증을 찾아서 arrayListCerts에 저장
                // temp
                arrayListCerts.add("정보처리기사");
                arrayListCerts.add("정보보안기사");
                arrayListCerts.add("정보보안산업기사");
                arrayListCerts.add("정보시스템감사사");
                arrayListCerts.add("정보처리산업기사");
                // end of temp
	            for (int i = 0; i < arrayListCerts.size(); i++) {
		            adapterCert.add(arrayListCerts.get(i));
	            }
	            adapterCert.notifyDataSetChanged();
	            listCerts.invalidateViews();
            }
        });
//        selectedCertList = new ArrayList<>();
        selectedCertList.add("");
        listCerts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedCertList.set(0, parent.getItemAtPosition(position).toString());
//                selectedCert = parent.getItemAtPosition(position).toString();
                Log.d("TAG", selectedCertList.get(0));
            }
        });

//        tvCertArray.add(textViewAddCert);
        Button buttonSaveCert = (Button) popupLayout.findViewById(R.id.button_save_cert);
        buttonSaveCert.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                if (Objects.equals(selectedCertList.get(0), "")) {
                    Toast.makeText(popupLayout.getContext(), "자격증을 입력해 주세요", Toast.LENGTH_SHORT).show();
                } else {
                    RelativeLayout baseLayout = (RelativeLayout) findViewById(R.id.layout_main);
                    TextView newInputText = new TextView(ExtraInfoActivity.this);
                    newInputText.setWidth(textViewAddCert.getWidth());
                    newInputText.setHeight(textViewAddCert.getHeight());
                    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                            RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                    params.addRule((RelativeLayout.ALIGN_PARENT_RIGHT));
                    params.addRule(RelativeLayout.BELOW, R.id.certif_input);
                    params.setMargins(0, 0, 0, 100);
                    newInputText.setBackgroundColor(Color.WHITE);
                    newInputText.setClickable(true);
                    newInputText.setHint(R.string.add);
                    newInputText.setPadding(30, 20, 20, 20);
                    newInputText.setHintTextColor(ContextCompat.getColor(getApplicationContext(), R.color.light_gray));
                    newInputText.setTextSize(20.f);
                    newInputText.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            addSecondCertificate();
                        }
                    });
                    newCertTextViews.add(newInputText);
                    baseLayout.addView(newInputText, params);

                    LinearLayout layoutBottom = (LinearLayout) findViewById(R.id.layout_bottom);
                    RelativeLayout.LayoutParams paramsBottom = new RelativeLayout.LayoutParams(
                            RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                    paramsBottom.addRule(RelativeLayout.BELOW, newInputText.getId());
                    paramsBottom.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
                    layoutBottom.setLayoutParams(paramsBottom);

                    textViewAddCert.setText(selectedCertList.get(0));
                    popupWindowCert.dismiss();
                }
            }
        });

        Button buttonCloseCertPopup = (Button) popupLayout.findViewById(R.id.button_cancel_cert);
        buttonCloseCertPopup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textViewAddCert.setText("-추가-");
                selectedCertList.set(0, "");
                popupWindowCert.dismiss();
            }
        });
    }

    private void addSecondCertificate() {
        LayoutInflater inflater = (LayoutInflater) ExtraInfoActivity.this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View popupLayout = inflater.inflate(R.layout.activity_popup_certificate,
                (ViewGroup) findViewById(R.id.popup_element));
        popupWindowCert = new PopupWindow(popupLayout, screenWidth / 4 * 3, screenHeight / 4 * 3);
        popupWindowCert.showAtLocation(popupLayout, Gravity.CENTER, 0, 0);
        popupWindowCert.setFocusable(true);
        popupWindowCert.update();

	    final ListView listCerts = (ListView) popupLayout.findViewById(R.id.list_cert);
	    final ArrayAdapter<String> adapterCert = new ArrayAdapter<>(this,
			    android.R.layout.simple_list_item_single_choice);
	    listCerts.setAdapter(adapterCert);
	    listCerts.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        //String inputCert = editTextSearchCert.getText().toString();
        Button buttonSearchCert = (Button) popupLayout.findViewById(R.id.button_search_cert);
        buttonSearchCert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                arrayListCerts.clear();
                // inputCert에 해당하는 자격증을 찾아서 arrayListCerts에 저장
                // temp
                arrayListCerts.add("정보처리기사");
                arrayListCerts.add("정보보안기사");
                arrayListCerts.add("정보보안산업기사");
                arrayListCerts.add("정보시스템감사사");
                arrayListCerts.add("정보처리산업기사");
                // end of temp
	            for (int i = 0; i < arrayListCerts.size(); i++) {
		            adapterCert.add(arrayListCerts.get(i));
	            }
	            adapterCert.notifyDataSetChanged();
	            listCerts.invalidateViews();
            }
        });
        selectedCertList.add("");
        isAlreadyExists = false;
        listCerts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @TargetApi(Build.VERSION_CODES.KITKAT)
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                for (int i = 0; i < selectedCertList.size(); i++) {
                    if (Objects.equals(selectedCertList.get(i), parent.getItemAtPosition(position).toString())) {
                        Toast.makeText(popupLayout.getContext(), "이미 추가 하셨습니다. 다시 시도해 주십시오.", Toast.LENGTH_SHORT).show();
                        isAlreadyExists = true;
                    }
//                    else {
//                        selectedCertList.set(1, parent.getItemAtPosition(position).toString());
//                        Log.d("TAG", selectedCertList.get(1));
//                    }
                }
                if (!isAlreadyExists) {
                    selectedCertList.set(1, parent.getItemAtPosition(position).toString());
                    Log.d("TAG", selectedCertList.get(1));
                }
            }
        });

        Button buttonSaveCert = (Button) popupLayout.findViewById(R.id.button_save_cert);
        buttonSaveCert.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                if (Objects.equals(selectedCertList.get(1), "")) {
                    Toast.makeText(popupLayout.getContext(), "자격증을 입력해 주세요", Toast.LENGTH_SHORT).show();
                } else {
                    RelativeLayout baseLayout = (RelativeLayout) findViewById(R.id.layout_main);
                    TextView newInputText = new TextView(ExtraInfoActivity.this);
                    newInputText.setWidth(textViewAddCert.getWidth());
                    newInputText.setHeight(textViewAddCert.getHeight());
                    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                            RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                    params.addRule((RelativeLayout.ALIGN_PARENT_RIGHT));
                    params.addRule(RelativeLayout.BELOW, newCertTextViews.get(0).getId());
                    params.setMargins(0, 0, 0, 100);
                    newInputText.setBackgroundColor(Color.WHITE);
                    newInputText.setClickable(true);
                    newInputText.setHint(R.string.add);
                    newInputText.setPadding(30, 20, 20, 20);
                    newInputText.setHintTextColor(ContextCompat.getColor(getApplicationContext(), R.color.light_gray));
                    newInputText.setTextSize(20.f);
                    newCertTextViews.add(newInputText);
                    baseLayout.addView(newInputText, params);

                    LinearLayout layoutBottom = (LinearLayout) findViewById(R.id.layout_bottom);
                    RelativeLayout.LayoutParams paramsBottom = new RelativeLayout.LayoutParams(
                            RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                    paramsBottom.addRule(RelativeLayout.BELOW, newInputText.getId());
                    paramsBottom.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
                    layoutBottom.setLayoutParams(paramsBottom);

                    newCertTextViews.get(0).setText(selectedCertList.get(1));
                    popupWindowCert.dismiss();
                }
            }
        });

        Button buttonCloseCertPopup = (Button) popupLayout.findViewById(R.id.button_cancel_cert);
        buttonCloseCertPopup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newCertTextViews.get(0).setText("-추가-");
                selectedCertList.set(1, "");
                popupWindowCert.dismiss();
            }
        });
    }

    private void addCompany(final boolean first) {
        LayoutInflater inflaterPopupComp = (LayoutInflater) ExtraInfoActivity.this
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

	    EditText editTextSearchComp = (EditText) viewCompany.findViewById(R.id.comp_name);
        String inputCompany = editTextSearchComp.getText().toString();
        arrayListCompanies = new ArrayList<>();
        Button buttonSearchComp = (Button) viewCompany.findViewById(R.id.button_search_comp);
        buttonSearchComp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                arrayListCompanies.clear();
//                inputCompany 값을 대학디비에서 찾는다
//                찾은 결과들은 arrayListCompanies에 저장
                // 일단 테스트를 위해 Apple로 지정
                arrayListCompanies.add("Apple");
	            for (int i = 0; i < arrayListCompanies.size(); i++) {
		            adapterCompany.add(arrayListCompanies.get(i));
	            }
	            adapterCompany.notifyDataSetChanged();
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
                    tvSelectedComp.setText(selectedCompany);
                else
                    tvSelectedCompExp.setText(selectedCompany);
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

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.input_company:
                addCompany(true);
                break;
            case R.id.uni_extra_input:
                LayoutInflater inflaterUni = (LayoutInflater) ExtraInfoActivity.this
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View layoutUni = inflaterUni.inflate(R.layout.activity_popup_university,
                        (ViewGroup) findViewById(R.id.popup_element));
                popupWindowUni = new PopupWindow(layoutUni, screenWidth / 4 * 3, screenHeight / 4 * 3);
                popupWindowUni.showAtLocation(layoutUni, Gravity.CENTER, 0, 0);
                popupWindowUni.setFocusable(true);
                popupWindowUni.update();

	            final ListView listViewUni = (ListView) layoutUni.findViewById(R.id.list_uni);
	            final ArrayAdapter<String> adapterUni = new ArrayAdapter<>(this,
			            android.R.layout.simple_list_item_single_choice);
	            listViewUni.setAdapter(adapterUni);
	            listViewUni.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

                EditText editTextSearchUni = (EditText) layoutUni.findViewById(R.id.uni_name);
                String[] inputUni = {editTextSearchUni.getText().toString()};
                arrayListUni = new ArrayList<>();
                Button buttonSearchUni = (Button) layoutUni.findViewById(R.id.button_search_uni);
                buttonSearchUni.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        arrayListUni.clear();
                        // inputUni 값을 대학디비에서 찾는다
                        // 찾은 결과들은 arrayListUni에 저장
                        // 일단 테스트를 위해 중앙대학교로 지정
                        arrayListUni.add("중앙대학교");
	                    for (int i = 0; i < arrayListUni.size(); i++) {
		                    adapterUni.add(arrayListUni.get(i));
	                    }
	                    adapterUni.notifyDataSetChanged();
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
//                showDialog(DATE_DIALOG_ID);
                showDatePickerDialog();
                break;
            case R.id.input_company_exp:
                addCompany(false);
                break;
            case R.id.certif_input:
                addFirstCertificate();
                break;
            case R.id.button_skip:

                // 디비에 null로 저장

                Intent intentHome = new Intent(this, HomeActivity.class);
                startActivity(intentHome);
                this.overridePendingTransition(R.anim.animation_enter_right2left,
                        R.anim.animation_leave_right2left);
                this.finish();
                break;
            case R.id.button_save:
                EditText editTextToeic = (EditText) findViewById(R.id.editText_toeic);
                String stringScore = editTextToeic.getText().toString();
                int toeicScore = 0;
                if (!Objects.equals(stringScore, "")) {
                    toeicScore = Integer.parseInt(stringScore);
                    if (toeicScore < 0 || toeicScore > 990) {
                        Toast.makeText(this, "올바른 토익점수를 입력해주세요.", Toast.LENGTH_LONG).show();
                        break;
                    }
                }

                Calendar calendar = Calendar.getInstance();
                int age = calendar.get(Calendar.YEAR) - year;

//                if (Objects.equals(textViewUniSearch.getText().toString(), "")
//                        || Objects.equals(spinnerMajor.getSelectedItem().toString(), "-선택-")
//                        || Objects.equals(inputGPA.getText().toString(), "")
//                        || Objects.equals(spinnerGPA.getSelectedItem().toString(), "-선택-")
//                        || Objects.equals(spinnerCompType.getSelectedItem().toString(), "-선택-")
//                        || Objects.equals(spinnerCompDuty.getSelectedItem().toString(), "-선택-")
//                        || Objects.equals(tvSelectedComp.getText().toString(), "")
//                        || (!isMaleClicked && !isFemaleClicked)
//                        || age == 0 || Objects.equals(spinnerWorkExp.getSelectedItem().toString(), "-선택-")
//                        || Objects.equals(tvSelectedCompExp.getText().toString(), "")) {
//                    Toast.makeText(this, "입력하지 않은 부분이 있습니다.", Toast.LENGTH_LONG).show();
//                    break;
//                }

                LoggedInUser.getLoggedinUser().setAge(age);
                Log.d("TAG", "" + LoggedInUser.getLoggedinUser().getAge());
                String certificates = "";
                for (int i = 0; i < selectedCertList.size(); i++) {
                    certificates = certificates.concat("|").concat(selectedCertList.get(i));
                }
                certificates = certificates.concat("|");
                LoggedInUser.getLoggedinUser().setCertifi(certificates);
                Log.d("TAG", "" + LoggedInUser.getLoggedinUser().getCertifi());
                LoggedInUser.getLoggedinUser().setCom_name(tvSelectedComp.getText().toString());
                Log.d("TAG", "" + LoggedInUser.getLoggedinUser().getCom_name());
                LoggedInUser.getLoggedinUser().setCom_type(spinnerCompType.getSelectedItem().toString());
                Log.d("TAG", "" + LoggedInUser.getLoggedinUser().getCom_type());
                LoggedInUser.getLoggedinUser().setDuty(spinnerCompDuty.getSelectedItem().toString());
                Log.d("TAG", "" + LoggedInUser.getLoggedinUser().getDuty());
                boolean gender = false;
                if (isFemaleClicked)
                    gender = true;
                if (isMaleClicked)
                    gender = false;
                LoggedInUser.getLoggedinUser().setGender(gender);
                Log.d("TAG", "" + LoggedInUser.getLoggedinUser().getGender());
                LoggedInUser.getLoggedinUser().setGPA(Double.parseDouble(inputGPA.getText().toString()));
                Log.d("TAG", "" + LoggedInUser.getLoggedinUser().getGPA());
                LoggedInUser.getLoggedinUser().setMajor(spinnerMajor.getSelectedItem().toString());
                Log.d("TAG", "" + LoggedInUser.getLoggedinUser().getMajor());
                LoggedInUser.getLoggedinUser().setMaxGPA(Double.parseDouble(spinnerGPA.getSelectedItem().toString()));
                Log.d("TAG", "" + LoggedInUser.getLoggedinUser().getMaxGPA());
                LoggedInUser.getLoggedinUser().setToeic(toeicScore);
                Log.d("TAG", "" + LoggedInUser.getLoggedinUser().getToeic());
                LoggedInUser.getLoggedinUser().setUniv(textViewUniSearch.getText().toString());
                Log.d("TAG", "" + LoggedInUser.getLoggedinUser().getUniv());
                String workExp = "";
                if (Objects.equals(spinnerWorkExp.getSelectedItem().toString(), "없음"))
                    LoggedInUser.getLoggedinUser().setCareer(null);
                else {
                    workExp = workExp.concat(spinnerWorkExp.getSelectedItem().toString()).concat("/")
                            .concat(tvSelectedCompExp.getText().toString()).concat("/")
                            .concat(inputExpMonths.getText().toString());
                    LoggedInUser.getLoggedinUser().setCareer(workExp);
                }
                Log.d("TAG", "" + LoggedInUser.getLoggedinUser().getCareer());
                String careerTemp = LoggedInUser.getLoggedinUser().getCareer();
                String careerToSend = "|";
                if(!careerTemp.equals("")) {
                    String[] tokens =careerTemp.split("/", 0);
                    for (String token : tokens) {
                        careerToSend += token + ":";    //Fav아이디들을 arrayList에 저장
                    }
                    careerToSend += "|";
                }

	            String userInfoUpdateMsg = "4;" + LoggedInUser.getLoggedinUser().getUniv() + ";" + LoggedInUser.getLoggedinUser().getMajor()
			            + ";" + LoggedInUser.getLoggedinUser().getCom_type() + ";" + LoggedInUser.getLoggedinUser().getDuty()
			            + ";" + LoggedInUser.getLoggedinUser().getCom_name() + ";" + LoggedInUser.getLoggedinUser().getGender()
			            + ";" + LoggedInUser.getLoggedinUser().getAge() + ";" + LoggedInUser.getLoggedinUser().getToeic()
			            + ";" + LoggedInUser.getLoggedinUser().getCertifi() + ";" + LoggedInUser.getLoggedinUser().getGPA()
			            + ";" + LoggedInUser.getLoggedinUser().getMaxGPA() + ";" + careerToSend + ";";
	            userInfoUpdateMsg = userInfoUpdateMsg.replace(";null", ";!");
	            while(userInfoUpdateMsg.contains(";;")){
		            userInfoUpdateMsg = userInfoUpdateMsg.replace(";;", ";!;");
	            }
                ////////////////////////////////////////////////////////////////////////////////////////////////////////
                 RequestMsgSender updateMsgSender = (RequestMsgSender) new RequestMsgSender()
                      .execute(userInfoUpdateMsg);
                //////////////////////////////////////////////////////////////////////////
                 String updateResult = null;
                 try {
                      updateResult = updateMsgSender.get();
                 } catch (InterruptedException | ExecutionException e) {
                      e.printStackTrace();
                 }
                ////////////////////////////////////////////////////////////////////////////////////////////////////////

                Toast.makeText(getApplicationContext(), "추가정보 입력 완료", Toast.LENGTH_LONG).show();
                Intent intentHome2 = new Intent(this, HomeActivity.class);
                startActivity(intentHome2);
                this.overridePendingTransition(R.anim.animation_enter_right2left,
                        R.anim.animation_leave_right2left);
                this.finish();
                break;
        }
    }

    @Override
    public void onBackPressed() {

    }
}
