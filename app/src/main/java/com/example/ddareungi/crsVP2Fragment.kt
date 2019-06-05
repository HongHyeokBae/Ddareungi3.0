package com.example.ddareungi


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class crsVP2Fragment : Fragment() {

    val plusforimg=2
    val plusforinfo=1
    var indexx=0



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
       indexx =this.getArguments()!!.getInt("index")
        return inflater.inflate(R.layout.fragment_crs_vp2, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setdata(indexx)
    }
    fun setdata(num:Int){
        var drawableTypeArray=context!!.resources.obtainTypedArray(R.array.drawable)

        if(activity!=null) {
            val imageView = activity!!.findViewById<ImageView>(R.id.crsvp2_titleimg)
            val title=activity!!.findViewById<TextView>(R.id.crsvp2_mainTitle)
            val subtitle=activity!!.findViewById<TextView>(R.id.crsvp2_subTitle)
            val bikeStop=activity!!.findViewById<TextView>(R.id.crsvp2_busstop)
            val open=activity!!.findViewById<TextView>(R.id.crsvp2_time)
            val place=activity!!.findViewById<TextView>(R.id.crsvp2_location)
            val tel=activity!!.findViewById<TextView>(R.id.crsvp2_tel)

            imageView.setImageResource(drawableTypeArray.getResourceId(num*5+plusforimg,-1))
          val index=num*4+plusforinfo
            title.text= MainActivity.courseInfoList[index].mainTitle
            subtitle.text= MainActivity.courseInfoList[index].subtitle
            bikeStop.text= MainActivity.courseInfoList[index].bikeStop
            open.text= MainActivity.courseInfoList[index].open
            place.text= MainActivity.courseInfoList[index].location
            tel.text= MainActivity.courseInfoList[index].tel
        }
    }

}
