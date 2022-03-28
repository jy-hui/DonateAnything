package com.example.donateanything

import android.app.ProgressDialog
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
   //ProgressDialog
    private lateinit var progressDialog: ProgressDialog

    //FirebaseAuth
    private lateinit var firebaseAuth: FirebaseAuth
    private var db : FirebaseFirestore? = null

    //SharedPreferences
    private lateinit var sharePref : SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        firebaseAuth= FirebaseAuth.getInstance()

        val forgotPwd=findViewById<TextView>(R.id.forgot)
        val signUp = findViewById<TextView>(R.id.signup)
        val btnLogin = findViewById<Button>(R.id.btnLogin)
        val rmb=findViewById<CheckBox>(R.id.rmbMe)

        db = FirebaseFirestore.getInstance()
        sharePref = getSharedPreferences("rememberMe", MODE_PRIVATE)
        if ( sharePref.contains("pref_rmbMe")) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
        val email = findViewById<EditText>(R.id.Email)
        val password= findViewById<EditText>(R.id.Password)

        forgotPwd.setOnClickListener {
            val intent = Intent(this, ForgotPwd::class.java)
            startActivity(intent)
        }

        signUp.setOnClickListener {
            val intent = Intent(this, SignUp::class.java)
            startActivity(intent)
        }

        btnLogin.setOnClickListener {
            if (email.text.toString().isEmpty()&&password.text.toString().isEmpty())
            {
                Toast.makeText(this, "Fields cannot be empty!", Toast.LENGTH_SHORT).show()
            }
            else if (email.text.toString().isEmpty()){
                email.error="Please enter your email!"
                email.requestFocus()
            }
            else if(!(android.util.Patterns.EMAIL_ADDRESS.matcher(email.text.toString()).matches())){
                email.error="Invalid email"
                email.requestFocus()
            }
            else if (password.text.toString().isEmpty()){
                password.error="Please enter your password!"
                password.requestFocus()
            }
            else {
                firebaseAuth.signInWithEmailAndPassword(
                    email.text.toString(),
                    Password.text.toString()
                )
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            progressDialog = ProgressDialog(this)
                            progressDialog.setTitle("Please wait")
                            progressDialog.setMessage("Logging In...")
                            progressDialog.setCanceledOnTouchOutside(false)
                            progressDialog.show()
                            with(sharePref.edit()) {
                                if (rmb.isChecked) {
                                    putString("pref_rmbMe", "Yes")
                                }
                                apply()
                            }
                            var intent = Intent(this, MainActivity::class.java)
                            startActivity(intent)
                            finish()
                        } else {
                            Toast.makeText(
                                this,
                                "Incorrect email or password!",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }

            }
        }


    }

}