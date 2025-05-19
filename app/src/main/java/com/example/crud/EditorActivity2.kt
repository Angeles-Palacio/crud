package com.example.crud

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.crud.data.AppDatabase
import com.example.crud.data.entity.User

class EditorActivity2 : AppCompatActivity() {

    private lateinit var fullName: EditText
    private lateinit var email: EditText
    private lateinit var phone: EditText
    private lateinit var btnSave: Button
    private lateinit var database: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editor2)

        fullName = findViewById(R.id.full_name)
        email = findViewById(R.id.email)
        phone = findViewById(R.id.phone)
        btnSave = findViewById(R.id.btn_save)

        database = AppDatabase.getInstance(applicationContext)

        val intent = intent.extras
        var userId: Int? = null

        if (intent != null) {
            userId = intent.getInt("id", 0)
            val user = database.userDao().get(userId)

            if (user != null) {
                fullName.setText("${user.firstName ?: ""} ${user.lastName ?: ""}".trim())
                email.setText(user.email)
                phone.setText(user.phone)
            }
        }

        btnSave.setOnClickListener {
            if (fullName.text.isNotEmpty() && email.text.isNotEmpty() && phone.text.isNotEmpty()) {

                val names = fullName.text.toString().split(" ", limit = 2)
                val firstName = names.getOrNull(0)
                val lastName = names.getOrNull(1)

                if (userId != null && userId != 0) {
                    // Actualizar usuario existente
                    database.userDao().update(
                        User(
                            uid = userId,
                            firstName = firstName,
                            lastName = lastName,
                            email = email.text.toString(),
                            phone = phone.text.toString()
                        )
                    )
                    Toast.makeText(this, "Usuario actualizado", Toast.LENGTH_SHORT).show()

                } else {
                    // Insertar nuevo usuario
                    database.userDao().insertall(
                        User(
                            firstName = firstName,
                            lastName = lastName,
                            email = email.text.toString(),
                            phone = phone.text.toString()
                        )
                    )
                    Toast.makeText(this, "Usuario guardado", Toast.LENGTH_SHORT).show()
                }

                finish()

            } else {
                Toast.makeText(
                    applicationContext,
                    "Por favor, completa todos los campos.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}
