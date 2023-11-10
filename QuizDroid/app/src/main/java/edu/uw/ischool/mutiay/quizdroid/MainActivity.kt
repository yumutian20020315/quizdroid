package edu.uw.ischool.mutiay.quizdroid

import android.annotation.SuppressLint
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private lateinit var quizs: ListView
    private lateinit var pref:Button
    private lateinit var barText:TextView




    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        // get the repo in and access titles
        val repo = (application as QuizApp).accessRepo()
        val titles = repo.getTopicsnames()

        //get action bar
        setSupportActionBar(findViewById(R.id.my_toolbar))




        val titlesWithDes = repo.getTitleswithDes()





        quizs= findViewById(R.id.quizListview)
        pref = findViewById(R.id.my_preference)

        pref.setOnClickListener {
            val intent = Intent(this, Setting::class.java)
            try {
                startActivity(intent)
            } catch (e: ActivityNotFoundException) {

            }
        }

        // old list view
//        val adapter = ArrayAdapter<String>(this,
//            android.R.layout.simple_list_item_1, titlesWithDes)

        // new version of listview with icons
        val adapter = TopicArrayAdapter(this, R.layout.topic_list_item, repo.getAlltopics())
        quizs.adapter = adapter

        quizs.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            val selectedTopic = titles[position]



            val intent = Intent(this, TopicOverViewActivity::class.java)
            intent.putExtra("TOPIC_NAME", selectedTopic)
            try {
                startActivity(intent)
            } catch (e: ActivityNotFoundException) {

            }
        }

    }

    override fun onResume() {
        super.onResume()
        barText = findViewById(R.id.showme)

        val prefs = this.getPreferences(Context.MODE_PRIVATE)


        val message1 = prefs.getString("url_download", "Howdy!")
        val message2 = prefs.getString("minutes_between_download", "Howdy!")

        if (message1 != null) {
            Log.i("test1", message1)
        }
        if (message2 != null) {
            Log.i("test1", message2)
        }
        barText.setText(message1 + " " + message2)
    }


}


// My version of Array adapter
class TopicArrayAdapter(
    context: Context,
    private val resource: Int,
    private val topics: List<Topic>
) : ArrayAdapter<Topic>(context, resource, topics) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var listItemView = convertView
        if (listItemView == null) {
            listItemView = LayoutInflater.from(context).inflate(resource, parent, false)
        }

        val currentTopic = topics[position]
        val titleTextView: TextView = listItemView!!.findViewById(R.id.topicTitle)
        val iconImageView: ImageView = listItemView.findViewById(R.id.topicIcon)

        titleTextView.text = "${currentTopic.title}: ${currentTopic.descriptionShort}"

        iconImageView.setImageResource(currentTopic.iconId)

        return listItemView
    }
}