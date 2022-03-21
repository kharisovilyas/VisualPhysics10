package com.example.visualphysics10.lessonsFragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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

public class L1Fragment extends Fragment {

    private PhysicView gameView;
    public static boolean flagInput = false;
    public static boolean isMoving = false;
    private boolean startVisual = true;
    public int switchFab = 0;
    public EditText input_speed;
    public EditText input_distance;
    public EditText input_acc;
    public TextView output_speed;
    public TextView output_distance;
    public TextView output_acc;
    AppDataBase db = App.getInstance().getDatabase();
    LessonData lessonData = new LessonData();

    @SuppressLint("ResourceType")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.l1_fragment, container, false);

        gameView = view.findViewById(R.id.physics_view);
        PhysicsModel.L1 = true;
        gameView.addModelGV(570, 570, 0, 0);
        initializationButton(view, switchFab);
        view.findViewById(R.id.bottom_sheet_event).setOnClickListener(v -> {
            switchBottomSheetFragment(startVisual);
        });

        Objects.requireNonNull(initializationButton(view, 1)).setOnClickListener(v -> {
            if (flagInput) {
                Objects.requireNonNull(initializationButton(view, 2)).setImageResource(R.drawable.ic_baseline_pause_circle_outline_24);
                flagInput = false;
                db.dataDao().getAllLiveData();
                PhysicsData.setAcc(lessonData.acc);
                gameView.updateMoving(lessonData.speed, 0, 0);
                db.dataDao().delete(lessonData);
            } else {
                Toast.makeText(getContext(), "Для начала введите исходные данные", Toast.LENGTH_SHORT).show();
            }
        });
        Objects.requireNonNull(initializationButton(view, 2)).setOnClickListener(v -> {
            PhysicsData.setX0(570);
            PhysicsData.setY0(570);
            flagInput = true;
        });
        MainActivity.isFragment = true;
        return view;

    }

    private void switchBottomSheetFragment(boolean startVisual) {
        if (startVisual){
            toggleBottomSheetInput();
        } else{
            toggleBottomSheetOutput();
        }
    }


    private FloatingActionButton initializationButton(View view, int indexOfFab) {
        FloatingActionsMenu floatingActionsMenu = view.findViewById(R.id.action_menu_l1);
        FloatingActionButton fab1 = view.findViewById(R.id.fab1_l1);
        FloatingActionButton fab2 = view.findViewById(R.id.fab2_l1);
        floatingActionsMenu.setOnFloatingActionsMenuUpdateListener(new FloatingActionsMenu.OnFloatingActionsMenuUpdateListener() {
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
        View view = getLayoutInflater().inflate(R.layout.l1_bottom_sheet, null);
        BottomSheetDialog dialog = new BottomSheetDialog(
                Objects.requireNonNull(getContext()), R.style.BottomSheetDialogTheme
        );
        dialog.setContentView(view);
        dialog.show();
        input_speed = view.findViewById(R.id.input_speed);
        input_distance = view.findViewById(R.id.input_distance);
        input_acc = view.findViewById(R.id.input_acc);
        FloatingActionButton saveInput = view.findViewById(R.id.save);
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
        lessonData.distance = Double.parseDouble(input_distance.getText().toString());
        lessonData.acc = Double.parseDouble(input_acc.getText().toString());
        db.dataDao().insert(lessonData);
    }

    @SuppressLint("SetTextI18n")
    private void toggleBottomSheetOutput() {
        View view = getLayoutInflater().inflate(R.layout.l1_bottom_sheet_output, null);
        BottomSheetDialog dialog = new BottomSheetDialog(
                Objects.requireNonNull(getContext()), R.style.BottomSheetDialogTheme
        );
        output_speed = view.findViewById(R.id.output_speed);
        output_speed.setText((int)lessonData.speed + " [м/с] - скорость");
        output_distance = view.findViewById(R.id.output_distance);
        output_distance.setText((int)lessonData.distance + " [м] - расстояние");
        output_acc = view.findViewById(R.id.output_acc);
        output_acc.setText((int)lessonData.acc + " [м/с^2] - ускорение");
        FloatingActionButton restartInput = view.findViewById(R.id.restart);
        restartInput.setOnClickListener(v -> {
            gameView.addModelGV(570, 570, 0, 0);
            startVisual = true;
            db.dataDao().delete(lessonData);
            dialog.dismiss();
        });
        dialog.setContentView(view);
        dialog.show();
    }

}
