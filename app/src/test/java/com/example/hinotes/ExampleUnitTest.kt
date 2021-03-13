package com.example.hinotes
import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import com.example.hinotes.core.main_activity.MainActivityContract
import com.example.hinotes.core.main_activity.MainActivityPresenter
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.*
import org.mockito.runners.MockitoJUnitRunner


/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(MockitoJUnitRunner::class)
class ExampleUnitTest{

    //Objet Mock
    val testName = "name"

    //Calling contract and presenter
    val view = Mockito.mock(MainActivityContract.View::class.java)
    val interactor= Mockito.mock(MainActivityContract.Interactor::class.java)
    lateinit var mPresenter : MainActivityPresenter

    @Before
    fun initialize(){
        mPresenter = MainActivityPresenter(view, interactor)
        doNothing().`when`(interactor).getprofileUpdate(testName)
    }
    @Test
    fun testProfileUpdate(){
        mPresenter.profileUpdate(testName)
        Mockito.verify(interactor, times(1)).getprofileUpdate(testName)
    }
}