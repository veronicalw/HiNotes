package com.example.hinotes
import android.app.Application
import android.util.Log
import com.example.hinotes.core.addnote_activity.AddNoteContract
import com.example.hinotes.core.addnote_activity.AddNotePresenter
import com.google.firebase.FirebaseApp
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.runners.MockitoJUnitRunner


/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(MockitoJUnitRunner::class)
class ExampleUnitTest: AddNoteContract.View{

    // Calling Presenter to test
    lateinit var mPresenter : AddNotePresenter

    //Input data to db
    var titles = "Note Unit Test"
    var contents = "This is a unit test sample for user veronicalw@gmail.com"
    @Before
    fun initialize(){
        mPresenter = AddNotePresenter(this)
    }
    @Test
    fun testAddNotePresenter(){
        Mockito.verify("success")
   }

    override fun onAddSuccess(message: String?) {
        Log.d("Firestore Add Data", "Success")
    }

    override fun onAddFailure(message: String?) {
        Log.d("Firestore Add Data", "Failed")
    }

}