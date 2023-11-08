package edu.uw.ischool.mutiay.quizdroid

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    private lateinit var quizs: ListView




    override fun onCreate(savedInstanceState: Bundle?) {
        // get the repo in and access titles
        val repo = (application as QuizApp).accessRepo()
        val titles = repo.getTopicsnames()

        val titlesWithDes = repo.getTitleswithDes()

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        quizs= findViewById(R.id.quizListview)

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