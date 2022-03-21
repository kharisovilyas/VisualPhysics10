package com.example.visualphysics10.lessonsFragment;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.example.visualphysics10.MainActivity;
import com.example.visualphysics10.R;
import com.example.visualphysics10.database.App;
import com.example.visualphysics10.database.AppDataBase;
import com.example.visualphysics10.database.LessonData;
import com.example.visualphysics10.database.PhysicsData;
import com.example.visualphysics10.objects.PhysicsModel;
import com.example.visualphysics10.physics.PhysicView;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Objects;

public class L5Fragment extends Fragment {
    private PhysicView gameView;
    public double vectorX = 0;
    public static double vectorY = 0;
    public double vectorX1 = 0;
    public static double vectorY1 = 0;
    public double vectorYI = 0;
    public static boolean isMoving = false;
    public static boolean flagInput = false;
    private boolean startVisual = true;
    public int switchFab = 0;
    public EditText input_speed;
    public EditText input_speed2;
    public EditText input_mass1;
    public EditText input_mass2;
    private FloatingActionButton saveInput;
    private FloatingActionButton restartInput;
    private Toolbar toolbar;
    private boolean elasticImpulse;
    AppDataBase db = App.getInstance().getDatabase();
    LessonData lessonData = new LessonData();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.l5_fragment, container, false);
        PhysicsModel.L5 = true;
        gameView = view.findViewById(R.id.physics_view);
        gameView.addModelGV(200, 400, vectorX, vectorY);
        gameView.addModelGV(800, 400, vectorX1, vectorY1);

        initializationButton(view, switchFab);
        view.findViewById(R.id.bottom_sheet_event).setOnClickListener(v -> {
            switchBottomSheetFragment(startVisual);
        });



        Objects.requireNonNull(initializationButton(view, 1)).setOnClickListener(v -> {
            if (flagInput) {
                isMoving = true;
                flagInput = false;
                vectorX = 15;
                vectorY = 0;
                vectorX1 = 0;
                vectorY1 = 0;
                db.dataDao().getAllLiveData();
                PhysicsData.setElasticImpulse(lessonData.elasticImpulse);
                gameView.updateMoving(vectorX, vectorY, 0);
                gameView.updateMoving(vectorX1, vectorY1, 1);
                Log.d("elastic ??", PhysicsData.getElasticImpulse()+"");
                db.dataDao().delete(lessonData);

            } else {
                Toast.makeText(getContext(), "Для начала введите исходные данных", Toast.LENGTH_SHORT).show();
            }
        });
        MainActivity.isFragment = true;
        return view;

    }

    private void switchBottomSheetFragment(boolean startVisual) {
        if (startVisual){
            toggleBottomSheetInput();
            Log.d("input","true");
        } else{
            toggleBottomSheetOutput();
            Log.d("output","true");
        }
    }


    private FloatingActionButton initializationButton(View view, int indexOfFab) {
        FloatingActionsMenu floatingActionsMenu = view.findViewById(R.id.action_menu);
        FloatingActionButton fab1 = view.findViewById(R.id.fab1_l5);
        FloatingActionButton fab2 = view.findViewById(R.id.fab2_l5);
        floatingActionsMenu.setOnFloatingActionsMenuUpdateListener( new FloatingActionsMenu.OnFloatingActionsMenuUpdateListener() {
            @Override
            public void onMenuExpanded() {
                fab1.setVisibility(View.VISIBLE);
                fab2.setVisibility(View.VISIBLE);
            }

            @Override
            public void onMenuCollapsed() {
                fab1.setVisibility(View.GONE);
                fab2.setVisibility(View.GONE);
            }
        });

        switch(indexOfFab){
            case 1: return fab1;
            case 2: return fab2;
            default: return null;
        }
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public void toggleBottomSheetInput() {
        @SuppressLint("InflateParams")
        View view = getLayoutInflater().inflate(R.layout.l5_bottom_sheet, null);
        BottomSheetDialog dialog = new BottomSheetDialog(
                requireContext(), R.style.BottomSheetDialogTheme
        );
        dialog.setContentView(view);
        dialog.show();
        input_speed = view.findViewById(R.id.input_speed1);
        input_speed2 = view.findViewById(R.id.input_speed2);
        input_mass1 = view.findViewById(R.id.input_mass1);
        input_mass2 = view.findViewById(R.id.input_mass2);
        saveInput = view.findViewById(R.id.save);
        RadioButton elastic = view.findViewById(R.id.elastic);
        RadioButton noElastic = view.findViewById(R.id.noElastic);
        elastic.setOnClickListener(v->{
            elasticImpulse = true;
        });
        noElastic.setOnClickListener(v->{
            elasticImpulse = false;
        });

        saveInput.setOnClickListener(v -> {
            try {
                saveData();
                startVisual = false;
            } catch (Exception e) {
                e.printStackTrace();
                Log.d("если ошибка", "проверь трюкач");
            }
            flagInput = true;
            dialog.dismiss();
        });




    }
    private void saveData() {
        lessonData.speed = Double.parseDouble(input_speed.getText().toString());
        lessonData.speed2 = Double.parseDouble(input_speed2.getText().toString());
        lessonData.mass1 = Double.parseDouble(input_mass1.getText().toString());
        lessonData.mass2 = Double.parseDouble(input_mass2.getText().toString());
        lessonData.elasticImpulse = elasticImpulse;
        Log.d("","nooo not work");
        db.dataDao().insert(lessonData);
    }

    private void toggleBottomSheetOutput() {
        View view = getLayoutInflater().inflate(R.layout.l5_bottom_sheet_output, null);
        BottomSheetDialog dialog = new BottomSheetDialog(
                requireContext(), R.style.BottomSheetDialogTheme
        );
        restartInput = view.findViewById(R.id.restart);
        restartInput.setOnClickListener(v -> {
            startVisual = true;
            db.dataDao().delete(lessonData);
            dialog.dismiss();
        });
        dialog.setContentView(view);
        dialog.show();
    }
}
