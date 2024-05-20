package com.example.reseasfic.models

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.reseasfic.R
import com.example.reseasfic.adapter.Reseña
import com.example.reseasfic.adapter.GestionReseñasAdapter
import com.example.reseasfic.network.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GestionReseñasActivity : AppCompatActivity() {
    private lateinit var gestionReseñasAdapter: GestionReseñasAdapter
    private val reseñas = mutableListOf<Reseña>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gestion_resenas)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerViewGestionResenas)
        recyclerView.layoutManager = LinearLayoutManager(this)
        gestionReseñasAdapter = GestionReseñasAdapter(reseñas, this::eliminarReseña)
        recyclerView.adapter = gestionReseñasAdapter

        cargarReseñas()
    }

    private fun cargarReseñas() {
        ApiClient.apiService.obtenerTodasLasReseñas().enqueue(object : Callback<List<Reseña>> {
            override fun onResponse(call: Call<List<Reseña>>, response: Response<List<Reseña>>) {
                if (response.isSuccessful) {
                    val reseñasObtenidas = response.body() ?: emptyList()
                    Log.d("GestionReseñasActivity", "Reseñas obtenidas: $reseñasObtenidas")
                    reseñas.clear()
                    reseñas.addAll(reseñasObtenidas)
                    gestionReseñasAdapter.notifyDataSetChanged()
                } else {
                    val errorBody = response.errorBody()?.string()
                    val errorMessage = "Error al obtener las reseñas: $errorBody"
                    Toast.makeText(this@GestionReseñasActivity, errorMessage, Toast.LENGTH_LONG).show()
                    Log.e("GestionReseñasActivity", errorMessage)
                }
            }

            override fun onFailure(call: Call<List<Reseña>>, t: Throwable) {
                val errorMessage = "Error al obtener las reseñas: ${t.message}"
                Toast.makeText(this@GestionReseñasActivity, errorMessage, Toast.LENGTH_LONG).show()
                Log.e("GestionReseñasActivity", errorMessage)
            }
        })
    }

    private fun eliminarReseña(reseña: Reseña) {
        ApiClient.apiService.eliminarReseña(reseña.idReseña).enqueue(object : Callback<BasicResponse> {
            override fun onResponse(call: Call<BasicResponse>, response: Response<BasicResponse>) {
                if (response.isSuccessful) {
                    val basicResponse = response.body()
                    if (basicResponse?.success == true) {
                        Toast.makeText(this@GestionReseñasActivity, "Reseña eliminada exitosamente", Toast.LENGTH_SHORT).show()
                        reseñas.remove(reseña)
                        gestionReseñasAdapter.notifyDataSetChanged()
                    } else {
                        val errorMessage = "Error al eliminar la reseña: ${basicResponse?.error}"
                        Toast.makeText(this@GestionReseñasActivity, errorMessage, Toast.LENGTH_LONG).show()
                        Log.e("GestionReseñasActivity", errorMessage)
                    }
                } else {
                    val errorBody = response.errorBody()?.string()
                    val errorMessage = "Error al eliminar la reseña: $errorBody"
                    Toast.makeText(this@GestionReseñasActivity, errorMessage, Toast.LENGTH_LONG).show()
                    Log.e("GestionReseñasActivity", errorMessage)
                }
            }

            override fun onFailure(call: Call<BasicResponse>, t: Throwable) {
                val errorMessage = "Error de conexión: ${t.message}"
                Toast.makeText(this@GestionReseñasActivity, errorMessage, Toast.LENGTH_LONG).show()
                Log.e("GestionReseñasActivity", errorMessage)
            }
        })
    }
}
