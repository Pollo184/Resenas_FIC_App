package com.example.reseasfic.models

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.reseasfic.R
import com.example.reseasfic.adapter.Reseña
import com.example.reseasfic.adapter.ReseñaAdapter
import com.example.reseasfic.network.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PerfilProfesorActivity : AppCompatActivity() {
    private lateinit var reseñasAdapter: ReseñaAdapter
    private var profesorId: Int = -1
    private var alumnoId: Int = -1
    private var materiaId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_perfil_profesor)

        profesorId = intent.getIntExtra("profesor_id", -1)
        alumnoId = intent.getIntExtra("alumno_id", -1)
        materiaId = intent.getIntExtra("materia_id", -1)

        Log.d("PerfilProfesorActivity", "profesorId: $profesorId, alumnoId: $alumnoId, materiaId: $materiaId")
        if (profesorId == -1 || alumnoId == -1 || materiaId == -1) {
            val errorMessage = "Error al obtener los datos necesarios: profesorId=$profesorId, alumnoId=$alumnoId, materiaId=$materiaId"
            Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show()
            Log.e("PerfilProfesorActivity", errorMessage)
            finish()
            return
        }

        val profesorName: TextView = findViewById(R.id.profesor_name)
        val profesorEmail: TextView = findViewById(R.id.profesor_email)
        val reseñaInput: EditText = findViewById(R.id.reseña_input)
        val submitButton: Button = findViewById(R.id.submit_reseña)

        val recyclerView = findViewById<RecyclerView>(R.id.rv_reseñas)
        recyclerView.layoutManager = LinearLayoutManager(this)
        reseñasAdapter = ReseñaAdapter(emptyList())
        recyclerView.adapter = reseñasAdapter

        // Obtener datos del profesor
        ApiClient.apiService.obtenerPerfilProfesor(profesorId).enqueue(object : Callback<Profesor> {
            override fun onResponse(call: Call<Profesor>, response: Response<Profesor>) {
                if (response.isSuccessful) {
                    val profesor = response.body()
                    profesor?.let {
                        profesorName.text = "${it.nombre_docente} ${it.apellido_pat_docente} ${it.apellido_mat_docente}"
                        profesorEmail.text = "Correo: ${it.correo_docente}"
                    } ?: run {
                        val errorBody = response.errorBody()?.string()
                        val errorMessage = "Error al obtener los datos del profesor: $errorBody"
                        Toast.makeText(this@PerfilProfesorActivity, errorMessage, Toast.LENGTH_LONG).show()
                        Log.e("PerfilProfesorActivity", errorMessage)
                    }
                } else {
                    val errorBody = response.errorBody()?.string()
                    val errorMessage = "Error al obtener los datos del profesor: $errorBody"
                    Toast.makeText(this@PerfilProfesorActivity, errorMessage, Toast.LENGTH_LONG).show()
                    Log.e("PerfilProfesorActivity", errorMessage)
                }
            }

            override fun onFailure(call: Call<Profesor>, t: Throwable) {
                val errorMessage = "Error al obtener los datos del profesor: ${t.message}"
                Toast.makeText(this@PerfilProfesorActivity, errorMessage, Toast.LENGTH_LONG).show()
                Log.e("PerfilProfesorActivity", errorMessage)
            }
        })

        // Obtener reseñas del profesor utilizando GET
        ApiClient.apiService.obtenerReseñasProfesor(profesorId, materiaId).enqueue(object : Callback<List<Reseña>> {
            override fun onResponse(call: Call<List<Reseña>>, response: Response<List<Reseña>>) {
                if (response.isSuccessful) {
                    val reseñas = response.body() ?: emptyList()
                    Log.d("PerfilProfesorActivity", "Reseñas obtenidas: $reseñas") // Log para verificar las reseñas obtenidas
                    reseñasAdapter.updateData(reseñas)
                    reseñasAdapter.updateData(reseñas)
                } else {
                    val errorBody = response.errorBody()?.string()
                    val errorMessage = "Error al obtener las reseñas: $errorBody"
                    Toast.makeText(this@PerfilProfesorActivity, errorMessage, Toast.LENGTH_LONG).show()
                    Log.e("PerfilProfesorActivity", errorMessage)
                }
            }

            override fun onFailure(call: Call<List<Reseña>>, t: Throwable) {
                val errorMessage = "Error al obtener las reseñas: ${t.message}"
                Toast.makeText(this@PerfilProfesorActivity, errorMessage, Toast.LENGTH_LONG).show()
                Log.e("PerfilProfesorActivity", errorMessage)
            }
        })

        // Habilitar el botón por defecto
        submitButton.isEnabled = true

        // Método para enviar la reseña en PerfilProfesorActivity
        submitButton.setOnClickListener {
            val contenido = reseñaInput.text.toString().trim()
            if (contenido.isEmpty()) {
                Toast.makeText(this, "La reseña no puede estar vacía", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            ApiClient.apiService.insertarReseña(alumnoId, profesorId, materiaId, contenido).enqueue(object : Callback<BasicResponse> {
                override fun onResponse(call: Call<BasicResponse>, response: Response<BasicResponse>) {
                    if (response.isSuccessful) {
                        val basicResponse = response.body()
                        if (basicResponse?.success == true) {
                            Toast.makeText(this@PerfilProfesorActivity, "Reseña enviada exitosamente", Toast.LENGTH_SHORT).show()
                            reseñaInput.text.clear()
                            // Actualizar la lista de reseñas
                            actualizarReseñas()
                        } else {
                            val errorMessage = "Error al enviar la reseña: ${basicResponse?.error}"
                            Toast.makeText(this@PerfilProfesorActivity, errorMessage, Toast.LENGTH_LONG).show()
                            Log.e("PerfilProfesorActivity", errorMessage)
                        }
                    } else {
                        val errorBody = response.errorBody()?.string()
                        val errorMessage = "Error al enviar la reseña: $errorBody"
                        Toast.makeText(this@PerfilProfesorActivity, errorMessage, Toast.LENGTH_LONG).show()
                        Log.e("PerfilProfesorActivity", errorMessage)
                    }
                }

                override fun onFailure(call: Call<BasicResponse>, t: Throwable) {
                    val errorMessage = "Error de conexión: ${t.message}"
                    Toast.makeText(this@PerfilProfesorActivity, errorMessage, Toast.LENGTH_LONG).show()
                    Log.e("PerfilProfesorActivity", errorMessage)
                }
            })
        }
    }

    private fun actualizarReseñas() {
        ApiClient.apiService.obtenerReseñasProfesor(profesorId, materiaId).enqueue(object : Callback<List<Reseña>> {
            override fun onResponse(call: Call<List<Reseña>>, response: Response<List<Reseña>>) {
                if (response.isSuccessful) {
                    val reseñas = response.body() ?: emptyList()
                    reseñasAdapter.updateData(reseñas)
                } else {
                    val errorBody = response.errorBody()?.string()
                    val errorMessage = "Error al actualizar las reseñas: $errorBody"
                    Toast.makeText(this@PerfilProfesorActivity, errorMessage, Toast.LENGTH_LONG).show()
                    Log.e("PerfilProfesorActivity", errorMessage)
                }
            }

            override fun onFailure(call: Call<List<Reseña>>, t: Throwable) {
                val errorMessage = "Error al actualizar las reseñas: ${t.message}"
                Toast.makeText(this@PerfilProfesorActivity, errorMessage, Toast.LENGTH_LONG).show()
                Log.e("PerfilProfesorActivity", errorMessage)
            }
        })
    }
}
