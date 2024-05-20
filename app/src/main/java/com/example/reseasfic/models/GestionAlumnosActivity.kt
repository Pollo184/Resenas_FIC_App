package com.example.reseasfic.models

import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.reseasfic.R
import com.example.reseasfic.network.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GestionAlumnosActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gestion_alumnos)

        val nombreAlumno = findViewById<EditText>(R.id.nombre_alumno)
        val apellidoPatAlumno = findViewById<EditText>(R.id.apellido_pat_alumno)
        val apellidoMatAlumno = findViewById<EditText>(R.id.apellido_mat_alumno)
        val correoAlumno = findViewById<EditText>(R.id.correo_alumno)
        val contrasenaAlumno = findViewById<EditText>(R.id.contrasena_alumno)
        val carreraAlumnoSpinner = findViewById<Spinner>(R.id.carrera_alumno_spinner)
        val semestreAlumnoSpinner = findViewById<Spinner>(R.id.semestre_alumno_spinner)
        val agregarAlumnoButton = findViewById<Button>(R.id.agregar_alumno_button)

        // Configurar spinner de carrera
        val carreras = arrayOf("1", "2", "3")
        val carreraAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, carreras)
        carreraAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        carreraAlumnoSpinner.adapter = carreraAdapter

        // Configurar spinner de semestre
        val semestres = arrayOf("1", "2", "3", "4", "5", "6", "7", "8", "9")
        val semestreAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, semestres)
        semestreAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        semestreAlumnoSpinner.adapter = semestreAdapter

        agregarAlumnoButton.setOnClickListener {
            val nombre = nombreAlumno.text.toString()
            val apellidoPat = apellidoPatAlumno.text.toString()
            val apellidoMat = apellidoMatAlumno.text.toString()
            val correo = correoAlumno.text.toString()
            val contrasena = contrasenaAlumno.text.toString()
            val carrera = carreraAlumnoSpinner.selectedItem.toString()
            val semestre = semestreAlumnoSpinner.selectedItem.toString()

            Log.d("GestionAlumnosActivity", "nombre: $nombre, apellidoPat: $apellidoPat, apellidoMat: $apellidoMat, correo: $correo, contrasena: $contrasena, carrera: $carrera, semestre: $semestre")

            if (nombre.isNotBlank() && apellidoPat.isNotBlank() && apellidoMat.isNotBlank() && correo.isNotBlank() && contrasena.isNotBlank()) {
                ApiClient.apiService.insertarAlumno(nombre, apellidoPat, apellidoMat, correo, contrasena, carrera, semestre).enqueue(object : Callback<BasicResponse> {
                    override fun onResponse(call: Call<BasicResponse>, response: Response<BasicResponse>) {
                        if (response.isSuccessful) {
                            val basicResponse = response.body()
                            if (basicResponse?.success == true) {
                                Toast.makeText(this@GestionAlumnosActivity, "Alumno agregado exitosamente", Toast.LENGTH_SHORT).show()
                                nombreAlumno.text.clear()
                                apellidoPatAlumno.text.clear()
                                apellidoMatAlumno.text.clear()
                                correoAlumno.text.clear()
                                contrasenaAlumno.text.clear()
                                carreraAlumnoSpinner.setSelection(0)
                                semestreAlumnoSpinner.setSelection(0)
                            } else {
                                Toast.makeText(this@GestionAlumnosActivity, "Error al agregar el alumno", Toast.LENGTH_SHORT).show()
                            }
                        } else {
                            Log.e("GestionAlumnosActivity", "Error en la respuesta del servidor: ${response.errorBody()?.string()}")
                        }
                    }

                    override fun onFailure(call: Call<BasicResponse>, t: Throwable) {
                        Log.e("GestionAlumnosActivity", "Error de conexión", t)
                        Toast.makeText(this@GestionAlumnosActivity, "Error de conexión", Toast.LENGTH_SHORT).show()
                    }
                })
            } else {
                Toast.makeText(this, "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
