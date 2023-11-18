package com.example.myapplication

import android.os.Bundle
import java.io.IOException

import android.widget.Button
import android.widget.TextView
import androidx.activity.ComponentActivity
import com.google.cloud.language.v1.Document
import java.util.Random
import com.google.cloud.language.v1.LanguageServiceClient
import org.w3c.dom.Text

data class Plant(val name: String, val growthRate: Double, val sensoryReceptors: List<SensoryReceptor>)

// Define SensoryReceptor class
data class SensoryReceptor(val id: String, val type: String)

class MainActivity() : ComponentActivity() {


private lateinit var comparisonTextView:TextView
private lateinit var  sentimentTextView1:TextView
private lateinit var sentimentTextView2:TextView
private lateinit var plant1DataTextView:TextView
private lateinit var plant2DataTextView:TextView





    override fun onCreate(savedInstanceState: Bundle?) {

                    super.onCreate(savedInstanceState)
            setContentView(R.layout.layout)

            // Create Plant1
            val plant1 = Plant("Sunflower", 0.75, listOf(SensoryReceptor("1", "type1"), SensoryReceptor("2", "type2")))

            // Create Plant2 with texture
            val plant2 = Plant("Orchids", 0.85, listOf(SensoryReceptor("3", "type1"), SensoryReceptor("4", "type2"), SensoryReceptor("5", "texture")))
        // Compare Plant1 and Plant2
        val comparisonResult = comparePlants(plant1, plant2)

        // Simulate sentiment analysis for Plant1
        val sentimentResult1 = analyzeSentiment(plant1)

        // Simulate sentiment analysis for Plant2
        val sentimentResult2 = analyzeSentiment(plant2)

        // Display results in TextViews
        val comparisonTextView: TextView = findViewById(R.id.comparisonTextView)
        comparisonTextView.text = comparisonResult

        val sentimentTextView1: TextView = findViewById(R.id.sentimentTextView1)
        sentimentTextView1.text = sentimentResult1

        val sentimentTextView2: TextView = findViewById(R.id.sentimentTextView2)
        sentimentTextView2.text = sentimentResult2

        val plant1DataTextView: TextView = findViewById(R.id.plant1DataTextView)
        plant1DataTextView.text = "Plant1 Data:\nName: ${plant1.name}\nGrowth Rate: ${plant1.growthRate}"

        val plant2DataTextView: TextView = findViewById(R.id.plant2DataTextView)
        plant2DataTextView.text = "Plant2 Data:\nName: ${plant2.name}\nGrowth Rate: ${plant2.growthRate}"
    }



        // Function to compare two plants based on growth rate
        private fun comparePlants(plant1: Plant, plant2: Plant): String {
            return when {
                plant1.growthRate > plant2.growthRate -> "${plant1.name} has a higher growth rate."
                plant1.growthRate < plant2.growthRate -> "${plant2.name} has a higher growth rate."
                else -> "Both plants have the same growth rate."
            }
        }

        // Function to simulate sentiment analysis
        private fun analyzeSentiment(plant: Plant): String {
         try {
        val languageServiceClient = LanguageServiceClient.create()
        val document = Document.newBuilder().setContent("${plant.name}'s energy levels are positive.").setType(Document.Type.PLAIN_TEXT).build()
        val sentiment = languageServiceClient.analyzeSentiment(document).sentiment.score
        languageServiceClient.close()

        return if (sentiment >= 0) {
            "${plant.name}'s energy levels are positive."
        } else {
            "${plant.name}'s energy levels are negative."
        }
    } catch (e: IOException) {
        e.printStackTrace()
        return "Error analyzing sentiment."
    }        }
    







                













