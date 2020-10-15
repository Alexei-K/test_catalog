package com.kolis.test_catalog_app.ui.start_info;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewStub;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.kolis.test_catalog_app.MainActivity;
import com.kolis.test_catalog_app.R;

public class StartInfoLastFragment extends StartInfoFragment {

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView title = view.findViewById(R.id.title_info);
        title.setVisibility(View.INVISIBLE);
        ViewStub stub = view.findViewById(R.id.stub);
        stub.inflate();
        TextView skipButton = view.findViewById(R.id.skip_button);
        skipButton.setVisibility(View.VISIBLE);
        skipButton.setOnClickListener(v -> {
            startActivity(new Intent(v.getContext(), MainActivity.class));
            requireActivity().finish();
        });
    }
}
