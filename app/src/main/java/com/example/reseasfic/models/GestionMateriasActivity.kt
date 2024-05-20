package com.example.reseasfic.models

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.reseasfic.R
import com.example.reseasfic.network.Alumno
import com.example.reseasfic.network.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GestionMateriasActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gestion_materias)

        val alumnoSpinner = findViewById<Spinner>(R.id.alumno_spinner)
        val materiaSpinner = findViewById<Spinner>(R.id.materia_spinner)
        val profesorSpinner = findViewById<Spinner>(R.id.profesor_spinner)
        val asignarButton = findViewById<Button>(R.id.asignar_button)

        cargarAlumnos(alumnoSpinner)
        cargarMaterias(materiaSpinner)

        materiaSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val materiaId = (parent.getItemAtPosition(position) as Materia).id_materia
                cargarProfesoresPorMateria(materiaId, profesorSpinner)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        asignarButton.setOnClickListener {
            val alumnoId = (alumnoSpinner.selectedItem as Alumno).id_alumno
            val materiaId = (materiaSpinner.selectedItem as Materia).id_materia
            val profesorId = (profesorSpinner.selectedItem as Profesor).id_docente

            asignarMateria(alumnoId, materiaId, profesorId)
        }
    }

    private fun cargarAlumnos(spinner: Spinner) {
        ApiClient.apiService.obtenerAlumnos().enqueue(object : Callback<List<Alumno>> {
            override fun onResponse(call: Call<List<Alumno>>, response: Response<List<Alumno>>) {
                if (response.isSuccessful) {
                    val alumnos = response.body() ?: emptyList()
                    val adapter = ArrayAdapter(this@GestionMateriasActivity, android.R.layout.simple_spinner_item, alumnos)
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    spinner.adapter = adapter
                } else {
                    Toast.makeText(this@GestionMateriasActivity, "Error al obtener los alumnos", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<Alumno>>, t: Throwable) {
                Toast.makeText(this@GestionMateriasActivity, "Error de conexión", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun cargarMaterias(spinner: Spinner) {
        ApiClient.apiService.obtenerMaterias().enqueue(object : Callback<List<Materia>> {
            override fun onResponse(call: Call<List<Materia>>, response: Response<List<Materia>>) {
                if (response.isSuccessful) {
                    val materias = response.body() ?: emptyList()
                    val adapter = ArrayAdapter(this@GestionMateriasActivity, android.R.layout.simple_spinner_item, materias)
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    spinner.adapter = adapter
                } else {
                    Toast.makeText(this@GestionMateriasActivity, "Error al obtener las materias", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<Materia>>, t: Throwable) {
                Toast.makeText(this@GestionMateriasActivity, "Error de conexión", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun cargarProfesoresPorMateria(idMateria: Int, spinner: Spinner) {
        ApiClient.apiService.obtenerProfesoresPorMateria(idMateria).enqueue(object : Callback<List<Profesor>> {
            override fun onResponse(call: Call<List<Profesor>>, response: Response<List<Profesor>>) {
                if (response.isSuccessful) {
                    val profesores = response.body() ?: emptyList()
                    val adapter = ArrayAdapter(this@GestionMateriasActivity, android.R.layout.simple_spinner_item, profesores)
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    spinner.adapter = adapter
                } else {
                    Toast.makeText(this@GestionMateriasActivity, "Error al obtener los profesores", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<Profesor>>, t: Throwable) {
                Toast.makeText(this@GestionMateriasActivity, "Error de conexión", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun asignarMateria(idAlumno: Int, idMateria: Int, idProfesor: Int) {
        ApiClient.apiService.asignarMateria(idAlumno, idMateria, idProfesor).enqueue(object : Callback<BasicResponse> {
            override fun onResponse(call: Call<BasicResponse>, response: Response<BasicResponse>) {
                if (response.isSuccessful) {
                    val basicResponse = response.body()
                    if (basicResponse?.success == true) {
                        Toast.makeText(this@GestionMateriasActivity, "Materia asignada exitosamente", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this@GestionMateriasActivity, "Error al asignar la materia", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this@GestionMateriasActivity, "Error en la respuesta del servidor", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<BasicResponse>, t: Throwable) {
                Toast.makeText(this@GestionMateriasActivity, "Error de conexión", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
