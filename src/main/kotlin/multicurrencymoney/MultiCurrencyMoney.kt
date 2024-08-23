package org.example.multicurrencymoney

import java.util.Hashtable
import kotlin.Pair


open class Money(initialValue: Int, protected val currency: String): Expression {
    var amount: Int = initialValue

    fun currency(): String = currency

    override fun equals(other: Any?): Boolean {
        val money = other as Money
        return amount == money.amount
                && currency == money.currency()
    }

    fun times(multiplier:Int): Money {
        return Money(amount * multiplier, currency)
    }

    override fun toString(): String {
        return amount.toString() + " " + currency
    }

    fun plus(addend: Money): Expression {
        return Sum(this, addend)
    }

    companion object {
        fun dollar(initialValue: Int) = Money(initialValue, "USD")
        fun franc(initialValue: Int) = Money(initialValue, "CHF")
    }

    override fun reduce(bank: Bank, to: String): Money {
        val rate = bank.rates.get(Pair(currency, to)) ?: 1
        return Money(amount / rate,to)
    }
}

interface Expression {
    fun reduce(bank: Bank, to: String): Money
}

class Bank {

    val rates = Hashtable<Pair<String, String>, Int>()
    fun reduce(source:Expression, to: String): Money {
        return source.reduce(this, to)
    }

    fun addRate(from: String, to: String, rate: Int) {
        rates[Pair(from, to)] = rate
    }

    fun getRate(from: String, to: String) = rates.get(Pair(from, to)) ?: 1

}

class Sum( 
    val augend: Money,
    val addend: Money
): Expression {
   override fun reduce(bank: Bank, to: String): Money {
       val amount = augend.amount + addend.amount
       return Money(amount, to)
   }
}