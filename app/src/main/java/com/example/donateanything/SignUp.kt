package com.example.donateanything
import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
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
            if(checking())
            {

                var email=editEmail.text.toString()
                var password= editPwd.text.toString()
                var username=editName.text.toString()
                var phone=editPhone.text.toString()
                val user= hashMapOf(
                    "Username" to username,
                    "Phone" to phone,
                    "Email" to email
                )


                val Users=db.collection("USERS")
                val query =Users.whereEqualTo("email",email).get()
                    .addOnSuccessListener {
                            tasks->
                        if(tasks.isEmpty)
                        {
                            auth.createUserWithEmailAndPassword(email,password)
                                .addOnCompleteListener(this){
                                        task->
                                    if(task.isSuccessful)
                                    {
                                        progressDialog= ProgressDialog(this)
                                        progressDialog.setTitle("Creating New Account")
                                        progressDialog.setMessage("Please wait...")
                                        progressDialog.setCanceledOnTouchOutside(false)
                                        progressDialog.show()
                                        Toast.makeText(this,"Sign Up successfully!", Toast.LENGTH_SHORT).show()
                                        Users.document(email).set(user)
                                        val intent=Intent(this,LoginActivity::class.java)
                                        intent.putExtra("email",email)
                                        startActivity(intent)
                                        finish()
                                    }
                                    else
                                    {
                                        Toast.makeText(this,"Authentication Failed", Toast.LENGTH_SHORT).show()
                                    }
                                }
                        }
                        else
                        {
                            Toast.makeText(this,"Email Already Registered", Toast.LENGTH_SHORT).show()
                        }
                    }.addOnFailureListener { error -> Toast.makeText(this,error.toString(), Toast.LENGTH_SHORT).show()}
            }
            else{
                Toast.makeText(this,"Field cannot be empty!", Toast.LENGTH_SHORT).show()
            }
        } }


    private fun checking():Boolean{
        if(editName.text.toString().trim().isNotEmpty()
            && editPhone.text.toString().trim().isNotEmpty()
            && editEmail.text.toString().trim().isNotEmpty()
            && editPwd.text.toString().trim().isNotEmpty()
            && editPwd1.text.toString().trim().isNotEmpty()
        )
        {
            return true
        }
        return false
    }
}