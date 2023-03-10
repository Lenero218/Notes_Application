package com.example.notesapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import java.text.SimpleDateFormat
import java.util.*

class AddEditNoteActivity : AppCompatActivity() {

    lateinit var noteTitleEdit : EditText
    lateinit var noteDescriptionEdit : EditText
    lateinit var addUpdateButton : Button

    lateinit var viewModel: NotesViewModel
    var noteId = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_edit_note)

        noteTitleEdit = findViewById(R.id.idEDTNoteTitle)
        noteDescriptionEdit = findViewById(R.id.idEdtNoteDescription)
        addUpdateButton = findViewById(R.id.BTNAddUpdate)
        val viewModelProviderFactory = ViewModelProviderFactory(application)

        //viewModel = ViewModelProvider(this,ViewModelProvider.AndroidViewModelFactory.getInstance(application)).get(NotesViewModel::class.java)
        viewModel = ViewModelProvider(this,viewModelProviderFactory).get(NotesViewModel::class.java)

        val noteType = intent.getStringExtra("noteType")

        if(noteType.equals("Edit")){

            val noteTitle = intent.getStringExtra("noteTitle")
            val noteDesc = intent.getStringExtra("noteDescription")
            noteId = intent.getIntExtra("noteId",-1)
            addUpdateButton.setText("Update Note")
            noteTitleEdit.setText(noteTitle)
            noteDescriptionEdit.setText(noteDesc)
        }else{
            addUpdateButton.setText("Save Note")
        }

        addUpdateButton.setOnClickListener {
            val noteTitle = noteTitleEdit.text.toString()
            val noteDescription = noteDescriptionEdit.text.toString()

            if(noteTitle.equals(("Edit"))){

                if(noteTitle.isNotEmpty() && noteDescription.isNotEmpty()){
                    val sdf = SimpleDateFormat("dd MMM, yyyy - HH:mm")
                    val currentDate : String = sdf.format(Date())
                    val updateNote = Note(noteTitle,noteDescription, currentDate)
                    updateNote.id = noteId
                    viewModel.updateNote(updateNote)
                    Toast.makeText(this,"Note Updated..", Toast.LENGTH_LONG).show()
                }

            }else{


                if(noteTitle.isNotEmpty() && noteDescription.isNotEmpty()) {
                    val sdf = SimpleDateFormat("dd MMM, yyyy - HH:mm")
                    val currentDate: String = sdf.format(Date())
                    viewModel.insertNote(Note(noteTitle,noteDescription,currentDate))
                    Toast.makeText(this,"Note Added..", Toast.LENGTH_LONG).show()
                }


            }

            startActivity(Intent(applicationContext, MainActivity::class.java))
            this.finish()

        }

    }
}