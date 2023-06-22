package uz.gita.hk2048.repository

import android.annotation.SuppressLint
import android.util.Log
import uz.gita.hk2048.model.Settings
import uz.gita.hk2048.utils.copyData
import java.time.LocalDate
import kotlin.random.Random


class Repository private constructor(){
    companion object{
        private lateinit var instance: Repository
        fun getInstance():Repository{
            if (!(::instance.isInitialized)){
                instance = Repository()
            }
            return instance
        }

    }

    private val settings = Settings.getInstance()
    private var NEW_ELEMENT = 2
    var SCORE = 0
    var REKORD = 0
    lateinit var matrix:Array<Array<Int>>
    val sharedPreferences = settings.gerSharpedPref()
    init {
        loadFromPref()
    }
    fun resetScore(){
        SCORE=0
    }

    fun isSwipable(): Boolean {
        for (i in 0 until  matrix.size){
            for (j in 0 until matrix.size-1){
                if (matrix[i][j]==matrix[i][j+1] || matrix[i][j]==0 || matrix[i][j+1]==0){
                    return true
                }
            }
        }
        for (j in 0 until matrix.size){
            for (i in 0 until  matrix.size-1){
                if (matrix[i][j]==matrix[i+1][j] || matrix[i][j]==0 || matrix[i+1][j]==0){
                    return true
                }
            }
        }
        return false
    }

    private fun addNewElement() {
        val emptyList = ArrayList<Pair<Int, Int>>()
        for (i in matrix.indices){
            for (j in matrix[i].indices){
                if (matrix[i][j]==0) emptyList.add(Pair(i,j))
            }
        }
        if(emptyList.isEmpty()) {

        } else {
            val randomPos = Random.nextInt(0, emptyList.size)
            matrix[emptyList[randomPos].first][emptyList[randomPos].second] = NEW_ELEMENT
        }
    }
    fun moveToLeft(){
        val temp = matrix.copyData()
        for (i in matrix.indices){
            val amounts = ArrayList<Int>(4)
            var bool = true
            for (j in matrix[i].indices){
                if (matrix[i][j]==0) continue
                if (amounts.isEmpty()) amounts.add(matrix[i][j])
                else{
                    if (amounts.last() == matrix[i][j] && bool){
                        amounts[amounts.size-1] = amounts.last()*2
                        SCORE+=amounts.last()
                        bool = false
                    }else{
                        amounts.add(matrix[i][j])
                        bool = true
                    }
                }
                matrix[i][j] = 0
            }
            for (k in amounts.indices){
                matrix[i][k] = amounts[k]
            }
        }
        if (REKORD<=SCORE){
            REKORD = SCORE
            sharedPreferences.edit().putInt("REKORD", SCORE).apply()

        }
        if (!temp.contentDeepEquals(matrix))
            addNewElement()
    }
    fun moveToRight(){
        val temp = matrix.copyData()
        for (i in matrix.indices){
            val amounts = ArrayList<Int>(4)
            var bool = true
            for (j in matrix[i].size-1 downTo 0){
                if (matrix[i][j]==0) continue
                if (amounts.isEmpty()) amounts.add(matrix[i][j])
                else{
                    if (amounts.last() == matrix[i][j] && bool){
                        amounts[amounts.size-1] = amounts.last()*2
                        SCORE+=amounts.last()
                        bool = false
                    }else{
                        amounts.add(matrix[i][j])
                        bool = true
                    }
                }
                matrix[i][j] = 0
            }
            for (k in amounts.indices){
                matrix[i][matrix[i].size-k-1] = amounts[k]
            }
        }
        if (REKORD<=SCORE){
            REKORD = SCORE
            sharedPreferences.edit().putInt("REKORD", SCORE).apply()

        }
        if (!temp.contentDeepEquals(matrix))
            addNewElement()
    }
     fun moveToDown() {
         val temp = matrix.copyData()
        for (i in matrix.indices) {
            val amount = ArrayList<Int>()
            var bool = true
            for (j in matrix[i].size-1 downTo 0) {
                if (matrix[j][i] == 0) continue
                if (amount.isEmpty()) amount.add(matrix[j][i])
                else {
                    if (amount.last() == matrix[j][i] && bool) {
                        amount[amount.size-1] =amount.last()*2
                        SCORE+=amount.last()
                        bool = false
                    } else {
                        bool = true
                        amount.add(matrix[j][i])
                    }
                }
                matrix[j][i] = 0
            }
             for (j in  amount.size-1 downTo  0) {
                matrix[3-j][i] = amount[j]
            }
        }
         if (REKORD<=SCORE){
             REKORD = SCORE
             sharedPreferences.edit().putInt("REKORD", SCORE).apply()

         }
         if (!temp.contentDeepEquals(matrix))
             addNewElement()
    }
    fun moveToUp() {
        val temp = matrix.copyData()
        for (i in matrix.indices)  {
            val amount = ArrayList<Int>()
            var bool = true
            for (j in matrix[i].indices) {
                if (matrix[j][i] == 0) continue
                if (amount.isEmpty()) amount.add(matrix[j][i])
                else {
                    if (amount.last() == matrix[j][i] && bool) {
                        amount[amount.size-1] =amount.last()*2
                        SCORE+=amount.last()
                        bool = false
                    } else {
                        bool = true
                        amount.add(matrix[j][i])
                    }
                }
                matrix[j][i] = 0
            }
            for (j in 0 until amount.size) {
                matrix[j][i] = amount[j]
            }
        }
        if (REKORD<=SCORE){
            REKORD = SCORE
            sharedPreferences.edit().putInt("REKORD", SCORE).apply()
        }
        if (!matrix.contentDeepEquals(temp))
            addNewElement()
    }


    @SuppressLint("CommitPrefEdits")
    fun saveToPref(){
        if (isSwipable()){
        var sb:StringBuilder = StringBuilder("")
        for (i in 0 until matrix.size){
            for (j in 0 until matrix[i].size){
                if(i==3 && j==3){
                    sb.append("${matrix[i][j]}")
                }else{
                    sb.append("${matrix[i][j]}##")
                }
            }
        }
            sharedPreferences.edit().putString("MATRIX", sb.toString()).apply()
            sharedPreferences.edit().putBoolean("ISPLAYING", true).apply()
            sharedPreferences.edit().putInt("SCORE", SCORE).apply()
        }else{
            sharedPreferences.edit().putInt("REKORD", REKORD).apply()
            sharedPreferences.edit().putBoolean("ISPLAYING", false).apply()
        }

    }
    fun loadFromPref(){
        if (sharedPreferences.getBoolean("ISPLAYING", false)) {
            matrix = arrayOf(
                arrayOf(0,0,0,0),
                arrayOf(0,0,0,0),
                arrayOf(0,0,0,0),
                arrayOf(0,0,0,0)
            )
            val s: String = sharedPreferences.getString("MATRIX", "0##2##0##0##0##128##512##0##0##1024##0##0##0##0##0##0").toString()
            val charArray: List<String> = s.split("##")
            var index = 0
            for (i in 0 until matrix.size) {
                for (j in 0 until matrix[0].size) {
                    matrix[i][j] = charArray[index++].toInt()
                }
            }
            SCORE = sharedPreferences.getInt("SCORE", 0)
            REKORD = sharedPreferences.getInt("REKORD", 0)

        }else{
            resetMatrix()
        }
    }

    fun resetMatrix(){
         matrix = arrayOf(
            arrayOf(0,0,0,0),
            arrayOf(0,0,0,0),
            arrayOf(0,0,0,0),
            arrayOf(0,0,0,0)
        )
        addNewElement()
        addNewElement()
    }
}