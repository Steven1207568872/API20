package com.example.api18;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    // Referencias a Firebase
    private DatabaseReference humedadRef, presionRef, velocidadRef, temperaturaRef;

    private TextView txt_humedad, txt_presion, txt_velocidad, txt_temperatura;
    private EditText setvalor_humedad, setvalor_presion, setvalor_velocidad, setvalor_temperatura;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        txt_humedad = findViewById(R.id.txt_humedad);
        txt_presion = findViewById(R.id.txt_presion);
        txt_velocidad = findViewById(R.id.txt_velocidad);
        txt_temperatura = findViewById(R.id.txt_temperatura);


        setvalor_humedad = findViewById(R.id.setvalor_Humedad);
        setvalor_presion = findViewById(R.id.setvalor_Presion);
        setvalor_velocidad = findViewById(R.id.setvalor_Velocidad);
        setvalor_temperatura = findViewById(R.id.setvalor_temperatura);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        humedadRef = database.getReference("Sensores/Humedad");
        presionRef = database.getReference("Sensores/Presion");
        velocidadRef = database.getReference("Sensores/Velocidad");
        temperaturaRef = database.getReference("Sensores/Temperatura");


        humedadRef.addValueEventListener(setListener(txt_humedad, "%"));
        presionRef.addValueEventListener(setListener(txt_presion, "hPa"));
        velocidadRef.addValueEventListener(setListener(txt_velocidad, "km/h"));
        temperaturaRef.addValueEventListener(setListener(txt_temperatura, "°C"));
    }

    // Método que devuelve un ValueEventListener para cada sensor
    public ValueEventListener setListener(TextView txt, String UnidadMedida){
        return (new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                txt.setText(snapshot.getValue().toString() + " " + UnidadMedida);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                txt.setText("");
            }
        });
        }
    public void clickBotonTemperatura(View view){
        temperaturaRef.setValue(Float.parseFloat(setvalor_temperatura.getText().toString()));
    }
    public void clickBotonHumedad(View view){
        humedadRef.setValue(Float.parseFloat(setvalor_humedad.getText().toString()));
        }
    public void clickBotonPresion(View view){
        presionRef.setValue(Float.parseFloat(setvalor_presion.getText().toString()));
        }
    public void clickBotonVelocidad(View view){
        velocidadRef.setValue(Float.parseFloat(setvalor_velocidad.getText().toString()));

    }
};

