package com.example.ddareungi


import android.os.Bundle
import android.os.CountDownTimer
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import com.example.ddareungi.MainActivity.Companion.timerStr


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class TimerFragment : Fragment() {

    var timerState=true//반납상태
    companion object {
        var hour=0//시간
    }
    lateinit var timer:CountDownTimer
    lateinit var timerBtn: Button
    lateinit var timerTxt:TextView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_timer, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        timerBtn=activity!!.findViewById(R.id.timerBtn)
        timerTxt = activity!!.findViewById<TextView>(R.id.timerTxt)
        val spinner = activity!!.findViewById<Spinner>(R.id.timerSpinner)
        spinner.onItemSelectedListener = SpinnerSelectedListener()
        updateUI(!timerState)
        init()
        Log.v("timer","oncreated")


    }

    fun init(){

        timer = object : CountDownTimer(3600000, 1000) {//1시간짜리
        override fun onTick(millisUntilFinished: Long) {
            Log.v("timer",timerStr)
         //   timerTxt.text = MainActivity.timerStr
            updateUI(!timerState)
        }

            override fun onFinish() {
                Log.v("timerstate", "onfinish")
                // timerTxt.text=MainActivity.timerStr
                timerTxt.text = "00:00"
            }
        }



        timerBtn.setOnClickListener {

            if (timerState) {
                timerBtn.text = "반납 완료"
                timerState = false
                timer.start()

            } else {
                timerBtn.text = "대여시작"
                timerState = true
                timerTxt.text="0"+hour.toString()+":00"
                timer.cancel()
            }


        }
    }
    fun updateUI(state:Boolean){
        if(state){//실행 중
            timerBtn.text = "반납 완료"
            timerTxt.text = MainActivity.timerStr
       }else {
            timerBtn.text = "대여시작"
            timerTxt.text="0"+hour.toString()+":00"
        }
    }
    override fun onDetach() {
        super.onDetach()
        Log.v("timer","onDetache")
        if(timerState){//타이머 상관없엉
            Log.v("timer","타이머 상관없어용")
        }else{//타이머 작동 중
            Log.v("timer","타이머 작동 중")

        }
    }

    override fun onResume() {
        super.onResume()
        updateUI(!timerState)
    }

    inner class SpinnerSelectedListener: AdapterView.OnItemSelectedListener{
        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            var spinnerStr=parent?.getItemAtPosition(position).toString()
            if(spinnerStr.length>3){
                Log.v("timer","대여시간을 선택하세요")
            }else{
                hour=spinnerStr.substring(0,1).toInt()
                timerTxt.text="0"+hour.toString()+":00"
            }
        }

        override fun onNothingSelected(parent: AdapterView<*>?) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }
    }



}
