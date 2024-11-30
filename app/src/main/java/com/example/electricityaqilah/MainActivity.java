package com.example.electricityaqilah;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText tvKWH; // Input for electricity usage in kWh
    private TextView tvTotalBill, tvRMRebate, tvRebate; // Displays for total bill and rebate
    private SeekBar seekBar2; // SeekBar for rebate percentage
    private Button btnCalc, btnReset; // Buttons for calculation and reset

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize UI components
        tvKWH = findViewById(R.id.tvKWH);
        tvTotalBill = findViewById(R.id.tvTotalBill);
        tvRMRebate = findViewById(R.id.tvRMRebate);
        tvRebate = findViewById(R.id.tvRebate);
        seekBar2 = findViewById(R.id.seekBar2);
        btnCalc = findViewById(R.id.btnCalc);
        btnReset = findViewById(R.id.btnReset);

        // Update the displayed rebate percentage when SeekBar is changed
        seekBar2.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tvRebate.setText("Rebate Percentage: " + progress + " %");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // Do nothing
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // Do nothing
            }
        });

        // About Me button functionality
        ImageView btnAboutme = findViewById(R.id.btnAboutme);
        btnAboutme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Electricity Bill Calculator Page", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, AboutMeActivity.class); // Open AboutMeActivity
                startActivity(intent);
            }
        });

        // Info button functionality (show instructions)
        ImageView btnInfo = findViewById(R.id.btnInfo);
        btnInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Instructions")
                        .setMessage("1. Enter the used electricity unit\n\n" +
                                "2. Slide the seekbar to select the rebate percentage (ranging from 0% to 5%).\n\n" +
                                "3. Click the \"Calculate\" button to compute total electricity bill \n\n" +
                                "4. Click \"Reset\" button  to clear the entered data and start over.")
                        .setPositiveButton("OK", null)
                        .show();
            }
        });

        // Set up Calculate button functionality
        btnCalc.setOnClickListener(v -> calculateBill());

        // Set up Reset button functionality
        btnReset.setOnClickListener(v -> resetFields());
    }

    // Function to calculate the total bill and final bill after rebate
    private void calculateBill() {
        String electricityUsedString = tvKWH.getText().toString();

        // Validate input to ensure it's not empty and is a valid number
        if (electricityUsedString.isEmpty()) {
            tvKWH.setError("Please enter electricity usage!");
            return;
        }

        try {
            // Parse the input for electricity used
            int electricityUsed = Integer.parseInt(electricityUsedString);
            double totalBill = calculateTotalBill(electricityUsed);

            // Display the total bill
            tvTotalBill.setText(String.format("RM%.2f", totalBill));

            // Get rebate percentage from SeekBar
            int rebatePercentage = seekBar2.getProgress();
            double finalBill = totalBill - (totalBill * rebatePercentage / 100.0);

            // Display the final bill after rebate
            tvRMRebate.setText(String.format("RM%.2f", finalBill));

        } catch (NumberFormatException e) {
            tvKWH.setError("Please enter a valid number!");
        }
    }

    // Function to calculate the total electricity bill based on usage slabs
    private double calculateTotalBill(int electricityUsed) {
        double total = 0;

        if (electricityUsed <= 200) {
            total = electricityUsed * 0.218;
        } else if (electricityUsed <= 300) {
            total = (200 * 0.218) + ((electricityUsed - 200) * 0.334);
        } else if (electricityUsed <= 600) {
            total = (200 * 0.218) + (100 * 0.334) + ((electricityUsed - 300) * 0.516);
        } else {
            total = (200 * 0.218) + (100 * 0.334) + (300 * 0.516) + ((electricityUsed - 600) * 0.546);
        }

        return total;
    }

    // Function to reset all fields to default
    private void resetFields() {
        tvKWH.setText("");
        tvTotalBill.setText("RM0.00");
        tvRMRebate.setText("RM0.00");
        seekBar2.setProgress(0);
        tvRebate.setText("Rebate Percentage: 0 %");
    }
}
