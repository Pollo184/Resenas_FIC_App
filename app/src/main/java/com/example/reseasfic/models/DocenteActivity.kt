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

class DocenteActivity : AppCompatActivity() {
    private var alumnoId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_materias)

        alumnoId = intent.getIntExtra("alumno_id", -1)

        // Verificar que alumnoId se reciba correctamente
        Log.d("DocenteActivity", "alumnoId: $alumnoId")
        if (alumnoId == -1) {
            val errorMessage = "Error al obtener el ID del alumno. AlumnoID: $alumnoId"
            Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show()
            Log.e("DocenteActivity", errorMessage)
            finish()
            return
        }

        val recyclerView = findViewById<RecyclerView>(R.id.rv_materias)
        recyclerView.layoutManager = LinearLayoutManager(this)

        ApiClient.apiService.obtenerMaterias().enqueue(object : Callback<List<Materia>> {
            override fun onResponse(call: Call<List<Materia>>, response: Response<List<Materia>>) {
                if (response.isSuccessful) {
                    val materias = response.body() ?: emptyList()
                    recyclerView.adapter = MateriaAdapter(materias) { materia ->
                        val intent = Intent(this@DocenteActivity, ProfesorActivity::class.java)
                        intent.putExtra("materia_id", materia.id_materia)
                        intent.putExtra("alumno_id", alumnoId)  // Pasar alumnoId correctamente
                        startActivity(intent)
                    }
                } else {
                    Log.e("DocenteActivity", "Error en la respuesta: ${response.errorBody()?.string()}")
                    Toast.makeText(this@DocenteActivity, "Error al obtener las materias", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<Materia>>, t: Throwable) {
                Log.e("DocenteActivity", "Error en la llamada: ${t.message}")
                Toast.makeText(this@DocenteActivity, "Error de conexión", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
