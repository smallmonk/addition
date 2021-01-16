package com.proxedure.addition;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;

import com.proxedure.addition.databinding.ActivityAdditionBinding;

import java.util.Random;

public class AdditionActivity extends AppCompatActivity {
    private ActivityAdditionBinding binding;

    private static boolean isInit = false;
    private static int num1 = 0;
    private static int num2 = 0;

    public AdditionActivity() {
        if (!isInit) {
            newQuiz();
            isInit = true;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdditionBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        initListeners();

        updateQuizView();
    }

    private void initListeners() {
        binding.tvConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isCorrect = checkAnswer();
                showResultDialog(isCorrect);
            }
        });
    }

    private void newQuiz() {
        Random rand = new Random();

        int first = rand.nextInt(99);
        int second = rand.nextInt(99);

        if (second > first) {
            num1 = first;
            num2 = second - first;
        } else {
            num1 = second;
            num2 = first - second;
        }

    }

    private void updateQuizView() {
        binding.box1.setText(String.valueOf(num1));
        binding.box2.setText(String.valueOf(num2));
        binding.answer.setText("");
    }

    private boolean checkAnswer() {
        int box1 = Integer.parseInt(binding.box1.getText().toString());
        int box2 = Integer.parseInt(binding.box2.getText().toString());

        try {
            int answer = Integer.parseInt(binding.answer.getText().toString());

            if (binding.operator.getText().equals("+")) {
                if (box1 + box2 == answer) {
                    return true;
                }
            }

        } catch (Exception ex) {
            return false;
        }
        return false;
    }

    private void showResultDialog(final boolean isCorrect) {
        final String dialogMessage;
        final String positiveBtnText;
        if (isCorrect) {
            dialogMessage = getString(R.string.message_correct);
            positiveBtnText = getString(R.string.action_new);
        } else {
            dialogMessage = getString(R.string.message_incorrect);
            positiveBtnText = getString(R.string.action_retry);
        }

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(dialogMessage);
        builder.setPositiveButton(positiveBtnText, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                if (isCorrect) {
                    newQuiz();
                    updateQuizView();
                } else {
                    binding.answer.setText("");
                }
            }
        });

        builder.setNegativeButton(getString(R.string.action_exit), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                finishAffinity();
            }
        });
        builder.show();
    }
}
