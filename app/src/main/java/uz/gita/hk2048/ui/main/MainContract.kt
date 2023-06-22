package uz.gita.hk2048.ui.main

import uz.gita.hk2048.model.FindSide

interface MainContract {
    interface Model{
        fun getRecord():String
        fun getMatrix():Array<Array<Int>>
    }
    interface View{
        fun loadWidgets()
        fun describeToDisplay(matrix:Array<Array<Int>>)

    }
    interface Presenter {
        fun controlValues()
        fun move(side: FindSide)

    }
}