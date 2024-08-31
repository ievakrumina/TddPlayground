package org.example.fibonacci

class Fibonacci {
        fun f(i: Int): Int {
           return when (i) {
               0 -> 0
               1 -> 1
               else -> f(i - 1) + f(i -2)
           }
    }
}