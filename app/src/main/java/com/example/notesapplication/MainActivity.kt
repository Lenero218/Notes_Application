package com.example.notesapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity(), NoteClickInterface, NoteClickDeleteInterface {

    lateinit var notesRV : RecyclerView
    lateinit var addFAB : FloatingActionButton
    lateinit var noteViewModel : NotesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        notesRV = findViewById(R.id.idRVNotes)
        addFAB = findViewById(R.id.idFABAddNote)
        val viewModelProviderFactory =ViewModelProviderFactory(application)

        val noteRVAdapter = NoteRVAdapter(this,this,this)
        notesRV.adapter = noteRVAdapter
        notesRV.layoutManager = LinearLayoutManager(this)
       // noteViewModel = ViewModelProvider(this,ViewModelProvider.AndroidViewModelFactory.getInstance(application)).get(NotesViewModel::class.java)

        noteViewModel = ViewModelProvider(this,viewModelProviderFactory).get(NotesViewModel::class.java)

        noteViewModel.allNotes.observe(this, Observer {list->
            list?.let{
                noteRVAdapter.updateList(list)
            }
        })
        addFAB.setOnClickListener{
            val intent = Intent(this@MainActivity,AddEditNoteActivity::class.java)
            startActivity(intent)
            this.finish()
        }



    }

    override fun onNoteClick(note: Note) {
        val intent = Intent(this@MainActivity,AddEditNoteActivity::class.java)
        intent.putExtra("noteType","Edit")
        intent.putExtra("noteTitle",note.noteTitle)
        intent.putExtra("noteDescription", note.noteDescription)
        intent.putExtra("noteId",note.id)
        startActivity(intent)
        this.finish()


    }

    override fun onDeleteIconClick(note: Note) {
        noteViewModel.deleteNote(note)
        Toast.makeText(this,"${note.noteTitle} Deleted", Toast.LENGTH_LONG).show()
    }
}