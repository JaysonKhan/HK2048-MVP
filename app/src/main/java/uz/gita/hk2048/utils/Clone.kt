package uz.gita.hk2048.utils

fun  Array<Array<Int>>.copyData() = map { it.clone() }.toTypedArray()