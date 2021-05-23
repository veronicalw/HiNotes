package com.example.hinotes.view

import android.app.Activity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.chinalwb.are.AREditText
import com.chinalwb.are.styles.toolbar.ARE_ToolbarDefault
import com.chinalwb.are.styles.toolbar.IARE_Toolbar
import com.chinalwb.are.styles.toolitems.*
import com.example.hinotes.R
import com.example.hinotes.core.addnote_activity.AddNoteContract
import com.example.hinotes.core.addnote_activity.AddNoteInteractor
import com.example.hinotes.core.addnote_activity.AddNotePresenter
import com.example.hinotes.html.DemoUtil
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore

class AddNotesActivity : AppCompatActivity(), AddNoteContract.View, AddNoteContract.onAddListener {
    private lateinit var edtTitle: EditText
    private lateinit var edtContent: AREditText
    private lateinit var mToolbar: IARE_Toolbar
    private lateinit var mImageArrow: ImageView
    private var scrollerAtEnd = false
    lateinit var firestore: FirebaseFirestore
    private lateinit var progressBar: ProgressBar
    lateinit var firebaseUser: FirebaseUser
    lateinit var mPresenter: AddNotePresenter
    val mInteractor = AddNoteInteractor(this)

    init {

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_notes)
        setSupportActionBar(findViewById(R.id.toolbars))

        edtTitle = findViewById(R.id.edtNoteTitle)
        edtContent = findViewById(R.id.edtNoteContent)
        mToolbar = findViewById(R.id.areToolbar)
        mImageArrow = findViewById(R.id.arrow)
        progressBar = findViewById(R.id.progressBar)
        firestore = FirebaseFirestore.getInstance()
        firebaseUser = FirebaseAuth.getInstance().currentUser!!

        initToolbar()
    }

    private fun initToolbar() {
        val bold: IARE_ToolItem = ARE_ToolItem_Bold()
        val italic: IARE_ToolItem = ARE_ToolItem_Italic()
        val underline: IARE_ToolItem = ARE_ToolItem_Underline()
        val strikethrough: IARE_ToolItem = ARE_ToolItem_Strikethrough()
        val textsize: IARE_ToolItem = ARE_ToolItem_FontSize()
        val quote: IARE_ToolItem = ARE_ToolItem_Quote()
        val listNumber: IARE_ToolItem = ARE_ToolItem_ListNumber()
        val listBullet: IARE_ToolItem = ARE_ToolItem_ListBullet()
        val hr: IARE_ToolItem = ARE_ToolItem_Hr()
        val link: IARE_ToolItem = ARE_ToolItem_Link()
        val left: IARE_ToolItem = ARE_ToolItem_AlignmentLeft()
        val center: IARE_ToolItem = ARE_ToolItem_AlignmentCenter()
        val right: IARE_ToolItem = ARE_ToolItem_AlignmentRight()

        mToolbar.addToolbarItem(bold)
        mToolbar.addToolbarItem(italic)
        mToolbar.addToolbarItem(underline)
        mToolbar.addToolbarItem(strikethrough)
        mToolbar.addToolbarItem(textsize)
        mToolbar.addToolbarItem(quote)
        mToolbar.addToolbarItem(listNumber)
        mToolbar.addToolbarItem(listBullet)
        mToolbar.addToolbarItem(hr)
        mToolbar.addToolbarItem(link)
        mToolbar.addToolbarItem(left)
        mToolbar.addToolbarItem(center)
        mToolbar.addToolbarItem(right)

        edtContent.setToolbar(mToolbar)

        initToolbarArrow()
    }

    private fun initToolbarArrow() {
        if (this.mToolbar is ARE_ToolbarDefault) {
            (mToolbar as ARE_ToolbarDefault).viewTreeObserver.addOnScrollChangedListener {
                val scrollX = (mToolbar as ARE_ToolbarDefault).scrollX
                val scrollWidth = (mToolbar as ARE_ToolbarDefault).width
                val fullWidth = (mToolbar as ARE_ToolbarDefault).getChildAt(0).width
                if (scrollX + scrollWidth < fullWidth) {
                    mImageArrow.setImageResource(R.drawable.arrow_right_foreground)
                    scrollerAtEnd = false
                } else {
                    mImageArrow.setImageResource(R.drawable.arrow_left_foreground)
                    scrollerAtEnd = true
                }
            }
        }
        mImageArrow.setOnClickListener {
            if (scrollerAtEnd) {
                (mToolbar as ARE_ToolbarDefault).smoothScrollBy(-Int.MAX_VALUE, 0)
                scrollerAtEnd = false
            } else {
                val hsWidth = (mToolbar as ARE_ToolbarDefault).getChildAt(0).width
                (mToolbar as ARE_ToolbarDefault).smoothScrollBy(hsWidth, 0)
                scrollerAtEnd = true
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.add_act_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId){
            R.id.nav_save -> {
                mPresenter = AddNotePresenter(this, mInteractor)
                val stringTitle = edtTitle.text.toString()
                val stringContent = edtContent.text.toString()
                if (stringTitle.isEmpty() && stringContent.isEmpty()) {
                    Toast.makeText(this, "your note shouldn't be empty!", Toast.LENGTH_LONG).show()
                    return false
                }
                progressBar.visibility = View.VISIBLE
                mPresenter.performAddNote(stringTitle, stringContent,this@AddNotesActivity, firestore)
                return true
            }
            R.id.nav_save_html -> {
                val stringContent = edtContent.text.toString()
                DemoUtil.saveHtml(this, stringContent)
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
    override fun onAddSuccess(message: String?) {
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show()
        finish()
    }
    override fun onAddFailure(message: String?) {
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show()
        finish()
    }

    override fun onSuccessListener(message: String) {
//
    }

    override fun onFailureListener(message: String) {
//
    }
}