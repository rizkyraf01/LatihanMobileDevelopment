package com.andan.simplenote

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.andan.simplenote.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var mainBinding: ActivityMainBinding
    private lateinit var sqliteHElper: SQLiteHelper
    private var adapter: NoteAdapter? = null
    private var note: NoteModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding =  ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)

        sqliteHElper = SQLiteHelper(this)
        setRvNote()

        mainBinding.addButton.setOnClickListener{
            addNote()
        }

        mainBinding.viewButton.setOnClickListener{
            getNote()
        }

        mainBinding.editButton.setOnClickListener{
            editNote()
        }

        adapter?.setOnClickItem {
            Toast.makeText(this, "Selected " + it.title, Toast.LENGTH_SHORT).show()

            mainBinding.titleEditText.setText(it.title)
            mainBinding.textEditText.setText(it.text)
            note=it
        }

        adapter?.setOnDeleteItem {
            deleteNote(it.id)
        }
    }

    private fun setRvNote(){
        mainBinding.noteRv.layoutManager = LinearLayoutManager(this)
        adapter = NoteAdapter()
        mainBinding.noteRv.adapter = adapter
    }

    private fun deleteNote(id: Int){
        val builder = AlertDialog.Builder(this)
        builder.setMessage("Are you sure want to delete this Note?")
        builder.setCancelable(true)
        builder.setPositiveButton("Yes"){dialog, _ ->
            sqliteHElper.deleteNote(id)
            getNote()
            dialog.dismiss()
        }
        builder.setNegativeButton("No"){dialog, _ ->
            dialog.dismiss()
        }

        val alert = builder.create()
        alert.show()
    }

    private fun editNote(){
        val title = mainBinding.titleEditText.text.toString()
        val text = mainBinding.textEditText.text.toString()

        if (title == note?.title && text == note?.text ){
            Toast.makeText(this, "Nothing changed..", Toast.LENGTH_SHORT).show()
            return
        }

        if (note == null) return

        val std = NoteModel(id = note!!.id, title=title, text=text)
        val status = sqliteHElper.updateNote(std)
        if (status > -1){
            clearEditText()
            getNote()
        }else{
            Toast.makeText(this, "Edit failed..", Toast.LENGTH_SHORT).show()
        }
    }

    private fun getNote(){
        val stdList = sqliteHElper.getAllNote()
        Log.e("test", "${stdList.size}")

        adapter?.addItems(stdList)
    }

    private fun addNote(){
        val title = mainBinding.titleEditText.text.toString()
        val text = mainBinding.textEditText.text.toString()

        if(title.isEmpty() || text.isEmpty()){
            Toast.makeText(this, "Masukan data..", Toast.LENGTH_SHORT).show()
        }else{
            val std = NoteModel(title = title, text = text)
            val status =  sqliteHElper.insertNote(std)

            if(status > -1){
                Toast.makeText(this, "Note Added..", Toast.LENGTH_SHORT).show()
                clearEditText()
                getNote()
            }else{
                Toast.makeText(this, "Not saved..", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun clearEditText(){
        mainBinding.titleEditText.setText("")
        mainBinding.textEditText.setText("")
        mainBinding.titleEditText.requestFocus()
    }
}