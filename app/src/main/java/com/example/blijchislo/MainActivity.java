package com.example.blijchislo;

import android.app.Activity;
import android.os.Bundle;
import android.text.InputType;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends Activity {
    private static final int ARRAY_SIZE = 1_000;
    private static final int MIN_RANDOM_VALUE = 1;
    private static final int MAX_RANDOM_VALUE = 10_000;
    private static final int RANDOM_BOUND_OFFSET = 1;
    private static final int ROOT_PADDING_DP = 24;
    private static final int VIEW_SPACING_DP = 16;
    private static final int TITLE_TEXT_SIZE_SP = 22;
    private static final int BODY_TEXT_SIZE_SP = 18;

    private ClosestNumberFinder closestNumberFinder;
    private EditText numberInput;
    private TextView resultText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        closestNumberFinder = new ClosestNumberFinder(generateRandomNumbers());
        setContentView(createContentView());
    }

    private LinearLayout createContentView() {
        LinearLayout root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setGravity(Gravity.CENTER_HORIZONTAL);
        int rootPadding = dpToPx(ROOT_PADDING_DP);
        root.setPadding(rootPadding, rootPadding, rootPadding, rootPadding);

        TextView titleText = createTextView("Поиск ближайшего числа", TITLE_TEXT_SIZE_SP);
        numberInput = createNumberInput();
        Button searchButton = createSearchButton();
        resultText = createTextView("Введите целое число и нажмите кнопку", BODY_TEXT_SIZE_SP);

        root.addView(titleText, createLayoutParams());
        root.addView(numberInput, createLayoutParams());
        root.addView(searchButton, createLayoutParams());
        root.addView(resultText, createLayoutParams());

        return root;
    }

    private EditText createNumberInput() {
        EditText input = new EditText(this);
        input.setHint("Введите целое число");
        input.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_SIGNED);
        input.setSingleLine(true);
        return input;
    }

    private Button createSearchButton() {
        Button button = new Button(this);
        button.setText("Найти ближайшее");
        button.setOnClickListener(view -> showClosestNumber());
        return button;
    }

    private TextView createTextView(String text, int textSizeSp) {
        TextView textView = new TextView(this);
        textView.setText(text);
        textView.setTextSize(textSizeSp);
        textView.setGravity(Gravity.CENTER);
        return textView;
    }

    private LinearLayout.LayoutParams createLayoutParams() {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(0, 0, 0, dpToPx(VIEW_SPACING_DP));
        return params;
    }

    private void showClosestNumber() {
        String inputText = numberInput.getText().toString().trim();
        if (inputText.isEmpty()) {
            resultText.setText("Введите целое число");
            return;
        }

        try {
            int target = Integer.parseInt(inputText);
            int closestNumber = closestNumberFinder.findClosest(target);
            resultText.setText("Ближайшее число: " + closestNumber);
        } catch (NumberFormatException exception) {
            resultText.setText("Число выходит за допустимый диапазон");
        }
    }

    private int[] generateRandomNumbers() {
        int[] numbers = new int[ARRAY_SIZE];
        Random random = new Random();
        int randomBound = MAX_RANDOM_VALUE - MIN_RANDOM_VALUE + RANDOM_BOUND_OFFSET;

        for (int index = 0; index < numbers.length; index++) {
            numbers[index] = random.nextInt(randomBound) + MIN_RANDOM_VALUE;
        }

        return numbers;
    }

    private int dpToPx(int dp) {
        float density = getResources().getDisplayMetrics().density;
        return Math.round(dp * density);
    }
}
