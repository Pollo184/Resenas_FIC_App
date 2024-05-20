package com.example.reseasfic.models

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.reseasfic.R

class AdminActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin)

        // Botón para gestionar alumnos
        findViewById<Button>(R.id.btn_gestion_alumnos).setOnClickListener {
            val intent = Intent(this, GestionAlumnosActivity::class.java)
            startActivity(intent)
        }

        // Botón para gestionar docentes
        findViewById<Button>(R.id.btn_gestion_docentes).setOnClickListener {
            val intent = Intent(this, GestionDocentesActivity::class.java)
            startActivity(intent)
        }

        // Botón para gestionar materias
        findViewById<Button>(R.id.btn_gestion_materias).setOnClickListener {
            val intent = Intent(this, GestionMateriasActivity::class.java)
            startActivity(intent)
        }

        findViewById<Button>(R.id.btn_gestion_reseñas).setOnClickListener {
            val intent = Intent(this, GestionReseñasActivity::class.java)
            startActivity(intent)
        }
    }
}
