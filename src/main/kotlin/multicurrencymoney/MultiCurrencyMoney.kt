package org.example.multicurrencymoney


open class Money(initialValue: Int, protected val currency: String): Expression {
    protected var amount: Int = initialValue

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

    fun plus(addition: Money): Expression {
        return Money(amount + addition.amount, currency)
    }

    companion object {
        fun dollar(initialValue: Int) = Money(initialValue, "USD")
        fun franc(initialValue: Int) = Money(initialValue, "CHF")
    }
}

interface Expression

class Bank {
    fun reduce(source:Expression, to: String): Money {
        return Money.dollar(10)
    }
}