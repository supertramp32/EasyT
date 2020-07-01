package com.seshra.everestcab.onboarding;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.seshra.everestcab.R;


public class OnboardingFragment extends Fragment {

    private static final String BACKGROUND_COLOR = "background_color";
    private static final String PAGE = "page";

    ImageView onBoardingImage;

    private int backgroundColor;
    private int page;

    public static OnboardingFragment newInstance(int page) {
        OnboardingFragment fragment = new OnboardingFragment();

        Bundle b = new Bundle();
        b.putInt(PAGE, page);

        fragment.setArguments(b);

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        backgroundColor = getArguments().getInt(BACKGROUND_COLOR);
        page = getArguments().getInt(PAGE);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        // Select a layout according to the current page
        int layoutResId;
        switch (page) {
            case 0:
                layoutResId = R.layout.fragment_layout_01;
                break;
            case 1:
                layoutResId = R.layout.fragment_layout_02;
                break;
            case 2:
                layoutResId = R.layout.fragment_layout_03;
                break;
            case 3:
                layoutResId = R.layout.fragment_layout_04;
                break;
            default:
                layoutResId = R.layout.fragment_layout_01;
                break;
        }

        // Inflate layout resource
        View view = getActivity().getLayoutInflater().inflate(layoutResId, container, false);

        // Set the current page index as the View's tag (used for PageTransformer)
        view.setTag(page);

        onBoardingImage = view.findViewById(R.id.onboardingImage);


        switch (page) {
            case 0:
                Glide.with(getActivity()).load(getResources().getDrawable(R.drawable.user_on_first)).into(onBoardingImage);
                break;
            case 1:
                Glide.with(getActivity()).load(getResources().getDrawable(R.drawable.user_second)).into(onBoardingImage);

                break;
            case 2:
                Glide.with(getActivity()).load(getResources().getDrawable(R.drawable.user_third)).into(onBoardingImage);

                break;

            case 3:
                Glide.with(getActivity()).load(getResources().getDrawable(R.drawable.user_last)).into(onBoardingImage);

                break;
            default:
                break;
        }

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        // Set the background color of the root view to the color specified in newInstance()
//        View background = view.findViewById(R.id.onboarding_fragment_bg);
//        background.setBackgroundColor(backgroundColor);
    }


}
