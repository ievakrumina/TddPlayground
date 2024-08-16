package org.example.multicurrencymoney


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

    override fun reduce(to: String) = this
}

interface Expression {
    fun reduce(to: String): Money
}

class Bank {
    fun reduce(source:Expression, to: String): Money {
        return source.reduce(to)
    }
}

class Sum( 
    val augend: Money,
    val addend: Money
): Expression {
   override fun reduce(to: String): Money {
       val amount = augend.amount + addend.amount
       return Money(amount, to)
   }
}