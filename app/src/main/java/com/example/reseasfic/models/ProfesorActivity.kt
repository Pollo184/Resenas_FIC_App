package com.example.reseasfic.models

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.reseasfic.R
import com.example.reseasfic.network.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfesorActivity : AppCompatActivity() {
    private var materiaId: Int = -1
    private var alumnoId: Int = -1  // Definir alumnoId

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profesores)

        materiaId = intent.getIntExtra("materia_id", -1)
        alumnoId = intent.getIntExtra("alumno_id", -1)  // Recibir alumnoId desde el intent

        // Verificar que se recibieron los IDs correctamente
        Log.d("ProfesorActivity", "materiaId: $materiaId, alumnoId: $alumnoId")
        if (materiaId == -1 || alumnoId == -1) {  // Asegúrate de validar ambos ids
            val errorMessage = "Error al obtener los datos necesarios: materiaId=$materiaId, alumnoId=$alumnoId"
            Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show()
            Log.e("ProfesorActivity", errorMessage)
            finish()
            return
        }

        val recyclerView = findViewById<RecyclerView>(R.id.rv_profesores)
        recyclerView.layoutManager = LinearLayoutManager(this)

        Log.d("ProfesorActivity", "Iniciando llamada a la API obtenerProfesoresPorMateria con materiaId: $materiaId")

        ApiClient.apiService.obtenerProfesoresPorMateria(materiaId).enqueue(object : Callback<List<Profesor>> {
            override fun onResponse(call: Call<List<Profesor>>, response: Response<List<Profesor>>) {
                if (response.isSuccessful) {
                    val profesores = response.body() ?: emptyList()
                    Log.d("ProfesorActivity", "Profesores obtenidos: ${profesores.size}")
                    recyclerView.adapter = ProfesorAdapter(profesores) { profesor ->
                        val intent = Intent(this@ProfesorActivity, PerfilProfesorActivity::class.java)
                        intent.putExtra("profesor_id", profesor.id_docente)
                        intent.putExtra("alumno_id", alumnoId)  // Pasar alumnoId correctamente
                        intent.putExtra("materia_id", materiaId)
                        startActivity(intent)
                    }
                } else {
                    val errorBody = response.errorBody()?.string()
                    Log.e("ProfesorActivity", "Error en la respuesta: $errorBody")
                    Toast.makeText(this@ProfesorActivity, "Error al obtener los profesores", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<Profesor>>, t: Throwable) {
                Log.e("ProfesorActivity", "Error en la llamada: ${t.message}")
                Toast.makeText(this@ProfesorActivity, "Error de conexión", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
