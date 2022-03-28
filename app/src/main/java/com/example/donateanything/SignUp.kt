package com.example.donateanything
import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUp : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    //ProgressDialog
    private lateinit var progressDialog: ProgressDialog

    private lateinit var email:EditText
    private lateinit var password:EditText
    private lateinit var password1:EditText
    private lateinit var username:EditText
    private lateinit var phone:EditText

    private val PHONE_REGEX = "^(01)[0-9]-[0-9]{7,8}\$"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        email = findViewById(R.id.editEmail)
        password= findViewById(R.id.editPwd)
        password1= findViewById(R.id.editPwd1)
        username=findViewById(R.id.editName)
        phone=findViewById(R.id.editPhone)

        val back = findViewById<ImageView>(R.id.back)

        back.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

        val signin = findViewById<TextView>(R.id.signin)
        signin.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

        auth= FirebaseAuth.getInstance()
        db= FirebaseFirestore.getInstance()

        btnSignup.setOnClickListener {
            if(signUpValidation())
            {
                val user= hashMapOf(
                    "Username" to username.text.toString(),
                    "Phone" to phone.text.toString(),
                    "Email" to email.text.toString()
                )


                val Users=db.collection("USERS")
                val query =Users.whereEqualTo("email",email.text.toString()).get()
                    .addOnSuccessListener {
                            tasks->
                        if(tasks.isEmpty)
                        {
                            auth.createUserWithEmailAndPassword(email.text.toString(),password.text.toString())
                                .addOnCompleteListener(this){
                                        task->
                                    if(task.isSuccessful)
                                    {
                                        progressDialog= ProgressDialog(this)
                                        progressDialog.setTitle("Creating New Account")
                                        progressDialog.setMessage("Please wait...")
                                        progressDialog.setCanceledOnTouchOutside(false)
                                        progressDialog.show()
                                        Users.document(email.text.toString()).set(user)
                                        val intent=Intent(this,LoginActivity::class.java)
                                        intent.putExtra("email",email.text.toString())
                                        startActivity(intent)
                                        finish()
                                        Toast.makeText(this,"Sign Up successfully!", Toast.LENGTH_SHORT).show()
                                    }
                                    else
                                    {
                                        Toast.makeText(this,"Email Already Registered", Toast.LENGTH_SHORT).show()
                                    }
                                }
                        }
                    }.addOnFailureListener { error -> Toast.makeText(this,error.toString(), Toast.LENGTH_SHORT).show()}
            }
        }
    }

    private fun signUpValidation():Boolean{
        var isValid=true
        if (email.text.toString().isEmpty()&&password.text.toString().isEmpty()&&username.text.toString().isEmpty()&&password.text.toString().isEmpty()&&password1.text.toString().isEmpty())
        {
            email.error="Please enter your email!"
            username.error="Please enter your username!"
            phone.error="Please enter your phone!"
            password.error="Please enter your password!"
            password1.error="Please confirm your password!"
            Toast.makeText(this, "Fields cannot be empty!", Toast.LENGTH_SHORT).show()
            isValid=false
        }
        if (email.text.toString().isEmpty()){
            email.error="Please enter your email!"
            email.requestFocus()
            isValid=false
        }
        if(!(android.util.Patterns.EMAIL_ADDRESS.matcher(email.text.toString()).matches())){
            email.error="Invalid email"
            email.requestFocus()
            isValid=false
        }
        if (username.text.toString().isEmpty()){
            username.error="Please enter your username!"
            username.requestFocus()
            isValid=false
        }
        if (phone.text.toString().isEmpty()){
            phone.error="Please enter your phone number!"
            phone.requestFocus()
            isValid=false
        }else if(!PHONE_REGEX.toRegex().matches(phone.text.toString())){
            phone.error = "Invalid phone number! format: 011-11111111"
            isValid = false
        }
        if (password.text.toString().isEmpty()){
            password.error="Please enter your password!"
            password.requestFocus()
            isValid=false
        }
        if (password1.text.toString().isEmpty()){
            password1.error="Please confirm your password!"
            password1.requestFocus()
            isValid=false
        }else if (!password.text.toString().equals(password1.text.toString())){
            password1.error="Does not match with password!"
            password1.requestFocus()
            isValid=false
        }
        return isValid
    }

}