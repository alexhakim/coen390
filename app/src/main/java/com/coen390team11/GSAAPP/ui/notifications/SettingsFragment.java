package com.coen390team11.GSAAPP.ui.notifications;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.coen390team11.GSAAPP.R;
import com.coen390team11.GSAAPP.databinding.FragmentSettingsBinding;
import com.google.android.material.textfield.TextInputLayout;

public class SettingsFragment extends Fragment {

    private SettingsViewModel notificationsViewModel;
    private FragmentSettingsBinding binding;

    TextInputLayout getNameEditText;
    TextInputLayout getEmailEditText;
    TextInputLayout getPhoneNumberEditText;
    TextInputLayout CartlyCardNumberEditText;
    Spinner changeLanguageSpinner;
    Button saveSettingsButton;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel =
                new ViewModelProvider(this).get(SettingsViewModel.class);

        binding = FragmentSettingsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        changeLanguageSpinner = binding.changeLanguageSpinner;
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.languages, android.R.layout.simple_list_item_1);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        changeLanguageSpinner.setAdapter(adapter);
        //changeLanguageSpinner.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) getContext());

        getNameEditText = binding.getNameEditText;
        notificationsViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                SharedPreferences sharedPreferences = getContext().getSharedPreferences("settings_fragment_info",Context.MODE_PRIVATE);
                String passName = sharedPreferences.getString("get_name","");
                getNameEditText.getEditText().setText(passName);
                //getNameEditText.getEditText().setTextColor(Color.parseColor("#000000"));
            }
        });

        getEmailEditText = binding.getEmailEditText;
        notificationsViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                SharedPreferences sharedPreferences = getContext().getSharedPreferences("settings_fragment_info",Context.MODE_PRIVATE);
                String passEmail = sharedPreferences.getString("get_email","");
                getEmailEditText.getEditText().setText(passEmail);
                //getEmailEditText.getEditText().setTextColor(Color.parseColor("#000000"));
            }
        });

        getPhoneNumberEditText = binding.getPhoneNumberEditText;
        notificationsViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                SharedPreferences sharedPreferences = getContext().getSharedPreferences("settings_fragment_info",Context.MODE_PRIVATE);
                Long passPhone = sharedPreferences.getLong("get_phone",0);
                getPhoneNumberEditText.getEditText().setText(passPhone + "");
            }
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}