package com.example.reseasfic.ui.theme

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.reseasfic.models.AdminActivity
import com.example.reseasfic.models.DocenteActivity
import com.example.reseasfic.models.LoginResponse
import com.example.reseasfic.network.ApiClient
import com.example.reseasfic.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login) // Mostrar la pantalla de login

        val studentIdField = findViewById<EditText>(R.id.et_student_id)
        val passwordField = findViewById<EditText>(R.id.et_password)
        val loginButton = findViewById<Button>(R.id.btn_login)

        loginButton.setOnClickListener {
            val studentId = studentIdField.text.toString()
            val password = passwordField.text.toString()

            // Verificar si es el administrador
            if (studentId == "Neto" && password == "1234") {
                val intent = Intent(this, AdminActivity::class.java)
                startActivity(intent)
            } else {
                // Llamar a la función para autenticar al usuario
                authenticateUser(studentId, password)
            }
        }
    }

    private fun authenticateUser(username: String, password: String) {
        val call = ApiClient.apiService.login(username, password)

        call.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful && response.body()?.success == true) {
                    val alumnoId = response.body()?.alumnoId ?: -1
                    if (alumnoId != -1) {
                        // Redirigir a la actividad de Docente y pasar el alumnoId
                        val intent = Intent(this@MainActivity, DocenteActivity::class.java)
                        intent.putExtra("alumno_id", alumnoId)
                        startActivity(intent)
                        finish()
                    } else {
                        val errorMessage = "Error al obtener el ID del alumno. AlumnoID: $alumnoId"
                        Toast.makeText(this@MainActivity, errorMessage, Toast.LENGTH_LONG).show()
                    }
                } else {
                    Toast.makeText(this@MainActivity, "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Toast.makeText(this@MainActivity, "Error de conexión", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
