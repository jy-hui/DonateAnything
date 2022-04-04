package com.example.donateanything

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_forgot_pwd.*

class ForgotPwd : AppCompatActivity() {
    private var mAuth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_pwd)

        mAuth = FirebaseAuth.getInstance()

        btnSendMail.setOnClickListener {
            val email = findViewById<EditText>(R.id.forgotEmail)
            if (TextUtils.isEmpty(email.text.toString()))
            {
                email.error="Please enter your email!"
                email.requestFocus()
            }
            else {
                mAuth!!.sendPasswordResetEmail(email.text.toString())
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(this,"Reset password email sent!", Toast.LENGTH_SHORT).show()
                        } else {
                            email.error="Invalid email!"
                            email.requestFocus()
                        }
                    }
            }
        }
        backBtn.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}