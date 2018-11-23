package com.example.smgue.choreappbypaulodichone.activities

import android.app.ProgressDialog
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.example.smgue.choreappbypaulodichone.R
import com.example.smgue.choreappbypaulodichone.data.ChoresDatabaseHandler
import com.example.smgue.choreappbypaulodichone.model.Chore
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var dbHandler : ChoresDatabaseHandler? = null
    var progressDialog : ProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        dbHandler = ChoresDatabaseHandler(this)

        progressDialog = ProgressDialog(this)
        saveChore.setOnClickListener{
            if(!TextUtils.isEmpty(enterChore.text.toString())
            && !TextUtils.isEmpty(assignBy.text.toString())
            && !TextUtils.isEmpty(assignTo.text.toString())){
                progressDialog!!.setMessage("Saving...")
                progressDialog!!.show()
                //save to database
                var chore = Chore()
                chore.choreName = enterChore.text.toString()
                chore.assignedBy = assignBy.text.toString()
                chore.assignedTo = assignTo.text.toString()

                saveToDB(chore)
                progressDialog!!.cancel()
                startActivity(Intent(this, ChoreListActivity::class.java))
            }else{
                Toast.makeText(this, "Please enter a choer", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun saveToDB(chore: Chore){

        dbHandler!!.createChore(chore)
    }
}