package uz.gita.hk2048.utils

import uz.gita.hk2048.R

class BackUtils {
    private val backgraundMap = HashMap<Int, Int>()

    init {
        loadMap()
    }

    private fun loadMap() {
        backgraundMap[0] = R.drawable.bg_item_0
        backgraundMap[1] = R.drawable.bg_item_1
        backgraundMap[2] = R.drawable.bg_item_2
        backgraundMap[4] = R.drawable.bg_item_4
        backgraundMap[8] = R.drawable.bg_item_8
        backgraundMap[16] = R.drawable.bg_item_16
        backgraundMap[32] = R.drawable.bg_item_32
        backgraundMap[64] = R.drawable.bg_item_64
        backgraundMap[128] = R.drawable.bg_item_128
        backgraundMap[256] = R.drawable.bg_item_256
        backgraundMap[512] = R.drawable.bg_item_512
        backgraundMap[1024] = R.drawable.bg_item_1024
        backgraundMap[2048] = R.drawable.bg_item_2048
    }
    fun colorByAmount(amount:Int):Int{
        return backgraundMap.getOrDefault(amount, R.drawable.bg_item_0)
    }
}