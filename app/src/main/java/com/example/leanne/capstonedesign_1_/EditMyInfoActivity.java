package com.example.leanne.capstonedesign_1_;

import android.annotation.TargetApi;
import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.TextureView;
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

/**
 * Created by Chloe on 4/12/2016.
 * 개인정보 수정 탭 화면
 */
public class EditMyInfoActivity extends AppCompatActivity
        implements View.OnClickListener {
    DisplayMetrics displaymetrics = new DisplayMetrics();
    int screenWidth, screenHeight;
    private Button buttonMale;
    private Button buttonFemale;
    static boolean isFemaleClicked, isMaleClicked;

    private int year, month, day;
    private TextView textViewBirthday;
    private EditText editTextGPA;
    private EditText editTextToeic;
    private EditText tvCompExpMonths;
    private PopupWindow popupCompany;
    private TextView tvSelectedComp;
    private TextView tvSelectedCompExp;
    private String selectedCompany;
    private TextView textViewAge;
    private PopupWindow popupWindowUni;
    private TextView textViewUniSearch;
    private TextView textViewCompSearch;
    private String selectedUni;
    private PopupWindow popupWindowCert;
    private TextView textViewAddCert;
    private ArrayList<String> arrayListCerts;
    private ArrayList<String> selectedCertList;
    private ArrayList<TextView> newCertTextViews;
    boolean isAlreadyExists;    // in selectedCertList
    private Spinner spinnerMajor, spinnerGPA, spinnerCompType, spinnerCompDuty, spinnerWorkExp;
    private String sendUniv, sendMajor, sendCompType, sendDuty, sendCompName, sendGender, sendAge, sendToeic, sendCert, sendGrade, sendGradeMax, typeCareer, typeCareerPlace, typeCareerDuration, sendCareer;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_myinfo);

        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        screenHeight = displaymetrics.heightPixels;
        screenWidth = displaymetrics.widthPixels;

        initView();
    }

    public void initView() {
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        Button saveEditMyInfo = (Button) findViewById(R.id.button_my_info_save);
        saveEditMyInfo.setOnClickListener(this);

        buttonMale = (Button) findViewById(R.id.button_male);
        buttonFemale = (Button) findViewById(R.id.button_female);
        isMaleClicked = false;
        isFemaleClicked = false;

  /*      final Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);*/

        textViewBirthday = (TextView) findViewById(R.id.text_birthday);
        textViewAge = (TextView) findViewById(R.id.age_extra);
        tvSelectedComp = (TextView) findViewById(R.id.input_company);
        tvSelectedComp.setOnClickListener(this);
        textViewUniSearch = (TextView) findViewById(R.id.uni_extra_input);
        textViewUniSearch.setOnClickListener(this);
        textViewCompSearch = (TextView) findViewById(R.id.text_company);
        tvSelectedCompExp = (TextView) findViewById(R.id.input_company_exp);
        tvCompExpMonths = (EditText) findViewById(R.id.comp_exp_months);
        tvSelectedCompExp.setOnClickListener(this);
        textViewAddCert = (TextView) findViewById(R.id.certif_input);
        textViewAddCert.setOnClickListener(this);
        editTextGPA = (EditText) findViewById(R.id.edit_text_gpa);
        editTextToeic = (EditText) findViewById(R.id.editText_toeic);


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

        //////////////////////////////////////////////////////////////////////////////////////////
        // INITIALIZE USER INFO
        //////////////////////////////////////////////////////////////////////////////////////////
        String getStr;
        TextView userName = (TextView) findViewById(R.id.text_user_name);
        userName.setText(LoggedInUser.getLoggedinUser().getUserName());
        TextView userID = (TextView) findViewById(R.id.text_user_id);
        userID.setText(LoggedInUser.getLoggedinUser().getId());
        // set uni textview
        getStr = LoggedInUser.getLoggedinUser().getUniv();
        if(!getStr.equals(""))
            textViewUniSearch.setText(LoggedInUser.getLoggedinUser().getUniv());
        // set major spinner
        getStr = LoggedInUser.getLoggedinUser().getMajor();
        if(!getStr.equals(""))
            spinnerMajor.setSelection(adapterMajor.getPosition(getStr));
        // set gpa
        getStr = LoggedInUser.getLoggedinUser().getGPA()+"";
        if(!getStr.equals(""))
            editTextGPA.setText(getStr);
        // set max gpa
        getStr = LoggedInUser.getLoggedinUser().getMaxGPA()+"";
        if(!getStr.equals(""))
            spinnerGPA.setSelection(adapterGPA.getPosition(getStr));
        // set wish comp type
        getStr = LoggedInUser.getLoggedinUser().getCom_type();
        if(!getStr.equals(""))
            spinnerCompType.setSelection(adapterCompType.getPosition(getStr));
        // set wish duty
        getStr = LoggedInUser.getLoggedinUser().getDuty();
        if(!getStr.equals(""))
            spinnerCompDuty.setSelection(adapterCompDuty.getPosition(getStr));
        // set wish comp name
        getStr = LoggedInUser.getLoggedinUser().getCom_name();
        if(!getStr.equals(""))
            tvSelectedComp.setText(getStr);
        // set gender
        getStr = LoggedInUser.getLoggedinUser().getGender()+"";
        if(!getStr.equals("")){
            if(getStr.equals("false")) { // 남자면
                buttonMale.setBackgroundResource(R.drawable.button_border_after);
                buttonMale.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.mint));
            }else{  // 여자면
                buttonFemale.setBackgroundResource(R.drawable.button_border_after);
                buttonFemale.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.mint));
            }
        }
        // set age
        getStr = LoggedInUser.getLoggedinUser().getAge()+"";
        if(!getStr.equals(""))
            textViewBirthday.setText(getStr);
        // set career
        getStr = LoggedInUser.getLoggedinUser().getCareer();
        if(!getStr.equals("")) {
            getStr = getStr.substring(1, getStr.length()-1);
            String[] tokens = getStr.split(":",0);
            spinnerWorkExp.setSelection(adapterWorkExp.getPosition(tokens[0]));
            tvSelectedCompExp.setText(tokens[1]);
            tvCompExpMonths.setText(tokens[2]);
        }
        // set toeic
        getStr = LoggedInUser.getLoggedinUser().getToeic()+"";
        if(!getStr.equals("")) {
            editTextToeic.setText(getStr);
        }
        // set certifications
        getStr = LoggedInUser.getLoggedinUser().getCertifi();
        if(!getStr.equals("")) {
            textViewAddCert.setText(getStr);
        }
    }

    private void addFirstCertificate() {
        LayoutInflater inflater = (LayoutInflater) EditMyInfoActivity.this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View popupLayout = inflater.inflate(R.layout.activity_popup_certificate,
                (ViewGroup) findViewById(R.id.popup_element));
        popupWindowCert = new PopupWindow(popupLayout, screenWidth / 4 * 3, screenHeight / 4 * 3);
        popupWindowCert.showAtLocation(popupLayout, Gravity.CENTER, 0, 0);
        popupWindowCert.setFocusable(true);
        popupWindowCert.update();

//        EditText editTextSearchCert = (EditText) popupLayout.findViewById(R.id.cert_name);
//        String inputCert = editTextSearchCert.getText().toString();
        Button buttonSearchCert = (Button) popupLayout.findViewById(R.id.button_search_cert);
        buttonSearchCert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // inputCert의 값과 유사한 결과를 arrayListCerts에서 찾아서 display!!!



            }
        });
        final ListView listCerts = (ListView) popupLayout.findViewById(R.id.list_cert);
        final ArrayAdapter<String> adapterCert = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_single_choice);
        listCerts.setAdapter(adapterCert);                  ////////////////////////////////// ERROR ////////////////////////
        for (int i = 0; i < arrayListCerts.size(); i++) {
            adapterCert.add(arrayListCerts.get(i));
        }
        listCerts.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

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
                    TextView newInputText = new TextView(EditMyInfoActivity.this);
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
        LayoutInflater inflater = (LayoutInflater) EditMyInfoActivity.this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View popupLayout = inflater.inflate(R.layout.activity_popup_certificate,
                (ViewGroup) findViewById(R.id.popup_element));
        popupWindowCert = new PopupWindow(popupLayout, screenWidth / 4 * 3, screenHeight / 4 * 3);
        popupWindowCert.showAtLocation(popupLayout, Gravity.CENTER, 0, 0);
        popupWindowCert.setFocusable(true);
        popupWindowCert.update();

