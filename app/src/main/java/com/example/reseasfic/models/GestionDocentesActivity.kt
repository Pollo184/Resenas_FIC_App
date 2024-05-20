package com.example.reseasfic.models

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.reseasfic.R
import com.example.reseasfic.network.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GestionDocentesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gestion_docentes)

        val nombreDocente = findViewById<EditText>(R.id.nombre_docente)
        val apellidoPatDocente = findViewById<EditText>(R.id.apellido_pat_docente)
        val apellidoMatDocente = findViewById<EditText>(R.id.apellido_mat_docente)
        val correoDocente = findViewById<EditText>(R.id.correo_docente)
        val agregarDocenteButton = findViewById<Button>(R.id.agregar_docente_button)

        agregarDocenteButton.setOnClickListener {
            val nombre = nombreDocente.text.toString()
            val apellidoPat = apellidoPatDocente.text.toString()
            val apellidoMat = apellidoMatDocente.text.toString()
            val correo = correoDocente.text.toString()

            if (nombre.isNotBlank() && apellidoPat.isNotBlank() && apellidoMat.isNotBlank() && correo.isNotBlank()) {
                ApiClient.apiService.insertarDocente(nombre, apellidoPat, apellidoMat, correo).enqueue(object : Callback<BasicResponse> {
                    override fun onResponse(call: Call<BasicResponse>, response: Response<BasicResponse>) {
                        if (response.isSuccessful) {
                            val basicResponse = response.body()
                            if (basicResponse?.success == true) {
                                Toast.makeText(this@GestionDocentesActivity, "Docente agregado exitosamente", Toast.LENGTH_SHORT).show()
                                nombreDocente.text.clear()
                                apellidoPatDocente.text.clear()
                                apellidoMatDocente.text.clear()
                                correoDocente.text.clear()
                            } else {
                                Toast.makeText(this@GestionDocentesActivity, "Error al agregar el docente", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }

                    override fun onFailure(call: Call<BasicResponse>, t: Throwable) {
                        Toast.makeText(this@GestionDocentesActivity, "Error de conexión", Toast.LENGTH_SHORT).show()
                    }
                })
            } else {
                Toast.makeText(this, "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
