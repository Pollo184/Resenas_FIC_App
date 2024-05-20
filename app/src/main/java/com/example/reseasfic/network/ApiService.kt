package com.example.reseasfic.network

import com.example.reseasfic.adapter.Reseña
import com.example.reseasfic.models.*
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded

interface ApiService {
    @GET("obtener_materias.php")
    fun obtenerMaterias(): Call<List<Materia>>

    @GET("obtener_profesores_por_materia.php")
    fun obtenerProfesoresPorMateria(@Query("id_materia") id: Int): Call<List<Profesor>>

    @GET("obtener_perfil_profesor.php")
    fun obtenerPerfilProfesor(@Query("id_profesor") id: Int): Call<Profesor>

    @GET("obtener_reseñas_profesor.php")
    fun obtenerReseñasProfesor(@Query("id_profesor") id: Int): Call<List<Reseña>>

    @GET("obtener_docente.php")
    fun obtenerDocente(@Query("id_docente") id: Int): Call<Profesor>

    @GET("obtener_alumnos.php")
    fun obtenerAlumnos(): Call<List<Alumno>>

    @GET("verificar_inscripcion.php")
    fun verificarInscripcion(
        @Query("id_alumno") idAlumno: Int,
        @Query("id_profesor") idProfesor: Int,
        @Query("id_materia") idMateria: Int
    ): Call<InscripcionResponse>

    @FormUrlEncoded
    @POST("insertar_reseña.php")
    fun insertarReseña(
        @Field("id_alumno") idAlumno: Int,
        @Field("id_docente") idDocente: Int,
        @Field("id_materia") idMateria: Int,
        @Field("contenido_reseña") contenidoReseña: String
    ): Call<BasicResponse>

    @FormUrlEncoded
    @POST("login.php")
    fun login(
        @Field("correo_alumno") correo: String,
        @Field("contraseña_alumno") contrasena: String
    ): Call<LoginResponse>

    @FormUrlEncoded
    @POST("insertar_alumno.php")
    fun insertarAlumno(
        @Field("nombre_alumno") nombreAlumno: String,
        @Field("apellido_pat_alumno") apellidoPatAlumno: String,
        @Field("apellido_mat_alumno") apellidoMatAlumno: String,
        @Field("correo_alumno") correoAlumno: String,
        @Field("contraseña_alumno") contrasenaAlumno: String,
        @Field("carrera_alumno") carreraAlumno: String,
        @Field("semestre_alumno") semestreAlumno: String
    ): Call<BasicResponse>

    @FormUrlEncoded
    @POST("insertar_docente.php")
    fun insertarDocente(
        @Field("nombre_docente") nombreDocente: String,
        @Field("apellido_pat_docente") apellidoPatDocente: String,
        @Field("apellido_mat_docente") apellidoMatDocente: String,
        @Field("correo_docente") correoDocente: String
    ): Call<BasicResponse>
    @FormUrlEncoded
    @POST("asignar_materia.php")
    fun asignarMateria(
        @Field("id_alumno") idAlumno: Int,
        @Field("id_materia") idMateria: Int,
        @Field("id_profesor") idProfesor: Int
    ): Call<BasicResponse>
    @GET("obtener_reseñas.php")
    fun obtenerReseñasProfesor(
        @Query("id_docente") idDocente: Int,
        @Query("id_materia") idMateria: Int
    ): Call<List<Reseña>>

    @GET("obtener_todas_las_resenas.php")
    fun obtenerTodasLasReseñas(): Call<List<Reseña>>

    @GET("eliminar_resena.php")
    fun eliminarReseña(@Query("id_reseña") idReseña: String): Call<BasicResponse>
}