//        String inputCert = editTextSearchCert.getText().toString();
        Button buttonSearchCert = (Button) popupLayout.findViewById(R.id.button_search_cert);
        buttonSearchCert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // inputCert의 값과 유사한 결과를 arrayListCerts에서 찾아서 display!!!
            }
        });
        final ListView listCerts = (ListView) popupLayout.findViewById(R.id.list_cert);
        final ArrayAdapter<String> adapterCert = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_single_choice);
        listCerts.setAdapter(adapterCert);
        for (int i = 0; i < arrayListCerts.size(); i++) {
            adapterCert.add(arrayListCerts.get(i));
        }
        listCerts.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

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
                    TextView newInputText = new TextView(EditMyInfoActivity.this);
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
        LayoutInflater inflaterPopupComp = (LayoutInflater) EditMyInfoActivity.this
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.input_company:
                addCompany(true);
                break;
            case R.id.uni_extra_input:
                LayoutInflater inflaterUni = (LayoutInflater) EditMyInfoActivity.this
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
            case R.id.input_company_exp:
                addCompany(false);
                break;
            case R.id.certif_input:
                addFirstCertificate();
                break;
            case R.id.button_my_info_save:
                getValuesToSend();
                saveToLoggedInUser();
                // if DB save successful
                Toast.makeText(this, "저장 완료", Toast.LENGTH_SHORT).show();
                // if DB save failure
                // Toast.makeText(this, "저장 실패", Toast.LENGTH_SHORT.show());
                onBackPressed();
                break;
        }
    }

    // get updated values from ui
    public void getValuesToSend() {
         sendUniv = textViewUniSearch.getText().toString();
         sendMajor = spinnerMajor.getSelectedItem().toString();
         sendCompType = spinnerCompType.getSelectedItem().toString();
         sendDuty = spinnerCompDuty.getSelectedItem().toString();
         sendCompName = tvSelectedComp.getText().toString();
         sendGender="";
        if(isMaleClicked == true)
            sendGender = "false";
        else
            sendGender = "true";
         sendAge = textViewAge.getText().toString();
         sendToeic = editTextToeic.getText().toString();
         sendCert = textViewAddCert.getText().toString();
         sendGrade = editTextGPA.getText().toString();
         sendGradeMax = spinnerGPA.getSelectedItem().toString();
         typeCareer = spinnerCompType.getSelectedItem().toString();
         typeCareerPlace = tvSelectedCompExp.getText().toString();
         typeCareerDuration = tvCompExpMonths.getText().toString();
         sendCareer = "|" + typeCareer + ":" + typeCareerPlace + ":" + typeCareerDuration + "|";
         String requestMsg = "4;" + sendUniv + ";" + sendMajor + ";" + sendCompType + ";" + sendDuty + ";" +
                 sendCompName + ";" + sendGender + ";" + sendAge + ";" + sendToeic + ";" + sendCert + ";" +
                 sendGrade + ";" + sendGradeMax + ";" + sendCareer;
        // send to server
    }

    // save updated info to save to logged in user
    public void saveToLoggedInUser() {
        LoggedInUser.getLoggedinUser().setUniv(sendUniv);
        LoggedInUser.getLoggedinUser().setUniv(sendMajor);
        LoggedInUser.getLoggedinUser().setUniv(sendCompType);
        LoggedInUser.getLoggedinUser().setUniv(sendDuty);
        LoggedInUser.getLoggedinUser().setUniv(sendCompName);
        LoggedInUser.getLoggedinUser().setUniv(sendGender);
        LoggedInUser.getLoggedinUser().setUniv(sendAge);
        LoggedInUser.getLoggedinUser().setUniv(sendToeic);
        LoggedInUser.getLoggedinUser().setUniv(sendCert);
        LoggedInUser.getLoggedinUser().setUniv(sendGrade);
        LoggedInUser.getLoggedinUser().setUniv(sendGradeMax);
        LoggedInUser.getLoggedinUser().setUniv(sendCareer);
    }

    @Override
    public void onBackPressed() {
        this.finish();
        overridePendingTransition(R.anim.animation_enter_left2right, R.anim.animation_leave_left2right);
    }
}
