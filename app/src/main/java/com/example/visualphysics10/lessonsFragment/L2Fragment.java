package com.example.visualphysics10.lessonsFragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
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

public class L2Fragment extends Fragment {
    private PhysicView gameView;
    public static boolean flagInput = false;
    public static boolean isMoving = false;
    private boolean startVisual = true;
    public int switchFab = 0;
    public EditText input_speed;
    public EditText input_radius;
    private FloatingActionButton saveInput;
    private FloatingActionButton restartInput;
    private Toolbar toolbar;
    AppDataBase db = App.getInstance().getDatabase();
    LessonData lessonData = new LessonData();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.l2_fragment, container, false);
        gameView = view.findViewById(R.id.physics_view);
        PhysicsModel.L2 = true;
        PhysicsModel.firstDraw = true;
        gameView.addModelGV(PhysicsModel.x0, PhysicsModel.y0, 0, 0);
        initializationButton(view, switchFab);
        view.findViewById(R.id.bottom_sheet_event).setOnClickListener(v -> {
            switchBottomSheetFragment(startVisual);
        });

        Objects.requireNonNull(initializationButton(view, 1)).setOnClickListener(v -> {
            if (flagInput) {
                isMoving = true;
                flagInput = false;
                db.dataDao().getAllLiveData();
                gameView.updateMoving(lessonData.speed, 0, 0);
                PhysicsData.setRadius(lessonData.radius);
                db.dataDao().delete(lessonData);
            } else {
                Toast.makeText(getContext(), "Для начала введите исходные данных", Toast.LENGTH_SHORT).show();
            }

        });
        MainActivity.isFragment = true;
        return view;

    }

    private FloatingActionButton initializationButton(View view, int indexOfFab) {
        FloatingActionsMenu floatingActionsMenu = view.findViewById(R.id.action_menu);
        FloatingActionButton fab1 = view.findViewById(R.id.fab1_l2);
        FloatingActionButton fab2 = view.findViewById(R.id.fab2_l2);
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

        switch (indexOfFab) {
            case 1:
                return fab1;
            case 2:
                return fab2;
            default:
                return null;
        }
    }

    private void switchBottomSheetFragment(boolean startVisual) {
        if (startVisual) {
            toggleBottomSheetInput();
            Log.d("input", "true");
        } else {
            toggleBottomSheetOutput();
            Log.d("output", "true");
        }
    }

    public void toggleBottomSheetInput() {
        @SuppressLint("InflateParams")
        View view = getLayoutInflater().inflate(R.layout.l2_bottom_sheet, null);
        BottomSheetDialog dialog = new BottomSheetDialog(
                Objects.requireNonNull(getContext()), R.style.BottomSheetDialogTheme
        );
        dialog.setContentView(view);
        dialog.show();
        input_speed = view.findViewById(R.id.input_speed);
        input_radius = view.findViewById(R.id.input_radius);
        saveInput = view.findViewById(R.id.save);
        saveInput.setOnClickListener(v -> {
            try {
                saveData();
                startVisual = false;
            } catch (Exception e) {
                e.printStackTrace();
                Log.d("если ошибка", "проверь трюкач");
            }
            dialog.dismiss();
        });

    }

    private void saveData() {
        lessonData.speed = Double.parseDouble(input_speed.getText().toString());
        lessonData.radius = Double.parseDouble(input_radius.getText().toString());
        db.dataDao().insert(lessonData);

    }

    private void toggleBottomSheetOutput() {
        View view = getLayoutInflater().inflate(R.layout.l2_bottom_sheet_output, null);
        BottomSheetDialog dialog = new BottomSheetDialog(
                Objects.requireNonNull(getContext()), R.style.BottomSheetDialogTheme
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

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


}